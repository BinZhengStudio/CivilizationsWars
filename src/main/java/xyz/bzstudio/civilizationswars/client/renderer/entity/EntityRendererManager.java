package xyz.bzstudio.civilizationswars.client.renderer.entity;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import xyz.bzstudio.civilizationswars.entity.EntityTypeList;

public class EntityRendererManager {
	public static void register() {
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeList.AERIAL_BLOCK, AerialBlockRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeList.LIGHT_PARTICLE, LightParticleRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeList.THREEBODY_MODEL, ThreebodyModelRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeList.TWO_WAY_FOIL, TwoWayFoilRenderer::new);
	}
}
