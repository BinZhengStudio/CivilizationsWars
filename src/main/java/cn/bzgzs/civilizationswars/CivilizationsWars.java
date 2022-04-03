package cn.bzgzs.civilizationswars;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import cn.bzgzs.civilizationswars.block.BlockList;
import cn.bzgzs.civilizationswars.client.gui.ScreenContainerManager;
import cn.bzgzs.civilizationswars.client.renderer.entity.EntityRendererManager;
import cn.bzgzs.civilizationswars.client.renderer.tileentity.TileEntityRendererManager;
import cn.bzgzs.civilizationswars.common.Config;
import cn.bzgzs.civilizationswars.entity.EntityTypeList;
import cn.bzgzs.civilizationswars.inventory.container.ContainerTypeList;
import cn.bzgzs.civilizationswars.item.ItemList;
import cn.bzgzs.civilizationswars.network.NetworkHandler;
import cn.bzgzs.civilizationswars.tileentity.TileEntityTypeList;

/**
 * @author BinZhengStudio
 */

@Mod(CivilizationsWars.MODID)
public class CivilizationsWars {
	public static final String MODID = "civilizationswars";

	public CivilizationsWars() {
		BlockList.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ContainerTypeList.CONTAINER_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
		EntityTypeList.ENTITY_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
		ItemList.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TileEntityTypeList.TILE_ENTITY_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(NetworkHandler::register);
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		event.enqueueWork(ScreenContainerManager::register);
		event.enqueueWork(TileEntityRendererManager::register);
		EntityRendererManager.register();
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
	}

	private void processIMC(final InterModProcessEvent event) {
	}
}
