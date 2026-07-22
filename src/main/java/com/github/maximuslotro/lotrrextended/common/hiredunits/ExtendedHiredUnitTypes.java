package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DatagenModLoader;

/**
 * Stores IDs for built-in hired unit profiles.
 */
public class ExtendedHiredUnitTypes {
	public static final Set<ResourceLocation> UNIT_TYPES = Sets.newHashSet();
	// HOBBIT
	public static final ResourceLocation HOBBIT_FARMHAND = make("hobbit_farmhand");
	// BREE
	public static final ResourceLocation BREE_MAN = make("bree_man");
	public static final ResourceLocation BREE_HOBBIT = make("bree_hobbit");
	public static final ResourceLocation BREE_GUARD = make("bree_guard");
	public static final ResourceLocation BREE_FARMHAND = make("bree_farmhand");
	// GUNDABD
	public static final ResourceLocation GUNDABAD_ORC = make("gundabad_orc");
	public static final ResourceLocation GUNDABAD_ORC_ARCHER = make("gundabad_orc_archer");
	public static final ResourceLocation GUNDABAD_URUK = make("gundabad_uruk");
	public static final ResourceLocation GUNDABAD_URUK_ARCHER = make("gundabad_uruk_archer");
	// DUNEDAIN
	public static final ResourceLocation DUNEDAIN_RANGER = make("dunedain_ranger");
	// DWARF
	public static final ResourceLocation DWARF = make("dwarf");
	public static final ResourceLocation DWARF_WARRIOR = make("dwarven_warrior");
	public static final ResourceLocation DWARF_ARCHER = make("dwarven_archer");
	// BLUE DWARF
	public static final ResourceLocation BLUE_DWARF = make("blue_dwarf");
	public static final ResourceLocation BLUE_DWARF_WARRIOR = make("blue_dwarven_warrior");
	public static final ResourceLocation BLUE_DWARF_ARCHER = make("blue_dwarven_archer");
	public static final ResourceLocation BLUE_DWARF_AXE_THROWER = make("blue_dwarven_axe_thrower");
	// RIVENDELL
	public static final ResourceLocation RIVENDELL_ELF = make("rivendell_elf");
	public static final ResourceLocation RIVENDELL_WARRIOR = make("rivendell_warrior");
	public static final ResourceLocation IMLADRIS_GUARD = make("rivendell_guard");
	// LINDON
	public static final ResourceLocation LINDON_ELF = make("lindon_elf");
	public static final ResourceLocation LINDON_WARRIOR = make("lindon_warrior");
	// ISENGARD
	public static final ResourceLocation ISENGARD_SNAGA = make("lsengard_snaga");
	public static final ResourceLocation ISENGARD_SNAGA_ARCHER = make("lsengard_snaga_archer");
	public static final ResourceLocation ISENGARD_URUK = make("lsengard_uruk");
	public static final ResourceLocation ISENGARD_URUK_ARCHER = make("lsengard_uruk_archer");
	public static final ResourceLocation ISENGARD_URUK_BERSERKER = make("lsengard_uruk_berserker");
	// DUNLANDING
	public static final ResourceLocation DUNLENDING = make("dunlending");
	public static final ResourceLocation DUNLENDING_WARRIOR = make("dunlending_warrior");
	public static final ResourceLocation DUNLENDING_BOWMAN = make("dunlending_bowman");
	public static final ResourceLocation DUNLENDING_AXE_THROWER = make("dunlending_axe_thrower");
	public static final ResourceLocation DUNLENDING_BERSERKER = make("dunlending_berserker");

	private static ResourceLocation make(String pId) {
		return validate(new ResourceLocation(LotrExtendedMod.MODID, pId));
	}

	private static ResourceLocation validate(ResourceLocation pId) {
		if(DatagenModLoader.isRunningDataGen()) {
			if (UNIT_TYPES.add(pId)) {
			} else {
				throw new IllegalArgumentException(pId + " is already a registered built-in extended hired unit type");
			}
		}
		return pId;
	}
}
