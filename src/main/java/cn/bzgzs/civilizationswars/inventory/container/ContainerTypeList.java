package cn.bzgzs.civilizationswars.inventory.container;

import cn.bzgzs.civilizationswars.CivilizationsWars;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeList {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS, CivilizationsWars.MODID);
	public static final ContainerType<CeramicsMakerContainer> CERAMICS_MAKER = register("ceramics_maker", ((windowId, inv, data) -> new CeramicsMakerContainer(windowId, inv, data.readBlockPos())));
	public static final ContainerType<CatapultControllerContainer> CATAPULT_CONTROLLER = register("catapult_controller", ((windowId, inv, data) -> new CatapultControllerContainer(windowId, inv, data.readBlockPos())));
	public static final ContainerType<ObjectCompressorContainer> OBJECT_COMPRESSOR = register("object_compressor", ((windowId, inv, data) -> new ObjectCompressorContainer(windowId, inv, data.readBlockPos())));

	private static <T extends Container> ContainerType<T> register(String name, IContainerFactory<T> factory) {
		ContainerType<T> type = IForgeContainerType.create(factory);
		CONTAINER_TYPE.register(name, () -> type);
		return type;
	}
}
