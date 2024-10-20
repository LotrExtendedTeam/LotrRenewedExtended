package com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist;

import java.util.UUID;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.fellowship.ExtendedFellowship;
import lotr.common.fellowship.ExtendedFellowshipHolderServer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedBannerProfileFellowship extends ExtendedBannerProfileBase{
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
	 * Gets the display name of this banner profile.
	 * <p />
	 * This may be null for partial profile data if constructed manually.
	 *
	 * @return Name of the profile
	 * 
	 */
	@Override
	public String getName() {
		return "f/"+name;
	}

	@Override
	public String getType() {
		return "fellowship";
	}

	@Override
	public boolean isPlayerListedWithPerms(ServerPlayerEntity player) {
		ExtendedFellowship fellowship = getFellowshipFromID(id);
		return fellowship!=null ? fellowship.isInFellowship(player.getUUID()) : false;
	}

	@Override
	public boolean isComplete() {
		return id != null && org.apache.commons.lang3.StringUtils.isNotBlank(getName());
	}
	
	@Override
	public boolean isValid() {
		return getFellowshipFromID(id) != null && !getFellowshipFromID(id).isRemoved();
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

		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}

		return true;
	}

	public static ExtendedBannerProfileFellowship readGameProfile(CompoundNBT pTag) {
		try {
			UUID uuid = pTag.getUUID("Id");
			ExtendedFellowship fellowship = getFellowshipFromID(uuid);
			if(fellowship!=null) {
				return new ExtendedBannerProfileFellowship(uuid);
			}else {
				ExtendedLog.info("Removed fellowship "+uuid.toString()+" from banner since it cannot be found!");
				return null;
			}
		} catch (Throwable throwable) {
			return null;
		}
	}

	private static ExtendedFellowship getFellowshipFromID(UUID id) {
		return ExtendedFellowshipHolderServer.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), id);
	}

	@Override
	public CompoundNBT writeProfileData(CompoundNBT pTag) {
		pTag.putUUID("Id", this.getId());
		return pTag;
	}
}
