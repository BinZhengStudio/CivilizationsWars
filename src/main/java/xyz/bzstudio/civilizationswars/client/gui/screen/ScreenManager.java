package xyz.bzstudio.civilizationswars.client.gui.screen;

import xyz.bzstudio.civilizationswars.inventory.container.ContainerTypeList;

public class ScreenManager {
	public static void register() {
		net.minecraft.client.gui.ScreenManager.registerFactory(ContainerTypeList.CERAMICS_MAKER, CeramicsMakerScreen::new);
	}
}
