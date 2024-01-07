package lotr.common.datagen.tags;


import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

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
    public static final ITag.INamedTag<Item> MORGUL_STEEL = ItemTags.bind("forge:ingots/morgul_steel");
    public static final ITag.INamedTag<Item> ORC_STEEL = ItemTags.bind("forge:ingots/orc_steel");
    public static final ITag.INamedTag<Item> MORGUL_INGOTS = ItemTags.bind("forge:ingots/morgul_ingots");
    public static final ITag.INamedTag<Item> GALVORN = ItemTags.bind("forge:ingots/galvorn");
    public static final ITag.INamedTag<Item> BLACK_URUK_STEEL = ItemTags.bind("forge:ingots/black_uruk_steel");
    public static final ITag.INamedTag<Item> GILDED_IRON = ItemTags.bind("forge:ingots/gilded_iron");
    public static final ITag.INamedTag<Item> DWARVEN_STEEL = ItemTags.bind("forge:ingots/dwarven_steel");
    public static final ITag.INamedTag<Item> ELVEN_STEEL = ItemTags.bind("forge:ingots/elven_steel");
    public static final ITag.INamedTag<Item> URUK_STEEL = ItemTags.bind("forge:ingots/uruk_steel");
    public static final ITag.INamedTag<Item> MITHRIL = ItemTags.bind("forge:ingots/mithril");
    public static final ITag.INamedTag<Item> TOPAZ_GEMS = ItemTags.bind("forge:gems/lotr_topaz");
    public static final ITag.INamedTag<Item> AMETHYST_GEMS = ItemTags.bind("forge:gems/lotr_amethyst");
    public static final ITag.INamedTag<Item> SAPPHIRE_GEMS = ItemTags.bind("forge:gems/lotr_sapphire");
    public static final ITag.INamedTag<Item> RUBY_GEMS = ItemTags.bind("forge:gems/lotr_ruby");
    public static final ITag.INamedTag<Item> AMBER_GEMS = ItemTags.bind("forge:gems/lotr_amber");
    public static final ITag.INamedTag<Item> DIAMOND_GEMS = ItemTags.bind("forge:gems/lotr_diamond");
    public static final ITag.INamedTag<Item> OPAL_GEMS = ItemTags.bind("forge:gems/lotr_opal");
    public static final ITag.INamedTag<Item> EMERALD_GEMS = ItemTags.bind("forge:gems/lotr_emerald");
    public static final ITag.INamedTag<Item> PEARL_GEMS = ItemTags.bind("forge:gems/lotr_pearl");
    public static final ITag.INamedTag<Item> CORAL_GEMS = ItemTags.bind("forge:gems/lotr_coral");
    public static final ITag.INamedTag<Item> DURNOR_GEMS = ItemTags.bind("forge:gems/lotr_durnor");
    public static final ITag.INamedTag<Item> EDHELVIR_GEMS = ItemTags.bind("forge:gems/lotr_edhelvir");
    public static final ITag.INamedTag<Item> GULDURIL_GEMS = ItemTags.bind("forge:gems/lotr_gulduril");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_GALVORN = ItemTags.bind("forge:storage_blocks/galvorn");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_BLACK_URUK_STEEL = ItemTags.bind("forge:storage_blocks/black_uruk_steel");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_GILDED_IRON = ItemTags.bind("forge:storage_blocks/gilded_iron");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_MORGUL_STEEL = ItemTags.bind("forge:storage_blocks/morgul_steel");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_DWARVEN_STEEL = ItemTags.bind("forge:storage_blocks/dwarven_steel");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_ELVEN_STEEL = ItemTags.bind("forge:storage_blocks/elven_steel");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_MITHRIL = ItemTags.bind("forge:storage_blocks/mithril");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_URUK_STEEL = ItemTags.bind("forge:storage_blocks/uruk_steel");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_TOPAZ = ItemTags.bind("forge:storage_blocks/lotr_topaz");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_AMETHYST = ItemTags.bind("forge:storage_blocks/lotr_amethyst");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_SAPPHIRE = ItemTags.bind("forge:storage_blocks/lotr_sapphire");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_RUBY = ItemTags.bind("forge:storage_blocks/lotr_ruby");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_AMBER = ItemTags.bind("forge:storage_blocks/lotr_amber");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_DIAMOND = ItemTags.bind("forge:storage_blocks/lotr_diamond");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_OPAL = ItemTags.bind("forge:storage_blocks/lotr_opal");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_EMERALD = ItemTags.bind("forge:storage_blocks/lotr_emerald");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_PEARL = ItemTags.bind("forge:storage_blocks/lotr_pearl");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_CORAL = ItemTags.bind("forge:storage_blocks/lotr_coral");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_DURNOR = ItemTags.bind("forge:storage_blocks/lotr_durnor");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_EDHELVIR = ItemTags.bind("forge:storage_blocks/lotr_edhelvir");
    public static final ITag.INamedTag<Item> STORAGE_BLOCKS_GULDURIL = ItemTags.bind("forge:storage_blocks/lotr_gulduril");
    
    public static final ITag.INamedTag<Item> COPPER_ORE = ItemTags.bind("forge:ores/copper");
    public static final ITag.INamedTag<Item> NITER_ORE = ItemTags.bind("forge:ores/niter");
    public static final ITag.INamedTag<Item> SALT_ORE = ItemTags.bind("forge:ores/salt");
    public static final ITag.INamedTag<Item> SILVER_ORE = ItemTags.bind("forge:ores/silver");
    public static final ITag.INamedTag<Item> SULFUR_ORE = ItemTags.bind("forge:ores/sulfur");
    public static final ITag.INamedTag<Item> TIN_ORE = ItemTags.bind("forge:ores/tin");

    public static final ITag.INamedTag<Item> GLOWSTONE_ORE = ItemTags.bind("forge:ores/glowstone");
    public static final ITag.INamedTag<Item> MITHRIL_ORE = ItemTags.bind("forge:ores/mithril");
    public static final ITag.INamedTag<Item> MORGUL_IRON_ORE = ItemTags.bind("forge:ores/morgul_iron");
    public static final ITag.INamedTag<Item> TOPAZ_ORE = ItemTags.bind("forge:ores/lotr_topaz");
    public static final ITag.INamedTag<Item> AMETHYST_ORE = ItemTags.bind("forge:ores/lotr_amethyst");
    public static final ITag.INamedTag<Item> SAPPHIRE_ORE = ItemTags.bind("forge:ores/lotr_sapphire");
    public static final ITag.INamedTag<Item> RUBY_ORE = ItemTags.bind("forge:ores/lotr_ruby");
    public static final ITag.INamedTag<Item> AMBER_ORE = ItemTags.bind("forge:ores/lotr_amber");
    public static final ITag.INamedTag<Item> DIAMOND_ORE = ItemTags.bind("forge:ores/lotr_diamond");
    public static final ITag.INamedTag<Item> OPAL_ORE = ItemTags.bind("forge:ores/lotr_opal");
    public static final ITag.INamedTag<Item> EMERALD_ORE = ItemTags.bind("forge:ores/lotr_emerald");
    public static final ITag.INamedTag<Item> DURNOR_ORE = ItemTags.bind("forge:ores/durnor");
    public static final ITag.INamedTag<Item> EDHELVIR_ORE = ItemTags.bind("forge:ores/edhelvir");
    public static final ITag.INamedTag<Item> GULDURIL_ORE = ItemTags.bind("forge:ores/gulduril");

	public ExtendedItemTagGenerator(DataGenerator generator, BlockTagsProvider blockprovider, ExistingFileHelper existingFileHelper) {
		super(generator, blockprovider, LotrExtendedMod.MODID, existingFileHelper);
	}

	@Override
    protected void addTags() {
		this.copy(ExtendedBlockTagGenerator.PILLARS, ExtendedItemTagGenerator.PILLARS);
		this.copy(ExtendedBlockTagGenerator.SLABS, ItemTags.SLABS);
		this.copy(ExtendedBlockTagGenerator.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		this.copy(ExtendedBlockTagGenerator.STAIRS, ItemTags.STAIRS);
		this.copy(ExtendedBlockTagGenerator.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		this.copy(ExtendedBlockTagGenerator.WALLS, ItemTags.WALLS);
		
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_GALVORN, STORAGE_BLOCKS_GALVORN);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_BLACK_URUK_STEEL, STORAGE_BLOCKS_BLACK_URUK_STEEL);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_GILDED_IRON, STORAGE_BLOCKS_GILDED_IRON);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_MORGUL_STEEL, STORAGE_BLOCKS_MORGUL_STEEL);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_DWARVEN_STEEL, STORAGE_BLOCKS_DWARVEN_STEEL);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_ELVEN_STEEL, STORAGE_BLOCKS_ELVEN_STEEL);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_MITHRIL, STORAGE_BLOCKS_MITHRIL);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_URUK_STEEL, STORAGE_BLOCKS_URUK_STEEL);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_TOPAZ, STORAGE_BLOCKS_TOPAZ);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_AMETHYST, STORAGE_BLOCKS_AMETHYST);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_SAPPHIRE, STORAGE_BLOCKS_SAPPHIRE);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_RUBY, STORAGE_BLOCKS_RUBY);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_AMBER, STORAGE_BLOCKS_AMBER);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_DIAMOND, STORAGE_BLOCKS_DIAMOND);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_OPAL, STORAGE_BLOCKS_OPAL);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_EMERALD, STORAGE_BLOCKS_EMERALD);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_PEARL, STORAGE_BLOCKS_PEARL);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_CORAL, STORAGE_BLOCKS_CORAL);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_DURNOR, STORAGE_BLOCKS_DURNOR);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_EDHELVIR, STORAGE_BLOCKS_EDHELVIR);
		this.copy(ExtendedBlockTagGenerator.STORAGE_BLOCKS_GULDURIL, STORAGE_BLOCKS_GULDURIL);
		
		this.copy(ExtendedBlockTagGenerator.COPPER_ORE, COPPER_ORE);
		this.copy(ExtendedBlockTagGenerator.NITER_ORE, NITER_ORE);
		this.copy(ExtendedBlockTagGenerator.SALT_ORE, SALT_ORE);
		this.copy(ExtendedBlockTagGenerator.SILVER_ORE, SILVER_ORE);
		this.copy(ExtendedBlockTagGenerator.SULFUR_ORE, SULFUR_ORE);
		this.copy(ExtendedBlockTagGenerator.TIN_ORE, TIN_ORE);
		this.copy(ExtendedBlockTagGenerator.GLOWSTONE_ORE, GLOWSTONE_ORE);
		this.copy(ExtendedBlockTagGenerator.MITHRIL_ORE, MITHRIL_ORE);
		this.copy(ExtendedBlockTagGenerator.MORGUL_IRON_ORE, MORGUL_IRON_ORE);
		this.copy(ExtendedBlockTagGenerator.TOPAZ_ORE, TOPAZ_ORE);
		this.copy(ExtendedBlockTagGenerator.AMETHYST_ORE, AMETHYST_ORE);
		this.copy(ExtendedBlockTagGenerator.SAPPHIRE_ORE, SAPPHIRE_ORE);
		this.copy(ExtendedBlockTagGenerator.RUBY_ORE, RUBY_ORE);
		this.copy(ExtendedBlockTagGenerator.AMBER_ORE, AMBER_ORE);
		this.copy(ExtendedBlockTagGenerator.DIAMOND_ORE, DIAMOND_ORE);
		this.copy(ExtendedBlockTagGenerator.OPAL_ORE, OPAL_ORE);
		this.copy(ExtendedBlockTagGenerator.EMERALD_ORE, EMERALD_ORE);
		this.copy(ExtendedBlockTagGenerator.DURNOR_ORE, DURNOR_ORE);
		this.copy(ExtendedBlockTagGenerator.EDHELVIR_ORE, EDHELVIR_ORE);
		this.copy(ExtendedBlockTagGenerator.GULDURIL_ORE, GULDURIL_ORE);
		
		tag(Tags.Items.STORAGE_BLOCKS).addTag(STORAGE_BLOCKS_GALVORN)
	    .addTag(STORAGE_BLOCKS_BLACK_URUK_STEEL).addTag(STORAGE_BLOCKS_GILDED_IRON)
	    .addTag(STORAGE_BLOCKS_MORGUL_STEEL).addTag(STORAGE_BLOCKS_TOPAZ)
	    .addTag(STORAGE_BLOCKS_AMETHYST).addTag(STORAGE_BLOCKS_SAPPHIRE)
	    .addTag(STORAGE_BLOCKS_RUBY).addTag(STORAGE_BLOCKS_AMBER)
	    .addTag(STORAGE_BLOCKS_DIAMOND).addTag(STORAGE_BLOCKS_OPAL)
	    .addTag(STORAGE_BLOCKS_EMERALD).addTag(STORAGE_BLOCKS_PEARL)
	    .addTag(STORAGE_BLOCKS_CORAL).addTag(STORAGE_BLOCKS_DURNOR)
	    .addTag(STORAGE_BLOCKS_EDHELVIR).addTag(STORAGE_BLOCKS_GULDURIL);
		
		tag(MORGUL_STEEL).add(ExtendedItems.MORGUL_STEEL_INGOT.get());
		tag(ORC_STEEL).add(LOTRItems.ORC_STEEL_INGOT.get());
		tag(MORGUL_INGOTS).add(ExtendedItems.MORGUL_STEEL_INGOT.get(),LOTRItems.ORC_STEEL_INGOT.get());
		tag(GALVORN).add(ExtendedItems.GALVORN_INGOT.get());
		tag(BLACK_URUK_STEEL).add(ExtendedItems.BLACK_URUK_STEEL_INGOT.get());
		tag(GILDED_IRON).add(ExtendedItems.GILDED_IRON_INGOT.get());
		tag(DWARVEN_STEEL).add(LOTRItems.DWARVEN_STEEL_INGOT.get());
		tag(ELVEN_STEEL).add(LOTRItems.ELVEN_STEEL_INGOT.get());
		tag(URUK_STEEL).add(LOTRItems.URUK_STEEL_INGOT.get());
		tag(MITHRIL).add(LOTRItems.MITHRIL_INGOT.get());

		tag(Tags.Items.INGOTS).addTag(MORGUL_STEEL)
		.addTag(ORC_STEEL).addTag(GALVORN)
		.addTag(BLACK_URUK_STEEL).addTag(GILDED_IRON)
		.addTag(DWARVEN_STEEL).addTag(ELVEN_STEEL)
		.addTag(URUK_STEEL).addTag(MITHRIL);
		
		tag(TOPAZ_GEMS).add(ExtendedItems.TOPAZ.get());
		tag(AMETHYST_GEMS).add(ExtendedItems.AMETHYST.get());
		tag(SAPPHIRE_GEMS).add(ExtendedItems.SAPPHIRE.get());
		tag(RUBY_GEMS).add(ExtendedItems.RUBY.get());
		tag(AMBER_GEMS).add(ExtendedItems.AMBER.get());
		tag(DIAMOND_GEMS).add(ExtendedItems.DIAMOND.get());
		tag(OPAL_GEMS).add(ExtendedItems.OPAL.get());
		tag(EMERALD_GEMS).add(ExtendedItems.EMERALD.get());
		tag(PEARL_GEMS).add(ExtendedItems.PEARL.get());
		tag(CORAL_GEMS).add(ExtendedItems.CORAL.get());
		tag(DURNOR_GEMS).add(LOTRItems.DURNOR.get());
		tag(EDHELVIR_GEMS).add(LOTRItems.EDHELVIR.get());
		tag(GULDURIL_GEMS).add(LOTRItems.GULDURIL.get());
	    
		tag(Tags.Items.GEMS).addTag(TOPAZ_GEMS)
		.addTag(AMETHYST_GEMS).addTag(SAPPHIRE_GEMS)
		.addTag(RUBY_GEMS).addTag(AMBER_GEMS)
		.addTag(DIAMOND_GEMS).addTag(OPAL_GEMS)
		.addTag(EMERALD_GEMS).addTag(PEARL_GEMS)
		.addTag(CORAL_GEMS).addTag(DURNOR_GEMS)
		.addTag(EDHELVIR_GEMS).addTag(GULDURIL_GEMS);

		tag(Tags.Items.ORES).addTag(TOPAZ_ORE)
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
		
		tag(FURS).add(ExtendedItems.BLACK_FUR.get(),ExtendedItems.GREY_FUR.get(),ExtendedItems.SILVER_FUR.get(),ExtendedItems.WHITE_FUR.get());
	}

	@Override
	public String getName() {
		return "Extended Renewed Item Tags Generator";
	}
}
