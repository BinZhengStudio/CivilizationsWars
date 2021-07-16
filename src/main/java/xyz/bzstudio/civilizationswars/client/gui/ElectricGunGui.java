package xyz.bzstudio.civilizationswars.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.bzstudio.civilizationswars.CivilizationsWars;
import xyz.bzstudio.civilizationswars.item.ElectromagneticEjectionGunItem;

@OnlyIn(Dist.CLIENT)
public class ElectricGunGui extends AbstractGui {
	private final int totalCharge;
	private final int charge;
	private final int width;
	private final int height;
	private final Minecraft minecraft;
	private final FontRenderer fontRenderer;
	private final ResourceLocation TEXTURE = new ResourceLocation(CivilizationsWars.MODID, "textures/gui/electric_gun.png");
	private final MatrixStack matrixStack;

	public ElectricGunGui(MatrixStack matrixStack, ItemStack gun) {
		this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
		this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
		this.minecraft = Minecraft.getInstance();
		this.fontRenderer = Minecraft.getInstance().fontRenderer;
		this.matrixStack = matrixStack;
		this.charge = ((ElectromagneticEjectionGunItem) gun.getItem()).getCharge(gun);
		this.totalCharge = ((ElectromagneticEjectionGunItem) gun.getItem()).getTotalCharge();
	}

	public void render() { // TODO
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		blit(matrixStack, width / 2 - 44, height - 36, 0, 0, 88, 13, 88, 26);
		blit(matrixStack, width / 2 - 44, height - 36, 0, 13, 3 + (int) (82 * ((float) this.charge / this.totalCharge)), 13, 88, 26);

		String text = this.charge + "/" + this.totalCharge;
		this.fontRenderer.drawText(this.matrixStack, new StringTextComponent(text), (float) (width - this.fontRenderer.getStringWidth(text)) / 2, height - 34, 0xFFFFFF);
	}
}
