package cn.bzgzs.civilizationswars.inventory.container;

import cn.bzgzs.civilizationswars.tileentity.CatapultControllerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import cn.bzgzs.civilizationswars.item.ItemList;

public class CatapultControllerContainer extends Container {
	private final BlockPos pos;
	public CatapultControllerContainer(int id, PlayerInventory playerInventory, BlockPos pos) {
		super(ContainerTypeList.CATAPULT_CONTROLLER, id);
		this.pos = pos;
		TileEntity tileEntity = playerInventory.player.world.getTileEntity(pos);

		if (tileEntity instanceof CatapultControllerTileEntity) {
			this.addSlot(new Slot(((CatapultControllerTileEntity) tileEntity).getInventory(), 0, 80, 34) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return stack.getItem() == (ItemList.LIGHT_PARTICLE);
				}

				@Override
				public int getSlotStackLimit() {
					return 1;
				}
			});
		}

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	public BlockPos getPos() {
		return this.pos;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return playerIn.getDistanceSq((double) playerIn.getPosition().getX() + 0.5D, (double) playerIn.getPosition().getY() + 0.5D, (double) playerIn.getPosition().getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < 1) {
				if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
}
