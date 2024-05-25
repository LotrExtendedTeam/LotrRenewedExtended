package com.github.maximuslotro.lotrrextended.common.advancements;

import net.minecraft.advancements.CriteriaTriggers;

public class ExtendedCriteriaTriggers {
	public static final AlignmentLevelTrigger ALIGNMENT_TRIGGER = CriteriaTriggers.register(new AlignmentLevelTrigger());
	public static final TraderTradedTrigger TRAGER_TRADED_TRIGGER = CriteriaTriggers.register(new TraderTradedTrigger());
	public static void generate() {}
}
