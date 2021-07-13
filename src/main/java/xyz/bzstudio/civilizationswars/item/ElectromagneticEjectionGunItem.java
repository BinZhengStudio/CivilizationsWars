package xyz.bzstudio.civilizationswars.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ElectromagneticEjectionGunItem extends Item {
	public ElectromagneticEjectionGunItem() {
		super(new Properties().group(ItemGroupList.TAB_CIVILIZATIONS_WARS));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isRemote && playerIn.getHeldItem(Hand.MAIN_HAND).getItem() == this) {
			ItemStack heldItemOffHand = playerIn.getHeldItem(Hand.OFF_HAND);

			if (playerIn.isCreative()) {
				this.createExplosion(worldIn, playerIn);
			} else if (heldItemOffHand.getItem() == ItemList.LIGHT_PARTICLE) {
//                worldIn.playSound(); TODO 以后会搞音效的
				this.createExplosion(worldIn, playerIn);
				heldItemOffHand.shrink(1);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	private void createExplosion(World world, PlayerEntity player) {
		Vector3d vector3d = player.getLookVec();
		for (int i = 0; i < 10; i++) {
			if (i == 0) {
				world.createExplosion((Entity) null, player.getPosX() + (vector3d.x * 7), player.getPosY() + (vector3d.y * 7), player.getPosZ() + (vector3d.z * 7), 3, Explosion.Mode.DESTROY);
			} else {
				world.createExplosion((Entity) null, player.getPosX() + (vector3d.x * 5 * (i + 1)), player.getPosY() + (vector3d.y * 5 * (i + 1)), player.getPosZ() + (vector3d.z * 5 * (i + 1)), 3, Explosion.Mode.DESTROY);
			}
		}
	}
}
