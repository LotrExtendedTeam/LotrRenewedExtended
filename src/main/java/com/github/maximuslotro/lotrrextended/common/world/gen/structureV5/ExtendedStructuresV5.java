package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.WeightedStructureConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.SinglePoolStructure;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.WeightedPoolStructure;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.hobbits.HobbitHoleLargeStructure;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.hobbits.HobbitTavernLargeStructure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedStructuresV5 {
    public static final DeferredRegister<Structure<?>> STRUCTURE_REGISTRY = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, LotrExtendedMod.MODID);
    public static final RegistryObject<Structure<SinglePoolConfig>> HOBBIT_BARN = STRUCTURE_REGISTRY.register("hobbit_barn", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> HOBBIT_HOLE_SMALL = STRUCTURE_REGISTRY.register("hobbit_hole_small", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_HOLE_LARGE = STRUCTURE_REGISTRY.register("hobbit_hole_large", () -> new HobbitHoleLargeStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> HOBBIT_HOUSE = STRUCTURE_REGISTRY.register("hobbit_house", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> HOBBIT_PICNIC_BLANKET = STRUCTURE_REGISTRY.register("hobbit_picnic_blanket", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> HOBBIT_PICNIC_TABLE = STRUCTURE_REGISTRY.register("hobbit_picnic_table", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> HOBBIT_TAVERN_SMALL = STRUCTURE_REGISTRY.register("hobbit_tavern_small", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_TAVERN_LARGE = STRUCTURE_REGISTRY.register("hobbit_tavern_large", () -> new HobbitTavernLargeStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> HOBBIT_WINDMILL = STRUCTURE_REGISTRY.register("hobbit_windmill", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> RANGER_CAMP = STRUCTURE_REGISTRY.register("ranger_camp", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> RANGER_ENCAMPMENT = STRUCTURE_REGISTRY.register("ranger_encampment", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> RANGER_FORT = STRUCTURE_REGISTRY.register("ranger_fort", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> RANGER_HIDEOUT = STRUCTURE_REGISTRY.register("ranger_hideout", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<SinglePoolConfig>> RANGER_WATCHTOWER = STRUCTURE_REGISTRY.register("ranger_watchtower", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<WeightedStructureConfig>> RANGER_HOUSE_HIDEOUT = STRUCTURE_REGISTRY.register("ranger_house_hideout", () -> new WeightedPoolStructure());
    @Deprecated
    /**
     * Combined with RANGER_HOUSE_HIDEOUT and removed from structure spawns map, kept so pre-existing chunks do not experience crashes
     * */
    public static final RegistryObject<Structure<SinglePoolConfig>> RANGER_HOUSE_RUIN = STRUCTURE_REGISTRY.register("ranger_house_ruin", () -> new SinglePoolStructure());
    public static final RegistryObject<Structure<WeightedStructureConfig>> ARNOR_RUINS = STRUCTURE_REGISTRY.register("arnor_ruins", () -> new WeightedPoolStructure());

	public static void setupStructures() {
		// For StructureSeparationSettings, the first value is the average distance in chunks between spawn attempts (spacing)
		// the second value it minimum distance in chunks between spawn attempts (separation) (this must always be less than the first!
		// Setting spacing/separation to be 1/0, you can then have the structure spawn only in certain chunks in Structure.isFeatureChunk()
		// Setting spacing/separation to be 2/1, you can then have the structure spawn in every chunk
		register(HOBBIT_BARN.get(), true,              32, 12, 795386759);
		register(HOBBIT_HOLE_SMALL.get(), true,        24, 10, 212561518);
		register(HOBBIT_HOLE_LARGE.get(), false,       40, 25, 445436264);
		register(HOBBIT_HOUSE.get(), true,             24, 5,  764008750);
		register(HOBBIT_PICNIC_BLANKET.get(), false,   14, 8,  639354136);
		register(HOBBIT_PICNIC_TABLE.get(), true,      14, 8,  852604573);
		register(HOBBIT_TAVERN_SMALL.get(), true,      25, 15, 489984587);
		register(HOBBIT_TAVERN_LARGE.get(), false,     43, 30, 230050245);
		register(HOBBIT_WINDMILL.get(), true,          40, 20, 965983479);
		register(RANGER_CAMP.get(), true,              42, 25, 663198446);
		register(RANGER_ENCAMPMENT.get(), true,        50, 38, 232963172);
		register(RANGER_FORT.get(), true,              56, 36, 213435145);
		register(RANGER_HIDEOUT.get(), true,           44, 32, 752122251);
		register(RANGER_WATCHTOWER.get(), true,        42, 31, 002406043);
		register(RANGER_HOUSE_HIDEOUT.get(), true,     50, 35, 358719680);
		register(RANGER_HOUSE_RUIN.get(), true,        32, 18, 340638920);
		register(ARNOR_RUINS.get(), true,              29, 18, 365876386);
	}
	
	public static void register(Structure<? extends IFeatureConfig> structure, boolean editLand, int spacing, int seperation, int salt) {
		if(spacing-seperation<= 0) {
			throw new IllegalArgumentException("Structure SeperationSettings setup detected an invalid configuration where seperation>=spacing: "+seperation+">="+spacing);
		}
		StructureSeparationSettings SSsettings = new StructureSeparationSettings(spacing, seperation, salt);
		/*
         * We need to add our structures into the map in Structure class
         * alongside vanilla structures or else it will cause errors.
         *
         * If the registration is setup properly for the structure, getRegistryName() should never return null.
         */
		Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

		if (editLand) {
			/*
	         * Whether surrounding land will be modified automatically to conform to the bottom of the structure.
	         * Basically, it adds land at the base of the structure like it does for Villages and Outposts.
	         * Doesn't work well on structure that have pieces stacked vertically or change in heights.
	         *
	         * Note: The air space this method will create will be filled with water if the structure is below SeaLevel.
	         * This means this is best for structure above SeaLevel so keep that in mind.
	         */
			Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
		}
		/*
         * This is the map that holds the default spacing of all structures.
         * Always add structures to here so that other Mods can utilize it if needed.
         *
         * However, while it does propagate the spacing to some correct dimensions from this map,
         * it seems it doesn't always work for code made dimensions as they read from this list beforehand.
         *
         * Instead, use the WorldEvent.Load event to add the structure spacing from this 
         * list into that dimension or to do dimension blacklisting properly.
         */
		DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, SSsettings).build();
		
		/*
         * There are very few Mods that relies on seeing your structure in the noise settings registry before the world is made.
         *
         * Some Mods add their spacings to DimensionSettings.BUILTIN_OVERWORLD instead of the NOISE_GENERATOR_SETTINGS loop below but
         * that field only applies for the default overworld and won't add to other WorldTypes or dimensions (like amplified or Nether).
         * So yeah, don't do DimensionSettings.BUILTIN_OVERWORLD. Use the NOISE_GENERATOR_SETTINGS loop below instead if you must.
         */
		WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(structureSettings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = structureSettings.getValue().structureSettings().structureConfig();
            /*
             * Pre-caution in case a Mod makes the structure map immutable like Datapacks do.
             * Take no chances myself. We never know what another mods does...
             */
            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, SSsettings);
                structureSettings.getValue().structureSettings().structureConfig = tempMap;

            } else {
                structureMap.put(structure, SSsettings);
            }
        });
	}
}
