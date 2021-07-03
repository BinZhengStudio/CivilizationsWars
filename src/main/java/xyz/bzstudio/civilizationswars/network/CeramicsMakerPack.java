package xyz.bzstudio.civilizationswars.network;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CeramicsMakerPack {
	private final ItemStack message;

	public CeramicsMakerPack(PacketBuffer buffer) {
		message = buffer.readItemStack();
	}

	public CeramicsMakerPack(ItemStack message) {
		this.message = message;
	}

	public void encoder(PacketBuffer buffer) {
		buffer.writeItemStack(this.message);
	}

	public void consumer(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
		});
		context.get().setPacketHandled(true);
	}
}
