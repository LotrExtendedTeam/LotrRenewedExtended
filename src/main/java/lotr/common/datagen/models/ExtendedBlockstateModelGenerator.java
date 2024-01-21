package lotr.common.datagen.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.ExtendedBerryBushBlock;
import lotr.common.block.ExtendedBerryPlantBlock;
import lotr.common.block.ExtendedCakeBlock;
import lotr.common.block.ExtendedDirectionalBundleBlock;
import lotr.common.block.ExtendedGroundDirtBlock;
import lotr.common.block.ExtendedGroundFarmlandBlock;
import lotr.common.block.ExtendedGroundGrassBlock;
import lotr.common.block.ExtendedGroundPathBlock;
import lotr.common.block.ExtendedPackedBundleBlock;
import lotr.common.block.ExtendedRemainsBlock;
import lotr.common.block.ExtendedSpawnerBlock;
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
import net.minecraft.block.CakeBlock;
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

public class ExtendedBlockstateModelGenerator extends BlockStateProvider {

	private List<Block> registeredBlocks = new ArrayList<>();

	public ExtendedBlockstateModelGenerator(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
	}

	public ResourceLocation lotrLoc(String name) {
        return new ResourceLocation("lotr", name);
    }

	@Override
	protected void registerStatesAndModels() {
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(registeredBlocks.contains(block.getValue().get())) {continue;}
			if(block.getValue().get() instanceof ExtendedDirectionalBundleBlock) {
				genCubeBottomTopSideBlockItem(block.getValue().get(), lotrLoc("block/"+block.getKey()+"_side"), lotrLoc("block/"+block.getKey()+"_bottom"), lotrLoc("block/"+block.getKey()+"_top"));
				continue;
			}
			if(block.getValue().get() instanceof ExtendedPackedBundleBlock) {
				numberTexturedRandomBlockItem(block.getValue().get(), "block/"+block.getKey(), 4);
				continue;
			}
			if(block.getValue().get() instanceof ExtendedRemainsBlock) {
				numberTexturedRandomBlockItem(block.getValue().get(), "block/"+block.getKey(), 3);
				continue;
			}
			if(block.getValue().get() instanceof ExtendedBerryPlantBlock) {
				genPlantAge3Block(block.getValue().get(), "block/"+block.getKey());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedBerryBushBlock) {
				genBushAge3Block(block.getValue().get(), "block/"+block.getKey());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedGroundDirtBlock) {
				genRandomCubeBlockItem(block.getValue().get(), lotrLoc("block/"+block.getKey()));
				continue;
			}
			if(block.getValue().get() instanceof ExtendedGroundFarmlandBlock) {
				genFarmlandBlockItem((ExtendedGroundFarmlandBlock) block.getValue().get(), "block/"+block.getKey());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedGroundGrassBlock) {
				genGrassBlockItem((ExtendedGroundGrassBlock) block.getValue().get(), "block/"+block.getKey());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedGroundPathBlock) {
				genGrassPathBlockItem((ExtendedGroundPathBlock) block.getValue().get(), "block/"+block.getKey());
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
			if(block.getValue().get() instanceof LOTRPillarBlock) {
				genPillarBlockItem((LOTRPillarBlock) block.getValue().get(), "block/"+block.getKey());
				continue;
			}
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
			if(block.getValue().get() instanceof ExtendedSpawnerBlock) {
				registeredBlocks.add(block.getValue().get());
				ModelFile cubeModel = models().cubeAll("lotr:"+block.getValue().get().getRegistryName().getPath(), lotrLoc("block/spawner_block"));
				simpleBlock(block.getValue().get(), cubeModel);
				continue;
			}
			if(block.getValue().get() instanceof ExtendedCakeBlock) {
				genCakeBlock((CakeBlock) block.getValue().get(), lotrLoc("block/"+block.getKey()+"_bottom"), lotrLoc("block/"+block.getKey()+"_top"), lotrLoc("block/"+block.getKey()+"_side"), lotrLoc("block/"+block.getKey()+"_inner"));
				continue;
			}
		}
		//Only Add entries to this loop if they depend on other models we are generating or are supertypes of other block types
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(registeredBlocks.contains(block.getValue().get())) {continue;}
			if(block.getValue().get() instanceof AxialSlabBlock && !(block.getValue().get() instanceof VerticalOnlySlabBlock)) {
				if(block.getKey().contains("_slab")) {
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
					if(block.getKey().contains("smooth_stone_stairs")) {
						genStairsBlockItem((StairsBlock) block.getValue().get(), mcLoc("block/smooth_stone"));
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
			if(block.getValue().get() instanceof LOTROreBlock) {
				genCubeBlockItem(block.getValue().get(), lotrLoc("block/"+block.getKey()));
				continue;
			}
		}
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(!registeredBlocks.contains(block.getValue().get())) {
				System.out.println("Didn't make BlockStateOrModel for: "+block.getKey());
			}
		}
	}

	@Override
	public String getName() {
		return "Renewed Extended Blockstate Provider";
	}

	private void genStairsBlockItem(StairsBlock stairs, ResourceLocation texture) {
		registeredBlocks.add(stairs);
		String name = stairs.getRegistryName().toString();
		ModelFile stairsModel = models().stairs(name, texture, texture, texture);
		ModelFile stairsModelInner = models().stairsInner(name + "_inner", texture, texture, texture);
		ModelFile stairsModelOuter = models().stairsOuter(name + "_outer", texture, texture, texture);
		stairsBlock(stairs, stairsModel, stairsModelInner, stairsModelOuter);
		itemModels().getBuilder("lotr:" + stairs.getRegistryName().getPath()).parent(stairsModel);
	}

	private void genPillarBlockItem(LOTRPillarBlock pillarB, String name) {
		registeredBlocks.add(pillarB);
		ResourceLocation pillar = lotrLoc(name);
		ResourceLocation face = lotrLoc(name+"_face");
		ResourceLocation top = lotrLoc(name+"_top");
		ResourceLocation middle = lotrLoc(name+"_middle");
		ResourceLocation bottom = lotrLoc(name+"_bottom");
		
		ModelFile pillarBottom = models().cubeColumn("lotr:"+pillarB.getRegistryName().getPath()+"_bottom", bottom, face);
		ModelFile pillarMiddle = models().cubeColumn("lotr:"+pillarB.getRegistryName().getPath()+"_middle", middle, face);
		ModelFile pillarTop = models().cubeColumn("lotr:"+pillarB.getRegistryName().getPath()+"_top", top, face);
		ModelFile pillarBlock = models().cubeColumn("lotr:"+pillarB.getRegistryName().getPath(), pillar, face);
		
		getVariantBuilder(pillarB)
		.forAllStatesExcept(state -> {
			Axis axis = state.getValue(LOTRPillarBlock.AXIS);
			Boolean above = state.getValue(LOTRPillarBlock.ABOVE);
			Boolean below = state.getValue(LOTRPillarBlock.BELOW);
			ModelFile selectedfile = null;
			if(above) {
				if(below) {selectedfile = pillarMiddle;}
				else {selectedfile = pillarBottom;}}
			else {
				if(below) {selectedfile = pillarTop;}
				else {selectedfile = pillarBlock;}}
            return ConfiguredModel.builder()
                    .modelFile(selectedfile)
                    .rotationX(axis == Axis.Z ? 270 : (axis == Axis.X ? 90 : 0))
                    .rotationY(axis == Axis.X ? 90 : 0)
                    .build();
        });
		itemModels().getBuilder("lotr:"+pillarB.getRegistryName().getPath()).parent(pillarBlock);
	}

	private void genSlabBlockItem(SlabBlock slab, ResourceLocation texture) {
		registeredBlocks.add(slab);
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
                    .uvLock(uvlock)
                    .build();
        }, SlabBlock.WATERLOGGED);
		itemModels().getBuilder("lotr:" + slab.getRegistryName().getPath()).parent(slabModel);
	}

	private void genFarmlandBlockItem(ExtendedGroundFarmlandBlock extendedGroundFarmlandBlock, String name) {
		registeredBlocks.add(extendedGroundFarmlandBlock);
		ResourceLocation farmland = lotrLoc(name);
		ResourceLocation farmlandMoist = lotrLoc(name+"_moist");
		ResourceLocation dirt = lotrLoc("block/"+extendedGroundFarmlandBlock.getBase().get().getRegistryName().getPath());
		ResourceLocation parent = mcLoc("block/template_farmland");
		ModelFile dry = models().withExistingParent("lotr:"+extendedGroundFarmlandBlock.getRegistryName().getPath(), parent)
                .texture("dirt", dirt)
                .texture("top", farmland);
		ModelFile wet = models().withExistingParent("lotr:"+extendedGroundFarmlandBlock.getRegistryName().getPath()+"_moist", parent)
                .texture("dirt", dirt)
                .texture("top", farmlandMoist);
		getVariantBuilder(extendedGroundFarmlandBlock)
		.forAllStatesExcept(state -> {
			Integer dripping = state.getValue(ExtendedGroundFarmlandBlock.MOISTURE);
            return ConfiguredModel.builder()
                    .modelFile(dripping <7 ? dry : wet)
                    .build();
        });
		itemModels().getBuilder("lotr:" + extendedGroundFarmlandBlock.getRegistryName().getPath()).parent(dry);
	}

	private void genGrassBlockItem(ExtendedGroundGrassBlock extendedGroundFarmlandBlock, String name) {
		registeredBlocks.add(extendedGroundFarmlandBlock);
		ResourceLocation top = lotrLoc(name+"_top");
		ResourceLocation side = lotrLoc(name+"_side");
		ResourceLocation overlay = lotrLoc(name+"_side_overlay");
		ResourceLocation dirt = lotrLoc("block/"+extendedGroundFarmlandBlock.getBase().get().getRegistryName().getPath());
		ResourceLocation parent = mcLoc("block/grass_block");
		ModelFile grass = models().withExistingParent("lotr:"+extendedGroundFarmlandBlock.getRegistryName().getPath(), parent)
                .texture("particle", dirt)
                .texture("bottom", dirt)
                .texture("top", top)
                .texture("side", side)
                .texture("overlay", overlay);
		ResourceLocation top1 = lotrLoc(name+"_snow_top");
		ResourceLocation side1 = lotrLoc(name+"_snow_side");
		ResourceLocation parent1 = mcLoc("block/cube_bottom_top");
		ModelFile snow = models().withExistingParent("lotr:"+extendedGroundFarmlandBlock.getRegistryName().getPath(), parent1)
                .texture("particle", dirt)
                .texture("bottom", dirt)
                .texture("top", top1)
                .texture("side", side1);
		getVariantBuilder(extendedGroundFarmlandBlock)
		.forAllStatesExcept(state -> {
			Boolean snowTrue = state.getValue(ExtendedGroundGrassBlock.SNOWY);
			if(snowTrue) {
				return ConfiguredModel.builder().modelFile(snow).build();
			}
			else {
				return ConfiguredModel.allYRotations(grass, 0, false);
			}
        });
		itemModels().getBuilder("lotr:" + extendedGroundFarmlandBlock.getRegistryName().getPath()).parent(grass);
	}

	private void genGrassPathBlockItem(ExtendedGroundPathBlock extendedGroundFarmlandBlock, String name) {
		registeredBlocks.add(extendedGroundFarmlandBlock);
		ResourceLocation top = lotrLoc(name+"_top");
		ResourceLocation side = lotrLoc(name+"_side");
		ResourceLocation dirt = lotrLoc("block/"+extendedGroundFarmlandBlock.getBase().get().getRegistryName().getPath());
		ResourceLocation parent = mcLoc("block/grass_path");
		ModelFile grass = models().withExistingParent("lotr:"+extendedGroundFarmlandBlock.getRegistryName().getPath(), parent)
                .texture("particle", dirt)
                .texture("bottom", dirt)
                .texture("top", top)
                .texture("side", side);
		getVariantBuilder(extendedGroundFarmlandBlock)
		.forAllStatesExcept(state -> {
			return ConfiguredModel.allYRotations(grass, 0, false);
        });
		itemModels().getBuilder("lotr:" + extendedGroundFarmlandBlock.getRegistryName().getPath()).parent(grass);
	}

	private void genHangingLanternBlockItem(NonWaterloggableLanternBlock lantern, String name) {
		registeredBlocks.add(lantern);
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
        });
	}

	private void genCakeBlock(CakeBlock cake, ResourceLocation textureBottom, ResourceLocation textureTop, ResourceLocation textureSide, ResourceLocation textureInside) {
		registeredBlocks.add(cake);
		ModelFile cakeBlock = models().withExistingParent("lotr:" + cake.getRegistryName().getPath(), "block/cake")
				.texture("particle", textureSide).texture("bottom", textureBottom).texture("top", textureTop)
				.texture("side", textureSide);

		getVariantBuilder(cake).forAllStatesExcept(state -> {
			int bites = state.getValue(CakeBlock.BITES);
			return ConfiguredModel.builder().modelFile(bites == 0 ? cakeBlock : models()
					.withExistingParent("lotr:" + cake.getRegistryName().getPath() + bites,"block/cake_slice" + bites)
					.texture("particle", textureSide).texture("bottom", textureBottom)
					.texture("top", textureTop).texture("side", textureSide)
					.texture("inside", textureInside))
					.build();
		});
	}

	private void genWallBlockItem(WallBlock wall, ResourceLocation texture) {
		registeredBlocks.add(wall);
		wallBlock(wall, texture);
		itemModels().wallInventory("lotr:" + wall.getRegistryName().getPath(), texture);
	}

	private void genTorchBlockItem(Block torch, ResourceLocation texture) {
		registeredBlocks.add(torch);
		ModelFile cubeModel = models().torch("lotr:"+torch.getRegistryName().getPath(), texture);
		simpleBlock(torch, cubeModel);
	}

	private void genTorchWallBlock(Block torch, ResourceLocation texture) {
		registeredBlocks.add(torch);
		ModelFile cubeModel = models().torchWall("lotr:"+torch.getRegistryName().getPath(), texture);
		simpleBlock(torch, cubeModel);
	}

	private void genCubeBottomTopSideBlockItem(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeBottomTop("lotr:"+block.getRegistryName().getPath(), side, bottom, top);
		directionalBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genEndSideNonRotatableBlockItem(Block block, String sidesBase) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeColumn("lotr:"+block.getRegistryName().getPath(), lotrLoc(sidesBase+"_side"), lotrLoc(sidesBase+"_end"));
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genTopBottomTwoSidedNonRotatableBlockItem(Block block, String sidesBase) {
		registeredBlocks.add(block);
		ResourceLocation down = lotrLoc(sidesBase+"_bottom");
		ResourceLocation up = lotrLoc(sidesBase+"_top");
		ResourceLocation north = lotrLoc(sidesBase+"_side_1");
		ResourceLocation south = lotrLoc(sidesBase+"_side_1");
		ResourceLocation east = lotrLoc(sidesBase+"_side_2");
		ResourceLocation west = lotrLoc(sidesBase+"_side_2");
		ModelFile cubeModel = models().cube("lotr:"+block.getRegistryName().getPath(), down, up, north, south, east, west).texture("particle", north);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genTopBottomFourSidedNonRotatableBlockItem(Block block, String sidesBase) {
		registeredBlocks.add(block);
		ResourceLocation down = lotrLoc(sidesBase+"_bottom");
		ResourceLocation up = lotrLoc(sidesBase+"_top");
		ResourceLocation north = lotrLoc(sidesBase+"_side_north");
		ResourceLocation south = lotrLoc(sidesBase+"_side_south");
		ResourceLocation east = lotrLoc(sidesBase+"_side_east");
		ResourceLocation west = lotrLoc(sidesBase+"_side_west");
		ModelFile cubeModel = models().cube("lotr:"+block.getRegistryName().getPath(), down, up, north, south, east, west).texture("particle", north);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genCubeBlockItem(Block block, String allSides) {
		genCubeBlockItem(block, lotrLoc(allSides));
	}
	
	private void genCubeBlockItem(Block block, ResourceLocation allSides) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeAll("lotr:"+block.getRegistryName().getPath(), allSides);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genRandomCubeBlockItem(Block block, ResourceLocation allSides) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeAll("lotr:"+block.getRegistryName().getPath(), allSides);
		getVariantBuilder(block)
		.forAllStatesExcept(state -> {
			return ConfiguredModel.allRotations(cubeModel, false);
        });
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void numberTexturedRandomBlockItem(Block block, String location, int numVariants) {
		registeredBlocks.add(block);
		if(numVariants==4) {
			ConfiguredModel[] variant1 = ConfiguredModel.allYRotations(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_1", new ResourceLocation("lotr",location+"_1")), 0, false);
			ConfiguredModel[] variant2 = ConfiguredModel.allYRotations(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_2", new ResourceLocation("lotr",location+"_2")), 0, false);
			ConfiguredModel[] variant3 = ConfiguredModel.allYRotations(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_3", new ResourceLocation("lotr",location+"_3")), 0, false);
			ConfiguredModel[] variant4 = ConfiguredModel.allYRotations(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_4", new ResourceLocation("lotr",location+"_4")), 0, false);
			getVariantBuilder(block).partialState().addModels(variant1).addModels(variant2).addModels(variant3).addModels(variant4);
			itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_1", new ResourceLocation("lotr",location+"_1")));
			return;
		}
		if(numVariants==3) {
			ConfiguredModel[] variant1 = ConfiguredModel.allYRotations(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_1", new ResourceLocation("lotr",location+"_1")), 0, false);
			ConfiguredModel[] variant2 = ConfiguredModel.allYRotations(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_2", new ResourceLocation("lotr",location+"_2")), 0, false);
			ConfiguredModel[] variant3 = ConfiguredModel.allYRotations(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_3", new ResourceLocation("lotr",location+"_3")), 0, false);
			getVariantBuilder(block).partialState().addModels(variant1).addModels(variant2).addModels(variant3);
			itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(models().cubeAll("lotr:"+block.getRegistryName().getPath()+"_1", new ResourceLocation("lotr",location+"_1")));
			return;
		}
	}
	
	private void genPlantAge3Block(Block block, String name) {
		registeredBlocks.add(block);
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
		registeredBlocks.add(block);
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
