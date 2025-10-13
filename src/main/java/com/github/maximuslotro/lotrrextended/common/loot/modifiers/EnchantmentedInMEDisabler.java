package com.github.maximuslotro.lotrrextended.common.loot.modifiers;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;
import com.google.gson.JsonObject;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRDimensions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;

public class EnchantmentedInMEDisabler extends LootModifier {

	public EnchantmentedInMEDisabler(ILootCondition[] conditions) {
		super(conditions);
	}
	@Nonnull
	@Override
	public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		ServerWorld world = context.getLevel();
		if(LOTRDimensions.isModDimension(world)) {
			if(!ExtendedServerConfig.enableVanillaEnchanting.get()) {
				generatedLoot.removeIf(stack -> (stack.getItem() == Items.ENCHANTED_BOOK));
				generatedLoot.removeIf(ItemStack::isEnchanted);
				return generatedLoot;
			}
			if(!ExtendedServerConfig.enableExtendedModifiers.get()) {
				generatedLoot.removeIf(stack -> (stack.getItem() == ExtendedItems.MODIFIER_SCROLL.get()));
				generatedLoot.removeIf(ExtendedModifierUtils::hasModifiers);
				return generatedLoot;
			}
		}
		return generatedLoot;
	}
	public static class Serializer extends GlobalLootModifierSerializer<EnchantmentedInMEDisabler> {
		@Override
		public EnchantmentedInMEDisabler read(ResourceLocation name, JsonObject jObject, ILootCondition[] conditions) {
			return new EnchantmentedInMEDisabler(conditions);
		}
		@Override
		public JsonObject write(EnchantmentedInMEDisabler instance) {
			return makeConditions(instance.conditions);
		}
	}
}