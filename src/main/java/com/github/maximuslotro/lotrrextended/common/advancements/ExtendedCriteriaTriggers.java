package com.github.maximuslotro.lotrrextended.common.advancements;

import net.minecraft.advancements.CriteriaTriggers;

public class ExtendedCriteriaTriggers {
	public static final ExtendedAlignmentLevelTrigger ALIGNMENT_TRIGGER = CriteriaTriggers.register(new ExtendedAlignmentLevelTrigger());
	public static final ExtendedTraderTradedTrigger TRAGER_TRADED_TRIGGER = CriteriaTriggers.register(new ExtendedTraderTradedTrigger());
	public static final ExtendedBlockBreakTrigger PLAYER_BREAK_BLOCK_TRIGGER = CriteriaTriggers.register(new ExtendedBlockBreakTrigger());
	public static final ExtendedTameLivingClassTrigger TAME_LIVING_TRIGGER = CriteriaTriggers.register(new ExtendedTameLivingClassTrigger());
	public static final ExtendedUnitHiredTrigger UNIT_HIRED_TRIGGER = CriteriaTriggers.register(new ExtendedUnitHiredTrigger());
	public static final ExtendedCraftingTrigger FACTION_CRAFTING_TRIGGER = CriteriaTriggers.register(new ExtendedCraftingTrigger());
	public static final ExtendedAbstractStringTrigger ABSTRACT_STRING_TRIGGER = CriteriaTriggers.register(new ExtendedAbstractStringTrigger());

	public static void generate() {}
}
