package lotr.common.entity.npc.data;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedEquipmentSlotType;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitEquipmentPool;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfileManager;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitCombatTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedUnitInventory;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSNpcUnitInfoPacket;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.entity.npc.BreeGuardEntity;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.GondorSoldierEntity;
import lotr.common.entity.npc.GundabadOrcEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ExtendedNpcHiredUnitInfoHolder implements ExtendedNpcDataInterface {
	/**
	 * Defines the hired unit inventory version
	 * DNE/0 for 1.7.1&lt;1.7.2
	 * 1 for 1.7.3&lt;1.9.0
	 * 2 for 1.9.1&lt;?.?.?
	 * */
	public static final int UNIT_INVENTORY_VERSION = 2;
	public static final int INVENTORY_SIZE = 29;
	private static final int HEAL_RATE = 80;
	// Unit meta data
	private final NPCEntity thisEntity;
	private ExtendedHiredUnitProfile hiredUnitProfileHolder;
	private ResourceLocation unitProfile = LotrExtendedMod.EMPTY;
	// Entity Settings
	private UUID ownerUUID = null;
	private int unit_level = 1;
	private float current_xp = 0.0f;
	private float max_xp = 1.0f;
	private int kills = 0;
	private int baseHealth = 20;
	private ExtendedUnitOrders unitOrders = ExtendedUnitOrders.STATIONARY;
	private boolean isHired = false;
	private boolean wasHired = false;
	private @Nullable UnitHiredInformation hiringInfomation;
	// Temporary data
	private int lastHealed = 0;
	private boolean resendData = false;
	public boolean isInteracting = false;
	private int currentHealth = -1;
	private ExtendedUnitInventory internalUnitInventory = makeUnitInventory();
	private @Nullable String squad;
	private int guardRadius = 8;

	public ExtendedNpcHiredUnitInfoHolder(NPCEntity thisEntity) {
		this.thisEntity = thisEntity;
	}

	/**
	 * SHOULD ONLY BE CALLED ON THE SERVER, UNIT PROFILES ARE NOT AVAILABLE CLIENT SIDE!
	 * IM LOOKING AT YOU MAX! (MYSELF) (I keep making the mistake)
	 * @return The {@link ExtendedHiredUnitProfile} instance associated with this unit
	 * */
	public ExtendedHiredUnitProfile getUnitProfile() {
		if(hiredUnitProfileHolder!=null) {
			return hiredUnitProfileHolder;
		}
		ExtendedHiredUnitProfile loaded = ExtendedHiredUnitProfileManager.INSTANCE.get(unitProfile);
		if(!loaded.isEmptyUnitType()) {
			return hiredUnitProfileHolder = loaded;
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
			// heal
			if (lastHealed >= HEAL_RATE)  {
				// Ensure that entity is not dead before healing
				if (this.thisEntity.getHealth() < this.thisEntity.getMaxHealth() && this.thisEntity.isAlive()) {
					this.thisEntity.setHealth(this.thisEntity.getHealth() + 1);
					lastHealed = 0;
				}
			} else {
				lastHealed++;
			}
			// Ensure unit base health is updated after level-ups
			checkHealthStat();

			if(isHired && getUnitOwner()!=null && hasHireInfo()) {
				LOTRPlayerData playerData = LOTRLevelData.getSidedData((PlayerEntity) getUnitOwner());
				if((playerData.getAlignmentData().getAlignment(thisEntity.getFaction()) < getHireInfo().getAlignmentRequired())
					|| (getHireInfo().isPledgeRequired() && !playerData.getAlignmentData().isPledgedTo(thisEntity.getFaction()))) {
					relieveUnitFromDuty(true);
				}
			}

			// Handle loading init data or fixing broken data
			if (resendData) {
				sendDataToAllBeings();
				resendData = false;
			}
		}
	}

	public ItemStack getValidOtherFood() {
		for (int i = 0; i < 9; ++i) {
			ItemStack itemstack = this.internalUnitInventory.getItem(i);
			if (itemstack.isEdible()) {
				return itemstack;
			}
		}
		return ItemStack.EMPTY;
	}

	public boolean isNpcActiveDuty() {
		return isHired;
	}

	public boolean isNPCDismissed() {
		return wasHired;
	}

	public boolean isNpcActiveOrDismissed() {
		return isHired || wasHired;
	}

	public boolean setNpcAsHired(PlayerEntity setOwner, ResourceLocation loc, UnitHiredInformation pHiringInfo) {
		this.unitProfile = loc;
		if(getUnitProfile()==null) {
			return false;
		}
		thisEntity.setPersistenceRequired();
		this.setOwnerUUID(setOwner.getUUID());
		isHired = true;
		wasHired = true;
		hiringInfomation = pHiringInfo;
		setupUnitEquipment();
		setOrders(ExtendedUnitOrders.FOLLOW_PLAYER);
		markDirty();
		return true;
	}

	@Override
	public void markDirty() {
		resendData = true;
	}

	@Override
	public void loadFromNbt(CompoundNBT nbt) {
		if(nbt.contains("HiredUnitInfo")) {
			CompoundNBT holder = nbt.getCompound("HiredUnitInfo");
			UUID uuid;
			if (holder.hasUUID("Owner")) {
				uuid = holder.getUUID("Owner");
			} else {
				String ownerString = holder.getString("Owner");
				uuid = PreYggdrasilConverter.convertMobOwnerIfNecessary(thisEntity.getServer(), ownerString);
			}
			// Ensure the unit is marked as was hired, if owner uuid is valid
			if (uuid != null) {
				this.setOwnerUUID(uuid);
				if (holder.contains("is_hired")) {
					this.isHired = holder.getBoolean("is_hired");
				}
				this.wasHired = true;
				this.unitProfile = new ResourceLocation(holder.getString("unit_profile"));
				loadHiredUnitInventory(holder);
				if (holder.contains("xp_level")) {
					this.setUnitLevel(holder.getInt("xp_level"));
				}
				if (holder.contains("current_xp")) {
					this.setCurrentXp(holder.getInt("current_xp"));
				}
				if (holder.contains("max_xp")) {
					this.setMaxXp(holder.getInt("max_xp"));
				}
				if (holder.contains("mob_kills")) {
					this.setMobKills(holder.getInt("mob_kills"));
				}
				if (holder.contains("base_health")) {
					this.setBaseHealth(holder.getInt("base_health"));
				}
				if (holder.contains("current_health")) {
					this.setCurrentHealth(holder.getInt("current_health"));
				}
				if(holder.contains("unit_orders")) {
					this.unitOrders = ExtendedUnitOrders.getByUnchangingIndex(holder.getInt("unit_orders"));
				}
				if(holder.contains("hire_info")) {
					this.hiringInfomation = UnitHiredInformation.loadFromNBT(holder.getCompound("hire_info"));
				}
				if(holder.contains("squad")) {
					this.squad = holder.getString("squad");
				}
				if(holder.contains("gradius")) {
					this.setGuardRadius(holder.getInt("gradius"));
				}
				markDirty();
			}else {
				this.isHired = false;
				this.wasHired = false;
			}
		}
		// Handle pre-1.8.0 hired unit data, if these three fields are present, assume its hired unit data then validate if it has an owner
		else if(nbt.contains("xp_level") && nbt.contains("current_xp") && nbt.contains("max_xp")) {
			UUID uuid;
			if (nbt.hasUUID("Owner")) {
				uuid = nbt.getUUID("Owner");
			} else {
				String ownerString = nbt.getString("Owner");
				uuid = PreYggdrasilConverter.convertMobOwnerIfNecessary(thisEntity.getServer(), ownerString);
			}
			// Only load old unit info if this IS a hired unit (has an owner)
			if(uuid!=null) {
				this.setOwnerUUID(uuid);
				// Handle profiles of pre-1.8.0 hired units
				if(thisEntity instanceof BreeGuardEntity) {
					this.unitProfile = ExtendedHiredUnitTypes.BREE_GUARD;
				}else if(thisEntity instanceof ExtendedDunedainRangerEntity) {
					this.unitProfile = ExtendedHiredUnitTypes.DUNEDAIN_RANGER;
				}else if(thisEntity instanceof GundabadOrcEntity) {
					this.unitProfile = ExtendedHiredUnitTypes.GUNDABAD_ORC;
				}else if(thisEntity instanceof GondorSoldierEntity) {
					// Handle migrating lotr-companions entity
					this.unitProfile = new ResourceLocation("lotrcompanions", "gondor_soldier");
				}
				// Load after determining profile for legacy unit 
				loadHiredUnitInventory(nbt);
				// Handle old units entities only being able to be in a hired state
				isHired = true;
				wasHired = true;
				// Load other values like normal
				if (nbt.contains("xp_level")) {
					this.setUnitLevel(nbt.getInt("xp_level"));
				}
				if (nbt.contains("current_xp")) {
					this.setCurrentXp(nbt.getInt("current_xp"));
				}
				if (nbt.contains("max_xp")) {
					this.setMaxXp(nbt.getInt("max_xp"));
				}
				if (nbt.contains("mob_kills")) {
					this.setMobKills(nbt.getInt("mob_kills"));
				}
				if (nbt.contains("base_health")) {
					this.setBaseHealth(nbt.getInt("base_health"));
				}
				if(nbt.contains("unit_orders")) {
					unitOrders = ExtendedUnitOrders.getByUnchangingIndex(nbt.getInt("unit_orders"));
				}else {
					// Ensure update compatibility from versions 1.7.1->1.7.2
					if (nbt.contains("following")&&nbt.getBoolean("following")) {
						unitOrders = ExtendedUnitOrders.FOLLOW_PLAYER;
					}else {
						unitOrders = ExtendedUnitOrders.STATIONARY;
					}
				}
				markDirty();
			}
		}
		// Load for not unit any time
		else {
			this.isHired = false;
			this.wasHired = false;
		}
	}

	@Override
	public void writeToNbt(CompoundNBT nbt) {
		CompoundNBT holder = new CompoundNBT();
		if (this.getOwnerUUID() != null) {
			holder.putUUID("Owner", this.getOwnerUUID());
			holder.putString("unit_profile", this.unitProfile.toString());
			saveHiredUnitInventory(holder);
			holder.putInt("unit_orders", this.getOrders().getUnchangingIndex());
			holder.putInt("mob_kills", this.getMobKills());
			holder.putInt("xp_level", this.getUnitLevel());
			holder.putFloat("current_xp", this.getCurrentXp());
			holder.putFloat("max_xp", this.getMaxXp());
			holder.putInt("base_health", this.getBaseHealth());
			holder.putBoolean("is_hired", isHired);
			holder.putInt("current_health", (int) this.thisEntity.getHealth());
			if(hiringInfomation!=null) {
				holder.put("hire_info", hiringInfomation.saveToNBT());
			}
			if(squad!=null && !squad.isEmpty()) {
				holder.putString("squad", squad);
			}
			holder.putInt("gradius", guardRadius);
		}
		nbt.put("HiredUnitInfo", holder);
	}

	/**
	 * Enable to allow loading hired status from nbt
	 * */
	@Override
	public boolean shouldLoad() {
		return true;
	}
	/**
	 * Enable hired unit ticks and data when active
	 * Also enable tick if marked as dismissed to allow one final tick to sync dismiss data
	 * */
	@Override
	public boolean shouldTick() {
		return isNpcActiveDuty() || resendData;
	}

	private void setHired(boolean isHired) {
		this.isHired = isHired;
	}

	private void setWasHired(boolean wasHired) {
		this.wasHired = wasHired;
	}

	/**
	 * Sync unit data for client GUI
	 * */
	@Override
	public PacketBuffer createDataPacketToClient(PacketBuffer buf) { 
		buf.writeVarInt(thisEntity.getId());
		boolean hasOwner = (this.ownerUUID != null);
		buf.writeBoolean(hasOwner);
		if (hasOwner) {
			buf.writeUUID(this.ownerUUID);
		}
		buf.writeBoolean(isHired);
		buf.writeBoolean(wasHired);

		return buf;
	}

	/**
	 * Sync unit data for client GUI
	 * */
	@Override
	public void sendDataToPlayer(ServerPlayerEntity player) {
		ExtendedSNpcUnitInfoPacket packet = new ExtendedSNpcUnitInfoPacket(this);
		ExtendedPacketHandler.sendToClient(packet, player);
	}

	/**
	 * Sync unit data for client GUI
	 * */
	@Override
	public void sendDataToAllBeings() {
		ExtendedSNpcUnitInfoPacket packet = new ExtendedSNpcUnitInfoPacket(this);
		ExtendedPacketHandler.sendToAllTrackingEntity(packet, thisEntity);
	}

	/**
	 * Handles data packets from the server to synchronize NPC hired unit data on the client.
	 *
	 * <p>This method reads an incoming {@link PacketBuffer} containing updated data for an {@link NPCEntity}
	 * and applies the data to the client's local entity if it exists and is of the correct type.
	 * Synchronizes ownership, hire status, level, experience, and mob kill counts for hired NPC units.</p>
	 *
	 * @param buf the {@link PacketBuffer} containing the server data
	 * @param world the client {@link World} in which to locate and update the entity
	 *
	 * @see PacketBuffer
	 * @see NPCEntity
	 * @see ExtendedNpcHiredUnitInfoHolder
	 */
	public static void readDataPacketFromServer(PacketBuffer buf, World world) {
		int entityId = buf.readVarInt();
		Entity entity = world.getEntity(entityId);
		if (entity instanceof NPCEntity) {
			ExtendedNpcHiredUnitInfoHolder unitInfo = ((ExtendedINPCEntity) entity).getUnitInfo();
			if(unitInfo!=null) {
				boolean hasOwner = buf.readBoolean();
				UUID ownerUUID = hasOwner ? buf.readUUID() : null;
				unitInfo.setOwnerUUID(ownerUUID);
				unitInfo.setHired(buf.readBoolean());
				unitInfo.setWasHired(buf.readBoolean());
			}
		}
	}

	// ######################################### Entity Info
	/**
	 * Should only ever be null if not hired. When hired or dismissed should always be present!
	 * @return {@link UUID} of the owner
	 * */
	@Nullable
	public UUID getOwnerUUID() {
		return ownerUUID;
	}

	/**
	 * Only not null when owner is online, refrain form using.
	 * 
	 * @return {@link LivingEntity} of the owner
	 * */
	@Nullable
	public PlayerEntity getUnitOwner() {
		try {
			UUID uuid = this.getOwnerUUID();
			return uuid == null ? null : thisEntity.level.getPlayerByUUID(uuid);
		} catch (IllegalArgumentException illegalargumentexception) {
			return null;
		}
	}

	public void setOwnerUUID(@Nullable UUID owner) {
		this.ownerUUID = owner;
	}

	public void setHiredUnitHealth(float health) {
		thisEntity.setHealth(health);
	}

	public void setBaseHealth(int health) {
		this.baseHealth = health;
	}

	public void setCurrentHealth(int health) {
		this.currentHealth = health;
	}

	public float getHiredUnitHealth() {
		return this.thisEntity.getHealth();
	}

	public float getHiredUnitMaxHealth() {
		return this.thisEntity.getMaxHealth();
	}

	public int getBaseHealth() {
		return this.baseHealth;
	}

	public void giveExperiencePoints(float points) {
		float newExperience = getCurrentXp() + points;
		if (newExperience >= getMaxXp()) {
			setUnitLevel(getUnitLevel() + 1);
			float difference = newExperience - getMaxXp();
			setCurrentXp(difference);
			setMaxXp(getMaxXp() + 2);
			setHiredUnitHealth(getHiredUnitHealth() + 2);
			setBaseHealth(getBaseHealth() + 2);
			if (getUnitOwner() != null) {
				boolean fancy = ((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) getUnitOwner())).getExtendedMiscData().doHighContrastMessages();
				TextComponent unitName = getHiredUnitNameAndTitle();
				TextComponent newlevel = new StringTextComponent(Integer.toString(getUnitLevel()));
				if(fancy) {
					unitName.withStyle(TextFormatting.AQUA);
					newlevel.withStyle(TextFormatting.LIGHT_PURPLE);
				}
				TranslationTextComponent output = new TranslationTextComponent("chat.lotrextended.hired_unit.levelup.combat", unitName, newlevel);
				if(fancy) {
					output.withStyle(TextFormatting.YELLOW);
				}
				getUnitOwner().sendMessage(output, getOwnerUUID());
			}
		} else {
			setCurrentXp(newExperience);
		}
		markDirty();
	}
	// ######################################### Inventory Management
	public ExtendedUnitInventory makeUnitInventory() {
		// 9 inventory slots + 10 player equipment slots + 10 base equipment slots
		return new ExtendedUnitInventory(INVENTORY_SIZE);
	}

	public ExtendedUnitInventory getInternalUnitInventory() {
		return internalUnitInventory;
	}
	// ######################################### Experience Points
	public void setUnitLevel(int lvl) {
		this.unit_level = lvl;
	}

	public int getUnitLevel() {
		return unit_level;
	}

	public void setCurrentXp(float currentXp) {
		this.current_xp = currentXp;
	}

	public float getCurrentXp() {
		return current_xp;
	}

	public void setMaxXp(float maxXp) {
		this.max_xp = maxXp;
	}

	public float getMaxXp() {
		return max_xp;
	}

	// ######################################### Mob kills
	public void incrementMobKills() {
		this.kills+=1;
	}

	public void setMobKills(int kills) {
		this.kills = kills;;
	}

	public int getMobKills() {
		return kills;
	}

	// ######################################### Orders
	public ExtendedUnitOrders getOrders() {
		return unitOrders;
	}

	protected void setOrders(ExtendedUnitOrders orders) {
		unitOrders = orders;
		if(orders.isHasRadius()) {
			this.getThisEntity().restrictTo(this.getThisEntity().blockPosition(), guardRadius);
		}else {
			this.getThisEntity().restrictTo(BlockPos.ZERO, -1);
		}
	}

	public static boolean canTypeAcceptOrders(ExtendedUnitCombatTypes unitType, ExtendedUnitOrders orders) {
		if(unitType.isFarming() && !orders.isOrderAllowedForFarmhands()) {
			return false;
		}else if(!unitType.isFarming() && orders==ExtendedUnitOrders.FARMING){
			return false;
		}
		return true;
	}

	public void trySetOrders(ExtendedUnitOrders orders) {
		if(canTypeAcceptOrders(getUnitProfile().getUnitType(), orders)) {
			setOrders(orders);
		}
	}
	// ######################################### Hiring

	public boolean hasHireInfo() {
		return hiringInfomation!=null;
	}

	public UnitHiredInformation getHireInfo() {
		return hiringInfomation;
	}

	// ######################################### LOGIC
	public @Nullable String getSquad() {
		return squad;
	}

	public boolean isSquad(@Nullable String pSquad) {
		if(this.squad==null) {
			return pSquad==null || pSquad.isEmpty();
		}else {
			return this.squad.equals(pSquad);
		}
	}

	public void setSquad(@Nullable String pSquad) {
		this.squad = (pSquad==null || pSquad.isEmpty()) ? null : pSquad;
	}

	public int getGuardRadius() {
		return guardRadius;
	}

	public void setGuardRadius(int guardRadius) {
		this.guardRadius = MathHelper.clamp(guardRadius, 1, 64);
		if(this.getThisEntity().hasRestriction()) {
			this.getThisEntity().restrictTo(this.getThisEntity().getRestrictCenter(), guardRadius);
		}
	}

	public void updateSlot(ItemStack stack, ExtendedEquipmentSlotType slot) {
		if (slot == ExtendedEquipmentSlotType.HEAD) {
			internalUnitInventory.setItem(9, stack);
			if(stack.isEmpty()) {
				thisEntity.setItemSlot(EquipmentSlotType.HEAD, internalUnitInventory.getItem(19));
			} else {
				thisEntity.setItemSlot(EquipmentSlotType.HEAD, stack);
			}
		} else if (slot == ExtendedEquipmentSlotType.CHEST) {
			internalUnitInventory.setItem(10, stack);
			if(stack.isEmpty()) {
				thisEntity.setItemSlot(EquipmentSlotType.CHEST, internalUnitInventory.getItem(20));
			} else {
				thisEntity.setItemSlot(EquipmentSlotType.CHEST, stack);
			}
		} else if (slot == ExtendedEquipmentSlotType.LEGS) {
			internalUnitInventory.setItem(11, stack);
			if(stack.isEmpty()) {
				thisEntity.setItemSlot(EquipmentSlotType.LEGS, internalUnitInventory.getItem(21));
			} else {
				thisEntity.setItemSlot(EquipmentSlotType.LEGS, stack);
			}
		} else if (slot == ExtendedEquipmentSlotType.FEET) {
			internalUnitInventory.setItem(12, stack);
			if(stack.isEmpty()) {
				thisEntity.setItemSlot(EquipmentSlotType.FEET, internalUnitInventory.getItem(22));
			} else {
				thisEntity.setItemSlot(EquipmentSlotType.FEET, stack);
			}
		} else if (slot == ExtendedEquipmentSlotType.MELEE) {
			internalUnitInventory.setItem(13, stack);
			if(stack.isEmpty()) {
				thisEntity.getNPCItemsInv().setMeleeWeapon(internalUnitInventory.getItem(23));
				if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()) {
					//internalUnitInventory.setItem(16, ItemStack.EMPTY);
					thisEntity.getNPCItemsInv().setIdleItem(internalUnitInventory.getItem(23));
				}
			} else {
				thisEntity.getNPCItemsInv().setMeleeWeapon(stack);
				if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()) {
					//internalUnitInventory.setItem(16, stack);
					thisEntity.getNPCItemsInv().setIdleItem(stack);
				}
			}
		} else if (slot == ExtendedEquipmentSlotType.RANGED) {
			internalUnitInventory.setItem(14, stack);
			if(stack.isEmpty()) {
				thisEntity.getNPCItemsInv().setRangedWeapon(internalUnitInventory.getItem(24));
				if(getUnitProfile().getEquipmentPools().isSetIdleFromRanged()) {
					//internalUnitInventory.setItem(16, ItemStack.EMPTY);
					//internalUnitInventory.setItem(17, ItemStack.EMPTY);
					thisEntity.getNPCItemsInv().setIdleItem(internalUnitInventory.getItem(24));
					thisEntity.getNPCItemsInv().setIdleItemMounted(internalUnitInventory.getItem(24));
				}
			} else {
				thisEntity.getNPCItemsInv().setRangedWeapon(stack);
				if(getUnitProfile().getEquipmentPools().isSetIdleFromRanged()) {
					//internalUnitInventory.setItem(16, stack);
					//internalUnitInventory.setItem(17, stack);
					thisEntity.getNPCItemsInv().setIdleItem(stack);
					thisEntity.getNPCItemsInv().setIdleItemMounted(stack);
				}
			}
		} else if (slot == ExtendedEquipmentSlotType.MOUNTED) {
			internalUnitInventory.setItem(15, stack);
			if(stack.isEmpty()) {
				thisEntity.getNPCItemsInv().setMeleeWeaponMounted(internalUnitInventory.getItem(25));
				if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()) {
					//internalUnitInventory.setItem(17, ItemStack.EMPTY);
					thisEntity.getNPCItemsInv().setIdleItem(internalUnitInventory.getItem(25));
				}
			} else {
				thisEntity.getNPCItemsInv().setMeleeWeaponMounted(stack);
				if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()) {
					//internalUnitInventory.setItem(17, stack);
					thisEntity.getNPCItemsInv().setIdleItem(stack);
				}
			}
		} else if (slot == ExtendedEquipmentSlotType.IDLE) {
			internalUnitInventory.setItem(16, stack);
			if(stack.isEmpty()) {
				thisEntity.getNPCItemsInv().setIdleItem(internalUnitInventory.getItem(26));
			} else {
				thisEntity.getNPCItemsInv().setIdleItem(stack);
			}
		} else if (slot == ExtendedEquipmentSlotType.IDLE_MOUNTED) {
			internalUnitInventory.setItem(17, stack);
			if(stack.isEmpty()) {
				thisEntity.getNPCItemsInv().setIdleItemMounted(internalUnitInventory.getItem(27));
			}else {
				thisEntity.getNPCItemsInv().setIdleItemMounted(stack);
			}
		} else if (slot == ExtendedEquipmentSlotType.OFFHAND) {
			internalUnitInventory.setItem(18, stack);
			if(stack.isEmpty()) {
				thisEntity.setItemSlot(EquipmentSlotType.OFFHAND, internalUnitInventory.getItem(28));
			} else {
				thisEntity.setItemSlot(EquipmentSlotType.OFFHAND, stack);
			}
		}
		if(slot.isRenewedHandling()) {
			thisEntity.getNPCCombatUpdater().refreshCurrentAttackMode();
		}
	}

	/**
	 * Generated the base equipment for a hired unit from a hired unit equipment pool.
	 * Don't Implement this method, instead call it in your entity finalize spawn method
	 * @see ExtendedHiredUnitEquipmentPool
	 * */
	private void setupUnitEquipment() {
		// set initial entity base equipment based upon pools
		// Also indicates if unit was spawned incorrectly if it spawns with no base equipment
		internalUnitInventory.setItem(19, getUnitProfile().getEquipmentPools().getRandomHelmetStack(thisEntity.getRandom()));
		internalUnitInventory.setItem(20, getUnitProfile().getEquipmentPools().getRandomChestplateStack(thisEntity.getRandom()));
		internalUnitInventory.setItem(21, getUnitProfile().getEquipmentPools().getRandomLeggingsStack(thisEntity.getRandom()));
		internalUnitInventory.setItem(22, getUnitProfile().getEquipmentPools().getRandomBootsStack(thisEntity.getRandom()));
		internalUnitInventory.setItem(23, getUnitProfile().getEquipmentPools().getRandomMeleeStack(thisEntity.getRandom()));
		internalUnitInventory.setItem(24, getUnitProfile().getEquipmentPools().getRandomRangedStack(thisEntity.getRandom()));
		internalUnitInventory.setItem(25, getUnitProfile().getEquipmentPools().getRandomMountedStack(thisEntity.getRandom()));
		internalUnitInventory.setItem(26, getUnitProfile().getEquipmentPools().getRandomIdleStack(thisEntity.getRandom()));
		internalUnitInventory.setItem(27, getUnitProfile().getEquipmentPools().getRandomIdleMountedStack(thisEntity.getRandom()));
		internalUnitInventory.setItem(28, getUnitProfile().getEquipmentPools().getRandomOffhandStack(thisEntity.getRandom()));
		thisEntity.setItemSlot(EquipmentSlotType.HEAD, internalUnitInventory.getItem(19));
		thisEntity.setItemSlot(EquipmentSlotType.CHEST, internalUnitInventory.getItem(20));
		thisEntity.setItemSlot(EquipmentSlotType.LEGS, internalUnitInventory.getItem(21));
		thisEntity.setItemSlot(EquipmentSlotType.FEET, internalUnitInventory.getItem(22));
		thisEntity.getNPCItemsInv().clearContent();
		thisEntity.getNPCItemsInv().setMeleeWeapon(internalUnitInventory.getItem(23));
		thisEntity.getNPCItemsInv().setRangedWeapon(internalUnitInventory.getItem(24));
		thisEntity.getNPCItemsInv().setMeleeWeaponMounted(internalUnitInventory.getItem(25));
		if(getUnitProfile().getEquipmentPools().isSetIdleFromPool()) {
			thisEntity.getNPCItemsInv().setIdleItem(internalUnitInventory.getItem(26));
			thisEntity.getNPCItemsInv().setIdleItemMounted(internalUnitInventory.getItem(27));
		}else if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()){
			internalUnitInventory.setItem(26, internalUnitInventory.getItem(23));
			internalUnitInventory.setItem(27, internalUnitInventory.getItem(25));
			thisEntity.getNPCItemsInv().setIdleItemsFromMeleeWeapons();
		}else {
			internalUnitInventory.setItem(26, internalUnitInventory.getItem(24));
			internalUnitInventory.setItem(27, internalUnitInventory.getItem(24));
			thisEntity.getNPCItemsInv().setIdleItemsFromRangedWeapons();
		}
		thisEntity.setItemSlot(EquipmentSlotType.OFFHAND, internalUnitInventory.getItem(28));
		thisEntity.getNPCCombatUpdater().refreshCurrentAttackMode();
	}

	/**
	 * Loads the hired unit's inventory from nbt, and handles inventory changes between versions
	 * Don't Implement this method, instead call it in your entity load data method
	 * @param tag A CompoundNBT containing the saved inventory
	 * */
	private void loadHiredUnitInventory(CompoundNBT tag) {
		if(tag.contains("HiredUnitInventory")) {
			CompoundNBT unitInventory = tag.getCompound("HiredUnitInventory");
			if(unitInventory.isEmpty()) {
				ExtendedLog.error("Tried loading a Hired Unit Inventory with a empty inventory, dumping nbt to logs and loading default base inventory!");
				ExtendedLog.error(tag.toString());
				setupUnitEquipment();
				return;
			}
			if(unitInventory.getInt("unit_inventory_version")>=1 && unitInventory.getInt("unit_inventory_version")<=UNIT_INVENTORY_VERSION) {
				// 1.7.3 Added ranged, mounted, idle and idle mounted slots
				// 9 inventory slots + 10 player equipment slots + 10 base equipment slots
				NonNullList<ItemStack> itemsStacks = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
				ItemStackHelper.loadAllItems(unitInventory, itemsStacks);
				for (int i = 0; i < INVENTORY_SIZE; i++) {
					ItemStack stack = itemsStacks.get(i);
					// Ensure pre 1.9.0 stacks have modifiers added, as well as if modifiers were enabled since item where given to unit
					if(stack.isDamageableItem() && ExtendedServerConfig.enableExtendedModifiers.get() && !ExtendedModifierUtils.hasRandomModifiers(stack)) {
						ExtendedModifierUtils.generateItemModifiersTagged(thisEntity.random, stack, false, false);
					}
					internalUnitInventory.setItem(i, stack);
				}
				if(unitInventory.getInt("unit_inventory_version")==1) {
					ExtendedLog.info("Loaded Hired Unit Inventory in format [1]. Upgrading inventory to format ["+2+"]");
					// 1.9.1 fixes base items having custom stack names which show in chat by re-setting all base equipment
					internalUnitInventory.setItem(19, getUnitProfile().getEquipmentPools().getRandomHelmetStack(thisEntity.getRandom()));
					internalUnitInventory.setItem(20, getUnitProfile().getEquipmentPools().getRandomChestplateStack(thisEntity.getRandom()));
					internalUnitInventory.setItem(21, getUnitProfile().getEquipmentPools().getRandomLeggingsStack(thisEntity.getRandom()));
					internalUnitInventory.setItem(22, getUnitProfile().getEquipmentPools().getRandomBootsStack(thisEntity.getRandom()));
					internalUnitInventory.setItem(23, getUnitProfile().getEquipmentPools().getRandomMeleeStack(thisEntity.getRandom()));
					internalUnitInventory.setItem(24, getUnitProfile().getEquipmentPools().getRandomRangedStack(thisEntity.getRandom()));
					internalUnitInventory.setItem(25, getUnitProfile().getEquipmentPools().getRandomMountedStack(thisEntity.getRandom()));
					internalUnitInventory.setItem(26, getUnitProfile().getEquipmentPools().getRandomIdleStack(thisEntity.getRandom()));
					internalUnitInventory.setItem(27, getUnitProfile().getEquipmentPools().getRandomIdleMountedStack(thisEntity.getRandom()));
					internalUnitInventory.setItem(28, getUnitProfile().getEquipmentPools().getRandomOffhandStack(thisEntity.getRandom()));
				}
			} else {
				ExtendedLog.error("Tried loading a Hired Unit Inventory with a malformed/unknown version number, dumping nbt to logs and loading default base inventory!");
				ExtendedLog.error(unitInventory.toString());
				setupUnitEquipment();
				return;
			}
		}else {
			// Handle version 1.7.2 and before
			// 1.7.1 can't properly upgrade with logic, just going to dump combined equipment into the player equipment slots
			// 9 inventory slots + 6 combined equipment slots
			// 1.7.2 Added split base and player equipment slots
			// 9 inventory slots + 6 player equipment slots + 6 base equipment slots
			ExtendedLog.info("Loaded Hired Unit Inventory in format [0]. Upgrading inventory to format ["+UNIT_INVENTORY_VERSION+"]");
			NonNullList<ItemStack> itemsStacks = NonNullList.withSize(21, ItemStack.EMPTY);
			ItemStackHelper.loadAllItems(tag, itemsStacks);
			// Load old item slot index into new positions
			// Food
			internalUnitInventory.setItem(0, itemsStacks.get(0));
			internalUnitInventory.setItem(1, itemsStacks.get(1));
			internalUnitInventory.setItem(2, itemsStacks.get(2));
			internalUnitInventory.setItem(3, itemsStacks.get(3));
			internalUnitInventory.setItem(4, itemsStacks.get(4));
			internalUnitInventory.setItem(5, itemsStacks.get(5));
			internalUnitInventory.setItem(6, itemsStacks.get(6));
			internalUnitInventory.setItem(7, itemsStacks.get(7));
			internalUnitInventory.setItem(8, itemsStacks.get(8));
			// Equipment
			internalUnitInventory.setItem(9, itemsStacks.get(9)); //Player Helmet
			internalUnitInventory.setItem(10, itemsStacks.get(10)); //Player Chestplate
			internalUnitInventory.setItem(11, itemsStacks.get(11)); //Player Leggings
			internalUnitInventory.setItem(12, itemsStacks.get(12)); //Player Boots
			internalUnitInventory.setItem(13, itemsStacks.get(13)); //Player Melee
			// Player 14-17 (14:ranged, 15:mounted, 16:idle, 17:idle_mounted empty for player equipment)
			// Catch bows in melee slot from 1.7.2 and before and put in ranged slot
			if(internalUnitInventory.getItem(13).getItem() instanceof BowItem) {
				internalUnitInventory.setItem(14, internalUnitInventory.getItem(13)); //Player Ranged
				internalUnitInventory.setItem(13, ItemStack.EMPTY);
			}
			// Move player offend item to new index
			internalUnitInventory.setItem(18, itemsStacks.get(14)); //Player Offhand
			// Try to catch updating unit inventory from 1.7.1 when base equipment will just be empty
			internalUnitInventory.setItem(19, itemsStacks.get(15).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomHelmetStack(thisEntity.getRandom()) : itemsStacks.get(15));
			internalUnitInventory.setItem(20, itemsStacks.get(16).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomChestplateStack(thisEntity.getRandom()) : itemsStacks.get(16));
			internalUnitInventory.setItem(21, itemsStacks.get(17).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomLeggingsStack(thisEntity.getRandom()) : itemsStacks.get(17));
			internalUnitInventory.setItem(22, itemsStacks.get(18).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomBootsStack(thisEntity.getRandom()) : itemsStacks.get(18));
			internalUnitInventory.setItem(23, itemsStacks.get(19).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomMeleeStack(thisEntity.getRandom()) : itemsStacks.get(19));
			// Catch base bows in base melee slot from 1.7.2 and update
			if(internalUnitInventory.getItem(23).getItem() instanceof BowItem) {
				internalUnitInventory.setItem(23, getUnitProfile().getEquipmentPools().getRandomMeleeStack(thisEntity.getRandom()));
			}
			// Update inventory from 1.7.2 by generating missing fields
			internalUnitInventory.setItem(24, getUnitProfile().getEquipmentPools().getRandomRangedStack(thisEntity.getRandom()));
			internalUnitInventory.setItem(25, getUnitProfile().getEquipmentPools().getRandomMountedStack(thisEntity.getRandom()));
			internalUnitInventory.setItem(26, getUnitProfile().getEquipmentPools().getRandomIdleStack(thisEntity.getRandom()));
			internalUnitInventory.setItem(27, getUnitProfile().getEquipmentPools().getRandomIdleMountedStack(thisEntity.getRandom()));
			// Handle idle item logic
			if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()){
				internalUnitInventory.setItem(26, internalUnitInventory.getItem(23));
				internalUnitInventory.setItem(27, internalUnitInventory.getItem(25));
			}else if(getUnitProfile().getEquipmentPools().isSetIdleFromRanged()){
				internalUnitInventory.setItem(26, internalUnitInventory.getItem(24));
				internalUnitInventory.setItem(27, internalUnitInventory.getItem(24));
			}
			// Finally move base offhand item to new index
			internalUnitInventory.setItem(28, itemsStacks.get(20).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomOffhandStack(thisEntity.getRandom()) : itemsStacks.get(20));
		}
		// Lastly update the entity's equipment slots and renewed item inventory with upgraded inventory layout
		thisEntity.setItemSlot(EquipmentSlotType.HEAD, internalUnitInventory.getItem(9).isEmpty() ? internalUnitInventory.getItem(19) : internalUnitInventory.getItem(9));
		thisEntity.setItemSlot(EquipmentSlotType.CHEST, internalUnitInventory.getItem(10).isEmpty() ? internalUnitInventory.getItem(20) : internalUnitInventory.getItem(10));
		thisEntity.setItemSlot(EquipmentSlotType.LEGS, internalUnitInventory.getItem(11).isEmpty() ? internalUnitInventory.getItem(21) : internalUnitInventory.getItem(11));
		thisEntity.setItemSlot(EquipmentSlotType.FEET, internalUnitInventory.getItem(12).isEmpty() ? internalUnitInventory.getItem(22) : internalUnitInventory.getItem(12));
		thisEntity.getNPCItemsInv().clearContent();
		thisEntity.getNPCItemsInv().setMeleeWeapon(internalUnitInventory.getItem(13).isEmpty() ? internalUnitInventory.getItem(23) : internalUnitInventory.getItem(13));
		thisEntity.getNPCItemsInv().setRangedWeapon(internalUnitInventory.getItem(14).isEmpty() ? internalUnitInventory.getItem(24) : internalUnitInventory.getItem(14));
		thisEntity.getNPCItemsInv().setMeleeWeaponMounted(internalUnitInventory.getItem(15).isEmpty() ? internalUnitInventory.getItem(25) : internalUnitInventory.getItem(15));
		if(!internalUnitInventory.getItem(16).isEmpty()) {
			thisEntity.getNPCItemsInv().setIdleItem(internalUnitInventory.getItem(16));
		}
		else{
			if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()){
				internalUnitInventory.setItem(26, internalUnitInventory.getItem(23));
				thisEntity.getNPCItemsInv().setIdleItem(internalUnitInventory.getItem(13).isEmpty() ? internalUnitInventory.getItem(26) : internalUnitInventory.getItem(13));
			}else if(getUnitProfile().getEquipmentPools().isSetIdleFromRanged()){
				internalUnitInventory.setItem(26, internalUnitInventory.getItem(24));
				thisEntity.getNPCItemsInv().setIdleItem(internalUnitInventory.getItem(14).isEmpty() ? internalUnitInventory.getItem(26) : internalUnitInventory.getItem(14));
			}
		}
		if(!internalUnitInventory.getItem(17).isEmpty()) {
			thisEntity.getNPCItemsInv().setIdleItemMounted(internalUnitInventory.getItem(17));
		}
		else{
			if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()){
				internalUnitInventory.setItem(27, internalUnitInventory.getItem(25));
				thisEntity.getNPCItemsInv().setIdleItemMounted(internalUnitInventory.getItem(15).isEmpty() ? internalUnitInventory.getItem(27) : internalUnitInventory.getItem(15));
			}else if(getUnitProfile().getEquipmentPools().isSetIdleFromRanged()){
				internalUnitInventory.setItem(27, internalUnitInventory.getItem(24));
				thisEntity.getNPCItemsInv().setIdleItemMounted(internalUnitInventory.getItem(14).isEmpty() ? internalUnitInventory.getItem(27) : internalUnitInventory.getItem(14));
			}
		}
		thisEntity.setItemSlot(EquipmentSlotType.OFFHAND, internalUnitInventory.getItem(18).isEmpty() ? internalUnitInventory.getItem(28) : internalUnitInventory.getItem(18));
		thisEntity.getNPCCombatUpdater().refreshCurrentAttackMode();
	}

	/**
	 * Saves a hired unit's inventory to nbt, no need to handle version changes here, since its corrected by the time this is saved
	 * Don't Implement this method, instead call it in your entity save data method
	 * @param tag A CompoundNBT to be saved to
	 * */
	private void saveHiredUnitInventory(CompoundNBT tag) {
		// create temporary NonNullList to save inventory to
		NonNullList<ItemStack> items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
		for (int i = 0; i < INVENTORY_SIZE; i++) {
			ItemStack item = internalUnitInventory.getItem(i);
			items.set(i, item);
		}
		CompoundNBT unitInventory = new CompoundNBT();
		unitInventory.putInt("unit_inventory_version", UNIT_INVENTORY_VERSION);
		ItemStackHelper.saveAllItems(unitInventory, items);
		tag.put("HiredUnitInventory", unitInventory);
	}

	public ITextComponent getHiredUnitName() {
		if(getUnitProfile().stringsAreTranslated()) {
			return new TranslationTextComponent(getUnitProfile().getEntityUnitShorthandName(),
				new StringTextComponent(thisEntity.getPersonalInfo().getName()));
		}
		return new StringTextComponent(getUnitProfile().getEntityUnitShorthandName());
	}

	public TextComponent getHiredUnitNameAndTitle() {
		if(getUnitProfile().stringsAreTranslated()) {
			return new TranslationTextComponent(getUnitProfile().getEntityUnitTitle(),
				new StringTextComponent(thisEntity.getPersonalInfo().getName()), new TranslationTextComponent(getUnitProfile().getEntityUnitTypeName()));
		}
		return new StringTextComponent(getUnitProfile().getEntityUnitShorthandName());
	}

	public void checkHealthStat() {
		if ((int) this.thisEntity.getMaxHealth() != getBaseHealth()) {
			modifyMaxHealth(getBaseHealth() - 20, "Base Health from current level", false);
			// restore their current health and only perform it once
			if (currentHealth > 0) {
				this.thisEntity.setHealth(currentHealth);
				currentHealth = -1;
			}
		}
	}

	private void modifyMaxHealth(int change, String name, boolean permanent) {
		ModifiableAttributeInstance attributeInstance = this.thisEntity.getAttribute(Attributes.MAX_HEALTH);
		Set<AttributeModifier> modifiers = attributeInstance.getModifiers();
		if (!modifiers.isEmpty()) {
			for (AttributeModifier attributeModifier : modifiers) {
				if (attributeModifier != null && attributeModifier.getName().equals(name)) {
					this.thisEntity.getAttribute(Attributes.MAX_HEALTH).removeModifier(attributeModifier);
				}
			}
		}
		AttributeModifier HEALTH_MODIFIER = new AttributeModifier(name, change, AttributeModifier.Operation.ADDITION);
		if (permanent) {
			attributeInstance.addPermanentModifier(HEALTH_MODIFIER);
		} else {
			attributeInstance.addTransientModifier(HEALTH_MODIFIER);
		}
	}
	public void dropCustomDeathLoot(Random random, int looting) {
		// Only try dropping player equipment and general inventory space
		for (int i = 0; i < 19; ++i) {
			if(!getInternalUnitInventory().getItem(i).isEmpty()) {
				ItemStack itemstack = getInternalUnitInventory().getItem(i).copy();
				boolean isEquipedItem = !(itemstack.hasTag() && itemstack.getTag().getBoolean("IsBaseArmor"));
				if(isEquipedItem && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					getThisEntity().spawnAtLocation(itemstack);
					getInternalUnitInventory().setItem(i, ItemStack.EMPTY);
				}
			}
		}
	}

	public void die(DamageSource source) {
		if (!getThisEntity().level.isClientSide && getThisEntity().level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && getUnitOwner() instanceof ServerPlayerEntity) {
			if (((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) getUnitOwner())).getExtendedMiscData().shouldShowHiredUnitDeathMessages()){
				boolean fancy = ((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) getUnitOwner())).getExtendedMiscData().doHighContrastMessages();
				getUnitOwner().sendMessage(getCompanionDeathMessage(source, fancy), getOwnerUUID());
			}
		}
	}

	private TranslationTextComponent getCompanionDeathMessage(DamageSource source, boolean fancy) {
		LivingEntity killerEntity = getThisEntity().getKillCredit();
		String s = "death.attack." + source.msgId;
		TextComponent unitName = getHiredUnitNameAndTitle();
		if(fancy) {
			unitName.withStyle(TextFormatting.AQUA);
		}
		TranslationTextComponent companionName = new TranslationTextComponent("chat.lotrextended.hired_unit.death", unitName);
		if(fancy) {
			companionName.withStyle(TextFormatting.YELLOW);
		}
		IFormattableTextComponent killerName = new StringTextComponent("");
		if (killerEntity != null) {
			if (killerEntity instanceof NPCEntity && ((ExtendedINPCEntity)killerEntity).getUnitInfo().isNpcActiveDuty()) {
				killerName = ((ExtendedINPCEntity)killerEntity).getUnitInfo().getHiredUnitNameAndTitle();
			}
			else {
				if(killerEntity.getDisplayName() instanceof IFormattableTextComponent) {
					killerName = (IFormattableTextComponent) killerEntity.getDisplayName();
				}else {
					killerName = new StringTextComponent(killerEntity.getDisplayName().getString());
				}
			}
			if(fancy) {
				killerName.withStyle(TextFormatting.RED);
			}
		}
		TranslationTextComponent output = killerEntity != null ? new TranslationTextComponent(s, companionName, killerName) : new TranslationTextComponent(s, companionName);
		if(fancy) {
			output.withStyle(TextFormatting.YELLOW);
		}
		return output;
	}

	/**
	 * Gets the parent NPC entity context linked to this data holder instance.
	 *
	 * @return The parent {@link NPCEntity}.
	 */
	public NPCEntity getThisEntity() {
		return thisEntity;
	}

	public void relieveUnitFromDuty(boolean deserted) {
		if(deserted) {
			getUnitOwner().sendMessage(new TranslationTextComponent("chat.lotrextended.hired_unit.desert", getHiredUnitNameAndTitle()), getUnitOwner().getUUID());
		}else {
			getUnitOwner().sendMessage(new TranslationTextComponent("chat.lotrextended.hired_unit.dismissed", getHiredUnitNameAndTitle()), getUnitOwner().getUUID());
		}
		isHired=false;
		setOrders(ExtendedUnitOrders.DISMISSED);
		markDirty();
	}

	public static class UnitHiredInformation {
		private int alignmentRequired;
		private boolean pledgeRequired;

		public UnitHiredInformation(int alignmentRequired, boolean pledgeRequired) {
			this.alignmentRequired = alignmentRequired;
			this.pledgeRequired = pledgeRequired;
		}

		public int getAlignmentRequired() {
			return alignmentRequired;
		}
		public boolean isPledgeRequired() {
			return pledgeRequired;
		}

		public CompoundNBT saveToNBT() {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putInt("align", alignmentRequired);
			nbt.putBoolean("pledged", pledgeRequired);
			int alignmentRequired = nbt.getInt("align");
			boolean pledgeRequired = nbt.getBoolean("pledged");
			return nbt;
		}

		public static UnitHiredInformation loadFromNBT(CompoundNBT nbt) {
			int alignmentRequired = nbt.getInt("align");
			boolean pledgeRequired = nbt.getBoolean("pledged");
			return new UnitHiredInformation(alignmentRequired, pledgeRequired);
		}
	}
}
