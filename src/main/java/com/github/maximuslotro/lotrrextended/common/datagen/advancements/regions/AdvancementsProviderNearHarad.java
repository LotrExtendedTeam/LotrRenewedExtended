package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;

public class AdvancementsProviderNearHarad extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement southward_bound = build(true, dataMain(LOTRItems.HARAD_BRICK.get(), "southward_bound", FrameType.TASK)
				.setTitle("Southward Bound").setDescription("Enter the region of Harondor."),
				addBiomeTriggers(make(), LOTRBiomes.HARONDOR));
		Advancement southron_slayer = build(true, data(Items.BONE, "southron_slayer", FrameType.TASK)
			.setTitle("Southron Slayer").setDescription("Kill a Southron of Near Harad."),
			addKillEntityTriggers(make(), LOTREntities.COAST_SOUTHRON.get(), LOTREntities.COAST_SOUTHRON_ARCHER.get(), 
				LOTREntities.COAST_SOUTHRON_WARRIOR.get(), ExtendedEntities.COAST_SOUTHRON_WARLORD.get(),
				LOTREntities.HARNEDHRIM.get(), LOTREntities.HARNENNOR_ARCHER.get(), LOTREntities.HARNENNOR_WARRIOR.get(), ExtendedEntities.HARNENNOR_WARLORD.get(),
				LOTREntities.UMBAR_ARCHER.get(), LOTREntities.UMBAR_MAN.get(), LOTREntities.UMBAR_SOLDIER.get(), ExtendedEntities.UMBAR_CAPTAIN.get()).parent(southward_bound));
		
		Advancement beyond_harnen = build(true, data(Items.COARSE_DIRT, "beyond_harnen", FrameType.TASK)
			.setTitle("Beyond the Harnen").setDescription("Enter the region of Harnennor."),
			addBiomeTriggers(make(), LOTRBiomes.HARNENNOR).parent(southward_bound));
		Advancement harnennor_armor = build(true, data(LOTRItems.HARNENNOR_CHESTPLATE.get(), "harnennor_armor", FrameType.TASK)
			.setTitle("Cruel Bronze Spikes").setDescription("Equip a full suit of Harnennor armor."),
			addArmorTrigger(make(), "harnennor", LOTRItems.HARNENNOR_HELMET.get(), LOTRItems.HARNENNOR_CHESTPLATE.get(),
					LOTRItems.HARNENNOR_LEGGINGS.get(), LOTRItems.HARNENNOR_BOOTS.get()).parent(beyond_harnen));
		Advancement hire_harennor = build(true, data(LOTRItems.HARAD_SWORD.get(), "sons_of_the_harnen", FrameType.TASK)
			.setTitle("Sons of the Harnen").setDescription("Hire a unit from a Harnennor Warlord."),
			addUnitHireTrigger(make(), ExtendedEntities.HARNENNOR_WARLORD.get()).parent(beyond_harnen));

		Advancement city_of_corsairs = build(true, data(LOTRItems.UMBAR_SCIMITAR.get(), "city_of_corsairs", FrameType.TASK)
			.setTitle("City of Corsairs").setDescription("Enter the land of Umbar."),
			addBiomeTriggers(make(), LOTRBiomes.UMBAR, LOTRBiomes.UMBAR_FOREST, LOTRBiomes.UMBAR_HILLS).parent(southward_bound));
		Advancement hire_umbar = build(true, data(LOTRItems.UMBAR_SCIMITAR.get(), "raiding_party", FrameType.TASK)
			.setTitle("Raiding Party").setDescription("Hire a unit from a Corsair Captain."),
			addUnitHireTrigger(make(), ExtendedEntities.UMBAR_CAPTAIN.get()).parent(city_of_corsairs));
		Advancement umbar_armor = build(true, data(LOTRItems.UMBAR_CHESTPLATE.get(), "umbar_armor", FrameType.TASK)
			.setTitle("Soldier of Umbar").setDescription("Equip a full suit of Umbaric armor."),
			addArmorTrigger(make(), "umbar", LOTRItems.UMBAR_HELMET.get(), LOTRItems.UMBAR_CHESTPLATE.get(),
					LOTRItems.UMBAR_LEGGINGS.get(), LOTRItems.UMBAR_BOOTS.get()).parent(city_of_corsairs));
		Advancement umbaric_crafter = build(true, data(LOTRItems.UMBAR_CRAFTING_TABLE.get(), "umbaric_crafter", FrameType.TASK)
			.setTitle("Umbaric Crafter").setDescription("Craft an item on an Umbaric crafting table."),
			addCraftingTrigger(make(), LOTRBlocks.UMBAR_CRAFTING_TABLE.get()).parent(city_of_corsairs));

		Advancement azra_zain = build(true, data(Items.SAND, "azra_zain", FrameType.TASK)
			.setTitle("In Azra-zâin").setDescription("Enter the coastland regions of Near Harad."),
			addBiomeTriggers(make(), LOTRBiomes.SOUTHRON_COASTS, LOTRBiomes.SOUTHRON_COASTS_FOREST).parent(southward_bound));
		Advancement southron_crafter = build(true, data(LOTRItems.HARAD_CRAFTING_TABLE.get(), "southron_crafter", FrameType.TASK)
			.setTitle("Southron Crafter").setDescription("Craft an item on the Haradric crafting table."),
			addCraftingTrigger(make(), LOTRBlocks.HARAD_CRAFTING_TABLE.get()).parent(southward_bound));
		Advancement hire_southron = build(true, data(LOTRItems.HARAD_SPEAR.get(), "league_of_southrons", FrameType.TASK)
			.setTitle("League of Southrons").setDescription("Hire a unit from a Coast Southron Warlord."),
			addUnitHireTrigger(make(), ExtendedEntities.COAST_SOUTHRON_WARLORD.get()).parent(azra_zain));
	
		Advancement the_great_desert = build(true, data(Items.SAND, "the_great_desert", FrameType.TASK)
			.setTitle("The Great Desert").setDescription("Enter the burning sands of Near Harad."),
			addBiomeTriggers(make(), LOTRBiomes.HARAD_DESERT, LOTRBiomes.HARAD_DESERT_HILLS, LOTRBiomes.HARAD_HALF_DESERT).parent(southward_bound));

		Advancement wide_emptiness = build(true, data(Items.STONE, "wide_emptiness", FrameType.TASK)
			.setTitle("Wide Emptiness").setDescription("Enter the desert of Lostladen."),
			addBiomeTriggers(make(), LOTRBiomes.HARNENNOR).parent(southward_bound));

		Advancement harad_armor = build(true, data(LOTRItems.HARAD_CHESTPLATE.get(), "harad_armor", FrameType.TASK)
			.setTitle("Coastland Soldier").setDescription("Equip a full suit of Near Harad armor."),
			addArmorTrigger(make(), "harad", LOTRItems.HARAD_HELMET.get(), LOTRItems.HARAD_CHESTPLATE.get(),
					LOTRItems.HARAD_LEGGINGS.get(), LOTRItems.HARAD_BOOTS.get()).parent(southward_bound));

		Advancement near_harad_10 = build(true, data(LOTRItems.GOLD_RING.get(), "near_harad_10", FrameType.TASK)
				.setTitle("Southron Guest").setDescription("Reach +10.0 alignment with the Near Haradrim."),
				addAlignmentTrigger(make(), FactionPointers.NEAR_HARAD, 10).parent(southward_bound));
		Advancement near_harad_50 = build(true, data(LOTRItems.GOLD_RING.get(), "near_harad_50", FrameType.TASK)
				.setTitle("Southron Friend").setDescription("Reach +50.0 alignment with the Near Haradrim."),
				addAlignmentTrigger(make(), FactionPointers.NEAR_HARAD, 50).parent(near_harad_10));
		Advancement near_harad_100 = build(true, data(LOTRItems.GOLD_RING.get(), "near_harad_100", FrameType.TASK)
				.setTitle("Southron Kinsman").setDescription("Reach +100.0 alignment with the Near Haradrim."),
				addAlignmentTrigger(make(), FactionPointers.NEAR_HARAD, 100).parent(near_harad_50));
		Advancement near_harad_200 = build(true, data(LOTRItems.GOLD_RING.get(), "near_harad_200", FrameType.TASK)
				.setTitle("Southron Warrior").setDescription("Reach +200.0 alignment with the Near Haradrim."),
				addAlignmentTrigger(make(), FactionPointers.NEAR_HARAD, 200).parent(near_harad_100));
		Advancement near_harad_500 = build(true, data(LOTRItems.GOLD_RING.get(), "near_harad_500", FrameType.TASK)
				.setTitle("Southron Champion").setDescription("Reach +500.0 alignment with the Near Haradrim."),
				addAlignmentTrigger(make(), FactionPointers.NEAR_HARAD, 500).parent(near_harad_200));
		Advancement near_harad_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "near_harad_1000", FrameType.TASK)
				.setTitle("Serpent Guard").setDescription("Reach +1000.0 alignment with the Near Haradrim."),
				addAlignmentTrigger(make(), FactionPointers.NEAR_HARAD, 1000).parent(near_harad_500));
		Advancement near_harad_1500 = build(true, data(LOTRItems.GOLD_RING.get(), "near_harad_1500", FrameType.GOAL)
				.setTitle("Southron Warlord").setDescription("Reach +1500.0 alignment with the Near Haradrim."),
				addAlignmentTrigger(make(), FactionPointers.NEAR_HARAD, 1500).parent(near_harad_1000));
		Advancement near_harad_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "near_harad_3000", FrameType.CHALLENGE)
				.setTitle("Prince/Princess of Harad").setDescription("Reach +3000.0 alignment with the Near Haradrim."),
				addAlignmentTrigger(make(), FactionPointers.NEAR_HARAD, 3000).parent(near_harad_1500));
	}

	@Override
	public String getFolder() {
		return "near_harad";
	}

	@Override
	public int getLegacyCount() {
		return 50;
	}

}
