package xyz.bzstudio.civilizationswars.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;

public class ObjectCompressorContainer extends Container {
	public ObjectCompressorContainer(int id, PlayerInventory inventory, BlockPos pos) {
		super(ContainerTypeList.OBJECT_COMPRESSOR, id);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return playerIn.getDistanceSq((double) playerIn.getPosition().getX() + 0.5D, (double) playerIn.getPosition().getY() + 0.5D, (double) playerIn.getPosition().getZ() + 0.5D) <= 64.0D;
	}
}
