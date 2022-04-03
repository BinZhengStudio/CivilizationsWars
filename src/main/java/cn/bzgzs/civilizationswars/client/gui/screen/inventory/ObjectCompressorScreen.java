package cn.bzgzs.civilizationswars.client.gui.screen.inventory;

import cn.bzgzs.civilizationswars.CivilizationsWars;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import cn.bzgzs.civilizationswars.inventory.container.ObjectCompressorContainer;

public class ObjectCompressorScreen extends ContainerScreen<ObjectCompressorContainer> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(CivilizationsWars.MODID, "textures/gui/container/object_compressor.png");

	public ObjectCompressorScreen(ObjectCompressorContainer container, PlayerInventory inv, ITextComponent titleIn) {
		super(container, inv, titleIn);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
		if (mouseX < this.guiLeft + 55 && mouseX > this.guiLeft + 26 && mouseY < this.guiTop + 78 && mouseY > this.guiTop + 8) {
			this.renderTooltip(matrixStack, new TranslationTextComponent("screen.tooltip.threebody_energy"), mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
