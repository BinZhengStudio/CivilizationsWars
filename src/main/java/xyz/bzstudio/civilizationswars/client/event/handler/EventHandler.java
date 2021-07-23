package xyz.bzstudio.civilizationswars.client.event.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.bzstudio.civilizationswars.client.gui.ElectricGunGui;
import xyz.bzstudio.civilizationswars.item.ItemList;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class EventHandler {
	@SubscribeEvent
	public static void renderGameOverlayEvent(final RenderGameOverlayEvent event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && Minecraft.getInstance().player != null) {
			if (Minecraft.getInstance().player.getHeldItem(Hand.MAIN_HAND).getItem() == ItemList.ELECTROMAGNETIC_EJECTION_GUN) {
				new ElectricGunGui(event.getMatrixStack(), Minecraft.getInstance().player.getHeldItem(Hand.MAIN_HAND), Minecraft.getInstance().player).render();
			}
		}
	}
}
