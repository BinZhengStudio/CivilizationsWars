package xyz.bzstudio.civilizationswars.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.bzstudio.civilizationswars.CivilizationsWars;
import xyz.bzstudio.civilizationswars.block.BlockList;

public class ItemList {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CivilizationsWars.MODID);
	public static final Item COPPER_INGOT = register("copper_ingot", new Item(new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item COPPER_NUGGET = register("copper_nugget", new Item(new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item CERAMICS_CYLINDER_MODEL = register("ceramics_cylinder_model", new Item(new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item CERAMICS_CYLINDER = register("ceramics_cylinder", new Item(new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item COPPER_WIRE = register("copper_wire", new Item(new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item COIL = register("coil", new Item(new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item LIGHT_PARTICLE = register("light_particle", new Item(new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item ELECTROMAGNETIC_EJECTION_GUN = register("electromagnetic_ejection_gun", new ElectromagneticEjectionGunItem());

	public static final Item COPPER_ORE = register("copper_ore", new BlockItem(BlockList.COPPER_ORE, new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item COPPER_BLOCK = register("copper_block", new BlockItem(BlockList.COPPER_BLOCK, new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item CERAMICS_MAKER = register("ceramics_maker", new BlockItem(BlockList.CERAMICS_MAKER, new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item ELECTROMAGNETIC_CATAPULT = register("electromagnetic_catapult", new BlockItem(BlockList.ELECTROMAGNETIC_CATAPULT, new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));
	public static final Item OBJECT_COMPRESSOR_BLOCK = register("object_compressor_block", new BlockItem(BlockList.OBJECT_COMPRESSOR_BLOCK, new Item.Properties().group(ItemGroups.TAB_CIVILIZATIONS_WARS)));

	private static Item register(String name, Item item) {
		ITEMS.register(name, () -> item);
		return item;
	}
}
