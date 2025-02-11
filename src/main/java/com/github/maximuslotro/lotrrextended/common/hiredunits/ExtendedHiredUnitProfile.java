package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.lang.reflect.Type;
import java.util.function.Consumer;

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

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHiredUnitProfile {
	public static final ExtendedHiredUnitProfile EMPTY = new ExtendedHiredUnitProfile(LotrExtendedMod.EMPTY, false, "EMPTY", "EMPTY", "EMPTY", ExtendedHiredUnitEquipmentInstance.EMPTY, 100000, false, 1000);
	private final ResourceLocation id;
	private final boolean translateStrings;
	private final String entity_title;
	private final String entity_shortname;
	private final String entity_unit_type;
	private final ExtendedHiredUnitEquipmentInstance equipmentPools;
	private final int hiringCost;
	private final boolean hiringRequiresPledge;
	private final int hiringAlignmentRequired;

	public ExtendedHiredUnitProfile(ResourceLocation id, boolean translateStrings, String entity_title, String entity_shortname, String entity_unit_type, ExtendedHiredUnitEquipmentInstance equipmentPools, int hiringCost, boolean hiringRequiresPledge, int hiringAlignmentRequired) {
		this.id = id;
		this.translateStrings = translateStrings;
		this.entity_title = entity_title;
		this.entity_shortname = entity_shortname;
		this.entity_unit_type = entity_unit_type;
		this.equipmentPools = equipmentPools;
		this.hiringCost = hiringCost;
		this.hiringRequiresPledge = hiringRequiresPledge;
		this.hiringAlignmentRequired = hiringAlignmentRequired;
	}

	/*
	 * Get the id of this ExtendedHiredUnitProfile
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
	public String getUnitTitle() {
		return entity_title;
	}

	/*
	 * Gets the string or key for the unit's shorthand name
	 * */
	public String getUnitShorthandName() {
		return entity_shortname;
	}

	/*
	 * Gets the string or key for the unit's type name
	 * */
	public String getUnitTypeName() {
		return entity_unit_type;
	}

	/**
	 * Check if unitType is of EMPTY type
	 * Could mean some data corruption happened
	 * */
	public boolean isEmptyUnitType() {
		return this.equals(ExtendedHiredUnitProfile.EMPTY) || id.equals(LotrExtendedMod.EMPTY);
	}

	/**
	 * Gets the eqquipment item pools for the unit profile
	 * @return the pool
	 * @see {@link ExtendedHiredUnitEquipmentInstance}
	 * */
	public ExtendedHiredUnitEquipmentInstance getEquipmentPools() {
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

	/*
	 * Serializer for ExtendedHiredUnitProfile
	 * */
	public static class Serializer implements JsonDeserializer<ExtendedHiredUnitProfile>, JsonSerializer<ExtendedHiredUnitProfile> {
		@Override
		public ExtendedHiredUnitProfile deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedHiredUnitProfile");
			String id = jsonobject.get("id").getAsString();
			if(id==null||!id.contains(":")) {
				ExtendedLog.warn("Failed to load hired unit profile due to invalid id: %s!", id);
				return null;
			}
			JsonObject languageObject = jsonobject.get("language").getAsJsonObject();
			boolean translateStrings = languageObject.get("translateStrings").getAsBoolean();
			String entity_title = languageObject.get("entity_title").getAsString();
			String entity_shortname = languageObject.get("entity_shortname").getAsString();
			String entity_unit_type = languageObject.get("entity_unit_type").getAsString();
			int hiringCost = languageObject.get("hiringCost").getAsInt();
			boolean hiringRequiresPledge = languageObject.get("hiringRequiresPledge").getAsBoolean();
			// TODO make required in the future
			int hiringAlignmentRequired = 100;
			if(languageObject.has("hiringAlignmentRequired")) {
				hiringAlignmentRequired = languageObject.get("hiringAlignmentRequired").getAsInt();
			}
			ExtendedHiredUnitEquipmentInstance equipmentPools = JSONUtils.getAsObject(jsonobject, "equipmentPools", ExtendedHiredUnitEquipmentInstance.EMPTY, context, ExtendedHiredUnitEquipmentInstance.class);
			return new ExtendedHiredUnitProfile(new ResourceLocation(id), translateStrings, entity_title, entity_shortname, entity_unit_type, equipmentPools, hiringCost, hiringRequiresPledge, hiringAlignmentRequired);
		}

		@Override
		public JsonElement serialize(ExtendedHiredUnitProfile unitProfile, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", unitProfile.getId().toString());
			JsonObject languageObject = new JsonObject();
			languageObject.addProperty("translateStrings", unitProfile.stringsAreTranslated());
			languageObject.addProperty("entity_title", unitProfile.getUnitTitle());
			languageObject.addProperty("entity_shortname", unitProfile.getUnitShorthandName());
			languageObject.addProperty("entity_unit_type", unitProfile.getUnitTypeName());
			jsonobject.add("language", languageObject);
			languageObject.addProperty("hiringCost", unitProfile.getHiringCost());
			languageObject.addProperty("hiringRequiresPledge", unitProfile.isHiringRequiresPledge());
			languageObject.addProperty("hiringAlignmentRequired", unitProfile.getHiringAlignmentRequired());
			jsonobject.add("equipmentPools", context.serialize(unitProfile.equipmentPools));
			return jsonobject;
		}
	}

	public static class Builder {
		private ResourceLocation id;
		private boolean translateStrings;
		private String entity_title;
		private String entity_shortname;
		private String entity_unit_type;
		private ExtendedHiredUnitEquipmentInstance equipmentPools = new ExtendedHiredUnitEquipmentInstance();
		private int hiringCost;
		private boolean hiringRequiresPledge;
		private int hiringAlignmentRequired;

		public Builder() {
		}

		/**
		 * Set Hired Unit string localization
		 */
		public ExtendedHiredUnitProfile.Builder setLocal(boolean translateStrings, String nameKey, String name) {
			this.translateStrings = translateStrings;
			if(translateStrings) {
				this.entity_title = "entity_title.lotrextended.hired_"+nameKey;
				DataGenValueHolder.hiredUnitStrings.put(entity_title, "%1$s the "+name);
				this.entity_shortname = "entity_shortname.lotrextended.hired_"+nameKey;
				DataGenValueHolder.hiredUnitStrings.put(entity_shortname, "%1$s");
				this.entity_unit_type = "entity_unit_type.lotrextended.hired_"+nameKey;
				DataGenValueHolder.hiredUnitStrings.put(entity_unit_type, name);
			}else {
				this.entity_title = name;
				this.entity_shortname = name;
				this.entity_unit_type = name;
			}
			return this;
		}

		/**
		 * Set Hired Unit purchase info
		 */
		public ExtendedHiredUnitProfile.Builder setPurchaseInfo(int cost, boolean pledgeRequired, int alignmnetRequired) {
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

		public ExtendedHiredUnitProfile.Builder addHelmetPoolItem(ItemStack stack, int weight) {
			equipmentPools.addHelmetStack(setBaseItem(stack, "item.lotrextended.base_gear.helmet"), weight);
			return this;
		}
		public ExtendedHiredUnitProfile.Builder addChestplatePoolItem(ItemStack stack, int weight) {
			equipmentPools.addChestplateStack(setBaseItem(stack, "item.lotrextended.base_gear.chestplate"), weight);
			return this;
		}
		public ExtendedHiredUnitProfile.Builder addLeggingPoolItem(ItemStack stack, int weight) {
			equipmentPools.addLeggingStack(setBaseItem(stack, "item.lotrextended.base_gear.leggings"), weight);
			return this;
		}
		public ExtendedHiredUnitProfile.Builder addBootsPoolItem(ItemStack stack, int weight) {
			equipmentPools.addBootsStack(setBaseItem(stack, "item.lotrextended.base_gear.boots"), weight);
			return this;
		}
		public ExtendedHiredUnitProfile.Builder addWeaponPoolItem(ItemStack stack, int weight) {
			equipmentPools.addWeaponStack(setBaseItem(stack, "item.lotrextended.base_gear.weapon"), weight);
			return this;
		}
		public ExtendedHiredUnitProfile.Builder addShieldItem(ItemStack stack, int weight) {
			equipmentPools.addShieldStack(setBaseItem(stack, "item.lotrextended.base_gear.shield"), weight);
			return this;
		}

		private static ItemStack setBaseItem(ItemStack stack, String name) {
			CompoundNBT compoundnbt = stack.getOrCreateTag();
			compoundnbt.putBoolean("IsBaseArmor", true);
			return stack.setHoverName(new TranslationTextComponent(name).withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
		}

		/**
		 * Generate a ExtendedStructureEntitySpawner
		 */
		public ExtendedHiredUnitProfile build(ResourceLocation id) {
			ExtendedHiredUnitProfile spawner = new ExtendedHiredUnitProfile(id, translateStrings, entity_title, entity_shortname, entity_unit_type, equipmentPools, hiringCost, hiringRequiresPledge, hiringAlignmentRequired);
			return spawner;
		}

		/**
		 * Generate a ExtendedStructureEntitySpawner and save with a Consumer
		 */
		public ExtendedHiredUnitProfile save(Consumer<ExtendedHiredUnitProfile> pConsumer, String pId) {
			return save(pConsumer, new ResourceLocation(pId));
		}

		/**
		 * Generate a ExtendedStructureEntitySpawner and save with a Consumer
		 */
		public ExtendedHiredUnitProfile save(Consumer<ExtendedHiredUnitProfile> pConsumer, ResourceLocation pId) {
			ExtendedHiredUnitProfile spawner = this.build(pId);
			pConsumer.accept(spawner);
			return spawner;
		}
	}
}