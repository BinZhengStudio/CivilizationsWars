package xyz.bzstudio.civilizationswars.inventory.container;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.bzstudio.civilizationswars.CivilizationsWars;

public class ContainerTypeList {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS, CivilizationsWars.MODID);
	public static final ContainerType<CeramicsMakerContainer> CERAMICS_MAKER = register("ceramics_maker", CeramicsMakerContainer::new);

	private static <T extends Container> ContainerType<T> register(String name, IContainerFactory<T> factory) {
		ContainerType<T> type = IForgeContainerType.create(factory);
		CONTAINER_TYPE.register(name, () -> type);
		return type;
	}
}
