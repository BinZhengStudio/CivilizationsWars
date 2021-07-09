package xyz.bzstudio.civilizationswars.client.gui.screen;

import net.minecraft.client.gui.ScreenManager;
import xyz.bzstudio.civilizationswars.inventory.container.ContainerTypeList;

public class ScreenContainerManager {
	public static void register() {
		ScreenManager.registerFactory(ContainerTypeList.CERAMICS_MAKER, CeramicsMakerScreen::new);
		ScreenManager.registerFactory(ContainerTypeList.OBJECT_COMPRESSOR, ObjectCompressorScreen::new);
	}
}
