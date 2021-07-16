package xyz.bzstudio.civilizationswars.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.bzstudio.civilizationswars.CivilizationsWars;
import xyz.bzstudio.civilizationswars.inventory.container.CatapultControllerContainer;

public class CatapultControllerScreen extends ContainerScreen<CatapultControllerContainer> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(CivilizationsWars.MODID, "textures/gui/container/catapult_controller.png");
	private TextFieldWidget textFieldX;
	private TextFieldWidget textFieldY;
	private TextFieldWidget textFieldZ;

	public CatapultControllerScreen(CatapultControllerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init() {
		super.init();
		this.minecraft.keyboardListener.enableRepeatEvents(true);

		this.textFieldX = new TextFieldWidget(this.font, this.guiLeft + 146, this.guiTop + 16, 16, 10, new TranslationTextComponent("screen.catapult_controller.field_x"));
		this.textFieldX.setMaxStringLength(3);
		this.textFieldX.setEnableBackgroundDrawing(false);
		this.textFieldX.setVisible(true);
		this.textFieldX.setTextColor(0xFF9999);
		this.textFieldX.setText("0");
		this.children.add(this.textFieldX);

		this.textFieldY = new TextFieldWidget(this.font, this.guiLeft + 146, this.guiTop + 38, 16, 10, new TranslationTextComponent("screen.catapult_controller.field_y"));
		this.textFieldY.setMaxStringLength(3);
		this.textFieldY.setEnableBackgroundDrawing(false);
		this.textFieldY.setVisible(true);
		this.textFieldY.setTextColor(0xFF9999);
		this.textFieldY.setText("0");
		this.children.add(this.textFieldY);

		this.textFieldZ = new TextFieldWidget(this.font, this.guiLeft + 146, this.guiTop + 60, 16, 10, new TranslationTextComponent("screen.catapult_controller.field_z"));
		this.textFieldZ.setMaxStringLength(3);
		this.textFieldZ.setEnableBackgroundDrawing(false);
		this.textFieldZ.setVisible(true);
		this.textFieldZ.setTextColor(0xFF9999);
		this.textFieldZ.setText("0");
		this.children.add(this.textFieldZ);

		this.addButton(new Button(this.guiLeft + 73, this.guiTop + 58, 30, 20, new TranslationTextComponent("screen.catapult_controller.fire"), (press) -> {
			// TODO
		}));
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
		if (mouseX < this.guiLeft + 30 && mouseX > this.guiLeft + 15 && mouseY < this.guiTop + 69 && mouseY > this.guiTop + 15) {
			this.renderTooltip(matrixStack, new TranslationTextComponent("screen.tooltip.threebody_energy"), mouseX, mouseY);
		}
		this.textFieldX.render(matrixStack, mouseX, mouseY, partialTicks);
		this.textFieldY.render(matrixStack, mouseX, mouseY, partialTicks);
		this.textFieldZ.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
		TranslationTextComponent xOffset = new TranslationTextComponent("screen.catapult_controller.x_offset");
		this.font.drawText(matrixStack, xOffset, 143 - this.font.getStringWidth(xOffset.getString()),16,4210752);

		TranslationTextComponent yOffset = new TranslationTextComponent("screen.catapult_controller.y_offset");
		this.font.drawText(matrixStack, yOffset, 143 - this.font.getStringWidth(yOffset.getString()),38,4210752);

		TranslationTextComponent zOffset = new TranslationTextComponent("screen.catapult_controller.z_offset");
		this.font.drawText(matrixStack, zOffset, 143 - this.font.getStringWidth(zOffset.getString()),60,4210752);

		for (int i = 0; i < 3; i++) {
			this.font.drawText(matrixStack, new StringTextComponent("°"), 162, i * 22 + 16, 4210752);
			this.font.drawText(matrixStack, new StringTextComponent("(-30~30)"), 112, i * 22 + 23, 0xFF7777);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
