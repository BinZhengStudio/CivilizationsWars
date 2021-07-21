package xyz.bzstudio.civilizationswars.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.bzstudio.civilizationswars.CivilizationsWars;
import xyz.bzstudio.civilizationswars.client.renderer.entity.model.TwoWayFoilModel;
import xyz.bzstudio.civilizationswars.entity.TwoWayFoilEntity;

@OnlyIn(Dist.CLIENT)
public class TwoWayFoilRenderer extends EntityRenderer<TwoWayFoilEntity> {
	private final TwoWayFoilModel model = new TwoWayFoilModel();

	protected TwoWayFoilRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public ResourceLocation getEntityTexture(TwoWayFoilEntity entity) {
		return new ResourceLocation(CivilizationsWars.MODID, "textures/entity/two_way_foil.png");
	}

	@Override
	public void render(TwoWayFoilEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.push();
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucentCull(this.getEntityTexture(entityIn)));
		this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();
	}
}
