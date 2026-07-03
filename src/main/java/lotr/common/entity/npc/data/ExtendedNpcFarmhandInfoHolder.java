package lotr.common.entity.npc.data;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSNpcFarmhandInfoPacket;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedNBTUtils;

import lotr.common.entity.npc.ExtendedIFarmhand;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

/**
 * Holds and manages extended data for NPC farmhands, including their farming level, 
 * experience points, harvest statistics, and dynamic inventory storage size.
 * <p>
 * This class implements {@link ExtendedNpcDataInterface} and handles data ticking, 
 * NBT serialization/deserialization, and client-server network synchronization.
 * </p>
 */
public class ExtendedNpcFarmhandInfoHolder implements ExtendedNpcDataInterface {
	/** The base minimum number of slots available in a farmhand's inventory. */
	public static final int MINIMUM_STORAGE_SIZE = 4;
	/** The NBT key used to store and retrieve the farmhand's item inventory list. */
	public static final String FARMHANDITEMSLIST = "farmItems";
	/** The NPC entity associated with this data holder. */
	private final NPCEntity thisEntity;
	/** The current farming level of the NPC. Defaults to 1. */
	private int farmerLevel = 1;
	/** The experience points accumulated toward the next farming level. */
	private int currentFarmXP = 0;
	/** 
	 * The cumulative total farming experience points earned. 
	 * Only present on the server side for internal tracking and level recalculation.
	 */
	private int totalFarmXP = 0;
	/** The amount of experience points required to reach the next farming level. Defaults to 40. */
	private int maxFarmXP = 40;
	/** The total number of crops harvested by this farmhand. */
	private int cropsHarvested = 0;
	/** Additional inventory slots unlocked via leveling up. Bounds are capped between 0 and 12. */
	private int inventoryAdditionalSize = 0;
	/** The current inventory instance allocated to this farmhand. */
	private Inventory farmhandInventory = makeFarmhandInventory();
	/** Flag indicating if updated farmhand data needs to be synchronized to tracking clients. */
	private boolean resendData = false;
	/**
	 * Constructs a new farmhand data holder for the specified NPC entity.
	 *
	 * @param thisEntity The {@link NPCEntity} instance to bind this data to.
	 */
	public ExtendedNpcFarmhandInfoHolder(NPCEntity thisEntity) {
		this.thisEntity = thisEntity;
	}
	/**
	 * Handles per-tick logical updates on the server. If data changes have been marked 
	 * via {@link #markDirty()}, it synchronizes the current state to all tracking entities.
	 */
	@Override
	public void tick() {
		if (!thisEntity.level.isClientSide) {
			if (resendData) {
				sendDataToAllBeings();
				resendData = false;
			}
		}
	}
	/**
	 * Marks the data holder as dirty, queuing a data synchronization packet to be 
	 * sent during the next tick cycle.
	 */
	@Override
	public void markDirty() {
		resendData = true;
	}
	/**
	 * Deserializes the farmhand's statistics and inventory data from an NBT compound.
	 *
	 * @param nbt The {@link CompoundNBT} tag to read from.
	 */
	@Override
	public void loadFromNbt(CompoundNBT nbt) {
		if(nbt.contains("FarmhandInfo")) {
			CompoundNBT holder = nbt.getCompound("FarmhandInfo");
			if (holder.contains("level")) {
				setFarmerLevel(holder.getInt("level"));
			}
			if (holder.contains("c_xp")) {
				setCurrentFarmerXp(holder.getInt("c_xp"));
			}
			if (holder.contains("t_xp")) {
				setTotalFarmerXp(holder.getInt("t_xp"));
			}
			if (holder.contains("m_xp")) {
				setMaxFarmerXp(holder.getInt("m_xp"));
			}
			if (holder.contains("crops_h")) {
				setCropsHarvested(holder.getInt("crops_h"));
			}
			if (holder.contains("size_add")) {
				setInventoryAdditionalSize(holder.getInt("size_add"));
			}
			farmhandInventory = makeFarmhandInventory();
			ExtendedNBTUtils.readInventoryFromNBT(nbt, farmhandInventory, FARMHANDITEMSLIST);
		}
	}
	/**
	 * Serializes the farmhand's statistics and inventory data into an NBT compound.
	 *
	 * @param nbt The {@link CompoundNBT} tag to write into.
	 */
	@Override
	public void writeToNbt(CompoundNBT nbt) {
		CompoundNBT holder = new CompoundNBT();
		holder.putInt("level", getFarmerLevel());
		holder.putInt("c_xp", getCurrentFarmerXp());
		holder.putInt("t_xp", getTotalFarmerXp());
		holder.putInt("m_xp", getMaxFarmerXp());
		holder.putInt("crops_h", getCropsHarvested());
		holder.putInt("size_add", getInventoryAdditionalSize());
		ExtendedNBTUtils.writeInventoryToNBT(nbt, farmhandInventory, FARMHANDITEMSLIST);
		nbt.put("FarmhandInfo", holder);
	}
	/**
	 * Determines whether this data holder's data should be loaded for the bound entity.
	 *
	 * @return {@code true} if the bound entity implements {@link ExtendedIFarmhand}, otherwise {@code false}.
	 */
	@Override
	public boolean shouldLoad() {
		return thisEntity instanceof ExtendedIFarmhand;
	}
	/**
	 * Determines whether this data holder should execute tick updates.
	 *
	 * @return {@code true} if the bound entity implements {@link ExtendedIFarmhand}, otherwise {@code false}.
	 */
	@Override
	public boolean shouldTick() {
		return thisEntity instanceof ExtendedIFarmhand;
	}
	/**
	 * Writes the core farmhand data to a network packet buffer to be sent to clients.
	 * Used primarily for updating client GUIs.
	 *
	 * @param buf The {@link PacketBuffer} to write data into.
	 * @return The modified {@link PacketBuffer} containing the entity ID and stats.
	 */
	@Override
	public PacketBuffer createDataPacketToClient(PacketBuffer buf) { 
		buf.writeVarInt(thisEntity.getId());
		buf.writeVarInt(getFarmerLevel());
		buf.writeVarInt(getCurrentFarmerXp());
		buf.writeVarInt(getMaxFarmerXp());
		buf.writeVarInt(getCropsHarvested());
		buf.writeVarInt(getInventoryAdditionalSize());
		return buf;
	}
	/**
	 * Reads farmhand data from a incoming network packet buffer on the client side 
	 * and applies it to the targeted entity's holder.
	 *
	 * @param buf   The incoming {@link PacketBuffer}.
	 * @param world The client {@link World} instance used to find the target entity.
	 */
	public static void readDataPacketFromServer(PacketBuffer buf, World world) {
		int entityId = buf.readVarInt();
		Entity entity = world.getEntity(entityId);
		if (entity instanceof NPCEntity) {
			ExtendedNpcFarmhandInfoHolder farmhandInfo = ((ExtendedINPCEntity) entity).getFarmhandInfo();
			if(farmhandInfo!=null) {
				farmhandInfo.setFarmerLevel(buf.readInt());
				farmhandInfo.setCurrentFarmerXp(buf.readInt());
				farmhandInfo.setMaxFarmerXp(buf.readInt());
				farmhandInfo.setCropsHarvested(buf.readInt());
				farmhandInfo.setInventoryAdditionalSize(buf.readInt());
			}
		}
	}
	/**
	 * Syncs the farmhand data directly to a single specific player.
	 *
	 * @param player The {@link ServerPlayerEntity} to receive the packet.
	 */
	@Override
	public void sendDataToPlayer(ServerPlayerEntity player) {
		ExtendedPacketHandler.sendToClient(new ExtendedSNpcFarmhandInfoPacket(this), player);
	}
	/**
	 * Syncs the farmhand data to all players tracking this entity.
	 */
	@Override
	public void sendDataToAllBeings() {
		ExtendedPacketHandler.sendToAllTrackingEntity(new ExtendedSNpcFarmhandInfoPacket(this), thisEntity);
	}
	/**
	 * Directly sets the farmer level. Private to restrict external tampering.
	 * 
	 * @param newLevel The new level to set.
	 */
	private void setFarmerLevel(int newLevel) {
		this.farmerLevel = newLevel;
	}
	/**
	 * Gets the current farming level of the NPC.
	 *
	 * @return The level value.
	 */
	public int getFarmerLevel() {
		return farmerLevel;
	}
	/**
	 * Sets the total lifetime experience points earned. Private to enforce proper tracking.
	 * 
	 * @param newTotalFarmXp The new total experience count.
	 */
	private void setTotalFarmerXp(int newTotalFarmXp) {
		this.totalFarmXP = newTotalFarmXp;
	}
	/**
	 * Gets the total lifetime experience points earned by the farmhand.
	 *
	 * @return Total accumulated XP.
	 */
	public int getTotalFarmerXp() {
		return totalFarmXP;
	}
	/**
	 * Sets the current level progression experience points. Private to protect level up logic bounds.
	 * 
	 * @param newCurrentFarmXp The current level experience value.
	 */
	private void setCurrentFarmerXp(int newCurrentFarmXp) {
		this.currentFarmXP = newCurrentFarmXp;
	}
	/**
	 * Gets the experience points earned within the current level toward the next.
	 *
	 * @return Current level progression XP.
	 */
	public int getCurrentFarmerXp() {
		return currentFarmXP;
	}
	/**
	 * Sets the required experience ceiling for the current level.
	 * 
	 * @param newMaxFarmXp The dynamic max XP limit.
	 */
	private void setMaxFarmerXp(int newMaxFarmXp) {
		this.maxFarmXP = newMaxFarmXp;
	}
	/**
	 * Gets the experience point threshold required to advance past the current level.
	 *
	 * @return Max XP needed for the current level.
	 */
	public int getMaxFarmerXp() {
		return maxFarmXP;
	}
	/**
	 * Increases the statistics of the farmhand when they successfully perform a harvest.
	 * <p>
	 * This method increments total harvests, updates current and cumulative experience, 
	 * handles loop evaluation for level-ups, updates dynamically scaled experience limits, 
	 * and expands the companion inventory size proportionately.
	 * </p>
	 *
	 * @param additionalCropsHarvested The number of physical crops harvested in this transaction.
	 * @param cropPoints               The amount of experience points granted by this action.
	 */
	public void addCropsHarvested(int additionalCropsHarvested, int cropPoints) {
		this.cropsHarvested += additionalCropsHarvested;
		this.totalFarmXP += cropPoints;
		this.currentFarmXP += cropPoints;
		while (this.currentFarmXP >= this.maxFarmXP) {
			this.currentFarmXP -= this.maxFarmXP;
			setFarmerLevel(this.farmerLevel + 1);
			setMaxFarmerXp(calculateMaxXpForLevel(this.farmerLevel));
			// Scales slots so the 12th slot unlocks EXACTLY at Level 100
			int earnedSlots = (this.farmerLevel - 1) * 12 / 99;
			setInventoryAdditionalSize(earnedSlots);
		}
	}
	/**
	 * Calculates the dynamic max experience ceiling for a specified farming level.
	 * Utilizes an exponential curve configuration.
	 *
	 * @param level The target level to evaluate.
	 * @return The required total experience points to complete the level.
	 */
	private int calculateMaxXpForLevel(int level) {
		// 1.04567 ensures Level 1 to 100 takes exactly 72,000 crops (100 hours at 5s/crop)
		return (int) Math.round(40 * Math.pow(1.04567, level - 1));
	}
	/**
	 * Updates the raw count of total harvested crops.
	 * 
	 * @param newCropsHarvested The total crops harvested count.
	 */
	private void setCropsHarvested(int newCropsHarvested) {
		this.cropsHarvested = newCropsHarvested;
	}
	/**
	 * Gets the historical record of total crops harvested by this entity.
	 *
	 * @return Total harvested crop count.
	 */
	public int getCropsHarvested() {
		return cropsHarvested;
	}
	/**
	 * Sets the additional inventory slots earned through level milestones. 
	 * The inputs are strictly clamped between a range of 0 and 12 slots.
	 *
	 * @param newInventorySize The target addition amount.
	 */
	private void setInventoryAdditionalSize(int newInventorySize) {
		this.inventoryAdditionalSize = Math.max(0, Math.min(12, newInventorySize));
	}
	/**
	 * Gets the current amount of bonus slots allocated to this farmhand due to level progression.
	 *
	 * @return Additional slot count.
	 */
	public int getInventoryAdditionalSize() {
		return inventoryAdditionalSize;
	}
	/**
	 * Instantiates a new Minecraft {@link Inventory} object sized according to the 
	 * combination of base storage size and current unlocked level bonus slots.
	 *
	 * @return A new ready-for-allocation {@link Inventory} container.
	 */
	public Inventory makeFarmhandInventory() {
		return new Inventory(MINIMUM_STORAGE_SIZE+inventoryAdditionalSize);
	}
	/**
	 * Gets the active item storage inventory instance bound to this farmhand.
	 *
	 * @return The current {@link Inventory}.
	 */
	public Inventory getFarmhandInventory() {
		return farmhandInventory;
	}
}
