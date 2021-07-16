package xyz.bzstudio.civilizationswars.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.bzstudio.civilizationswars.tileentity.CatapultControllerTileEntity;
import xyz.bzstudio.civilizationswars.tileentity.ObjectCompressorTileEntity;

import javax.annotation.Nullable;

public class CatapultControllerBlock extends ContainerBlock {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	private int amount = 0;

	protected CatapultControllerBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).tickRandomly());
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			this.checkCatapults(worldIn, pos, player);
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof CatapultControllerTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (CatapultControllerTileEntity) tileEntity, (buf) -> buf.writeBlockPos(tileEntity.getPos()));
			}
			return ActionResultType.CONSUME;
		}
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) { // 将物品槽中的物品掉出
		if (!state.matchesBlock(newState.getBlock())) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof ObjectCompressorTileEntity) {
				InventoryHelper.dropInventoryItems(worldIn, pos, ((ObjectCompressorTileEntity) tileentity).getInventory());
				worldIn.updateComparatorOutputLevel(pos, this);
			}
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	private void checkCatapults(World world, BlockPos pos, PlayerEntity player) {
		BlockState controller = world.getBlockState(pos);
		this.amount = 0;
		if (controller.getBlock() == this) {
			for (BlockState catapult = world.getBlockState(pos.offset(controller.get(FACING), this.amount + 1));
				 catapult.getBlock() == BlockList.ELECTROMAGNETIC_CATAPULT && catapult.get(ElectromagneticCatapultBlock.FACING) == controller.get(FACING);
				 this.amount++);
		}
		// TODO 发包
	}

	public int getCatapultNum() {
		return amount;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new CatapultControllerTileEntity();
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getFace());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
