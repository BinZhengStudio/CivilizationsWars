package xyz.bzstudio.civilizationswars.client.gui;

import net.minecraft.client.gui.ScreenManager;
import xyz.bzstudio.civilizationswars.client.gui.screen.inventory.CatapultControllerScreen;
import xyz.bzstudio.civilizationswars.client.gui.screen.inventory.CeramicsMakerScreen;
import xyz.bzstudio.civilizationswars.client.gui.screen.inventory.ObjectCompressorScreen;
import xyz.bzstudio.civilizationswars.inventory.container.ContainerTypeList;

public class ScreenContainerManager {
	public static void register() {
		ScreenManager.registerFactory(ContainerTypeList.CATAPULT_CONTROLLER, CatapultControllerScreen::new);
		ScreenManager.registerFactory(ContainerTypeList.CERAMICS_MAKER, CeramicsMakerScreen::new);
		ScreenManager.registerFactory(ContainerTypeList.OBJECT_COMPRESSOR, ObjectCompressorScreen::new);
	}
}
