package com.github.maximuslotro.lotrrextended.mixins.lotr.common.fac;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import lotr.common.fac.AreasOfInfluence;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointer;
import lotr.common.fac.FactionPointers;
import lotr.common.fac.FactionRegion;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionType;
import lotr.common.fac.MapSquare;
import lotr.common.fac.TechnicalFactions;
import lotr.common.world.map.MapSettings;
import net.minecraft.util.ResourceLocation;

/**
 * @reason Need to fix hostile faction mobs not attacking civilians
 * @Mixin (TechnicalFactions.class)
 * */
@Mixin(TechnicalFactions.class)
public class MixinTechnicalFactions {

	/**
	 * @author maximuslotro
	 * @reason Fix hostile aligned mobs not attacking everyone
	 * @Overwrite (remap=false)
	 * @param facSettings The loaded FactionSettings instance
	 * @param mapSettings The loaded MapSettings instance
	 * @param facPointer The Unique FactionPointer for this faction
	 * @param id The assigned ID for this Faction
	 * @return The built Faction for passed settings
	 * */
	@Overwrite(remap=false)
	private static Faction createTechnicalFaction(FactionSettings facSettings, MapSettings mapSettings, FactionPointer facPointer, int id) {
		ResourceLocation res = facPointer.getName();
		String name = String.format("faction.%s.%s", res.getNamespace(), res.getPath());
		boolean translateName = false;
		String subtitle = "";
		boolean translateSubtitle = false;
		FactionRegion region = null;
		int ordering = 0;
		int color = 0;
		MapSquare mapSquare = null;
		boolean isPlayableAlignmentFaction = false;
		Set<FactionType> types = ImmutableSet.of();
		boolean civilianKills = false;
		// Extended Start
		if(facPointer.equals(FactionPointers.HOSTILE)) {
			civilianKills=true;
		}
		// Extended End
		Faction faction = new Faction(facSettings, res, id, name, translateName, subtitle, translateSubtitle, region, ordering, color, mapSquare, isPlayableAlignmentFaction, types, civilianKills);
		faction.setAreasOfInfluence(AreasOfInfluence.makeEmptyAreas(mapSettings, faction));
		faction.setSpeechbankHomeBiomes(ImmutableList.of());
		return faction;
	}
}
