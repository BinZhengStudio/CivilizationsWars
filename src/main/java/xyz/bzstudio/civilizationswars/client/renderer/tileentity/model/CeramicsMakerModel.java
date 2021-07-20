package xyz.bzstudio.civilizationswars.client.renderer.tileentity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class CeramicsMakerModel extends Model {
	private final ModelRenderer body;

	public CeramicsMakerModel(Function<ResourceLocation, RenderType> renderTypeIn) {
		super(renderTypeIn);

		this.textureWidth = 48;
		this.textureHeight = 18;

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.setTextureOffset(0, 15).addBox(-1.0F, 12.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		this.body.setTextureOffset(21, 5).addBox(-3.0F, 13.0F, -8.0F, 6.0F, 3.0F, 1.0F, 0.0F, false);
		this.body.setTextureOffset(3, 2).addBox(-3.0F, 13.0F, 7.0F, 6.0F, 3.0F, 1.0F, 0.0F, false);
		this.body.setTextureOffset(12, 3).addBox(-5.0F, 13.0F, -7.0F, 10.0F, 3.0F, 1.0F, 0.0F, false);
		this.body.setTextureOffset(12, 4).addBox(-5.0F, 13.0F, 6.0F, 10.0F, 3.0F, 1.0F, 0.0F, false);
		this.body.setTextureOffset(0, 0).addBox(-6.0F, 13.0F, -6.0F, 12.0F, 3.0F, 12.0F, 0.0F, false);
		this.body.setTextureOffset(20, 3).addBox(-8.0F, 13.0F, -3.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
		this.body.setTextureOffset(16, 0).addBox(-7.0F, 13.0F, -5.0F, 1.0F, 3.0F, 10.0F, 0.0F, false);
		this.body.setTextureOffset(22, 0).addBox(6.0F, 13.0F, -5.0F, 1.0F, 3.0F, 10.0F, 0.0F, false);
		this.body.setTextureOffset(12, 5).addBox(7.0F, 13.0F, -3.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}
