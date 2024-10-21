package com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public abstract class ExtendedBannerProfileBase {

	/**
	 * Get unique identifier for profile
	 * @return UUID
	 * */
	public abstract UUID getId();
	
	/**
	 * Get player viewable name for profile
	 * * <p />
	 * This may be null for partial profile data if constructed manually.
	 * @return String
	 * */
	@Nullable
	public abstract String getName();

	/**
	 * Get profile type
	 * @return String
	 * */
	public abstract String getType();

	/**
	 * Checks if this profile is complete.
	 * <p />
	 * A complete profile has no empty fields. Partial profiles may be constructed manually and used as input to methods.
	 *
	 * @return True if this profile is complete (as opposed to partial)
	 */
	public abstract boolean isComplete();

	/**
	 * Check if the child profile class has all fields filled
	 * @return boolean
	 * */
	public abstract boolean isValid();

	/**
	 * Checks of the passed ServerPlayerEntity has permissions in this entry
	 * @param ServerPlayerEntity
	 * @return boolean
	 * 
	 */
	public abstract boolean isPlayerContained(PlayerEntity player);

	/**
	 * Saves the profile data to the passed CompoundNBT, returns the modified CompoundNBT
	 * @param CompoundNBT
	 * @return CompoundNBT
	 */
	public abstract CompoundNBT writeProfileData(CompoundNBT pTag);
	
	/**
	 * Saves the profile entry to the passed CompoundNBT, returns the modified CompoundNBT
	 * @param CompoundNBT
	 * @return CompoundNBT
	 */
	public CompoundNBT writeGameProfile(CompoundNBT pTag) {
		pTag.putString("type", getType());
		return writeProfileData(pTag);
	}
}
