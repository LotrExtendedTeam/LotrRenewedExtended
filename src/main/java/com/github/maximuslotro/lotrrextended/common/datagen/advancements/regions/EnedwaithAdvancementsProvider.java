package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;


public class EnedwaithAdvancementsProvider extends BaseAdvancementType{

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement enedwaith = build(true, data(Items.GRASS_BLOCK, "forsaken_country", true, FrameType.TASK, true, true, false)
				.setTitle("Forsaken Country").setDescription("Enter the Region of Enedwaith.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.ENEDWAITH));

		Advancement old_pukel_land = build(true, data(Items.DARK_OAK_SAPLING, "old_pukel_land", true, FrameType.TASK, true, true, false)
				.setTitle("Old Púkel Land").setDescription("Enter the region of Drúwaith Iaur.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.DRUWAITH_IAUR).parent(enedwaith));

		Advancement everlasting_chill = build(true, data(LOTRItems.URUK_BERSERKER_CLEAVER.get(), "vale_of_the_wizard", true, FrameType.TASK, true, true, false)
				.setTitle("Vale of the Wizard").setDescription("Enter the valley of Nan Curunír.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.NAN_CURUNIR).parent(enedwaith));
	}

	@Override
	public String getFolder() {
		return "enedwaith";
	}

}
