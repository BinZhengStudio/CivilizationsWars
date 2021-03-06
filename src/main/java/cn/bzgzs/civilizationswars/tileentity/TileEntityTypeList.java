package cn.bzgzs.civilizationswars.tileentity;

import cn.bzgzs.civilizationswars.CivilizationsWars;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import cn.bzgzs.civilizationswars.block.BlockList;

import java.util.function.Supplier;

public class TileEntityTypeList {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CivilizationsWars.MODID);
	public static final TileEntityType<CeramicsMakerTileEntity> CERAMICS_MAKER = register("ceramics_maker", CeramicsMakerTileEntity::new, BlockList.CERAMICS_MAKER);
	public static final TileEntityType<CatapultControllerTileEntity> CATAPULT_CONTROLLER = register("catapult_controller",CatapultControllerTileEntity::new,BlockList.CATAPULT_CONTROLLER);
	public static final TileEntityType<ObjectCompressorTileEntity> OBJECT_COMPRESSOR = register("object_compressor",ObjectCompressorTileEntity::new,BlockList.OBJECT_COMPRESSOR);

	private static <T extends TileEntity> TileEntityType<T> register(String name, Supplier<T> factoryIn, Block... block) {
		TileEntityType<T> tileEntityType = TileEntityType.Builder.create(factoryIn, block).build(null);
		TILE_ENTITY_TYPE.register(name, () -> tileEntityType);
		return tileEntityType;
	}
}
