package lotr.common.datagen.models;

import java.util.Map.Entry;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.item.ExtendedSilverCoin;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRAxeItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.item.LOTRPickaxeItem;
import lotr.common.item.LOTRShovelItem;
import lotr.common.item.LOTRSwordItem;
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
			if(item.getValue().get() instanceof LOTRSwordItem) {
				genGenerated(item.getKey(), lotrLoc("item/"+item.getKey()));
				genSword(item.getKey());
				continue;
			}
			if(item.getValue().get() instanceof LOTRPickaxeItem) {
				genGenerated(item.getKey(), lotrLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRShovelItem) {
				genGenerated(item.getKey(), lotrLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRAxeItem) {
				genGenerated(item.getKey(), lotrLoc("item/"+item.getKey()));
				continue;
			}
			if(item.getValue().get() instanceof LOTRHoeItem ) {
				genGenerated(item.getKey(), lotrLoc("item/"+item.getKey()));
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

	private ItemModelBuilder genGenerated(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "item/generated");
		return gen(builder, layers);
	}

	private ItemModelBuilder genHandheld(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "item/handheld");
		return gen(builder, layers);
	}

	private ItemModelBuilder gen(ItemModelBuilder builder, ResourceLocation... layers) {
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}
}
