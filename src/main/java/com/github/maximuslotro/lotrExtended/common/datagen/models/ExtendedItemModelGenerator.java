package com.github.maximuslotro.lotrExtended.common.datagen.models;

import java.util.Map.Entry;

import com.github.maximuslotro.lotrExtended.client.render.item.ExtendedItemModelsProperties;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.item.DaggerItem;
import lotr.common.item.ExtendedBrandingIronItem;
import lotr.common.item.ExtendedCommandHornItem;
import lotr.common.item.ExtendedSilverCoin;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRAxeItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.item.LOTRPickaxeItem;
import lotr.common.item.LOTRShovelItem;
import lotr.common.item.LOTRSwordItem;
import lotr.common.item.SpearItem;
import net.minecraft.data.DataGenerator;
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

	public ResourceLocation lotrLoc(String name) {
        return new ResourceLocation("lotr", name);
    }

	@Override
	protected void registerModels() {
		genGenerated(ExtendedItems.MORGUL_LANTERN.getId().getPath(), lotrLoc("item/"+ExtendedItems.MORGUL_LANTERN.getId().getPath()));
		for(Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDITEMS.entrySet()) {
			if(item.getKey().contains("torch")) {
				genGenerated(item.getKey(), lotrLoc("block/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRArmorItem) {
				genGenerated(item.getKey(), lotrLoc("item/"+item.getKey()));
				continue;
				
			}
			if(item.getValue().get() instanceof SpearItem) {
				genHandheld(item.getKey(), lotrLoc("item/"+item.getKey()));
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
				genHandheld(item.getKey(), lotrLoc("item/"+item.getKey()));
				genSword(item.getKey());
				continue;
			}
			if(item.getValue().get() instanceof LOTRPickaxeItem) {
				genHandheld(item.getKey(), lotrLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRShovelItem) {
				genHandheld(item.getKey(), lotrLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRAxeItem) {
				genHandheld(item.getKey(), lotrLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRHoeItem ) {
				genHandheld(item.getKey(), lotrLoc("item/"+item.getKey()));
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
			if(item.getValue().get() instanceof ExtendedSilverCoin ) {
				//TODO add first/third person placement gen
				//continue using manual generation for coins for now
				continue;
			}
			try {
				genGenerated(item.getKey(), lotrLoc("item/"+item.getKey()));
				continue;
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Didn't make ItemModel for: "+item.getKey());
		}
		for(String entity : ExtendedEntities.REGISTEREDENTITIES) {
			withExistingParent("lotr:"+entity+"_spawn_egg", "item/template_spawn_egg");
		}
	}

	@Override
	public String getName() {
		return "Renewed Extended Item Model Provider";
	}

	private ItemModelBuilder genSword(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_handheld", "lotr:item/handheld_large");
		return gen(builder, lotrLoc("item/"+name+"_large"));
	}

	private ItemModelBuilder genSpear(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_handheld", "lotr:item/handheld_spear");
		builder.override().model(getExistingFile(lotrLoc("item/"+name+"_handheld_pulling"))).predicate(new ResourceLocation("pulling"), 1);
		return gen(builder, lotrLoc("item/"+name+"_large"));
	}

	private ItemModelBuilder genSpearPulling(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_handheld_pulling", "lotr:item/handheld_spear_pulling");
		return gen(builder, lotrLoc("item/"+name+"_large"));
	}

	private ItemModelBuilder genDagger(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "lotr:item/handheld_dagger");
		builder.override().model(getExistingFile(lotrLoc("item/"+name+"_sneaking"))).predicate(new ResourceLocation("lotr:sneaking"), 1);
		return gen(builder, lotrLoc("item/"+name));
	}

	private ItemModelBuilder genDaggerSneaking(String name) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name+"_sneaking", "lotr:item/handheld_dagger_sneaking");
		return gen(builder, lotrLoc("item/"+name));
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
		ResourceLocation horn = lotrLoc(location);
		ResourceLocation horn_summon = lotrLoc(location+"_summon");
		ResourceLocation horn_halt = lotrLoc(location+"_halt");
		ItemModelBuilder builder_halt = gen(withExistingParent("lotr:"+name+"_halt", "item/generated"), horn_halt);
		ItemModelBuilder builder_reddy = gen(withExistingParent("lotr:"+name+"_reddy", "item/generated"), horn_halt);
		ItemModelBuilder builder_summon = gen(withExistingParent("lotr:"+name+"_summon", "item/generated"), horn_summon);
		ItemModelBuilder builder = gen(withExistingParent("lotr:"+name, "item/generated"), horn);
		builder.override().model(builder_halt).predicate(ExtendedItemModelsProperties.hornType, 1.0F);
		builder.override().model(builder_reddy).predicate(ExtendedItemModelsProperties.hornType, 2.0F);
		builder.override().model(builder_summon).predicate(ExtendedItemModelsProperties.hornType, 3.0F);
		return builder;
	}

	private ItemModelBuilder genBrandingIron(String name, String location) {
		ResourceLocation iron = lotrLoc(location);
		ResourceLocation iron_hot = lotrLoc(location+"_hot");
		ItemModelBuilder builder_iron_hot = gen(withExistingParent("lotr:"+name+"_hot", "item/generated"), iron_hot);
		ItemModelBuilder builder = gen(withExistingParent("lotr:"+name, "item/generated"), iron);
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
