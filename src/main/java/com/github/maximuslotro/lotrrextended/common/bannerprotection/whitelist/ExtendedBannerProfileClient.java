package com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;

public class ExtendedBannerProfileClient extends ExtendedBannerProfileBase{
	private String name;

	/**
	 * Constructs a new Banner Profile with the specified ID and name.
	 * <p />
	 * Either ID or name may be null/empty, but at least one must be filled.
	 *
	 * @param id Unique ID of the profile
	 * @param name Display name of the profile
	 * @throws java.lang.IllegalArgumentException Both ID and name are either null or empty
	 */
	public ExtendedBannerProfileClient(final String name) {
		this.name = name;
	}

	/**
	 * Gets the unique ID of this banner profile.
	 * <p />
	 * This may be null for partial profile data if constructed manually.
	 *
	 * @return ID of the profile
	 */
	@Override
	public UUID getId() {
		return Util.NIL_UUID;
	}

	/**
	 * Get underlying name for profile
	 * * <p />
	 * This may be null for partial profile data if constructed manually.
	 * @return String
	 * */
	@Override
	@Nullable
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get display name for profile
	 * same as getName()
	 * * <p />
	 * This may be null for partial profile data if constructed manually.
	 * @return String
	 * */
	@Override
	@Nullable
	public String getDisplayName() {
		return name;
	}

	@Override
	public String getType() {
		return "client";
	}

	@Override
	public boolean isPlayerContained(PlayerEntity player) {
		return false;
	}

	/**
	 * Checks if this profile is complete.
	 * <p />
	 * A complete profile has no empty fields. Partial profiles may be constructed manually and used as input to methods.
	 *
	 * @return True if this profile is complete (as opposed to partial)
	 */
	@Override
	public boolean isComplete() {
		return org.apache.commons.lang3.StringUtils.isNotBlank(getName());
	}

	/**
	 * Check if the profile class has all fields filled
	 * @return boolean
	 * */
	@Override
	public boolean isValid() {
		return isComplete();
	}

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
