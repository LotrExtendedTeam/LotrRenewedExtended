package lotr.common.entity.npc.data;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

/*
 * A standard data structure for NPC data modules
 * */
public interface ExtendedNpcDataInterface {
	/*
	 * Checks if this NPC data holder can function.
	 * Used to disable trader info if not trader NPC
	 * @return True if is active
	 * */
	public boolean isActive();
	/*
	 * Activity blocking Tick NPC Data Module 
	 * */
	public default void tickI() {
		if(isActive()) {
			tick();
		}
	}
	/*
	 * Tick NPC Data Module 
	 * */
	public void tick();
	/*
	 * Mark Module as dirty for data transmitting
	 * */
	public void markDirty();
	/*
	 * Activity blocking Load NPC Data Module from NBT
	 * @param nbt The {@link CompoundNBT} to load from
	 * */
	public default void loadFromNbtI(CompoundNBT nbt) {
		if(isActive()) {
			loadFromNbt(nbt);
		}
	}
	/*
	 * Load NPC Data Module from NBT
	 * Don't call this method
	 * @param nbt The {@link CompoundNBT} to load from
	 * */
	public void loadFromNbt(CompoundNBT nbt);
	/*
	 * Activity blocking Save NPC Data Module to NBT
	 * @param nbt The {@link CompoundNBT} to save from
	 * */
	public default void writeToNbtI(CompoundNBT nbt) {
		if(isActive()) {
			writeToNbt(nbt);
		}
	}
	/*
	 * Save NPC Data Module to NBT
	 * Don't call this method
	 * @param nbt The {@link CompoundNBT} to save from
	 * */
	public void writeToNbt(CompoundNBT nbt);
	/*
	 * Activity blocking Update Client Entity Data
	 * @param player The {@link ServerPlayerEntity} to send entity data to
	 * */
	public default void sendDataToPlayerI(ServerPlayerEntity player) {
		if(isActive()) {
			sendDataToPlayer(player);
		}
	}
	/*
	 * Update Client Entity Data
	 * Don't call this method
	 * @param player The {@link ServerPlayerEntity} to send entity data to
	 * */
	public void sendDataToPlayer(ServerPlayerEntity player);
	/*
	 * Update All Client's Entity Data
	 * */
	public void sendDataToAllBeings();
	/*
	 * Generate a {@link PacketBuffer} to send information to a client
	 * @param buf A {@link PacketBuffer} to save info to
	 * @return A data filled {@link PacketBuffer}
	 * */
	public PacketBuffer createDataPacketToClient(PacketBuffer buf);
}
