package xyz.bzstudio.civilizationswars.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
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

	// Override if your IItemHandler does not implement IItemHandlerModifiable
	@Override
	public void putStack(@Nonnull ItemStack stack) {
		((IItemHandlerModifiable) this.getItemHandler()).setStackInSlot(0, stack);
	}

	@Override
	public int getSlotStackLimit() {
		return 64;
	}

	@Override
	public int getItemStackLimit(@Nonnull ItemStack stack) { // TODO 保留了原始代码，以后可能会该简单些
		ItemStack maxAdd = stack.copy();
		int maxInput = stack.getMaxStackSize();
		maxAdd.setCount(maxInput);

		IItemHandler handler = this.getItemHandler();
		ItemStack currentStack = handler.getStackInSlot(0);
		ItemStack remainder = handler.insertItem(0, maxAdd, true);

		int current = currentStack.getCount();
		int added = maxInput - remainder.getCount();
		return current + added;
	}

	@Override
	public boolean canTakeStack(PlayerEntity playerIn) {
		return false;
	}

	@Override
	@Nonnull
	public ItemStack decrStackSize(int amount) { // TODO 保留原始代码
		return this.getItemHandler().extractItem(0, amount, false);
	}
}
