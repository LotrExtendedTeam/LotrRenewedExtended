package com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;

import lotr.common.fellowship.ExtendedFellowship;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedBannerProfileFellowship extends ExtendedBannerProfileBase{
	private final UUID id;
	private final String name;

	/**
	 * Constructs a new Banner Profile with the specified ID and name.
	 *
	 * @param id Unique ID of the profile
	 * @throws java.lang.IllegalArgumentException Both ID and name are either null or empty
	 */
	public ExtendedBannerProfileFellowship(final UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("Fellowship ID cannot both be blank");
		}
		this.id = id;
		ExtendedFellowship fellowship = getFellowshipFromID(id);
		if(fellowship!=null) {
			this.name = fellowship.getFellowshipName();
		}else {
			this.name = "";
		}
	}

	/**
	 * Constructs a new Fellowship banner profile manually
	 *
	 * @param id Unique ID of the profile
	 * @param name Display name of the profile
	 */
	public ExtendedBannerProfileFellowship(final String name, final UUID id) {
		this.name = name;
		this.id = id;
	}

	/**
	 * Gets the unique ID of this banner profile.
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
	 * This may be null for partial profile data if constructed manually.
	 * @return String
	 * */
	@Override
	@Nullable
	public  String getName() {
		return name;
	}

	/**
	 * Get display name for profile
	 * can be the same as getName()
	 * This may be null for partial profile data if constructed manually.
	 * @return String
	 * */
	@Override
	@Nullable
	public String getDisplayName() {
		return "f/"+name;
	}

	@Override
	public String getType() {
		return "fellowship";
	}

	@Override
	public boolean isPlayerContained(PlayerEntity player) {
		ExtendedFellowship fellowship = getFellowshipFromID(id);
		return fellowship != null && fellowship.isInFellowship(player.getUUID());
	}

	@Override
	public boolean isComplete() {
		return id != null && org.apache.commons.lang3.StringUtils.isNotBlank(getName());
	}

	@Override
	public boolean isValid() {
		return id != null && getFellowshipFromID(id) != null && !getFellowshipFromID(id).isDisbanded();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final ExtendedBannerProfileFellowship that = (ExtendedBannerProfileFellowship) o;

        return (Objects.equals(id, that.id)) && (Objects.equals(name, that.name));
    }

	public static ExtendedBannerProfileFellowship readGameProfile(CompoundNBT pTag) {
		try {
			UUID uuid = pTag.getUUID("Id");
			ExtendedFellowship fellowship = getFellowshipFromID(uuid);
			if(fellowship!=null) {
				return new ExtendedBannerProfileFellowship(uuid);
			}else {
				ExtendedLog.warn("Removed fellowship "+ uuid +" from banner since it cannot be found!");
				return null;
			}
		} catch (Throwable throwable) {
			return null;
		}
	}

	private static ExtendedFellowship getFellowshipFromID(UUID id) {
		return ExtendedLevelData.serverInstance().getFellowshipData().getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), id);
	}

	@Override
	public CompoundNBT writeProfileData(CompoundNBT pTag) {
		pTag.putUUID("Id", this.getId());
		return pTag;
	}
}
