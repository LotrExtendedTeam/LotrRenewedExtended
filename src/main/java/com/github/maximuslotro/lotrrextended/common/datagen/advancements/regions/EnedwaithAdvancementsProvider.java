package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;

import lotr.common.init.LOTRBiomes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class EnedwaithAdvancementsProvider extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement enedwaith = build(true, data(Items.GRASS_BLOCK, "forsaken_country", true, FrameType.TASK, true, true, false)
				.setTitle("Forsaken Country").setDescription("Enter the Region of Enedwaith."),
				addBiomeTrigger(make(), LOTRBiomes.ENEDWAITH));

		Advancement old_pukel_land = build(true, data(Items.DARK_OAK_SAPLING, "old_pukel_land", false, FrameType.TASK, true, true, false)
				.setTitle("Old Púkel Land").setDescription("Enter the region of Drúwaith Iaur."),
				addBiomeTrigger(make(), LOTRBiomes.DRUWAITH_IAUR).parent(enedwaith));
	}

	@Override
	public String getFolder() {
		return "enedwaith";
	}

}
