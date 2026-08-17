package com.github.maximuslotro.lotrrextended.common.datagen.structures;

import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.BrickWeathererProcessor;
import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.ExtendedArmorStandProcessor;
import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.GraniteWhiteWoolBeehiveThatchRemoverProcessor;
import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.ReplaceAirWithGrassVoidsProcessor;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;


public class ExtendedStructureProcessorListGenerator extends ExtendedStructureProcessorListProvider {

    public ExtendedStructureProcessorListGenerator(DataGenerator pGenerator, String pModid) {
        super(pGenerator, pModid);
    }

	@Override
	public void registerProcessors() {
		add("arnor_ruins_processor", listBuilder -> listBuilder
			.addProcessor(BrickWeathererProcessor.INSTANCE)
        );
		add("basic_armor_stand_processor", listBuilder -> listBuilder
			.addProcessor(ExtendedArmorStandProcessor.INSTANCE)
        );
		add("blue_mountains_settlement_underground_processor", listBuilder -> listBuilder
			.addProcessor(ReplaceAirWithGrassVoidsProcessor.INSTANCE)
        );
		add("gundabad_camp_roads_processor", listBuilder -> listBuilder
			.addProcessor(GraniteWhiteWoolBeehiveThatchRemoverProcessor.INSTANCE)
        );
		add("gundabad_camp_tent_processor", listBuilder -> listBuilder
			.addProcessor(BrickWeathererProcessor.INSTANCE)
        );
		add("ranger_camp_firepit", listBuilder -> listBuilder
			.addProcessor(GraniteWhiteWoolBeehiveThatchRemoverProcessor.INSTANCE)
        );
		add("ranger_camp_roads", listBuilder -> listBuilder
			.addProcessor(GraniteWhiteWoolBeehiveThatchRemoverProcessor.INSTANCE)
        );
		addReplacement("dunland_processor", Blocks.CAKE, 1.0F, weights -> weights
            .add(Blocks.AIR, 35)
            .add(Blocks.CAKE, 4)
            .add(ExtendedBlocks.BLACKBERRY_PIE, 8)
            .add(ExtendedBlocks.BLUEBERRY_PIE, 8)
            .add(ExtendedBlocks.CRANBERRY_PIE, 6)
            .add(ExtendedBlocks.ELDERBERRY_PIE, 7)
            .add(ExtendedBlocks.RASPBERRY_PIE, 8)
            .add(ExtendedBlocks.SWEET_BERRY_PIE, 6)
            .add(ExtendedBlocks.WILDBERRY_PIE, 2)
            .add(LOTRBlocks.APPLE_CRUMBLE, 6)
        );
	}
}
