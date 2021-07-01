package xyz.bzstudio.civilizationswars.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CeramicsMakerScreen extends Screen {
	protected CeramicsMakerScreen() {
		super(new StringTextComponent("ceramics_maker"));
	}

	@Override
	protected void init() {
		this.addButton(new Button(this.width / 2,this.height/2,50,20, new TranslationTextComponent("ceramics_maker_screen.button.make"),(p) -> {
			// TODO
		}));
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		RenderSystem.color4f(1.0F,1.0F,1.0F,1.0F);
		// TODO
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
}
