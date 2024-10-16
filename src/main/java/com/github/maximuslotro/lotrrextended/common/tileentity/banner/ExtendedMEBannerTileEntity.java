package com.github.maximuslotro.lotrrextended.common.tileentity.banner;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.enums.BannerPermissions;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class ExtendedMEBannerTileEntity extends TileEntity implements ITickableTileEntity{
	private boolean isActive = false;
	//Use for user whitelisting
	private List<ProtectionEntry> protectionWhitelist = new ArrayList<>();
	//Use for faction alignment level
	private int alignmentLevel = 1;
	//False for faction, true for whitelist
	private boolean protectionType = false;
	//Default perms for everyone
	private List<BannerPermissions> defaultPerms = new ArrayList<>();
	//Should protect myself from destruction besides faction member or player placed
	private boolean canProtectSelf = true;
	//Should add nbt to dropped items?
	private boolean used = false;
	
	public ExtendedMEBannerTileEntity() {
		super(ExtendedTileEntities.BANNER.get());
	}

	private boolean firstTick = false;
	@Override
	public void tick() {
		if(!firstTick) {
			isActive = this.getBlockState().getValue(ExtendedMEBannerBlock.HALF) == DoubleBlockHalf.LOWER;
			firstTick = true;
		}
	}

	/**
	 * Checks if this instance of a banner is the "main" banner block
	 * @return boolean
	 * */
	public boolean isActiveOrBottom() {
		return isActive;
	}

	public boolean isFactionProtcting() {
		return !protectionType;
	}

	public boolean isWhitelistProtcting() {
		return protectionType;
	}

	@Override
	public void load(BlockState blockState, CompoundNBT pCompound) {
		super.load(blockState, pCompound);

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
	                protectionWhitelist.add(ProtectionEntry.fromNBT(node));
	            }
	        }
		}
		if (pCompound.contains("defaultPerms", 7)) {  // 7 corresponds to a byte array in NBT
			byte[] ordinalValues = pCompound.getByteArray("perms");
			// Convert each ordinal value back to a BannerPermissions enum
			defaultPerms.clear();
			for (byte ordinal : ordinalValues) {
				defaultPerms.add(BannerPermissions.values()[ordinal]);
			}
		}
		if(pCompound.contains("protectionSelf")) {
			canProtectSelf = pCompound.getBoolean("protectionSelf");
		}
		if(pCompound.contains("used")) {
			used = pCompound.getBoolean("used");
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		if(isActive && firstTick)
		pCompound.putBoolean("protectionType", protectionType);
		if(alignmentLevel>1) {
			pCompound.putInt("alignmentThreshold", alignmentLevel);
		}
		if(!protectionWhitelist.isEmpty()) {
			ListNBT whitelist = new ListNBT();
			for(ProtectionEntry protectionWWhitelist : protectionWhitelist) {
				whitelist.add(protectionWWhitelist.save());
			}
			pCompound.put("whitelistEntrys", pCompound);
		}
		if (!defaultPerms.isEmpty()) {
			// Initialize a byte array to store the ordinal values of BannerPermissions
			byte[] ordinalValues = new byte[defaultPerms.size()];
			// Iterate over the entryPerms list to populate the byte array
			for (int i = 0; i < defaultPerms.size(); i++) {
				// Get the ordinal of the current BannerPermissions enum and cast it to byte
				ordinalValues[i] = (byte) defaultPerms.get(i).ordinal();
			}
			// Store the byte array in the CompoundNBT with the key "perms"
			pCompound.putByteArray("defaultPerms", ordinalValues);
		}
		if(!canProtectSelf) {
			pCompound.putBoolean("protectionSelf", canProtectSelf);
		}
		if(used) {
			pCompound.putBoolean("used", used);
		}
		return pCompound;
	}

	public static class ProtectionEntry {
		private List<BannerPermissions> entryPerms = new ArrayList<>();
		private ExtendedBannerProfile profile;
		
		public ProtectionEntry(ExtendedBannerProfile profile) {
			this.profile = profile;
		}

		public void setPerms(List<BannerPermissions> perms) {
			entryPerms = perms;
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
				// Initialize a byte array to store the ordinal values of BannerPermissions
				byte[] ordinalValues = new byte[entryPerms.size()];
				// Iterate over the entryPerms list to populate the byte array
				for (int i = 0; i < entryPerms.size(); i++) {
					// Get the ordinal of the current BannerPermissions enum and cast it to byte
					ordinalValues[i] = (byte) entryPerms.get(i).ordinal();
				}
				// Store the byte array in the CompoundNBT with the key "perms"
				data.putByteArray("perms", ordinalValues);
			}
			// Serialize the ExtendedBannerProfile and add it to the CompoundNBT
			ExtendedBannerProfile.writeGameProfile(data, profile);
			// Return the populated CompoundNBT containing all serialized data
			return data;
		}


		public static ProtectionEntry fromNBT(CompoundNBT nbt) {
			// Create an empty list to store BannerPermissions
			List<BannerPermissions> entryPerms = new ArrayList<>();
			// Read the byte array of ordinal values from NBT, if present
			if (nbt.contains("perms", 7)) {  // 7 corresponds to a byte array in NBT
				byte[] ordinalValues = nbt.getByteArray("perms");
				// Convert each ordinal value back to a BannerPermissions enum
				for (byte ordinal : ordinalValues) {
					entryPerms.add(BannerPermissions.values()[ordinal]);
				}
			}
			// Read the ExtendedBannerProfile from the NBT data
			ExtendedBannerProfile user = ExtendedBannerProfile.readGameProfile(nbt);
			// Create the ProtectionEntry object with the user profile
			ProtectionEntry entry = new ProtectionEntry(user);
			// Set the permissions for the entry
			entry.setPerms(entryPerms);
			return entry;
		}
	}
}
