package xyz.bzstudio.civilizationswars.tileentity;

import net.minecraft.inventory.Inventory;
import net.minecraft.tileentity.TileEntity;

public class CeramicsMakerTileEntity extends TileEntity {
	private Inventory inventory = new Inventory(1);

	public CeramicsMakerTileEntity() {
		super(TileEntityTypeList.CERAMICS_MAKER);
	}

	public Inventory getInventory() {
		return inventory;
	}
}
