package com.github.maximuslotro.lotrrextended.common.codex.benchmarks;

import com.github.maximuslotro.lotrrextended.common.codex.ExtendedCodexEntry;
import com.github.maximuslotro.lotrrextended.common.codex.ExtendedPlayerCodexes;
import com.google.gson.JsonObject;

import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

public interface IBenchmarkTrigger<T extends IBenchmarkInstance> {
	   ResourceLocation getId();

	   void addPlayerListener(ExtendedPlayerCodexes pPlayerAdvancements, IBenchmarkTrigger.Listener<T> pListener);

	   void removePlayerListener(ExtendedPlayerCodexes pPlayerAdvancements, IBenchmarkTrigger.Listener<T> pListener);

	   void removePlayerListeners(ExtendedPlayerCodexes pPlayerAdvancements);

	   T createInstance(JsonObject pObject, ConditionArrayParser pConditions);

	   public static class Listener<T extends IBenchmarkInstance> {
	      private final T trigger;
	      private final ExtendedCodexEntry codexEntry;
	      private final String criterion;

	      public Listener(T pTrigger, ExtendedCodexEntry codexEntry, String pCriterion) {
	         this.trigger = pTrigger;
	         this.codexEntry = codexEntry;
	         this.criterion = pCriterion;
	      }

	      public T getTriggerInstance() {
	         return this.trigger;
	      }

	      public void run(ExtendedPlayerCodexes pPlayerAdvancements) {
	         pPlayerAdvancements.award(this.codexEntry, this.criterion);
	      }

	      public boolean equals(Object object) {
	         if (this == object) {
	            return true;
	         } else if (object != null && this.getClass() == object.getClass()) {
	            IBenchmarkTrigger.Listener<?> listener = (IBenchmarkTrigger.Listener<?>)object;
	            if (!this.trigger.equals(listener.trigger)) {
	               return false;
	            } else {
	               return !this.codexEntry.equals(listener.codexEntry) ? false : this.criterion.equals(listener.criterion);
	            }
	         } else {
	            return false;
	         }
	      }

	      public int hashCode() {
	         int i = this.trigger.hashCode();
	         i = 31 * i + this.codexEntry.hashCode();
	         return 31 * i + this.criterion.hashCode();
	      }
	   }
	}
