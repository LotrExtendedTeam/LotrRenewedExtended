package com.github.maximuslotro.lotrrextended.common.codex.benchmark;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.github.maximuslotro.lotrrextended.common.codex.ExtendedPlayerCodexes;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedEntityPredicate;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedIBenchmarkTrigger;
import com.github.maximuslotro.lotrrextended.common.extensions.entity.player.ExtendedServerPlayerEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.LootContext;

/**
 * Codex implementation based upon {@Link AbstractCriterionTrigger}'s logic 
 * */
public abstract class ExtendedAbstractBenchmarkTrigger<T extends ExtendedBenchmarkInstance> implements ExtendedIBenchmarkTrigger<T> {
	private final Map<ExtendedPlayerCodexes, Set<ExtendedIBenchmarkTrigger.Listener<T>>> players = Maps.newIdentityHashMap();

	public final void addPlayerListener(ExtendedPlayerCodexes pPlayerAdvancements, ExtendedIBenchmarkTrigger.Listener<T> pListener) {
		this.players.computeIfAbsent(pPlayerAdvancements, (p_227072_0_) -> {
			return Sets.newHashSet();
		}).add(pListener);
	}

	public final void removePlayerListener(ExtendedPlayerCodexes pPlayerAdvancements, ExtendedIBenchmarkTrigger.Listener<T> pListener) {
		Set<ExtendedIBenchmarkTrigger.Listener<T>> set = this.players.get(pPlayerAdvancements);
		if (set != null) {
			set.remove(pListener);
			if (set.isEmpty()) {
				this.players.remove(pPlayerAdvancements);
			}
		}
	}

	public final void removePlayerListeners(ExtendedPlayerCodexes pPlayerAdvancements) {
		this.players.remove(pPlayerAdvancements);
	}

	protected abstract T createInstance(JsonObject pJson, ExtendedEntityPredicate.AndPredicate pEntityPredicate, ConditionArrayParser pConditionsParser);

	public final T createInstance(JsonObject pObject, ConditionArrayParser pConditions) {
		ExtendedEntityPredicate.AndPredicate entitypredicate$andpredicate = ExtendedEntityPredicate.AndPredicate.fromJson(pObject, "player", pConditions);
		return this.createInstance(pObject, entitypredicate$andpredicate, pConditions);
	}

	protected void trigger(ServerPlayerEntity pServerPlayer, Predicate<T> pTestTrigger) {
		ExtendedPlayerCodexes playeradvancements = ((ExtendedServerPlayerEntity)pServerPlayer).getPlayerCodexes();
		Set<ExtendedIBenchmarkTrigger.Listener<T>> set = this.players.get(playeradvancements);
		if (set != null && !set.isEmpty()) {
			LootContext lootcontext = ExtendedEntityPredicate.createContext(pServerPlayer, pServerPlayer);
			List<ExtendedIBenchmarkTrigger.Listener<T>> list = null;

			for(ExtendedIBenchmarkTrigger.Listener<T> listener : set) {
				T t = listener.getTriggerInstance();
				if (t.getPlayerPredicate().matches(lootcontext) && pTestTrigger.test(t)) {
					if (list == null) {
						list = Lists.newArrayList();
					}

					list.add(listener);
				}
			}

			if (list != null) {
				for(ExtendedIBenchmarkTrigger.Listener<T> listener1 : list) {
					listener1.run(playeradvancements);
				}
			}

		}
	}
}