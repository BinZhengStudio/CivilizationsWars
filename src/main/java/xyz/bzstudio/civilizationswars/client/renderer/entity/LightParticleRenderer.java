package xyz.bzstudio.civilizationswars.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.bzstudio.civilizationswars.CivilizationsWars;
import xyz.bzstudio.civilizationswars.client.renderer.entity.model.LightParticleModel;
import xyz.bzstudio.civilizationswars.entity.LightParticleEntity;

@OnlyIn(Dist.CLIENT)
public class LightParticleRenderer extends EntityRenderer<LightParticleEntity> {
	private final LightParticleModel model = new LightParticleModel();

	protected LightParticleRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public ResourceLocation getEntityTexture(LightParticleEntity entity) {
		return new ResourceLocation(CivilizationsWars.MODID, "textures/entity/light_particle.png");
	}

	@Override
	public void render(LightParticleEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.push();
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(this.getEntityTexture(entityIn)));
		this.model.render(matrixStackIn, ivertexbuilder, 200, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();
	}
}
