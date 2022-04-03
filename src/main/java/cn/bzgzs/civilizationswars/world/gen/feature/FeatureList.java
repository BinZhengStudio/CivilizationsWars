package cn.bzgzs.civilizationswars.world.gen.feature;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import cn.bzgzs.civilizationswars.block.BlockList;

public class FeatureList {
	public static final ConfiguredFeature<?, ?> ORE_COPPER = register("ore_copper", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockList.COPPER_ORE.getDefaultState(), 9)).range(64).square().count(20));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
	}
}
