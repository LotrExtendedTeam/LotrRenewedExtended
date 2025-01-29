package com.github.maximuslotro.lotrrextended.mixins.lotr.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.LOTRGameRules;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Category;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraft.world.GameRules.RuleType;

@Mixin(LOTRGameRules.class)
public class MixinLOTRGameRules {

	/**
	 * @reason Need to remove custom Waypoint Gamerule to allow for differentiating between legacy and renewed Waypoints in the future
	 * @author maximuslotro
	 */
	@Overwrite(remap = false)
	private static <T extends GameRules.RuleValue<T>> RuleKey<T> register(String ruleName, Category category, RuleType<T> type) {
		// Extended Start
		if(ruleName.equals("custom_waypoint_creation")) {
			return null;
		}
		// Extended End
		String namespacedName = new ResourceLocation("lotr", ruleName).toString();
		return GameRules.register(namespacedName, category, type);
	}
}
