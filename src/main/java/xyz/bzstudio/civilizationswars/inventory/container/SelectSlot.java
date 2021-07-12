package xyz.bzstudio.civilizationswars.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public abstract class SelectSlot extends SlotItemHandler {
	private final ItemStack stack;

	public SelectSlot(ItemStack stack, int xPosition, int yPosition) {
		super(new ItemStackHandler(1), 0, xPosition, yPosition);
		this.stack = stack;
		this.putStack(stack);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack) {
		return false;
	}

	@Override
	@Nonnull
	public ItemStack getStack() {
		return this.stack;
	}

	public abstract void onClick(PlayerEntity player);

	@Override
	public int getSlotStackLimit() {
		return 64;
	}

	@Override
	public boolean canTakeStack(PlayerEntity playerIn) {
		return false;
	}
}
