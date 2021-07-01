package xyz.bzstudio.civilizationswars;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.bzstudio.civilizationswars.block.BlockList;
import xyz.bzstudio.civilizationswars.inventory.container.ContainerTypeList;
import xyz.bzstudio.civilizationswars.item.ItemList;
import xyz.bzstudio.civilizationswars.tileentity.TileEntityTypeList;

/**
 * @author BinZhengStudio
 */

@Mod(CivilizationsWars.MODID)
public class CivilizationsWars {
	public static final String MODID = "civilizationswars";

	public CivilizationsWars() {
		BlockList.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ContainerTypeList.CONTAINER_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
		ItemList.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TileEntityTypeList.TILE_ENTITY_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
	}

	private void processIMC(final InterModProcessEvent event) {
	}
}
