package lotr.common.entity.npc.data;


import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfileManager;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class ExtendedNpcHiredUnitInfoHolder implements ExtendedNpcDataInterface {
	private final NPCEntity thisEntity;
	private ExtendedServerHiredUnitProfile hiredUnitProfileHolder;

	private boolean resendData = false;
	private boolean loadedData = false;

	public ExtendedNpcHiredUnitInfoHolder(NPCEntity thisEntity) {
		this.thisEntity = thisEntity;
	}

	/**
	 * SHOULD ONLY BE CALLED ON THE SERVER, UNIT PROFILES ARE NOT AVAILABLE CLIENT SIDE!
	 * IM LOOKING AT YOU MAX! (MYSELF) (I keep making the mistake)
	 * @return A {@link ExtendedServerHiredUnitProfile} instance
	 * */
	public ExtendedServerHiredUnitProfile getProfile() {
		if(hiredUnitProfileHolder!=null) {
			return hiredUnitProfileHolder;
		}
		if(isActive()) {
			hiredUnitProfileHolder = ExtendedServerHiredUnitProfileManager.INSTANCE.get(null); // TODO add a way to dynamically add a unit profile to vanilla npcs
			return hiredUnitProfileHolder;
		}
		ExtendedLog.error("ERROR ERROR! TRIED GETTING A UNIT PROFILE FROM AN NPC THAT DOES NOT SUPPORT UNIT CREATION");
		try { 
			throw new Exception("Dumping stack trace and returning null!");
		} catch (Exception e) {
			e.printStackTrace(); // Prints the stack trace to System.err
		}
		return null;
	}

	/**
	 * Generate new unit data if not loaded, respecting previously set values
	 * */
	@Override
	public void tick() {
		if (!thisEntity.level.isClientSide) {
			// Handle loading init data or fixing broken data
			if(loadedData==false) {
				loadedData = true;
				markDirty();
			}
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
	public void loadFromNbt(CompoundNBT nbt) {
		if(nbt.contains("UnitInfo")) {
			CompoundNBT holder = nbt.getCompound("UnitInfo");
			loadedData = holder.getBoolean("HasLoaded");
		}else {
			loadedData=false;
		}
		// Handle pre-1.8.0 hired unit data
		if(nbt.contains("isWanderingTrader")) {
		}
	}

	@Override
	public void writeToNbt(CompoundNBT nbt) {
		CompoundNBT holder = new CompoundNBT();
		holder.putBoolean("HasLoaded", loadedData);
		nbt.put("HiredUnitInfo", holder);
	}

	/**
	 * Only enable this module if its attached to an active unit
	 * */
	@Override
	public boolean isActive() {
		return false; // TODO change to better logic
	}

	/**
	 * Sync unit data?
	 * */
	@Override
	public PacketBuffer createDataPacketToClient(PacketBuffer buf) { 
		buf.writeVarInt(thisEntity.getId());
		return buf;
	}

	/**
	 * Sync unit data?
	 * */
	@Override
	public void sendDataToPlayer(ServerPlayerEntity player) {
		//ExtendedSNpcTraderInfoPacket packet = new ExtendedSNpcTraderInfoPacket(this);
		//ExtendedPacketHandler.sendToClient(packet, player);
	}

	/**
	 * Sync unit data?
	 * */
	@Override
	public void sendDataToAllBeings() {
		//ExtendedSNpcTraderInfoPacket packet = new ExtendedSNpcTraderInfoPacket(this);
		//ExtendedPacketHandler.sendToAllTrackingEntity(packet, thisEntity);
	}

	/**
	 * Handle data packets from the server
	 */
	public static void readDataPacketFromServer(PacketBuffer buf, World world) {
		int entityId = buf.readVarInt();
		Entity entity = world.getEntity(entityId);
		if (entity instanceof NPCEntity) {
			NPCEntity npc = (NPCEntity) entity;
			ExtendedNpcHiredUnitInfoHolder traderInfo = ((ExtendedNPCEntity) npc).getUnitInfo();
			if(traderInfo!=null) {
				
			}
		}
	}
}
