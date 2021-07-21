package xyz.bzstudio.civilizationswars.client.renderer.entity.model;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.bzstudio.civilizationswars.entity.TwoWayFoilEntity;

@OnlyIn(Dist.CLIENT)
public class TwoWayFoilModel extends EntityModel<TwoWayFoilEntity> {
	private final ModelRenderer body;

	public TwoWayFoilModel() {
		this.textureWidth = 32;
		this.textureHeight = 16;

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.setTextureOffset(-16, 0).addBox(8.0F, 0.0F, 8.0F, -16.0F, -0.01F, -16.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(TwoWayFoilEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.body.rotateAngleX = limbSwing;
		this.body.rotateAngleY = netHeadYaw;
		this.body.rotateAngleZ = headPitch;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, 0.7F);
	}
}