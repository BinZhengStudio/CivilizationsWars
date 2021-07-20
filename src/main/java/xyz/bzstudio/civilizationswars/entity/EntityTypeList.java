package xyz.bzstudio.civilizationswars.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.bzstudio.civilizationswars.CivilizationsWars;

public class EntityTypeList {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, CivilizationsWars.MODID);
	public static final EntityType<LightParticleEntity> LIGHT_PARTICLE = register("light_particle", EntityType.Builder.<LightParticleEntity>create(LightParticleEntity::new, EntityClassification.MISC).size(0.07F, 0.07F).trackingRange(10).updateInterval(20).immuneToFire().build("light_particle"));
	public static final EntityType<TwoWayFoilEntity> TWO_WAY_FOIL = register("two_way_foil", EntityType.Builder.<TwoWayFoilEntity>create(TwoWayFoilEntity::new, EntityClassification.MISC).size(0.75F, 0.0F).trackingRange(10).updateInterval(20).immuneToFire().build("two_way_foil"));

	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
		ENTITY_TYPE.register(name, () -> type);
		return type;
	}
}
