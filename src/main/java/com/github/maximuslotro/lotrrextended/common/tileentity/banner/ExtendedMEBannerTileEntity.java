package com.github.maximuslotro.lotrrextended.common.tileentity.banner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileBase;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileFellowship;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfilePlayer;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.github.maximuslotro.lotrrextended.common.enums.BannerPermission;
import com.github.maximuslotro.lotrrextended.common.enums.BannerProtectionLevel;
import com.github.maximuslotro.lotrrextended.common.enums.BannerTypes;

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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class ExtendedMEBannerTileEntity extends TileEntity implements ITickableTileEntity{
	private boolean isActiveHalf = false;
	//Use for user whitelist
	private List<ProtectionEntry> protectionWhitelist = new ArrayList<>();
	//Use for faction alignment level
	private int alignmentLevel = 1;
	//False for faction, true for whitelist
	private boolean protectionType = false;
	//Default perms for everyone in faction mode
	private List<BannerPermission> defaultPerms = new ArrayList<>();
	//Should protect myself from destruction besides faction member or player placed
	private boolean canProtectSelf = true;
	//Should add NBT to dropped items?
	private boolean used = false;
	private UUID placedPlayer;
	private String placedPlayerName;
	private UUID connectedEntity;
	private BannerProtectionLevel protectionLevel = BannerProtectionLevel.NONE;

	public ExtendedMEBannerTileEntity() {
		super(ExtendedTileEntities.BANNER_STANDING.get());
	}

	@Override
	public void tick() {
		isActiveHalf = this.getBlockState().getValue(ExtendedMEBannerBlock.HALF) == DoubleBlockHalf.LOWER;
		if(isActiveHalf) {
			setProtectionLevelFromBelowBlock();
			if(isProtecting()) {
				if(connectedEntity==null) {
					createAndSetBannerEntity();
				}else {
					AxisAlignedBB checkBox = new AxisAlignedBB(this.getBlockPos(), this.getBlockPos().offset(1, 1, 1));
					Predicate<ExtendedBannerEntity> pred = (bannerEntity) -> bannerEntity.getUUID().equals(connectedEntity);
					List<ExtendedBannerEntity> banners = level.getEntitiesOfClass(ExtendedBannerEntity.class, checkBox, pred);
					if(banners.size()<=0) {
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

	private void setProtectionLevelFromBelowBlock() {
		BlockState belowBlockstate = level.getBlockState(this.getBlockPos().below());
		if(belowBlockstate.is(Blocks.GOLD_BLOCK)) {
			setProtectionLevel(BannerProtectionLevel.GOLD);
		}else if(belowBlockstate.is(LOTRBlocks.SILVER_BLOCK.get())) {
			setProtectionLevel(BannerProtectionLevel.SILVER);
		}else if(belowBlockstate.is(LOTRBlocks.BRONZE_BLOCK.get())) {
			setProtectionLevel(BannerProtectionLevel.BRONZE);
		}else {
			setProtectionLevel(BannerProtectionLevel.NONE);
		}
	}

	/**
	 * Creates a new ExtendedBannerEntity for this banner
	 * */
	private void createAndSetBannerEntity() {
		EntityType<ExtendedBannerEntity> newBannerEntity = ExtendedEntities.BANNER_HOLDER.get();
		ExtendedBannerEntity banner = newBannerEntity.create(level);
		if(banner!=null) {
			banner.moveTo(this.getBlockPos().offset(0.5D, 0, 0.5D), 0.0f, 0.0F);
			level.addFreshEntity(banner);
			connectedEntity = banner.getUUID();
		}
	}

	/**
	 * Checks if this instance of a banner is the "main" banner block
	 * Not a top block or a handing block
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
	public BannerProtectionLevel getProtectionLevel() {
		return protectionLevel;
	}

	/**
	 * Sets the banner's protection level
	 * @param BannerProtectionLevel
	 * */
	public void setProtectionLevel(BannerProtectionLevel protectionLevel) {
		this.protectionLevel = protectionLevel;
		if(protectionLevel.getProtectionRadius()>0) {
			used = true;
		}
	}

	/**
	 * Checks if this banner is protecting by faction
	 * @return boolean
	 * */
	public boolean isFactionProtcting() {
		return !protectionType;
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
	public int getAlignmentLevel() {
		return alignmentLevel;
	}

	/**
	 * Checks if this banner is protecting by whitelist
	 * @return boolean
	 * */
	public boolean isWhitelistProtcting() {
		return protectionType;
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
	public UUID getPlacedPlayer() {
		return placedPlayer;
	}

	/**
	 * Gets Name of player who placed the banner
	 * @return UUID
	 * */
	public String getPlacedPlayerName() {
		return placedPlayerName;
	}

	public void setPlacedPlayerID(UUID placedPlayer) {
		this.placedPlayer = placedPlayer;
	}

	public void setPlacedPlayerName(String placedPlayerName) {
		this.placedPlayerName = placedPlayerName;
	}

	public void setPlacedPlayerNameAndID(UUID placedPlayer, String placedPlayerName) {
		setPlacedPlayerID(placedPlayer);
		setPlacedPlayerName(placedPlayerName);
	}

	public void addWhitelistPlayer(PlayerEntity player) {
		protectionWhitelist.add(new ProtectionEntry(new ExtendedBannerProfilePlayer(player.getUUID(), player.getGameProfile().getName())));
	}

	public void addWhitelistPlayerWithPerms(PlayerEntity player, List<BannerPermission> perms) {
		protectionWhitelist.add(new ProtectionEntry(new ExtendedBannerProfilePlayer(player.getUUID(), player.getGameProfile().getName())).setPerms(perms));
	}

	public void addWhitelistPlayer(ExtendedFellowship fellowship) {
		protectionWhitelist.add(new ProtectionEntry(new ExtendedBannerProfileFellowship(fellowship.getFellowshipId())));
	}

	public void addWhitelistPlayerWithPerms(ExtendedFellowship fellowship, List<BannerPermission> perms) {
		protectionWhitelist.add(new ProtectionEntry(new ExtendedBannerProfileFellowship(fellowship.getFellowshipId())).setPerms(perms));
	}

	@Override
	public void load(BlockState blockState, CompoundNBT pCompound) {
		super.load(blockState, pCompound);
		loadProtectionData(pCompound);
	}

	public void loadProtectionData(CompoundNBT loadableData) {
		if(loadableData.contains("ProtectionDataUnique")) {
			if(loadableData.contains("connectedEntity")) {
				connectedEntity = loadableData.getUUID("connectedEntity");
			}
		}
		if(loadableData.contains("ProtectionData")) {
			CompoundNBT pCompound = loadableData.getCompound("ProtectionData");
			protectionType = pCompound.getBoolean("protectionType");
			used = true;
			if(pCompound.contains("placedPlayer")) {
				placedPlayer = pCompound.getUUID("placedPlayer");
			}
			if(pCompound.contains("placedPlayerName")) {
				placedPlayerName = pCompound.getString("placedPlayerName");
			}
			if(pCompound.contains("protectionType")) {
				protectionType = pCompound.getBoolean("protectionType");
			}
			if(pCompound.contains("alignmentThreshold")) {
				alignmentLevel = pCompound.getInt("alignmentThreshold");
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
			//TODO validate player fellowships 
			if (pCompound.contains("defaultPerms", 7)) {  // 7 corresponds to a byte array in NBT
				byte[] ordinalValues = pCompound.getByteArray("perms");
				// Convert each ordinal value back to a BannerPermission enum
				defaultPerms.clear();
				for (byte ordinal : ordinalValues) {
					defaultPerms.add(BannerPermission.values()[ordinal]);
				}
			}
			if(pCompound.contains("protectionSelf")) {
				canProtectSelf = pCompound.getBoolean("protectionSelf");
			}
			if(pCompound.contains("used")) {
				used = pCompound.getBoolean("used");
			}
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		CompoundNBT pUniqueCompound = new CompoundNBT();
		if(connectedEntity!=null) {
			pUniqueCompound.putUUID("connectedEntity", connectedEntity);
		}
		if(!pUniqueCompound.isEmpty()) {
			pCompound.put("ProtectionDataUnique", pUniqueCompound);
		}
		saveProtectionData(pCompound);
		return pCompound;
	}

	public CompoundNBT saveProtectionData(CompoundNBT returnCompound) {
		if(used) {
			CompoundNBT pCompound = new CompoundNBT();
			pCompound.putBoolean("protectionType", protectionType);
			pCompound.putBoolean("used", used);
			if(alignmentLevel>1) {
				pCompound.putInt("alignmentThreshold", alignmentLevel);
			}
			if(placedPlayer!=null) {
				pCompound.putUUID("placedPlayer", placedPlayer);
			}
			if(placedPlayerName!=null) {
				pCompound.putString("placedPlayerName", placedPlayerName);
			}
			if(!protectionWhitelist.isEmpty()) {
				ListNBT whitelist = new ListNBT();
				for(ProtectionEntry protectionWWhitelist : protectionWhitelist) {
					whitelist.add(protectionWWhitelist.save());
				}
				pCompound.put("whitelistEntrys", pCompound);
			}
			if (!defaultPerms.isEmpty()) {
				// Initialize a byte array to store the ordinal values of BannerPermission
				byte[] ordinalValues = new byte[defaultPerms.size()];
				// Iterate over the entryPerms list to populate the byte array
				for (int i = 0; i < defaultPerms.size(); i++) {
					// Get the ordinal of the current BannerPermission enum and cast it to byte
					ordinalValues[i] = (byte) defaultPerms.get(i).ordinal();
				}
				// Store the byte array in the CompoundNBT with the key "perms"
				pCompound.putByteArray("defaultPerms", ordinalValues);
			}
			if(!canProtectSelf) {
				pCompound.putBoolean("protectionSelf", canProtectSelf);
			}
			returnCompound.put("ProtectionData", pCompound);
		}
		return returnCompound;
	}

	/**
	 * Gets the copied item, handling if the banner has protection data and has been used
	 * @return ItemStack
	 * */
	public ItemStack getItem(BannerTypes bannerType) {
		ItemStack itemstack = new ItemStack(bannerType.getBannerItem().get());
		if (used) {
			saveProtectionData(itemstack.getOrCreateTag());
		}
		return itemstack;
	}


	public static class ProtectionEntry {
		private List<BannerPermission> entryPerms = new ArrayList<>();
		private ExtendedBannerProfileBase profile;
		
		public ProtectionEntry(ExtendedBannerProfileBase profile) {
			this.profile = profile;
		}

		public ProtectionEntry setPerms(List<BannerPermission> perms) {
			entryPerms = perms;
			return this;
		}

		public boolean containsPermissionNode(BannerPermission permission) {
			return entryPerms.contains(permission);
		}

		public boolean hasPermission(BannerPermission permission) {
			return containsPermissionNode(BannerPermission.EVERYTHING) || containsPermissionNode(permission);
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
			profile.writeGameProfile(data);
			// Return the populated CompoundNBT containing all serialized data
			return data;
		}


		public static ProtectionEntry fromNBT(CompoundNBT nbt) {
			// Create an empty list to store BannerPermission
			List<BannerPermission> entryPerms = new ArrayList<>();
			// Read the byte array of ordinal values from NBT, if present
			if (nbt.contains("perms", 7)) {  // 7 corresponds to a byte array in NBT
				byte[] ordinalValues = nbt.getByteArray("perms");
				// Convert each ordinal value back to a BannerPermission enum
				for (byte ordinal : ordinalValues) {
					entryPerms.add(BannerPermission.values()[ordinal]);
				}
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
