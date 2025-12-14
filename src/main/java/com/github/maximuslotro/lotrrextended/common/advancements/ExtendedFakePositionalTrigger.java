package com.github.maximuslotro.lotrrextended.common.advancements;

import com.github.maximuslotro.lotrrextended.common.advancements.predicates.ExtendedFakeLocationPredicate;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFakePositionalTrigger extends AbstractCriterionTrigger<ExtendedFakePositionalTrigger.Instance> {
	private final ResourceLocation id;

	public ExtendedFakePositionalTrigger(ResourceLocation p_i47432_1_) {
		this.id = p_i47432_1_;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public ExtendedFakePositionalTrigger.Instance createInstance(JsonObject pJson, EntityPredicate.AndPredicate pEntityPredicate, ConditionArrayParser pConditionsParser) {
		JsonObject jsonobject = JSONUtils.getAsJsonObject(pJson, "location", pJson);
		ExtendedFakeLocationPredicate locationpredicate = ExtendedFakeLocationPredicate.fromJson(jsonobject);
		return new ExtendedFakePositionalTrigger.Instance(this.id, pEntityPredicate, locationpredicate);
	}

	public void trigger(ServerPlayerEntity pPlayer) {
		this.trigger(pPlayer, (p_226923_1_) -> {
			return p_226923_1_.matches(pPlayer.getLevel(), pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
		});
	}

	public static class Instance extends CriterionInstance {
		private final ExtendedFakeLocationPredicate location;

		public Instance(ResourceLocation p_i231661_1_, EntityPredicate.AndPredicate p_i231661_2_, ExtendedFakeLocationPredicate p_i231661_3_) {
			super(p_i231661_1_, p_i231661_2_);
			this.location = p_i231661_3_;
		}

		public static ExtendedFakePositionalTrigger.Instance located(ExtendedFakeLocationPredicate pLocation) {
			return new ExtendedFakePositionalTrigger.Instance(new ResourceLocation("location"), EntityPredicate.AndPredicate.ANY, pLocation);
		}

		public boolean matches(ServerWorld pLevel, double pX, double pY, double pZ) {
			return this.location.matches(pLevel, pX, pY, pZ);
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer pConditions) {
			JsonObject jsonobject = super.serializeToJson(pConditions);
			jsonobject.add("location", this.location.serializeToJson());
			return jsonobject;
		}
	}
}