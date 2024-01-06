package lotr.common.datagen;

import java.util.Map.Entry;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import lotr.common.init.ExtendedItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedItemGenerator extends ItemModelProvider {

	public ExtendedItemGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, LotrExtendedMod.MODID, existingFileHelper);
	}

	public ResourceLocation lotrLoc(String name) {
        return new ResourceLocation("lotr", name);
    }

	@Override
	protected void registerModels() {
		generated(ExtendedItems.MORGUL_LANTERN.getId().getPath(), lotrLoc("item/"+ExtendedItems.MORGUL_LANTERN.getId().getPath()));
		for(Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDITEMS.entrySet()) {
			if(item.getKey().contains("torch")) {
				generated(item.getKey(), lotrLoc("block/"+item.getKey()));
				continue;
			}
			System.out.println("Didn't make ItemModel for: "+item.getKey());
		}
	}

	@Override
	public String getName() {
		return "Renewed Extended Item Model Provider";
	}

	private ItemModelBuilder generated(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent("lotr:"+name, "item/generated");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}
}
