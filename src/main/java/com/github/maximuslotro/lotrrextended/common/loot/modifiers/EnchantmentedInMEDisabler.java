package com.github.maximuslotro.lotrrextended.common.loot.modifiers;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lotr.common.init.LOTRDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class EnchantmentedInMEDisabler extends LootModifier {
	List<ResourceLocation> fishingRods;

	public EnchantmentedInMEDisabler(ILootCondition[] conds, List<ResourceLocation> fishingRods) {
		super(conds);
		this.fishingRods = fishingRods;
	}

	@Override
	public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		ServerWorld world = context.getLevel();
		if(LOTRDimensions.isModDimension(world)) {
			if(!ExtendedServerConfig.enableEnchantedLootDrops.get()) {
				Vector3d location = context.getParamOrNull(LootParameters.ORIGIN);
				if(location==null) {location = new Vector3d(0d, 0d, 0d);} //Fix a crash with null locations of piglin bartering
				if(generatedLoot.removeIf(item -> (item.getItem() == Items.ENCHANTED_BOOK))) {
					ExtendedLog.info("Removed Enchanted Book from loot in %s at %s ", context.getLevel().dimension().location(), location.toString());
				}
				if(generatedLoot.removeIf(ItemStack::isEnchanted)) {
					ExtendedLog.info("Removed Enchanted Item from loot in %s at %s ", context.getLevel().dimension().location(), location.toString());
				}
				return generatedLoot;
			}
			if(!ExtendedServerConfig.enableFishingEnchantedItems.get()) {
				ItemStack tool = context.getParamOrNull(LootParameters.TOOL);
				Vector3d location = context.getParamOrNull(LootParameters.ORIGIN);
				Entity entity = context.getParamOrNull(LootParameters.THIS_ENTITY);
				if ((tool != null && fishingRods.contains(tool.getItem().getRegistryName())) || (entity != null && entity instanceof FishingBobberEntity)) {
					if(location==null) {location = new Vector3d(0d, 0d, 0d);} //Fix a crash with null locations of piglin bartering
					if(generatedLoot.removeIf(item -> (item.getItem() == Items.ENCHANTED_BOOK))) {
						ExtendedLog.info("Removed Enchanted Book from fishing loot in %s at %s ", context.getLevel().dimension().location(), location.toString());
					}
					if(generatedLoot.removeIf(ItemStack::isEnchanted)) {
						ExtendedLog.info("Removed Enchanted Item from fishing loot in %s at %s ", context.getLevel().dimension().location(), location.toString());
					}
				}
			}
		}
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<EnchantmentedInMEDisabler> {
		@Override
		public EnchantmentedInMEDisabler read(ResourceLocation name, JsonObject jObject, ILootCondition[] conditions) {
			List<ResourceLocation> fishingRods = new ArrayList<>();
			JsonArray rodArray = jObject.get("fishing_rods_affected").getAsJsonArray();
			for (JsonElement rod : rodArray) {
				fishingRods.add(new ResourceLocation(rod.getAsString()));
			}
			return new EnchantmentedInMEDisabler(conditions, fishingRods);
		}

		@Override
		public JsonObject write(EnchantmentedInMEDisabler instance) {
			JsonObject jObject = makeConditions(instance.conditions);
			JsonArray list = new JsonArray();
			for (ResourceLocation fishingRodResourceLocation : instance.fishingRods)
				list.add(fishingRodResourceLocation.toString());
			jObject.add("fishing_rods_affected", list);
			return jObject;
		}
	}
}
