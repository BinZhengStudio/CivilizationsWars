package xyz.bzstudio.civilizationswars.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.bzstudio.civilizationswars.entity.LightParticleEntity;
import xyz.bzstudio.civilizationswars.tileentity.CatapultControllerTileEntity;

import javax.annotation.Nullable;

public class CatapultControllerBlock extends ContainerBlock {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	private static final int MAX_STACK_SIZE = 25;
	private static ServerWorld world;

	protected CatapultControllerBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).tickRandomly());
	}

	public static void fireLightParticle(BlockPos pos, int[] offset) {
		((CatapultControllerTileEntity) world.getTileEntity(pos)).getInventory().setInventorySlotContents(0, ItemStack.EMPTY);

		int stackSize = getStackSize(world, pos);
		int x_offset = offset[0];
		int y_offset = offset[1];
		int z_offset = offset[2];
		Direction facing = world.getBlockState(pos).get(FACING);
		BlockPos spawnPos = pos.offset(facing, MathHelper.ceil(stackSize + LightParticleEntity.MAX_EXPLOSION_POWER * stackSize / MAX_STACK_SIZE) + 1);

		double accelX = 0.0D;
		double accelY = 0.0D;
		double accelZ = 0.0D;

		if (facing.getAxis() == Direction.Axis.X) {
			accelY = Math.tan(y_offset * (Math.PI / 180.0D));
			accelZ = Math.tan(z_offset * (Math.PI / 180.0D));
		} else if (facing.getAxis() == Direction.Axis.Y) {
			accelX = Math.tan(x_offset * (Math.PI / 180.0D));
			accelZ = Math.tan(z_offset * (Math.PI / 180.0D));
		} else if (facing.getAxis() == Direction.Axis.Z) {
			accelX = Math.tan(x_offset * (Math.PI / 180.0D));
			accelY = Math.tan(y_offset * (Math.PI / 180.0D));
		}

		LightParticleEntity entity = new LightParticleEntity(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), facing.getXOffset() + accelX, facing.getYOffset() + accelY, facing.getZOffset() + accelZ, LightParticleEntity.MAX_EXPLOSION_POWER * stackSize / MAX_STACK_SIZE, world);
		world.addEntity(entity);
		System.out.println(x_offset);
		System.out.println(y_offset);
		System.out.println(z_offset);
		System.out.println(accelX);
		System.out.println(accelY);
		System.out.println(accelZ);
//		for (int i = 0; i < 10; i++) {
//			if (i == 0) {
//				world.createExplosion((Entity) null, pos.getX(), pos.getY() + size + 7, pos.getZ(), 3.0F, Explosion.Mode.NONE);
//			} else {
//				world.createExplosion((Entity) null, pos.getX(), pos.getY() + size + 5 * (i + 1), pos.getZ(), 3.0F, Explosion.Mode.NONE);
//			}
//		}
	}

	private static int getStackSize(World world, BlockPos pos) {
		BlockState controller = world.getBlockState(pos);
		int stackSize = 0;
		if (controller.getBlock() == BlockList.CATAPULT_CONTROLLER) {
			for (BlockState catapult = world.getBlockState(pos.offset(controller.get(FACING), stackSize + 1));
				 catapult.getBlock() == BlockList.ELECTROMAGNETIC_CATAPULT && catapult.get(ElectromagneticCatapultBlock.FACING) == controller.get(FACING);
				 catapult = world.getBlockState(pos.offset(controller.get(FACING), stackSize + 1)))
				stackSize++;
			if (stackSize > MAX_STACK_SIZE) stackSize = MAX_STACK_SIZE;
		}
		return stackSize;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			world = (ServerWorld) worldIn;
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
			if (tileentity instanceof CatapultControllerTileEntity) {
				InventoryHelper.dropInventoryItems(worldIn, pos, ((CatapultControllerTileEntity) tileentity).getInventory());
				worldIn.updateComparatorOutputLevel(pos, this);
			}
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
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
