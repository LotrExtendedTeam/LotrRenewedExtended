package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;


public class LothlorienAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> LOTHLORIEN_BIOMES = ImmutableList.of(LOTRBiomes.LOTHLORIEN, LOTRBiomes.LOTHLORIEN_EAVES);

	@Override
	public void generateAdvancements() {
		Advancement lothlorien = build(true, data(LOTRItems.MALLORN_LOG.get(), "lothlorien", true, FrameType.TASK, true, true, false)
				.setTitle("The Golden Wood").setDescription("Enter the woods of Lothlórien."),
				addBiomeTriggers(make(), LOTHLORIEN_BIOMES));

		Advancement elf_slayer = build(true, data(LOTRItems.ELF_BONE.get(), "elf_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Elf Slayer").setDescription("Kill a Galadhrim elf."),
				addKillEntityTriggers(make(), LOTREntities.GALADHRIM_ELF.get(), LOTREntities.GALADHRIM_WARRIOR.get()).parent(lothlorien));

		Advancement galadhrim_armor = build(true, data(LOTRItems.GALADHRIM_CHESTPLATE.get(), "galadhrim_armor", false, FrameType.TASK, true, true, false)
				.setTitle("Golden Wood Warrior").setDescription("Equip a full suit of Galadhrim armor."),
				addArmorTrigger(make(), "galadhrim", LOTRItems.GALADHRIM_HELMET.get(), LOTRItems.GALADHRIM_CHESTPLATE.get(),
						LOTRItems.GALADHRIM_LEGGINGS.get(), LOTRItems.GALADHRIM_BOOTS.get()).parent(lothlorien));

		Advancement edhelmir_ore = build(true, data(LOTRItems.EDHELVIR_ORE.get(), "edhelmir_ore", false, FrameType.GOAL, true, true, false)
				.setTitle("Glittering Depths").setDescription("Mine a crystal of Edhelmir."),
				addBreakBlockTriggers(make(), LOTRBlocks.EDHELVIR_ORE.get(), LOTRBlocks.EDHELVIR_CRYSTAL.get()).parent(lothlorien));

		Advancement gifts_from_afar = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "gifts_from_afar", false, FrameType.TASK, true, true, false)
				.setTitle("Gifts from Afar").setDescription("Trade with a Galadhrim Wanderer."),
				addTradeEntityTrigger(make(), ExtendedEntities.GALADHRIM_WANDERER.get()).parent(lothlorien));

		Advancement elf_10 = build(true, data(LOTRItems.GOLD_RING.get(), "elf_10", false, FrameType.TASK, true, true, false)
				.setTitle("Lórien Guest").setDescription("Reach +10.0 alignment with the Galadhrim."),
				addAlignmentTrigger(make(), FactionPointers.LOTHLORIEN, 10).parent(lothlorien));
		Advancement elf_50 = build(true, data(LOTRItems.GOLD_RING.get(), "elf_50", false, FrameType.TASK, true, true, false)
				.setTitle("Elvellon").setDescription("Reach +50.0 alignment with the Galadhrim."),
				addAlignmentTrigger(make(), FactionPointers.LOTHLORIEN, 50).parent(elf_10));
		Advancement elf_100 = build(true, data(LOTRItems.GOLD_RING.get(), "elf_100", false, FrameType.TASK, true, true, false)
				.setTitle("Glandirithon").setDescription("Reach +100.0 alignment with the Galadhrim."),
				addAlignmentTrigger(make(), FactionPointers.LOTHLORIEN, 100).parent(elf_50));
		Advancement elf_200 = build(true, data(LOTRItems.GOLD_RING.get(), "elf_200", false, FrameType.TASK, true, true, false)
				.setTitle("Maethor").setDescription("Reach +200.0 alignment with the Galadhrim."),
				addAlignmentTrigger(make(), FactionPointers.LOTHLORIEN, 200).parent(elf_100));
		Advancement elf_500 = build(true, data(LOTRItems.GOLD_RING.get(), "elf_500", false, FrameType.TASK, true, true, false)
				.setTitle("Condir").setDescription("Reach +500.0 alignment with the Galadhrim."),
				addAlignmentTrigger(make(), FactionPointers.LOTHLORIEN, 500).parent(elf_200));
		Advancement elf_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "elf_1000", false, FrameType.TASK, true, true, false)
				.setTitle("Hest Lórien").setDescription("Reach +1000.0 alignment with the Galadhrim."),
				addAlignmentTrigger(make(), FactionPointers.LOTHLORIEN, 1000).parent(elf_500));
		Advancement elf_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "elf_2000", false, FrameType.GOAL, true, true, false)
				.setTitle("Arphen Lórien").setDescription("Reach +2000.0 alignment with the Galadhrim."),
				addAlignmentTrigger(make(), FactionPointers.LOTHLORIEN, 2000).parent(elf_1000));
		Advancement elf_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "elf_3000", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Hîr/Híril Lórien").setDescription("Reach +3000.0 alignment with the Galadhrim."),
				addAlignmentTrigger(make(), FactionPointers.LOTHLORIEN, 3000).parent(elf_2000));
	}

	@Override
	public String getFolder() {
		return "lothlorien";
	}

}
