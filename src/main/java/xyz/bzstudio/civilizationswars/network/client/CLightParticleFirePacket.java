package xyz.bzstudio.civilizationswars.network.client;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.bzstudio.civilizationswars.block.CatapultControllerBlock;

import java.util.Objects;
import java.util.function.Supplier;

public class CLightParticleFirePacket {
	private final int[] offset;
	private final BlockPos pos;

	public CLightParticleFirePacket(PacketBuffer buffer) {
		this.offset = buffer.readVarIntArray();
		this.pos = buffer.readBlockPos();
	}

	public CLightParticleFirePacket(int[] offset, BlockPos pos) {
		this.offset = offset;
		this.pos = pos;
	}

	public void encode(PacketBuffer buffer) {
		buffer.writeVarIntArray(this.offset);
		buffer.writeBlockPos(this.pos);
	}

	public void consumer(Supplier<NetworkEvent.Context> context) {
		try	{
			context.get().enqueueWork(() -> CatapultControllerBlock.fireLightParticle(this.pos, this.offset, Objects.requireNonNull(context.get().getSender())));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		context.get().setPacketHandled(true);
	}

}
