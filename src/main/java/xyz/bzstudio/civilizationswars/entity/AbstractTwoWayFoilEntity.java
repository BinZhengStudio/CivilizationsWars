package xyz.bzstudio.civilizationswars.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.bzstudio.civilizationswars.common.Config;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class AbstractTwoWayFoilEntity extends Entity implements IEntityAdditionalSpawnData {
	public static int radius = 20;
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;
	private UUID owner;
	private int ownerId;
	private boolean leftOwner;

	public AbstractTwoWayFoilEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public AbstractTwoWayFoilEntity(EntityType<? extends AbstractTwoWayFoilEntity> type, double x, double y, double z, double accelX, double accelY, double accelZ, World world) {
		this(type, world);
		this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
		this.recenterBoundingBox();
		this.accelerationX = accelX * 0.1D;
		this.accelerationY = accelY * 0.1D;
		this.accelerationZ = accelZ * 0.1D;
		if (!world.isRemote) {
			radius = Config.COMMON.twoWayFoilRadius.get();
		}
	}

	public AbstractTwoWayFoilEntity(EntityType<? extends AbstractTwoWayFoilEntity> type, Entity shooter, double accelX, double accelY, double accelZ, World world) {
		this(type, shooter.getPosX(), shooter.getPosY(), shooter.getPosZ(), accelX, accelY, accelZ, world);
		this.setShooter(shooter);
		this.setRotation(shooter.rotationYaw, shooter.rotationPitch);
	}

	@Override
	protected void registerData() {
	}

	@Nullable
	public Entity getShooter() {
		if (this.owner != null && this.world instanceof ServerWorld) {
			return ((ServerWorld) this.world).getEntityByUuid(this.owner);
		} else {
			return this.ownerId != 0 ? this.world.getEntityByID(this.ownerId) : null;
		}
	}

	public void setShooter(@Nullable Entity entityIn) {
		if (entityIn != null) {
			this.owner = entityIn.getUniqueID();
			this.ownerId = entityIn.getEntityId();
		}

	}

	public void tick() {
		Entity entity = this.getShooter();
		if (this.world.isRemote || (entity == null || !entity.removed) && this.world.isBlockLoaded(this.getPosition())) {

			if (!this.leftOwner) {
				this.leftOwner = this.leftOwner();
			}
			super.tick();

			RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, (entityIn) -> {
				boolean flag;
				if (!entityIn.isSpectator() && entityIn.isAlive() && entityIn.canBeCollidedWith()) {
					flag = entity == null || this.leftOwner || !entity.isRidingSameEntity(entityIn);
				} else {
					flag = false;
				}
				return flag && !entityIn.noClip;
			});
			if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
				this.onImpact(raytraceresult);
			}

			this.doBlockCollisions();
			Vector3d vector3d = this.getMotion();
			double d0 = this.getPosX() + vector3d.x;
			double d1 = this.getPosY() + vector3d.y;
			double d2 = this.getPosZ() + vector3d.z;
			ProjectileHelper.rotateTowardsMovement(this, 0.2F);

			this.setMotion(vector3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(0.95D));
			this.setPosition(d0, d1, d2);
		} else {
			this.remove();
		}
	}

	private boolean leftOwner() {
		Entity entity = this.getShooter();
		if (entity != null) {
			for (Entity entity1 : this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (p_234613_0_) -> !p_234613_0_.isSpectator() && p_234613_0_.canBeCollidedWith())) {
				if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		if (compound.hasUniqueId("Owner")) {
			this.owner = compound.getUniqueId("Owner");
		}
		this.leftOwner = compound.getBoolean("LeftOwner");

		if (compound.contains("power", 9)) {
			ListNBT listnbt = compound.getList("power", 6);
			if (listnbt.size() == 3) {
				this.accelerationX = listnbt.getDouble(0);
				this.accelerationY = listnbt.getDouble(1);
				this.accelerationZ = listnbt.getDouble(2);
			}
		}
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		if (this.owner != null) {
			compound.putUniqueId("Owner", this.owner);
		}

		if (this.leftOwner) {
			compound.putBoolean("LeftOwner", true);
		}

		compound.put("power", this.newDoubleNBTList(this.accelerationX, this.accelerationY, this.accelerationZ));
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	public float getCollisionBorderSize() {
		return 1.0F;
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}

	protected abstract void onImpact(RayTraceResult result);

	@Override
	public void readSpawnData(PacketBuffer buffer) {
		radius = buffer.readInt();
		this.accelerationX = buffer.readDouble();
		this.accelerationY = buffer.readDouble();
		this.accelerationZ = buffer.readDouble();
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeInt(radius);
		buffer.writeDouble(this.accelerationX);
		buffer.writeDouble(this.accelerationY);
		buffer.writeDouble(this.accelerationZ);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
