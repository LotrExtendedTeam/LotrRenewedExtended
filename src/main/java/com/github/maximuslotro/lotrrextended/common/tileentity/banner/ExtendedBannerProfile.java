package com.github.maximuslotro.lotrrextended.common.tileentity.banner;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

import net.minecraft.nbt.CompoundNBT;


public class ExtendedBannerProfile {
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
	public ExtendedBannerProfile(final UUID id, final String name) {
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
	public UUID getId() {
		return id;
	}

	/**
	 * Gets the display name of this banner profile.
	 * <p />
	 * This may be null for partial profile data if constructed manually.
	 *
	 * @return Name of the profile
	 */
	public String getName() {
		return name;
	}

	/**
	 * Checks if this profile is complete.
	 * <p />
	 * A complete profile has no empty fields. Partial profiles may be constructed manually and used as input to methods.
	 *
	 * @return True if this profile is complete (as opposed to partial)
	 */
	public boolean isComplete() {
		return id != null && org.apache.commons.lang3.StringUtils.isNotBlank(getName());
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final ExtendedBannerProfile that = (ExtendedBannerProfile) o;

		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("name", name)
				.toString();
	}

	/**
	 * Reads and returns a ExtendedBannerProfile that has been saved to the passed in NBTTagCompound
	 */
	public static ExtendedBannerProfile readGameProfile(CompoundNBT pTag) {
		String s = null;
		UUID uuid = null;
		if (pTag.contains("Name", 8)) {
			s = pTag.getString("Name");
		}

		if (pTag.hasUUID("Id")) {
			uuid = pTag.getUUID("Id");
		}

		try {
			ExtendedBannerProfile gameprofile = new ExtendedBannerProfile(uuid, s);
			return gameprofile;
		} catch (Throwable throwable) {
			return null;
		}
	}

	/**
	 * Writes a {@code profile} to the given {@code tag}.
	 */
	public static CompoundNBT writeGameProfile(CompoundNBT pTag, ExtendedBannerProfile pProfile) {
		if (!net.minecraft.util.StringUtils.isNullOrEmpty(pProfile.getName())) {
			pTag.putString("Name", pProfile.getName());
		}

		if (pProfile.getId() != null) {
			pTag.putUUID("Id", pProfile.getId());
		}


		return pTag;
	}
}