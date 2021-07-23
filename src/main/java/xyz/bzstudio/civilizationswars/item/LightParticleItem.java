package xyz.bzstudio.civilizationswars.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class LightParticleItem extends Item {
	public LightParticleItem() {
		super(new Item.Properties().group(ItemGroupList.TAB_CIVILIZATIONS_WARS));
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("itemTooltip.civilizationswars.light_particle").mergeStyle(TextFormatting.LIGHT_PURPLE));
	}
}
