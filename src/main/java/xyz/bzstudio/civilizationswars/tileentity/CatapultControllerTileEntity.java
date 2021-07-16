package xyz.bzstudio.civilizationswars.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.bzstudio.civilizationswars.CivilizationsWars;
import xyz.bzstudio.civilizationswars.inventory.container.CatapultControllerContainer;

import javax.annotation.Nullable;

public class CatapultControllerTileEntity extends TileEntity implements INamedContainerProvider {
	private final Inventory inventory = new Inventory(1);

	public CatapultControllerTileEntity() {
		super(TileEntityTypeList.CATAPULT_CONTROLLER);
	}

	public IInventory getInventory() {
		return inventory;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("gui." + CivilizationsWars.MODID + ".catapult_controller");
	}

	@Nullable
	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new CatapultControllerContainer(p_createMenu_1_, p_createMenu_2_, this.pos);
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		if (nbt.contains("item")) {
			this.inventory.addItem(ItemStack.read(nbt.getCompound("item")));
		}
		super.read(state, nbt);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("item", this.inventory.getStackInSlot(0).copy().serializeNBT());
		return super.write(compound);
	}
}
