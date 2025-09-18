package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;
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

public class ExtendedHiredUnitEquipmentPool {
	public final static ExtendedHiredUnitEquipmentPool EMPTY = new ExtendedHiredUnitEquipmentPool(null, null, null, null, null, null, null, null, null, null, false, false);
	// Armor pools
	private final List<ExtendedHiredUnitEquipmentItem> helmetPool;
	private final List<ExtendedHiredUnitEquipmentItem> chestplatePool;
	private final List<ExtendedHiredUnitEquipmentItem> leggingPool;
	private final List<ExtendedHiredUnitEquipmentItem> bootsPool;
	// Weapon pools
	private final List<ExtendedHiredUnitEquipmentItem> meleePool;
	private final List<ExtendedHiredUnitEquipmentItem> rangedPool;
	private final List<ExtendedHiredUnitEquipmentItem> mountedPool;
	private final List<ExtendedHiredUnitEquipmentItem> idlePool;
	private final List<ExtendedHiredUnitEquipmentItem> idleMountedPool;
	// Offhand pool
	private final List<ExtendedHiredUnitEquipmentItem> offhandPool;
	private boolean setIdleFromMelee;
	private boolean setIdleFromRanged;

	public ExtendedHiredUnitEquipmentPool() {
		this.helmetPool = new ArrayList<>();
		this.chestplatePool = new ArrayList<>();
		this.leggingPool = new ArrayList<>();
		this.bootsPool = new ArrayList<>();
		this.meleePool = new ArrayList<>();
		this.rangedPool = new ArrayList<>();
		this.mountedPool = new ArrayList<>();
		this.idlePool = new ArrayList<>();
		this.idleMountedPool = new ArrayList<>();
		this.offhandPool = new ArrayList<>();
		this.setIdleFromMelee = false;
		this.setIdleFromRanged = false;
	}

	public ExtendedHiredUnitEquipmentPool(List<ExtendedHiredUnitEquipmentItem> helmetPool, List<ExtendedHiredUnitEquipmentItem> chestplatePool, 
		List<ExtendedHiredUnitEquipmentItem> leggingPool, List<ExtendedHiredUnitEquipmentItem> bootsPool,
		List<ExtendedHiredUnitEquipmentItem> weaponPool, List<ExtendedHiredUnitEquipmentItem> rangedPool, 
		List<ExtendedHiredUnitEquipmentItem> mountedPool, List<ExtendedHiredUnitEquipmentItem> idleWeaponPool, 
		List<ExtendedHiredUnitEquipmentItem> idleMountedPool, List<ExtendedHiredUnitEquipmentItem> shieldPool, boolean setIdleFromMelee, boolean setIdleFromRanged) {
		this.helmetPool = helmetPool;
		this.chestplatePool = chestplatePool;
		this.leggingPool = leggingPool;
		this.bootsPool = bootsPool;
		this.meleePool = weaponPool;
		this.rangedPool = rangedPool;
		this.mountedPool = mountedPool;
		this.idlePool = idleWeaponPool;
		this.idleMountedPool = idleMountedPool;
		this.offhandPool = shieldPool;
		if(setIdleFromMelee && setIdleFromRanged) {
			ExtendedLog.warn("Both setIdleFromMelee && setIdleFromRanged are both enabled for a loaded unit profile, THIS IS NOT ALLOWED! Disabling both, please fix your error!");
			this.setIdleFromMelee = false;
			this.setIdleFromRanged = false;
		}else {
			this.setIdleFromMelee = setIdleFromMelee;
			this.setIdleFromRanged = setIdleFromRanged;
		}
	}

	public ItemStack getRandomHelmetStack(Random random) {
		return getRandomINTERNALStack(random, helmetPool);
	}

	public ItemStack getRandomChestplateStack(Random random) {
		return getRandomINTERNALStack(random, chestplatePool);
	}

	public ItemStack getRandomLeggingsStack(Random random) {
		return getRandomINTERNALStack(random, leggingPool);
	}

	public ItemStack getRandomBootsStack(Random random) {
		return getRandomINTERNALStack(random, bootsPool);
	}

	public ItemStack getRandomMeleeStack(Random random) {
		return getRandomINTERNALStack(random, meleePool);
	}

	public ItemStack getRandomRangedStack(Random random) {
		return getRandomINTERNALStack(random, rangedPool);
	}

	public ItemStack getRandomMountedStack(Random random) {
		return getRandomINTERNALStack(random, mountedPool);
	}

	public ItemStack getRandomIdleStack(Random random) {
		return getRandomINTERNALStack(random, idlePool);
	}

	public ItemStack getRandomIdleMountedStack(Random random) {
		return getRandomINTERNALStack(random, idleMountedPool);
	}

	public ItemStack getRandomOffhandStack(Random random) {
		return getRandomINTERNALStack(random, offhandPool);
	}

	private static ItemStack getRandomINTERNALStack(Random random, List<ExtendedHiredUnitEquipmentItem> pool) {
		if(pool==null || pool.isEmpty()) {
			return ItemStack.EMPTY;
		}
		ItemStack generated = WeightedRandom.getRandomItem(random, pool).getStack();
		if(generated.isDamageableItem() && !ExtendedModifierUtils.hasRandomModifiers(generated)) {
			if(!ExtendedModifierUtils.hasModifiers(generated)) {
				ExtendedModifierUtils.generateItemModifiers(random, generated, false, false);
			}
			ExtendedModifierUtils.addModifierAddedTag(generated);
		}
		return generated;
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

	protected void addMeleeStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, meleePool);
	}

	protected void addRangedStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, rangedPool);
	}

	protected void addMountedStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, mountedPool);
	}

	protected void addIdleStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, idlePool);
	}

	protected void addIdleMountedStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, idleMountedPool);
	}

	protected void addOffhandStack(ItemStack item, int weight) {
		addINTERNALStack(item, weight, offhandPool);
	}

	private static void addINTERNALStack(ItemStack item, int weight, List<ExtendedHiredUnitEquipmentItem> pool) {
		if(weight<=0) {
			weight=1;
			System.out.println("UnitProfile stack weight should not be less than 1! Offender: "+item.toString());
		}
		pool.add(new ExtendedHiredUnitEquipmentItem(item, weight));
	}

	protected void setIdlePoolFromMeleePool() {
		this.setIdleFromMelee = true;
		this.setIdleFromRanged = false;
	}

	protected void setIdlePoolFromRangedPool() {
		this.setIdleFromMelee = false;
		this.setIdleFromRanged = true;
	}

	protected void disableIdleWeaponUsingOtherPools() {
		this.setIdleFromMelee = false;
		this.setIdleFromRanged = false;
	}

	public boolean isSetIdleFromMelee() {
		return setIdleFromMelee;
	}

	public boolean isSetIdleFromRanged() {
		return setIdleFromRanged;
	}

	public boolean isSetIdleFromPool() {
		return !(setIdleFromRanged || setIdleFromMelee);
	}

	public static class Serializer implements JsonDeserializer<ExtendedHiredUnitEquipmentPool>, JsonSerializer<ExtendedHiredUnitEquipmentPool> {
		@Override
		public ExtendedHiredUnitEquipmentPool deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedHiredUnitEquipmentPool");
			List<ExtendedHiredUnitEquipmentItem> helmetPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "helmets", new ExtendedHiredUnitEquipmentItem[0], context, ExtendedHiredUnitEquipmentItem[].class));
			List<ExtendedHiredUnitEquipmentItem> chestplatePool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "chestplates", new ExtendedHiredUnitEquipmentItem[0], context, ExtendedHiredUnitEquipmentItem[].class));
			List<ExtendedHiredUnitEquipmentItem> leggingPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "leggings", new ExtendedHiredUnitEquipmentItem[0], context, ExtendedHiredUnitEquipmentItem[].class));
			List<ExtendedHiredUnitEquipmentItem> bootsPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "boots", new ExtendedHiredUnitEquipmentItem[0], context, ExtendedHiredUnitEquipmentItem[].class));
			List<ExtendedHiredUnitEquipmentItem> weaponPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "melee", new ExtendedHiredUnitEquipmentItem[0], context, ExtendedHiredUnitEquipmentItem[].class));
			List<ExtendedHiredUnitEquipmentItem> rangedPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "ranged", new ExtendedHiredUnitEquipmentItem[0], context, ExtendedHiredUnitEquipmentItem[].class));
			List<ExtendedHiredUnitEquipmentItem> mountedPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "mounted", new ExtendedHiredUnitEquipmentItem[0], context, ExtendedHiredUnitEquipmentItem[].class));
			List<ExtendedHiredUnitEquipmentItem> idlePool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "idle", new ExtendedHiredUnitEquipmentItem[0], context, ExtendedHiredUnitEquipmentItem[].class));
			List<ExtendedHiredUnitEquipmentItem> idleMountedPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "idle_mounted", new ExtendedHiredUnitEquipmentItem[0], context, ExtendedHiredUnitEquipmentItem[].class));
			List<ExtendedHiredUnitEquipmentItem> offhandPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "offhand", new ExtendedHiredUnitEquipmentItem[0], context, ExtendedHiredUnitEquipmentItem[].class));
			boolean setIdleFromMelee = JSONUtils.getAsBoolean(jsonobject, "setIdleFromMelee", false);
			boolean setIdleFromRanged = JSONUtils.getAsBoolean(jsonobject, "setIdleFromRanged", false);
			return new ExtendedHiredUnitEquipmentPool(helmetPool, chestplatePool, leggingPool, bootsPool, weaponPool, rangedPool, mountedPool, idlePool, idleMountedPool, offhandPool, setIdleFromMelee, setIdleFromRanged);
		}

		@Override
		public JsonElement serialize(ExtendedHiredUnitEquipmentPool unitEquipmentInstance, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.add("helmets", context.serialize(unitEquipmentInstance.helmetPool));
			jsonobject.add("chestplates", context.serialize(unitEquipmentInstance.chestplatePool));
			jsonobject.add("leggings", context.serialize(unitEquipmentInstance.leggingPool));
			jsonobject.add("boots", context.serialize(unitEquipmentInstance.bootsPool));
			if(unitEquipmentInstance.setIdleFromMelee) {
				jsonobject.addProperty("setIdleFromMelee", unitEquipmentInstance.setIdleFromMelee);
			}
			if(unitEquipmentInstance.setIdleFromRanged) {
				jsonobject.addProperty("setIdleFromRanged", unitEquipmentInstance.setIdleFromRanged);
			}
			if(!unitEquipmentInstance.meleePool.isEmpty()) {
				jsonobject.add("melee", context.serialize(unitEquipmentInstance.meleePool));

			}
			if(!unitEquipmentInstance.rangedPool.isEmpty()) {
				jsonobject.add("ranged", context.serialize(unitEquipmentInstance.rangedPool));

			}
			if(!unitEquipmentInstance.mountedPool.isEmpty()) {
				jsonobject.add("mounted", context.serialize(unitEquipmentInstance.mountedPool));

			}
			if(!unitEquipmentInstance.idlePool.isEmpty()) {
				jsonobject.add("idle", context.serialize(unitEquipmentInstance.idlePool));

			}
			if(!unitEquipmentInstance.idleMountedPool.isEmpty()) {
				jsonobject.add("idle_mounted", context.serialize(unitEquipmentInstance.idleMountedPool));

			}
			if(!unitEquipmentInstance.offhandPool.isEmpty()) {
				jsonobject.add("offhand", context.serialize(unitEquipmentInstance.offhandPool));

			}
			return jsonobject;
		}
	}
}