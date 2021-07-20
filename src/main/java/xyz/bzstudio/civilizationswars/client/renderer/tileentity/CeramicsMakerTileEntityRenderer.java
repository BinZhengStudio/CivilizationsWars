package xyz.bzstudio.civilizationswars.client.renderer.tileentity;

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
import xyz.bzstudio.civilizationswars.CivilizationsWars;
import xyz.bzstudio.civilizationswars.block.CeramicsMakerBlock;
import xyz.bzstudio.civilizationswars.client.renderer.tileentity.model.CeramicsMakerModel;
import xyz.bzstudio.civilizationswars.item.ItemList;
import xyz.bzstudio.civilizationswars.tileentity.CeramicsMakerTileEntity;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class CeramicsMakerTileEntityRenderer extends TileEntityRenderer<CeramicsMakerTileEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(CivilizationsWars.MODID, "textures/entity/ceramics_maker.png");

	public CeramicsMakerTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(CeramicsMakerTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0.5D, 0.0D, 0.5D);
		if (tileEntityIn.hasPlayerUsing()) {
			try {
				matrixStackIn.rotate(Vector3f.YP.rotationDegrees(Objects.requireNonNull(tileEntityIn.getWorld()).getGameTime() % 360.0F * 10.0F));
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		CeramicsMakerModel model = new CeramicsMakerModel(RenderType::getEntityCutout);
		model.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntityCutout(TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
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
		matrixStackIn.push();
		matrixStackIn.translate(0.325D, 1.D, 0.325D);
		matrixStackIn.scale(0.35F, 0.35F, 0.35F);
		if (tileEntity.hasPlayerUsing()) {
			try {
				matrixStackIn.rotate(Vector3f.YP.rotationDegrees(Objects.requireNonNull(tileEntity.getWorld()).getGameTime() % 360.0F * 10.0F));
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		renderer.renderBlock(state, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, data);
		matrixStackIn.pop();
	}

	private void renderItem(ItemRenderer renderer, ItemStack stack, CeramicsMakerTileEntity tileEntity, MatrixStack matrixStackIn, World world, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0.5D, 1.25D, 0.5D);
		matrixStackIn.scale(1, 1, 1);
		if (tileEntity.hasPlayerUsing()) {
			try {
				matrixStackIn.rotate(Vector3f.YP.rotationDegrees(Objects.requireNonNull(tileEntity.getWorld()).getGameTime() % 360.0F * 10.0F));
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		IBakedModel model = renderer.getItemModelWithOverrides(stack, world, null);
		renderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, false, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, model);
		matrixStackIn.pop();
	}
}
