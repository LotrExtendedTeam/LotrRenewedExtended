package lotr.common.datagen;

import java.util.Map.Entry;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

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
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_GALVORN = BlockTags.bind("forge:storage_blocks/galvorn");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_BLACK_URUK_STEEL = BlockTags.bind("forge:storage_blocks/black_uruk_steel");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_GILDED_IRON = BlockTags.bind("forge:storage_blocks/gilded_iron");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_MORGUL_STEEL = BlockTags.bind("forge:storage_blocks/morgul_steel");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_DWARVEN_STEEL = BlockTags.bind("forge:storage_blocks/dwarven_steel");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_ELVEN_STEEL = BlockTags.bind("forge:storage_blocks/elven_steel");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_MITHRIL = BlockTags.bind("forge:storage_blocks/mithril");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_URUK_STEEL = BlockTags.bind("forge:storage_blocks/uruk_steel");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_TOPAZ = BlockTags.bind("forge:storage_blocks/lotr_topaz");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_AMETHYST = BlockTags.bind("forge:storage_blocks/lotr_amethyst");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_SAPPHIRE = BlockTags.bind("forge:storage_blocks/lotr_sapphire");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_RUBY = BlockTags.bind("forge:storage_blocks/lotr_ruby");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_AMBER = BlockTags.bind("forge:storage_blocks/lotr_amber");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_DIAMOND = BlockTags.bind("forge:storage_blocks/lotr_diamond");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_OPAL = BlockTags.bind("forge:storage_blocks/lotr_opal");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_EMERALD = BlockTags.bind("forge:storage_blocks/lotr_emerald");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_PEARL = BlockTags.bind("forge:storage_blocks/lotr_pearl");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_CORAL = BlockTags.bind("forge:storage_blocks/lotr_coral");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_DURNOR = BlockTags.bind("forge:storage_blocks/durnor");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_EDHELVIR = BlockTags.bind("forge:storage_blocks/edhelvir");
    public static final ITag.INamedTag<Block> STORAGE_BLOCKS_GULDURIL = BlockTags.bind("forge:storage_blocks/gulduril");

    public static final ITag.INamedTag<Block> COPPER_ORE = BlockTags.bind("forge:ores/copper");
    public static final ITag.INamedTag<Block> NITER_ORE = BlockTags.bind("forge:ores/niter");
    public static final ITag.INamedTag<Block> SALT_ORE = BlockTags.bind("forge:ores/salt");
    public static final ITag.INamedTag<Block> SILVER_ORE = BlockTags.bind("forge:ores/silver");
    public static final ITag.INamedTag<Block> SULFUR_ORE = BlockTags.bind("forge:ores/sulfur");
    public static final ITag.INamedTag<Block> TIN_ORE = BlockTags.bind("forge:ores/tin");

    public static final ITag.INamedTag<Block> GLOWSTONE_ORE = BlockTags.bind("forge:ores/glowstone");
    public static final ITag.INamedTag<Block> MITHRIL_ORE = BlockTags.bind("forge:ores/mithril");
    public static final ITag.INamedTag<Block> MORGUL_IRON_ORE = BlockTags.bind("forge:ores/morgul_iron");
    public static final ITag.INamedTag<Block> TOPAZ_ORE = BlockTags.bind("forge:ores/lotr_topaz");
    public static final ITag.INamedTag<Block> AMETHYST_ORE = BlockTags.bind("forge:ores/lotr_amethyst");
    public static final ITag.INamedTag<Block> SAPPHIRE_ORE = BlockTags.bind("forge:ores/lotr_sapphire");
    public static final ITag.INamedTag<Block> RUBY_ORE = BlockTags.bind("forge:ores/lotr_ruby");
    public static final ITag.INamedTag<Block> AMBER_ORE = BlockTags.bind("forge:ores/lotr_amber");
    public static final ITag.INamedTag<Block> DIAMOND_ORE = BlockTags.bind("forge:ores/lotr_diamond");
    public static final ITag.INamedTag<Block> OPAL_ORE = BlockTags.bind("forge:ores/lotr_opal");
    public static final ITag.INamedTag<Block> EMERALD_ORE = BlockTags.bind("forge:ores/lotr_emerald");
    public static final ITag.INamedTag<Block> DURNOR_ORE = BlockTags.bind("forge:ores/durnor");
    public static final ITag.INamedTag<Block> EDHELVIR_ORE = BlockTags.bind("forge:ores/edhelvir");
    public static final ITag.INamedTag<Block> GULDURIL_ORE = BlockTags.bind("forge:ores/gulduril");

	public ExtendedBlockTagGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, LotrExtendedMod.MODID, existingFileHelper);
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

	    tag(STORAGE_BLOCKS_GALVORN).add(ExtendedBlocks.GALVORN_BLOCK.get());
	    tag(STORAGE_BLOCKS_BLACK_URUK_STEEL).add(ExtendedBlocks.BLACK_URUK_STEEL_BLOCK.get());
	    tag(STORAGE_BLOCKS_GILDED_IRON).add(ExtendedBlocks.GILDED_IRON_BLOCK.get());
	    tag(STORAGE_BLOCKS_MORGUL_STEEL).add(ExtendedBlocks.MORGUL_STEEL_BLOCK.get());
	    tag(STORAGE_BLOCKS_DWARVEN_STEEL).add(LOTRBlocks.DWARVEN_STEEL_BLOCK.get());
	    tag(STORAGE_BLOCKS_ELVEN_STEEL).add(LOTRBlocks.ELVEN_STEEL_BLOCK.get());
	    tag(STORAGE_BLOCKS_MITHRIL).add(LOTRBlocks.MITHRIL_BLOCK.get());
	    tag(STORAGE_BLOCKS_URUK_STEEL).add(LOTRBlocks.URUK_STEEL_BLOCK.get());
	    tag(STORAGE_BLOCKS_TOPAZ).add(ExtendedBlocks.TOPAZ_BLOCK.get());
	    tag(STORAGE_BLOCKS_AMETHYST).add(ExtendedBlocks.AMETHYST_BLOCK.get());
	    tag(STORAGE_BLOCKS_SAPPHIRE).add(ExtendedBlocks.SAPPHIRE_BLOCK.get());
	    tag(STORAGE_BLOCKS_RUBY).add(ExtendedBlocks.RUBY_BLOCK.get());
	    tag(STORAGE_BLOCKS_AMBER).add(ExtendedBlocks.AMBER_BLOCK.get());
	    tag(STORAGE_BLOCKS_DIAMOND).add(ExtendedBlocks.DIAMOND_BLOCK.get());
	    tag(STORAGE_BLOCKS_OPAL).add(ExtendedBlocks.OPAL_BLOCK.get());
	    tag(STORAGE_BLOCKS_EMERALD).add(ExtendedBlocks.EMERALD_BLOCK.get());
	    tag(STORAGE_BLOCKS_PEARL).add(ExtendedBlocks.PEARL_BLOCK.get());
	    tag(STORAGE_BLOCKS_CORAL).add(ExtendedBlocks.CORAL_BLOCK.get());
	    tag(STORAGE_BLOCKS_DURNOR).add(LOTRBlocks.DURNOR_BLOCK.get());
	    tag(STORAGE_BLOCKS_EDHELVIR).add(LOTRBlocks.EDHELVIR_BLOCK.get());
	    tag(STORAGE_BLOCKS_GULDURIL).add(LOTRBlocks.GULDURIL_BLOCK.get());

	    tag(BlockTags.BEACON_BASE_BLOCKS).addTag(STORAGE_BLOCKS_GALVORN)
	    .addTag(STORAGE_BLOCKS_BLACK_URUK_STEEL).addTag(STORAGE_BLOCKS_GILDED_IRON)
	    .addTag(STORAGE_BLOCKS_MORGUL_STEEL).addTag(STORAGE_BLOCKS_TOPAZ)
	    .addTag(STORAGE_BLOCKS_AMETHYST).addTag(STORAGE_BLOCKS_SAPPHIRE)
	    .addTag(STORAGE_BLOCKS_RUBY) .addTag(STORAGE_BLOCKS_AMBER)
	    .addTag(STORAGE_BLOCKS_DIAMOND).addTag(STORAGE_BLOCKS_OPAL)
	    .addTag(STORAGE_BLOCKS_EMERALD).addTag(STORAGE_BLOCKS_PEARL)
	    .addTag(STORAGE_BLOCKS_CORAL);

        tag(Tags.Blocks.STORAGE_BLOCKS)
        .addTag(STORAGE_BLOCKS_GALVORN).addTag(STORAGE_BLOCKS_BLACK_URUK_STEEL)
	    .addTag(STORAGE_BLOCKS_GILDED_IRON).addTag(STORAGE_BLOCKS_MORGUL_STEEL)
	    .addTag(STORAGE_BLOCKS_DWARVEN_STEEL).addTag(STORAGE_BLOCKS_ELVEN_STEEL)
	    .addTag(STORAGE_BLOCKS_MITHRIL).addTag(STORAGE_BLOCKS_URUK_STEEL)
	    .addTag(STORAGE_BLOCKS_TOPAZ).addTag(STORAGE_BLOCKS_AMETHYST)
	    .addTag(STORAGE_BLOCKS_SAPPHIRE).addTag(STORAGE_BLOCKS_RUBY)
	    .addTag(STORAGE_BLOCKS_AMBER).addTag(STORAGE_BLOCKS_DIAMOND)
	    .addTag(STORAGE_BLOCKS_OPAL).addTag(STORAGE_BLOCKS_EMERALD)
	    .addTag(STORAGE_BLOCKS_PEARL).addTag(STORAGE_BLOCKS_CORAL)
	    .addTag(STORAGE_BLOCKS_DURNOR).addTag(STORAGE_BLOCKS_EDHELVIR)
	    .addTag(STORAGE_BLOCKS_GULDURIL);

        tag(COPPER_ORE).add(LOTRBlocks.COPPER_ORE.get());
        tag(NITER_ORE).add(LOTRBlocks.NITER_ORE.get());
        tag(SALT_ORE).add(LOTRBlocks.SALT_ORE.get());
        tag(SILVER_ORE).add(LOTRBlocks.SILVER_ORE.get());
        tag(SULFUR_ORE).add(LOTRBlocks.SULFUR_ORE.get());
        tag(TIN_ORE).add(LOTRBlocks.TIN_ORE.get());

		tag(TOPAZ_ORE).add(ExtendedBlocks.AMBER_ORE.get());
	    tag(AMETHYST_ORE).add(ExtendedBlocks.AMETHYST_ORE.get());
	    tag(SAPPHIRE_ORE).add(ExtendedBlocks.SAPPHIRE_ORE.get());
	    tag(RUBY_ORE).add(ExtendedBlocks.RUBY_ORE.get());
	    tag(AMBER_ORE).add(ExtendedBlocks.AMBER_ORE.get());
	    tag(DIAMOND_ORE).add(ExtendedBlocks.DIAMOND_ORE.get());
	    tag(OPAL_ORE).add(ExtendedBlocks.OPAL_ORE.get());
	    tag(EMERALD_ORE).add(ExtendedBlocks.EMERALD_ORE.get());
	    tag(DURNOR_ORE).add(LOTRBlocks.DURNOR_ORE.get());
	    tag(EDHELVIR_ORE).add(LOTRBlocks.EDHELVIR_ORE.get());
	    tag(GULDURIL_ORE).add(LOTRBlocks.GULDURIL_ORE_MORDOR.get()).add(LOTRBlocks.GULDURIL_ORE_STONE.get());
	    tag(GLOWSTONE_ORE).add(LOTRBlocks.GLOWSTONE_ORE.get());
	    tag(MITHRIL_ORE).add(LOTRBlocks.MITHRIL_ORE.get());
	    tag(MORGUL_IRON_ORE).add(LOTRBlocks.MORGUL_IRON_ORE_MORDOR.get()).add(LOTRBlocks.MORGUL_IRON_ORE_STONE.get());

	    tag(Tags.Blocks.ORES).addTag(TOPAZ_ORE)
	    .addTag(AMETHYST_ORE).addTag(SAPPHIRE_ORE)
	    .addTag(RUBY_ORE).addTag(AMBER_ORE)
	    .addTag(DIAMOND_ORE).addTag(OPAL_ORE)
	    .addTag(EMERALD_ORE) .addTag(DURNOR_ORE)
	    .addTag(EDHELVIR_ORE).addTag(GULDURIL_ORE)
	    .addTag(GLOWSTONE_ORE).addTag(MITHRIL_ORE)
	    .addTag(MORGUL_IRON_ORE).addTag(COPPER_ORE)
	    .addTag(NITER_ORE).addTag(SALT_ORE)
	    .addTag(SILVER_ORE).addTag(SULFUR_ORE)
	    .addTag(TIN_ORE);
	}

	@Override
	public String getName() {
		return "Extended Renewed Block Tags Generator";
	}
}
