package cn.bzgzs.civilizationswars.client.renderer.entity;

import cn.bzgzs.civilizationswars.CivilizationsWars;
import cn.bzgzs.civilizationswars.client.renderer.entity.model.ThreebodyModelModel;
import cn.bzgzs.civilizationswars.entity.ThreebodyModelEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class ThreebodyModelRenderer extends EntityRenderer<ThreebodyModelEntity> {
	private final ThreebodyModelModel model = new ThreebodyModelModel();

	protected ThreebodyModelRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public ResourceLocation getEntityTexture(ThreebodyModelEntity entity) {
		return new ResourceLocation(CivilizationsWars.MODID, "textures/entity/threebody_model.png");
	}

	@Override
	public void render(ThreebodyModelEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.push();
		matrixStackIn.scale(4.0F, 4.0F, 4.0F);
		IVertexBuilder builder = bufferIn.getBuffer(this.model.getRenderType(this.getEntityTexture(entityIn)));
		float limbSwing = (float) Math.asin((entityIn.rawZ - entityIn.getPosZ()) / 49);
		float headPitch = (float) Math.asin((entityIn.getPosX() - entityIn.rawX) / 49);
		this.model.setRotationAngles(entityIn, limbSwing, 0.0F, 0.0F, 0.0F, headPitch);
		this.model.render(matrixStackIn, builder, 200, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();
	}
}
