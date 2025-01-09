package com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class ExtendedBannerProfilePlayer extends ExtendedBannerProfileBase{
	private final UUID id;
	private final String name;

	/**
	 * Constructs a new Banner Profile with the specified ID and name.
	 * <p />
	 * Either ID or name may be null/empty, but at least one must be filled.
	 *
	 * @param id Unique ID of the profile
	 * @param name Display name of the profile
	 * @throws java.lang.IllegalArgumentException Both ID and name are either null or empty
	 */
	public ExtendedBannerProfilePlayer(final UUID id, final String name) {
		if (id == null && org.apache.commons.lang3.StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Name and ID cannot both be blank");
		}

		this.id = id;
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
		return id;
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
		return "player";
	}

	@Override
	public boolean isPlayerContained(PlayerEntity player) {
		return player.getUUID().equals(id);
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
		return id != null && org.apache.commons.lang3.StringUtils.isNotBlank(getName());
	}

	@Override
	public boolean isValid() {
		return id != null;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final ExtendedBannerProfilePlayer that = (ExtendedBannerProfilePlayer) o;

		if ((id != null ? !id.equals(that.id) : that.id != null) || (name != null ? !name.equals(that.name) : that.name != null)) {
			return false;
		}

		return true;
	}

	public static ExtendedBannerProfilePlayer readGameProfile(CompoundNBT pTag) {
		String s = null;
		UUID uuid = null;
		if (pTag.contains("Name", 8)) {
			s = pTag.getString("Name");
		}

		if (pTag.hasUUID("Id")) {
			uuid = pTag.getUUID("Id");
		}

		try {
			ExtendedBannerProfilePlayer gameprofile = new ExtendedBannerProfilePlayer(uuid, s);
			return gameprofile;
		} catch (Throwable throwable) {
			return null;
		}
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
