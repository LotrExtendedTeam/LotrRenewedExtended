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
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBlockstateGenerator extends BlockStateProvider {

	public ExtendedBlockstateGenerator(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
	}

	public ResourceLocation lotrLoc(String name) {
        return new ResourceLocation("lotr", name);
    }

	@Override
	protected void registerStatesAndModels() {
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(block.getValue().get() instanceof BundleBlock) {}
			if(block.getValue().get() instanceof AxialSlabBlock) {}
			if(block.getValue().get() instanceof LOTRStairsBlock) {
				if(block.getKey().contains("_stairs")) {
					if(block.getKey().contains("packed_fur")) {
						genStairsBlockItem((StairsBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)+"_1"));
						continue;
					}
					genStairsBlockItem((StairsBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)));
					continue;
				}
			}
			if(block.getValue().get() instanceof LOTRWallBlock) {
				if(block.getKey().contains("_wall")) {
					if(block.getKey().contains("packed_fur")) {
						genWallBlockItem((WallBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_1"));
						continue;
					}
					genWallBlockItem((WallBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
					continue;
				}
			}
			if(block.getValue().get() instanceof LOTRStoneBlock) {}
			if(block.getValue().get() instanceof LOTRPillarBlock) {}
			if(block.getValue().get() instanceof ExtendedBerryBushBlock) {}
			if(block.getValue().get() instanceof FactionCraftingTableBlock) {}
			if(block.getValue().get() instanceof LOTRTorchBlock) {}
			if(block.getValue().get() instanceof LOTRWallTorchBlock) {}
			System.out.println("Didn't make BlockStateOrModel for: "+block.getKey());
		}
	}

	@Override
	public String getName() {
		return "Renewed Extended Blockstate Provider";
	}

	private void genStairsBlockItem(StairsBlock stairs, ResourceLocation texture) {
		String name = stairs.getRegistryName().toString();
		ModelFile stairsModel = models().stairs(name, texture, texture, texture);
		ModelFile stairsModelInner = models().stairsInner(name + "_inner", texture, texture, texture);
		ModelFile stairsModelOuter = models().stairsOuter(name + "_outer", texture, texture, texture);
		stairsBlock(stairs, stairsModel, stairsModelInner, stairsModelOuter);
		itemModels().getBuilder("lotr:" + stairs.getRegistryName().getPath()).parent(stairsModel);
	}

	private void genWallBlockItem(WallBlock wall, ResourceLocation texture) {
		String name = wall.getRegistryName().toString();
		wallBlock(wall, texture);
		itemModels().wallInventory("lotr:" + wall.getRegistryName().getPath(), texture);
	}
	}
}
