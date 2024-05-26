package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;


public class EriadorAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> ERIADOR_BIOMES = ImmutableList.of(LOTRBiomes.ERIADOR, LOTRBiomes.ERIADOR_DOWNS, 
			LOTRBiomes.LONE_LANDS, LOTRBiomes.LONE_LANDS_HILLS, LOTRBiomes.MIDGEWATER, LOTRBiomes.MINHIRIATH, LOTRBiomes.ERYN_VORN, 
			LOTRBiomes.TROLLSHAWS, LOTRBiomes.ETTENMOORS, LOTRBiomes.COLDFELLS, LOTRBiomes.SWANFLEET, LOTRBiomes.RIVENDELL, 
			LOTRBiomes.RIVENDELL_HILLS);//, LOTRBiomes.THE_ANGLE,LOTRBiomes.BARROW_DOWNS);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement eriadorBase = build(true, data(LOTRItems.ARNOR_BRICK.get(), "the_kindly_west", true, FrameType.TASK, true, true, false)
				.setTitle("The Kindly West").setDescription("Enter Eriador.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), ERIADOR_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement lone_lands = build(true, data(LOTRItems.CRACKED_ARNOR_BRICK.get(), "a_lonely_land", false, FrameType.TASK, true, true, false)
				.setTitle("A Lonely Land").setDescription("Enter the Lone Lands.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(addBiomeTrigger(make(), LOTRBiomes.LONE_LANDS), LOTRBiomes.LONE_LANDS_HILLS).requirements(IRequirementsStrategy.OR)
				.parent(eriadorBase));
		Advancement gun_orc_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "gundabad_orc_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Gundabad Orc Slayer").setDescription("Kill a Gundabad Orc.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.GUNDABAD_ORC.get()), LOTREntities.GUNDABAD_ORC_ARCHER.get())
				.requirements(IRequirementsStrategy.OR).parent(lone_lands));

		Advancement ranger_armor = build(true, data(LOTRItems.RANGER_NORTH_CHESTPLATE.get(), "watcher", false, FrameType.TASK, true, true, false)
				.setTitle("Watcher").setDescription("Acquire a full set of Ranger armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "ranger_north", LOTRItems.RANGER_NORTH_HELMET.get(), LOTRItems.RANGER_NORTH_CHESTPLATE.get(), 
						LOTRItems.RANGER_NORTH_LEGGINGS.get(), LOTRItems.RANGER_NORTH_BOOTS.get()).parent(eriadorBase));
		Advancement arnor_armor = build(true, data(LOTRItems.ARNOR_CHESTPLATE.get(), "memories_of_the_north", false, FrameType.TASK, true, true, false)
				.setTitle("Memories of the North").setDescription("Acquire a full set of Arnorian armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "arnor", LOTRItems.ARNOR_HELMET.get(), LOTRItems.ARNOR_CHESTPLATE.get(), 
						LOTRItems.ARNOR_LEGGINGS.get(), LOTRItems.ARNOR_BOOTS.get()).parent(eriadorBase));

		Advancement dunedain_slayer = build(true, data(Items.BONE, "dunedain_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Dúnedain Slayer").setDescription("Kill a Dúnedain of the North.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(make(), ExtendedEntities.DUNEDAIN_RANGER.get()).parent(eriadorBase));
		Advancement ranger_slayer = build(true, data(ExtendedItems.DUNEDAIN_BOW.get(), "ranger_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Outlaw").setDescription("Kill a Ranger of the North.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(make(), ExtendedEntities.DUNEDAIN_RANGER.get()).parent(eriadorBase));

//		Advancement gundabad_orc_slayer = build(data(lotrextended:gundabad_uruk_helmet, "bigger_stronger_meaner", false, FrameType.TASK, true, true, false)
//				.setTitle("Bigger, Stronger, Meaner").setDescription("Kill a Gundabad Uruk.").setTitleStype(TextFormatting.GREEN), 
//				addArmorTrigger(slay trigger lotrextended:gundabad_uruk).parent(eriadorBase));

//		Advancement gundabad_uruk_armor = build(data(LOTRItems.GUNDABAD_URUK_CHESTPLATE.get(), "scourge_of_the_mountains", false, FrameType.TASK, true, true, false)
//				.setTitle("Scourge of the Mountains").setDescription("Acquire a full set of Gundabad Uruk armor.").setTitleStype(TextFormatting.GREEN), 
//				addArmorTrigger(make(), "gundabad_uruk", LOTRItems.GUNDABAD_URUK_HELMET.get(), LOTRItems.GUNDABAD_URUK_CHESTPLATE.get(), 
//						LOTRItems.GUNDABAD_URUK_LEGGINGS.get(), LOTRItems.GUNDABAD_URUK_BOOTS.get()).parent(eriadorBase));

//		Advancement theAngle = build(data(Items.GREEN_WOOL, "a_fastness_in_the_wild", false, FrameType.TASK, true, true, false)
//				.setTitle("A Fastness in the Wild").setDescription("Enter the Lands of the Angle.").setTitleStype(TextFormatting.GREEN), 
//				addBiomeTrigger(make(),LOTRBiomes.THE_ANGLE).parent(eriadorBase));

//		Advancement theAngle = build(data(Items.BONE, the_hills_of_the_dead, false, FrameType.TASK, true, true, false)
//		.setTitle("The Hills of the Dead").setDescription("Enter the Barrow-downs.").setTitleStype(TextFormatting.GREEN), 
//		addBiomeTrigger(make(),LOTRBiomes.BARROW_DOWNS).parent(eriadorBase));

		Advancement minhiriath = build(true, data(Items.GRASS_BLOCK, "between_the_rivers", false, FrameType.TASK, true, true, false)
				.setTitle("Between the Rivers").setDescription("Enter the region of Minhiriath.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.MINHIRIATH).parent(eriadorBase));

		Advancement midgewater = build(true, data(LOTRItems.QUAGMIRE.get(), "more_midges_than_water", false, FrameType.TASK, true, true, false)
				.setTitle("More Midges than Water!").setDescription("Enter the marshes of Midgewater.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.MIDGEWATER).parent(eriadorBase));

		Advancement troll_shaws = build(true, data(Items.COOKED_MUTTON, "roast_mutton", false, FrameType.TASK, true, true, false)
				.setTitle("Roast Mutton").setDescription("Enter the Trollshaws.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.TROLLSHAWS).parent(eriadorBase));

		Advancement rivendell = build(true, data(LOTRItems.HIGH_ELVEN_BRICK.get(), "the_hidden_valley", false, FrameType.TASK, true, true, false)
				.setTitle("The Hidden Valley").setDescription("Enter the realm of Rivendell.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(addBiomeTrigger(make(), LOTRBiomes.RIVENDELL), LOTRBiomes.RIVENDELL_HILLS).requirements(IRequirementsStrategy.OR)
				.parent(eriadorBase));
		Advancement rivendell_armor = build(true, data(LOTRItems.RIVENDELL_CHESTPLATE.get(), "livery_of_lord_elrond", false, FrameType.TASK, true, true, false)
				.setTitle("Livery of Lord Elrond").setDescription("Acquire a full set of Rivendell armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "rivendell", LOTRItems.RIVENDELL_HELMET.get(), LOTRItems.RIVENDELL_CHESTPLATE.get(), 
						LOTRItems.RIVENDELL_LEGGINGS.get(), LOTRItems.RIVENDELL_BOOTS.get()).parent(rivendell));
		Advancement riv_elf_slayer = build(true, data(LOTRItems.ELF_BONE.get(), "rivendell_elf_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Rivendell Elf Slayer").setDescription("Kill a Rivendell Elf.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.RIVENDELL_ELF.get()), LOTREntities.RIVENDELL_WARRIOR.get())
				.requirements(IRequirementsStrategy.OR).parent(rivendell));

		Advancement coldfells = build(true, data(Items.OAK_SAPLING, "the_wild_north", false, FrameType.TASK, true, true, false)
				.setTitle("The Wild North").setDescription("Enter the Coldfells.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.COLDFELLS).parent(eriadorBase));

		Advancement ettenmoors = build(true, data(Items.SPRUCE_SAPLING, "the_troll_fells", false, FrameType.TASK, true, true, false)
				.setTitle("The Troll-fells").setDescription("Enter the Ettenmoors.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.ETTENMOORS).parent(eriadorBase));

		Advancement swanfleet = build(true, data(Items.LILY_PAD, "nin_in_eilph", false, FrameType.TASK, true, true, false)
				.setTitle("Nîn-in-Eilph").setDescription("Enter the fenlands of Swanfleet.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.SWANFLEET).parent(eriadorBase));

		Advancement ranger_10 = build(true, data(LOTRItems.GOLD_RING.get(), "dunedain_friend", false, FrameType.TASK, true, true, false)
				.setTitle("Dúnedain Friend").setDescription("Reach +10.0 alignment with the Dúnedain of the North.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 10).parent(eriadorBase));

		Advancement ranger_50 = build(true, data(LOTRItems.GOLD_RING.get(), "dunedain_warden", false, FrameType.TASK, true, true, false)
				.setTitle("Dúnedain Warden").setDescription("Reach +50.0 alignment with the Dúnedain of the North.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 50).parent(ranger_10));

		Advancement ranger_100 = build(true, data(LOTRItems.GOLD_RING.get(), "ranger_of_the_north", false, FrameType.TASK, true, true, false)
				.setTitle("Ranger of the North").setDescription("Reach +100.0 alignment with the Dúnedain of the North.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 100).parent(ranger_50));

		Advancement ranger_200 = build(true, data(LOTRItems.GOLD_RING.get(), "ohtar_of_the_north", false, FrameType.TASK, true, true, false)
				.setTitle("Ohtar of the North").setDescription("Reach +200.0 alignment with the Dúnedain of the North.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 200).parent(ranger_100));

		Advancement ranger_500 = build(true, data(LOTRItems.GOLD_RING.get(), "roquen_of_the_north", false, FrameType.TASK, true, true, false)
				.setTitle("Roquen of the North").setDescription("Reach +500.0 alignment with the Dúnedain of the North.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 500).parent(ranger_200));

		Advancement ranger_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "champion_of_the_north", false, FrameType.GOAL, true, true, false)
				.setTitle("Champion of the North").setDescription("Reach +1000.0 alignment with the Dúnedain of the North.").setTitleStype(TextFormatting.GOLD), 
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 1000).parent(ranger_500));

		Advancement ranger_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "captain_of_the_north", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Captain of the North").setDescription("Reach +2000.0 alignment with the Dúnedain of the North.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 2000).parent(ranger_1000));

		Advancement orc_10 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_thrall", false, FrameType.TASK, true, true, false)
				.setTitle("Mountain-thrall").setDescription("Reach +10.0 alignment with the Orcs of the North.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 10).parent(eriadorBase));

		Advancement orc_50 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_snaga", false, FrameType.TASK, true, true, false)
				.setTitle("Mountain-snaga").setDescription("Reach +50.0 alignment with the Orcs of the North.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 50).parent(orc_10));

		Advancement orc_100 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_raider", false, FrameType.TASK, true, true, false)
				.setTitle("Gundabad Raider").setDescription("Reach +100.0 alignment with the Orcs of the North.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 100).parent(orc_50));

		Advancement orc_200 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_ravager", false, FrameType.TASK, true, true, false)
				.setTitle("Gundabad Ravager").setDescription("Reach +200.0 alignment with the Orcs of the North.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 200).parent(orc_100));

		Advancement orc_500 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_scourge", false, FrameType.TASK, true, true, false)
				.setTitle("Gundabad Scourge").setDescription("Reach +500.0 alignment with the Orcs of the North.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 500).parent(orc_200));

		Advancement orc_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_warlord", false, FrameType.GOAL, true, true, false)
				.setTitle("Gundabad Warlord").setDescription("Reach +1000.0 alignment with the Orcs of the North.").setTitleStype(TextFormatting.GOLD), 
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 1000).parent(orc_500));

		Advancement orc_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_chieftain", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Gundabad Chieftain").setDescription("Reach +2000.0 alignment with the Orcs of the North.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 2000).parent(orc_1000));

		Advancement riven_elf_trader = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "treasures_of_imladris", false, FrameType.TASK, true, true, false)
				.setTitle("Treasures of Imladris").setDescription("Trade with a Rivendell Wanderer.").setTitleStype(TextFormatting.GREEN), 
				addTradeEntityTrigger(make(), ExtendedEntities.RIVENDELL_WANDERER.get()).parent(eriadorBase));
	}

	@Override
	public String getFolder() {
		return "eriador";
	}

}
