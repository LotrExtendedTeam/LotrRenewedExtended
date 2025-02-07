package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.WeightedRandom;

public class ExtendedHiredUnitEquipmentInstance {
	public final static ExtendedHiredUnitEquipmentInstance EMPTY = new ExtendedHiredUnitEquipmentInstance(null, null, null, null, null, null);
	private final List<ExtendedHiredUnitEquipmentEntry> helmetPool;
	private final List<ExtendedHiredUnitEquipmentEntry> chestplatePool;
	private final List<ExtendedHiredUnitEquipmentEntry> leggingPool;
	private final List<ExtendedHiredUnitEquipmentEntry> bootsPool;
	private final List<ExtendedHiredUnitEquipmentEntry> weaponPool;
	private final List<ExtendedHiredUnitEquipmentEntry> shieldPool;

	public ExtendedHiredUnitEquipmentInstance() {
		this.helmetPool = new ArrayList<>();
		this.chestplatePool = new ArrayList<>();
		this.leggingPool = new ArrayList<>();
		this.bootsPool = new ArrayList<>();
		this.weaponPool = new ArrayList<>();
		this.shieldPool = new ArrayList<>();
	}

	public ExtendedHiredUnitEquipmentInstance(List<ExtendedHiredUnitEquipmentEntry> helmetPool, List<ExtendedHiredUnitEquipmentEntry> chestplatePool, 
		List<ExtendedHiredUnitEquipmentEntry> leggingPool, List<ExtendedHiredUnitEquipmentEntry> bootsPool,
		List<ExtendedHiredUnitEquipmentEntry> weaponPool, List<ExtendedHiredUnitEquipmentEntry> shieldPool) {
		this.helmetPool = helmetPool;
		this.chestplatePool = chestplatePool;
		this.leggingPool = leggingPool;
		this.bootsPool = bootsPool;
		this.weaponPool = weaponPool;
		this.shieldPool = shieldPool;

	}

	public ItemStack getRandomHelmetStack(Random random) {
		return getRandomINTERNALStack(random, helmetPool);
	}

	public ItemStack getRandomChestplateStack(Random random) {
		return getRandomINTERNALStack(random, chestplatePool);
	}

	public ItemStack getRandomLeggingStack(Random random) {
		return getRandomINTERNALStack(random, leggingPool);
	}

	public ItemStack getRandomBootStack(Random random) {
		return getRandomINTERNALStack(random, bootsPool);
	}

	public ItemStack getRandomWeaponStack(Random random) {
		return getRandomINTERNALStack(random, weaponPool);
	}

	public ItemStack getRandomShieldStack(Random random) {
		return getRandomINTERNALStack(random, shieldPool);
	}

	private static ItemStack getRandomINTERNALStack(Random random, List<ExtendedHiredUnitEquipmentEntry> pool) {
		if(pool==null || pool.isEmpty()) {
			return ItemStack.EMPTY;
		}
		return WeightedRandom.getRandomItem(random, pool).getStack();
	}

	protected void addHelmetStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, helmetPool);
	}

	protected void addChestplateStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, chestplatePool);
	}

	protected void addLeggingStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, leggingPool);
	}

	protected void addBootsStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, bootsPool);
	}

	protected void addWeaponStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, weaponPool);
	}

	protected void addShieldStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, shieldPool);
	}

	private static void addINTERNALStack(ItemStack item, int weight, List<ExtendedHiredUnitEquipmentEntry> pool) {
		pool.add(new ExtendedHiredUnitEquipmentEntry(item, weight));
	}

	public static class Serializer implements JsonDeserializer<ExtendedHiredUnitEquipmentInstance>, JsonSerializer<ExtendedHiredUnitEquipmentInstance> {
		@Override
		public ExtendedHiredUnitEquipmentInstance deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedHiredUnitEquipmentInstance");
			List<ExtendedHiredUnitEquipmentEntry> helmetPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "helmets", new ExtendedHiredUnitEquipmentEntry[0], context, ExtendedHiredUnitEquipmentEntry[].class));
			List<ExtendedHiredUnitEquipmentEntry> chestplatePool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "chestplates", new ExtendedHiredUnitEquipmentEntry[0], context, ExtendedHiredUnitEquipmentEntry[].class));
			List<ExtendedHiredUnitEquipmentEntry> leggingPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "leggings", new ExtendedHiredUnitEquipmentEntry[0], context, ExtendedHiredUnitEquipmentEntry[].class));
			List<ExtendedHiredUnitEquipmentEntry> bootsPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "boots", new ExtendedHiredUnitEquipmentEntry[0], context, ExtendedHiredUnitEquipmentEntry[].class));
			List<ExtendedHiredUnitEquipmentEntry> weaponPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "weapons", new ExtendedHiredUnitEquipmentEntry[0], context, ExtendedHiredUnitEquipmentEntry[].class));
			List<ExtendedHiredUnitEquipmentEntry> shieldPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "offhand", new ExtendedHiredUnitEquipmentEntry[0], context, ExtendedHiredUnitEquipmentEntry[].class));
			return new ExtendedHiredUnitEquipmentInstance(helmetPool, chestplatePool, leggingPool, bootsPool, weaponPool, shieldPool);
		}

		@Override
		public JsonElement serialize(ExtendedHiredUnitEquipmentInstance unitEquipmentInstance, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.add("helmets", context.serialize(unitEquipmentInstance.helmetPool));
			jsonobject.add("chestplates", context.serialize(unitEquipmentInstance.chestplatePool));
			jsonobject.add("leggings", context.serialize(unitEquipmentInstance.leggingPool));
			jsonobject.add("boots", context.serialize(unitEquipmentInstance.bootsPool));
			jsonobject.add("weapons", context.serialize(unitEquipmentInstance.weaponPool));
			jsonobject.add("offhand", context.serialize(unitEquipmentInstance.shieldPool));
			return jsonobject;
		}
	}
}