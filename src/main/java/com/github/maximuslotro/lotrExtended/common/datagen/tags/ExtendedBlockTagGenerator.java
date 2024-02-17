package com.github.maximuslotro.lotrExtended.common.datagen.tags;

import java.util.Map.Entry;

import com.github.maximuslotro.lotrExtended.common.init.ExtendedTags;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBlockTagGenerator extends BlockTagsProvider {
	
    public static final ITag.INamedTag<Block> PILLARS = BlockTags.bind("lotr:pillars");
    public static final ITag.INamedTag<Block> SLABS = BlockTags.bind(BlockTags.SLABS.getName().toString());
    public static final ITag.INamedTag<Block> WOODEN_SLABS = BlockTags.bind(BlockTags.WOODEN_SLABS.getName().toString());
    public static final ITag.INamedTag<Block> STAIRS = BlockTags.bind(BlockTags.STAIRS.getName().toString());
    public static final ITag.INamedTag<Block> WOODEN_STAIRS = BlockTags.bind(BlockTags.WOODEN_STAIRS.getName().toString());
    public static final ITag.INamedTag<Block> WALLS = BlockTags.bind(BlockTags.WALLS.getName().toString());

	public ExtendedBlockTagGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	@Override
    protected void addTags() {
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(block.getValue().get() instanceof LOTRPillarBlock) {
				tag(PILLARS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof AxialSlabBlock) {
				tag(SLABS).add(block.getValue().get());
				if(block.getValue().get().properties.material.isFlammable()) {
					tag(WOODEN_SLABS).add(block.getValue().get());
				}
			}
			if(block.getValue().get() instanceof LOTRStairsBlock) {
				tag(STAIRS).add(block.getValue().get());
				if(block.getValue().get().properties.material.isFlammable()) {
					tag(WOODEN_STAIRS).add(block.getValue().get());
				}
			}
			if(block.getValue().get() instanceof LOTRWallBlock) {
				tag(WALLS).add(block.getValue().get());
			}
			if(block.getValue().get() instanceof LOTRTorchBlock) {
				tag(BlockTags.WALL_POST_OVERRIDE).add(block.getValue().get());
			}
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

        tag(ExtendedTags.Blocks.COPPER_ORE).add(LOTRBlocks.COPPER_ORE.get());
        tag(ExtendedTags.Blocks.NITER_ORE).add(LOTRBlocks.NITER_ORE.get());
        tag(ExtendedTags.Blocks.SALT_ORE).add(LOTRBlocks.SALT_ORE.get());
        tag(ExtendedTags.Blocks.SILVER_ORE).add(LOTRBlocks.SILVER_ORE.get());
        tag(ExtendedTags.Blocks.SULFUR_ORE).add(LOTRBlocks.SULFUR_ORE.get());
        tag(ExtendedTags.Blocks.TIN_ORE).add(LOTRBlocks.TIN_ORE.get());

		tag(ExtendedTags.Blocks.TOPAZ_ORE).add(ExtendedBlocks.AMBER_ORE.get());
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
	}

	@Override
	public String getName() {
		return "Extended Renewed Block Tags Generator";
	}
}
