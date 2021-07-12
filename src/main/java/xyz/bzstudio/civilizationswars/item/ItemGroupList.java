package xyz.bzstudio.civilizationswars.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class ItemGroupList {
	public static final ItemGroup TAB_CIVILIZATIONS_WARS = new ItemGroup("civilizationsWars") {
		@OnlyIn(Dist.CLIENT)
		@Nonnull
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ItemList.COPPER_INGOT);
		}
	};
}
