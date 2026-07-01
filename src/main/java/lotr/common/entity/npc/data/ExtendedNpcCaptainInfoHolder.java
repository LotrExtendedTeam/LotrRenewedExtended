package lotr.common.entity.npc.data;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainProfileManager;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSNpcCaptainInfoPacket;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.ExtendedICaptainEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class ExtendedNpcCaptainInfoHolder implements ExtendedNpcDataInterface {
	private final NPCEntity thisEntity;
	private int hiringAlignmentRequired = 1659; // February 16 – The first known check (400 pounds) is written.
	private boolean resendData = false;
	private boolean loadedData = false;
	public ExtendedNpcCaptainInfoHolder(NPCEntity thisEntity) {
		this.thisEntity = thisEntity;
	}

	/**
	 * Gets the amount of alignment needed to hire.
	 * Synced to the client
	 * @return The integer value of the needed alignment
	 * */
	public int getHiringAlignmentRequiredDualSided() {
		return hiringAlignmentRequired;
	}

	/**
	 * Generate new trader data if not loaded, respecting previously set values
	 * */
	@Override
	public void tick() {
		if (!thisEntity.level.isClientSide) {
			// Handle loading init data or fixing broken data
			if(loadedData==false) {
				ExtendedCaptainProfile profile = ExtendedCaptainProfileManager.INSTANCE.get(((ExtendedICaptainEntity)thisEntity).getCaptainProfile());
				hiringAlignmentRequired = profile.getInterractionAlignmentRequired();
				loadedData = true;
				markDirty();
			}
			// Re-send data if changed
			if (resendData) {
				sendDataToAllBeings();
				resendData = false;
			}
		}
	}

	@Override
	public void markDirty() {
		resendData = true;
	}

	@Override
	public void loadFromNbt(CompoundNBT nbt) {}

	@Override
	public void writeToNbt(CompoundNBT nbt) {}

	/**
	 * We don't need to load data as there is none to load
	 * */
	@Override
	public boolean shouldLoad() {
		return false;
	}
	/**
	 * Check if this NPC is a captain
	 * */
	@Override
	public boolean shouldTick() {
		return thisEntity instanceof ExtendedICaptainEntity;
	}

	/**
	 * Sync trader alignment level data
	 * */
	@Override
	public PacketBuffer createDataPacketToClient(PacketBuffer buf) { 
		buf.writeVarInt(thisEntity.getId());
		buf.writeVarInt(hiringAlignmentRequired);
		return buf;
	}

	/**
	 * Sync trader alignment level data
	 * */
	@Override
	public void sendDataToPlayer(ServerPlayerEntity player) {
		ExtendedPacketHandler.sendToClient(new ExtendedSNpcCaptainInfoPacket(this), player);
	}

	/**
	 * Sync trader alignment level data
	 * */
	@Override
	public void sendDataToAllBeings() {
		ExtendedPacketHandler.sendToAllTrackingEntity(new ExtendedSNpcCaptainInfoPacket(this), thisEntity);
	}

	/*
	 * Handle data packets from the server
	 * */
	public static void readDataPacketFromServer(PacketBuffer buf, World world) {
		int entityId = buf.readVarInt();
		Entity entity = world.getEntity(entityId);
		if (entity instanceof NPCEntity) {
			NPCEntity npc = (NPCEntity) entity;
			ExtendedNpcCaptainInfoHolder traderInfo = ((ExtendedINPCEntity) npc).getCaptainInfo();
			if(traderInfo!=null) {
				traderInfo.hiringAlignmentRequired = buf.readVarInt();
			}
		}
	}
}
