package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;

public class AdvancementsProviderGondor extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement kingdom_of_men = build(true, dataMain(LOTRItems.GONDOR_BRICK.get(), "kingdom_of_men", FrameType.TASK)
				.setTitle("The Kingdom of Men").setDescription("Enter the land of Gondor."),
				addBiomeTriggers(make(), LOTRBiomes.FURTHER_GONDOR, LOTRBiomes.ANFALAS, LOTRBiomes.ANORIEN, LOTRBiomes.ANDRAST, LOTRBiomes.BLACKROOT_VALE, 
					LOTRBiomes.WHITE_MOUNTAINS, LOTRBiomes.WHITE_MOUNTAINS_FOOTHILLS, LOTRBiomes.ETHIR_ANDUIN, LOTRBiomes.LEBENNIN, LOTRBiomes.ITHILIEN, 
					LOTRBiomes.DOR_EN_ERNIL, LOTRBiomes.PINNATH_GELIN, LOTRBiomes.TOLFALAS, LOTRBiomes.PELARGIR, 
					LOTRBiomes.LOSSARNACH));

		Advancement bows_and_blackroots = build(true, data(LOTRItems.BLACKROOT.get(), "bows_and_blackroots", FrameType.TASK)
				.setTitle("Bows and Blackroots").setDescription("Enter the fiefdom of Blackroot Vale."),
				addBiomeTrigger(make(), LOTRBiomes.BLACKROOT_VALE).parent(kingdom_of_men));

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

	}

	@Override
	public String getFolder() {
		return "rohan";
	}

}
