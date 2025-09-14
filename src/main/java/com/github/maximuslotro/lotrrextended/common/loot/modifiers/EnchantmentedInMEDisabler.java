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

import javax.annotation.Nonnull;

/**
 * Loot modifier that removes enchanted items and enchanted books
 * from loot in certain dimensions or under specific conditions.
 * <p>
 * This modifier can be used to disable enchanted drops globally
 * or selectively during fishing, based on server configuration
 * settings and specific fishing rods.
 * </p>
 */
public class EnchantmentedInMEDisabler extends LootModifier {
	/**
	 * List of fishing rod items (by {@link ResourceLocation}) that
	 * are affected by this modifier when {@link LootParameters#TOOL}
	 * or {@link FishingBobberEntity} is involved in fishing loot.
	 */
	List<ResourceLocation> fishingRods;
	/**
	 * Creates a new loot modifier instance.
	 *
	 * @param conditions   the loot conditions that must be satisfied for this modifier to apply
	 * @param fishingRods  the list of fishing rod {@link ResourceLocation}s affected by this modifier
	 */
	public EnchantmentedInMEDisabler(ILootCondition[] conditions, List<ResourceLocation> fishingRods) {
		super(conditions);
		this.fishingRods = fishingRods;
	}
	/**
	 * Applies the loot modifier to the generated loot.
	 * <p>
	 * Removes enchanted items and enchanted books if:
	 * <ul>
	 *     <li>The current dimension is a mod-specific dimension and enchanted loot is disabled in config.</li>
	 *     <li>The loot originates from fishing with a rod in the configured list and fishing enchanted items are disabled in config.</li>
	 * </ul>
	 * <p>
	 * Logs information about removed items including the dimension and loot origin.
	 * </p>
	 *
	 * @param generatedLoot the current list of loot items
	 * @param context       the loot context providing world, entity, tool, and position information
	 * @return the modified list of loot items
	 */
	@Nonnull
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
				if ((tool != null && fishingRods.contains(tool.getItem().getRegistryName())) || (entity instanceof FishingBobberEntity)) {
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
	/**
	 * Serializer for {@link EnchantmentedInMEDisabler}, handles reading and writing
	 * from JSON for global loot modifier registration.
	 */
	public static class Serializer extends GlobalLootModifierSerializer<EnchantmentedInMEDisabler> {
		/**
		 * Reads a {@link EnchantmentedInMEDisabler} instance from JSON.
		 *
		 * @param name       the resource location of the modifier
		 * @param jObject    the JSON object representing the modifier
		 * @param conditions the conditions for this loot modifier
		 * @return a new {@link EnchantmentedInMEDisabler} instance
		 */
		@Override
		public EnchantmentedInMEDisabler read(ResourceLocation name, JsonObject jObject, ILootCondition[] conditions) {
			List<ResourceLocation> fishingRods = new ArrayList<>();
			JsonArray rodArray = jObject.get("fishing_rods_affected").getAsJsonArray();
			for (JsonElement rod : rodArray) {
				fishingRods.add(new ResourceLocation(rod.getAsString()));
			}
			return new EnchantmentedInMEDisabler(conditions, fishingRods);
		}
		/**
		 * Writes a {@link EnchantmentedInMEDisabler} instance to JSON.
		 *
		 * @param instance the modifier instance to serialize
		 * @return a JSON object representing this loot modifier
		 */
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