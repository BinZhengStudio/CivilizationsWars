package xyz.bzstudio.civilizationswars.item;

import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.bzstudio.civilizationswars.CivilizationsWars;

public class ItemList {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CivilizationsWars.MODID);

	private static Item register(String name, Item item) {
		ITEMS.register(name, () -> item);
		return item;
	}
}
