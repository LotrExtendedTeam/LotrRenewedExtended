package com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedAbstractBenchmarkTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkInstance;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedEntityPredicate;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedBlockBreakTrigger extends ExtendedAbstractBenchmarkTrigger<ExtendedBlockBreakTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation("lotrextended", "break_block");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public ExtendedBlockBreakTrigger.Instance createInstance(JsonObject pJson, ExtendedEntityPredicate.AndPredicate pEntityPredicate, ConditionArrayParser pConditionsParser) {
		Block block = deserializeBlock(pJson);
		StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.fromJson(pJson.get("state"));
		if (block != null) {
			statepropertiespredicate.checkState(block.getStateDefinition(), (p_226948_1_) -> {
				throw new JsonSyntaxException("Block " + block + " has no property " + p_226948_1_ + ":");
			});
		}

		LocationPredicate locationpredicate = LocationPredicate.fromJson(pJson.get("location"));
		ItemPredicate itempredicate = ItemPredicate.fromJson(pJson.get("item"));
		return new ExtendedBlockBreakTrigger.Instance(pEntityPredicate, block, statepropertiespredicate, locationpredicate, itempredicate);
	}

	@Nullable
	private static Block deserializeBlock(JsonObject pObject) {
		if (pObject.has("block")) {
			ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getAsString(pObject, "block"));
			return ForgeRegistries.BLOCKS.getValue(resourcelocation);
		} else {
			return null;
		}
	}

	public void trigger(ServerPlayerEntity pPlayer, BlockPos pPos, ItemStack pItem) {
		BlockState blockstate = pPlayer.getLevel().getBlockState(pPos);
		this.trigger(pPlayer, (p_226949_4_) -> {
			return p_226949_4_.matches(blockstate, pPos, pPlayer.getLevel(), pItem);
		});
	}

	public static class Instance extends ExtendedBenchmarkInstance {
		private final Block brokenBlock;
		private final StatePropertiesPredicate brokenBlockState;
		private final LocationPredicate brokenBlockLocation;
		private final ItemPredicate playerHeldItem;

		public Instance(ExtendedEntityPredicate.AndPredicate p_i231810_1_, @Nullable Block block, StatePropertiesPredicate statePred, LocationPredicate lolcationPred, ItemPredicate itemPred) {
			super(ExtendedBlockBreakTrigger.ID, p_i231810_1_);
			this.brokenBlock = block;
			this.brokenBlockState = statePred;
			this.brokenBlockLocation = lolcationPred;
			this.playerHeldItem = itemPred;
		}

		public static ExtendedBlockBreakTrigger.Instance brokenBlock(Block pBlock) {
			return new ExtendedBlockBreakTrigger.Instance(ExtendedEntityPredicate.AndPredicate.ANY, pBlock, StatePropertiesPredicate.ANY, LocationPredicate.ANY, ItemPredicate.ANY);
		}

		public boolean matches(BlockState pState, BlockPos pPos, ServerWorld pLevel, ItemStack pItem) {
			if (this.brokenBlock != null && !pState.is(this.brokenBlock)) {
				return false;
			} else if (!this.brokenBlockState.matches(pState)) {
				return false;
			} else if (!this.brokenBlockLocation.matches(pLevel, pPos.getX(), pPos.getY(), pPos.getZ())) {
				return false;
			} else {
				return this.playerHeldItem.matches(pItem);
			}
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer pConditions) {
			JsonObject jsonobject = super.serializeToJson(pConditions);
			if (this.brokenBlock != null) {
				jsonobject.addProperty("block", ForgeRegistries.BLOCKS.getKey(this.brokenBlock).toString());
			}

			jsonobject.add("state", this.brokenBlockState.serializeToJson());
			jsonobject.add("location", this.brokenBlockLocation.serializeToJson());
			jsonobject.add("item", this.playerHeldItem.serializeToJson());
			return jsonobject;
		}
	}
}
