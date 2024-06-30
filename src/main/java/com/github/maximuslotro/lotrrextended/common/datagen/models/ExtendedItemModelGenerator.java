package com.github.maximuslotro.lotrrextended.common.datagen.models;

import java.util.Map.Entry;

import com.github.maximuslotro.lotrrextended.client.render.item.ExtendedItemModelsProperties;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBrandingIronItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMCBannerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedSilverCoin;

import lotr.common.block.ChandelierBlock;
import lotr.common.block.LOTRDoorBlock;
import lotr.common.block.LOTRSaplingBlock;
import lotr.common.block.LOTRStandingSignBlock;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.item.*;
import net.minecraft.block.DoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
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

	@Override
	protected void registerModels() {
		genGenerated(ExtendedItems.MORGUL_LANTERN.getId().getPath(), lotrExtendedLoc("item/"+ExtendedItems.MORGUL_LANTERN.getId().getPath()));
		genGenerated(ExtendedItems.WEAPON_RACK.getId().getPath(), lotrExtendedLoc("item/"+ExtendedItems.WEAPON_RACK.getId().getPath()));
		for(Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDBLOCKITEMS.entrySet()) {
			if(((BlockItem)item.getValue().get()).getBlock() instanceof LOTRDoorBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof DoorBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof ChandelierBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof LOTRSaplingBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("block/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()).getBlock() instanceof LOTRStandingSignBlock) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(((BlockItem)item.getValue().get()) instanceof FallenLeavesItem) {
				try {
					genGenerated(item.getKey(), lotrExtendedLoc("block/"+item.getKey().substring(7)));
				}catch(IllegalArgumentException e) {
					genGenerated(item.getKey(), lotrExtendedLoc("block/"+item.getKey().substring(7)+"_0"));
				}
				continue;
			}
		}
		for(Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDITEMS.entrySet()) {
			if(item.getKey().contains("torch")) {
				genGenerated(item.getKey(), lotrExtendedLoc("block/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRArmorItem) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
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
				continue;
			}

			// ignore bows as they have their own custom models
			if(item.getValue().get() instanceof ExtendedBowItem) {
				continue;
			}

			if(item.getValue().get() instanceof LOTRPickaxeItem) {
				genHandheld(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRShovelItem) {
				genHandheld(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRAxeItem) {
				genHandheld(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRHoeItem ) {
				genHandheld(item.getKey(), lotrExtendedLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof ExtendedCommandHornItem ) {
				genCommandHorn(item.getKey(), "item/"+item.getKey());
				continue;
			}
			if(item.getValue().get() instanceof ExtendedBrandingIronItem ) {
				genBrandingIron(item.getKey(), "item/"+item.getKey());
				continue;
			}
			if(item.getValue().get() instanceof ExtendedMCBannerItem ) {
				genGenerated(item.getKey(), lotrExtendedLoc("item/banner/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof ExtendedSilverCoin ) {
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
		for(String entity : ExtendedEntities.REGISTEREDMOBENTITIES) {
			withExistingParent("lotr:"+entity+"_spawn_egg", "item/template_spawn_egg");
		}
	}

	@Override
	public String getName() {
		return "Renewed Extended Item Model Provider";
	}

	private ItemModelBuilder genSword(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_handheld", "lotr:item/handheld_large");
		return gen(builder, lotrExtendedLoc("item/"+name+"_large"));
	}

	private ItemModelBuilder genSpear(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_handheld", "lotr:item/handheld_spear");
		builder.override().model(getExistingFile(lotrLoc("item/"+name+"_handheld_pulling"))).predicate(mcLoc("pulling"), 1);
		return gen(builder, lotrExtendedLoc("item/"+name+"_large"));
	}

	private ItemModelBuilder genSpearPulling(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_handheld_pulling", "lotr:item/handheld_spear_pulling");
		return gen(builder, lotrExtendedLoc("item/"+name+"_large"));
	}

	private ItemModelBuilder genDagger(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "lotr:item/handheld_dagger");
		builder.override().model(getExistingFile(lotrLoc("item/"+name+"_sneaking"))).predicate(lotrLoc("sneaking"), 1);
		return gen(builder, lotrExtendedLoc("item/"+name));
	}

	private ItemModelBuilder genDaggerSneaking(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_sneaking", "lotr:item/handheld_dagger_sneaking");
		return gen(builder, lotrExtendedLoc("item/"+name));
	}

	private ItemModelBuilder genGenerated(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "item/generated");
		return gen(builder, layers);
	}

	private ItemModelBuilder genHandheld(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "item/handheld");
		return gen(builder, layers);
	}

	private ItemModelBuilder genCommandHorn(String name, String location) {
		ResourceLocation horn = lotrExtendedLoc(location);
		ResourceLocation horn_summon = lotrExtendedLoc(location+"_summon");
		ResourceLocation horn_halt = lotrExtendedLoc(location+"_halt");
		ItemModelBuilder builder_halt = genGenerated(name+"_halt", horn_halt);
		ItemModelBuilder builder_reddy = genGenerated(name+"_reddy", horn_halt);
		ItemModelBuilder builder_summon = genGenerated(name+"_summon", horn_summon);
		ItemModelBuilder builder = genGenerated(name, horn);
		builder.override().model(builder_halt).predicate(ExtendedItemModelsProperties.hornType, 1.0F);
		builder.override().model(builder_reddy).predicate(ExtendedItemModelsProperties.hornType, 2.0F);
		builder.override().model(builder_summon).predicate(ExtendedItemModelsProperties.hornType, 3.0F);
		return builder;
	}

	private ItemModelBuilder genBrandingIron(String name, String location) {
		ResourceLocation iron = lotrExtendedLoc(location);
		ResourceLocation iron_hot = lotrExtendedLoc(location+"_hot");
		ItemModelBuilder builder_iron_hot = genHandheld(name+"_hot", iron_hot);
		ItemModelBuilder builder = genHandheld(name, iron);
		builder.override().model(builder_iron_hot).predicate(ExtendedItemModelsProperties.heated, 1.0F);
		return builder;
	}

	private ItemModelBuilder gen(ItemModelBuilder builder, ResourceLocation... layers) {
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}
}
