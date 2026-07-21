package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.lang.reflect.Type;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedServerHiredUnitProfile {
	public static final ExtendedServerHiredUnitProfile EMPTY = new ExtendedServerHiredUnitProfile(LotrExtendedMod.EMPTY, false, "EMPTY", "EMPTY", "EMPTY", ExtendedHiredUnitEquipmentPool.EMPTY, 100000, false, 1000, ExtendedUnitCombatTypes.MELEE, EntityType.PIG);
	private final ResourceLocation id;
	private final boolean translateStrings;
	private final String entity_title;
	private final String entity_shortname;
	private final String entity_unit_type;
	private final ExtendedHiredUnitEquipmentPool equipmentPools;
	private final int hiringCost;
	private final boolean hiringRequiresPledge;
	private final int hiringAlignmentRequired;
	private final ExtendedUnitCombatTypes attackType;
	private final EntityType<?> unitEntityType;

	public ExtendedServerHiredUnitProfile(ResourceLocation id, boolean translateStrings, String entity_title, String entity_shortname, String entity_unit_type, ExtendedHiredUnitEquipmentPool equipmentPools, int hiringCost, boolean hiringRequiresPledge, int hiringAlignmentRequired, ExtendedUnitCombatTypes attackType, EntityType<?> unitEntityType) {
		this.id = id;
		this.translateStrings = translateStrings;
		this.entity_title = entity_title;
		this.entity_shortname = entity_shortname;
		this.entity_unit_type = entity_unit_type;
		this.equipmentPools = equipmentPools;
		this.hiringCost = hiringCost;
		this.hiringRequiresPledge = hiringRequiresPledge;
		this.hiringAlignmentRequired = hiringAlignmentRequired;
		this.attackType = attackType;
		this.unitEntityType = unitEntityType;
	}

	/*
	 * Get the id of this ExtendedServerHiredUnitProfile
	 * */
	public ResourceLocation getId() {
		return id;
	}

	/*
	 * Check if the strings of this profile should be localized
	 * */
	public boolean stringsAreTranslated() {
		return translateStrings;
	}

	/*
	 * Gets the string or key for the unit's title
	 * */
	public String getEntityUnitTitle() {
		return entity_title;
	}

	/*
	 * Gets the string or key for the unit's shorthand name
	 * */
	public String getEntityUnitShorthandName() {
		return entity_shortname;
	}

	/*
	 * Gets the string or key for the unit's type name
	 * */
	public String getEntityUnitTypeName() {
		return entity_unit_type;
	}

	/**
	 * Check if unitType is of EMPTY type
	 * Could mean some data corruption happened
	 * @return Returns TRUE if this is a corrupted hired unit type
	 * */
	public boolean isEmptyUnitType() {
		return this.equals(ExtendedServerHiredUnitProfile.EMPTY) || id.equals(LotrExtendedMod.EMPTY);
	}

	/**
	 * Gets the equipment item pools for the unit profile
	 * @return the pool
	 * @see ExtendedHiredUnitEquipmentPool
	 * */
	public ExtendedHiredUnitEquipmentPool getEquipmentPools() {
		return equipmentPools;
	}

	/**
	 * Hiring cost for this unit profile
	 * @return the cost in coins
	 * */
	public int getHiringCost() {
		return hiringCost;
	}

	/**
	 * Does this unit profile require pledge to hire?
	 * @return if pledge is required
	 * */
	public boolean isHiringRequiresPledge() {
		return hiringRequiresPledge;
	}

	/**
	 * Hiring aligment level for the unit
	 * @return the alignment level
	 * */
	public int getHiringAlignmentRequired() {
		return hiringAlignmentRequired;
	}

	/**
	 * Gets the attack type enum for this profile
	 * @see ExtendedUnitCombatTypes
	 * @return The attack type enum instance
	 * */
	public ExtendedUnitCombatTypes getUnitType() {
		return attackType;
	}

	/**
	 * Gets the unit entity type for this profile
	 * @see EntityType
	 * @return The EntityType instance for this profile
	 * */
	public EntityType<?> getUnitEntityType() {
		return unitEntityType;
	}

	/*
	 * Serializer for ExtendedServerHiredUnitProfile
	 * */
	public static class Serializer implements JsonDeserializer<ExtendedServerHiredUnitProfile>, JsonSerializer<ExtendedServerHiredUnitProfile> {
		@Override
		public ExtendedServerHiredUnitProfile deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedServerHiredUnitProfile");
			String id = jsonobject.get("id").getAsString();
			if(id==null||!id.contains(":")) {
				ExtendedLog.warn("Failed to load hired unit profile due to invalid id: {}!", id);
				return null;
			}
			EntityType<?> entityType = getEntityTypeFromString(jsonobject.get("entity_type").getAsString());
			if (entityType == null) {
				ExtendedLog.warn("Failed to load unit entity type: {}", jsonobject.get("entity_type").getAsString());
				return null;
			}
			// Language
			JsonObject languageObject = jsonobject.get("language").getAsJsonObject();
			boolean translateStrings = languageObject.get("translateStrings").getAsBoolean();
			String entity_title = languageObject.get("entity_title").getAsString();
			String entity_shortname = languageObject.get("entity_shortname").getAsString();
			String entity_unit_type = languageObject.get("entity_unit_type").getAsString();
			// Hiring
			JsonObject hiringObject = jsonobject.get("hiring").getAsJsonObject();
			int hiringCost = hiringObject.get("hiringCost").getAsInt();
			boolean hiringRequiresPledge = hiringObject.get("hiringRequiresPledge").getAsBoolean();
			int hiringAlignmentRequired = hiringObject.get("hiringAlignmentRequired").getAsInt();
			// Attack information
			ExtendedUnitCombatTypes attackType;
			if(jsonobject.has("attack_type")) {
				attackType = ExtendedUnitCombatTypes.byName(jsonobject.get("attack_type").getAsString());
			}else {
				// Fallback for incorrect placement that occurred before 1.10.2
				attackType = ExtendedUnitCombatTypes.byName(languageObject.get("attack_type").getAsString());
			}
			// Equipment
			ExtendedHiredUnitEquipmentPool equipmentPools = JSONUtils.getAsObject(jsonobject, "equipmentPools", ExtendedHiredUnitEquipmentPool.EMPTY, context, ExtendedHiredUnitEquipmentPool.class);
			return new ExtendedServerHiredUnitProfile(new ResourceLocation(id), translateStrings, entity_title, entity_shortname, entity_unit_type, equipmentPools, hiringCost, hiringRequiresPledge, hiringAlignmentRequired, attackType, entityType);
		}

		@Override
		public JsonElement serialize(ExtendedServerHiredUnitProfile unitProfile, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", unitProfile.getId().toString());
			jsonobject.addProperty("entity_type", unitProfile.unitEntityType.getRegistryName().toString());
			// Language
			JsonObject languageObject = new JsonObject();
			languageObject.addProperty("translateStrings", unitProfile.stringsAreTranslated());
			languageObject.addProperty("entity_title", unitProfile.getEntityUnitTitle());
			languageObject.addProperty("entity_shortname", unitProfile.getEntityUnitShorthandName());
			languageObject.addProperty("entity_unit_type", unitProfile.getEntityUnitTypeName());
			jsonobject.add("language", languageObject);
			// Hiring
			JsonObject hiringObject = new JsonObject();
			hiringObject.addProperty("hiringCost", unitProfile.getHiringCost());
			hiringObject.addProperty("hiringRequiresPledge", unitProfile.isHiringRequiresPledge());
			hiringObject.addProperty("hiringAlignmentRequired", unitProfile.getHiringAlignmentRequired());
			jsonobject.add("hiring", hiringObject);
			// Attack information
			jsonobject.addProperty("attack_type", unitProfile.getUnitType().getOperationTypeName());
			// Equipment
			jsonobject.add("equipmentPools", context.serialize(unitProfile.equipmentPools));
			return jsonobject;
		}
	}

	public static class Builder {
		private boolean translateStrings;
		private String entity_title;
		private String entity_shortname;
		private String entity_unit_type;
		private ExtendedHiredUnitEquipmentPool equipmentPools = new ExtendedHiredUnitEquipmentPool();
		private int hiringCost;
		private boolean hiringRequiresPledge;
		private int hiringAlignmentRequired;
		private ExtendedUnitCombatTypes attackType;
		private final EntityType<?> unitEntityType;
		private final String modId;

		public Builder(String modId, EntityType<?> unitEntityType, ExtendedUnitCombatTypes pAttackType) {
			this.attackType = pAttackType;
			this.unitEntityType = unitEntityType;
			this.modId = modId;
			// TODO remove and replace with new version of setLocals
			this.entity_title = "entity."+modId+".hiredunits.entity_title";
			DataGenValueHolder.hiredUnitStrings.put(entity_title, "%1$s the %2$s");
			this.entity_shortname = "entity."+modId+".hiredunits.entity_shortname";
			DataGenValueHolder.hiredUnitStrings.put(entity_shortname, "%1$s");
			this.entity_unit_type = unitEntityType.getDescriptionId();
			this.translateStrings = true;
		}

		/**
		 * Set Hired Unit string localization.
		 * @param translateStrings Whether to use translation keys or raw strings
		 * @param nameKey The key suffix used for localization keys
		 * @param name The display name used if not translating
		 * @return This builder instance for chaining
		 * @deprecated Needs a complete re-write as the way hired unit name and title locals worked was changed, but kept in a working state so LOTR Companions can properly generate unit profiles
		 */
		public ExtendedServerHiredUnitProfile.Builder setLocal(boolean translateStrings, String nameKey, String name) {
			this.translateStrings = translateStrings;
			if(translateStrings) {
				this.entity_title = "entity_title."+modId+".hired_"+nameKey;
				DataGenValueHolder.hiredUnitStrings.put(entity_title, "%1$s the "+name);
				this.entity_shortname = "entity_shortname."+modId+".hired_"+nameKey;
				DataGenValueHolder.hiredUnitStrings.put(entity_shortname, "%1$s");
				this.entity_unit_type = "entity_unit_type."+modId+".hired_"+nameKey;
				DataGenValueHolder.hiredUnitStrings.put(entity_unit_type, name);
			}else {
				this.entity_title = name;
				this.entity_shortname = name;
				this.entity_unit_type = name;
			}
			return this;
		}

		/**
		 * Set Hired Unit purchase info.
		 * 
		 * @param cost The hiring cost (minimum 1)
		 * @param pledgeRequired Whether hiring requires a pledge
		 * @param alignmnetRequired The alignment required to hire (minimum 1)
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder setPurchaseInfo(int cost, boolean pledgeRequired, int alignmnetRequired) {
			this.hiringRequiresPledge = pledgeRequired;
			if(cost<=0) {
				cost=1;
			}
			this.hiringCost = cost;
			if(alignmnetRequired<=0) {
				alignmnetRequired=1;
			}
			this.hiringAlignmentRequired = alignmnetRequired;
			return this;
		}
		/**
		 * Add a helmet item to the equipment pool with weight.
		 *
		 * @param stack The item stack representing the helmet
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addHelmetPoolItem(ItemStack stack, int weight) {
			if(stack.getItem() instanceof ArmorItem && ((ArmorItem)stack.getItem()).getSlot()!=EquipmentSlotType.HEAD){
				System.out.println("Tried setting invalid helmet for hired units: "+stack.toString());
				throw new IllegalArgumentException("Invalid Helmet");
			}
			equipmentPools.addHelmetStack(setBaseItem(stack, "item.lotrextended.base_gear.helmet"), weight);
			return this;
		}
		/**
		 * Add a chestplate item to the equipment pool with weight.
		 *
		 * @param stack The item stack representing the chestplate
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addChestplatePoolItem(ItemStack stack, int weight) {
			if(stack.getItem() instanceof ArmorItem && ((ArmorItem)stack.getItem()).getSlot()!=EquipmentSlotType.CHEST){
				System.out.println("Tried setting invalid chestplate for hired units: "+stack.toString());
				throw new IllegalArgumentException("Invalid Chestplate");
			}
			equipmentPools.addChestplateStack(setBaseItem(stack, "item.lotrextended.base_gear.chestplate"), weight);
			return this;
		}
		/**
		 * Add leggings item to the equipment pool with weight.
		 *
		 * @param stack The item stack representing the leggings
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addLeggingPoolItem(ItemStack stack, int weight) {
			if(stack.getItem() instanceof ArmorItem && ((ArmorItem)stack.getItem()).getSlot()!=EquipmentSlotType.LEGS){
				System.out.println("Tried setting invalid legging for hired units: "+stack.toString());
				throw new IllegalArgumentException("Invalid Leggings");
			}
			equipmentPools.addLeggingStack(setBaseItem(stack, "item.lotrextended.base_gear.leggings"), weight);
			return this;
		}
		/**
		 * Add boots item to the equipment pool with weight.
		 *
		 * @param stack The item stack representing the boots
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addBootsPoolItem(ItemStack stack, int weight) {
			if(stack.getItem() instanceof ArmorItem && ((ArmorItem)stack.getItem()).getSlot()!=EquipmentSlotType.FEET){
				System.out.println("Tried setting invalid boots for hired units: "+stack.toString());
				throw new IllegalArgumentException("Invalid Boots");
			}
			equipmentPools.addBootsStack(setBaseItem(stack, "item.lotrextended.base_gear.boots"), weight);
			return this;
		}
		/**
		 * Add melee weapon item to the equipment pool with weight.
		 *
		 * @param stack The item stack representing the melee weapon
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addMeleePoolItem(ItemStack stack, int weight) {
			equipmentPools.addMeleeStack(setBaseItem(stack, "item.lotrextended.base_gear.weapon"), weight);
			return this;
		}
		/**
		 * Add melee weapon item to the equipment pool with weight.
		 *
		 * @param stack The item stack representing the melee weapon
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addToolPoolItem(ItemStack stack, int weight) {
			equipmentPools.addMeleeStack(setBaseItem(stack, "item.lotrextended.base_gear.tool"), weight);
			return this;
		}
		/**
		 * Add ranged weapon item to the equipment pool with weight (only if attack type supports ranged).
		 *
		 * @param stack The item stack representing the ranged weapon
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addRangedPoolItem(ItemStack stack, int weight) {
			if(attackType.isRanged()) {
				equipmentPools.addRangedStack(setBaseItem(stack, "item.lotrextended.base_gear.weapon_ranged"), weight);
			}
			return this;
		}
		/**
		 * Add mounted weapon item to the equipment pool with weight (only if attack type supports mounted).
		 *
		 * @param stack The item stack representing the mounted weapon
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addMountedPoolItem(ItemStack stack, int weight) {
			if(attackType.isMounted()) {
				equipmentPools.addMountedStack(setBaseItem(stack, "item.lotrextended.base_gear.weapon_mounted"), weight);
			}
			return this;
		}
		/**
		 * Add idle weapon item to the equipment pool with weight.
		 *
		 * @param stack The item stack representing the idle weapon
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addIdlePoolItem(ItemStack stack, int weight) {
			equipmentPools.addIdleStack(setBaseItem(stack, "item.lotrextended.base_gear.weapon_idle"), weight);
			return this;
		}
		/**
		 * Add idle mounted weapon item to the equipment pool with weight (only if attack type supports mounted).
		 *
		 * @param stack The item stack representing the idle mounted weapon
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addIdleMountedPoolItem(ItemStack stack, int weight) {
			if(attackType.isMounted()) {
				equipmentPools.addIdleStack(setBaseItem(stack, "item.lotrextended.base_gear.weapon_idle_mounted"), weight);
			}
			return this;
		}
		/**
		 * Add shield item to the equipment pool with weight.
		 *
		 * @param stack The item stack representing the shield
		 * @param weight The weight for random selection
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder addShieldItem(ItemStack stack, int weight) {
			equipmentPools.addOffhandStack(setBaseItem(stack, "item.lotrextended.base_gear.shield"), weight);
			return this;
		}
		private static ItemStack setBaseItem(ItemStack stack, String name) {
			if(stack.isEmpty()) {
				return stack;
			}
			CompoundNBT compoundnbt = stack.getOrCreateTag();
			compoundnbt.putBoolean("IsBaseArmor", true);
			// Only save item localization title 
			compoundnbt.putString("langTitle", name);
			return stack;//.setHoverName(new TranslationTextComponent(name).withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
		}
		/**
		 * Sets the idle equipment pool to match the melee pool.
		 *
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder setIdlePoolFromMeleePool() {
			equipmentPools.setIdlePoolFromMeleePool();
			return this;
		}
		/**
		 * Sets the idle equipment pool to match the ranged pool.
		 *
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder setIdlePoolFromRangedPool() {
			equipmentPools.setIdlePoolFromRangedPool();
			return this;
		}
		/**
		 * Disables using other pools when selecting idle weapons.
		 *
		 * @return This builder instance for chaining
		 */
		public ExtendedServerHiredUnitProfile.Builder disableIdleWeaponUsingOtherPools() {
			equipmentPools.disableIdleWeaponUsingOtherPools();
			return this;
		}
		/**
		 * Generates an {@link ExtendedServerHiredUnitProfile} instance with the given ID.
		 *
		 * @param id The resource location identifier for the profile
		 * @return The built ExtendedServerHiredUnitProfile instance
		 */
		public ExtendedServerHiredUnitProfile build(ResourceLocation id) {
			ExtendedServerHiredUnitProfile spawner = new ExtendedServerHiredUnitProfile(id, translateStrings, entity_title, entity_shortname, entity_unit_type, equipmentPools, hiringCost, hiringRequiresPledge, hiringAlignmentRequired, attackType, unitEntityType);
			return spawner;
		}

		/**
		 * Generates and saves an {@link ExtendedServerHiredUnitProfile} instance using the given consumer and string ID.
		 *
		 * @param pConsumer The consumer to accept the built profile
		 * @param pId The string ID of the profile
		 * @return The built and consumed ExtendedServerHiredUnitProfile instance
		 */
		public ExtendedServerHiredUnitProfile save(Consumer<ExtendedServerHiredUnitProfile> pConsumer, String pId) {
			return save(pConsumer, new ResourceLocation(pId));
		}

		/**
		 * Generates and saves an {@link ExtendedServerHiredUnitProfile} instance using the given consumer and resource location ID.
		 *
		 * @param pConsumer The consumer to accept the built profile
		 * @param pId The resource location ID of the profile
		 * @return The built and consumed ExtendedServerHiredUnitProfile instance
		 */
		public ExtendedServerHiredUnitProfile save(Consumer<ExtendedServerHiredUnitProfile> pConsumer, ResourceLocation pId) {
			ExtendedServerHiredUnitProfile spawner = this.build(pId);
			pConsumer.accept(spawner);
			return spawner;
		}
	}

	@Nullable
	public static EntityType<?> getEntityTypeFromString(String str) {
		ResourceLocation entityReasourceName = new ResourceLocation(str);
		return ForgeRegistries.ENTITIES.getValue(entityReasourceName);
	}
}