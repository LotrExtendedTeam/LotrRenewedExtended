package com.github.maximuslotro.lotrextended.mixins.lotr.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.LOTRGameRules;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Category;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraft.world.GameRules.RuleType;

/**
 * Removes some Renewed GameRules in favor of the Extended config
 * @Mixin (LOTRGameRules.class)
 * */
@Mixin(LOTRGameRules.class)
public class MixinLOTRGameRules {

	/**
	 * @reason Need to remove custom Waypoint Gamerule to allow for differentiating between legacy and renewed Waypoints in the future
	 * @author maximuslotro
	 * @Overwrite (remap = false)
	 * @param <T> The type of {@link GameRules}.
	 * @param ruleName The name of the rule
	 * @param category The gamerule category
	 * @param type The data type for the rule
	 * @return A built GameRule
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
