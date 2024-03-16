package com.github.maximuslotro.lotrrextended.mixins.lotr.common.data;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.math.IntMath;

import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRPlayerData;
import lotr.common.data.PlayerDataModule;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionRegion;
import lotr.common.init.LOTRDimensions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(AlignmentDataModule.class)
public abstract class MixinAlignmentDataModule extends PlayerDataModule{

	protected MixinAlignmentDataModule(LOTRPlayerData pd) {
		super(pd);
	}
	@Shadow(remap = false)
	private Faction currentViewedFaction;

	@Shadow(remap = false)
	private int pledgeKillCooldown;

	@Shadow(remap = false)
	private int pledgeBreakCooldown;

	@Shadow(remap = false)
	private void runAlignmentDraining(ServerPlayerEntity player, int tick) {}

	@Shadow(remap = false)
	private void updateCurrentViewedFactionToNewDimension(RegistryKey<World> currentDimension) {}

	@Shadow(remap = false)
	public void setPledgeBreakCooldown(int i) {}


	@Shadow(remap = false)
	public Faction getRegionLastViewedFaction(FactionRegion region) {return null;}

	@Shadow(remap = false)
	public void setCurrentViewedFaction(Faction fac) {}
	/**
	 * @author maximuslotro
	 * @reason need to hide special factions from rendering and stuff
	 * */
	@Overwrite(remap = false)
	protected void onUpdate(ServerPlayerEntity player, ServerWorld world, int tick) {
		RegistryKey<World> currentDimension = LOTRDimensions.getCurrentLOTRDimensionOrFallback(world);
		if (currentViewedFaction != null) {
			FactionRegion currentRegion = currentViewedFaction.getRegion();
			if (!currentRegion.getDimension().equals(currentDimension)) {
				updateCurrentViewedFactionToNewDimension(currentDimension);
			}
			if(currentRegion.getName().toString().equals("lotr:special")) {
				List<FactionRegion> dimensionRegions = currentFactionSettings().getRegionsForDimension(currentDimension);
				int i = dimensionRegions.indexOf(currentRegion);
				++i;
				i = IntMath.mod(i, dimensionRegions.size());
				FactionRegion newRegion = dimensionRegions.get(i);
				List<Faction> regionFacs = currentFactionSettings().getFactionsForRegion(newRegion);
				if (regionFacs != null && !regionFacs.isEmpty()) {
					Faction fac = getRegionLastViewedFaction(newRegion);
					if (fac != null) {
						setCurrentViewedFaction(fac);
					}
					else {
						setCurrentViewedFaction(regionFacs.get(0));
					}
				}
				else {
					newRegion = dimensionRegions.get(0);
					regionFacs = currentFactionSettings().getFactionsForRegion(newRegion);
					if (regionFacs != null && !regionFacs.isEmpty()) {
						Faction fac = getRegionLastViewedFaction(newRegion);
						if (fac != null) {
							setCurrentViewedFaction(fac);
						}
						else {
							setCurrentViewedFaction(regionFacs.get(0));
						}
					}
					else {
						setCurrentViewedFaction(currentFactionSettings().getFactionsForRegion(dimensionRegions.get(0)).get(0));
					}
				}
			}
		} else {
			updateCurrentViewedFactionToNewDimension(currentDimension);
		}

		runAlignmentDraining(player, tick);
		if (pledgeKillCooldown > 0) {
			--pledgeKillCooldown;
			if (pledgeKillCooldown == 0 || isTimerAutosaveTick()) {
				markDirty();
			}
		}

		if (pledgeBreakCooldown > 0) {
			setPledgeBreakCooldown(pledgeBreakCooldown - 1);
		}

	}
}
