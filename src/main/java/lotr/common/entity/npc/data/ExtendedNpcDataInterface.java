package lotr.common.entity.npc.data;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public interface ExtendedNpcDataInterface {

	public void loadFromNbt(CompoundNBT nbt);

	public void writeToNbt(CompoundNBT nbt);

	public PacketBuffer createDataPacketToClient(PacketBuffer buf);

	public void sendDataToPlayer(ServerPlayerEntity player);

	public void sendDataToAllBeings();

	public void tick();

	public void markDirty();
}
