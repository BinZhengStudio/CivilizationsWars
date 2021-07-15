package xyz.bzstudio.civilizationswars.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class LightParticleEntity extends DamagingProjectileEntity {
	public LightParticleEntity(EntityType<? extends DamagingProjectileEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public LightParticleEntity(double x, double y, double z, double accelX, double accelY, double accelZ, World world) {
		this(EntityTypeList.LIGHT_PARTICLE, world);
		this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
		this.recenterBoundingBox();
		this.accelerationX = accelX;
		this.accelerationY = accelY;
		this.accelerationZ = accelZ;
	}

	public LightParticleEntity(LivingEntity shooter, double accelX, double accelY, double accelZ, World world) {
		this(shooter.getPosX(), shooter.getPosY(), shooter.getPosZ(), accelX, accelY, accelZ, world);
		this.setShooter(shooter);
		this.setRotation(shooter.rotationYaw, shooter.rotationPitch);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (!this.world.isRemote) {
			this.world.createExplosion((Entity) null, this.getPosX(), this.getPosY(), this.getPosZ(), 4.0F, false, Explosion.Mode.DESTROY);
			this.remove();
		}
	}
}
