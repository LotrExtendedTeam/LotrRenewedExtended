package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.WeightedStructureConfig;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ExtendedConfiguredStructuresV5 {
	/**
     * Static instances of our structures so we can reference them and add it to biomes easily.
     */
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_BARN = register(ExtendedStructuresV5.HOBBIT_BARN.get().configured(new SinglePoolConfig("hobbit/hobbit_barn_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOLE_SMALL = register(ExtendedStructuresV5.HOBBIT_HOLE_SMALL.get().configured(new SinglePoolConfig("hobbit/hobbit_hole_small_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOLE_LARGE = register(ExtendedStructuresV5.HOBBIT_HOLE_LARGE.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOUSE = register(ExtendedStructuresV5.HOBBIT_HOUSE.get().configured(new SinglePoolConfig("hobbit/hobbit_house_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_PICNIC_BLANKET = register(ExtendedStructuresV5.HOBBIT_PICNIC_BLANKET.get().configured(new SinglePoolConfig("hobbit/hobbit_picnic_blanket_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_PICNIC_TABLE = register(ExtendedStructuresV5.HOBBIT_PICNIC_TABLE.get().configured(new SinglePoolConfig("hobbit/hobbit_picnic_table_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_TAVERN_SMALL = register(ExtendedStructuresV5.HOBBIT_TAVERN_SMALL.get().configured(new SinglePoolConfig("hobbit/hobbit_tavern_small_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_TAVERN_LARGE = register(ExtendedStructuresV5.HOBBIT_TAVERN_LARGE.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_WINDMILL = register(ExtendedStructuresV5.HOBBIT_WINDMILL.get().configured(new SinglePoolConfig("hobbit/hobbit_windmill_pool")));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_CAMP = register(ExtendedStructuresV5.RANGER_CAMP.get().configured(new SinglePoolConfig("ranger/camp_parts/firepit_pool")));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_ENCAMPMENT = register(ExtendedStructuresV5.RANGER_ENCAMPMENT.get().configured(new SinglePoolConfig("ranger/encampment_pool")));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_FORT = register(ExtendedStructuresV5.RANGER_FORT.get().configured(new SinglePoolConfig("ranger/fort_pool")));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_HIDEOUT = register(ExtendedStructuresV5.RANGER_HIDEOUT.get().configured(new SinglePoolConfig("ranger/hideout_pool")));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_WATCHTOWER = register(ExtendedStructuresV5.RANGER_WATCHTOWER.get().configured(new SinglePoolConfig("ranger/watchtower_pool")));
	public static WeightedStructureConfig rangerHouseHideoutConfig = new WeightedStructureConfig()
			.addStructureToPool("ranger/hideout_house_pool", 50).addStructureToPool("ranger/ruins_pool", 70).addStructureToPool("ranger/hideout_house_pool2", 50);
	public static StructureFeature<?, ?> CONFIGURED_RANGER_HOUSE_HIDEOUT = register(ExtendedStructuresV5.RANGER_HOUSE_HIDEOUT.get().configured(rangerHouseHideoutConfig));
	//public static StructureFeature<?, ?> CONFIGURED_RANGER_HOUSE_RUIN = register(ExtendedStructuresV5.RANGER_HOUSE_RUIN.get().configured(new SinglePoolConfig("ranger/ruins_pool")));
	public static WeightedStructureConfig arnorRuinsConfig = new WeightedStructureConfig()
			.addStructureToPool("ruins/arnor_ruins_pool", 50).addStructureToPool("ruins/general_ruins_pool", 50);
	public static StructureFeature<?, ?> CONFIGURED_ARNOR_RUINS = register(ExtendedStructuresV5.ARNOR_RUINS.get().configured(arnorRuinsConfig));
	public static StructureFeature<?, ?> CONFIGURED_RUINED_HOUSE = register(ExtendedStructuresV5.RANGER_HOUSE_RUIN.get().configured(new SinglePoolConfig("ranger/ruins_pool")));
	public static StructureFeature<?, ?> CONFIGURED_GUNDABAD_CAMP = register(ExtendedStructuresV5.GUNDABAD_CAMP.get().configured(new SinglePoolConfig("gundabad/camp_parts/firepit_pool")));
	public static StructureFeature<?, ?> CONFIGURED_GUNDABAD_ENCAMPMENT = register(ExtendedStructuresV5.GUNDABAD_ENCAMPMENT.get().configured(new SinglePoolConfig("gundabad/gundabad_encampment_pool")));
	public static WeightedStructureConfig barrowDownsRuinsConfig = new WeightedStructureConfig().addStructureToPool("ruins/arnor_other_ruins_pool", 15)
		.addStructureToPool("ruins/arnor_tower_ruins_pool", 5).addStructureToPool("ruins/general_ruins_pool", 80);
	public static StructureFeature<?, ?> CONFIGURED_BARROW_DOWNS_RUINS = register(ExtendedStructuresV5.BARROW_DOWN_RUINS.get().configured(barrowDownsRuinsConfig));
	public static StructureFeature<?, ?> CONFIGURED_BARROW_DOWN_BARROWS = register(ExtendedStructuresV5.BARROW_DOWN_BARROWS.get().configured(new WeightedStructureConfig().addStructureToPool("barrow_downs/barrow_pool", 1)));

	public static void registerConfiguredStructures(){}

	/**
     * Registers the configured structure which is what gets added to the biomes.
     * Noticed this is not using a forge registry because there is none for configured structures.
     *
     * We can register configured structures at any time before a world is clicked on and made.
     * But the best time to register configured features by code is honestly to do it in FMLCommonSetupEvent.
     */
    public static StructureFeature<?, ?> register(StructureFeature<?, ?> structure) {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(LotrExtendedMod.MODID, "configured_"+structure.feature.getRegistryName().getPath()), structure);
        /* Ok so, this part may be hard to grasp but basically, just add your structure to this to
        * prevent any sort of crash or issue with other mod's custom ChunkGenerators. If they use
        * FlatGenerationSettings.STRUCTURE_FEATURES in it and you don't add your structure to it, the game
        * could crash later when you attempt to add the StructureSeparationSettings to the dimension.
        *
        * (It would also crash with Superflat WorldType if you omit the below line
        * and attempt to add the structure's StructureSeparationSettings to the world)
        *
        * Note: If we want to have our structures spawn in Superflat, remove the FlatChunkGenerator check
        * in addDimensionalSpacing and then create a Superflat world, exit it,
        * and re-enter it and the structures will be spawning. I could not figure out why it needs
        * the restart but honestly, Superflat is really buggy and shouldn't be your main focus in my opinion.
        */
        FlatGenerationSettings.STRUCTURE_FEATURES.put(structure.feature, structure);
        return structure;
    }
}
