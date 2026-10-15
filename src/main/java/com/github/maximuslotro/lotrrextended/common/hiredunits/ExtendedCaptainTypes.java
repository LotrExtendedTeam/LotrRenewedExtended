package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DatagenModLoader;

/**
 * Stores IDs for built-in hired unit profiles.
 */
public class ExtendedCaptainTypes {
	public static final Set<ResourceLocation> CAPTAIN_TYPES = Sets.newHashSet();

	public static final ResourceLocation BREE_SHERIFF = make("bree_sheriff");
	public static final ResourceLocation GUNDABAD_CHIEFTAIN = make("gundabad_chieftain");
	public static final ResourceLocation DUNEDAIN_CAPTAIN = make("dunedain_captain");
	public static final ResourceLocation DWARF_COMMANDER = make("dwarven_commander");
	public static final ResourceLocation BLUE_DWARF_COMMANDER = make("blue_dwarven_commander");
	public static final ResourceLocation RIVENDELL_CAPTAIN = make("rivendell_captain");
	public static final ResourceLocation LINDON_LORD = make("lindon_lord");
	public static final ResourceLocation ISENGARD_CAPTAIN = make("lsengard_captain");
	public static final ResourceLocation DUNLENDING_WARLORD = make("dunlending_warlord");
	public static final ResourceLocation HOBBIT_FARMER = make("hobbit_farmer");
	public static final ResourceLocation BREE_FARMER = make("bree_farmer");
	public static final ResourceLocation DUNLENDING_FARMER = make("dunlending_farmer");

	public static final ResourceLocation UMBAR_CAPTAIN = make("umbar_captain");
	public static final ResourceLocation MORDOR_COMMANDER = make("mordor_commander");
	public static final ResourceLocation COAST_SOUTHRON_WARLORD = make("coast_southron_warlord");
	public static final ResourceLocation HARNENNOR_WARLORD = make("harnennor_warlord");
	public static final ResourceLocation DALISH_CAPTAIN = make("dalish_captain");
	public static final ResourceLocation WOOD_ELF_CAPTAIN = make("wood_elf_captain");
	public static final ResourceLocation GALADHRIM_LORD = make("galadhrim_lord");
	public static final ResourceLocation GONDORIAN_CAPTAIN = make("gondor_captain");
	public static final ResourceLocation ROHAN_MARSHAL = make("rohan_marshal");

	private static ResourceLocation make(String pId) {
		return validate(new ResourceLocation(LotrExtendedMod.MODID, pId));
	}

	private static ResourceLocation validate(ResourceLocation pId) {
		if(DatagenModLoader.isRunningDataGen()) {
			if (CAPTAIN_TYPES.add(pId)) {
			} else {
				throw new IllegalArgumentException(pId + " is already a registered built-in extended hired unit type");
			}
		}
		return pId;
	}
}
