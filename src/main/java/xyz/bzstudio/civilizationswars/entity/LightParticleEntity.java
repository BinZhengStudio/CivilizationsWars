package xyz.bzstudio.civilizationswars.entity;

import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.bzstudio.civilizationswars.util.DamageSourceList;

import java.util.List;

public class LightParticleEntity extends DamagingProjectileEntity {
	public static final float MAX_EXPLOSION_POWER = 5.0F;
	private static final int TOTAL_ATTACK_TIME = 160;
	private static final int WARNING_TIME = 60;
	private float explosionPower = 0;
	private int attackTime = 0;

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

	public LightParticleEntity(Entity shooter, BlockPos spawnPos, double accelX, double accelY, double accelZ, float explosionPower, World world) {
		this(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), accelX, accelY, accelZ, explosionPower, world);
		this.setShooter(shooter);
	}

	@Override
	public void tick() {
		super.tick();
		if (!world.isRemote) {
			this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), this.explosionPower, Explosion.Mode.DESTROY);

			if (this.accelerationX == 0 && this.accelerationY == 1 && this.accelerationZ == 0) {
				if (this.explosionPower == MAX_EXPLOSION_POWER && this.world.canSeeSky(this.getPosition())) {
					long time = this.world.getDayTime();
					ServerWorld serverWorld = (ServerWorld) this.world;
					if (time > 5555 && time < 6400) {
						this.attackTime++;

						if (this.attackTime == WARNING_TIME) {
							serverWorld.getServer().getCommandManager().handleCommand(this.getCommandSource(), "/title @a title [{\"text\":\"" + new TranslationTextComponent("cmd.civilizationswars.light_particle.text1").getString() + "\",\"color\":\"red\",\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false}]");
							if (this.getShooter() instanceof ServerPlayerEntity) {
								serverWorld.getServer().getCommandManager().handleCommand(this.getCommandSource(), "/title @a subtitle [{\"text\":\"" + new TranslationTextComponent("cmd.civilizationswars.light_particle.text2").getString() + this.getShooter().getDisplayName().getString() + new TranslationTextComponent("cmd.civilizationswars.light_particle.text3").getString() + "\",\"color\":\"light_purple\",\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false}]");
							}
						}

						if (this.attackTime >= TOTAL_ATTACK_TIME) {
							serverWorld.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, ((ServerWorld) this.world).getServer());
							serverWorld.setDayTime(18000);
							serverWorld.getServer().getCommandManager().handleCommand(this.getCommandSource(), "/title @a title [{\"text\":\"" + new TranslationTextComponent("cmd.civilizationswars.light_particle.text4").getString() + "\",\"color\":\"gold\",\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false}]");
							serverWorld.getServer().getCommandManager().handleCommand(this.getCommandSource(), "/title @a subtitle [{\"text\":\"" + new TranslationTextComponent("cmd.civilizationswars.light_particle.text5").getString() + "\",\"color\":\"yellow\",\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false}]");
							this.remove();
						}
					}
				}
			}
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
		} else {
			this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
			this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 8.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
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
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("explodePower")) {
			this.explosionPower = compound.getFloat("explodePower");
		}
		if (compound.contains("attackTime")) {
			this.attackTime = compound.getInt("attackTime");
		}
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putFloat("explodePower", this.explosionPower);
		compound.putInt("attackTime", this.attackTime);
	}

	@Override
	public CommandSource getCommandSource() {
		return new CommandSource(ICommandSource.DUMMY, this.world instanceof ServerWorld ? Vector3d.copy(((ServerWorld) this.world).getSpawnPoint()) : Vector3d.ZERO, Vector2f.ZERO, this.world instanceof ServerWorld ? (ServerWorld) this.world : null, 3, "Server", new StringTextComponent("Server"), this.world.getServer(), (Entity) null);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
