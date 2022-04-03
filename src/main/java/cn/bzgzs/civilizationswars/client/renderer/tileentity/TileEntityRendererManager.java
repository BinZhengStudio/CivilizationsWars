package cn.bzgzs.civilizationswars.client.renderer.tileentity;

import cn.bzgzs.civilizationswars.tileentity.TileEntityTypeList;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class TileEntityRendererManager {
	public static void register() {
		ClientRegistry.bindTileEntityRenderer(TileEntityTypeList.CERAMICS_MAKER, CeramicsMakerTileEntityRenderer::new);
	}
}
