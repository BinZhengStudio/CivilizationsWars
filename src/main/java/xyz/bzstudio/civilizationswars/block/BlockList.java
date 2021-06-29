package xyz.bzstudio.civilizationswars.block;

import net.minecraft.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.bzstudio.civilizationswars.CivilizationsWars;

public class BlockList {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CivilizationsWars.MODID);

	private static Block register(String name, Block block) {
		BLOCKS.register(name, () -> block);
		return block;
	}
}
