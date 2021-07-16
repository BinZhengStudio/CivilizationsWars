package xyz.bzstudio.civilizationswars.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.bzstudio.civilizationswars.item.ItemList;

public class TwoWayFoilEntity extends ProjectileItemEntity {
	public TwoWayFoilEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public TwoWayFoilEntity(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn) {
		super(type, x, y, z, worldIn);
	}

	public TwoWayFoilEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity livingEntityIn, World worldIn) {
		super(type, livingEntityIn, worldIn);
	}

	@Override
	protected Item getDefaultItem() {
		return ItemList.TWO_WAY_FOIL;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
