package com.github.maximuslotro.lotrrextended.common.loot.modifiers;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;
import com.google.gson.JsonObject;

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

	public EnchantmentedInMEDisabler(ILootCondition[] pConditions) {
		super(pConditions);
	}
	@Nonnull
	@Override
	public List<ItemStack> doApply(List<ItemStack> pGeneratedLoot, LootContext pContext) {
		ServerWorld world = pContext.getLevel();
		if(LOTRDimensions.isModDimension(world)) {
			if(!ExtendedServerConfig.enableVanillaEnchanting.get() && ExtendedServerConfig.removeVanillaEnchanting.get()) {
				pGeneratedLoot.removeIf(stack -> (stack.getItem() == Items.ENCHANTED_BOOK));
				for (ItemStack stack : pGeneratedLoot) {
					if (stack.isEnchanted()) {
						ExtendedModifierUtils.clearEnchantments(stack); 
					}
				}
				return pGeneratedLoot;
			}
			if(!ExtendedServerConfig.enableExtendedModifiers.get() && ExtendedServerConfig.removeExtendedModifiers.get()) {
				for (ItemStack stack : pGeneratedLoot) {
					if (ExtendedModifierUtils.hasModifiers(stack) || ExtendedModifierUtils.hasRandomModifiers(stack)) {
						ExtendedModifierUtils.clearModifiers(stack);
						ExtendedModifierUtils.removeModifierAddedTag(stack);
					}
				}				
				return pGeneratedLoot;
			}
		}
		return pGeneratedLoot;
	}
	public static class Serializer extends GlobalLootModifierSerializer<EnchantmentedInMEDisabler> {
		@Override
		public EnchantmentedInMEDisabler read(ResourceLocation pName, JsonObject pObject, ILootCondition[] pConditions) {
			return new EnchantmentedInMEDisabler(pConditions);
		}
		@Override
		public JsonObject write(EnchantmentedInMEDisabler pInstance) {
			return makeConditions(pInstance.conditions);
		}
	}
}