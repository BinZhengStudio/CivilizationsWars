package xyz.bzstudio.civilizationswars.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.bzstudio.civilizationswars.CivilizationsWars;

public class BlockList {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CivilizationsWars.MODID);
	public static final Block COPPER_ORE = register("copper_ore", new OreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0F, 3.0F)));
	public static final Block COPPER_BLOCK = register("copper_block", new OreBlock(AbstractBlock.Properties.create(Material.IRON).setAir().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)));
	public static final Block CERAMICS_MAKER = register("ceramics_maker", new CeramicsMakerBlock());

	private static Block register(String name, Block block) {
		BLOCKS.register(name, () -> block);
		return block;
	}
}
