package xyz.bzstudio.civilizationswars.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ElectromagneticEjectionGunItem extends Item {
	public ElectromagneticEjectionGunItem() {
		super(new Properties().group(ItemGroupList.TAB_CIVILIZATIONS_WARS));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isRemote && playerIn.getHeldItem(Hand.MAIN_HAND).getItem() == this) {
			ItemStack heldItemOffHand = playerIn.getHeldItem(Hand.OFF_HAND);

			if (heldItemOffHand.getItem() == ItemList.LIGHT_PARTICLE) {
				this.shoot(playerIn);
//                worldIn.playSound(); TODO 以后会搞音效的
				heldItemOffHand.shrink(1);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	private void shoot(PlayerEntity player) {
		// TODO 需要算，以后再写
	}
}
