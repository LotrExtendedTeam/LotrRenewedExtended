package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw.ExtendedSingleJigsawPiece;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedSinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedWeightedStructureConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.ExtendedSinglePoolStructure;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.ExtendedWeightedPoolStructure;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.fixed.*;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.overrides.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Manages the registration and configuration of custom extended structures for lotrextended.
 * <p>
 * This class registers various structures with Forge's deferred registry system and sets up their spawning parameters.
 * It also provides utilities for registering custom jigsaw piece serializers and adding structure separation settings to vanilla Minecraft's world generation.
 * </p>
 */
public class ExtendedStructuresV5 {
	/**
	 * Jigsaw deserializer for extended single jigsaw pieces used in custom structure pools.
	 */
	public static final IJigsawDeserializer<ExtendedSingleJigsawPiece> SINGLE_EXTENDED = registerJigsaw("extended_single_pool_element", ExtendedSingleJigsawPiece.CODEC);
	/**
	 * Deferred registry for registering custom structures in the Forge registry.
	 */
	public static final DeferredRegister<Structure<?>> STRUCTURE_REGISTRY = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, LotrExtendedMod.MODID);

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> HOBBIT_BARN = STRUCTURE_REGISTRY.register("hobbit_barn", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.HOBBIT));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> HOBBIT_HOLE_SMALL = STRUCTURE_REGISTRY.register("hobbit_hole_small", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.HOBBIT));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> HOBBIT_HOLE_LARGE = STRUCTURE_REGISTRY.register("hobbit_hole_large", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.HOBBIT));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> HOBBIT_HOUSE = STRUCTURE_REGISTRY.register("hobbit_house", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.HOBBIT));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> HOBBIT_PICNIC_BLANKET = STRUCTURE_REGISTRY.register("hobbit_picnic_blanket", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.HOBBIT));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> HOBBIT_PICNIC_TABLE = STRUCTURE_REGISTRY.register("hobbit_picnic_table", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.HOBBIT));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> HOBBIT_TAVERN_SMALL = STRUCTURE_REGISTRY.register("hobbit_tavern_small", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.HOBBIT));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> HOBBIT_TAVERN_LARGE = STRUCTURE_REGISTRY.register("hobbit_tavern_large", () -> new ExtendedCheckAreaChunkStructure(ExtendedStructureType.HOBBIT));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> HOBBIT_WINDMILL = STRUCTURE_REGISTRY.register("hobbit_windmill", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.HOBBIT));

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> RANGER_CAMP = STRUCTURE_REGISTRY.register("ranger_camp", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.RANGER_OPTIONAL_TOWER_HILLS));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> RANGER_ENCAMPMENT = STRUCTURE_REGISTRY.register("ranger_encampment", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.RANGER));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> RANGER_FORT = STRUCTURE_REGISTRY.register("ranger_fort", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.RANGER));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> RANGER_HIDEOUT = STRUCTURE_REGISTRY.register("ranger_hideout", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.RANGER));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> RANGER_WATCHTOWER = STRUCTURE_REGISTRY.register("ranger_watchtower", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.RANGER_OPTIONAL_TOWER_HILLS));
	public static final RegistryObject<Structure<ExtendedWeightedStructureConfig>> RANGER_HOUSE_HIDEOUT = STRUCTURE_REGISTRY.register("ranger_house_hideout", () -> new ExtendedWeightedPoolStructure(ExtendedStructureType.RANGER));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> RANGER_HOUSE_RUIN = STRUCTURE_REGISTRY.register("ranger_house_ruin", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.RANGER));
	public static final RegistryObject<Structure<ExtendedWeightedStructureConfig>> ARNOR_RUINS = STRUCTURE_REGISTRY.register("arnor_ruins", () -> new ExtendedWeightedPoolStructure(ExtendedStructureType.RUIN));

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> GUNDABAD_CAMP = STRUCTURE_REGISTRY.register("gundabad_camp", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.GUNDABAD_ORC));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> GUNDABAD_ENCAMPMENT = STRUCTURE_REGISTRY.register("gundabad_encampment", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.GUNDABAD_ORC));

	public static final RegistryObject<Structure<ExtendedWeightedStructureConfig>> BARROW_DOWN_RUINS = STRUCTURE_REGISTRY.register("barrow_down_ruins", () -> new ExtendedWeightedPoolStructure(ExtendedStructureType.RUIN));
	public static final RegistryObject<Structure<ExtendedWeightedStructureConfig>> BARROW_DOWN_BARROWS = STRUCTURE_REGISTRY.register("barrow_down_barrows", () -> new ExtendedWeightedPoolStructure(ExtendedStructureType.BARROW));

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> BREE_HAMLET = STRUCTURE_REGISTRY.register("bree_hamlet", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.BREE_HAMLET));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> BREE_VILLAGE = STRUCTURE_REGISTRY.register("bree_village", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.BREE_VILLAGE));

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> BLUE_MOUNTAIN_SMITHY = STRUCTURE_REGISTRY.register("blue_mountain_smithy", () -> new ExtendedCheckAreaChunkStructure(ExtendedStructureType.BLUE_MOUNTAINS));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> BLUE_MOUNTAIN_STRONGHOLD = STRUCTURE_REGISTRY.register("blue_mountain_stronghold", () -> new ExtendedCheckAreaCrossStructure(ExtendedStructureType.BLUE_MOUNTAINS));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> BLUE_MOUNTAIN_MINE = STRUCTURE_REGISTRY.register("blue_mountain_mine", () -> new ExtendedCheckAreaChunkStructure(ExtendedStructureType.BLUE_MOUNTAINS));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> BLUE_MOUNTAIN_SETTLEMENT = STRUCTURE_REGISTRY.register("blue_mountain_settlement", () -> new ExtendedCheckAreaCrossStructure(ExtendedStructureType.BLUE_MOUNTAINS));

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> STONE_OF_HAPLESS = STRUCTURE_REGISTRY.register("stone_of_hapless", () -> new ExtendedStructureStoneOfHapless(ExtendedStructureType.LORE));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> THE_LAST_HOMELY_HOUSE = STRUCTURE_REGISTRY.register("last_homely_house", () -> new ExtendedStructureLastHomelyHouse(ExtendedStructureType.LORE));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> TOWER_HILLS_SOUTH_TOWER = STRUCTURE_REGISTRY.register("isilostirion", () -> new ExtendedStructureIsilostirion(ExtendedStructureType.LORE));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> TOWER_HILLS_MIDDLE_TOWER = STRUCTURE_REGISTRY.register("elostirion", () -> new ExtendedStructureElostirion(ExtendedStructureType.LORE));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> TOWER_HILLS_NORTH_TOWER = STRUCTURE_REGISTRY.register("anarostirion", () -> new ExtendedStructureAnarostirion(ExtendedStructureType.LORE));

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> RIVENDELL_STRUCTURE = STRUCTURE_REGISTRY.register("rivendell_structures", () -> new ExtendedCheckAreaChunkFlatStructure(ExtendedStructureType.RIVENDELL));

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> EREGION_RUINS_STRUCTURE = STRUCTURE_REGISTRY.register("eregion_ruins", () -> new ExtendedCheckAreaChunkFlatStructure(ExtendedStructureType.EREGION_RUIN));

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> TOWER_HILLS_TOWER = STRUCTURE_REGISTRY.register("tower_hills_tower", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.TOWER_HILLS));

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> LINDON_VILLAGE = STRUCTURE_REGISTRY.register("lindon_village", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.LINDON));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> LINDON_TOWER = STRUCTURE_REGISTRY.register("lindon_tower", () -> new ExtendedCheckAreaChunkStructure(ExtendedStructureType.LINDON));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> LINDON_HALL = STRUCTURE_REGISTRY.register("lindon_hall", () -> new ExtendedSinglePoolStructure(ExtendedStructureType.LINDON));

	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> ISENGARD_GATEHOUSE = STRUCTURE_REGISTRY.register("isengard_gatehouse", () -> new ExtendedStructureIsengardGatehouse(ExtendedStructureType.ISENGARD));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> ORTHANC = STRUCTURE_REGISTRY.register("orthanc", () -> new ExtendedStructureOrthanc(ExtendedStructureType.ISENGARD));
	public static final RegistryObject<Structure<ExtendedSinglePoolConfig>> ISENGARD_CAMP = STRUCTURE_REGISTRY.register("isengard_camp", () -> new ExtendedIsengardStructure(ExtendedStructureType.ISENGARD));

	/**
	 * Initializes and registers all custom structures with their generation parameters.
	 * <p>
	 * This method must be called during mod setup to ensure proper structure registration
	 * and spacing configuration.
	 * </p>
	 */
	public static void setupStructures() {
		// For StructureSeparationSettings, the first value is the average distance in chunks between spawn attempts (spacing)
		// the second value it minimum distance in chunks between spawn attempts (separation) (this must always be less than the first!
		// Setting spacing/separation to be 1/0, you can then have the structure spawn only in certain chunks in Structure.isFeatureChunk()
		// Setting spacing/separation to be 2/1, you can then have the structure spawn in every chunk
		register(HOBBIT_BARN.get(), true,              32, 12, 795386759);
		register(HOBBIT_HOLE_SMALL.get(), true,        24, 9,  212561518);
		register(HOBBIT_HOLE_LARGE.get(), false,       40, 25, 445436264);
		register(HOBBIT_HOUSE.get(), true,             24, 5,  764008750);
		register(HOBBIT_PICNIC_BLANKET.get(), false,   20, 8,  639354136);
		register(HOBBIT_PICNIC_TABLE.get(), true,      30, 10, 852604573);
		register(HOBBIT_TAVERN_SMALL.get(), true,      30, 15, 489984587);
		register(HOBBIT_TAVERN_LARGE.get(), false,     50, 40, 230050245);
		register(HOBBIT_WINDMILL.get(), true,          40, 20, 965983479);
		register(RANGER_CAMP.get(), true,              55, 35, 6678446);
		register(RANGER_ENCAMPMENT.get(), true,        60, 40, 232963172);
		register(RANGER_FORT.get(), true,              56, 36, 213435145);
		register(RANGER_HIDEOUT.get(), true,           50, 38, 75222251);
		register(RANGER_WATCHTOWER.get(), true,        60, 38, 00246043);
		register(RANGER_HOUSE_HIDEOUT.get(), true,     50, 35, 358719680);
		register(RANGER_HOUSE_RUIN.get(), true,        52, 26, 34063920);
		register(ARNOR_RUINS.get(), true,              29, 18, 365876386);
		register(GUNDABAD_CAMP.get(), true,            45, 25, 967990477);
		register(GUNDABAD_ENCAMPMENT.get(), true,      50, 35, 43757456);
		register(BARROW_DOWN_RUINS.get(), true,        7,  3,  45574576);
		register(BARROW_DOWN_BARROWS.get(), true,      7,  3,  57854769);
		register(BREE_HAMLET.get(), true,              40, 25, 57854769);
		register(BREE_VILLAGE.get(), true,             60, 45, 78547894);
		register(BLUE_MOUNTAIN_SMITHY.get(), false,    27, 12, 589758935);
		register(BLUE_MOUNTAIN_STRONGHOLD.get(), false,35, 15, 889329084);
		register(BLUE_MOUNTAIN_MINE.get(), false,      30, 15, 17277298);
		register(BLUE_MOUNTAIN_SETTLEMENT.get(), false,45, 25, 167568658);
		register(STONE_OF_HAPLESS.get(), false,        1,  0,  0);
		register(RIVENDELL_STRUCTURE.get(), false,     20, 8,  534646346);
		register(EREGION_RUINS_STRUCTURE.get(), true,  25, 10, 875649477);
		register(THE_LAST_HOMELY_HOUSE.get(), true,    1,  0,  0);
		register(TOWER_HILLS_SOUTH_TOWER.get(), false, 1,  0,  0);
		register(TOWER_HILLS_MIDDLE_TOWER.get(), false,1,  0,  0);
		register(TOWER_HILLS_NORTH_TOWER.get(), false, 1,  0,  0);
		register(TOWER_HILLS_TOWER.get(), false,       30, 20, 467385464);
		register(LINDON_VILLAGE.get(), true,           70, 55, 584474884);
		register(LINDON_TOWER.get(), true,             75, 50, 848856948);
		register(LINDON_HALL.get(), true,              80, 65, 356254478);
		register(ISENGARD_GATEHOUSE.get(), false,      1,  0,  0);
		register(ORTHANC.get(), false,                 1,  0,  0);
		register(ORTHANC.get(), false,                 15,  10,  848458030);
	}

	/**
	 * Registers a custom jigsaw piece deserializer codec under the mod's namespace.
	 *
	 * @param <P>    the type of JigsawPiece
	 * @param pName  the name of the jigsaw type to register
	 * @param pCodec the codec used to serialize and deserialize the jigsaw piece
	 * @return the registered {@link IJigsawDeserializer} instance
	 */
	public static <P extends JigsawPiece> IJigsawDeserializer<P> registerJigsaw(String pName, Codec<P> pCodec) {
		return Registry.register(Registry.STRUCTURE_POOL_ELEMENT, new ResourceLocation(LotrExtendedMod.MODID, pName), () -> { return pCodec; });
	}
	/**
	 * Registers a structure with its separation and spacing settings for world generation.
	 * <p>
	 * Also adds the structure to various internal registries to enable proper generation and
	 * integration with Minecraft's dimension and noise generator settings.
	 * </p>
	 *
	 * @param structure the structure to register
	 * @param editLand  whether surrounding terrain should be automatically modified
	 * @param spacing   average distance in chunks between spawn attempts (must be greater than separation)
	 * @param seperation minimum distance in chunks between spawn attempts
	 * @param salt      random seed salt used for structure placement randomness
	 * @throws IllegalArgumentException if separation is greater than or equal to spacing
	 */
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
