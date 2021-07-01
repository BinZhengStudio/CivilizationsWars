package xyz.bzstudio.civilizationswars.tileentity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.bzstudio.civilizationswars.CivilizationsWars;
import xyz.bzstudio.civilizationswars.block.BlockList;

import java.util.function.Supplier;

public class TileEntityTypeList {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CivilizationsWars.MODID);
	public static final TileEntityType<CeramicsMakerTileEntity> CERAMICS_MAKER = register("ceramics_maker", CeramicsMakerTileEntity::new, BlockList.CERAMICS_MAKER);

	private static <T extends TileEntity> TileEntityType<T> register(String name, Supplier<T> factoryIn, Block... block) {
		TileEntityType<T> tileEntityType = TileEntityType.Builder.create(factoryIn, block).build(null);
		TILE_ENTITY_TYPE.register(name, () -> tileEntityType);
		return tileEntityType;
	}
}
