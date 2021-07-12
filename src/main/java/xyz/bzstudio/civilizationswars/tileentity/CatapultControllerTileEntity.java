package xyz.bzstudio.civilizationswars.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.bzstudio.civilizationswars.CivilizationsWars;

import javax.annotation.Nullable;

public class CatapultControllerTileEntity extends TileEntity implements INamedContainerProvider {
	public CatapultControllerTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("gui." + CivilizationsWars.MODID + ".catapult_controller");
	}

	@Nullable
	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return null;
	}
}
