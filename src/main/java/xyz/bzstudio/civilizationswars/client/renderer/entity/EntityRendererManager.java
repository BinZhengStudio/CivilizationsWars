package xyz.bzstudio.civilizationswars.client.renderer.entity;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import xyz.bzstudio.civilizationswars.entity.EntityTypeList;

public class EntityRendererManager {
	public static void register() {
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeList.LIGHT_PARTICLE, LightParticleRenderer::new);
	}
}