package xyz.bzstudio.civilizationswars.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.bzstudio.civilizationswars.CivilizationsWars;
import xyz.bzstudio.civilizationswars.inventory.container.CeramicsMakerContainer;
import xyz.bzstudio.civilizationswars.inventory.container.SelectSlot;

public class CeramicsMakerScreen extends ContainerScreen<CeramicsMakerContainer> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(CivilizationsWars.MODID, "textures/gui/container/ceramics_maker.png");

	public CeramicsMakerScreen(CeramicsMakerContainer container, PlayerInventory inventory, ITextComponent title) {
		super(container, inventory, title);
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		this.font.drawText(matrixStack, new TranslationTextComponent("screen_text.ceramics_maker.1"), 12, 7, 0xFF7777);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		// 绘制材质
		this.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void renderHoveredTooltip(MatrixStack matrixStack, int x, int y) {
		if (this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.getHasStack() && !(this.hoveredSlot instanceof SelectSlot)) {
			this.renderTooltip(matrixStack, this.hoveredSlot.getStack(), x, y);
		}
	}
}
