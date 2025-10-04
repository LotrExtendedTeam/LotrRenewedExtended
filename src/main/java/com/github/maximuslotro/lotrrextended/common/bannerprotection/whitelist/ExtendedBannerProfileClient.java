package com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;

/**
 * Client-side implementation of {@link ExtendedBannerProfileBase}.
 * Represents a banner profile with optional name and a fixed NIL UUID.
 */
public class ExtendedBannerProfileClient extends ExtendedBannerProfileBase {

	private String name;

	/**
	 * Constructs a new client banner profile with the specified display name.
	 * Requires a name, (ID is fixed as NIL_UUID here in the client profile).
	 *
	 * @param name the display name of the profile; may be {@code null}, but ideally the name should not be empty
	 * @throws IllegalArgumentException if the name is null
	 */
	public ExtendedBannerProfileClient(final String name) {
		if ((name == null)) {
			throw new IllegalArgumentException("Name is null");
		}
		this.name = name;
	}

	/**
	 * Gets the unique ID of this banner profile.
	 * For client profiles, this is always {@link Util#NIL_UUID}.
	 *
	 * @return the NIL UUID
	 */
	@Override
	public UUID getId() {
		return Util.NIL_UUID;
	}

	/**
	 * Gets the underlying name of the profile.
	 * May be {@code null} if partial or incomplete.
	 *
	 * @return the profile name, or {@code null}
	 */
	@Override
	@Nullable
	public String getName() {
		return name;
	}

	/**
	 * Sets the profile name.
	 *
	 * @param name the new name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the display name of the profile.
	 * Same as {@link #getName()}.
	 *
	 * @return the display name, or {@code null}
	 */
	@Override
	@Nullable
	public String getDisplayName() {
		return name;
	}

	/**
	 * Gets the profile type.
	 *
	 * @return the string literal "client"
	 */
	@Override
	public String getType() {
		return "client";
	}

	/**
	 * Checks if the specified player is contained in this profile.
	 * Client profiles never contain players.
	 *
	 * @param player the player entity to check
	 * @return {@code false} always
	 */
	@Override
	public boolean isPlayerContained(PlayerEntity player) {
		return false;
	}

	/**
	 * Checks if this profile is complete.
	 * For client profiles, completeness means having a non-blank name.
	 *
	 * @return {@code true} if the name is not blank, otherwise {@code false}
	 */
	@Override
	public boolean isComplete() {
		return org.apache.commons.lang3.StringUtils.isNotBlank(getName());
	}

	/**
	 * Checks if the profile is valid.
	 * Equivalent to {@link #isComplete()} for this implementation.
	 *
	 * @return {@code true} if valid, {@code false} otherwise
	 */
	@Override
	public boolean isValid() {
		return isComplete();
	}

	/**
	 * Writes this profile's data to the provided {@link CompoundNBT}.
	 * Saves the name and ID if present.
	 *
	 * @param pTag the NBT compound to write to
	 * @return the modified {@code pTag} with profile data added
	 */
	@Override
	public CompoundNBT writeProfileData(CompoundNBT pTag) {
		if (!net.minecraft.util.StringUtils.isNullOrEmpty(this.getName())) {
			pTag.putString("Name", this.getName());
		}
		if (this.getId() != null) {
			pTag.putUUID("Id", this.getId());
		}
		return pTag;
	}
}