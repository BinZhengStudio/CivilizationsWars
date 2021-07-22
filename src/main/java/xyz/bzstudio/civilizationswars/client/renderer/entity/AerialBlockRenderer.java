package xyz.bzstudio.civilizationswars.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
import xyz.bzstudio.civilizationswars.entity.AbstractTwoWayFoilEntity;
import xyz.bzstudio.civilizationswars.entity.AerialBlockEntity;

@OnlyIn(Dist.CLIENT)
public class AerialBlockRenderer extends EntityRenderer<AerialBlockEntity> {
	protected AerialBlockRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public ResourceLocation getEntityTexture(AerialBlockEntity entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}

	@Override
	public void render(AerialBlockEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		BlockState aerialBlock = entityIn.getAerialBlock();
		if (entityIn.world.getBlockState(entityIn.getPosition()) != aerialBlock) {
			float scale = (float) (entityIn.getDistance() / AbstractTwoWayFoilEntity.radius);
			BlockRendererDispatcher renderer = Minecraft.getInstance().getBlockRendererDispatcher();
			matrixStackIn.push();
			matrixStackIn.translate(-0.5D, 0.0D, -0.5D);
			matrixStackIn.scale(scale, scale, scale);
			renderer.renderBlock(aerialBlock, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
			matrixStackIn.pop();
		}
	}
}
