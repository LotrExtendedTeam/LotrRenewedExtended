package lotr.common.datagen;

import java.util.Map.Entry;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import lotr.common.block.LOTRPillarBlock;
import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBlockTagGenerator extends BlockTagsProvider {
	
    public static final ITag.INamedTag<Block> PILLARS = BlockTags.bind("lotr:pillars");
 
    
	public ExtendedBlockTagGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, LotrExtendedMod.MODID, existingFileHelper);
	}

	@Override
    protected void addTags() {
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(block.getValue().get() instanceof LOTRPillarBlock) {
				tag(PILLARS).add(block.getValue().get());
			}
		}
	}

	@Override
	public String getName() {
		return "Extended Renewed Block Tags Generator";
	}
}
