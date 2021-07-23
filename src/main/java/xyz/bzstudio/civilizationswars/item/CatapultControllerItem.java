package xyz.bzstudio.civilizationswars.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import xyz.bzstudio.civilizationswars.block.BlockList;

import javax.annotation.Nullable;
import java.util.List;

public class CatapultControllerItem extends BlockItem {
	public CatapultControllerItem() {
		super(BlockList.CATAPULT_CONTROLLER, new Item.Properties().group(ItemGroupList.TAB_CIVILIZATIONS_WARS));
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("itemTooltip.civilizationswars.catapult_controller.1").mergeStyle(TextFormatting.LIGHT_PURPLE));
		tooltip.add(new TranslationTextComponent("itemTooltip.civilizationswars.catapult_controller.2").mergeStyle(TextFormatting.GREEN));
		tooltip.add(new TranslationTextComponent("itemTooltip.civilizationswars.catapult_controller.3").mergeStyle(TextFormatting.GOLD));
		tooltip.add(new TranslationTextComponent("itemTooltip.civilizationswars.catapult_controller.4").mergeStyle(TextFormatting.BLUE));
		tooltip.add(new TranslationTextComponent("itemTooltip.civilizationswars.catapult_controller.5").mergeStyle(TextFormatting.RED));
	}
}
