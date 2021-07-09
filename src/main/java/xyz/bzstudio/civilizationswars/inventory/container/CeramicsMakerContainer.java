package xyz.bzstudio.civilizationswars.inventory.container;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import xyz.bzstudio.civilizationswars.block.BlockList;
import xyz.bzstudio.civilizationswars.block.CeramicsMakerBlock;
import xyz.bzstudio.civilizationswars.item.ItemList;
import xyz.bzstudio.civilizationswars.tileentity.CeramicsMakerTileEntity;

public class CeramicsMakerContainer extends Container {
	public CeramicsMakerContainer(int id, PlayerInventory playerInventory, BlockPos pos) {
		super(ContainerTypeList.CERAMICS_MAKER, id);
		CeramicsMakerTileEntity tileEntity = (CeramicsMakerTileEntity) playerInventory.player.world.getTileEntity(pos);

		this.addSlot(new SelectSlot(new ItemStack(ItemList.CERAMICS_CYLINDER_MODEL), 12, 20) {
			@Override
			public void onClick(PlayerEntity player) {
				BlockState state = player.world.getBlockState(pos);
				if (state.getBlock() == BlockList.CERAMICS_MAKER) {
					if (state.get(CeramicsMakerBlock.PLACED_CLAY)) {
						tileEntity.getInventory().setInventorySlotContents(0, this.getStack());
					}
				}
			}
		});

		this.addSlot(new Slot(tileEntity.getInventory(), 0, 145, 34) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			@Override
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
				playerInventory.player.world.setBlockState(pos, BlockList.CERAMICS_MAKER.getDefaultState());
				return super.onTake(thePlayer, stack);
			}
		});

		// 添加玩家背包物品槽
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return playerIn.getDistanceSq((double) playerIn.getPosition().getX() + 0.5D, (double) playerIn.getPosition().getY() + 0.5D, (double) playerIn.getPosition().getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			if (index < 2) {
				if (!this.mergeItemStack(itemStack1, 2, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemStack1, 2, this.inventorySlots.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
				slot.onTake(playerIn, itemStack);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemStack;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
		if (slotId == 0 && clickTypeIn == ClickType.PICKUP) {
			Slot slot = this.inventorySlots.get(slotId);
			if (slot instanceof SelectSlot) {
				((SelectSlot) slot).onClick(player);
			}
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
}
