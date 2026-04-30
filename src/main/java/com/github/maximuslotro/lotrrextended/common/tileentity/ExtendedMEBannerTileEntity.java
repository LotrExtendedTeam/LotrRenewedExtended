package com.github.maximuslotro.lotrrextended.common.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileBase;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileFellowship;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfilePlayer;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerProtectionLevels;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerProtectionOwners;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenChangedScreenPacket;
import com.github.maximuslotro.lotrrextended.common.utils.AxisAlignedBBUtils;

import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.fac.FactionPointer;
import lotr.common.fac.FactionPointers;
import lotr.common.fellowship.ExtendedFellowship;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedTileEntities;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

/**
 * Tile entity for the Extended ME Banner.
 *
 * <p>This tile entity handles banner ownership, protection logic, permissions,
 * and alignment-based behavior. It also tracks placement metadata and lifecycle state.</p>
 */
public class ExtendedMEBannerTileEntity extends TileEntity implements ITickableTileEntity {
	/**
     * Whether this banner is the "active half" of a the two-block high banner instance.
     */
	private boolean isActiveHalf = false;
	/**
     * Whitelist of players/entities allowed to interact with this banner
     * regardless of other protection rules.
     */
	private final List<ProtectionEntry> protectionWhitelist = new ArrayList<>();
	/**
     * Alignment level used for faction-based protection logic.
     * Default value is {@code 1}.
     */
	private float alignmentLevel = 1;
	/**
     * Defines who owns this banner for protection purposes.
     * Defaults to {@link ExtendedBannerProtectionOwners#FACTION}.
     */
	private ExtendedBannerProtectionOwners protectionType = ExtendedBannerProtectionOwners.FACTION;
	/**
     * Override permissions superseding any other set permission
     */
	private final List<ExtendedBannerPermissions> globalPermsOverrides = new ArrayList<>();
	/**
     * Faction mode permissions
     */
	private final List<ExtendedBannerPermissions> factionPerms = new ArrayList<>();
	/**
     * Determines whether the banner should protect itself from being destroyed
     * by unauthorized players/entities.
     */
	private boolean canProtectSelf = true;
	/**
     * Whether this banner has already been used and should attach NBT
     * data to dropped items.
     */
	private boolean used = false;
	/**
     * UUID of the player who placed this banner.
     */
	private UUID placedPlayerUUID = null;
	/**
     * Name of the player who placed this banner.
     */
	private String placedPlayerName = null;
	/**
     * UUID of an entity currently connected or linked to this banner.
     */
	private UUID connectedEntity = null;
	/**
     * Current protection level applied to this banner.
     */
	private ExtendedBannerProtectionLevels protectionLevel = ExtendedBannerProtectionLevels.NONE;
	/**
     * Tracks whether this is the first tick since the tile entity was loaded.
     */
	private boolean firstTick = false;
	/**
     * Name of the player who placed this banner.
     */
	private String protectionOverrideMessage = "";
	/**
     * Constructs a new {@code ExtendedMEBannerTileEntity} and registers it
     * with the standing banner tile entity type.
     */
	public ExtendedMEBannerTileEntity() {
		super(ExtendedTileEntities.BANNER_STANDING.get());
	}

	@Override
	public void tick() {
		isActiveHalf = this.getBlockState().getValue(ExtendedMEBannerBlock.HALF) == DoubleBlockHalf.LOWER;
		if(isActiveHalf) {
			setProtectionLevel(getProtectionLevelFromBelowBlock(this.level, this.getBlockPos().below()));
			if(isProtecting()) {
				if(!firstTick && this.level!=null && !this.level.isClientSide()) {
					ServerPlayerEntity player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(placedPlayerUUID);
					if(player!=null) {
						ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger(player, "bannerPlaced");
						if(protectionType == ExtendedBannerProtectionOwners.PLAYER) {
							ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger(player, "bannerSelf");
						}else if(protectionType == ExtendedBannerProtectionOwners.FACTION) {
							ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger(player, "bannerFaction");
						}
						if(!protectionWhitelist.isEmpty()) {
							ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger(player, "bannerOthers");
						}
					}
					firstTick = true;
					markAndUpdate();
				}
				if(connectedEntity==null) {
					createAndSetBannerEntity();
				}else {
					AxisAlignedBB checkBox = AxisAlignedBBUtils.makeCroppedBB(this.getBlockPos());
					Predicate<ExtendedBannerEntity> predicate = (bannerEntity) -> bannerEntity.getUUID().equals(connectedEntity);
					List<ExtendedBannerEntity> banners = level.getEntitiesOfClass(ExtendedBannerEntity.class, checkBox, predicate);
					if(banners.size() == 0) {
						createAndSetBannerEntity();
					}else if(banners.size()>1){
						for(ExtendedBannerEntity banner : banners) {
							banner.remove();
						}
						createAndSetBannerEntity();
					}
				}
			}
		}
	}

	public static ExtendedBannerProtectionLevels getProtectionLevelFromBelowBlock(World level, BlockPos pos) {
		BlockState belowBlockstate = level.getBlockState(pos);
		if(belowBlockstate.is(Blocks.GOLD_BLOCK)) {
			return ExtendedBannerProtectionLevels.GOLD;
		}else if(belowBlockstate.is(LOTRBlocks.SILVER_BLOCK.get())) {
			return ExtendedBannerProtectionLevels.SILVER;
		}else if(belowBlockstate.is(LOTRBlocks.BRONZE_BLOCK.get())) {
			return ExtendedBannerProtectionLevels.BRONZE;
		}else if(belowBlockstate.is(LOTRBlocks.COPPER_BLOCK.get())) {
			return ExtendedBannerProtectionLevels.COPPER;
		}else {
			return ExtendedBannerProtectionLevels.NONE;
		}
	}

	/**
	 * Creates a new ExtendedBannerEntity for this banner
	 * */
	private void createAndSetBannerEntity() {
		if(level!=null && !level.isClientSide) {
			EntityType<ExtendedBannerEntity> newBannerEntity = ExtendedEntities.BANNER_HOLDER.get();
			ExtendedBannerEntity banner = newBannerEntity.create(level);
			if(banner!=null) {
				banner.moveTo(this.getBlockPos().offset(0.5D, 0, 0.5D), 0.0f, 0.0F);
				level.addFreshEntity(banner);
				connectedEntity = banner.getUUID();
			}
		}
	}

	/**
	 * Checks if this instance of a banner is the "main" banner block
	 * Not a top block or a handling block
	 * @return boolean
	 * */
	public boolean isActiveHalf() {
		return isActiveHalf;
	}

	/**
	 * Checks if this banner is actively protecting
	 * @return boolean
	 * */
	public boolean isProtecting() {
		return protectionLevel.getProtectionRadius()>0;
	}

	/**
	 * Get banner protection radius
	 * @return int
	 * */
	public int getProtectionRadius() {
		return protectionLevel.getProtectionRadius();
	}

	/**
	 * Gets the banner's protection level
	 * @return BannerProtectionLevel
	 * */
	public ExtendedBannerProtectionLevels getProtectionLevel() {
		return protectionLevel;
	}

	/**
	 * Sets the banner's protection level
	 * @param protectionLevel {@link ExtendedBannerProtectionLevels} to be set
	 * */
	public void setProtectionLevel(ExtendedBannerProtectionLevels protectionLevel) {
		this.protectionLevel = protectionLevel;
		if(protectionLevel.getProtectionRadius()>0) {
			used = true;
		}
	}

	/**
	 * Sets the banner protection type
	 * @param protectionType {@link ExtendedBannerProtectionOwners} to be set
	 * */
	public void setProtectionType(ExtendedBannerProtectionOwners protectionType) {
		this.protectionType = protectionType;
	}

	/**
	 * Gets the actual banner protection type
	 * @return BannerProtectionType
	 * */
	public ExtendedBannerProtectionOwners getProtectionType() {
		return protectionType;
	}

	/**
	 * Checks if this banner is protecting by faction
	 * @return boolean
	 * */
	public boolean isFactionProtecting() {
		return protectionType==ExtendedBannerProtectionOwners.FACTION;
	}

	/**
	 * Checks if this banner is protecting by whitelist
	 * @return boolean
	 * */
	public boolean isWhitelistProtecting() {
		return protectionType==ExtendedBannerProtectionOwners.PLAYER;
	}
	/**
	 * Checks if this banner is protecting by the server
	 * @return boolean
	 * */
	public boolean isCustomProtecting() {
		return protectionType==ExtendedBannerProtectionOwners.CUSTOM;
	}
	/**
	 * Checks if this banner is protecting by faction
	 * @return boolean
	 * */
	public FactionPointer getFactionProtcting() {
		Block abstractBlock = this.getBlockState().getBlock();
		if(abstractBlock instanceof ExtendedMEBannerBlock) {
			return ((ExtendedMEBannerBlock)abstractBlock).getBanner().getFaction();
		}
		return FactionPointers.UNALIGNED;
	}

	/**
	 * Gets Faction protecting alignment level
	 * @return integer
	 * */
	public float getAlignmentLevel() {
		return alignmentLevel;
	}

	/**
	 * Sets the Faction protecting alignment level
	 * @param level {@link Float} alignment level to be set
	 * */
	public void setAlignmentLevel(float level) {
		this.alignmentLevel = level;
	}

	/**
	 * Gets ID of currently paired banner entity
	 * @return UUID
	 * */
	public UUID getEntityID() {
		return connectedEntity;
	}

	/**
	 * Gets ID of player who placed the banner
	 * @return UUID
	 * */
	public UUID getPlacedPlayerUUID() {
		return placedPlayerUUID;
	}

	/**
	 * Gets Name of player who placed the banner
	 * @return UUID
	 * */
	public String getPlacedPlayerName() {
		if(placedPlayerName==null) {
			if(placedPlayerUUID==null) {
				placedPlayerName="UNKNOWN";
				markAndUpdate();
				return placedPlayerName;
			}
            placedPlayerName = UsernameCache.getLastKnownUsername(placedPlayerUUID);
			if(placedPlayerName==null) {
				placedPlayerName="UNKNOWN";
				markAndUpdate();
				return placedPlayerName;
			}
			markAndUpdate();
			ExtendedLog.warn("Fixed missing banner player username!");
		}
		return placedPlayerName;
	}

	public void setPlacedPlayerUUID(UUID placedPlayer) {
		this.placedPlayerUUID = placedPlayer;
	}

	public void setPlacedPlayerName(String placedPlayerName) {
		this.placedPlayerName = placedPlayerName;
	}

	public void setPlacedPlayerNameAndID(UUID placedPlayer, String placedPlayerName) {
		setPlacedPlayerUUID(placedPlayer);
		setPlacedPlayerName(placedPlayerName);
		factionPerms.clear();
		factionPerms.add(ExtendedBannerPermissions.EVERYTHING);
		markAndUpdate();
	}

	/**
	 * Gets a custom protection message if it exists
	 * @return String
	 * */
	public String getProtectionOverrideMessage() {
		return protectionOverrideMessage;
	}

	public void setProtectionOverrideMessage(String pPotectionMessage) {
		this.protectionOverrideMessage = pPotectionMessage;
	}

	public void setGlobalPerms(List<ExtendedBannerPermissions> perms) {
		globalPermsOverrides.clear();
		globalPermsOverrides.addAll(perms);
	}

	public List<ExtendedBannerPermissions> getGlobalPerms() {
		return globalPermsOverrides;
	}

	public boolean hasGlobalPerm(ExtendedBannerPermissions perm) {
		if(perm==ExtendedBannerPermissions.BANNER) {
			return globalPermsOverrides.contains(perm);
		}
		return globalPermsOverrides.contains(ExtendedBannerPermissions.EVERYTHING) || globalPermsOverrides.contains(perm);
	}

	public void setFactionPerms(List<ExtendedBannerPermissions> perms) {
		factionPerms.clear();
		factionPerms.addAll(perms);
	}

	public List<ExtendedBannerPermissions> getFactionPerms() {
		return factionPerms;
	}

	public boolean hasFactionPerm(ExtendedBannerPermissions perm) {
		// Faction mode only allows placed player to edit
		if(perm==ExtendedBannerPermissions.BANNER) {
			return false;
		}
		return factionPerms.contains(ExtendedBannerPermissions.EVERYTHING) || factionPerms.contains(perm);
	}

	public void setProtectionWhitelist(List<ProtectionEntry> whitelist) {
		protectionWhitelist.clear();
		protectionWhitelist.addAll(whitelist);
	}

	public List<ProtectionEntry> getProtectionWhitelist() {
		return protectionWhitelist;
	}

	public void addWhitelistPlayer(PlayerEntity player) {
		protectionWhitelist.add(new ProtectionEntry(new ExtendedBannerProfilePlayer(player.getUUID(), player.getGameProfile().getName())));
	}

	public void addWhitelistPlayerWithPerms(PlayerEntity player, List<ExtendedBannerPermissions> perms) {
		protectionWhitelist.add(new ProtectionEntry(new ExtendedBannerProfilePlayer(player.getUUID(), player.getGameProfile().getName())).setPerms(perms));
	}

	public void addWhitelistPlayer(ExtendedFellowship fellowship) {
		protectionWhitelist.add(new ProtectionEntry(new ExtendedBannerProfileFellowship(fellowship.getFellowshipId())));
	}

	public void addWhitelistPlayerWithPerms(ExtendedFellowship fellowship, List<ExtendedBannerPermissions> perms) {
		protectionWhitelist.add(new ProtectionEntry(new ExtendedBannerProfileFellowship(fellowship.getFellowshipId())).setPerms(perms));
	}

	public boolean isCanProtectSelf() {
		return canProtectSelf;
	}

	public void setCanProtectSelf(boolean canProtectSelf) {
		this.canProtectSelf = canProtectSelf;
	}

	public ExtendedSBannerOpenChangedScreenPacket verifyInfo(ServerPlayerEntity serverPlayer) {
		boolean fixedBroken = false;
		if(alignmentLevel<0) {
			alignmentLevel = 0f;
			fixedBroken = true;
		}
		if(alignmentLevel>10000) {
			alignmentLevel = 10000;
			fixedBroken = true;
		}
		List<ProtectionEntry> toRemove = new ArrayList<>();
		for (ProtectionEntry entry : this.protectionWhitelist) {
			if(entry.getProfile() instanceof ExtendedBannerProfilePlayer) {
				if(entry.getProfile().getId().equals(Util.NIL_UUID)) {
					ServerPlayerEntity player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByName(entry.getProfile().getName());
					if(player!=null) {
						entry.updateProfile(new ExtendedBannerProfilePlayer(player.getUUID(), player.getGameProfile().getName()));
						fixedBroken = true;
					}else {
						boolean foundPlayer = false;
						for(Entry<UUID, String> profile : UsernameCache.getMap().entrySet()) {
							if(profile.getValue().equals(entry.getProfile().getName())) {
								entry.updateProfile(new ExtendedBannerProfilePlayer(profile.getKey(), entry.getProfile().getName()));
								foundPlayer = true;
								fixedBroken = true;
								break;
							}
						}
						if(!foundPlayer) {
							toRemove.add(entry);
						}
					}
				}else {
					// General username updating
					String lastKnownUsername = UsernameCache.getLastKnownUsername(entry.getProfile().getId());
                    if (lastKnownUsername != null && !lastKnownUsername.equals(entry.getProfile().getName())) {
                        entry.updateProfile(new ExtendedBannerProfilePlayer(entry.getProfile().getId(), lastKnownUsername));
                        fixedBroken = true;
                    }
                }
			}else if(entry.getProfile() instanceof ExtendedBannerProfileFellowship) {
				if(entry.getProfile().getId().equals(Util.NIL_UUID) && serverPlayer!=null) {
					ExtendedFellowshipDataModule fellowshipModule = ((ExtendedLOTRPlayerData)LOTRLevelData.getSidedData(serverPlayer)).getFellowshipData();
					if(fellowshipModule.anyMatchingFellowshipNames(entry.getProfile().getName())) {
						ExtendedFellowship fellow = fellowshipModule.getFellowshipByName(entry.getProfile().getName());
						entry.updateProfile(new ExtendedBannerProfileFellowship(fellow.getFellowshipId()));
						fixedBroken = true;
					}else {
						toRemove.add(entry);
					}
				}else {
					if(!entry.getProfile().isValid()){
						toRemove.add(entry);
					}
				}
			}
		}
		if(!toRemove.isEmpty()) {
			protectionWhitelist.removeAll(toRemove);
			fixedBroken = true;
		}
		firstTick=false;
		if(!protectionWhitelist.isEmpty()) {
			ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger(serverPlayer, "bannerOthers");
		}
		if(protectionType==ExtendedBannerProtectionOwners.FACTION && factionPerms.contains(ExtendedBannerPermissions.BANNER)) {
			factionPerms.remove(ExtendedBannerPermissions.BANNER);
			fixedBroken = true;
		}
		if(protectionType!=ExtendedBannerProtectionOwners.CUSTOM && !protectionOverrideMessage.isEmpty()) {
			protectionOverrideMessage = "";
			fixedBroken = true;
		}
		if(protectionType==ExtendedBannerProtectionOwners.CUSTOM && !ServerLifecycleHooks.getCurrentServer().getPlayerList().isOp(serverPlayer.getGameProfile())) {
			protectionType = ExtendedBannerProtectionOwners.FACTION;
			protectionOverrideMessage = "";
			fixedBroken = true;
		}
		markAndUpdate();
		if(fixedBroken) {
			return new ExtendedSBannerOpenChangedScreenPacket(this);
		}
		return null;
	}
	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, -1, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.save(new CompoundNBT());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
		this.load(level.getBlockState(pkt.getPos()), pkt.getTag());
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		super.handleUpdateTag(state, tag);
	}

	@Override
	public void load(BlockState blockState, CompoundNBT pCompound) {
		super.load(blockState, pCompound);
		loadProtectionData(pCompound);
	}

	@Override
	public void setChanged() {
		super.setChanged();
	}

	public void markAndUpdate() {
		this.setChanged();
		if (this.hasLevel()) {
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}

	public void loadProtectionData(CompoundNBT loadableData) {
		if(loadableData.contains("ProtectionDataUnique")) {
			if(loadableData.contains("connectedEntity")) {
				connectedEntity = loadableData.getUUID("connectedEntity");
			}
		}
		if(loadableData.contains("ProtectionData")) {
			CompoundNBT pCompound = loadableData.getCompound("ProtectionData");
			protectionType = ExtendedBannerProtectionOwners.getByID(pCompound.getInt("protectionType"));
			used = true;
			if(pCompound.contains("placedPlayer")) {
				placedPlayerUUID = pCompound.getUUID("placedPlayer");
			}
			if(pCompound.contains("placedPlayerName")) {
				placedPlayerName = pCompound.getString("placedPlayerName");
			}
			if(pCompound.contains("alignmentThreshold")) {
				alignmentLevel = pCompound.getFloat("alignmentThreshold");
			}
			if(pCompound.contains("whitelistEntrys")) {
				ListNBT whitelist = pCompound.getList("whitelistEntrys", 10);
				for (int i = 0; i < whitelist.size(); i++) {
					CompoundNBT node = whitelist.getCompound(i);
					if (node != null) {
						ProtectionEntry entry = ProtectionEntry.fromNBT(node);
						if(entry!=null) {
							protectionWhitelist.add(entry);
						}
					}
				}
			}
			if (pCompound.contains("defaultPerms", 7)) {  // 7 corresponds to a byte array in NBT
				byte[] ordinalValues = pCompound.getByteArray("defaultPerms");
				// Convert each ordinal value back to a BannerPermission enum
				globalPermsOverrides.clear();
				for (byte ordinal : ordinalValues) {
					globalPermsOverrides.add(ExtendedBannerPermissions.values[ordinal]);
				}
				if(globalPermsOverrides.contains(ExtendedBannerPermissions.EVERYTHING)) {
					globalPermsOverrides.removeIf(permission -> permission !=ExtendedBannerPermissions.BANNER && permission !=ExtendedBannerPermissions.EVERYTHING);
				}
			}
			if (pCompound.contains("factionPerms", 7)) {  // 7 corresponds to a byte array in NBT
				byte[] ordinalValues = pCompound.getByteArray("factionPerms");
				// Convert each ordinal value back to a BannerPermission enum
				factionPerms.clear();
				for (byte ordinal : ordinalValues) {
					factionPerms.add(ExtendedBannerPermissions.values[ordinal]);
				}
				if(factionPerms.contains(ExtendedBannerPermissions.EVERYTHING)) {
					factionPerms.removeIf(permission -> permission !=ExtendedBannerPermissions.EVERYTHING);
				}
			}else {
				factionPerms.clear();
				factionPerms.add(ExtendedBannerPermissions.EVERYTHING);
			}
			if(pCompound.contains("protectionSelf")) {
				canProtectSelf = pCompound.getBoolean("protectionSelf");
			}
			if(pCompound.contains("used")) {
				used = pCompound.getBoolean("used");
			}
			if(pCompound.contains("customMessage")) {
				protectionOverrideMessage = pCompound.getString("customMessage");
			}
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		if(connectedEntity!=null) {
			CompoundNBT pUniqueCompound = new CompoundNBT();
			pUniqueCompound.putUUID("connectedEntity", connectedEntity);
			pCompound.put("ProtectionDataUnique", pUniqueCompound);
		}
		saveProtectionData(pCompound);
		return pCompound;
	}

	public CompoundNBT saveProtectionData(CompoundNBT returnCompound) {
		if(used) {
			CompoundNBT pCompound = new CompoundNBT();
			pCompound.putInt("protectionType", protectionType.getId());
			pCompound.putBoolean("used", used);
			if(alignmentLevel!=1) {
				pCompound.putFloat("alignmentThreshold", alignmentLevel);
			}
			if(placedPlayerUUID!=null) {
				pCompound.putUUID("placedPlayer", placedPlayerUUID);
			}
			if(placedPlayerName!=null) {
				pCompound.putString("placedPlayerName", placedPlayerName);
			}
			if(protectionLevel.getProtectionRadius()>0) {
				pCompound.putInt("radius", protectionLevel.getProtectionRadius());
			}
			if(!protectionWhitelist.isEmpty()) {
				ListNBT whitelist = new ListNBT();
				for(ProtectionEntry protectionWWhitelist : protectionWhitelist) {
					whitelist.add(protectionWWhitelist.save());
				}
				pCompound.put("whitelistEntrys", whitelist);
			}
			if (!globalPermsOverrides.isEmpty()) {
				// Initialize a byte array to store the ordinal values of BannerPermission
				byte[] ordinalValues = new byte[globalPermsOverrides.size()];
				// Iterate over the entryPerms list to populate the byte array
				for (int i = 0; i < globalPermsOverrides.size(); i++) {
					// Get the ordinal of the current BannerPermission enum and cast it to byte
					ordinalValues[i] = (byte) globalPermsOverrides.get(i).ordinal();
				}
				// Store the byte array in the CompoundNBT with the key "defaultPerms"
				pCompound.putByteArray("defaultPerms", ordinalValues);
			}
			if (!factionPerms.isEmpty()) {
				// Initialize a byte array to store the ordinal values of BannerPermission
				byte[] ordinalValues = new byte[factionPerms.size()];
				// Iterate over the entryPerms list to populate the byte array
				for (int i = 0; i < factionPerms.size(); i++) {
					// Get the ordinal of the current BannerPermission enum and cast it to byte
					ordinalValues[i] = (byte) factionPerms.get(i).ordinal();
				}
				// Store the byte array in the CompoundNBT with the key "factionPerms"
				pCompound.putByteArray("factionPerms", ordinalValues);
			}
			if(!canProtectSelf) {
				pCompound.putBoolean("protectionSelf", canProtectSelf);
			}
			if(!protectionOverrideMessage.isEmpty()) {
				pCompound.putString("customMessage", protectionOverrideMessage);
			}
			returnCompound.put("ProtectionData", pCompound);
		}
		return returnCompound;
	}

	/**
	 * Gets the copied item, handling if the banner has protection data and has been used
	 * @param bannerType The banner type instance to get item from
	 * @return ItemStack
	 * */
	public ItemStack getItem(ExtendedBannerTypes bannerType) {
		ItemStack itemstack = new ItemStack(bannerType.getBannerItem().get());
		CompoundNBT data = saveProtectionData(new CompoundNBT());
		if(!data.isEmpty()) {
			itemstack.setTag(data);
		}
		return itemstack;
	}


	public static class ProtectionEntry {
		private List<ExtendedBannerPermissions> entryPerms = new ArrayList<>();
		private ExtendedBannerProfileBase profile;

		public ProtectionEntry(ExtendedBannerProfileBase profile) {
			this.profile = profile;
		}

		public ProtectionEntry setPerms(List<ExtendedBannerPermissions> perms) {
			entryPerms = perms;
			return this;
		}

		public ProtectionEntry addPerm(ExtendedBannerPermissions perm) {
			entryPerms.add(perm);
			return this;
		}

		public boolean containsPermissionNode(ExtendedBannerPermissions permission) {
			return entryPerms.contains(permission);
		}

		public boolean hasPermission(ExtendedBannerPermissions permission) {
			if(permission==ExtendedBannerPermissions.BANNER) {
				return containsPermissionNode(permission);
			}
			return entryPerms.contains(ExtendedBannerPermissions.EVERYTHING) || containsPermissionNode(permission);
		}

		public ExtendedBannerProfileBase getProfile() {
			return profile;
		}

		public void updateProfile(ExtendedBannerProfileBase newProfile) {
			profile = newProfile;
		}

		public List<ExtendedBannerPermissions> getEntryPerms() {
			return entryPerms;
		}

		/**
		 * Serializes the ProtectionEntry object into a CompoundNBT structure.
		 *
		 * @return A CompoundNBT object containing the serialized data of this ProtectionEntry.
		 */
		public CompoundNBT save() {
			// Create a new CompoundNBT instance to hold the serialized data
			CompoundNBT data = new CompoundNBT();
			// Check if the entryPerms list is not empty
			if (!entryPerms.isEmpty()) {
				// Initialize a byte array to store the ordinal values of BannerPermission
				byte[] ordinalValues = new byte[entryPerms.size()];
				// Iterate over the entryPerms list to populate the byte array
				for (int i = 0; i < entryPerms.size(); i++) {
					// Get the ordinal of the current BannerPermission enum and cast it to byte
					ordinalValues[i] = (byte) entryPerms.get(i).ordinal();
				}
				// Store the byte array in the CompoundNBT with the key "perms"
				data.putByteArray("perms", ordinalValues);
			}
			// Serialize the ExtendedBannerProfile and add it to the CompoundNBT
			data = profile.writeGameProfile(data);
			// Return the populated CompoundNBT containing all serialized data
			return data;
		}


		public static ProtectionEntry fromNBT(CompoundNBT nbt) {
			// Create an empty list to store BannerPermission
			List<ExtendedBannerPermissions> entryPerms = new ArrayList<>();
			// Read the byte array of ordinal values from NBT, if present
			if (nbt.contains("perms", 7)) {  // 7 corresponds to a byte array in NBT
				byte[] ordinalValues = nbt.getByteArray("perms");
				// Convert each ordinal value back to a BannerPermission enum
				for (byte ordinal : ordinalValues) {
					entryPerms.add(ExtendedBannerPermissions.values[ordinal]);
				}
			}
			if(entryPerms.contains(ExtendedBannerPermissions.EVERYTHING)) {
				entryPerms.removeIf(permission -> permission !=ExtendedBannerPermissions.BANNER && permission !=ExtendedBannerPermissions.EVERYTHING);
			}
			ExtendedBannerProfileBase type;
			if("player".equals(nbt.getString("type"))) {
				type = ExtendedBannerProfilePlayer.readGameProfile(nbt);
			}else if("fellowship".equals(nbt.getString("type"))) {
				type = ExtendedBannerProfileFellowship.readGameProfile(nbt);
			}
			else {
				ExtendedLog.error("Read an unknown ExtendedBannerProfileBase data type!");
				ExtendedLog.error("The following data has been removed from the banner: "+nbt.toString());
				ExtendedLog.error("PLEASE REPORT THIS!");
				return null;
			}
			if(type==null || !type.isValid()) {
				return null;
			}
			// Create the ProtectionEntry object with the user profile
			ProtectionEntry entry = new ProtectionEntry(type);
			// Set the permissions for the entry
			entry.setPerms(entryPerms);
			return entry;
		}
	}
}
