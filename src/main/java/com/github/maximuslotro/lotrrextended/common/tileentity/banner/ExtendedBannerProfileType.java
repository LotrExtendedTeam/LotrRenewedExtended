package com.github.maximuslotro.lotrrextended.common.tileentity.banner;

import java.util.UUID;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public abstract class ExtendedBannerProfileType {

	/**
	 * Get unique identifier for profile
	 * */
	public abstract UUID getId();
	
	/**
	 * Get player viewable name for profile
	 * */
	public abstract String getName();

	/**
	 * Get profile type
	 * */
	public abstract String getType();

	/**
	 * Check if the child profile class has all fields filled
	 * */
	public abstract boolean isComplete();

	public abstract boolean isPlayerListedWithPerms(ServerPlayerEntity player);

	/**
	 * Writes a {@code profile} to the given {@code tag}.
	 */
	public abstract CompoundNBT writeProfileData(CompoundNBT pTag);
	
	public CompoundNBT writeGameProfile(CompoundNBT pTag) {
		pTag.putString("type", getType());
		return writeProfileData(pTag);
	}
}
