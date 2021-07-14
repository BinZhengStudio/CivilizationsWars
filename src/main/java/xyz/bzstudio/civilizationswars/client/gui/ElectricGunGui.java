package xyz.bzstudio.civilizationswars.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
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
	private final ResourceLocation TEXTURE = new ResourceLocation(CivilizationsWars.MODID, "textures/gui/electric_gun.png");
	private MatrixStack matrixStack;

	public ElectricGunGui(MatrixStack matrixStack, ItemStack gun) {
		this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
		this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
		this.minecraft = Minecraft.getInstance();
		this.matrixStack = matrixStack;
		this.charge = ((ElectromagneticEjectionGunItem) gun.getItem()).getCharge(gun);
		this.totalCharge = ((ElectromagneticEjectionGunItem) gun.getItem()).getTotalCharge();
	}

	public void setMatrixStack(MatrixStack matrixStack) {
		this.matrixStack = matrixStack;
	}

	public void render() { // TODO
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		blit(matrixStack, width / 2 - 44, height / 2 - 64, 0, 0, 88, 22, 88, 44);
		blit(matrixStack, width / 2 - 44, height / 2 - 64, 0, 22, 5 + (int) (78 * ((float) this.charge / this.totalCharge)), 22, 88, 44);
	}
}
