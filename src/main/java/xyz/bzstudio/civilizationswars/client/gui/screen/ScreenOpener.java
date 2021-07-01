package xyz.bzstudio.civilizationswars.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenOpener {
	public static void ceramicsMaker() {
		Minecraft.getInstance().displayGuiScreen(new CeramicsMakerScreen());
	}
}
