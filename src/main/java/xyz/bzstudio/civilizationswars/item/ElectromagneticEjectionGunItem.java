package xyz.bzstudio.civilizationswars.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import xyz.bzstudio.civilizationswars.util.DamageSourceList;

public class ElectromagneticEjectionGunItem extends Item {
	private int charge;

	public ElectromagneticEjectionGunItem() {
		super(new Properties().group(ItemGroupList.TAB_CIVILIZATIONS_WARS).maxStackSize(1));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isRemote && playerIn.getHeldItem(Hand.MAIN_HAND).getItem() == this) {
			ItemStack heldItemMainHand = playerIn.getHeldItem(Hand.MAIN_HAND);
			ItemStack heldItemOffHand = playerIn.getHeldItem(Hand.OFF_HAND);

			if (playerIn.isCrouching()) {
				if (playerIn.isCreative()) {
					this.createExplosion(worldIn, playerIn, this.getCharge(heldItemMainHand));
				} else {
					if (heldItemOffHand.getItem() == ItemList.LIGHT_PARTICLE) {
						this.createExplosion(worldIn, playerIn, this.getCharge(heldItemMainHand));
						heldItemOffHand.shrink(1);
					}
				}
				this.setCharge(heldItemMainHand, 0);
			} else {
				playerIn.setActiveHand(handIn);
			}
		}
		return ActionResult.resultPass(playerIn.getHeldItem(handIn));
	}

	@Override
	public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
		if (!worldIn.isRemote && livingEntityIn instanceof PlayerEntity) {
			this.setCharge(stack, this.getCharge(stack) + (int) ((float) (stack.getUseDuration() - count) / (stack.getUseDuration() - 1) * this.getTotalCharge()));
		}
	}

	public int getTotalCharge() {
		return 114514;
	}

	public int getCharge(ItemStack stack) {
		CompoundNBT nbt = stack.getTag();
		if (nbt == null) {
			nbt = stack.getOrCreateTag();
			nbt.putInt("Charge", 0);
			this.charge = 0;
		} else if (nbt.contains("Charge")) {
			this.charge = nbt.getInt("Charge");
		} else {
			this.charge = 0;
			nbt.putInt("Charge", this.charge);
		}
		return this.charge;
	}

	public void setCharge(ItemStack stack, int charge) {
		CompoundNBT nbt = stack.getOrCreateTag();
		this.charge = Math.min(charge, this.getTotalCharge());
		nbt.putInt("Charge", this.charge);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 3000;
	}

	private void createExplosion(World world, PlayerEntity player, int charge) {
		Vector3d vector3d = player.getLookVec();
		for (int i = 0; i < 10 * ((float) charge / this.getTotalCharge()); i++) {
			if (i == 0) {
				world.createExplosion((Entity) null, player.getPosX() + (vector3d.x * 7), player.getPosY() + (vector3d.y * 7), player.getPosZ() + (vector3d.z * 7), 3.0F * ((float) charge / this.getTotalCharge()), Explosion.Mode.DESTROY);
			} else {
				world.createExplosion((Entity) null, player.getPosX() + (vector3d.x * 5 * (i + 1)), player.getPosY() + (vector3d.y * 5 * (i + 1)), player.getPosZ() + (vector3d.z * 5 * (i + 1)), 3.0F * ((float) charge / this.getTotalCharge()), Explosion.Mode.DESTROY);
			}
		}

		for (int i = 0; i < 50 * ((float) charge / this.getTotalCharge()); i++) {
			double x = player.getPosX() + vector3d.x * i;
			double y = player.getPosY() + vector3d.y * i;
			double z = player.getPosZ() + vector3d.z * i;
			AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
			for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(player, axisAlignedBB)) {
				if (!entity.isInvulnerableTo(DamageSourceList.LIGHT_PARTICLE)) {
					if (entity instanceof LivingEntity) {
						LivingEntity livingEntity = (LivingEntity) entity;
						livingEntity.attackEntityFrom(DamageSourceList.LIGHT_PARTICLE, 30 * ((float) charge / this.getTotalCharge()));
					} else {
						entity.remove();
					}
				}
			}
		}
	}
}
