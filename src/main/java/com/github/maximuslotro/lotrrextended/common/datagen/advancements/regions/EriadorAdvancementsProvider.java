package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

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
		Advancement eriadorBase = build(data(LOTRItems.ARNOR_BRICK.get(), "the_kindly_west", true, FrameType.TASK, true, true, false)
				.setTitle("The Kindly West").setDescription("Enter Eriador.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), ERIADOR_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement lone_lands = build(data(LOTRItems.CRACKED_ARNOR_BRICK.get(), "a_lonely_land", false, FrameType.TASK, true, true, false)
				.setTitle("A Lonely Land").setDescription("Enter the Lone Lands.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(addBiomeTrigger(make(), LOTRBiomes.LONE_LANDS), LOTRBiomes.LONE_LANDS_HILLS).requirements(IRequirementsStrategy.OR)
				.parent(eriadorBase));
		Advancement gun_orc_slayer = build(data(LOTRItems.ORC_BONE.get(), "gundabad_orc_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Gundabad Orc Slayer").setDescription("Kill a Gundabad Orc.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.GUNDABAD_ORC.get()), LOTREntities.GUNDABAD_ORC_ARCHER.get())
				.requirements(IRequirementsStrategy.OR).parent(lone_lands));

		Advancement ranger_armor = build(data(LOTRItems.RANGER_NORTH_CHESTPLATE.get(), "watcher", false, FrameType.TASK, true, true, false)
				.setTitle("Watcher").setDescription("Acquire a full set of Ranger armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "ranger_north", LOTRItems.RANGER_NORTH_HELMET.get(), LOTRItems.RANGER_NORTH_CHESTPLATE.get(), 
						LOTRItems.RANGER_NORTH_LEGGINGS.get(), LOTRItems.RANGER_NORTH_BOOTS.get()).parent(eriadorBase));
		Advancement arnor_armor = build(data(LOTRItems.ARNOR_CHESTPLATE.get(), "memories_of_the_north", false, FrameType.TASK, true, true, false)
				.setTitle("Memories of the North").setDescription("Acquire a full set of Arnorian armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "arnor", LOTRItems.ARNOR_HELMET.get(), LOTRItems.ARNOR_CHESTPLATE.get(), 
						LOTRItems.ARNOR_LEGGINGS.get(), LOTRItems.ARNOR_BOOTS.get()).parent(eriadorBase));

		Advancement dunedain_slayer = build(data(Items.BONE, "dunedain_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Dúnedain Slayer").setDescription("Kill a Dúnedain of the North.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(make(), ExtendedEntities.DUNEDAIN_RANGER.get()).parent(eriadorBase));
		Advancement ranger_slayer = build(data(ExtendedItems.DUNEDAIN_BOW.get(), "ranger_slayer", false, FrameType.TASK, true, true, false)
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

		Advancement minhiriath = build(data(Items.GRASS_BLOCK, "between_the_rivers", false, FrameType.TASK, true, true, false)
				.setTitle("Between the Rivers").setDescription("Enter the region of Minhiriath.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.MINHIRIATH).parent(eriadorBase));

		Advancement midgewater = build(data(LOTRItems.QUAGMIRE.get(), "more_midges_than_water", false, FrameType.TASK, true, true, false)
				.setTitle("More Midges than Water!").setDescription("Enter the marshes of Midgewater.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.MIDGEWATER).parent(eriadorBase));

		Advancement troll_shaws = build(data(Items.COOKED_MUTTON, "roast_mutton", false, FrameType.TASK, true, true, false)
				.setTitle("Roast Mutton").setDescription("Enter the Trollshaws.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.TROLLSHAWS).parent(eriadorBase));

		Advancement rivendell = build(data(LOTRItems.HIGH_ELVEN_BRICK.get(), "the_hidden_valley", false, FrameType.TASK, true, true, false)
				.setTitle("The Hidden Valley").setDescription("Enter the realm of Rivendell.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(addBiomeTrigger(make(), LOTRBiomes.RIVENDELL), LOTRBiomes.RIVENDELL_HILLS).requirements(IRequirementsStrategy.OR)
				.parent(eriadorBase));
		Advancement rivendell_armor = build(data(LOTRItems.RIVENDELL_CHESTPLATE.get(), "livery_of_lord_elrond", false, FrameType.TASK, true, true, false)
				.setTitle("Livery of Lord Elrond").setDescription("Acquire a full set of Rivendell armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "rivendell", LOTRItems.RIVENDELL_HELMET.get(), LOTRItems.RIVENDELL_CHESTPLATE.get(), 
						LOTRItems.RIVENDELL_LEGGINGS.get(), LOTRItems.RIVENDELL_BOOTS.get()).parent(rivendell));
		Advancement riv_elf_slayer = build(data(LOTRItems.ELF_BONE.get(), "rivendell_elf_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Rivendell Elf Slayer").setDescription("Kill a Rivendell Elf.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.RIVENDELL_ELF.get()), LOTREntities.RIVENDELL_WARRIOR.get())
				.requirements(IRequirementsStrategy.OR).parent(rivendell));

		Advancement coldfells = build(data(Items.OAK_SAPLING, "the_wild_north", false, FrameType.TASK, true, true, false)
				.setTitle("The Wild North").setDescription("Enter the Coldfells.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.COLDFELLS).parent(eriadorBase));

		Advancement ettenmoors = build(data(Items.SPRUCE_SAPLING, "the_troll_fells", false, FrameType.TASK, true, true, false)
				.setTitle("The Troll-fells").setDescription("Enter the Ettenmoors.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.ETTENMOORS).parent(eriadorBase));

		Advancement swanfleet = build(data(Items.LILY_PAD, "nin_in_eilph", false, FrameType.TASK, true, true, false)
				.setTitle("Nîn-in-Eilph").setDescription("Enter the fenlands of Swanfleet.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.SWANFLEET).parent(eriadorBase));
	}

	@Override
	public String getFolder() {
		return "eriador";
	}

}
