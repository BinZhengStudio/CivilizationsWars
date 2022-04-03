package cn.bzgzs.civilizationswars.client.gui;

import cn.bzgzs.civilizationswars.client.gui.screen.inventory.CeramicsMakerScreen;
import cn.bzgzs.civilizationswars.client.gui.screen.inventory.ObjectCompressorScreen;
import net.minecraft.client.gui.ScreenManager;
import cn.bzgzs.civilizationswars.client.gui.screen.inventory.CatapultControllerScreen;
import cn.bzgzs.civilizationswars.inventory.container.ContainerTypeList;

public class ScreenContainerManager {
	public static void register() {
		ScreenManager.registerFactory(ContainerTypeList.CATAPULT_CONTROLLER, CatapultControllerScreen::new);
		ScreenManager.registerFactory(ContainerTypeList.CERAMICS_MAKER, CeramicsMakerScreen::new);
		ScreenManager.registerFactory(ContainerTypeList.OBJECT_COMPRESSOR, ObjectCompressorScreen::new);
	}
}
