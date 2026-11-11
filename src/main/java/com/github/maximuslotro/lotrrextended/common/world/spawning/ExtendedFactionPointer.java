package com.github.maximuslotro.lotrrextended.common.world.spawning;

import java.util.Optional;

import lotr.common.fac.Faction;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.thread.EffectiveSide;

public class ExtendedFactionPointer {
	private final ResourceLocation factionID;

	public ExtendedFactionPointer(ResourceLocation factionID) {
		this.factionID = factionID;
	}

	public ResourceLocation getFactionID() {
		return factionID;
	}

	/**
	 * Prioritize using rather than other method
	 * @param pLevel The world instance used to determine sidedness
	 * @return  An optional faction object
	 * */
	public Optional<Object> getFaction(World pLevel) {
		FactionSettings factions = FactionSettingsManager.sidedInstance(pLevel).getCurrentLoadedFactions();
		return factions==null ? Optional.empty() : Optional.ofNullable(factions.getFactionByName(factionID));
	}

	/**
	 * Prioritize using above method when possible
	 * @return An optional faction object
	 * */
	public Optional<Faction> getFaction() {
		FactionSettings factions = FactionSettingsManager.sidedInstance(EffectiveSide.get()).getCurrentLoadedFactions();
		return factions==null ? Optional.empty() : Optional.ofNullable(factions.getFactionByName(factionID));
	}
}

