package xyz.bzstudio.civilizationswars.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.bzstudio.civilizationswars.entity.ThreebodyModelEntity;

import javax.annotation.Nullable;

public class ThreebodyModelBlock extends Block {
	public ThreebodyModelBlock() {
		super(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).noDrops());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (!worldIn.isRemote) {
			if (placer != null && placer.isCrouching()) {
				ThreebodyModelEntity entity = new ThreebodyModelEntity(worldIn, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 1);
				worldIn.addEntity(entity);
			} else {
				ThreebodyModelEntity entity = new ThreebodyModelEntity(worldIn, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0);
				worldIn.addEntity(entity);
			}
		}
	}
}