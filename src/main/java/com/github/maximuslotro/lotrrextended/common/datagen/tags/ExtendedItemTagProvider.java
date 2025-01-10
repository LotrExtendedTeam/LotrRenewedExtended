package com.github.maximuslotro.lotrrextended.common.datagen.tags;

import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Function;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBrandingIronItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedChiselItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRTags;
import lotr.common.item.DaggerItem;
import lotr.common.item.HammerItem;
import lotr.common.item.LOTRAxeItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.item.LOTRPickaxeItem;
import lotr.common.item.LOTRShovelItem;
import lotr.common.item.LOTRSwordItem;
import lotr.common.item.SpearItem;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedItemTagProvider extends ExtendedTagsProvider<Item> {
	private final Function<ITag.INamedTag<Block>, ITag.Builder> blockTags;
	//LOTR
    public static final ITag.INamedTag<Item> PILLARS = ItemTags.bind("lotr:pillars");
	public static final ITag.INamedTag<Item> WOODEN_FENCES = ItemTags.bind("lotr:wooden_fences");
	public static final ITag.INamedTag<Item> WOODEN_FENCE_GATES = ItemTags.bind("lotr:wooden_fence_gates");
	public static final ITag.INamedTag<Item> LOG_SLABS = ItemTags.bind("lotr:log_slabs");
	public static final ITag.INamedTag<Item> LOG_STAIRS = ItemTags.bind("lotr:log_stairs");
	public static final ITag.INamedTag<Item> WOODEN_BEAMS = ItemTags.bind("lotr:wooden_beams");
	public static final ITag.INamedTag<Item> WOODEN_BEAM_SLABS = ItemTags.bind("lotr:wooden_beam_slabs");
	public static final ITag.INamedTag<Item> BRANCHES = ItemTags.bind("lotr:branches");
	public static final ITag.INamedTag<Item> CHARRABLE_LOGS = ItemTags.bind("lotr:charrable_logs");
	public static final ITag.INamedTag<Item> CHARRABLE_WOODS = ItemTags.bind("lotr:charrable_woods");
	public static final ITag.INamedTag<Item> CHARRABLE_STRIPPED_LOGS = ItemTags.bind("lotr:charrable_stripped_logs");
	public static final ITag.INamedTag<Item> CHARRABLE_STRIPPED_WOODS = ItemTags.bind("lotr:charrable_stripped_woods");

	@SuppressWarnings("deprecation")
	public ExtendedItemTagProvider(DataGenerator generator, ExtendedBlockTagProvider blockprovider, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, Registry.ITEM, modid, existingFileHelper);
		this.blockTags = blockprovider::getOrCreateRawBuilder;
	}

	@Override
    protected void addTags() {
		this.copy(ExtendedBlockTagProvider.PILLARS, ExtendedItemTagProvider.PILLARS);
		this.copy(ExtendedBlockTagProvider.SLABS, ItemTags.SLABS);
		this.copy(ExtendedBlockTagProvider.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		this.copy(ExtendedBlockTagProvider.STAIRS, ItemTags.STAIRS);
		this.copy(ExtendedBlockTagProvider.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		this.copy(ExtendedBlockTagProvider.WALLS, ItemTags.WALLS);
		this.copy(ExtendedBlockTagProvider.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		this.copy(ExtendedBlockTagProvider.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
		this.copy(ExtendedBlockTagProvider.TRAPDOORS, ItemTags.TRAPDOORS);
		this.copy(ExtendedBlockTagProvider.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
		this.copy(ExtendedBlockTagProvider.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
		this.copy(ExtendedBlockTagProvider.DOORS, ItemTags.DOORS);
		this.copy(ExtendedBlockTagProvider.STANDING_SIGNS, ItemTags.SIGNS);
		this.copy(ExtendedBlockTagProvider.SAPLINGS, ItemTags.SAPLINGS);
		this.copy(ExtendedBlockTagProvider.PLANKS, ItemTags.PLANKS);
		this.copy(ExtendedBlockTagProvider.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
		this.copy(ExtendedBlockTagProvider.LEAVES, ItemTags.LEAVES);
		this.copy(ExtendedBlockTagProvider.CHESTS, Tags.Items.CHESTS);
		this.copy(ExtendedBlockTagProvider.BARRELS, Tags.Items.BARRELS);
		this.copy(ExtendedBlockTagProvider.BARRELS_WOODEN, Tags.Items.BARRELS_WOODEN);

		this.copy(ExtendedBlockTagProvider.WOODEN_FENCES, ExtendedItemTagProvider.WOODEN_FENCES);
		this.copy(ExtendedBlockTagProvider.WOODEN_FENCE_GATES, ExtendedItemTagProvider.WOODEN_FENCE_GATES);
		this.copy(ExtendedBlockTagProvider.LOG_SLABS, ExtendedItemTagProvider.LOG_SLABS);
		this.copy(ExtendedBlockTagProvider.LOG_STAIRS, ExtendedItemTagProvider.LOG_STAIRS);
		this.copy(ExtendedBlockTagProvider.WOODEN_BEAMS, ExtendedItemTagProvider.WOODEN_BEAMS);
		this.copy(ExtendedBlockTagProvider.WOODEN_BEAM_SLABS, ExtendedItemTagProvider.WOODEN_BEAM_SLABS);
		this.copy(ExtendedBlockTagProvider.BRANCHES, ExtendedItemTagProvider.BRANCHES);
		this.copy(ExtendedBlockTagProvider.CHARRABLE_LOGS, ExtendedItemTagProvider.CHARRABLE_LOGS);
		this.copy(ExtendedBlockTagProvider.CHARRABLE_WOODS, ExtendedItemTagProvider.CHARRABLE_WOODS);
		this.copy(ExtendedBlockTagProvider.CHARRABLE_STRIPPED_LOGS, ExtendedItemTagProvider.CHARRABLE_STRIPPED_LOGS);
		this.copy(ExtendedBlockTagProvider.CHARRABLE_STRIPPED_WOODS, ExtendedItemTagProvider.CHARRABLE_STRIPPED_WOODS);

		this.copy(ExtendedTags.Blocks.CHESTNUT_LOGS, ExtendedTags.Items.CHESTNUT_LOGS);
		this.copy(ExtendedTags.Blocks.BANANA_LOGS, ExtendedTags.Items.BANANA_LOGS);
		this.copy(ExtendedTags.Blocks.PLUM_LOGS, ExtendedTags.Items.PLUM_LOGS);

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

		tag(ExtendedTags.Items.ORC_STEEL_NUGGET).add(ExtendedItems.ORC_STEEL_NUGGET.get());

		tag(Tags.Items.INGOTS).addTag(ExtendedTags.Items.MORGUL_STEEL)
		.addTag(ExtendedTags.Items.ORC_STEEL).addTag(ExtendedTags.Items.GALVORN)
		.addTag(ExtendedTags.Items.BLACK_URUK_STEEL).addTag(ExtendedTags.Items.GILDED_IRON)
		.addTag(ExtendedTags.Items.DWARVEN_STEEL).addTag(ExtendedTags.Items.ELVEN_STEEL)
		.addTag(ExtendedTags.Items.URUK_STEEL).addTag(ExtendedTags.Items.MITHRIL);

		tag(Tags.Items.NUGGETS).addTag(ExtendedTags.Items.ORC_STEEL_NUGGET);

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

		tag(Tags.Items.EGGS).add(ExtendedItems.DUCK_EGG.get());

		tag(ExtendedTags.Items.FURS).add(ExtendedItems.BLACK_FUR.get(),ExtendedItems.GRAY_FUR.get(),ExtendedItems.SILVER_FUR.get(),ExtendedItems.WHITE_FUR.get());

		tag(ItemTags.BOATS).add(ExtendedItems.CHESTNUT_BOAT.get()).add(ExtendedItems.BANANA_BOAT.get()).add(ExtendedItems.PLUM_BOAT.get());

		tag(ExtendedTags.Items.WEAPONRACK_ITEMS_HALF_SCALING).add(Items.AIR);
		tag(ExtendedTags.Items.WEAPONRACK_ITEMS_EXCLUDE).add(Items.AIR).add(Items.BARRIER).add(Items.STRUCTURE_VOID).add(Items.DEBUG_STICK).add(Items.COMMAND_BLOCK);

		tag(ExtendedTags.Items.DUCK_FOOD).add(Items.BEETROOT_SEEDS).add(Items.MELON_SEEDS)
		.add(Items.PUMPKIN_SEEDS).add(Items.WHEAT_SEEDS).add(Items.APPLE).add(Items.BREAD)
		.add(LOTRItems.FLAX_SEEDS.get()).add(LOTRItems.PIPEWEED_SEEDS.get()).add(LOTRItems.FISH_AND_CHIPS.get())
		.add(LOTRItems.LETTUCE.get()).add(LOTRItems.GREEN_APPLE.get()).add(LOTRItems.PEAR.get()).add(LOTRItems.CHERRY.get())
		.add(ExtendedItems.BANANA.get()).add(ExtendedItems.BANANA_BREAD.get()).add(ExtendedItems.BLACKBERRY.get())
		.add(ExtendedItems.BLUEBERRY.get()).add(ExtendedItems.CRANBERRY.get()).add(ExtendedItems.ELDERBERRY.get())
		.add(ExtendedItems.RASPBERRY.get()).add(ExtendedItems.PLUM.get());

		tag(ExtendedTags.Items.COIN_ALL).add(ExtendedItems.SILVER_COIN_ONE.get())
		.add(ExtendedItems.SILVER_COIN_TEN.get()).add(ExtendedItems.SILVER_COIN_HUNDRED.get());
		tag(ExtendedTags.Items.COIN_ONE).add(ExtendedItems.SILVER_COIN_ONE.get());
		tag(ExtendedTags.Items.COIN_TEN).add(ExtendedItems.SILVER_COIN_TEN.get());
		tag(ExtendedTags.Items.COIN_HUNDRED).add(ExtendedItems.SILVER_COIN_HUNDRED.get());

		tag(LOTRTags.Items.DRINK_VESSELS).add(ExtendedItems.SKULL_GOBLET.get());

		tag(ExtendedTags.Items.TORCH_EXTINGUISHING).add(Items.POTION).add(LOTRItems.WATER_DRINK.get());
		tag(ExtendedTags.Items.TORCH_LIGHTING).add(LOTRItems.POCKET_MATCH.get()).add(Items.FLINT_AND_STEEL);

		TreeMap<String, Item> fullitems = new TreeMap<>();
		for(RegistryObject<Item> item : LOTRItems.ITEMS.getEntries()) {
			if(item.get() instanceof SpearItem) {
				fullitems.put(item.get().getRegistryName().getPath(), item.get());
				continue;
			}
			if(item.get() instanceof HammerItem) {
				if(addTagWithFilter(item.get())) {
					fullitems.put(item.get().getRegistryName().getPath(), item.get());
				}
				continue;
			}
			if(item.get() instanceof DaggerItem) {
				continue;
			}
			if(item.get() instanceof LOTRSwordItem) {
				if(addTagWithFilter(item.get())) {
					fullitems.put(item.get().getRegistryName().getPath(), item.get());
				}
				continue;
			}
		}
		for(Entry<String, Item> ent : fullitems.entrySet()) {
			tag(ExtendedTags.Items.WEAPONRACK_ITEMS_FULL_SCALE).add(ent.getValue());
		}

		tag(ExtendedTags.Items.STONE_GRINDER_LUBRICANTS).add(Items.WATER_BUCKET).add(Items.POTION);
//		tag(ExtendedTags.Items.STONE_GRINDER_LUBRICANTS).add(Items.MILK_BUCKET).add(Items.HONEY_BOTTLE).add(Items.WATER_BUCKET)
//		.add(Items.POTION).add(Items.SPLASH_POTION).add(Items.LINGERING_POTION);
//		for(Entry<RegistryKey<Item>, Item> item : ForgeRegistries.ITEMS.getEntries()) {
//			if(item.getValue() instanceof VesselDrinkItem) {
//				tag(ExtendedTags.Items.STONE_GRINDER_LUBRICANTS).add(item.getValue());
//			}
//		}

		TreeMap<String, Item> additems = new TreeMap<>();
		for(Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDITEMS.entrySet()) {
			if((item.getValue().get() instanceof SpearItem) || (item.getValue().get() instanceof DaggerItem)
					|| (item.getValue().get() instanceof LOTRSwordItem)
					|| (item.getValue().get() instanceof LOTRPickaxeItem)) {
				additems.put(item.getKey(), item.getValue().get());
				continue;
			}
			if(item.getValue().get() instanceof LOTRShovelItem) {
				additems.put(item.getKey(), item.getValue().get());
				continue;
			}
			if(item.getValue().get() instanceof LOTRAxeItem) {
				additems.put(item.getKey(), item.getValue().get());
				continue;
			}
			if(item.getValue().get() instanceof LOTRHoeItem ) {
				additems.put(item.getKey(), item.getValue().get());
				continue;
			}
			if(item.getValue().get() instanceof ExtendedChiselItem ) {
				additems.put(item.getKey(), item.getValue().get());
				continue;
			}
			if(item.getValue().get() instanceof ExtendedCommandHornItem) {
				additems.put(item.getKey(), item.getValue().get());
				continue;
			}
			if(item.getValue().get() instanceof ExtendedBrandingIronItem) {
				additems.put(item.getKey(), item.getValue().get());
				continue;
			}
		}
		for(Entry<String, Item> ent : additems.entrySet()) {
			tag(ExtendedTags.Items.WEAPONRACK_ITEMS).add(ent.getValue());
		}
	}

	public boolean addTagWithFilter(Item itemToAdd) {
		if(itemToAdd == LOTRItems.BLUE_DWARVEN_SWORD.get()||itemToAdd == LOTRItems.DWARVEN_SWORD.get()||itemToAdd == LOTRItems.BRONZE_SWORD.get()
				||itemToAdd == LOTRItems.MALLORN_SWORD.get()||itemToAdd == LOTRItems.DALE_SWORD.get()||itemToAdd == LOTRItems.BLACKSMITH_HAMMER.get()) {
			return false;
		}
		return true;
	}

	protected void copy(ITag.INamedTag<Block> pBlockTag, ITag.INamedTag<Item> pItemTag) {
		ITag.Builder itag$builder = this.getOrCreateRawBuilder(pItemTag);
		ITag.Builder itag$builder1 = this.blockTags.apply(pBlockTag);
		itag$builder1.getEntries().forEach(itag$builder::add);
	}

	@Override
	public String getName() {
		return "Extended Renewed Item Tags Generator";
	}

	@Override
	protected Path getPath(ResourceLocation pId) {
		return this.generator.getOutputFolder().resolve("data/" + pId.getNamespace() + "/tags/items/" + pId.getPath() + ".json");
	}
}
