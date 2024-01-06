package lotr.common.datagen;

import java.util.Map.Entry;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.ExtendedBerryBushBlock;
import lotr.common.block.ExtendedBerryPlantBlock;
import lotr.common.block.ExtendedDirectionalBundleBlock;
import lotr.common.block.ExtendedPackedBundleBlock;
import lotr.common.block.FactionCraftingTableBlock;
import lotr.common.block.LOTRBlockStates;
import lotr.common.block.LOTROreBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStoneBlock;
import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.block.LOTRWallTorchBlock;
import lotr.common.block.MineralBlock;
import lotr.common.block.NonWaterloggableLanternBlock;
import lotr.common.block.VerticalOnlySlabBlock;
import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Direction.Axis;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
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
			if(block.getValue().get() instanceof ExtendedDirectionalBundleBlock) {
				genCubeBottomTopSideBlockItem(block.getValue().get(), lotrLoc("block/"+block.getKey()+"_side"), lotrLoc("block/"+block.getKey()+"_bottom"), lotrLoc("block/"+block.getKey()+"_top"));
				continue;
			}
			if(block.getValue().get() instanceof ExtendedPackedBundleBlock) {
				fourTexturedRandomBlockItem(block.getValue().get(), "block/"+block.getKey(), 4);
				continue;
			}
			if(block.getValue().get() instanceof AxialSlabBlock && !(block.getValue().get() instanceof VerticalOnlySlabBlock)) {
				if(block.getKey().contains("_slab")&&!block.getKey().contains("_pillar")) {
					if(block.getKey().contains("packed_fur")) {
						genSlabBlockItem((SlabBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_1"));
						continue;
					}
					genSlabBlockItem((SlabBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
					continue;
				}
			}
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
			if(block.getValue().get() instanceof ExtendedBerryPlantBlock) {
				genPlantAge3Block(block.getValue().get(), "block/"+block.getKey());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedBerryBushBlock) {
				genBushAge3Block(block.getValue().get(), "block/"+block.getKey());
				continue;
			}
			if(block.getValue().get() instanceof LOTROreBlock) {
				genCubeBlockItem(block.getValue().get(), lotrLoc("block/"+block.getKey()));
				continue;
			}
			if(block.getValue().get() instanceof LOTRStoneBlock) {
				if(block.getKey()=="cobbled_drystone") {
					genCubeBlockItem(block.getValue().get(), lotrLoc("block/drystone"));
					continue;
				}
				genCubeBlockItem(block.getValue().get(), lotrLoc("block/"+block.getKey()));
				continue;
			}
			if(block.getValue().get() instanceof MineralBlock) {
				try {
					genCubeBlockItem(block.getValue().get(),"block/"+block.getKey());
				}catch(IllegalArgumentException e) {
					System.out.println(block.getKey()+" is not CubeBlockItem!");
					genEndSideNonRotatableBlockItem(block.getValue().get(),"block/"+block.getKey());
				}
				continue;
			}
			if(block.getValue().get() instanceof LOTRPillarBlock) {}
			if(block.getValue().get() instanceof FactionCraftingTableBlock) {
				genTopBottomTwoSidedNonRotatableBlockItem(block.getValue().get(), "block/"+block.getKey());
				continue;
			}
			if(block.getValue().get() instanceof LOTRTorchBlock) {
				genTorchBlockItem(block.getValue().get(), lotrLoc("block/"+block.getKey()));
				continue;
			}
			if(block.getValue().get() instanceof LOTRWallTorchBlock) {
				if(block.getKey().contains("_wall")) {
					genTorchWallBlock(block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
					continue;
				}
			}
			if(block.getValue().get() instanceof NonWaterloggableLanternBlock) {
				genHangingLanternBlockItem((NonWaterloggableLanternBlock) block.getValue().get(), "block/"+block.getKey());
				continue;
			}
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

	private void genSlabBlockItem(SlabBlock slab, ResourceLocation texture) {
		ModelFile slabModel = models().slab("lotr:" + slab.getRegistryName().getPath(), texture, texture, texture);
		ModelFile slabModelTop = models().slabTop("lotr:" + slab.getRegistryName().getPath()+"_top", texture, texture, texture);
		ModelFile slabDouble = models().getExistingFile(texture);

		getVariantBuilder(slab)
		.forAllStatesExcept(state -> {
			Axis axis = state.getValue(LOTRBlockStates.SLAB_AXIS);
			SlabType type = state.getValue(AxialSlabBlock.TYPE);
            boolean uvlock = axis != Axis.Y;
            return ConfiguredModel.builder()
                    .modelFile(type == SlabType.DOUBLE ? slabDouble : (type == SlabType.BOTTOM ? slabModel : slabModelTop))
                    .rotationX(axis == Axis.Z ? 270 : (axis == Axis.X ? 90 : 0))
                    .rotationY(axis == Axis.X ? 90 : 0)
                    .uvLock(axis != Axis.Y)
                    .build();
        }, StairsBlock.WATERLOGGED);
		itemModels().getBuilder("lotr:" + slab.getRegistryName().getPath()).parent(slabModel);
	}

	private void genHangingLanternBlockItem(NonWaterloggableLanternBlock lantern, String name) {
		ResourceLocation lamp = lotrLoc(name);
		ResourceLocation fire = lotrLoc(name+"_fire");
		ResourceLocation chain = lotrLoc(name+"_chain");
		ResourceLocation parent = lotrLoc("block/template_lamp");
		ResourceLocation parentHanging = lotrLoc("block/template_lamp_hanging");
		ModelFile lanternModel = models().withExistingParent("lotr:"+lantern.getRegistryName().getPath(), parent)
                .texture("lamp", lamp)
                .texture("fire", fire);
		ModelFile hangingModel = models().withExistingParent("lotr:"+lantern.getRegistryName().getPath()+"_hanging", parentHanging)
                .texture("lamp", lamp)
                .texture("fire", fire)
                .texture("chain", chain);
		getVariantBuilder(lantern)
		.forAllStatesExcept(state -> {
			Boolean hanging = state.getValue(NonWaterloggableLanternBlock.HANGING);
            return ConfiguredModel.builder()
                    .modelFile(hanging ? hangingModel : lanternModel)
                    .build();
        }, StairsBlock.WATERLOGGED);
	}

	private void genWallBlockItem(WallBlock wall, ResourceLocation texture) {
		String name = wall.getRegistryName().toString();
		wallBlock(wall, texture);
		itemModels().wallInventory("lotr:" + wall.getRegistryName().getPath(), texture);
	}

	private void genTorchBlockItem(Block torch, ResourceLocation texture) {
		ModelFile cubeModel = models().torch("lotr:"+torch.getRegistryName().getPath(), texture);
		simpleBlock(torch, cubeModel);
	}

	private void genTorchWallBlock(Block torch, ResourceLocation texture) {
		ModelFile cubeModel = models().torchWall("lotr:"+torch.getRegistryName().getPath(), texture);
		simpleBlock(torch, cubeModel);
	}

	private void genCubeBottomTopSideBlockItem(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
		ModelFile cubeModel = models().cubeBottomTop("lotr:"+block.getRegistryName().getPath(), side, bottom, top);
		directionalBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genEndSideNonRotatableBlockItem(Block block, String sidesBase) {
		ModelFile cubeModel = models().cubeColumn("lotr:"+block.getRegistryName().getPath(), lotrLoc(sidesBase+"_side"), lotrLoc(sidesBase+"_end"));
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genTopBottomTwoSidedNonRotatableBlockItem(Block block, String sidesBase) {
		ResourceLocation down = lotrLoc(sidesBase+"_bottom");
		ResourceLocation up = lotrLoc(sidesBase+"_top");
		ResourceLocation north = lotrLoc(sidesBase+"_side_1");
		ResourceLocation south = lotrLoc(sidesBase+"_side_1");
		ResourceLocation east = lotrLoc(sidesBase+"_side_2");
		ResourceLocation west = lotrLoc(sidesBase+"_side_2");
		ModelFile cubeModel = models().cube("lotr:"+block.getRegistryName().getPath(), down, up, north, south, east, west);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genTopBottomFourSidedNonRotatableBlockItem(Block block, String sidesBase) {
		ResourceLocation down = lotrLoc(sidesBase+"_bottom");
		ResourceLocation up = lotrLoc(sidesBase+"_top");
		ResourceLocation north = lotrLoc(sidesBase+"_side_north");
		ResourceLocation south = lotrLoc(sidesBase+"_side_south");
		ResourceLocation east = lotrLoc(sidesBase+"_side_east");
		ResourceLocation west = lotrLoc(sidesBase+"_side_west");
		ModelFile cubeModel = models().cube("lotr:"+block.getRegistryName().getPath(), down, up, north, south, east, west);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genCubeBlockItem(Block block, String allSides) {
		genCubeBlockItem(block, lotrLoc(allSides));
	}
	
	private void genCubeBlockItem(Block block, ResourceLocation allSides) {
		ModelFile cubeModel = models().cubeAll("lotr:"+block.getRegistryName().getPath(), allSides);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void fourTexturedRandomBlockItem(Block block, String location, int numVariants) {
		if(numVariants==4) {
			ConfiguredModel variant1 = new ConfiguredModel(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_1", new ResourceLocation("lotr",location+"_1")));
			ConfiguredModel variant2 = new ConfiguredModel(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_2", new ResourceLocation("lotr",location+"_2")));
			ConfiguredModel variant3 = new ConfiguredModel(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_3", new ResourceLocation("lotr",location+"_3")));
			ConfiguredModel variant4 = new ConfiguredModel(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_4", new ResourceLocation("lotr",location+"_4")));
			getVariantBuilder(block).partialState().setModels(variant1, variant2, variant3, variant4);
			itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(variant1.model);
		}
	}
	
	private void genPlantAge3Block(Block block, String name) {
		getVariantBuilder(block)
        .partialState().with(BlockStateProperties.AGE_3, 0)
            .modelForState().modelFile(models().cross("lotr:"+block.getRegistryName().getPath()+"_age0", lotrLoc(name+"_0"))).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 1)
            .modelForState().modelFile(models().cross("lotr:"+block.getRegistryName().getPath()+"_age1", lotrLoc(name+"_1"))).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 2)
            .modelForState().modelFile(models().cross("lotr:"+block.getRegistryName().getPath()+"_age2", lotrLoc(name+"_2"))).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 3)
            .modelForState().modelFile(models().cross("lotr:"+block.getRegistryName().getPath()+"_age3", lotrLoc(name+"_3"))).addModel();
	}

	private void genBushAge3Block(Block block, String name) {
		ModelFile empty = models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_empty", lotrLoc(name+"_empty"));
		ModelFile full = models().cubeAll("lotr:"+block.getRegistryName().getPath(), lotrLoc(name));
		getVariantBuilder(block)
        .partialState().with(BlockStateProperties.AGE_3, 0)
            .modelForState().modelFile(empty).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 1)
            .modelForState().modelFile(empty).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 2)
            .modelForState().modelFile(empty).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 3)
            .modelForState().modelFile(full).addModel();
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(full);
	}
}
