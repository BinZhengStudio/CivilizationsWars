package cn.bzgzs.civilizationswars.tileentity;

import cn.bzgzs.civilizationswars.CivilizationsWars;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import cn.bzgzs.civilizationswars.inventory.container.CeramicsMakerContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CeramicsMakerTileEntity extends TileEntity implements INamedContainerProvider, IInventory {
	private final NonNullList<ItemStack> inventoryContents = NonNullList.withSize(1, ItemStack.EMPTY);
	private int numPlayerUsing = 0;

	public CeramicsMakerTileEntity() {
		super(TileEntityTypeList.CERAMICS_MAKER);
	}

	@Nonnull
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("gui." + CivilizationsWars.MODID + ".ceramics_maker");
	}

	@Nullable
	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new CeramicsMakerContainer(p_createMenu_1_, p_createMenu_2_, this.pos);
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		if (nbt.contains("item")) {
			this.setInventorySlotContents(0, ItemStack.read(nbt.getCompound("item")));
		}
		super.read(state, nbt);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("item", this.getStackInSlot(0).copy().serializeNBT());
		return super.write(compound);
	}

	@Override
	public void openInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			if (this.numPlayerUsing < 0) {
				this.numPlayerUsing = 0;
			}

			++this.numPlayerUsing;
		}
	}

	@Override
	public void closeInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			--this.numPlayerUsing;
		}
	}

	public boolean hasPlayerUsing() {
		return this.numPlayerUsing > 0;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.inventoryContents) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index >= 0 && index < this.inventoryContents.size() ? this.inventoryContents.get(index) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.inventoryContents, index, count);
		if (!itemstack.isEmpty()) {
			this.markDirty();
		}

		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack itemstack = this.inventoryContents.get(index);
		if (itemstack.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.inventoryContents.set(index, ItemStack.EMPTY);
			return itemstack;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventoryContents.set(index, stack);
		if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		this.markDirty();
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}

	@Override
	public void clear() {
		this.inventoryContents.clear();
		this.markDirty();
	}
}
