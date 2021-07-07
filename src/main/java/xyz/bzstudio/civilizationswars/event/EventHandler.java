package xyz.bzstudio.civilizationswars.event;

import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.bzstudio.civilizationswars.world.gen.feature.FeatureList;

@Mod.EventBusSubscriber
public class EventHandler {
	@SubscribeEvent
	public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
		event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureList.ORE_COPPER);
	}
}
