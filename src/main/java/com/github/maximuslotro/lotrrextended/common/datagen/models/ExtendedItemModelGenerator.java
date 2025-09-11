package com.github.maximuslotro.lotrrextended.common.datagen.models;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import com.github.maximuslotro.lotrrextended.client.render.item.ExtendedItemModelsProperties;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBirdcageBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedChainBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCrystalChandelierBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLargeFlowerPotBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLargePotBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedVineBlock;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBrandingIronItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedEntitySpawnerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMCBannerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedSilverCoin;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedSmithScrollItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableAxeItem;

import lotr.common.block.ChandelierBlock;
import lotr.common.block.LOTRDoorBlock;
import lotr.common.block.LOTRSaplingBlock;
import lotr.common.block.LOTRStandingSignBlock;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.item.DaggerItem;
import lotr.common.item.ExtendedDyeableLOTRArmorItem;
import lotr.common.item.FallenLeavesItem;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRAxeItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.item.LOTRPickaxeItem;
import lotr.common.item.LOTRShieldItem;
import lotr.common.item.LOTRShovelItem;
import lotr.common.item.LOTRSwordItem;
import lotr.common.item.SpearItem;
import lotr.common.item.VesselDrinkItem;
import net.minecraft.block.DoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedItemModelGenerator extends ItemModelProvider {

	public ExtendedItemModelGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	public ResourceLocation lotrExtendedLoc(String name) {
        return new ResourceLocation("lotrextended", name);
    }

	public ResourceLocation lotrLoc(String name) {
        return new ResourceLocation("lotr", name);
    }

	public void registerOverrides() {
		genGenerated(ExtendedItems.MORGUL_LANTERN.getId().getPath(), lotrExtendedLoc("item/"+ExtendedItems.MORGUL_LANTERN.getId().getPath()));
		genGenerated(ExtendedItems.WEAPON_RACK.getId().getPath(), lotrExtendedLoc("item/"+ExtendedItems.WEAPON_RACK.getId().getPath()));
		genGenerated(ExtendedItems.ENTITY_SPAWNER.getId().getPath(), lotrExtendedLoc("block/spawner_block"));
		genGenerated(ExtendedItems.ROPE.getId().getPath(), lotrExtendedLoc("item/"+ExtendedItems.ROPE.getId().getPath()));
		genGenerated(ExtendedItems.GRASS_VOID.getId().getPath(), lotrExtendedLoc("item/"+ExtendedItems.GRASS_VOID.getId().getPath()));
	}
	@Override
	protected void registerModels() {
		for(Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDBLOCKITEMS.entrySet()) {
			if((((BlockItem)item.getValue().get()).getBlock() instanceof LOTRDoorBlock) || (((BlockItem)item.getValue().get()).getBlock() instanceof DoorBlock)
					|| (((BlockItem)item.getValue().get()).getBlock() instanceof ChandelierBlock)) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof LOTRSaplingBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("block/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof ExtendedLargePotBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof ExtendedLargeFlowerPotBlock && !item.getKey().contains("large_pot_")) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof LOTRStandingSignBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof ExtendedBirdcageBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof VesselDrinkBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof ExtendedChainBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof ExtendedCrystalChandelierBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof ExtendedChainBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof ExtendedVineBlock) {
				if(item.getKey().contains(ExtendedItems.DEAD_MOSS_VINES.get().getRegistryName().getPath())) {
					genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()+"_hand"));
					continue;
				}else {
					genGenerated(item.getKey(), lotrExtendedLoc("block/"+item.getKey()));
				}
				continue;
			}
			if(item.getValue().get() instanceof FallenLeavesItem) {
				try {
					genGenerated(item.getKey(), true, lotrExtendedLoc("block/"+item.getKey().substring(7)));
				}catch(IllegalArgumentException e) {
					genGenerated(item.getKey(), lotrExtendedLoc("block/"+item.getKey().substring(7)+"_0"));
				}
				continue;
			}
		}
		for(Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDITEMS.entrySet()) {
			if(item.getValue().get() instanceof ExtendedEntitySpawnerItem) {
				continue;
			}
			if(item.getValue().get() instanceof ExtendedDyeableLOTRArmorItem) {
				ItemModelBuilder builder = withExistingParent("lotr:"+item.getKey(), "lotrextended:item/template_coin");
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()), lotrExtendedLoc("item/"+item.getKey()+"_overlay"));
				continue;
			}
			if(item.getKey().contains("torch")) {
				genGenerated(item.getKey(), lotrExtendedLoc("block/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRArmorItem) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;

			}
			if(item.getValue().get() instanceof VesselDrinkItem) {
				genDrink(item.getKey(), "item/"+item.getKey());
				continue;

			}
			if(item.getValue().get() instanceof BannerPatternItem) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof SpearItem) {
				genHandheld(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				genSpearPulling(item.getKey());
				genSpear(item.getKey());
				continue;
			}
			if(item.getValue().get() instanceof DaggerItem) {
				genDaggerSneaking(item.getKey());
				genDagger(item.getKey());
				continue;
			}
			if(item.getValue().get() instanceof LOTRSwordItem) {
				genHandheld(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				genSword(item.getKey());
				continue;
			}
			if(item.getValue().get() instanceof LOTRShieldItem) {
				try {
					getExistingFile(lotrLoc("item/"+item.getKey()));
					getExistingFile(lotrLoc("item/"+item.getKey()+"_blocking"));
				}catch(IllegalStateException e) {
					System.out.println("Missing shield model: "+item.getKey());
					System.out.println("Missing shield model: "+item.getKey()+"_blocking");
				}
				continue;
			}

			if(item.getValue().get() instanceof ExtendedBowItem) {
				genBow(item.getKey());
				continue;
			}

			if((item.getValue().get() instanceof LOTRPickaxeItem) || (item.getValue().get() instanceof LOTRShovelItem)
					|| (item.getValue().get() instanceof LOTRAxeItem)
					|| (item.getValue().get() instanceof LOTRHoeItem)
					|| (item.getValue().get() instanceof ExtendedThrowableAxeItem)) {
				genHandheld(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof ExtendedCommandHornItem) {
				genCommandHorn(item.getKey(), "item/"+item.getKey());
				continue;
			}
			if(item.getValue().get() instanceof ExtendedBrandingIronItem) {
				genBrandingIron(item.getKey(), "item/"+item.getKey());
				continue;
			}
			if(item.getValue().get() instanceof ExtendedSmithScrollItem) {
				genModifierScroll(item.getKey(), "item/"+item.getKey());
				continue;
			}
			if(item.getValue().get() instanceof ExtendedMCBannerItem) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/banner/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof ExtendedSilverCoin) {
				ItemModelBuilder builder = withExistingParent("lotr:"+item.getKey(), "lotrextended:item/template_coin");
				builder.texture("layer0", lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			try {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Didn't make ItemModel for: "+item.getKey());
		}
		for(String entity : ExtendedEntities.REGISTEREDNPCENTITIES) {
			withExistingParent("lotr:"+entity+"_spawn_egg", "item/template_spawn_egg");
		}
		for(String entity : ExtendedEntities.REGISTEREDANIMALENTITIES) {
			withExistingParent("lotr:"+entity+"_spawn_egg", "item/template_spawn_egg");
		}
		registerOverrides();
	}

	@Override
	public String getName() {
		return "Renewed Extended Item Model Provider";
	}

	private void genBow(String name) {
		String bow = "lotr:"+name;
//		String bowPulling0 = name+"_pulling_0";
//		String bowPulling1 = name+"_pulling_1";
//		String bowPulling2 = name+"_pulling_2";
		String bowhandheld = "lotr:"+name+"_handheld";
		String bowhandheldPulling0 = name+"_handheld_pulling_0";
		String bowhandheldPulling1 = name+"_handheld_pulling_1";
		String bowhandheldPulling2 = name+"_handheld_pulling_2";

		ItemModelBuilder builder_bow_handleld = gen(withExistingParent(bowhandheld, "lotrextended:item/template_bow"), false, lotrExtendedLoc("item/"+name+"_handheld"));
		ItemModelBuilder builder_bow_handleld_p0 = gen(withExistingParent("lotr:item/"+bowhandheldPulling0, bowhandheld), false, lotrExtendedLoc("item/"+name+"_handheld_pulling_0"));
		ItemModelBuilder builder_bow_handleld_p1 = gen(withExistingParent("lotr:item/"+bowhandheldPulling1, bowhandheld), false, lotrExtendedLoc("item/"+name+"_handheld_pulling_1"));
		ItemModelBuilder builder_bow_handleld_2 = gen(withExistingParent("lotr:item/"+bowhandheldPulling2, bowhandheld), false, lotrExtendedLoc("item/"+name+"_handheld_pulling_2"));
		ItemModelBuilder builder_bow = gen(withExistingParent(bow, "lotrextended:item/template_bow"), false, lotrExtendedLoc("item/"+name));
//		ItemModelBuilder builder_bow_p0 = gen(withExistingParent("lotr:item/"+bowPulling0, bow), false, lotrExtendedLoc("item/"+name+"_pulling_0"));
//		ItemModelBuilder builder_bow_p1 = gen(withExistingParent("lotr:item/"+bowPulling1, bow), false, lotrExtendedLoc("item/"+name+"_pulling_1"));
//		ItemModelBuilder builder_bow_p2 = gen(withExistingParent("lotr:item/"+bowPulling2, bow), false, lotrExtendedLoc("item/"+name+"_pulling_2"));
		builder_bow_handleld.override().model(getExistingFile(lotrLoc(bowhandheldPulling0))).predicate(ExtendedItemModelsProperties.pulling, 1);
		builder_bow_handleld.override().model(getExistingFile(lotrLoc(bowhandheldPulling1))).predicate(ExtendedItemModelsProperties.pulling, 1).predicate(ExtendedItemModelsProperties.pull, 0.65F);
		builder_bow_handleld.override().model(getExistingFile(lotrLoc(bowhandheldPulling2))).predicate(ExtendedItemModelsProperties.pulling, 1).predicate(ExtendedItemModelsProperties.pull, 0.9F);
//		builder_bow.override().model(getExistingFile(lotrLoc(bowPulling0))).predicate(ExtendedItemModelsProperties.pulling, 1);
//		builder_bow.override().model(getExistingFile(lotrLoc(bowPulling1))).predicate(ExtendedItemModelsProperties.pulling, 1).predicate(ExtendedItemModelsProperties.pull, 0.65F);
//		builder_bow.override().model(getExistingFile(lotrLoc(bowPulling2))).predicate(ExtendedItemModelsProperties.pulling, 1).predicate(ExtendedItemModelsProperties.pull, 0.9F);
	}

	private ItemModelBuilder genDrink(String name, String location) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "item/generated");
		builder.texture("liquid", lotrExtendedLoc(location+"_liquid"));
		builder.customLoader(ExtendedVesselModelBuilder::new);
		return builder;
	}

	private ItemModelBuilder genSword(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_handheld", "lotr:item/handheld_large");
		return gen(builder, false, lotrExtendedLoc("item/"+name+"_large"));
	}

	private ItemModelBuilder genSpear(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_handheld", "lotr:item/handheld_spear");
		builder.override().model(getExistingFile(lotrLoc("item/"+name+"_handheld_pulling"))).predicate(mcLoc("pulling"), 1);
		return gen(builder, false, lotrExtendedLoc("item/"+name+"_large"));
	}

	private ItemModelBuilder genSpearPulling(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_handheld_pulling", "lotr:item/handheld_spear_pulling");
		return gen(builder, false, lotrExtendedLoc("item/"+name+"_large"));
	}

	private ItemModelBuilder genDagger(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "lotr:item/handheld_dagger");
		builder.override().model(getExistingFile(lotrLoc("item/"+name+"_sneaking"))).predicate(lotrLoc("sneaking"), 1);
		return gen(builder, false, lotrExtendedLoc("item/"+name));
	}

	private ItemModelBuilder genDaggerSneaking(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_sneaking", "lotr:item/handheld_dagger_sneaking");
		return gen(builder, false, lotrExtendedLoc("item/"+name));
	}

	private ItemModelBuilder genGenerated(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "item/generated");
		return gen(builder, false, layers);
	}

	private ItemModelBuilder genGenerated(String name, boolean canFail, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "item/generated");
		return gen(builder, canFail, layers);
	}

	private ItemModelBuilder genHandheld(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "item/handheld");
		return gen(builder, false, layers);
	}

	private ItemModelBuilder genCommandHorn(String name, String location) {
		ItemModelBuilder builder = genGenerated(name, lotrExtendedLoc(location));
		ItemModelBuilder builder_halt = genGenerated(name+"_halt", lotrExtendedLoc(location+"_halt"));
		ItemModelBuilder builder_reddy = genGenerated(name+"_reddy", lotrExtendedLoc(location+"_ready"));
		ItemModelBuilder builder_summon = genGenerated(name+"_summon", lotrExtendedLoc(location+"_summon"));
		builder.override().model(builder_halt).predicate(ExtendedItemModelsProperties.hornType, 1);
		builder.override().model(builder_reddy).predicate(ExtendedItemModelsProperties.hornType, 2);
		builder.override().model(builder_summon).predicate(ExtendedItemModelsProperties.hornType, 3);
		return builder;
	}

	private ItemModelBuilder genModifierScroll(String name, String location) {
		ItemModelBuilder builder = genGenerated(name, lotrExtendedLoc(location+"_wooden"));//TODO maybe have a "basic" modifier scroll texture in the future?
		ItemModelBuilder builder_wooden = genGenerated(name+"_wooden", lotrExtendedLoc(location+"_wooden"));
		ItemModelBuilder builder_bronze = genGenerated(name+"_bronze", lotrExtendedLoc(location+"_bronze"));
		ItemModelBuilder builder_silver = genGenerated(name+"_silver", lotrExtendedLoc(location+"_silver"));
		ItemModelBuilder builder_golden = genGenerated(name+"_golden", lotrExtendedLoc(location+"_golden"));
		builder.override().model(builder_wooden).predicate(ExtendedItemModelsProperties.scroll_type, 0);
		builder.override().model(builder_bronze).predicate(ExtendedItemModelsProperties.scroll_type, 1);
		builder.override().model(builder_silver).predicate(ExtendedItemModelsProperties.scroll_type, 2);
		builder.override().model(builder_golden).predicate(ExtendedItemModelsProperties.scroll_type, 3);
		return builder_wooden;
	}

	private ItemModelBuilder genBrandingIron(String name, String location) {
		ItemModelBuilder builder = genHandheld(name, lotrExtendedLoc(location));
		ItemModelBuilder builder_iron_hot = genHandheld(name+"_hot", lotrExtendedLoc(location+"_hot"));
		builder.override().model(builder_iron_hot).predicate(ExtendedItemModelsProperties.heated, 1);
		return builder;
	}

	@SuppressWarnings("unchecked")
	private ItemModelBuilder gen(ItemModelBuilder builder, boolean canFail, ResourceLocation... layers) {
		try {
			for (int i = 0; i < layers.length; i++) {
				builder = builder.texture("layer" + i, layers[i]);
			}
		}catch(IllegalArgumentException e) {
			if(!canFail) {
				for (int i = 0; i < layers.length; i++) {
					try {
						Field f1 = ModelBuilder.class.getDeclaredField("textures");
						f1.setAccessible(true);
						((Map<String, String>)f1.get(builder)).put("layer" + i, layers[i].toString());
						System.out.println("Missing texture for: "+layers[i]);
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}else {
				throw new IllegalArgumentException(e);
			}
		}
		return builder;
	}
}
