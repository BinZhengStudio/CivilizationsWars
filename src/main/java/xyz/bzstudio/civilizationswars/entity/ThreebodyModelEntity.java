package xyz.bzstudio.civilizationswars.entity;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class ThreebodyModelEntity extends Entity {

	public ThreebodyModelEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public ThreebodyModelEntity(World world, double x, double y, double z) {
		this(EntityTypeList.THREEBODY_MODEL, world);
		this.setLocationAndAngles(x, z, z, this.rotationYaw, this.rotationPitch);
		this.setInvulnerable(true);
		this.setNoGravity(true);
	}

	@Override
	public void tick() {
		if (this.world.isAreaLoaded(this.getPosition(), 0)) {
			Vector3d vector3d = this.getMotion();
			double x = this.getPosX() + vector3d.x;
			double y = this.getPosY() + vector3d.y;
			double z = this.getPosZ() + vector3d.z;
			ProjectileHelper.rotateTowardsMovement(this, 0.2F);
			this.setMotion(vector3d.add(this.updateAccel()));
			this.setPosition(x, y, z);
		}
	}

	private Vector3d updateAccel() {
		Vector3d vector3d = Vector3d.ZERO;
		for (ThreebodyModelEntity entity : this.getOthersInRadius()) {
			double distance = this.getDistance(entity);
			double xDistance = entity.getPosX() - this.getPosX();
			double yDistance = entity.getPosY() - this.getPosY();
			double zDistance = entity.getPosZ() - this.getPosZ();
			vector3d.add(xDistance / distance, yDistance / distance, zDistance / distance);
		}
		return vector3d;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		this.markVelocityChanged();
		Entity entity = source.getTrueSource();
		if (entity instanceof PlayerEntity) {
			this.setMotion(this.getMotion().add(entity.getLookVec().scale(0.1D)));
			return true;
		} else {
			return false;
		}
	}

	private List<ThreebodyModelEntity> getOthersInRadius() {
		AxisAlignedBB axisAlignedBB = new AxisAlignedBB(this.getPosX() - 10, this.getPosY() - 10, this.getPosZ() - 10, this.getPosX() + 10, this.getPosY() + 10, this.getPosZ() + 10);
		List<ThreebodyModelEntity> list = Lists.newArrayList();
		for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, axisAlignedBB)) {
			if (entity instanceof ThreebodyModelEntity) {
				list.add((ThreebodyModelEntity) entity);
			}
		}
		return list;
	}

	@Override
	protected void registerData() {
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
