package xyz.bzstudio.civilizationswars.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import xyz.bzstudio.civilizationswars.tileentity.CeramicsMakerTileEntity;

public class CeramicsMakerContainer extends Container {
	protected CeramicsMakerContainer(int id, PlayerInventory playerInventory, PacketBuffer data) {
		super(ContainerTypeList.CERAMICS_MAKER, id);
		CeramicsMakerTileEntity tileEntity = (CeramicsMakerTileEntity) playerInventory.player.world.getTileEntity(data.readBlockPos());
		this.addSlot(new Slot(tileEntity.getInventory(), 0, 128, 128));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return playerIn.getDistanceSq((double) playerIn.getPosition().getX() + 0.5D, (double) playerIn.getPosition().getY() + 0.5D, (double) playerIn.getPosition().getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		return ItemStack.EMPTY; // TODO 以后再改
	}
}
