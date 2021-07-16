package xyz.bzstudio.civilizationswars.network.server;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SCatapultCheckedPacket {
	private final int num;

	public SCatapultCheckedPacket(PacketBuffer buffer) {
		num = buffer.readInt();
	}

	public SCatapultCheckedPacket(int num) {
		this.num = num;
	}

	public void encode(PacketBuffer buffer) {
		buffer.writeInt(this.num);
	}

	public void consumer(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {}); // TODO
		context.get().setPacketHandled(true);
	}
}
