package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;


public class GeneralAdvancementsProvider extends BaseAdvancementType{
	private static final List<ResourceLocation> BIOMES;

	static {
		BIOMES = new ArrayList<>();
		Collection<RegistryObject<Biome>> eeMES = LOTRBiomes.BIOMES.getEntries();
		for(RegistryObject<Biome> b : eeMES) {
			BIOMES.add(b.getId());
		}
	}

	@Override
	public void generateAdvancements() {
		Advancement base = build(true, data(LOTRItems.RED_BOOK.get(), "first_steps", true, FrameType.TASK, true, true, false)
				.setTitle("First Steps").setDescription("Enter Middle-Earth.").setTitleStype(TextFormatting.GREEN), 
				make().addCriterion("entered_middle_earth", ChangeDimensionTrigger.Instance.changedDimensionTo(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)));

		Advancement orc_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "orc_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Orc Slayer").setDescription("Kill an Orc.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(addKillEntityTrigger(addKillEntityTrigger(addKillEntityTrigger(addKillEntityTrigger(
						make(), LOTREntities.GUNDABAD_ORC.get()), LOTREntities.GUNDABAD_ORC_ARCHER.get()), 
						LOTREntities.MORDOR_ORC.get()), LOTREntities.MORDOR_ORC_ARCHER.get()), 
						LOTREntities.ISENGARD_SNAGA.get()), LOTREntities.ISENGARD_SNAGA_ARCHER.get())
				.requirements(IRequirementsStrategy.OR).parent(base));

		Advancement athelas = build(true, data(LOTRItems.ATHELAS.get(), "kingsfoil", false, FrameType.TASK, true, true, false)
				.setTitle("Kingsfoil").setDescription("Find the Athelas herb.").setTitleStype(TextFormatting.GREEN), 
				addItemTrigger(make(), LOTRItems.ATHELAS.get()).parent(base));

		Advancement trash = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "one_mans_trash", false, FrameType.TASK, true, true, false)
				.setTitle("One Man's Trash").setDescription("Trade with a travelling Oddment Collector.").setTitleStype(TextFormatting.GREEN), 
				addTradeEntityTrigger(make(), ExtendedEntities.ODDMENT_COLLECTOR.get()).parent(base));

		Advancement pouches = build(true, data(LOTRItems.SMALL_POUCH.get(), "bigger_on_the_inside", false, FrameType.TASK, true, true, false)
				.setTitle("Bigger on the Inside").setDescription("Acquire a pouch.").setTitleStype(TextFormatting.GREEN), 
				addItemTrigger(addItemTrigger(addItemTrigger(make(), LOTRItems.SMALL_POUCH.get()), LOTRItems.MEDIUM_POUCH.get()), 
						LOTRItems.LARGE_POUCH.get()).requirements(IRequirementsStrategy.OR).parent(base));
		
		Advancement true_silver = build(true, data(LOTRItems.MITHRIL_ORE.get(), "true_silver", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("True Silver").setDescription("Obtain Mithril Ore.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addItemTrigger(make(), LOTRItems.MITHRIL_ORE.get()).parent(base));
		Advancement very_serious_dedication = build(true, data(LOTRItems.MITHRIL_HOE.get(), "very_serious_dedication", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Very Serious Dedication").setDescription("Acquire a Mithril Hoe.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addItemTrigger(make(), LOTRItems.MITHRIL_HOE.get()).parent(true_silver));
		Advancement mithril_armor = build(true, data(LOTRItems.MITHRIL_CHESTPLATE.get(), "treasures_of_old", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Treasures of Old").setDescription("Acquire a full set of Mithril armor.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addArmorTrigger(make(), "mithril", LOTRItems.MITHRIL_HELMET.get(), LOTRItems.MITHRIL_CHESTPLATE.get(), 
						LOTRItems.MITHRIL_LEGGINGS.get(), LOTRItems.MITHRIL_BOOTS.get()).parent(true_silver));

		Advancement warg_slayer = build(true, data(LOTRItems.WARG_BONE.get(), "warg_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Warg Slayer").setDescription("Kill a Warg.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.GUNDABAD_WARG.get()), 
						LOTREntities.ISENGARD_WARG.get()), LOTREntities.MORDOR_WARG.get())
				.requirements(IRequirementsStrategy.OR).parent(base));

		Advancement warg_armor = build(true, data(LOTRItems.FUR_CHESTPLATE.get(), "master_hunter", false, FrameType.TASK, true, true, false)
				.setTitle("Master Hunter").setDescription("Acquire a full set of Warg Fur armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "warg", LOTRItems.FUR_HELMET.get(), LOTRItems.FUR_CHESTPLATE.get(), 
						LOTRItems.FUR_LEGGINGS.get(), LOTRItems.FUR_BOOTS.get()).parent(base));

		Advancement copper_award = build(false, data(LOTRItems.COPPER_INGOT.get(), "copper_award", false, FrameType.TASK, true, true, false)
				.setTitle("Copper Award").setDescription("Obtain a copper ingot by smelting copper ore.").setTitleStype(TextFormatting.GREEN), 
				addItemTrigger(make(), LOTRItems.COPPER_INGOT.get()).parent(base));
		
		Advancement tin_award = build(false, data(LOTRItems.TIN_INGOT.get(), "tin_award", false, FrameType.TASK, true, true, false)
				.setTitle("Tin Award").setDescription("Obtain a tin ingot by smelting tin ore.").setTitleStype(TextFormatting.GREEN), 
				addItemTrigger(make(), LOTRItems.TIN_INGOT.get()).parent(copper_award));
		
		Advancement bronze_award = build(true, data(LOTRItems.BRONZE_INGOT.get(), "bronze_award", false, FrameType.TASK, true, true, false)
				.setTitle("Bronze Award").setDescription("Craft a bronze ingot by combining tin and copper.").setTitleStype(TextFormatting.GREEN), 
				addItemTrigger(make(), LOTRItems.BRONZE_INGOT.get()).parent(tin_award));

		Advancement get_moldy_bread = build(true, data(LOTRItems.MAGGOTY_BREAD.get(), "three_stinking_days", false, FrameType.TASK, true, true, false)
				.setTitle("Three Stinking Days!").setDescription("Eat some Maggoty Bread.").setTitleStype(TextFormatting.GREEN), 
				addUseTrigger(make(), LOTRItems.MAGGOTY_BREAD.get()).parent(base));

		Advancement get_bad_meat = build(true, data(LOTRItems.SUSPICIOUS_MEAT.get(), "suspicious", false, FrameType.TASK, true, true, false)
				.setTitle("Suspicious...").setDescription("Produce some suspicious meat from questionable ingredients.").setTitleStype(TextFormatting.GREEN), 
				addItemTrigger(make(), LOTRItems.SUSPICIOUS_MEAT.get()).parent(get_moldy_bread));

		Advancement eat_bad_meat = build(false, data(LOTRItems.SUSPICIOUS_MEAT.get(), "very_suspicious", false, FrameType.GOAL, true, true, false)
				.setTitle("Yum...").setDescription("Eat some suspicious meat...").setTitleStype(TextFormatting.GOLD), 
				addUseTrigger(make(), LOTRItems.SUSPICIOUS_MEAT.get()).parent(get_bad_meat));

		Advancement get_man_meat = build(true, data(LOTRItems.MAN_FLESH.get(), "i_smell_man_flesh", false, FrameType.TASK, true, true, false)
				.setTitle("I Smell Man-Flesh!").setDescription("Eat a hunk of Man-Flesh.").setTitleStype(TextFormatting.GREEN), 
				addUseTrigger(make(), LOTRItems.MAN_FLESH.get()).parent(get_moldy_bread));

		Advancement saddle = build(true, data(Items.SADDLE, "time_to_ride", false, FrameType.TASK, true, true, false)
				.setTitle("Time to Ride!").setDescription("Acquire a Saddle.").setTitleStype(TextFormatting.GREEN), 
				addUseTrigger(make(), Items.SADDLE).parent(base));

		Advancement apple_crumble = build(true, data(LOTRItems.APPLE_CRUMBLE.get(), "traditional_dish", false, FrameType.TASK, true, true, false)
				.setTitle("Traditional Dish").setDescription("Acquire an Apple Crumble.").setTitleStype(TextFormatting.GREEN), 
				addUseTrigger(make(), LOTRItems.APPLE_CRUMBLE.get()).parent(base));

		Advancement fish_ring = build(true, data(Items.FISHING_ROD, "its_our_birthday", false, FrameType.TASK, true, true, false)
				.setTitle("It's Our Birthday").setDescription("Catch a ring with a fishing rod.").setTitleStype(TextFormatting.GREEN), 
				addFishTrigger(addFishTrigger(addFishTrigger(make(), LOTRItems.SILVER_RING.get()), LOTRItems.GOLD_RING.get()), 
						LOTRItems.MITHRIL_RING.get()).requirements(IRequirementsStrategy.OR).parent(base));

		Advancement exp_fish_ring = build(false, data(Items.FISHING_ROD, "swag_catch", false, FrameType.GOAL, true, true, false)
				.setTitle("Swaggest Catch Ever").setDescription("Catch a mithril ring with a fishing rod.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addFishTrigger(make(), LOTRItems.MITHRIL_RING.get()).requirements(IRequirementsStrategy.OR).parent(fish_ring));

	}

	@Override
	public String getFolder() {
		return "general";
	}

}
