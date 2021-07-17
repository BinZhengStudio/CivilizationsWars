package xyz.bzstudio.civilizationswars.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.bzstudio.civilizationswars.entity.LightParticleEntity;

public class LightParticleModel extends EntityModel<LightParticleEntity> {
	private final ModelRenderer body;

	public LightParticleModel() {
		this.textureWidth = 4;
		this.textureHeight = 2;

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.body.setTextureOffset(0, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(LightParticleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.body.rotateAngleX = limbSwing;
		this.body.rotateAngleY = netHeadYaw;
		this.body.rotateAngleZ = headPitch;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.body.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}
