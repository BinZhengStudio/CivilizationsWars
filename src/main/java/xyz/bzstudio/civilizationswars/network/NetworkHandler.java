package xyz.bzstudio.civilizationswars.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xyz.bzstudio.civilizationswars.CivilizationsWars;
import xyz.bzstudio.civilizationswars.network.client.CLightParticleFirePacket;

public class NetworkHandler {
	public static final String VERSION = "1.0";
	public static final SimpleChannel LIGHT_PARTICLE_FIRE = NetworkRegistry.newSimpleChannel(new ResourceLocation(CivilizationsWars.MODID, "catapult_fire"), () -> VERSION, (v) -> v.equals(VERSION), (v) -> v.equals(VERSION));
	private static int id = 0;

	public static void register() {
		LIGHT_PARTICLE_FIRE.messageBuilder(CLightParticleFirePacket.class, getId()).encoder(CLightParticleFirePacket::encode).decoder(CLightParticleFirePacket::new).consumer(CLightParticleFirePacket::consumer).add();
	}

	private static int getId() {
		return id++;
	}
}
