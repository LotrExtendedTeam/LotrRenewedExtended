package com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

/**
 * Base class for extended banner profiles.
 * Provides abstract methods to access profile details and persistence operations.
 */
public abstract class ExtendedBannerProfileBase {
	/**
	 * Gets the unique identifier for this profile.
	 *
	 * @return the UUID of the profile
	 */
	public abstract UUID getId();
	/**
	 * Gets the underlying name of the profile.
	 * This may be {@code null} for partial profile data constructed manually.
	 *
	 * @return the name of the profile, or {@code null} if not available
	 */
	@Nullable
	public abstract String getName();
	/**
	 * Creates a deep copy/clone of this profile entry.
	 * 
	 * @return a new independent instance of the profile
	 */
	public abstract ExtendedBannerProfileBase copy();
	/**
	 * Gets the display name of the profile.
	 * Can be the same as {@link #getName()}.
	 * This may be {@code null} for partial profile data constructed manually.
	 *
	 * @return the display name, or {@code null} if not available
	 */
	@Nullable
	public abstract String getDisplayName();
	/**
	 * Gets the profile type.
	 *
	 * @return the type of the profile as a String
	 */
	public abstract String getType();
	/**
	 * Determines if this profile is complete.
	 * A complete profile has no empty fields.
	 * Partial profiles may be constructed manually and used as input to methods.
	 *
	 * @return {@code true} if the profile is complete, {@code false} otherwise
	 */
	public abstract boolean isComplete();
	/**
	 * Checks if all fields in the profile class are filled.
	 *
	 * @return {@code true} if the profile is valid, {@code false} otherwise
	 */
	public abstract boolean isValid();
	/**
	 * Checks if the given player is contained within this profile entry.
	 *
	 * @param player the {@link PlayerEntity} instance to check permissions for
	 * @return {@code true} if the player is contained, {@code false} otherwise
	 */
	public abstract boolean isPlayerContained(PlayerEntity player);
	/**
	 * Saves the profile data into the given {@link CompoundNBT}.
	 *
	 * @param pTag the {@link CompoundNBT} to write data into
	 * @return the modified {@link CompoundNBT} containing the profile data
	 */
	public abstract CompoundNBT writeProfileData(CompoundNBT pTag);
	/**
	 * Saves the profile entry to the given {@link CompoundNBT}.
	 * <p>
	 * Note: Do not call this method directly; instead, call {@link #writeProfileData(CompoundNBT)}.
	 *
	 * @param pTag the {@link CompoundNBT} to write to
	 * @return the modified {@link CompoundNBT} with profile type and data
	 */
	public CompoundNBT writeGameProfile(CompoundNBT pTag) {
		pTag.putString("type", getType());
		return writeProfileData(pTag);
	}

	@Override
	public boolean equals(Object o) {
	    if(this == o) return true;
	    if(o == null || getClass() != o.getClass()) {
	    	return false;
	    }
	    ExtendedBannerProfileBase that = (ExtendedBannerProfileBase) o;
	    return Objects.equals(this.getId(), that.getId()) &&
	           Objects.equals(this.getType(), that.getType()) &&
	           Objects.equals(this.getName(), that.getName());
	}

	@Override
	public int hashCode() {
	    return Objects.hash(getId(), getType(), getName());
	}
}
