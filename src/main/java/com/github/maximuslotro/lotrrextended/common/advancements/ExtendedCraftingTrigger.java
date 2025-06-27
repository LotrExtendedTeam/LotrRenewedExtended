package com.github.maximuslotro.lotrrextended.common.advancements;

import com.github.maximuslotro.lotrrextended.common.advancements.predicates.ExtendedSimpleBlockPredicate;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class ExtendedCraftingTrigger extends AbstractCriterionTrigger<ExtendedCraftingTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation("lotrextended", "crafting_with_block");

	/**
	 * @return The unique ID of this criterion trigger.
	 */
	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public ExtendedCraftingTrigger.Instance createInstance(JsonObject jsonObject, EntityPredicate.AndPredicate playerPredicate, ConditionArrayParser contextParser) {
		return new ExtendedCraftingTrigger.Instance(playerPredicate, ItemPredicate.fromJson(jsonObject.get("item")), ExtendedSimpleBlockPredicate.fromJson(jsonObject.get("crafting_block")));
	}

	public void trigger(ServerPlayerEntity player, ItemStack stack, Block craftingBlock) {
		this.trigger(player, (instance) -> instance.matches(stack, craftingBlock));
	}

	/**
	 * Represents an instance of the {@link ExtendedCraftingTrigger}.
	 * It holds the specific conditions under which the advancement should trigger.
	 */
	public static class Instance extends CriterionInstance {
		private final ItemPredicate item;
		private final ExtendedSimpleBlockPredicate craftingBlock;

		public Instance(EntityPredicate.AndPredicate playerPredicate, ItemPredicate itemProvider, ExtendedSimpleBlockPredicate craftingBlock) {
			super(ExtendedCraftingTrigger.ID, playerPredicate);
			this.item = itemProvider;
			this.craftingBlock = craftingBlock;
		}

		public static ExtendedCraftingTrigger.Instance craftedWithItem(IItemProvider possableItem, Block block) {
			return new ExtendedCraftingTrigger.Instance(EntityPredicate.AndPredicate.ANY, 
					ItemPredicate.Builder.item().of(possableItem).build(), new ExtendedSimpleBlockPredicate(block));
		}

		public static ExtendedCraftingTrigger.Instance crafted(Block block) {
			return new ExtendedCraftingTrigger.Instance(EntityPredicate.AndPredicate.ANY, ItemPredicate.ANY, new ExtendedSimpleBlockPredicate(block));
		}

		public boolean matches(ItemStack stack, Block block) {
			if (item.matches(stack) && craftingBlock.matches(block)) {
				return true;
			}
			return false;
		}

		/**
		 * Serializes this instance into a JSON object for saving in an advancement file.
		 *
		 * @param serializer The advancement condition serializer.
		 * @return A JSON representation of this trigger instance.
		 */
		@Override
		public JsonObject serializeToJson(ConditionArraySerializer serializer) {
			JsonObject jsonobject = super.serializeToJson(serializer);
			jsonobject.add("item", this.item.serializeToJson());
			jsonobject.add("crafting_block", this.craftingBlock.serializeToJson());
			return jsonobject;
		}
	}
}