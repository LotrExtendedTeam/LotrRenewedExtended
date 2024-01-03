package lotr.common.datagen;

import java.util.Map.Entry;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.BundleBlock;
import lotr.common.block.ExtendedBerryBushBlock;
import lotr.common.block.FactionCraftingTableBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStoneBlock;
import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.block.LOTRWallTorchBlock;
import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBlockstateGenerator extends BlockStateProvider {

	public ExtendedBlockstateGenerator(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(block.getValue().get() instanceof BundleBlock) {}
			if(block.getValue().get() instanceof AxialSlabBlock) {}
			if(block.getValue().get() instanceof LOTRStairsBlock) {}
			if(block.getValue().get() instanceof LOTRWallBlock) {}
			if(block.getValue().get() instanceof LOTRStoneBlock) {}
			if(block.getValue().get() instanceof LOTRPillarBlock) {}
			if(block.getValue().get() instanceof ExtendedBerryBushBlock) {}
			if(block.getValue().get() instanceof FactionCraftingTableBlock) {}
			if(block.getValue().get() instanceof LOTRTorchBlock) {}
			if(block.getValue().get() instanceof LOTRWallTorchBlock) {}
		}
	}

	@Override
	public String getName() {
		return "Renewed Extended Blockstate Provider";
	}
}
