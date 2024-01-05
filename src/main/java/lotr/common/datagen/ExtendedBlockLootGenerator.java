package lotr.common.datagen;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBlockLootGenerator extends BlockLootTables  {

	@Override
    protected void addTables() {
    }
	
	@Override
    protected Iterable<Block> getKnownBlocks() {
        return ExtendedBlocks.REGISTEREDBLOCKS.values().stream().map(RegistryObject::get)::iterator;
    }
}
