package com.github.maximuslotro.lotrrextended.mixins.lotr.common.data;

import java.util.List;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
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
import net.minecraftforge.fml.server.ServerLifecycleHooks;

/**
 * @reason Needed to inject functionality into the alignment system
 * @Mixin (AlignmentDataModule.class)
 * */
@Mixin(AlignmentDataModule.class)
public abstract class MixinAlignmentDataModule extends PlayerDataModule{

	/**
	 * @reason Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param pd A placeholder variable instance of LOTRPlayerData
	 * */
	protected MixinAlignmentDataModule(LOTRPlayerData pd) {
		super(pd);
	}
	/**
	 * The currently viewed faction
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private Faction currentViewedFaction;
	/**
	 * The current cooldown until player ???
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private int pledgeKillCooldown;
	/**
	 * The current cooldown until player can pledge again
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private int pledgeBreakCooldown;
	/**
	 * Runs alignment draining for the player
	 * @Shadow (remap = false)
	 * @param player The ServerPlayerEntity to drain
	 * @param tick The current server tick
	 * */
	@Shadow(remap = false)
	private void runAlignmentDraining(ServerPlayerEntity player, int tick) {}
	/**
	 * Updates the player's factions depending on the passed dimension
	 * @Shadow (remap = false)
	 * @param currentDimension The current dimension
	 * */
	@Shadow(remap = false)
	private void updateCurrentViewedFactionToNewDimension(RegistryKey<World> currentDimension) {}
	/**
	 * Sets the pledge break cooldown time for this player
	 * @Shadow (remap = false)
	 * @param i Updates the cooldown on pledge breaking
	 * */
	@Shadow(remap = false)
	public void setPledgeBreakCooldown(int i) {}
	/**
	 * Updates the player's viewed FactionRegion
	 * @Shadow (remap = false)
	 * @param region A FactionRegion 
	 * */
	@Shadow(remap = false)
	public Faction getRegionLastViewedFaction(FactionRegion region) {return null;}
	/**
	 * Updates the player's viewed Faction
	 * @Shadow (remap = false)
	 * @param region A Faction
	 * */
	@Shadow(remap = false)
	public void setCurrentViewedFaction(Faction fac) {}

	/**
	 * @author maximuslotro
	 * @reason Adds a advancement hook for when a players alignment level changes
	 * @Inject (method = "setAlignment(Llotr/common/fac/Faction;F)V", at = @At("TAIL"), remap = false)
	 * @param faction The Faction for which the players alignment is being changed
	 * @param alignment The new value for the alignment
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "setAlignment(Llotr/common/fac/Faction;F)V", at = @At("TAIL"), remap = false)
	public void setAlignment(Faction faction, float alignment, CallbackInfo ci) {
		if(ServerLifecycleHooks.getCurrentServer() == null && LOTRMod.PROXY.isClient()) {
			return;
		}
		UUID uuid = (((MixinPlayerDataModuleInvoker)(AlignmentDataModule)(Object)this)).invokeGetPlayerUUID();
		if(uuid!=null) {
			ServerPlayerEntity player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(uuid);
			ExtendedCriteriaTriggers.ALIGNMENT_TRIGGER.trigger(player, faction, alignment);
		}
	}
	/**
	 * @author maximuslotro
	 * @reason need to hide special factions from rendering and stuff
	 * @Overwrite (remap = false)
	 * @param player The ServerPlayerEntity to update
	 * @param world The current ServerWorld to update
	 * @param tick The current server tick, used to run alignment draining
	 * */
	@Override
	@Overwrite(remap = false)
	protected void onUpdate(ServerPlayerEntity player, ServerWorld world, int tick) {
		RegistryKey<World> currentDimension = LOTRDimensions.getCurrentLOTRDimensionOrFallback(world);
		if (currentViewedFaction != null) {
			FactionRegion currentRegion = currentViewedFaction.getRegion();
			if (!currentRegion.getDimension().equals(currentDimension)) {
				updateCurrentViewedFactionToNewDimension(currentDimension);
			}
			// Extended Start
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
			// Extended End
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
