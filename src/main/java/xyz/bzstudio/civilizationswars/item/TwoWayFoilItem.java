package xyz.bzstudio.civilizationswars.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import xyz.bzstudio.civilizationswars.entity.TwoWayFoilEntity;

public class TwoWayFoilItem extends Item {
	public TwoWayFoilItem() {
		super(new Properties().group(ItemGroupList.TAB_CIVILIZATIONS_WARS));
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		worldIn.playSound(playerIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isRemote) {
			Vector3d vector3d = playerIn.getLookVec();
			TwoWayFoilEntity entity = new TwoWayFoilEntity(playerIn, vector3d.x, vector3d.y, vector3d.z, worldIn);
			entity.setPosition(playerIn.getPosX() + vector3d.x, playerIn.getPosY() + 1.5D + vector3d.y, playerIn.getPosZ() + vector3d.z);
			worldIn.addEntity(entity);
		}

		playerIn.addStat(Stats.ITEM_USED.get(this));
		if (!playerIn.abilities.isCreativeMode) {
			itemstack.shrink(1);
		}

		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}
}
