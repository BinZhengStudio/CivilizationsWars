package xyz.bzstudio.civilizationswars.common;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
	public static class Common {
		public final ForgeConfigSpec.IntValue twoWayFoilRadius;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Common configure").push("common");

			twoWayFoilRadius = builder
					.comment("The radius of Two-way Foil")
					.translation("civilizationswars.config.twoWayFoilRange")
					.worldRestart()
					.defineInRange("twoWayFoilRadius", 20, 10, Integer.MAX_VALUE);
			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
