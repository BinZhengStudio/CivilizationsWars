package xyz.bzstudio.civilizationswars.client.renderer.tileentity;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import xyz.bzstudio.civilizationswars.tileentity.TileEntityTypeList;

public class TileEntityRendererManager {
	public static void register() {
		ClientRegistry.bindTileEntityRenderer(TileEntityTypeList.CERAMICS_MAKER, CeramicsMakerTileEntityRenderer::new);
	}
}
