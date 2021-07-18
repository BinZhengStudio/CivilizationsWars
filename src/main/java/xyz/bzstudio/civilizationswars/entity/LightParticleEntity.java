package xyz.bzstudio.civilizationswars.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.bzstudio.civilizationswars.util.DamageSourceList;

import java.util.List;

public class LightParticleEntity extends DamagingProjectileEntity {
	public static final float MAX_EXPLOSION_POWER = 5.0F;
	private float explosionPower;

	public LightParticleEntity(EntityType<? extends DamagingProjectileEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public LightParticleEntity(double x, double y, double z, double accelX, double accelY, double accelZ, float explosionPower, World world) {
		this(EntityTypeList.LIGHT_PARTICLE, world);
		this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
		this.recenterBoundingBox();
		this.accelerationX = accelX;
		this.accelerationY = accelY;
		this.accelerationZ = accelZ;
		this.explosionPower = explosionPower;
	}

	public LightParticleEntity(LivingEntity shooter, double accelX, double accelY, double accelZ, float explosionPower, World world) {
		this(shooter.getPosX(), shooter.getPosY(), shooter.getPosZ(), accelX, accelY, accelZ, explosionPower, world);
		this.setShooter(shooter);
		this.setRotation(shooter.rotationYaw, shooter.rotationPitch);
	}

	public LightParticleEntity(Entity shooter, BlockPos spawnPos, double accelX, double accelY, double accelZ, float explosionPower, World world) {
		this(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), accelX, accelY, accelZ, explosionPower, world);
		this.setShooter(shooter);
	}

	@Override
	public void tick() {
		super.tick();
		if (!world.isRemote) {
			world.createExplosion((Entity) null, this.getPosX(), this.getPosY(), this.getPosZ(), this.explosionPower, Explosion.Mode.DESTROY);
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (!this.world.isRemote) {
			float radius = 20 * this.explosionPower / MAX_EXPLOSION_POWER;
			this.destroyBlock(radius);
			this.killEntity(radius);
			this.remove();
		}
	}

	private void destroyBlock(float radius) {
		int minX = MathHelper.floor(this.getPosX() - radius);
		int maxX = MathHelper.ceil(this.getPosX() + radius);
		int minY = MathHelper.floor(this.getPosY() - radius);
		int maxY = MathHelper.ceil(this.getPosY() + radius);
		int minZ = MathHelper.floor(this.getPosZ() - radius);
		int maxZ = MathHelper.ceil(this.getPosZ() + radius);

		for (int x = minX; x < maxX; ++x) {
			for (int y = minY; y < maxY; ++y) {
				for (int z = minZ; z < maxZ; ++z) {
					if (this.getDistanceSq(x, y, z) < radius * radius) {
						if (!(this.world.getBlockState(new BlockPos(x, y, z)).getBlock() == (Blocks.BEDROCK))) {
							this.world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
						}
					}
				}
			}
		}
	}

	private void killEntity(float radius) {
		float killRadius = radius * 1.5F;
		AxisAlignedBB axisAlignedBB = new AxisAlignedBB(this.getPosX() - killRadius, this.getPosY() - killRadius, this.getPosZ() - killRadius, this.getPosX() + killRadius, this.getPosY() + killRadius, this.getPosZ() + killRadius);
		List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, axisAlignedBB);
		for (Entity entity : entities) {
			if (this.getDistanceSq(entity) < killRadius * killRadius) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					livingEntity.attackEntityFrom(DamageSourceList.LIGHT_PARTICLE, livingEntity.getMaxHealth());
				} else {
					entity.remove();
				}
			}
		}

		List<ItemEntity> itemEntities = this.world.getEntitiesWithinAABB(ItemEntity.class, axisAlignedBB);
		for (ItemEntity itemEntity : itemEntities) {
			if (this.getDistanceSq(itemEntity) < killRadius * killRadius) {
				itemEntity.remove();
			}
		}
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
