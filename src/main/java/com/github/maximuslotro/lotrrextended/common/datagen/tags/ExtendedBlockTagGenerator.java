package com.github.maximuslotro.lotrrextended.common.datagen.tags;

import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedChainBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedThreeTallPillarBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedVineBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedFullModelBedBlock;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.BranchBlock;
import lotr.common.block.ExtendedLanternBlock;
import lotr.common.block.LOTRDoorBlock;
import lotr.common.block.LOTRFenceBlock;
import lotr.common.block.LOTRFenceGateBlock;
import lotr.common.block.LOTRLeavesBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRPlanksBlock;
import lotr.common.block.LOTRPottedPlantBlock;
import lotr.common.block.LOTRSaplingBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStandingSignBlock;
import lotr.common.block.LOTRStrippedLogBlock;
import lotr.common.block.LOTRStrippedWoodBlock;
import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRTrapdoorBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.block.LOTRWallSignBlock;
import lotr.common.block.LOTRWoodButtonBlock;
import lotr.common.block.LOTRWoodPressurePlateBlock;
import lotr.common.block.StrippableLogBlock;
import lotr.common.block.StrippableWoodBlock;
import lotr.common.block.WoodBeamBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBlockTagGenerator extends ExtendedTagsProvider<Block> {
	//LOTR
	public static final ITag.INamedTag<Block> PILLARS = BlockTags.bind("lotr:pillars");
	public static final ITag.INamedTag<Block> WOODEN_FENCES = BlockTags.bind("lotr:wooden_fences");
	public static final ITag.INamedTag<Block> WOODEN_FENCE_GATES = BlockTags.bind("lotr:wooden_fence_gates");
	public static final ITag.INamedTag<Block> LOG_SLABS = BlockTags.bind("lotr:log_slabs");
	public static final ITag.INamedTag<Block> LOG_STAIRS = BlockTags.bind("lotr:log_stairs");
	public static final ITag.INamedTag<Block> WOODEN_BEAMS = BlockTags.bind("lotr:wooden_beams");
	public static final ITag.INamedTag<Block> WOODEN_BEAM_SLABS = BlockTags.bind("lotr:wooden_beam_slabs");
	public static final ITag.INamedTag<Block> BRANCHES = BlockTags.bind("lotr:branches");
	public static final ITag.INamedTag<Block> CHARRABLE_LOGS = BlockTags.bind("lotr:charrable_logs");
	public static final ITag.INamedTag<Block> CHARRABLE_WOODS = BlockTags.bind("lotr:charrable_woods");
	public static final ITag.INamedTag<Block> CHARRABLE_STRIPPED_LOGS = BlockTags.bind("lotr:charrable_stripped_logs");
	public static final ITag.INamedTag<Block> CHARRABLE_STRIPPED_WOODS = BlockTags.bind("lotr:charrable_stripped_woods");
	//MINECRAFT
	public static final ITag.INamedTag<Block> BEDS = BlockTags.bind(BlockTags.BEDS.getName().toString());
	public static final ITag.INamedTag<Block> SLABS = BlockTags.bind(BlockTags.SLABS.getName().toString());
	public static final ITag.INamedTag<Block> WOODEN_SLABS = BlockTags.bind(BlockTags.WOODEN_SLABS.getName().toString());
	public static final ITag.INamedTag<Block> STAIRS = BlockTags.bind(BlockTags.STAIRS.getName().toString());
	public static final ITag.INamedTag<Block> WOODEN_STAIRS = BlockTags.bind(BlockTags.WOODEN_STAIRS.getName().toString());
	public static final ITag.INamedTag<Block> WALLS = BlockTags.bind(BlockTags.WALLS.getName().toString());
	public static final ITag.INamedTag<Block> WOODEN_BUTTONS = BlockTags.bind(BlockTags.WOODEN_BUTTONS.getName().toString());
	public static final ITag.INamedTag<Block> WOODEN_TRAPDOORS = BlockTags.bind(BlockTags.WOODEN_TRAPDOORS.getName().toString());
	public static final ITag.INamedTag<Block> WOODEN_PRESSURE_PLATES = BlockTags.bind(BlockTags.WOODEN_PRESSURE_PLATES.getName().toString());
	public static final ITag.INamedTag<Block> WOODEN_DOORS = BlockTags.bind(BlockTags.WOODEN_DOORS.getName().toString());
	public static final ITag.INamedTag<Block> DOORS = BlockTags.bind(BlockTags.DOORS.getName().toString());
	public static final ITag.INamedTag<Block> TRAPDOORS = BlockTags.bind(BlockTags.TRAPDOORS.getName().toString());
	public static final ITag.INamedTag<Block> WALL_SIGNS = BlockTags.bind(BlockTags.WALL_SIGNS.getName().toString());
	public static final ITag.INamedTag<Block> STANDING_SIGNS = BlockTags.bind(BlockTags.STANDING_SIGNS.getName().toString());
	public static final ITag.INamedTag<Block> SAPLINGS = BlockTags.bind(BlockTags.SAPLINGS.getName().toString());
	public static final ITag.INamedTag<Block> PLANKS = BlockTags.bind(BlockTags.PLANKS.getName().toString());
	public static final ITag.INamedTag<Block> LOGS_THAT_BURN = BlockTags.bind(BlockTags.LOGS_THAT_BURN.getName().toString());
	public static final ITag.INamedTag<Block> LEAVES = BlockTags.bind(BlockTags.LEAVES.getName().toString());
	public static final ITag.INamedTag<Block> FLOWER_POTS = BlockTags.bind(BlockTags.FLOWER_POTS.getName().toString());
	public static final ITag.INamedTag<Block> CHESTS = BlockTags.bind(Tags.Blocks.CHESTS.getName().toString());
	public static final ITag.INamedTag<Block> BARRELS = BlockTags.bind(Tags.Blocks.BARRELS.getName().toString());
	public static final ITag.INamedTag<Block> BARRELS_WOODEN = BlockTags.bind(Tags.Blocks.BARRELS_WOODEN.getName().toString());

	@SuppressWarnings("deprecation")
	public ExtendedBlockTagGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, Registry.BLOCK, modid, existingFileHelper);
	}

	@Override
	protected void addTags() {
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(block.getValue().get() instanceof LOTRPillarBlock) {
				tag(PILLARS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof ExtendedThreeTallPillarBlock ) {
				tag(ExtendedTags.Blocks.FIXED_SIZE_PILLARS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRPottedPlantBlock) {
				tag(FLOWER_POTS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof ExtendedBedBlock||block.getValue().get() instanceof ExtendedFullModelBedBlock) {
				tag(BEDS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRPlanksBlock) {
				tag(PLANKS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRLeavesBlock) {
				tag(LEAVES).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRSaplingBlock) {
				tag(SAPLINGS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRStandingSignBlock) {
				tag(STANDING_SIGNS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRWallSignBlock) {
				tag(WALL_SIGNS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRWoodButtonBlock) {
				tag(WOODEN_BUTTONS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRTrapdoorBlock) {
				tag(WOODEN_TRAPDOORS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof TrapDoorBlock && !(block.getValue().get() instanceof LOTRTrapdoorBlock)) {
				tag(TRAPDOORS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRWoodPressurePlateBlock) {
				tag(WOODEN_PRESSURE_PLATES).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRDoorBlock) {
				tag(WOODEN_DOORS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof DoorBlock && !(block.getValue().get() instanceof LOTRDoorBlock)) {
				tag(DOORS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof AxialSlabBlock) {
				tag(SLABS).add(block.getValue().get());
				if(block.getValue().get().properties.material.isFlammable()) {
					tag(WOODEN_SLABS).add(block.getValue().get());
				}
				if(block.getKey().contains("_log_slab")||block.getKey().contains("_wood_slab")) {
					tag(LOG_SLABS).add(block.getValue().get());
				}
				if(block.getKey().contains("_beam_slab")) {
					tag(WOODEN_BEAM_SLABS).add(block.getValue().get());
				}
			}
			if(block.getValue().get() instanceof LOTRStairsBlock) {
				tag(STAIRS).add(block.getValue().get());
				if(block.getValue().get().properties.material.isFlammable()) {
					tag(WOODEN_STAIRS).add(block.getValue().get());
				}
				if(block.getKey().contains("_wood_stairs")) {
					tag(LOG_STAIRS).add(block.getValue().get());
				}
			}
			if(block.getValue().get() instanceof LOTRWallBlock) {
				tag(WALLS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRFenceGateBlock) {
				if(block.getValue().get().properties.material.isFlammable()) {
					tag(WOODEN_FENCE_GATES).add(block.getValue().get());
				}
			}
			if(block.getValue().get() instanceof LOTRFenceBlock) {
				if(block.getValue().get().properties.material.isFlammable()) {
					tag(WOODEN_FENCES).add(block.getValue().get());
				}
			}
			if(block.getValue().get() instanceof LOTRTorchBlock) {
				tag(BlockTags.WALL_POST_OVERRIDE).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof ExtendedLanternBlock) {
				tag(BlockTags.WALL_POST_OVERRIDE).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof WoodBeamBlock) {
				tag(WOODEN_BEAMS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof BranchBlock) {
				tag(BRANCHES).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof ExtendedVineBlock) {
				tag(ExtendedTags.Blocks.SHEARABLE_BLOCKS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof StrippableLogBlock) {
				tag(CHARRABLE_LOGS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof StrippableWoodBlock) {
				tag(CHARRABLE_WOODS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRStrippedLogBlock) {
				tag(CHARRABLE_STRIPPED_LOGS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRStrippedWoodBlock) {
				tag(CHARRABLE_STRIPPED_WOODS).add(block.getValue().get());
			}
			if(block.getKey().contains("packed_fur")||block.getKey().contains("fur_bundle")||block.getKey().contains("fur_carpet")) {
				tag(ExtendedTags.Blocks.FUR_BLOCKS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof ExtendedChainBlock) {
				tag(ExtendedTags.Blocks.CHAINS).add(block.getValue().get());
			}
		}

		tag(BlockTags.BASE_STONE_OVERWORLD)//.add(ExtendedBlocks.GNEISS_ROCK.get()).add(ExtendedBlocks.LIMESTONE_ROCK.get()).add(ExtendedBlocks.SLATE_ROCK.get())
		.add(LOTRBlocks.BLUE_ROCK.get()).add(LOTRBlocks.GONDOR_ROCK.get()).add(LOTRBlocks.MORDOR_ROCK.get())
		.add(LOTRBlocks.RED_ROCK.get()).add(LOTRBlocks.ROHAN_ROCK.get());

		tag(ExtendedTags.Blocks.MOSS_REPLACEABLE).addTag(BlockTags.BASE_STONE_OVERWORLD).add(Blocks.VINE).add(ExtendedBlocks.MORDOR_MOSS_VINES.get())
		.add(ExtendedBlocks.WILLOW_VINES.get()).add(ExtendedBlocks.BARREN_DIRT.get()).addTag(Tags.Blocks.DIRT).add(ExtendedBlocks.MUD.get())
		.add(ExtendedBlocks.MUD_GRASS.get());

		tag(Tags.Blocks.DIRT).add(ExtendedBlocks.MOSS_BLOCK.get()).add(ExtendedBlocks.MUD.get()).add(ExtendedBlocks.MUD_GRASS.get())
		.add(ExtendedBlocks.BARREN_DIRT.get()).addTag(ExtendedTags.Blocks.REMAINS_ORE);

		tag(LOGS_THAT_BURN).addTag(ExtendedTags.Blocks.CHESTNUT_LOGS).addTag(ExtendedTags.Blocks.BANANA_LOGS).addTag(ExtendedTags.Blocks.PLUM_LOGS)
		.addTag(ExtendedTags.Blocks.WILLOW_LOGS);

		tag(BlockTags.ENDERMAN_HOLDABLE).add(ExtendedBlocks.BARREN_DIRT.get()).add(ExtendedBlocks.MOSS_BLOCK.get()).add(ExtendedBlocks.MUD.get())
		.add(ExtendedBlocks.MUD_GRASS.get());

		tag(BlockTags.BAMBOO_PLANTABLE_ON).add(ExtendedBlocks.MOSS_BLOCK.get()).add(ExtendedBlocks.MUD.get()).add(ExtendedBlocks.MUD_GRASS.get())
		.addTag(ExtendedTags.Blocks.REMAINS_ORE);

		for(Entry<ResourceLocation, Supplier<? extends Block>> blo : ((FlowerPotBlock)Blocks.FLOWER_POT).getFullPotsView().entrySet()) {
			tag(ExtendedTags.Blocks.LARGE_FLOWER_POT_COMPATABLE).add(blo.getValue().get());
		}

		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_GALVORN).add(ExtendedBlocks.GALVORN_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_BLACK_URUK_STEEL).add(ExtendedBlocks.BLACK_URUK_STEEL_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_GILDED_IRON).add(ExtendedBlocks.GILDED_IRON_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_MORGUL_STEEL).add(ExtendedBlocks.MORGUL_STEEL_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_DWARVEN_STEEL).add(LOTRBlocks.DWARVEN_STEEL_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_ELVEN_STEEL).add(LOTRBlocks.ELVEN_STEEL_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_MITHRIL).add(LOTRBlocks.MITHRIL_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_URUK_STEEL).add(LOTRBlocks.URUK_STEEL_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_TOPAZ).add(ExtendedBlocks.TOPAZ_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_AMETHYST).add(ExtendedBlocks.AMETHYST_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_SAPPHIRE).add(ExtendedBlocks.SAPPHIRE_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_RUBY).add(ExtendedBlocks.RUBY_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_AMBER).add(ExtendedBlocks.AMBER_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_DIAMOND).add(ExtendedBlocks.DIAMOND_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_OPAL).add(ExtendedBlocks.OPAL_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_EMERALD).add(ExtendedBlocks.EMERALD_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_PEARL).add(ExtendedBlocks.PEARL_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_CORAL).add(ExtendedBlocks.CORAL_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_DURNOR).add(LOTRBlocks.DURNOR_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_EDHELVIR).add(LOTRBlocks.EDHELVIR_BLOCK.get());
		tag(ExtendedTags.Blocks.STORAGE_BLOCKS_GULDURIL).add(LOTRBlocks.GULDURIL_BLOCK.get());

		tag(BlockTags.BEACON_BASE_BLOCKS).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_GALVORN)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_BLACK_URUK_STEEL).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_GILDED_IRON)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_MORGUL_STEEL).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_TOPAZ)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_AMETHYST).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_SAPPHIRE)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_RUBY) .addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_AMBER)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_DIAMOND).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_OPAL)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_EMERALD).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_PEARL)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_CORAL);

		tag(Tags.Blocks.STORAGE_BLOCKS)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_GALVORN).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_BLACK_URUK_STEEL)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_GILDED_IRON).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_MORGUL_STEEL)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_DWARVEN_STEEL).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_ELVEN_STEEL)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_MITHRIL).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_URUK_STEEL)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_TOPAZ).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_AMETHYST)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_SAPPHIRE).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_RUBY)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_AMBER).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_DIAMOND)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_OPAL).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_EMERALD)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_PEARL).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_CORAL)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_DURNOR).addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_EDHELVIR)
		.addTag(ExtendedTags.Blocks.STORAGE_BLOCKS_GULDURIL);

		tag(BlockTags.FIRE).add(ExtendedBlocks.GREEN_FIRE_BLOCK.get());
		tag(ExtendedTags.Blocks.GREEN_FIRE_BASE_BLOCKS).add(ExtendedBlocks.GREEN_FLAME_HEARTH_BLOCK.get());

		tag(BlockTags.CLIMBABLE).add(ExtendedBlocks.ROPE.get()).add(ExtendedBlocks.MORDOR_MOSS_VINES.get()).add(ExtendedBlocks.WILLOW_VINES.get());

		tag(ExtendedTags.Blocks.CHAINS).add(Blocks.CHAIN);


		tag(ExtendedTags.Blocks.SHEARABLE_BLOCKS).addTag(ExtendedTags.Blocks.FUR_BLOCKS);

		tag(ExtendedTags.Blocks.COPPER_ORE).add(LOTRBlocks.COPPER_ORE.get());
		tag(ExtendedTags.Blocks.NITER_ORE).add(LOTRBlocks.NITER_ORE.get());
		tag(ExtendedTags.Blocks.SALT_ORE).add(LOTRBlocks.SALT_ORE.get());
		tag(ExtendedTags.Blocks.SILVER_ORE).add(LOTRBlocks.SILVER_ORE.get());
		tag(ExtendedTags.Blocks.SULFUR_ORE).add(LOTRBlocks.SULFUR_ORE.get());
		tag(ExtendedTags.Blocks.TIN_ORE).add(LOTRBlocks.TIN_ORE.get());
		tag(ExtendedTags.Blocks.REMAINS_ORE).add(ExtendedBlocks.REMAINS_DWARF.get()).add(ExtendedBlocks.REMAINS_ELF.get())
		.add(ExtendedBlocks.REMAINS_MAN.get()).add(ExtendedBlocks.REMAINS_ORC.get());

		tag(ExtendedTags.Blocks.TOPAZ_ORE).add(ExtendedBlocks.TOPAZ_ORE.get());
		tag(ExtendedTags.Blocks.AMETHYST_ORE).add(ExtendedBlocks.AMETHYST_ORE.get());
		tag(ExtendedTags.Blocks.SAPPHIRE_ORE).add(ExtendedBlocks.SAPPHIRE_ORE.get());
		tag(ExtendedTags.Blocks.RUBY_ORE).add(ExtendedBlocks.RUBY_ORE.get());
		tag(ExtendedTags.Blocks.AMBER_ORE).add(ExtendedBlocks.AMBER_ORE.get());
		tag(ExtendedTags.Blocks.DIAMOND_ORE).add(ExtendedBlocks.DIAMOND_ORE.get());
		tag(ExtendedTags.Blocks.OPAL_ORE).add(ExtendedBlocks.OPAL_ORE.get());
		tag(ExtendedTags.Blocks.EMERALD_ORE).add(ExtendedBlocks.EMERALD_ORE.get());
		tag(ExtendedTags.Blocks.DURNOR_ORE).add(LOTRBlocks.DURNOR_ORE.get());
		tag(ExtendedTags.Blocks.EDHELVIR_ORE).add(LOTRBlocks.EDHELVIR_ORE.get());
		tag(ExtendedTags.Blocks.GULDURIL_ORE).add(LOTRBlocks.GULDURIL_ORE_MORDOR.get()).add(LOTRBlocks.GULDURIL_ORE_STONE.get());
		tag(ExtendedTags.Blocks.GLOWSTONE_ORE).add(LOTRBlocks.GLOWSTONE_ORE.get());
		tag(ExtendedTags.Blocks.MITHRIL_ORE).add(LOTRBlocks.MITHRIL_ORE.get());
		tag(ExtendedTags.Blocks.MORGUL_IRON_ORE).add(LOTRBlocks.MORGUL_IRON_ORE_MORDOR.get()).add(LOTRBlocks.MORGUL_IRON_ORE_STONE.get());

		tag(Tags.Blocks.ORES).addTag(ExtendedTags.Blocks.TOPAZ_ORE)
		.addTag(ExtendedTags.Blocks.AMETHYST_ORE).addTag(ExtendedTags.Blocks.SAPPHIRE_ORE)
		.addTag(ExtendedTags.Blocks.RUBY_ORE).addTag(ExtendedTags.Blocks.AMBER_ORE)
		.addTag(ExtendedTags.Blocks.DIAMOND_ORE).addTag(ExtendedTags.Blocks.OPAL_ORE)
		.addTag(ExtendedTags.Blocks.EMERALD_ORE) .addTag(ExtendedTags.Blocks.DURNOR_ORE)
		.addTag(ExtendedTags.Blocks.EDHELVIR_ORE).addTag(ExtendedTags.Blocks.GULDURIL_ORE)
		.addTag(ExtendedTags.Blocks.GLOWSTONE_ORE).addTag(ExtendedTags.Blocks.MITHRIL_ORE)
		.addTag(ExtendedTags.Blocks.MORGUL_IRON_ORE).addTag(ExtendedTags.Blocks.COPPER_ORE)
		.addTag(ExtendedTags.Blocks.NITER_ORE).addTag(ExtendedTags.Blocks.SALT_ORE)
		.addTag(ExtendedTags.Blocks.SILVER_ORE).addTag(ExtendedTags.Blocks.SULFUR_ORE)
		.addTag(ExtendedTags.Blocks.TIN_ORE);

		tag(CHESTS).add(ExtendedBlocks.STONE_CHEST.get());
		tag(BARRELS).add(ExtendedBlocks.REED_BASKET.get()).add(ExtendedBlocks.ORC_BARREL.get());
		tag(BARRELS_WOODEN).add(ExtendedBlocks.REED_BASKET.get()).add(ExtendedBlocks.ORC_BARREL.get());

		tag(ExtendedTags.Blocks.CHESTNUT_LOGS).add(ExtendedBlocks.CHESTNUT_LOG.get()).add(ExtendedBlocks.STRIPPED_CHESTNUT_LOG.get())
		.add(ExtendedBlocks.CHESTNUT_WOOD.get()).add(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD.get());
		tag(ExtendedTags.Blocks.BANANA_LOGS).add(ExtendedBlocks.BANANA_LOG.get()).add(ExtendedBlocks.STRIPPED_BANANA_LOG.get())
		.add(ExtendedBlocks.BANANA_WOOD.get()).add(ExtendedBlocks.STRIPPED_BANANA_WOOD.get());
		tag(ExtendedTags.Blocks.PLUM_LOGS).add(ExtendedBlocks.PLUM_LOG.get()).add(ExtendedBlocks.STRIPPED_PLUM_LOG.get())
		.add(ExtendedBlocks.PLUM_WOOD.get()).add(ExtendedBlocks.STRIPPED_PLUM_WOOD.get());
		tag(ExtendedTags.Blocks.WILLOW_LOGS).add(ExtendedBlocks.WILLOW_LOG.get()).add(ExtendedBlocks.STRIPPED_WILLOW_LOG.get())
		.add(ExtendedBlocks.WILLOW_WOOD.get()).add(ExtendedBlocks.STRIPPED_WILLOW_WOOD.get());
		
		tag(ExtendedTags.Blocks.WOOL_BLOCKS).add(Blocks.BLACK_WOOL).add(Blocks.BLUE_WOOL).add(Blocks.BROWN_WOOL).add(Blocks.CYAN_WOOL).add(Blocks.GRAY_WOOL)
		.add(Blocks.GREEN_WOOL).add(Blocks.LIGHT_BLUE_WOOL).add(Blocks.LIGHT_GRAY_WOOL).add(Blocks.LIME_WOOL).add(Blocks.MAGENTA_WOOL).add(Blocks.ORANGE_WOOL)
		.add(Blocks.PINK_WOOL).add(Blocks.PURPLE_WOOL).add(Blocks.RED_WOOL).add(Blocks.WHITE_WOOL).add(Blocks.YELLOW_WOOL);
	}

	@Override
	public String getName() {
		return "Extended Renewed Block Tags Generator";
	}

	@Override
	protected Path getPath(ResourceLocation pId) {
		return this.generator.getOutputFolder().resolve("data/" + pId.getNamespace() + "/tags/blocks/" + pId.getPath() + ".json");
	}
}
