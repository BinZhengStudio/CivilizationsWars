package xyz.bzstudio.civilizationswars.entity;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import xyz.bzstudio.civilizationswars.util.DamageSourceList;

import java.util.List;

public class TwoWayFoilEntity extends AbstractTwoWayFoilEntity {
	private int life = 200;
	private boolean impacted = false;

	protected TwoWayFoilEntity(EntityType<? extends AbstractTwoWayFoilEntity> entityType, World world) {
		super(entityType, world);
	}

	public TwoWayFoilEntity(double x, double y, double z, double accelX, double accelY, double accelZ, World world) {
		super(EntityTypeList.TWO_WAY_FOIL, x, y, z, accelX, accelY, accelZ, world);
	}

	public TwoWayFoilEntity(Entity shooter, double accelX, double accelY, double accelZ, World world) {
		super(EntityTypeList.TWO_WAY_FOIL, shooter, accelX, accelY, accelZ, world);
	}

	@Override
	public void tick() {
		if (this.impacted) {
			if (!this.world.isRemote) {
				this.destroyBlock();
				this.killEntity();
			}
			if (this.getEntitiesInRadius().isEmpty() || this.life <= 0) {
				for (Entity entity : this.getEntitiesInRadius()) {
					entity.setNoGravity(false);
				}
				this.remove();
				this.entityDropItem(new ItemStack(Items.PAINTING));
			}
			this.life--;
		} else {
			super.tick();
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		this.impacted = true;
	}

	public boolean isImpacted() {
		return this.impacted;
	}

	private void destroyBlock() {
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
						BlockPos pos = new BlockPos(x, y, z);
						BlockState state = this.world.getBlockState(pos);
						if (!(state.getBlockHardness(this.world, pos) < 0 || state.getMaterial() == Material.AIR || state.getBlock() instanceof FlowingFluidBlock)) {
							this.world.setBlockState(pos, Blocks.AIR.getDefaultState());
							this.world.setTileEntity(pos, null);
							AerialBlockEntity entity = new AerialBlockEntity(this.world, x + 0.5D, y, z + 0.5D, this.getPosX(), this.getPosY(), this.getPosZ(), state);
							this.world.addEntity(entity);
						}
					}
				}
			}
		}
	}

	private void killEntity() {
		for (Entity entity : this.getEntitiesInRadius()) {
			double distance = this.getDistance(entity);
			if (entity instanceof AerialBlockEntity) {
				if (distance < 1.0D) {
					entity.remove();
				}
			} else {
				if (!entity.isInvulnerableTo(DamageSourceList.TWO_WAY_FOIL)) {
					if (distance < 2.0D) {
						if (entity instanceof LivingEntity) {
							LivingEntity livingEntity = (LivingEntity) entity;
							livingEntity.attackEntityFrom(DamageSourceList.TWO_WAY_FOIL, livingEntity.getMaxHealth());
						} else {
							entity.remove();
						}
					} else if (distance < radius) {
						Vector3d vector3d = new Vector3d((this.getPosX() - entity.getPosX()) / distance, (this.getPosY() - entity.getPosY()) / distance, (this.getPosZ() - entity.getPosZ()) / distance).scale(0.09D * (1 - distance / radius) + 0.01D);
						if (entity instanceof PlayerEntity) {
							if (((PlayerEntity) entity).isCreative() || entity.isSpectator()) {
								entity.setNoGravity(false);
							} else {
								entity.setNoGravity(true);
								entity.setMotion(entity.getMotion().add(vector3d));
								entity.setPosition(entity.getPosX() + vector3d.x, entity.getPosY() + vector3d.y, entity.getPosZ() + vector3d.z);
							}
						} else {
							entity.setNoGravity(true);
							entity.setMotion(entity.getMotion().add(vector3d));
							entity.setPosition(entity.getPosX() + vector3d.x, entity.getPosY() + vector3d.y, entity.getPosZ() + vector3d.z);
						}
					}
				}
			}
		}
	}

	private List<Entity> getEntitiesInRadius() {
		AxisAlignedBB axisAlignedBB = new AxisAlignedBB(this.getPosX() - radius, this.getPosY() - radius, this.getPosZ() - radius, this.getPosX() + radius, this.getPosY() + radius, this.getPosZ() + radius);
		List<Entity> list = Lists.newArrayList();
		for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, axisAlignedBB)) {
			if (this.getDistance(entity) < radius && !(entity instanceof AbstractTwoWayFoilEntity)) {
				list.add(entity);
			}
		}
		return list;
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("life")) {
			this.life = compound.getInt("life");
		}
		if (compound.contains("impacted")) {
			this.impacted = compound.getBoolean("impacted");
		}
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("life", this.life);
		compound.putBoolean("impacted", this.impacted);
	}

	@Override
	public void readSpawnData(PacketBuffer buffer) {
		super.readSpawnData(buffer);
		this.life = buffer.readInt();
		this.impacted = buffer.readBoolean();
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		super.writeSpawnData(buffer);
		buffer.writeInt(this.life);
		buffer.writeBoolean(this.impacted);
	}
}
