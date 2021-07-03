package xyz.bzstudio.civilizationswars.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xyz.bzstudio.civilizationswars.CivilizationsWars;

public class NetworkManager {
	private static int ID = 0;
	public static final String VERSION = "1.0";
	public static final SimpleChannel CERAMICS_MAKER = NetworkRegistry.newSimpleChannel(new ResourceLocation(CivilizationsWars.MODID, "ceramics_maker"), () -> VERSION, VERSION::equals,VERSION::equals);

	public static void registerMessage() {
		CERAMICS_MAKER.messageBuilder(CeramicsMakerPack.class, ID++).encoder(CeramicsMakerPack::encoder).decoder(CeramicsMakerPack::new).consumer(CeramicsMakerPack::consumer).add();
	}
}
