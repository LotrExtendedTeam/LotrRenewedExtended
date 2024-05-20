package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.PositionTrigger;


public class EriadorAdvancementsProvider extends BaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement base = build(data(LOTRItems.ARNOR_BRICK.get(), "the_kindly_west", true, FrameType.TASK, true, true, false), 
				make().addCriterion("enter_eriador", PositionTrigger.Instance.located(LocationPredicate.inBiome(LOTRBiomes.ERIADOR))));
	}

	@Override
	public String getFolder() {
		return "eriador";
	}

}
