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

public class BzLogoItem extends Item {

	public BzLogoItem() {
		super(new Item.Properties().group(ItemGroupList.TAB_CIVILIZATIONS_WARS));
	}

	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("itemTooltip.civilizationswars.bz_logo.1").mergeStyle(TextFormatting.GREEN));
		tooltip.add(new TranslationTextComponent("itemTooltip.civilizationswars.bz_logo.2").mergeStyle(TextFormatting.AQUA));
		tooltip.add(new TranslationTextComponent("itemTooltip.civilizationswars.bz_logo.3").mergeStyle(TextFormatting.YELLOW));
		tooltip.add(new TranslationTextComponent("itemTooltip.civilizationswars.bz_logo.4").mergeStyle(TextFormatting.GOLD));
	}
}
