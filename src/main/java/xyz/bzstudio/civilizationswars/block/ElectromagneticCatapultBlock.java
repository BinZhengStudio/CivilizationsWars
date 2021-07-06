package xyz.bzstudio.civilizationswars.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xyz.bzstudio.civilizationswars.tileentity.ElectromagneticCatapultTileEntity;

import javax.annotation.Nullable;

public class ElectromagneticCatapultBlock extends Block {
	public ElectromagneticCatapultBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ElectromagneticCatapultTileEntity();
	}
}
