package com.github.maximuslotro.lotroExtended.datagen.tags;

import com.github.maximuslotro.lotroExtended.common.init.ExtendedTags;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedItemTagGenerator extends ItemTagsProvider {

    public static final ITag.INamedTag<Item> PILLARS = ItemTags.bind("lotr:pillars");
    public static final ITag.INamedTag<Item> FURS = ItemTags.bind("forge:furs");


	public ExtendedItemTagGenerator(DataGenerator generator, BlockTagsProvider blockprovider, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, blockprovider, modid, existingFileHelper);
	}

	@Override
    protected void addTags() {
		this.copy(ExtendedBlockTagGenerator.PILLARS, ExtendedItemTagGenerator.PILLARS);
		this.copy(ExtendedBlockTagGenerator.SLABS, ItemTags.SLABS);
		this.copy(ExtendedBlockTagGenerator.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		this.copy(ExtendedBlockTagGenerator.STAIRS, ItemTags.STAIRS);
		this.copy(ExtendedBlockTagGenerator.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		this.copy(ExtendedBlockTagGenerator.WALLS, ItemTags.WALLS);
		
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_GALVORN, ExtendedTags.Items.STORAGE_BLOCKS_GALVORN);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_BLACK_URUK_STEEL, ExtendedTags.Items.STORAGE_BLOCKS_BLACK_URUK_STEEL);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_GILDED_IRON, ExtendedTags.Items.STORAGE_BLOCKS_GILDED_IRON);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_MORGUL_STEEL, ExtendedTags.Items.STORAGE_BLOCKS_MORGUL_STEEL);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_DWARVEN_STEEL, ExtendedTags.Items.STORAGE_BLOCKS_DWARVEN_STEEL);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_ELVEN_STEEL, ExtendedTags.Items.STORAGE_BLOCKS_ELVEN_STEEL);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_MITHRIL, ExtendedTags.Items.STORAGE_BLOCKS_MITHRIL);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_URUK_STEEL, ExtendedTags.Items.STORAGE_BLOCKS_URUK_STEEL);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_TOPAZ, ExtendedTags.Items.STORAGE_BLOCKS_TOPAZ);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_AMETHYST, ExtendedTags.Items.STORAGE_BLOCKS_AMETHYST);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_SAPPHIRE, ExtendedTags.Items.STORAGE_BLOCKS_SAPPHIRE);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_RUBY, ExtendedTags.Items.STORAGE_BLOCKS_RUBY);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_AMBER, ExtendedTags.Items.STORAGE_BLOCKS_AMBER);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_DIAMOND, ExtendedTags.Items.STORAGE_BLOCKS_DIAMOND);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_OPAL, ExtendedTags.Items.STORAGE_BLOCKS_OPAL);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_EMERALD, ExtendedTags.Items.STORAGE_BLOCKS_EMERALD);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_PEARL, ExtendedTags.Items.STORAGE_BLOCKS_PEARL);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_CORAL, ExtendedTags.Items.STORAGE_BLOCKS_CORAL);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_DURNOR, ExtendedTags.Items.STORAGE_BLOCKS_DURNOR);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_EDHELVIR, ExtendedTags.Items.STORAGE_BLOCKS_EDHELVIR);
		this.copy(ExtendedTags.Blocks.STORAGE_BLOCKS_GULDURIL, ExtendedTags.Items.STORAGE_BLOCKS_GULDURIL);
		
		this.copy(ExtendedTags.Blocks.COPPER_ORE, ExtendedTags.Items.COPPER_ORE);
		this.copy(ExtendedTags.Blocks.NITER_ORE, ExtendedTags.Items.NITER_ORE);
		this.copy(ExtendedTags.Blocks.SALT_ORE, ExtendedTags.Items.SALT_ORE);
		this.copy(ExtendedTags.Blocks.SILVER_ORE, ExtendedTags.Items.SILVER_ORE);
		this.copy(ExtendedTags.Blocks.SULFUR_ORE, ExtendedTags.Items.SULFUR_ORE);
		this.copy(ExtendedTags.Blocks.TIN_ORE, ExtendedTags.Items.TIN_ORE);
		this.copy(ExtendedTags.Blocks.GLOWSTONE_ORE, ExtendedTags.Items.GLOWSTONE_ORE);
		this.copy(ExtendedTags.Blocks.MITHRIL_ORE, ExtendedTags.Items.MITHRIL_ORE);
		this.copy(ExtendedTags.Blocks.MORGUL_IRON_ORE, ExtendedTags.Items.MORGUL_IRON_ORE);
		this.copy(ExtendedTags.Blocks.TOPAZ_ORE, ExtendedTags.Items.TOPAZ_ORE);
		this.copy(ExtendedTags.Blocks.AMETHYST_ORE, ExtendedTags.Items.AMETHYST_ORE);
		this.copy(ExtendedTags.Blocks.SAPPHIRE_ORE, ExtendedTags.Items.SAPPHIRE_ORE);
		this.copy(ExtendedTags.Blocks.RUBY_ORE, ExtendedTags.Items.RUBY_ORE);
		this.copy(ExtendedTags.Blocks.AMBER_ORE, ExtendedTags.Items.AMBER_ORE);
		this.copy(ExtendedTags.Blocks.DIAMOND_ORE, ExtendedTags.Items.DIAMOND_ORE);
		this.copy(ExtendedTags.Blocks.OPAL_ORE, ExtendedTags.Items.OPAL_ORE);
		this.copy(ExtendedTags.Blocks.EMERALD_ORE, ExtendedTags.Items.EMERALD_ORE);
		this.copy(ExtendedTags.Blocks.DURNOR_ORE, ExtendedTags.Items.DURNOR_ORE);
		this.copy(ExtendedTags.Blocks.EDHELVIR_ORE, ExtendedTags.Items.EDHELVIR_ORE);
		this.copy(ExtendedTags.Blocks.GULDURIL_ORE, ExtendedTags.Items.GULDURIL_ORE);
		
		tag(Tags.Items.STORAGE_BLOCKS).addTag(ExtendedTags.Items.STORAGE_BLOCKS_GALVORN)
	    .addTag(ExtendedTags.Items.STORAGE_BLOCKS_BLACK_URUK_STEEL).addTag(ExtendedTags.Items.STORAGE_BLOCKS_GILDED_IRON)
	    .addTag(ExtendedTags.Items.STORAGE_BLOCKS_MORGUL_STEEL).addTag(ExtendedTags.Items.STORAGE_BLOCKS_TOPAZ)
	    .addTag(ExtendedTags.Items.STORAGE_BLOCKS_AMETHYST).addTag(ExtendedTags.Items.STORAGE_BLOCKS_SAPPHIRE)
	    .addTag(ExtendedTags.Items.STORAGE_BLOCKS_RUBY).addTag(ExtendedTags.Items.STORAGE_BLOCKS_AMBER)
	    .addTag(ExtendedTags.Items.STORAGE_BLOCKS_DIAMOND).addTag(ExtendedTags.Items.STORAGE_BLOCKS_OPAL)
	    .addTag(ExtendedTags.Items.STORAGE_BLOCKS_EMERALD).addTag(ExtendedTags.Items.STORAGE_BLOCKS_PEARL)
	    .addTag(ExtendedTags.Items.STORAGE_BLOCKS_CORAL).addTag(ExtendedTags.Items.STORAGE_BLOCKS_DURNOR)
	    .addTag(ExtendedTags.Items.STORAGE_BLOCKS_EDHELVIR).addTag(ExtendedTags.Items.STORAGE_BLOCKS_GULDURIL);
		
		tag(ExtendedTags.Items.MORGUL_STEEL).add(ExtendedItems.MORGUL_STEEL_INGOT.get());
		tag(ExtendedTags.Items.ORC_STEEL).add(LOTRItems.ORC_STEEL_INGOT.get());
		tag(ExtendedTags.Items.MORGUL_INGOTS).add(ExtendedItems.MORGUL_STEEL_INGOT.get(),LOTRItems.ORC_STEEL_INGOT.get());
		tag(ExtendedTags.Items.GALVORN).add(ExtendedItems.GALVORN_INGOT.get());
		tag(ExtendedTags.Items.BLACK_URUK_STEEL).add(ExtendedItems.BLACK_URUK_STEEL_INGOT.get());
		tag(ExtendedTags.Items.GILDED_IRON).add(ExtendedItems.GILDED_IRON_INGOT.get());
		tag(ExtendedTags.Items.DWARVEN_STEEL).add(LOTRItems.DWARVEN_STEEL_INGOT.get());
		tag(ExtendedTags.Items.ELVEN_STEEL).add(LOTRItems.ELVEN_STEEL_INGOT.get());
		tag(ExtendedTags.Items.URUK_STEEL).add(LOTRItems.URUK_STEEL_INGOT.get());
		tag(ExtendedTags.Items.MITHRIL).add(LOTRItems.MITHRIL_INGOT.get());

		tag(Tags.Items.INGOTS).addTag(ExtendedTags.Items.MORGUL_STEEL)
		.addTag(ExtendedTags.Items.ORC_STEEL).addTag(ExtendedTags.Items.GALVORN)
		.addTag(ExtendedTags.Items.BLACK_URUK_STEEL).addTag(ExtendedTags.Items.GILDED_IRON)
		.addTag(ExtendedTags.Items.DWARVEN_STEEL).addTag(ExtendedTags.Items.ELVEN_STEEL)
		.addTag(ExtendedTags.Items.URUK_STEEL).addTag(ExtendedTags.Items.MITHRIL);
		
		tag(ExtendedTags.Items.TOPAZ_GEMS).add(ExtendedItems.TOPAZ.get());
		tag(ExtendedTags.Items.AMETHYST_GEMS).add(ExtendedItems.AMETHYST.get());
		tag(ExtendedTags.Items.SAPPHIRE_GEMS).add(ExtendedItems.SAPPHIRE.get());
		tag(ExtendedTags.Items.RUBY_GEMS).add(ExtendedItems.RUBY.get());
		tag(ExtendedTags.Items.AMBER_GEMS).add(ExtendedItems.AMBER.get());
		tag(ExtendedTags.Items.DIAMOND_GEMS).add(ExtendedItems.DIAMOND.get());
		tag(ExtendedTags.Items.OPAL_GEMS).add(ExtendedItems.OPAL.get());
		tag(ExtendedTags.Items.EMERALD_GEMS).add(ExtendedItems.EMERALD.get());
		tag(ExtendedTags.Items.PEARL_GEMS).add(ExtendedItems.PEARL.get());
		tag(ExtendedTags.Items.CORAL_GEMS).add(ExtendedItems.CORAL.get());
		tag(ExtendedTags.Items.DURNOR_GEMS).add(LOTRItems.DURNOR.get());
		tag(ExtendedTags.Items.EDHELVIR_GEMS).add(LOTRItems.EDHELVIR.get());
		tag(ExtendedTags.Items.GULDURIL_GEMS).add(LOTRItems.GULDURIL.get());
	    
		tag(Tags.Items.GEMS).addTag(ExtendedTags.Items.TOPAZ_GEMS)
		.addTag(ExtendedTags.Items.AMETHYST_GEMS).addTag(ExtendedTags.Items.SAPPHIRE_GEMS)
		.addTag(ExtendedTags.Items.RUBY_GEMS).addTag(ExtendedTags.Items.AMBER_GEMS)
		.addTag(ExtendedTags.Items.DIAMOND_GEMS).addTag(ExtendedTags.Items.OPAL_GEMS)
		.addTag(ExtendedTags.Items.EMERALD_GEMS).addTag(ExtendedTags.Items.PEARL_GEMS)
		.addTag(ExtendedTags.Items.CORAL_GEMS).addTag(ExtendedTags.Items.DURNOR_GEMS)
		.addTag(ExtendedTags.Items.EDHELVIR_GEMS).addTag(ExtendedTags.Items.GULDURIL_GEMS);

		tag(Tags.Items.ORES).addTag(ExtendedTags.Items.TOPAZ_ORE)
	    .addTag(ExtendedTags.Items.AMETHYST_ORE).addTag(ExtendedTags.Items.SAPPHIRE_ORE)
	    .addTag(ExtendedTags.Items.RUBY_ORE).addTag(ExtendedTags.Items.AMBER_ORE)
	    .addTag(ExtendedTags.Items.DIAMOND_ORE).addTag(ExtendedTags.Items.OPAL_ORE)
	    .addTag(ExtendedTags.Items.EMERALD_ORE) .addTag(ExtendedTags.Items.DURNOR_ORE)
	    .addTag(ExtendedTags.Items.EDHELVIR_ORE).addTag(ExtendedTags.Items.GULDURIL_ORE)
	    .addTag(ExtendedTags.Items.GLOWSTONE_ORE).addTag(ExtendedTags.Items.MITHRIL_ORE)
	    .addTag(ExtendedTags.Items.MORGUL_IRON_ORE).addTag(ExtendedTags.Items.COPPER_ORE)
	    .addTag(ExtendedTags.Items.NITER_ORE).addTag(ExtendedTags.Items.SALT_ORE)
	    .addTag(ExtendedTags.Items.SILVER_ORE).addTag(ExtendedTags.Items.SULFUR_ORE)
	    .addTag(ExtendedTags.Items.TIN_ORE);
		
		tag(FURS).add(ExtendedItems.BLACK_FUR.get(),ExtendedItems.GREY_FUR.get(),ExtendedItems.SILVER_FUR.get(),ExtendedItems.WHITE_FUR.get());
	}

	@Override
	public String getName() {
		return "Extended Renewed Item Tags Generator";
	}
}
