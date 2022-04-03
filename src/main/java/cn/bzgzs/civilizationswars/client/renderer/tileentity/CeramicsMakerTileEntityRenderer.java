package cn.bzgzs.civilizationswars.client.renderer.tileentity;

import cn.bzgzs.civilizationswars.CivilizationsWars;
import cn.bzgzs.civilizationswars.block.CeramicsMakerBlock;
import cn.bzgzs.civilizationswars.tileentity.CeramicsMakerTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import cn.bzgzs.civilizationswars.item.ItemList;

@OnlyIn(Dist.CLIENT)
public class CeramicsMakerTileEntityRenderer extends TileEntityRenderer<CeramicsMakerTileEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(CivilizationsWars.MODID, "textures/entity/ceramics_maker.png");
	private final ModelRenderer body;

	public CeramicsMakerTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);

		this.body = new ModelRenderer(48, 15, 0, 0);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.setTextureOffset(0, 0).addBox(-1.0F, 12.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
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
	public void render(CeramicsMakerTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0.5D, 0.0D, 0.5D);
		if (tileEntityIn.hasPlayerUsing()) {
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(tileEntityIn.getWorld().getGameTime() % 360.0F * 20.0F));
		}
		this.body.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntityCutout(TEXTURE)), combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();

		if (tileEntityIn.getBlockState().get(CeramicsMakerBlock.PLACED_CLAY)) {
			ItemStack stack = tileEntityIn.getStackInSlot(0);
			BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

			if (stack.getItem() == Items.CLAY_BALL) {
				this.renderBlock(blockRenderer, Blocks.CLAY.getDefaultState(), tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
			} else if (stack.getItem() == ItemList.CERAMICS_CYLINDER_MODEL) {
				this.renderItem(itemRenderer, new ItemStack(ItemList.CERAMICS_CYLINDER_MODEL), tileEntityIn, matrixStackIn, tileEntityIn.getWorld(), bufferIn, combinedLightIn, combinedOverlayIn);
			} else {
				matrixStackIn.translate(0.325D, 1.D, 0.325D);
				matrixStackIn.scale(0.35F, 0.35F, 0.35F);
				blockRenderer.renderBlock(Blocks.CLAY.getDefaultState(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
			}

		}
	}

	private void renderBlock(BlockRendererDispatcher renderer, BlockState state, CeramicsMakerTileEntity tileEntity, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, IModelData data) {
		int degree = Math.toIntExact(tileEntity.getWorld().getGameTime() % 360 * 20);
		double radian = -(degree + 135) * Math.PI / 180.0D;

		matrixStackIn.push();
		if (tileEntity.hasPlayerUsing()) {
			matrixStackIn.translate(Math.cos(radian) * Math.sqrt(0.06125) + 0.5D, 1.D, Math.sin(radian) * Math.sqrt(0.06125) + 0.5D);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(degree));
		} else {
			matrixStackIn.translate(0.325D, 1.D, 0.325D);
		}
		matrixStackIn.scale(0.35F, 0.35F, 0.35F);
		renderer.renderBlock(state, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, data);
		matrixStackIn.pop();
	}

	private void renderItem(ItemRenderer renderer, ItemStack stack, CeramicsMakerTileEntity tileEntity, MatrixStack matrixStackIn, World world, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0.5D, 1.25D, 0.5D);
		matrixStackIn.scale(1, 1, 1);
		if (tileEntity.hasPlayerUsing()) {
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(tileEntity.getWorld().getGameTime() % 360.0F * 20.0F));
		}
		IBakedModel model = renderer.getItemModelWithOverrides(stack, world, null);
		renderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, false, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, model);
		matrixStackIn.pop();
	}
}
