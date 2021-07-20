package xyz.bzstudio.civilizationswars.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class TwoWayFoilEntity extends DamagingProjectileEntity {
	protected TwoWayFoilEntity(EntityType<? extends DamagingProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public TwoWayFoilEntity(double x, double y, double z, double accelX, double accelY, double accelZ, World world) {
		this(EntityTypeList.TWO_WAY_FOIL, world);
		this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
		this.recenterBoundingBox();
		this.accelerationX = accelX * 0.1D;
		this.accelerationY = accelY * 0.1D;
		this.accelerationZ = accelZ * 0.1D;
	}

	public TwoWayFoilEntity(Entity shooter, double accelX, double accelY, double accelZ, World world) {
		this(shooter.getPosX(), shooter.getPosY(), shooter.getPosZ(), accelX, accelY, accelZ, world);
		this.setShooter(shooter);
		this.setRotation(shooter.rotationYaw, shooter.rotationPitch);
	}

//	@Override
//	public void tick() {
//		this.baseTick();
//	}

	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		this.remove();
	}

//	@Override
//	public void baseTick() {
//		Entity entity = this.getShooter();
//		if (this.world.isRemote || (entity == null || !entity.removed) && this.world.isAreaLoaded(this.getPosition(), 0)) {
//
//			if (!this.world.isRemote) {
//				this.setFlag(6, this.isGlowing());
//			}
//			super.baseTick();
//
//			RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
//			if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
//				this.onImpact(raytraceresult);
//			}
//
//			this.doBlockCollisions();
//			Vector3d vector3d = this.getMotion();
//			double d0 = this.getPosX() + vector3d.x;
//			double d1 = this.getPosY() + vector3d.y;
//			double d2 = this.getPosZ() + vector3d.z;
//			ProjectileHelper.rotateTowardsMovement(this, 0.2F);
//			float f = this.getMotionFactor();
//
//			this.setMotion(vector3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(f));
//			this.setPosition(d0, d1, d2);
//		} else {
//			this.remove();
//		}
//	}

//	@Override
//	protected boolean func_230298_a_(Entity entityIn) {
//		return !entityIn.noClip;
//	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
