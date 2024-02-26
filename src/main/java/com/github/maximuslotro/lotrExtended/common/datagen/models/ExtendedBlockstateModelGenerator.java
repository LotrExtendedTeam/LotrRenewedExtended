package com.github.maximuslotro.lotrExtended.common.datagen.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.ExtendedBerryBushBlock;
import lotr.common.block.ExtendedBerryPlantBlock;
import lotr.common.block.ExtendedCakeBlock;
import lotr.common.block.ExtendedDirectionalBundleBlock;
import lotr.common.block.ExtendedFireBlock;
import lotr.common.block.ExtendedGroundDirtBlock;
import lotr.common.block.ExtendedGroundFarmlandBlock;
import lotr.common.block.ExtendedGroundGrassBlock;
import lotr.common.block.ExtendedGroundPathBlock;
import lotr.common.block.ExtendedHearthBlock;
import lotr.common.block.ExtendedPackedBundleBlock;
import lotr.common.block.ExtendedRemainsBlock;
import lotr.common.block.ExtendedSpawnerBlock;
import lotr.common.block.ExtendedTwoTypeCraftingTableBlock;
import lotr.common.block.ExtendedTwoTypeDummyBlock;
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
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBlockstateModelGenerator extends BlockStateProvider {

	private List<Block> registeredBlocks = new ArrayList<>();

	public ExtendedBlockstateModelGenerator(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
	}

	public ResourceLocation lotrExtendedLoc(String name) {
        return new ResourceLocation("lotrextended", name);
    }

	public ResourceLocation lotrLoc(String name) {
        return new ResourceLocation("lotr", name);
    }

	@Override
	protected void registerStatesAndModels() {
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(registeredBlocks.contains(block.getValue().get())) {continue;}
			if(block.getValue().get() instanceof ExtendedDirectionalBundleBlock) {
				genCubeBottomTopSideBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()+"_side"), lotrExtendedLoc("block/"+block.getKey()+"_bottom"), lotrExtendedLoc("block/"+block.getKey()+"_top"));
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
				genRandomCubeBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
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
				genCubeBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
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
			if(block.getValue().get() instanceof ExtendedTwoTypeDummyBlock) {
				genTopBottomTwoSidedNonRotatableBlockItem((ExtendedTwoTypeDummyBlock) block.getValue().get(), "block/"+block.getKey());
				continue;
			}
			if(block.getValue().get() instanceof LOTRTorchBlock) {
				genTorchBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
				continue;
			}
			if(block.getValue().get() instanceof ExtendedFireBlock) {
				genFireTextureBlock(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRWallTorchBlock) {
				if(block.getKey().contains("_wall")) {
					genTorchWallBlock((LOTRWallTorchBlock)block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
					continue;
				}
			}
			if(block.getValue().get() instanceof NonWaterloggableLanternBlock) {
				genHangingLanternBlockItem((NonWaterloggableLanternBlock) block.getValue().get(), "block/"+block.getKey());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedSpawnerBlock) {
				registeredBlocks.add(block.getValue().get());
				ModelFile cubeModel = models().cubeAll("lotrextended:"+block.getValue().get().getRegistryName().getPath(), lotrExtendedLoc("block/spawner_block"));
				simpleBlock(block.getValue().get(), cubeModel);
				continue;
			}
			if(block.getValue().get() instanceof ExtendedCakeBlock) {
				genCakeBlock((CakeBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()+"_bottom"), lotrExtendedLoc("block/"+block.getKey()+"_top"), lotrExtendedLoc("block/"+block.getKey()+"_side"), lotrExtendedLoc("block/"+block.getKey()+"_inner"));
				continue;
			}
			if(block.getValue().get() instanceof ExtendedHearthBlock) {
				genHearthBlockType((ExtendedHearthBlock) block.getValue().get(), "block/"+block.getKey());
				continue;
			}
			if(block.getKey().contains("barren_dirt")) {
				genExistingBlock(block.getValue().get(), "dirt");
				continue;
			}
			if(block.getKey().contains("scorched_cobblestone")) {
				genCubeBlockItem(block.getValue().get(),"block/"+block.getKey());
				continue;
			}
		}
		//Only Add entries to this loop if they depend on other models we are generating or are supertypes of other block types
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(registeredBlocks.contains(block.getValue().get())) {continue;}
			if(block.getValue().get() instanceof AxialSlabBlock && !(block.getValue().get() instanceof VerticalOnlySlabBlock)) {
				if(block.getKey().contains("_slab")) {
					if(block.getKey().contains("packed_fur")) {
						genSlabBlockItem((SlabBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_1"));
						continue;
					}
					if(block.getKey().contains("drystone")&&!block.getKey().contains("mossy")) {
						genSlabBlockItem((SlabBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
						continue;
					}
					genSlabBlockItem((SlabBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
					continue;
				}
			}
			if(block.getValue().get() instanceof LOTRStairsBlock) {
				if(block.getKey().contains("_stairs")) {
					if(block.getKey().contains("packed_fur")) {
						genStairsBlockItem((StairsBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)+"_1"));
						continue;
					}
					if(block.getKey().contains("drystone")&&!block.getKey().contains("mossy")) {
						genStairsBlockItem((StairsBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)));
						continue;
					}
					if(block.getKey().contains("smooth_mordor_rock")||block.getKey().contains("smooth_gondor_rock")||block.getKey().contains("smooth_rohan_rock")
							||block.getKey().contains("smooth_blue_rock")||block.getKey().contains("smooth_red_rock")) {
						genStairsBlockItem((StairsBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)));
						continue;
					}
					if(block.getKey().contains("smooth_stone_stairs")) {
						genStairsBlockItem((StairsBlock) block.getValue().get(), mcLoc("block/smooth_stone"));
						continue;
					}
					genStairsBlockItem((StairsBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)));
					continue;
				}
			}
			if(block.getValue().get() instanceof LOTRWallBlock) {
				if(block.getKey().contains("_wall")) {
					if(block.getKey().contains("packed_fur")) {
						genWallBlockItem((WallBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_1"));
						continue;
					}
					if(block.getKey().contains("drystone")&&!block.getKey().contains("mossy")) {
						genWallBlockItem((WallBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
						continue;
					}
					genWallBlockItem((WallBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
					continue;
				}
			}
			if(block.getValue().get() instanceof LOTROreBlock) {
				genCubeBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
				continue;
			}
			if(block.getValue().get() instanceof FactionCraftingTableBlock) {
				if(block.getValue().get() instanceof ExtendedTwoTypeCraftingTableBlock) {
					genTwoTextureTableBlockItem((ExtendedTwoTypeCraftingTableBlock) block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				genTopBottomTwoSidedNonRotatableBlockItem(block.getValue().get(), "block/"+block.getKey());
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

	private void genExistingBlock(Block block, String model) {
		registeredBlocks.add(block);
		ModelFile modelFile = models().getExistingFile(new ResourceLocation(model));
		simpleBlock(block, modelFile);
		itemModels().getBuilder("lotr:" + block.getRegistryName().getPath()).parent(modelFile);
	}
	private void genStairsBlockItem(StairsBlock stairs, ResourceLocation texture) {
		registeredBlocks.add(stairs);
		String name = "lotrextended:"+stairs.getRegistryName().getPath();
		ModelFile stairsModel = models().stairs(name, texture, texture, texture);
		ModelFile stairsModelInner = models().stairsInner(name + "_inner", texture, texture, texture);
		ModelFile stairsModelOuter = models().stairsOuter(name + "_outer", texture, texture, texture);
		stairsBlock(stairs, stairsModel, stairsModelInner, stairsModelOuter);
		itemModels().getBuilder("lotr:" + stairs.getRegistryName().getPath()).parent(stairsModel);
	}

	private void genPillarBlockItem(LOTRPillarBlock pillarB, String name) {
		registeredBlocks.add(pillarB);
		ResourceLocation pillar = lotrExtendedLoc(name);
		ResourceLocation face = lotrExtendedLoc(name+"_face");
		ResourceLocation top = lotrExtendedLoc(name+"_top");
		ResourceLocation middle = lotrExtendedLoc(name+"_middle");
		ResourceLocation bottom = lotrExtendedLoc(name+"_bottom");
		
		ModelFile pillarBottom = models().cubeColumn("lotrextended:"+pillarB.getRegistryName().getPath()+"_bottom", bottom, face);
		ModelFile pillarMiddle = models().cubeColumn("lotrextended:"+pillarB.getRegistryName().getPath()+"_middle", middle, face);
		ModelFile pillarTop = models().cubeColumn("lotrextended:"+pillarB.getRegistryName().getPath()+"_top", top, face);
		ModelFile pillarBlock = models().cubeColumn("lotrextended:"+pillarB.getRegistryName().getPath(), pillar, face);
		
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
		ModelFile slabModel = models().slab("lotrextended:" + slab.getRegistryName().getPath(), texture, texture, texture);
		ModelFile slabModelTop = models().slabTop("lotrextended:" + slab.getRegistryName().getPath()+"_top", texture, texture, texture);
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
		ResourceLocation farmland = lotrExtendedLoc(name);
		ResourceLocation farmlandMoist = lotrExtendedLoc(name+"_moist");
		ResourceLocation dirt = lotrExtendedLoc("block/"+extendedGroundFarmlandBlock.getBase().get().getRegistryName().getPath());
		ResourceLocation parent = mcLoc("block/template_farmland");
		ModelFile dry = models().withExistingParent("lotrextended:"+extendedGroundFarmlandBlock.getRegistryName().getPath(), parent)
                .texture("dirt", dirt)
                .texture("top", farmland);
		ModelFile wet = models().withExistingParent("lotrextended:"+extendedGroundFarmlandBlock.getRegistryName().getPath()+"_moist", parent)
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
		ResourceLocation top = lotrExtendedLoc(name+"_top");
		ResourceLocation side = lotrExtendedLoc(name+"_side");
		ResourceLocation dirt = lotrExtendedLoc("block/"+extendedGroundFarmlandBlock.getBase().get().getRegistryName().getPath());
		ResourceLocation parent = mcLoc("block/cube_bottom_top");
		ModelFile grass = models().withExistingParent("lotrextended:"+extendedGroundFarmlandBlock.getRegistryName().getPath(), parent)
                .texture("particle", dirt)
                .texture("bottom", dirt)
                .texture("top", top)
                .texture("side", side);
		ResourceLocation top1 = lotrExtendedLoc(name+"_snow_top");
		ResourceLocation side1 = lotrExtendedLoc(name+"_snow_side");
		ModelFile snow = models().withExistingParent("lotrextended:"+extendedGroundFarmlandBlock.getRegistryName().getPath()+"_snow", parent)
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
		ResourceLocation top = lotrExtendedLoc(name+"_top");
		ResourceLocation side = lotrExtendedLoc(name+"_side");
		ResourceLocation dirt = lotrExtendedLoc("block/"+extendedGroundFarmlandBlock.getBase().get().getRegistryName().getPath());
		ResourceLocation parent = mcLoc("block/grass_path");
		ModelFile grass = models().withExistingParent("lotrextended:"+extendedGroundFarmlandBlock.getRegistryName().getPath(), parent)
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

	private void genTwoTextureTableBlockItem(ExtendedTwoTypeCraftingTableBlock table, String name) {
		registeredBlocks.add(table);
		ResourceLocation down = lotrExtendedLoc(name+"_bottom");
		ResourceLocation up = lotrExtendedLoc(name+"_top");
		ResourceLocation north = lotrExtendedLoc(name+"_side_1");
		ResourceLocation south = lotrExtendedLoc(name+"_side_1");
		ResourceLocation east = lotrExtendedLoc(name+"_side_2");
		ResourceLocation west = lotrExtendedLoc(name+"_side_2");
		ModelFile cubeModel = models().cube("lotrextended:"+table.getRegistryName().getPath(), down, up, north, south, east, west).texture("particle", north);
		String name2 = table.getAltBlock().get().getRegistryName().getPath();
		ModelFile cubeModel1 = models().getExistingFile(lotrExtendedLoc(name2));
		getVariantBuilder(table)
		.forAllStatesExcept(state -> {
			Boolean type = state.getValue(ExtendedTwoTypeCraftingTableBlock.ALTERNATITE);
            return ConfiguredModel.builder()
                    .modelFile(type ? cubeModel1 : cubeModel)
                    .build();
        });
		itemModels().getBuilder("lotr:"+table.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genHangingLanternBlockItem(NonWaterloggableLanternBlock lantern, String name) {
		registeredBlocks.add(lantern);
		ResourceLocation lamp = lotrExtendedLoc(name);
		ResourceLocation fire = lotrExtendedLoc(name+"_fire");
		ResourceLocation chain = lotrExtendedLoc(name+"_chain");
		ResourceLocation parent = lotrLoc("block/template_lamp");
		ResourceLocation parentHanging = lotrLoc("block/template_lamp_hanging");
		ModelFile lanternModel = models().withExistingParent("lotrextended:"+lantern.getRegistryName().getPath(), parent)
                .texture("lamp", lamp)
                .texture("fire", fire);
		ModelFile hangingModel = models().withExistingParent("lotrextended:"+lantern.getRegistryName().getPath()+"_hanging", parentHanging)
                .texture("lamp", lamp)
                .texture("fire", fire)
                .texture("chain", chain);
		getVariantBuilder(lantern)
		.forAllStates(state -> {
			Boolean hanging = state.getValue(NonWaterloggableLanternBlock.HANGING);
            return ConfiguredModel.builder()
                    .modelFile(hanging ? hangingModel : lanternModel)
                    .build();
        });
	}

	private void genCakeBlock(CakeBlock cake, ResourceLocation textureBottom, ResourceLocation textureTop, ResourceLocation textureSide, ResourceLocation textureInside) {
		registeredBlocks.add(cake);
		ModelFile cakeBlock = models().withExistingParent("lotrextended:" + cake.getRegistryName().getPath(), "block/cake")
				.texture("particle", textureSide).texture("bottom", textureBottom).texture("top", textureTop)
				.texture("side", textureSide);

		getVariantBuilder(cake).forAllStates(state -> {
			int bites = state.getValue(CakeBlock.BITES);
			return ConfiguredModel.builder().modelFile(bites == 0 ? cakeBlock : models()
					.withExistingParent("lotrextended:" + cake.getRegistryName().getPath() + bites,"block/cake_slice" + bites)
					.texture("particle", textureSide).texture("bottom", textureBottom)
					.texture("top", textureTop).texture("side", textureSide)
					.texture("inside", textureInside))
					.build();
		});
	}

	private void genWallBlockItem(WallBlock wall, ResourceLocation texture) {
		registeredBlocks.add(wall);
		wallBlock(wall, "lotrextended:"+wall.getRegistryName().getPath(), texture);
		itemModels().wallInventory("lotr:" + wall.getRegistryName().getPath(), texture);
	}

	private void genTorchBlockItem(Block torch, ResourceLocation texture) {
		registeredBlocks.add(torch);
		ModelFile cubeModel = models().torch("lotrextended:"+torch.getRegistryName().getPath(), texture);
		simpleBlock(torch, cubeModel);
	}

	private void genTorchWallBlock(LOTRWallTorchBlock torch, ResourceLocation texture) {
		registeredBlocks.add(torch);
		ModelFile cubeModel = models().torchWall("lotrextended:"+torch.getRegistryName().getPath(), texture);
		getVariantBuilder(torch).forAllStates(state -> {
			Direction bites = state.getValue(LOTRWallTorchBlock.FACING);
			return ConfiguredModel.builder()
                    .modelFile(cubeModel)
                    .rotationY(bites == Direction.EAST ? 0 : (bites == Direction.SOUTH ? 90 : (bites == Direction.WEST ? 180 : 270)))
                    .build();
		});
	}

	private void genHearthBlockType(ExtendedHearthBlock block, String base) {
		registeredBlocks.add(block);
		ResourceLocation top = lotrExtendedLoc(base+"_lit_top");
		ResourceLocation bottom = new ResourceLocation("lotr:block/hearth_block_bottom");
		ResourceLocation side = lotrExtendedLoc(base+"_lit_side");
		ModelFile lit = models().cubeBottomTop("lotrextended:"+block.getRegistryName().getPath()+"_lit", side, bottom, top);
		ModelFile unlit = models().getExistingFile(new ResourceLocation("lotr:block/hearth_block"));
		getVariantBuilder(block).forAllStates(state -> {
			Boolean litBoolean = state.getValue(ExtendedHearthBlock.LIT);
			return ConfiguredModel.builder()
                    .modelFile(litBoolean ? lit : unlit)
                    .build();
		});
	}

	private void genCubeBottomTopSideBlockItem(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeBottomTop("lotrextended:"+block.getRegistryName().getPath(), side, bottom, top);
		directionalBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genEndSideNonRotatableBlockItem(Block block, String sidesBase) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeColumn("lotrextended:"+block.getRegistryName().getPath(), lotrExtendedLoc(sidesBase+"_side"), lotrExtendedLoc(sidesBase+"_end"));
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genTopBottomTwoSidedNonRotatableBlockItem(Block block, String sidesBase) {
		registeredBlocks.add(block);
		ResourceLocation down = lotrExtendedLoc(sidesBase+"_bottom");
		ResourceLocation up = lotrExtendedLoc(sidesBase+"_top");
		ResourceLocation north = lotrExtendedLoc(sidesBase+"_side_1");
		ResourceLocation south = lotrExtendedLoc(sidesBase+"_side_1");
		ResourceLocation east = lotrExtendedLoc(sidesBase+"_side_2");
		ResourceLocation west = lotrExtendedLoc(sidesBase+"_side_2");
		ModelFile cubeModel = models().cube("lotrextended:"+block.getRegistryName().getPath(), down, up, north, south, east, west).texture("particle", north);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genTopBottomFourSidedNonRotatableBlockItem(Block block, String sidesBase) {
		registeredBlocks.add(block);
		ResourceLocation down = lotrExtendedLoc(sidesBase+"_bottom");
		ResourceLocation up = lotrExtendedLoc(sidesBase+"_top");
		ResourceLocation north = lotrExtendedLoc(sidesBase+"_side_north");
		ResourceLocation south = lotrExtendedLoc(sidesBase+"_side_south");
		ResourceLocation east = lotrExtendedLoc(sidesBase+"_side_east");
		ResourceLocation west = lotrExtendedLoc(sidesBase+"_side_west");
		ModelFile cubeModel = models().cube("lotrextended:"+block.getRegistryName().getPath(), down, up, north, south, east, west).texture("particle", north);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genCubeBlockItem(Block block, String allSides) {
		genCubeBlockItem(block, lotrExtendedLoc(allSides));
	}
	
	private void genCubeBlockItem(Block block, ResourceLocation allSides) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeAll("lotrextended:"+block.getRegistryName().getPath(), allSides);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genRandomCubeBlockItem(Block block, ResourceLocation allSides) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeAll("lotrextended:"+block.getRegistryName().getPath(), allSides);
		getVariantBuilder(block)
		.forAllStatesExcept(state -> {
			return ConfiguredModel.allRotations(cubeModel, false);
        });
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void numberTexturedRandomBlockItem(Block block, String location, int numVariants) {
		registeredBlocks.add(block);
		if(numVariants==4) {
			ConfiguredModel[] variant1 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_1", new ResourceLocation("lotrextended",location+"_1")), 0, false);
			ConfiguredModel[] variant2 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_2", new ResourceLocation("lotrextended",location+"_2")), 0, false);
			ConfiguredModel[] variant3 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_3", new ResourceLocation("lotrextended",location+"_3")), 0, false);
			ConfiguredModel[] variant4 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_4", new ResourceLocation("lotrextended",location+"_4")), 0, false);
			getVariantBuilder(block).partialState().addModels(variant1).addModels(variant2).addModels(variant3).addModels(variant4);
			itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_1", new ResourceLocation("lotrextended",location+"_1")));
			return;
		}
		if(numVariants==3) {
			ConfiguredModel[] variant1 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_1", new ResourceLocation("lotrextended",location+"_1")), 0, false);
			ConfiguredModel[] variant2 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_2", new ResourceLocation("lotrextended",location+"_2")), 0, false);
			ConfiguredModel[] variant3 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_3", new ResourceLocation("lotrextended",location+"_3")), 0, false);
			getVariantBuilder(block).partialState().addModels(variant1).addModels(variant2).addModels(variant3);
			itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_1", new ResourceLocation("lotrextended",location+"_1")));
			return;
		}
	}

	private void genFireTextureBlock(Block block) {
		registeredBlocks.add(block);
		ModelFile floor0 = models().withExistingParent("lotrextended:"+block.getRegistryName().getPath()+"_floor0", "minecraft:block/template_fire_floor")
                .texture("fire", lotrExtendedLoc("block/"+block.getRegistryName().getPath()+"_0"));
		ModelFile floor1 = models().withExistingParent("lotrextended:"+block.getRegistryName().getPath()+"_floor1", "minecraft:block/template_fire_floor")
                .texture("fire", lotrExtendedLoc("block/"+block.getRegistryName().getPath()+"_1"));
		
		ModelFile side_alt0 = models().withExistingParent("lotrextended:"+block.getRegistryName().getPath()+"_side_alt0", "minecraft:block/template_fire_side_alt")
                .texture("fire", lotrExtendedLoc("block/"+block.getRegistryName().getPath()+"_0"));
		ModelFile side_alt1 = models().withExistingParent("lotrextended:"+block.getRegistryName().getPath()+"_side_alt1", "minecraft:block/template_fire_side_alt")
                .texture("fire", lotrExtendedLoc("block/"+block.getRegistryName().getPath()+"_1"));
		
		ModelFile side0 = models().withExistingParent("lotrextended:"+block.getRegistryName().getPath()+"_side0", "minecraft:block/template_fire_side")
                .texture("fire", lotrExtendedLoc("block/"+block.getRegistryName().getPath()+"_0"));
		ModelFile side1 = models().withExistingParent("lotrextended:"+block.getRegistryName().getPath()+"_side1", "minecraft:block/template_fire_side")
                .texture("fire", lotrExtendedLoc("block/"+block.getRegistryName().getPath()+"_1"));

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block).part().modelFile(floor0).nextModel().modelFile(floor1).addModel().end();
        builder.part().modelFile(side0).nextModel().modelFile(side1).nextModel().modelFile(side_alt0).nextModel().modelFile(side_alt1).addModel().end();
        builder.part().modelFile(side0).rotationY(90).nextModel().modelFile(side1).rotationY(90).nextModel().modelFile(side_alt0).rotationY(90).nextModel().modelFile(side_alt1).rotationY(90).addModel().end();
        builder.part().modelFile(side0).rotationY(180).nextModel().modelFile(side1).rotationY(180).nextModel().modelFile(side_alt0).rotationY(180).nextModel().modelFile(side_alt1).rotationY(180).addModel().end();
        builder.part().modelFile(side0).rotationY(270).nextModel().modelFile(side1).rotationY(270).nextModel().modelFile(side_alt0).rotationY(270).nextModel().modelFile(side_alt1).rotationY(270).addModel().end();
	}

	private void genPlantAge3Block(Block block, String name) {
		registeredBlocks.add(block);
		getVariantBuilder(block)
        .partialState().with(BlockStateProperties.AGE_3, 0)
            .modelForState().modelFile(models().cross("lotrextended:"+block.getRegistryName().getPath()+"_age0", lotrExtendedLoc(name+"_0"))).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 1)
            .modelForState().modelFile(models().cross("lotrextended:"+block.getRegistryName().getPath()+"_age1", lotrExtendedLoc(name+"_1"))).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 2)
            .modelForState().modelFile(models().cross("lotrextended:"+block.getRegistryName().getPath()+"_age2", lotrExtendedLoc(name+"_2"))).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 3)
            .modelForState().modelFile(models().cross("lotrextended:"+block.getRegistryName().getPath()+"_age3", lotrExtendedLoc(name+"_3"))).addModel();
	}

	private void genBushAge3Block(Block block, String name) {
		registeredBlocks.add(block);
		ModelFile empty = models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_empty", lotrExtendedLoc(name+"_empty"));
		ModelFile full = models().cubeAll("lotrextended:"+block.getRegistryName().getPath(), lotrExtendedLoc(name));
		getVariantBuilder(block)
        .partialState().with(BlockStateProperties.AGE_3, 0).modelForState().modelFile(empty).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 1).modelForState().modelFile(empty).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 2).modelForState().modelFile(empty).addModel()
        .partialState().with(BlockStateProperties.AGE_3, 3).modelForState().modelFile(full).addModel();
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(full);
	}
}
