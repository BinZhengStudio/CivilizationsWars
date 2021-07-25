package xyz.bzstudio.civilizationswars.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThreebodyModelEntity extends Entity implements IEntityAdditionalSpawnData {
	public int type;
	public double rawX;
	public double rawY;
	public double rawZ;
	public double leftX;
	public double leftY;
	public double leftZ;
	public double rightX;
	public double rightY;
	public double rightZ;

	public ThreebodyModelEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public ThreebodyModelEntity(World worldIn, double x, double y, double z, int type) {
		this(EntityTypeList.THREEBODY_MODEL, worldIn);
		this.setLocationAndAngles(x, y, z, rotationYaw, this.rotationPitch);
		this.type = type;
		this.setLeftAndRight();
		this.setInvulnerable(true);
		this.setNoGravity(true);
	}

	@Override
	public void tick() {
		if (world.isAreaLoaded(this.getPosition(), 0)) {
			Vector3d vector3d = this.getMotion();
			if (vector3d.equals(Vector3d.ZERO)) {
				this.setPosition(this.leftX, this.leftY, this.leftZ);
			} else {
				double x = this.getPosX() + vector3d.x;
				double z = this.getPosZ() + vector3d.z;
				this.setPosition(x, this.calculatePosY(), z);
			}
			this.setMotion(this.updateAccel());
		}
	}

	private void setLeftAndRight() {
		this.rawX = this.getPosX();
		this.rawY = this.getPosY();
		this.rawZ = this.getPosZ();
		this.leftY = this.rawY + 49 - Math.cos(0.5D) * 49;
		this.rightY = this.rawY + 49 - Math.cos(0.5D) * 49;
		if (this.type == 0) {
			this.leftX = this.rawX + Math.sin(0.5D) * 49;
			this.leftZ = this.rawZ;
			this.rightX = this.rawX - Math.sin(0.5D) * 49;
			this.rightZ = this.rawZ;
		} else {
			this.leftX = this.rawX;
			this.leftZ = this.rawZ + Math.sin(0.5D) * 49;
			this.rightX = this.rawX;
			this.rightZ = this.rawZ - Math.sin(0.5D) * 49;
		}
	}

	private Vector3d updateAccel() {
		double distance = Math.sqrt(this.getDistanceSq(this.rawX, this.rawY, this.rawZ));
		double xDistance = this.rawX - this.getPosX();
		double zDistance = this.rawZ - this.getPosZ();
		Vector3d vector3d = this.getMotion();
		return new Vector3d(vector3d.x + xDistance / distance * 0.1D, 0, vector3d.z + zDistance / distance * 0.1D);
	}

	private double calculatePosY() {
		if (this.type == 0) {
			double xDistance = this.rawX - this.getPosX();
			return 49.0D - Math.sqrt(2401.0D - (xDistance * xDistance)) + this.rawY; // 49 * 49 = 2401
		} else {
			double zDistance = this.rawZ - this.getPosZ();
			return 49.0D - Math.sqrt(2401.0D - (zDistance * zDistance)) + this.rawY;
		}
	}

	@Override
	protected void registerData() {
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		if (compound.contains("raw", 9)) {
			ListNBT listnbt = compound.getList("raw", 6);
			if (listnbt.size() == 3) {
				this.rawX = listnbt.getDouble(0);
				this.rawY = listnbt.getDouble(1);
				this.rawZ = listnbt.getDouble(2);
			}
		}
		if (compound.contains("left", 9)) {
			ListNBT listnbt = compound.getList("left", 6);
			if (listnbt.size() == 3) {
				this.leftX = listnbt.getDouble(0);
				this.leftY = listnbt.getDouble(1);
				this.leftZ = listnbt.getDouble(2);
			}
		}
		if (compound.contains("right", 9)) {
			ListNBT listnbt = compound.getList("right", 6);
			if (listnbt.size() == 3) {
				this.rightX = listnbt.getDouble(0);
				this.rightY = listnbt.getDouble(1);
				this.rightZ = listnbt.getDouble(2);
			}
		}
		if (compound.contains("type")) {
			this.type = compound.getInt("type");
		}
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.put("raw", this.newDoubleNBTList(this.rawX, this.rawY, this.rawZ));
		compound.put("left", this.newDoubleNBTList(this.leftX, this.leftY, this.leftZ));
		compound.put("right", this.newDoubleNBTList(this.rightX, this.rightY, this.rightZ));
		compound.putInt("type", this.type);
	}

	@Override
	public void readSpawnData(PacketBuffer buffer) {
		this.rawX = buffer.readDouble();
		this.rawY = buffer.readDouble();
		this.rawZ = buffer.readDouble();
		this.leftX = buffer.readDouble();
		this.leftY = buffer.readDouble();
		this.leftZ = buffer.readDouble();
		this.rightX = buffer.readDouble();
		this.rightY = buffer.readDouble();
		this.rightZ = buffer.readDouble();
		this.type = buffer.readInt();
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeDouble(this.rawX);
		buffer.writeDouble(this.rawY);
		buffer.writeDouble(this.rawZ);
		buffer.writeDouble(this.leftX);
		buffer.writeDouble(this.leftY);
		buffer.writeDouble(this.leftZ);
		buffer.writeDouble(this.rightX);
		buffer.writeDouble(this.rightY);
		buffer.writeDouble(this.rightZ);
		buffer.writeInt(this.type);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
