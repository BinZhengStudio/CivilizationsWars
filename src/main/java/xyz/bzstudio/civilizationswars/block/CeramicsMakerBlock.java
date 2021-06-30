package xyz.bzstudio.civilizationswars.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class CeramicsMakerBlock extends Block {
	public static final BooleanProperty PLACED_CLAY = BooleanProperty.create("placed_clay");
	public CeramicsMakerBlock() {
		super(Properties.create(Material.IRON).sound(SoundType.METAL).setRequiresTool());
		this.setDefaultState(this.getStateContainer().getBaseState().with(PLACED_CLAY, false));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			ItemStack heldItem = player.getHeldItem(handIn);
			// 检测玩家手持物品，右键的方块以及方块上是否已经放置黏土
			if (heldItem.getItem() == Items.CLAY_BALL && state.getBlock() == this && !state.get(PLACED_CLAY)) {
					worldIn.setBlockState(pos, this.getDefaultState().with(PLACED_CLAY, true));
					if (!player.isCreative()) { // 检测玩家是不是创造模式
						heldItem.shrink(1);
					}
					return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
		if (!worldIn.isRemote() && state.getBlock() == this && state.get(PLACED_CLAY)) {
			// 把玩家放置的黏土掉落出来
			worldIn.addEntity(new ItemEntity((World) worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.CLAY_BALL)));
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PLACED_CLAY);
		super.fillStateContainer(builder);
	}
}
