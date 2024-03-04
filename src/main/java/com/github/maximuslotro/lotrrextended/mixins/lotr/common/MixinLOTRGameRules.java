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
	 * @reason Because I'm Lazy and didn't want to write an inject
	 * @author maximuslotro
	 * */
	@Overwrite(remap = false)
	private static <T extends GameRules.RuleValue<T>> RuleKey<T> register(String ruleName, Category category, RuleType<T> type) {
		if(ruleName.equals("custom_waypoint_creation")) {
			return null;
		}
		String namespacedName = new ResourceLocation("lotr", ruleName).toString();
		return GameRules.register(namespacedName, category, type);
	}
}
