package xyz.bzstudio.civilizationswars.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class AerialBlockEntity extends Entity implements IEntityAdditionalSpawnData {
	public double destinationX;
	public double destinationY;
	public double destinationZ;
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;
	private BlockState aerialBlock;

	public AerialBlockEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public AerialBlockEntity(World world, double x, double y, double z, double desX, double desY, double desZ, BlockState aerialBlockState) {
		this(EntityTypeList.AERIAL_BLOCK, world);
		this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
		this.destinationX = desX;
		this.destinationY = desY;
		this.destinationZ = desZ;
		this.aerialBlock = aerialBlockState;
	}

	@Override
	protected void registerData() {
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
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
		} else {
			this.remove();
		}
	}

	public BlockState getAerialBlock() {
		return this.aerialBlock;
	}

	private Vector3d updateAccel() {
		double distance = Math.sqrt(this.getDistanceSq(this.destinationX, this.destinationY, this.destinationZ));
		double xDistance = this.destinationX - this.getPosX();
		double yDistance = this.destinationY - this.getPosY();
		double zDistance = this.destinationZ - this.getPosZ();
		return new Vector3d(xDistance / distance, yDistance / distance, zDistance / distance).scale(0.09D * (1 - distance / AbstractTwoWayFoilEntity.RADIUS) + 0.01D);
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		if (compound.contains("AerialBlock")) {
			this.aerialBlock = NBTUtil.readBlockState(compound.getCompound("AerialBlock"));
		}
		if (compound.contains("destination", 9)) {
			ListNBT listnbt = compound.getList("destination", 6);
			if (listnbt.size() == 3) {
				this.destinationX = listnbt.getDouble(0);
				this.destinationY = listnbt.getDouble(1);
				this.destinationZ = listnbt.getDouble(2);
			}
		}
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.put("AerialBlock", NBTUtil.writeBlockState(this.aerialBlock));
		compound.put("destination", this.newDoubleNBTList(this.destinationX, this.destinationY, this.destinationZ));
	}

	@Override
	public void readSpawnData(PacketBuffer buffer) {
		this.destinationX = buffer.readDouble();
		this.destinationY = buffer.readDouble();
		this.destinationZ = buffer.readDouble();
		this.aerialBlock = Block.getStateById(buffer.readInt());
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeDouble(this.destinationX);
		buffer.writeDouble(this.destinationY);
		buffer.writeDouble(this.destinationZ);
		buffer.writeInt(Block.getStateId(this.aerialBlock));
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}