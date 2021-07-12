package xyz.bzstudio.civilizationswars.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EggItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import xyz.bzstudio.civilizationswars.entity.LightParticleEntity;

public class ElectromagneticEjectionGunItem extends Item {
	public ElectromagneticEjectionGunItem() {
		super(new Properties().group(ItemGroupList.TAB_CIVILIZATIONS_WARS));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isRemote && playerIn.getHeldItem(Hand.MAIN_HAND).getItem() == this) {
			ItemStack heldItemOffHand = playerIn.getHeldItem(Hand.OFF_HAND);

			if (playerIn.isCreative()) {
				this.shoot(worldIn, playerIn);
			} else if (heldItemOffHand.getItem() == ItemList.LIGHT_PARTICLE) {
				this.shoot(worldIn, playerIn);
//                worldIn.playSound(); TODO 以后会搞音效的
				heldItemOffHand.shrink(1);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	private void shoot(World world, PlayerEntity player) {
		Vector3d vector3d = player.getLookVec();
		LightParticleEntity entity = new LightParticleEntity(player, vector3d.x, vector3d.y, vector3d.z, world);
		entity.setPosition(player.getPosX() + vector3d.x * 4.0D, player.getPosYHeight(0.5D) + 0.5D, player.getPosZ() + vector3d.z * 4.0D);
		world.addEntity(entity);
		// TODO 需要算，以后再写
	}
}
