package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedBiomes;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class AdvancementsProviderEriador extends ExtendedBaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> ERIADOR_BIOMES = ImmutableList.of(LOTRBiomes.ERIADOR, LOTRBiomes.ERIADOR_DOWNS,
			LOTRBiomes.LONE_LANDS, LOTRBiomes.LONE_LANDS_HILLS, LOTRBiomes.MIDGEWATER, LOTRBiomes.MINHIRIATH, LOTRBiomes.ERYN_VORN,
			LOTRBiomes.TROLLSHAWS, LOTRBiomes.ETTENMOORS, LOTRBiomes.COLDFELLS, LOTRBiomes.SWANFLEET, LOTRBiomes.RIVENDELL,
			LOTRBiomes.RIVENDELL_HILLS, ExtendedBiomes.BARROW_DOWNS, ExtendedBiomes.BARROW_DOWNS_RIVER);//, LOTRBiomes.THE_ANGLE);

	@Override
	public void generateAdvancements() {
		Advancement eriadorBase = build(true, dataMain(LOTRItems.ARNOR_BRICK.get(), "the_kindly_west", FrameType.TASK)
				.setTitle("The Kindly West").setDescription("Enter Eriador."),
				addBiomeTriggers(make(), ERIADOR_BIOMES));

		Advancement lone_lands = build(true, data(LOTRItems.CRACKED_ARNOR_BRICK.get(), "a_lonely_land", FrameType.TASK)
				.setTitle("A Lonely Land").setDescription("Enter the Lone Lands."),
				addBiomeTriggers(make(), LOTRBiomes.LONE_LANDS, LOTRBiomes.LONE_LANDS_HILLS).parent(eriadorBase));
		Advancement gun_orc_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "gundabad_orc_slayer", FrameType.TASK)
				.setTitle("Gundabad Orc Slayer").setDescription("Kill a Gundabad Orc."),
				addKillEntityTriggers(make(), LOTREntities.GUNDABAD_ORC.get(), LOTREntities.GUNDABAD_ORC_ARCHER.get(),
					ExtendedEntities.GUNDABAD_CAMP_TRADER.get(), ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN.get()).parent(lone_lands));
		Advancement gun_uruk_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "gundabad_uruk_slayer", FrameType.GOAL)
			.setTitle("Bigger, Stronger, Meaner").setDescription("Kill a Gundabad Uruk."),
			addKillEntityTriggers(make(), ExtendedEntities.GUNDABAD_URUK.get(), ExtendedEntities.GUNDABAD_URUK_ARCHER.get()).parent(gun_orc_slayer));

		Advancement ranger_armor = build(true, data(LOTRItems.RANGER_NORTH_CHESTPLATE.get(), "watcher", FrameType.TASK)
				.setTitle("Watcher").setDescription("Acquire a full set of Ranger armor."),
				addArmorTrigger(make(), "ranger_north", LOTRItems.RANGER_NORTH_HELMET.get(), LOTRItems.RANGER_NORTH_CHESTPLATE.get(),
						LOTRItems.RANGER_NORTH_LEGGINGS.get(), LOTRItems.RANGER_NORTH_BOOTS.get()).parent(eriadorBase));
		Advancement arnor_armor = build(true, data(LOTRItems.ARNOR_CHESTPLATE.get(), "memories_of_the_north", FrameType.TASK)
				.setTitle("Memories of the North").setDescription("Acquire a full set of Arnorian armor."),
				addArmorTrigger(make(), "arnor", LOTRItems.ARNOR_HELMET.get(), LOTRItems.ARNOR_CHESTPLATE.get(),
						LOTRItems.ARNOR_LEGGINGS.get(), LOTRItems.ARNOR_BOOTS.get()).parent(eriadorBase));

//		Advancement dunedain_slayer = build(true, data(Items.BONE, "dunedain_slayer", FrameType.TASK)
//				.setTitle("Dúnedain Slayer").setDescription("Kill a Dúnedain of the North."),
//				addKillEntityTrigger(make(), ExtendedEntities.DUNEDAIN_RANGER.get()).parent(eriadorBase));
		Advancement ranger_slayer = build(true, data(ExtendedItems.DUNEDAIN_BOW.get(), "ranger_slayer", FrameType.TASK)
				.setTitle("Outlaw").setDescription("Kill a Dúnedain Ranger of the North."),
				addKillEntityTriggers(make(), ExtendedEntities.DUNEDAIN_RANGER.get(), ExtendedEntities.DUNEDAIN_RANGER_CAPTAIN.get()).parent(eriadorBase));

		Advancement gundabad_uruk_armor = build(true, data(ExtendedItems.GUNDABAD_URUK_CHESTPLATE.get(), "scourge_of_the_mountains", FrameType.TASK)
				.setTitle("Scourge of the Mountains").setDescription("Acquire a full set of Gundabad Uruk armor."),
				addArmorTrigger(make(), "gundabad_uruk", ExtendedItems.GUNDABAD_URUK_HELMET.get(), ExtendedItems.GUNDABAD_URUK_CHESTPLATE.get(),
					ExtendedItems.GUNDABAD_URUK_LEGGINGS.get(), ExtendedItems.GUNDABAD_URUK_BOOTS.get()).parent(eriadorBase));

//		Advancement theAngle = build(data(Items.GREEN_WOOL, "a_fastness_in_the_wild", FrameType.TASK)
//				.setTitle("A Fastness in the Wild").setDescription("Enter the Lands of the Angle."),
//				addBiomeTrigger(make(),LOTRBiomes.THE_ANGLE).parent(eriadorBase));
		
		Advancement minhiriath = build(true, data(Items.GRASS_BLOCK, "between_the_rivers", FrameType.TASK)
				.setTitle("Between the Rivers").setDescription("Enter the region of Minhiriath."),
				addBiomeTrigger(make(), LOTRBiomes.MINHIRIATH).parent(eriadorBase));

		Advancement midgewater = build(true, data(LOTRItems.QUAGMIRE.get(), "more_midges_than_water", FrameType.TASK)
				.setTitle("More Midges than Water!").setDescription("Enter the marshes of Midgewater."),
				addBiomeTrigger(make(), LOTRBiomes.MIDGEWATER).parent(eriadorBase));

		Advancement troll_shaws = build(true, data(Items.COOKED_MUTTON, "roast_mutton", FrameType.TASK)
				.setTitle("Roast Mutton").setDescription("Enter the Trollshaws."),
				addBiomeTrigger(make(), LOTRBiomes.TROLLSHAWS).parent(eriadorBase));

		Advancement rivendell = build(true, data(LOTRItems.HIGH_ELVEN_BRICK.get(), "the_hidden_valley", FrameType.TASK)
				.setTitle("The Hidden Valley").setDescription("Enter the realm of Rivendell."),
				addBiomeTriggers(make(), LOTRBiomes.RIVENDELL, LOTRBiomes.RIVENDELL_HILLS).parent(eriadorBase));
		Advancement rivendell_armor = build(true, data(LOTRItems.RIVENDELL_CHESTPLATE.get(), "livery_of_lord_elrond", FrameType.TASK)
				.setTitle("Livery of Lord Elrond").setDescription("Acquire a full set of Rivendell armor."),
				addArmorTrigger(make(), "rivendell", LOTRItems.RIVENDELL_HELMET.get(), LOTRItems.RIVENDELL_CHESTPLATE.get(),
						LOTRItems.RIVENDELL_LEGGINGS.get(), LOTRItems.RIVENDELL_BOOTS.get()).parent(rivendell));
		Advancement riv_elf_slayer = build(true, data(LOTRItems.ELF_BONE.get(), "rivendell_elf_slayer", FrameType.TASK)
				.setTitle("Rivendell Elf Slayer").setDescription("Kill a Rivendell Elf."),
				addKillEntityTriggers(make(), LOTREntities.RIVENDELL_ELF.get(), LOTREntities.RIVENDELL_WARRIOR.get(), 
					ExtendedEntities.RIVENDELL_WANDERER.get()).parent(rivendell));

		Advancement coldfells = build(true, data(Items.OAK_SAPLING, "the_wild_north", FrameType.TASK)
				.setTitle("The Wild North").setDescription("Enter the Coldfells."),
				addBiomeTrigger(make(), LOTRBiomes.COLDFELLS).parent(eriadorBase));

		Advancement ettenmoors = build(true, data(Items.SPRUCE_SAPLING, "the_troll_fells", FrameType.TASK)
				.setTitle("The Troll-fells").setDescription("Enter the Ettenmoors."),
				addBiomeTrigger(make(), LOTRBiomes.ETTENMOORS).parent(eriadorBase));

		Advancement swanfleet = build(true, data(Items.LILY_PAD, "nin_in_eilph", FrameType.TASK)
				.setTitle("Nîn-in-Eilph").setDescription("Enter the fenlands of Swanfleet."),
				addBiomeTrigger(make(), LOTRBiomes.SWANFLEET).parent(eriadorBase));

		Advancement ranger_10 = build(true, data(LOTRItems.GOLD_RING.get(), "dunedain_friend", FrameType.TASK)
				.setTitle("Dúnedain Friend").setDescription("Reach +10.0 alignment with the Dúnedain of the North."),
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 10).parent(eriadorBase));

		Advancement ranger_50 = build(true, data(LOTRItems.GOLD_RING.get(), "dunedain_warden", FrameType.TASK)
				.setTitle("Dúnedain Warden").setDescription("Reach +50.0 alignment with the Dúnedain of the North."),
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 50).parent(ranger_10));

		Advancement ranger_100 = build(true, data(LOTRItems.GOLD_RING.get(), "ranger_of_the_north", FrameType.TASK)
				.setTitle("Ranger of the North").setDescription("Reach +100.0 alignment with the Dúnedain of the North."),
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 100).parent(ranger_50));

		Advancement ranger_200 = build(true, data(LOTRItems.GOLD_RING.get(), "ohtar_of_the_north", FrameType.TASK)
				.setTitle("Ohtar of the North").setDescription("Reach +200.0 alignment with the Dúnedain of the North."),
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 200).parent(ranger_100));

		Advancement ranger_500 = build(true, data(LOTRItems.GOLD_RING.get(), "roquen_of_the_north", FrameType.TASK)
				.setTitle("Roquen of the North").setDescription("Reach +500.0 alignment with the Dúnedain of the North."),
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 500).parent(ranger_200));

		Advancement ranger_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "champion_of_the_north", FrameType.GOAL)
				.setTitle("Champion of the North").setDescription("Reach +1000.0 alignment with the Dúnedain of the North."),
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 1000).parent(ranger_500));

		Advancement ranger_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "captain_of_the_north", FrameType.CHALLENGE)
				.setTitle("Captain of the North").setDescription("Reach +2000.0 alignment with the Dúnedain of the North."),
				addAlignmentTrigger(make(), FactionPointers.DUNEDAIN_NORTH, 2000).parent(ranger_1000));

		Advancement orc_10 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_thrall", FrameType.TASK)
				.setTitle("Mountain-thrall").setDescription("Reach +10.0 alignment with the Orcs of the North."),
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 10).parent(eriadorBase));

		Advancement orc_50 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_snaga", FrameType.TASK)
				.setTitle("Mountain-snaga").setDescription("Reach +50.0 alignment with the Orcs of the North."),
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 50).parent(orc_10));

		Advancement orc_100 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_raider", FrameType.TASK)
				.setTitle("Gundabad Raider").setDescription("Reach +100.0 alignment with the Orcs of the North."),
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 100).parent(orc_50));

		Advancement orc_200 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_ravager", FrameType.TASK)
				.setTitle("Gundabad Ravager").setDescription("Reach +200.0 alignment with the Orcs of the North."),
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 200).parent(orc_100));

		Advancement orc_500 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_scourge", FrameType.TASK)
				.setTitle("Gundabad Scourge").setDescription("Reach +500.0 alignment with the Orcs of the North."),
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 500).parent(orc_200));

		Advancement orc_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_warlord", FrameType.GOAL)
				.setTitle("Gundabad Warlord").setDescription("Reach +1000.0 alignment with the Orcs of the North."),
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 1000).parent(orc_500));

		Advancement orc_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "orc_chieftain", FrameType.CHALLENGE)
				.setTitle("Gundabad Chieftain").setDescription("Reach +2000.0 alignment with the Orcs of the North."),
				addAlignmentTrigger(make(), FactionPointers.NORTHERN_ORCS, 2000).parent(orc_1000));

		Advancement riven_elf_trader = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "treasures_of_imladris", FrameType.TASK)
				.setTitle("Treasures of Imladris").setDescription("Trade with a Rivendell Wanderer."),
				addTradeEntityTrigger(make(), ExtendedEntities.RIVENDELL_WANDERER.get()).parent(eriadorBase));

		Advancement orc_trader = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "scrounging_and_scavenging", FrameType.TASK)
						.setTitle("Scrounging and Scavenging").setDescription("Trade with a Gundabad Orc Scrounger."),
				addTradeEntityTrigger(make(), ExtendedEntities.GUNDABAD_CAMP_TRADER.get()).parent(eriadorBase));
	}

	@Override
	public String getFolder() {
		return "eriador";
	}

}
