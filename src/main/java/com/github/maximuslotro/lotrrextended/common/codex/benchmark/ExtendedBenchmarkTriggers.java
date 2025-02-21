package com.github.maximuslotro.lotrrextended.common.codex.benchmark;

import java.util.Map;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedBlockBreakTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedConsumeItemTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedEntityHurtPlayerTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedIBenchmarkTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedInventoryChangeTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedPlacedBlockTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedPlayerEntityInteractionTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedPlayerHurtEntityTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedPositionTrigger;
import com.google.common.collect.Maps;

import net.minecraft.util.ResourceLocation;

/**
 * Codex implementation based upon {@Link CriteriaTriggers}'s logic 
 * */
public class ExtendedBenchmarkTriggers {
	private static final Map<ResourceLocation, ExtendedIBenchmarkTrigger<?>> CRITERIA = Maps.newHashMap();
	public static final ExtendedBlockBreakTrigger BROKE_BLOCK = register(new ExtendedBlockBreakTrigger());
	public static final ExtendedConsumeItemTrigger CONSUME_ITEM = register(new ExtendedConsumeItemTrigger());
	public static final ExtendedEntityHurtPlayerTrigger ENTITY_HURT_PLAYER = register(new ExtendedEntityHurtPlayerTrigger());
	public static final ExtendedInventoryChangeTrigger INVENTORY_CHANGE = register(new ExtendedInventoryChangeTrigger());
	public static final ExtendedPositionTrigger LOCATION = register(new ExtendedPositionTrigger());
	public static final ExtendedPlacedBlockTrigger PLACED_BLOCK = register(new ExtendedPlacedBlockTrigger());
	public static final ExtendedPlayerHurtEntityTrigger PLAYER_HURT_ENTITY = register(new ExtendedPlayerHurtEntityTrigger());
	public static final ExtendedPlayerEntityInteractionTrigger PLAYER_INTERACTED_WITH_ENTITY = register(new ExtendedPlayerEntityInteractionTrigger());

	public static <T extends ExtendedIBenchmarkTrigger<?>> T register(T pCriterion) {
		if (CRITERIA.containsKey(pCriterion.getId())) {
			throw new IllegalArgumentException("Duplicate benchmark id " + pCriterion.getId());
		} else {
			CRITERIA.put(pCriterion.getId(), pCriterion);
			return pCriterion;
		}
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public static <T extends ExtendedIBenchmarkInstance> ExtendedIBenchmarkTrigger<T> getBenchmark(ResourceLocation pId) {
		return (ExtendedIBenchmarkTrigger<T>) CRITERIA.get(pId);
	}

	public static Iterable<? extends ExtendedIBenchmarkTrigger<?>> all() {
		return CRITERIA.values();
	}
}
