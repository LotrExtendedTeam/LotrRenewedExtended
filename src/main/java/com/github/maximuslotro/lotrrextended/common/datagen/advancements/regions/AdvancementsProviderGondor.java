package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;

public class AdvancementsProviderGondor extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement kingdom_of_men = build(true, dataMain(LOTRItems.GONDOR_SPEAR.get(), "kingdom_of_men", FrameType.TASK)
				.setTitle("The Kingdom of Men").setDescription("Enter the land of Gondor."),
				addBiomeTriggers(make(), LOTRBiomes.FURTHER_GONDOR, LOTRBiomes.ANFALAS, LOTRBiomes.ANORIEN, LOTRBiomes.ANDRAST, LOTRBiomes.BLACKROOT_VALE, 
					LOTRBiomes.WHITE_MOUNTAINS, LOTRBiomes.WHITE_MOUNTAINS_FOOTHILLS, LOTRBiomes.ETHIR_ANDUIN, LOTRBiomes.LEBENNIN, LOTRBiomes.ITHILIEN, 
					LOTRBiomes.DOR_EN_ERNIL, LOTRBiomes.PINNATH_GELIN, LOTRBiomes.TOLFALAS, LOTRBiomes.PELARGIR, 
					LOTRBiomes.LOSSARNACH, LOTRBiomes.LAMEDON, LOTRBiomes.LAMEDON_HILLS));

		Advancement bows_and_blackroots = build(true, data(LOTRItems.BLACKROOT.get(), "bows_and_blackroots", FrameType.TASK)
				.setTitle("Bows and Blackroots").setDescription("Enter the fiefdom of Blackroot Vale."),
				addBiomeTrigger(make(), LOTRBiomes.BLACKROOT_VALE).parent(kingdom_of_men));

		Advancement white_mountains = build(true, data(LOTRItems.GONDOR_ROCK.get(), "white_mountains", FrameType.TASK)
			.setTitle("Ered Nimrais").setDescription("Enter the White Mountains."),
			addBiomeTriggers(make(), LOTRBiomes.WHITE_MOUNTAINS, LOTRBiomes.WHITE_MOUNTAINS_FOOTHILLS).parent(kingdom_of_men));

		Advancement ethir_anduin = build(true, data(Items.LILY_PAD, "ethir_anduin", FrameType.TASK)
			.setTitle("Ered Nimrais").setDescription("Enter the mouths of the Anduin."),
			addBiomeTrigger(make(), LOTRBiomes.ETHIR_ANDUIN).parent(kingdom_of_men));

		Advancement five_fair_steams = build(true, data(Items.BIRCH_SAPLING, "five_fair_steams", FrameType.TASK)
			.setTitle("Five Fair Streams").setDescription("Enter the fiefdom of Lebennin."),
			addBiomeTrigger(make(), LOTRBiomes.LEBENNIN).parent(kingdom_of_men));

		Advancement hills_and_vales = build(true, data(LOTRItems.BRONZE_AXE.get(), "hills_and_vales", FrameType.TASK)
			.setTitle("Hills and Vales").setDescription("Enter the fiefdom of Lamedon."),
			addBiomeTriggers(make(), LOTRBiomes.LAMEDON, LOTRBiomes.LAMEDON_HILLS).parent(kingdom_of_men));

		Advancement land_of_the_moon = build(true, data(LOTRItems.CRACKED_NUMENOREAN_BRICK.get(), "land_of_the_moon", FrameType.TASK)
			.setTitle("Land of the Moon").setDescription("Enter the fiefdom of Ithilien."),
			addBiomeTrigger(make(), LOTRBiomes.ITHILIEN).parent(kingdom_of_men));

		Advancement land_of_the_prince = build(true, data(Items.FEATHER, "land_of_the_prince", FrameType.TASK)
			.setTitle("Land of the Prince").setDescription("Enter the fiefdom of Dor-en-Ernil."),
			addBiomeTrigger(make(), LOTRBiomes.DOR_EN_ERNIL).parent(kingdom_of_men));

		Advancement the_green_hills = build(true, data(Items.GRASS_BLOCK, "the_green_hills", FrameType.TASK)
			.setTitle("The Green Hills").setDescription("Enter the fiefdom of Pinnath Gelin."),
			addBiomeTrigger(make(), LOTRBiomes.PINNATH_GELIN).parent(kingdom_of_men));

		Advancement isle_in_the_bay = build(true, data(Items.STONE, "isle_in_the_bay", FrameType.TASK)
			.setTitle("The Isle in the Bay").setDescription("Enter the island of Tolfalas."),
			addBiomeTrigger(make(), LOTRBiomes.TOLFALAS).parent(kingdom_of_men));

		Advancement royal_havens = build(true, data(LOTRItems.GONDOR_BRICK.get(), "royal_havens", FrameType.TASK)
			.setTitle("The Royal Havens").setDescription("Enter the fiefdom of Pelargir."),
			addBiomeTrigger(make(), LOTRBiomes.PELARGIR).parent(kingdom_of_men));

//		Advancement the_townlands = build(true, data(LOTRItems.GONDOR_BRICK.get(), "the_townlands", FrameType.TASK)
//			.setTitle("The Townlands").setDescription("Enter the Pelennor Fields."),
//			addBiomeTrigger(make(), LOTRBiomes.PELENNOR_FIELDS).parent(kingdom_of_men));

		Advancement vale_of_flowers = build(true, data(LOTRItems.APPLE_SAPLING.get(), "vale_of_flowers", FrameType.TASK)
			.setTitle("Vale of Flowers").setDescription("Enter the fiefdom of Lossarnach."),
			addBiomeTrigger(make(), LOTRBiomes.LOSSARNACH).parent(kingdom_of_men));
		
//		Advancement vale_of_roses = build(true, data(Items.ROSE_BUSH, "vale_of_roses", FrameType.TASK)
//			.setTitle("Valley of Roses").setDescription("Enter the valley of Imloth Melui."),
//			addBiomeTrigger(make(), LOTRBiomes.IMLOTH_MELUI).parent(kingdom_of_men));

		Advancement wild_shores = build(true, data(Items.SAND, "wild_shores", FrameType.TASK)
			.setTitle("Wild Shores").setDescription("Enter the cape of Andrast."),
			addBiomeTrigger(make(), LOTRBiomes.ANDRAST).parent(kingdom_of_men));

		Advancement gondor_10 = build(true, data(LOTRItems.GOLD_RING.get(), "gondor_10", FrameType.TASK)
				.setTitle("Gondor Guest").setDescription("Reach +10.0 alignment with Gondor."),
				addAlignmentTrigger(make(), FactionPointers.GONDOR, 10).parent(kingdom_of_men));
		Advancement gondor_50 = build(true, data(LOTRItems.GOLD_RING.get(), "gondor_50", FrameType.TASK)
				.setTitle("Gondor Friend").setDescription("Reach +50.0 alignment with Gondor."),
				addAlignmentTrigger(make(), FactionPointers.GONDOR, 50).parent(gondor_10));
		Advancement gondor_100 = build(true, data(LOTRItems.GOLD_RING.get(), "gondor_100", FrameType.TASK)
				.setTitle("Gondorian-at-Arms").setDescription("Reach +100.0 alignment with Gondor."),
				addAlignmentTrigger(make(), FactionPointers.GONDOR, 100).parent(gondor_50));
		Advancement gondor_200 = build(true, data(LOTRItems.GOLD_RING.get(), "gondor_200", FrameType.TASK)
				.setTitle("Gondor Soldier").setDescription("Reach +200.0 alignment with Gondor."),
				addAlignmentTrigger(make(), FactionPointers.GONDOR, 200).parent(gondor_100));
		Advancement gondor_500 = build(true, data(LOTRItems.GOLD_RING.get(), "gondor_500", FrameType.TASK)
				.setTitle("Gondor Knight").setDescription("Reach +500.0 alignment with Gondor."),
				addAlignmentTrigger(make(), FactionPointers.GONDOR, 500).parent(gondor_200));
		Advancement gondor_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "gondor_1000", FrameType.TASK)
				.setTitle("Gondor Champion").setDescription("Reach +1000.0 alignment with Gondor."),
				addAlignmentTrigger(make(), FactionPointers.GONDOR, 1000).parent(gondor_500));
		Advancement gondor_1500 = build(true, data(LOTRItems.GOLD_RING.get(), "gondor_2000", FrameType.GOAL)
				.setTitle("Gondor Captain").setDescription("Reach +2000.0 alignment with Gondor."),
				addAlignmentTrigger(make(), FactionPointers.GONDOR, 1500).parent(gondor_1000));
		Advancement gondor_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "gondor_3000", FrameType.CHALLENGE)
				.setTitle("Gondorian Lord/Lady").setDescription("Reach +3000.0 alignment with Gondor."),
				addAlignmentTrigger(make(), FactionPointers.GONDOR, 3000).parent(gondor_1500));

		Advancement gondor_crafter = build(true, data(LOTRItems.GONDOR_CRAFTING_TABLE.get(), "gondor_crafter", FrameType.TASK)
			.setTitle("Gondorian Crafter").setDescription("Craft an item on the Gondorian Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.GONDOR_CRAFTING_TABLE.get()).parent(kingdom_of_men));

		Advancement dol_amroth_crafter = build(true, data(LOTRItems.DOL_AMROTH_CRAFTING_TABLE.get(), "dol_amroth_crafter", FrameType.TASK)
			.setTitle("Dol Amroth Crafter").setDescription("Craft an item on the Dol Amroth Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.DOL_AMROTH_CRAFTING_TABLE.get()).parent(land_of_the_prince));

		Advancement dol_amroth_armor = build(true, data(LOTRItems.DOL_AMROTH_CHESTPLATE.get(), "dol_amroth_armor", FrameType.TASK)
			.setTitle("Swan Knight").setDescription("Acquire a full set of Dol Amroth armor."),
			addArmorTrigger(make(), "dol_amroth", LOTRItems.DOL_AMROTH_HELMET.get(), LOTRItems.DOL_AMROTH_CHESTPLATE.get(),
					LOTRItems.DOL_AMROTH_LEGGINGS.get(), LOTRItems.DOL_AMROTH_BOOTS.get()).parent(land_of_the_prince));

		Advancement gondor_armor = build(true, data(LOTRItems.GONDOR_CHESTPLATE.get(), "gondor_armor", FrameType.TASK)
			.setTitle("For Gondor!").setDescription("Acquire a full set of Dol Amroth armor."),
			addArmorTrigger(make(), "gondow", LOTRItems.GONDOR_HELMET.get(), LOTRItems.GONDOR_CHESTPLATE.get(),
					LOTRItems.GONDOR_LEGGINGS.get(), LOTRItems.GONDOR_BOOTS.get()).parent(kingdom_of_men));

		Advancement ithilien_ranger_armor = build(true, data(LOTRItems.RANGER_ITHILIEN_CHESTPLATE.get(), "ithilien_ranger_armor", FrameType.TASK)
			.setTitle("Beneath the Moon").setDescription("Equip a full suit of Ithilien Ranger armor."),
			addArmorTrigger(make(), "ithilien_ranger", LOTRItems.RANGER_ITHILIEN_HELMET.get(), LOTRItems.RANGER_ITHILIEN_CHESTPLATE.get(),
					LOTRItems.RANGER_ITHILIEN_LEGGINGS.get(), LOTRItems.RANGER_ITHILIEN_BOOTS.get()).parent(land_of_the_moon));

		Advancement gondorian_slayer = build(true, data(LOTRItems.GONDOR_SWORD.get(), "gondorian_slayer", FrameType.TASK)
			.setTitle("Gondorian Slayer").setDescription("Kill a Man of Gondor."),
			addKillEntityTriggers(make(), LOTREntities.GONDOR_MAN.get(), LOTREntities.GONDOR_ARCHER.get(), LOTREntities.GONDOR_SOLDIER.get()).parent(kingdom_of_men));

	}

	@Override
	public String getFolder() {
		return "gondor";
	}

	@Override
	public int getLegacyCount() {
		return 53;
	}

}
