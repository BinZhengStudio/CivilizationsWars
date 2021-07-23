package xyz.bzstudio.civilizationswars.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
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
	private final int width;
	private final int height;
	private final int totalCharge;
	private final int charge;
	private final int hudLeft;
	private final int hudTop;
	private final int textureWidth;
	private final int textureHeight;
	private final Minecraft minecraft;
	private final FontRenderer fontRenderer;
	private final ResourceLocation TEXTURE = new ResourceLocation(CivilizationsWars.MODID, "textures/gui/electric_gun.png");
	private final MatrixStack matrixStack;

	public ElectricGunGui(MatrixStack matrixStack, ItemStack gun, ClientPlayerEntity player) {
		this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
		this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
		this.hudLeft = this.width / 2 - 44;
		if (player.isCreative()) {
			this.hudTop = this.height - 36;
		} else {
			this.hudTop = this.height - 52;
		}
		this.textureWidth = 88;
		this.textureHeight = 26;
		this.minecraft = Minecraft.getInstance();
		this.fontRenderer = Minecraft.getInstance().fontRenderer;
		this.matrixStack = matrixStack;
		this.charge = ((ElectromagneticEjectionGunItem) gun.getItem()).getCharge(gun);
		this.totalCharge = ((ElectromagneticEjectionGunItem) gun.getItem()).getTotalCharge();
	}

	public void render() {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		blit(matrixStack, this.hudLeft, this.hudTop, 0, 0, 88, 13, this.textureWidth, this.textureHeight);
		blit(matrixStack, this.hudLeft, this.hudTop, 0, 13, 3 + (int) (82 * ((float) this.charge / this.totalCharge)), 13, this.textureWidth, this.textureHeight);

		String text = this.charge + "/" + this.totalCharge;
		this.fontRenderer.drawText(this.matrixStack, new StringTextComponent(text), (float) (this.width - this.fontRenderer.getStringWidth(text)) / 2, this.hudTop + 2, 0xFFFFFF);
	}
}
