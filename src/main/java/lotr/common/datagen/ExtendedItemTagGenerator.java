package lotr.common.datagen;


import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedItemTagGenerator extends ItemTagsProvider {

    public static final ITag.INamedTag<Item> PILLARS = ItemTags.bind("lotr:pillars");

	public ExtendedItemTagGenerator(DataGenerator generator, BlockTagsProvider blockprovider, ExistingFileHelper existingFileHelper) {
		super(generator, blockprovider, LotrExtendedMod.MODID, existingFileHelper);
	}

	@Override
    protected void addTags() {
		this.copy(ExtendedBlockTagGenerator.PILLARS, ExtendedItemTagGenerator.PILLARS);
	}

	@Override
	public String getName() {
		return "Extended Renewed Item Tags Generator";
	}
}
