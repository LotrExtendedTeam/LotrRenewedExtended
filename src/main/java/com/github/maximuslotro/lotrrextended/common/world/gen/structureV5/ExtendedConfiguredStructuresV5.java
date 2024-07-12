package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

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
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_BARN = register(ExtendedStructuresV5.HOBBIT_BARN.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOLE_SMALL = register(ExtendedStructuresV5.HOBBIT_HOLE_SMALL.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOLE_LARGE = register(ExtendedStructuresV5.HOBBIT_HOLE_LARGE.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOUSE = register(ExtendedStructuresV5.HOBBIT_HOUSE.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_PICNIC_BLANKET = register(ExtendedStructuresV5.HOBBIT_PICNIC_BLANKET.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_PICNIC_TABLE = register(ExtendedStructuresV5.HOBBIT_PICNIC_TABLE.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_TAVERN_SMALL = register(ExtendedStructuresV5.HOBBIT_TAVERN_SMALL.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_TAVERN_LARGE = register(ExtendedStructuresV5.HOBBIT_TAVERN_LARGE.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_WINDMILL = register(ExtendedStructuresV5.HOBBIT_WINDMILL.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_CAMP = register(ExtendedStructuresV5.RANGER_CAMP.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_ENCAMPMENT = register(ExtendedStructuresV5.RANGER_ENCAMPMENT.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_FORT = register(ExtendedStructuresV5.RANGER_FORT.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_HIDEOUT = register(ExtendedStructuresV5.RANGER_HIDEOUT.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_WATCHTOWER = register(ExtendedStructuresV5.RANGER_WATCHTOWER.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_HOUSE_HIDEOUT = register(ExtendedStructuresV5.RANGER_HOUSE_HIDEOUT.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_HOUSE_RUIN = register(ExtendedStructuresV5.RANGER_HOUSE_RUIN.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_ARNOR_RUINS = register(ExtendedStructuresV5.ARNOR_RUINS.get().configured(IFeatureConfig.NONE));

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
