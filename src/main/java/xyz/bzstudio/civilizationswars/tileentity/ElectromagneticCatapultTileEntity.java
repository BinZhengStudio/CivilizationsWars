package xyz.bzstudio.civilizationswars.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ElectromagneticCatapultTileEntity extends TileEntity {
	public int speed = 0;
	public ElectromagneticCatapultTileEntity() {
		super(TileEntityTypeList.ELECTROMAGNETIC_CATAPULT);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		return super.getCapability(cap, side);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
		return super.getCapability(cap);
	}
}
