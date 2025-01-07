package com.github.maximuslotro.lotrrextended.common.datagen.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedAncientStoneRuneBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryBushBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryPlantBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCakeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedWargCarpetBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedChainBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedConquestTableBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedFireBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundDirtBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundFarmlandBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundGrassBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundPathBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedHangingBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLargeFlowerPotBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLargePotBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerWallBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMossBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMossCarpetBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGrinderBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedRopeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedFullBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedBarrelBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedReedBasketBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedSingleChestBlock;
import com.github.maximuslotro.lotrrextended.common.block.spawners.ExtendedGroupSpawnerBlock;
import com.github.maximuslotro.lotrrextended.common.block.spawners.ExtendedSpawnerBlock;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.github.maximuslotro.lotrrextended.common.enums.PillarType;
import com.github.maximuslotro.lotrrextended.common.enums.RopeType;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedStoneBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedThreeTallPillarBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedVineBlock;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.BranchBlock;
import lotr.common.block.ChandelierBlock;
import lotr.common.block.ExtendedDirectionalBundleBlock;
import lotr.common.block.ExtendedHearthBlock;
import lotr.common.block.ExtendedLOTRTorchBlock;
import lotr.common.block.ExtendedLOTRWallTorchBlock;
import lotr.common.block.ExtendedLanternBlock;
import lotr.common.block.ExtendedPackedFurBlock;
import lotr.common.block.ExtendedRemainsBlock;
import lotr.common.block.ExtendedTwoTypeCraftingTableBlock;
import lotr.common.block.FactionCraftingTableBlock;
import lotr.common.block.FallenLeavesBlock;
import lotr.common.block.FourLeafCloverBlock;
import lotr.common.block.LOTRBlockStates;
import lotr.common.block.LOTRDoorBlock;
import lotr.common.block.LOTRFenceBlock;
import lotr.common.block.LOTRFenceGateBlock;
import lotr.common.block.LOTRLeavesBlock;
import lotr.common.block.LOTROreBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRPlanksBlock;
import lotr.common.block.LOTRPottedPlantBlock;
import lotr.common.block.LOTRSaplingBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStandingSignBlock;
import lotr.common.block.LOTRStrippedLogBlock;
import lotr.common.block.LOTRStrippedWoodBlock;
import lotr.common.block.LOTRTrapdoorBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.block.LOTRWallSignBlock;
import lotr.common.block.LOTRWallTorchBlock;
import lotr.common.block.LOTRWoodButtonBlock;
import lotr.common.block.LOTRWoodPressurePlateBlock;
import lotr.common.block.MineralBlock;
import lotr.common.block.NettleBlock;
import lotr.common.block.StrippableLogBlock;
import lotr.common.block.StrippableWoodBlock;
import lotr.common.block.ThistleBlock;
import lotr.common.block.ThreeLeafCloverBlock;
import lotr.common.block.VerticalOnlySlabBlock;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.block.WildPipeweedBlock;
import lotr.common.block.WoodBeamBlock;
import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.NetherRootsBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
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
			try {
				if(registeredBlocks.contains(block.getValue().get())) {continue;}
				if(block.getValue().get() instanceof ExtendedDirectionalBundleBlock) {
					genCubeBottomTopSideBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()+"_side"), lotrExtendedLoc("block/"+block.getKey()+"_bottom"), lotrExtendedLoc("block/"+block.getKey()+"_top"));
					continue;
				}
				if(block.getValue().get() instanceof ExtendedPackedFurBlock) {
					genCubeBlockItem(block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof VesselDrinkBlock) {
					if(block.getValue().get()==ExtendedBlocks.SKULL_GOBLET.get()) {
						genSkullGobletBlock((VesselDrinkBlock)block.getValue().get());
						continue;
					}
				}
				if(block.getValue().get() instanceof ExtendedRemainsBlock) {
					numberTexturedRandomBlockItem(block.getValue().get(), "block/"+block.getKey(), 3);
					continue;
				}
				if(block.getValue().get() instanceof ExtendedBerryPlantBlock) {
					genPlantAge3Block(block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedLargePotBlock) {
					genLargePotBlockItem((ExtendedLargePotBlock) block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedLargeFlowerPotBlock) {
					genLargeFlowerPotBlockItem((ExtendedLargeFlowerPotBlock) block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedChainBlock) {
					genChainBlock(block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedBerryBushBlock) {
					genBushAge3Block(block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedBedBlock) {
					genBedBlockItem((ExtendedBedBlock) block.getValue().get(), block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedVineBlock) {
					genVineBlock((ExtendedVineBlock) block.getValue().get(), block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedWargCarpetBlock) {
					genCarpetBlockItem((ExtendedWargCarpetBlock) block.getValue().get(), block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof FallenLeavesBlock) {
					genFallenLeavesBlock((FallenLeavesBlock) block.getValue().get(), block.getKey().substring(7));
					continue;
				}
				if(block.getValue().get() instanceof ExtendedAncientStoneRuneBlock) {
					genAncientRuneBlockItem((ExtendedAncientStoneRuneBlock) block.getValue().get(), block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedReedBasketBlock) {
					genReedBarrelBlockItem((ExtendedReedBasketBlock) block.getValue().get(), block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedBarrelBlock) {
					genBarrelBlockItem((ExtendedBarrelBlock) block.getValue().get(), block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedFullBedBlock) {
					genBedrollBlockItem((ExtendedFullBedBlock) block.getValue().get(), block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedRopeBlock) {
					genRopeBlock((ExtendedRopeBlock) block.getValue().get());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedGrinderBlock) {
					genMillstoneBlock((ExtendedGrinderBlock) block.getValue().get());
					continue;
				}
				if(block.getValue().get() instanceof BranchBlock) {
					genBranchBlockItem((BranchBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)+"_log"));
					continue;
				}
				if(block.getValue().get() instanceof ExtendedConquestTableBlock) {
					genConquestTableBlockItem((ExtendedConquestTableBlock) block.getValue().get(), block.getKey());
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
				if(block.getValue().get() instanceof StrippableLogBlock) {
					genLogBlockItem((StrippableLogBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
					continue;
				}
				if(block.getValue().get() instanceof StrippableWoodBlock) {
					genWoodBlockItem((StrippableWoodBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_log"));
					continue;
				}
				if(block.getValue().get() instanceof LOTRStrippedLogBlock) {
					genStrippedLogBlockItem((LOTRStrippedLogBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
					continue;
				}
				if(block.getValue().get() instanceof LOTRStrippedWoodBlock) {
					genStrippedWoodBlockItem((LOTRStrippedWoodBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_log"));
					continue;
				}
				if(block.getValue().get() instanceof WoodBeamBlock) {
					genBeamBlockItem((WoodBeamBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
					continue;
				}
				if(block.getValue().get() instanceof ExtendedMEBannerBlock) {
					genBannerBlock((ExtendedMEBannerBlock) block.getValue().get(), lotrExtendedLoc("block/banner/"+block.getKey()));
					continue;
				}
				if(block.getValue().get() instanceof ExtendedMEBannerWallBlock) {
					genBannerWallBlock((ExtendedMEBannerWallBlock) block.getValue().get(), lotrExtendedLoc("block/banner/"+block.getKey()));
					continue;
				}
				if(block.getValue().get() instanceof ExtendedWeaponRackBlock) {
					genWeaponRackBlock((ExtendedWeaponRackBlock) block.getValue().get());
					continue;
				}
				if(block.getValue().get() instanceof LOTRStandingSignBlock) {
					genSignBlock((LOTRStandingSignBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_planks"));
					continue;
				}
				if(block.getValue().get() instanceof LOTRWallSignBlock) {
					genWallSignBlock((LOTRWallSignBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 10)+"_planks"));
					continue;
				}
				if(block.getValue().get() instanceof LOTRPlanksBlock) {
					genCubeBlockItem(block.getValue().get(),"block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ChandelierBlock) {
					genChandelierBlock((ChandelierBlock)block.getValue().get(), block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedMossCarpetBlock) {
					genMossCarpetBlockItem((ExtendedMossCarpetBlock)block.getValue().get());
					continue;
				}
				if(block.getValue().get() instanceof LOTRTrapdoorBlock) {
					genTrapdoorBlockItem((LOTRTrapdoorBlock)block.getValue().get(),"block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof TrapDoorBlock) {
					genTrapdoorBlockItem((TrapDoorBlock)block.getValue().get(),"block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof LOTRSaplingBlock) {
					genSapplingBlock((LOTRSaplingBlock)block.getValue().get(),lotrExtendedLoc("block/"+block.getKey()));
					continue;
				}
				if(block.getValue().get() instanceof ExtendedHangingBlock) {
					genHangingBlock((ExtendedHangingBlock)block.getValue().get(),lotrExtendedLoc("block/"+block.getKey()));
					continue;
				}
				if(block.getValue().get() instanceof LOTRPottedPlantBlock) {
					genPottedSapplingBlock((LOTRPottedPlantBlock)block.getValue().get(),lotrExtendedLoc("block/"+block.getKey().substring(7, block.getKey().length())));
					continue;
				}
				if(block.getValue().get() instanceof LOTRWoodPressurePlateBlock) {
					genPressurePlateBlockItem((LOTRWoodPressurePlateBlock)block.getValue().get(),lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 15)+"_planks"));
					continue;
				}
				if(block.getValue().get() instanceof LOTRWoodButtonBlock) {
					genButtonBlockItem((LOTRWoodButtonBlock)block.getValue().get(),lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)+"_planks"));
					continue;
				}
				if(block.getValue().get() instanceof LOTRDoorBlock) {
					genDoorBlock((LOTRDoorBlock)block.getValue().get(),"block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof DoorBlock) {
					genDoorBlock((DoorBlock)block.getValue().get(),"block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof LOTRFenceBlock) {
					boolean didGen = false;
					for(String woodType : DataGenValueHolder.newWoodTypesHolder) {
						if(block.getKey().contains(woodType)) {
							didGen = true;
							genFenceBlockItem((LOTRFenceBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 6)+"_planks"));
							continue;
						}
					}
					if(didGen) {
						continue;
					}
				}
				if(block.getValue().get() instanceof LOTRFenceGateBlock) {
					boolean didGen = false;
					for(String woodType : DataGenValueHolder.newWoodTypesHolder) {
						if(block.getKey().contains(woodType)) {
							didGen = true;
							genFenceGateBlockItem((LOTRFenceGateBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 11)+"_planks"));
							continue;
						}
					}
					if(didGen) {
						continue;
					}
				}
				if(block.getValue().get() instanceof ExtendedMossBlock) {
					registeredBlocks.add(block.getValue().get());
					ModelFile cubeModel = models().singleTexture("lotrextended:"+block.getValue().get().getRegistryName().getPath(), lotrExtendedLoc("block/tinted_cube_all"), "all", lotrExtendedLoc("block/"+block.getKey()));
					ModelFile display = models().cubeAll("lotrextended:"+block.getValue().get().getRegistryName().getPath()+"_display", lotrExtendedLoc("block/"+block.getKey()+"_display"));
					simpleBlock(block.getValue().get(), cubeModel);
					itemModels().getBuilder("lotr:"+block.getValue().get().getRegistryName().getPath()).parent(display);
					continue;
				}
				if(block.getValue().get() instanceof ExtendedStoneBlock) {
					if(block.getKey()=="cobbled_drystone") {
						genCubeBlockItem(block.getValue().get(), lotrLoc("block/drystone"));
						continue;
					}
					if(block.getKey()=="scorched_stone") {
						genMirroredBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
						continue;
					}
					if(block.getKey()=="gneiss") {
						genCubeTopBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()), lotrExtendedLoc("block/"+block.getKey()+"_top"));
						continue;
					}
					if(block.getKey()=="polished_limestone") {
						genCubeTopBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()), lotrExtendedLoc("block/"+block.getKey()+"_top"));
						continue;
					}
					genCubeBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
					continue;
				}
				if(block.getValue().get() instanceof MineralBlock) {
					try {
						genCubeBlockItem(block.getValue().get(),"block/"+block.getKey());
					}catch(IllegalArgumentException e) {
						System.out.println(block.getKey()+" is not CubeBlockItem! Using alternative model.");
						genEndSideNonRotatableBlockItem(block.getValue().get(),"block/"+block.getKey());
					}
					continue;
				}
				if(block.getValue().get() instanceof LOTRPillarBlock) {
					genPillarBlockItem((LOTRPillarBlock) block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedThreeTallPillarBlock) {
					genSpecialPillarBlockItem((ExtendedThreeTallPillarBlock) block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedLOTRTorchBlock) {
					genTorchBlockItem((ExtendedLOTRTorchBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
					continue;
				}
				if(block.getValue().get() instanceof LOTRLeavesBlock) {
					if(block.getKey().equals("chestnut_leaves")) {
						numberTexturedBlockItem(block.getValue().get(), new int[] {5,5,1,1,1});
						continue;
					}
					if(block.getKey().equals("chestnut_leaves_fruit")) {
						numberTexturedBlockItem(block.getValue().get(), new int[] {1});
						continue;
					}
					genCubeBlockItem(block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()));
					continue;
				}
				if(block.getValue().get() instanceof ExtendedFireBlock) {
					genFireTextureBlock(block.getValue().get());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedLOTRWallTorchBlock) {
					if(block.getKey().contains("_wall")) {
						genTorchWallBlock((ExtendedLOTRWallTorchBlock)block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
						continue;
					}
				}
				if(block.getValue().get() instanceof ExtendedLanternBlock) {
					genHangingLanternBlockItem((ExtendedLanternBlock) block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedSpawnerBlock) {
					registeredBlocks.add(block.getValue().get());
					ModelFile cubeModel = models().cubeAll("lotrextended:"+block.getValue().get().getRegistryName().getPath(), lotrExtendedLoc("block/spawner_block"));
					simpleBlock(block.getValue().get(), cubeModel);
					continue;
				}
				if(block.getValue().get() instanceof ExtendedGroupSpawnerBlock) {
					registeredBlocks.add(block.getValue().get());
					ModelFile cubeModel = models().cubeAll("lotrextended:"+block.getValue().get().getRegistryName().getPath(), lotrExtendedLoc("block/spawner_block"));
					simpleBlock(block.getValue().get(), cubeModel);
					continue;
				}
				if(block.getValue().get() instanceof ExtendedCakeBlock) {
					genCakeBlock((ExtendedCakeBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey()+"_bottom"), lotrExtendedLoc("block/"+block.getKey()+"_top"), lotrExtendedLoc("block/"+block.getKey()+"_side"), lotrExtendedLoc("block/"+block.getKey()+"_inner"));
					continue;
				}
				if(block.getValue().get() instanceof ExtendedHearthBlock) {
					genHearthBlockType((ExtendedHearthBlock) block.getValue().get(), "block/"+block.getKey());
					continue;
				}
				if(block.getValue().get() instanceof ExtendedSingleChestBlock) {
					genChestBlockItem(block.getValue().get(), block.getKey());
					continue;
				}
				if(block.getKey().equals("barren_dirt")) {
					genExistingMcBlock(block.getValue().get(), "dirt");
					continue;
				}
			}catch(IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		//Only Add entries to this loop if they depend on other models we are generating or are super-types of other block types
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(registeredBlocks.contains(block.getValue().get())) {continue;}
			try {
				if(block.getValue().get() instanceof AxialSlabBlock && !(block.getValue().get() instanceof VerticalOnlySlabBlock)) {
					if(block.getKey().contains("_slab")) {
						if(block.getKey().contains("drystone")&&!block.getKey().contains("mossy")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
							continue;
						}
						if(block.getKey().contains("pillar")) {
							ResourceLocation TB = lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_face");
							ResourceLocation S = lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5));
							genSlabBlockItem((SlabBlock) block.getValue().get(), S, TB, TB, S);
							continue;
						}
						if(block.getKey().equals("mud_path_slab")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), lotrExtendedLoc("block/mud_path"),
									lotrExtendedLoc("block/mud_path"),
									lotrExtendedLoc("block/mud_path"),
									models().cubeAll("lotrextended:"+block.getValue().get().getRegistryName().getPath()+"_double", lotrExtendedLoc("block/mud_path")), false, true);
							continue;
						}
						if(block.getKey().contains("wood")) {
							genSlabBlockItemWOOD((SlabBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 10)+"_log"));
							continue;
						}
						if(block.getKey().contains("log")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)),
									lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_top"),
									lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_top"),
									lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)), false, false);
							continue;
						}
						boolean didGen = false;
						for(String woodType : DataGenValueHolder.newWoodTypesHolder) {
							if(block.getKey().contains(woodType)&&!block.getKey().contains("beam")) {
								didGen = true;
								genSlabBlockItem((SlabBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_planks"));
								continue;
							}
							if(block.getKey().contains(woodType)&&block.getKey().contains("beam")) {
								didGen = true;
								genSlabBlockItem((SlabBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)),
										lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_top"),
										lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_top"),
										lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)), false, false);
								continue;
							}
						}
						if(didGen) {
							continue;
						}
						if(block.getKey().equals("coarse_dirt_slab")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), mcLoc("block/coarse_dirt"));
							continue;
						}
						if(block.getKey().equals("dirt_slab")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), mcLoc("block/dirt"));
							continue;
						}
						if(block.getKey().equals("bone_block_slab")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), mcLoc("block/bone_block_side"),
									mcLoc("block/bone_block_top"),
									mcLoc("block/bone_block_top"),
							models().withExistingParent("lotrextended:"+block.getValue().get().getRegistryName().getPath()+"_double", mcLoc("block/bone_block")), false, true);
							continue;
						}
						if(block.getKey().equals("grass_path_slab")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), mcLoc("block/grass_path_top"),
									mcLoc("block/grass_path_top"),
									mcLoc("block/grass_path_top"),
							models().cubeAll("lotrextended:"+block.getValue().get().getRegistryName().getPath()+"_double", mcLoc("block/grass_path_top")), false, true);
							continue;
						}
						if(block.getKey().equals("podzol_slab")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), mcLoc("block/podzol_top"),
									mcLoc("block/podzol_top"),
									mcLoc("block/podzol_top"),
							models().cubeAll("lotrextended:"+block.getValue().get().getRegistryName().getPath()+"_double", mcLoc("block/podzol_top")), false, true);
							continue;
						}
						if(block.getKey().equals("mycelium_slab")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), mcLoc("block/mycelium_top"),
									mcLoc("block/mycelium_top"),
									mcLoc("block/mycelium_top"),
							models().cubeAll("lotrextended:"+block.getValue().get().getRegistryName().getPath()+"_double", mcLoc("block/mycelium_top")), false, true);
							continue;
						}
						if(block.getKey().equals("grass_block_slab")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), mcLoc("block/grass_block_top"),
									mcLoc("block/grass_block_top"),
									mcLoc("block/grass_block_top"),
									models().singleTexture("lotrextended:"+block.getValue().get().getRegistryName().getPath()+"_double", lotrExtendedLoc("block/tinted_cube_all"), "all", mcLoc("block/grass_block_top")), true, true);
							continue;
						}
						if(block.getKey().equals("barren_dirt_slab")) {
							genSlabBlockItem((SlabBlock) block.getValue().get(), mcLoc("block/dirt"));
							continue;
						}
						genSlabBlockItem((SlabBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
						continue;
					}
				}
				if(block.getValue().get() instanceof LOTRStairsBlock) {
					if(block.getKey().contains("_stairs")) {
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
						if(block.getKey().contains("wood")) {
							genStairsBlockItem((StairsBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 12)+"_log"));
							continue;
						}
						if(block.getKey().contains("log")) {
							genStairsBlockItem((StairsBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)),
									lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)+"_top"),
									lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)+"_top"));
							continue;
						}
						if(block.getKey().equals("bone_block_stairs")) {
							genStairsBlockItem((StairsBlock) block.getValue().get(), mcLoc("block/bone_block_side"),
									mcLoc("block/bone_block_top"),
									mcLoc("block/bone_block_top"));
							continue;
						}
						if(block.getKey().contains("mud_path")) {
							genStairsBlockItem((StairsBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)));
							continue;
						}
						boolean didGen = false;
						for(String woodType : DataGenValueHolder.newWoodTypesHolder) {
							if(block.getKey().contains(woodType)) {
								didGen = true;
								genStairsBlockItem((StairsBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)+"_planks"));
								continue;
							}
						}
						if(didGen) {
							continue;
						}
						genStairsBlockItem((StairsBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 7)));
						continue;
					}
				}
				if(block.getValue().get() instanceof LOTRWallBlock) {
					if(block.getKey().contains("_wall")) {
						if(block.getKey().contains("drystone")&&!block.getKey().contains("mossy")) {
							genWallBlockItem((WallBlock) block.getValue().get(), lotrLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)));
							continue;
						}
						if(block.getKey().contains("mud_path")) {
							genWallBlockItem((WallBlock) block.getValue().get(), lotrExtendedLoc("block/"+block.getKey().substring(0, block.getKey().length() - 5)+"_1"));
							continue;
						}
						if(block.getKey().contains("bone_block_wall")) {
							genWallBlockItem((WallBlock) block.getValue().get(), mcLoc("block/bone_block_side"));
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
			}catch(IllegalArgumentException e) {
				e.printStackTrace();
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

	private void genExistingMcBlock(Block block, String model) {
		registeredBlocks.add(block);
		ModelFile modelFile = models().getExistingFile(mcLoc(model));
		simpleBlock(block, modelFile);
		itemModels().getBuilder("lotr:" + block.getRegistryName().getPath()).parent(modelFile);
	}
	private void genStairsBlockItem(StairsBlock stairs, ResourceLocation texture) {
		genStairsBlockItem(stairs, texture, texture, texture);
	}

	private void genStairsBlockItem(StairsBlock stairs, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
		registeredBlocks.add(stairs);
		String name = "lotrextended:"+stairs.getRegistryName().getPath();
		ModelFile stairsModel = models().stairs(name, side, bottom, top);
		ModelFile stairsModelInner = models().stairsInner(name + "_inner", side, bottom, top);
		ModelFile stairsModelOuter = models().stairsOuter(name + "_outer", side, bottom, top);
		stairsBlock(stairs, stairsModel, stairsModelInner, stairsModelOuter);
		itemModels().getBuilder("lotr:" + stairs.getRegistryName().getPath()).parent(stairsModel);
	}

	private void genPressurePlateBlockItem(LOTRWoodPressurePlateBlock plate, ResourceLocation texture) {
		registeredBlocks.add(plate);
		ResourceLocation parent = mcLoc("block/pressure_plate_up");
		ModelFile plateModel = models().withExistingParent("lotrextended:"+plate.getRegistryName().getPath(), parent).texture("texture", texture);
		ResourceLocation parent2 = mcLoc("block/pressure_plate_down");
		ModelFile down = models().withExistingParent("lotrextended:"+plate.getRegistryName().getPath()+"_down", parent2).texture("texture", texture);
		getVariantBuilder(plate)
		.forAllStatesExcept(state -> {
			Boolean powered = state.getValue(LOTRWoodPressurePlateBlock.POWERED);
            return ConfiguredModel.builder()
                    .modelFile(powered ? down : plateModel)
                    .build();
        });
		itemModels().getBuilder("lotr:" + plate.getRegistryName().getPath()).parent(plateModel);
	}

	private void genBranchBlockItem(BranchBlock branch, ResourceLocation texture) {
		registeredBlocks.add(branch);
		ResourceLocation sideParent = lotrLoc("block/template_branch_side");
		ModelFile sideModel = models().withExistingParent("lotrextended:"+branch.getRegistryName().getPath()+"_side", sideParent).texture("branch", texture);
		ResourceLocation postParent = lotrLoc("block/template_branch_post");
		ModelFile postModel = models().withExistingParent("lotrextended:"+branch.getRegistryName().getPath()+"_post", postParent).texture("branch", texture);
		MultiPartBlockStateBuilder builder = getMultipartBuilder(branch).part().modelFile(postModel).addModel().end();
	        builder.part().modelFile(sideModel).uvLock(true).addModel().condition(BranchBlock.NORTH, true).end();
	        builder.part().modelFile(sideModel).uvLock(true).rotationY(90).addModel().condition(BranchBlock.EAST, true).end();
	        builder.part().modelFile(sideModel).uvLock(true).rotationY(180).addModel().condition(BranchBlock.SOUTH, true).end();
	        builder.part().modelFile(sideModel).uvLock(true).rotationY(270).addModel().condition(BranchBlock.WEST, true).end();
		itemModels().getBuilder("lotr:" + branch.getRegistryName().getPath()).parent(postModel);
	}
	

	private void genButtonBlockItem(LOTRWoodButtonBlock button, ResourceLocation texture) {
		registeredBlocks.add(button);
		ResourceLocation parent = mcLoc("block/button");
		ModelFile buttonModel = models().withExistingParent("lotrextended:"+button.getRegistryName().getPath(), parent).texture("texture", texture);
		ResourceLocation parent2 = mcLoc("block/button_pressed");
		ModelFile pressed = models().withExistingParent("lotrextended:"+button.getRegistryName().getPath()+"_pressed", parent2).texture("texture", texture);
		ResourceLocation parent3 = mcLoc("block/button_inventory");
		ModelFile inventory = models().withExistingParent("lotrextended:"+button.getRegistryName().getPath()+"_inventory", parent3).texture("texture", texture);
		
		getVariantBuilder(button)
		.forAllStatesExcept(state -> {
			AttachFace face = state.getValue(LOTRWoodButtonBlock.FACE);
			Direction faciing = state.getValue(LOTRWoodButtonBlock.FACING);
			Boolean powered = state.getValue(LOTRWoodButtonBlock.POWERED);
			int yValue = 0;
			if(faciing==Direction.EAST) {
				if(face==AttachFace.WALL||face==AttachFace.FLOOR) {
					yValue=90;
				}
				else {
					yValue=270;
				}
			}
			if(faciing==Direction.NORTH) {
				if(face==AttachFace.CEILING) {
					yValue=180;
				}
			}
			if(faciing==Direction.SOUTH) {
				if(face==AttachFace.WALL||face==AttachFace.FLOOR) {
					yValue=180;
				}
			}
			if(faciing==Direction.WEST) {
				if(face==AttachFace.WALL||face==AttachFace.FLOOR) {
					yValue=270;
				}
				else {
					yValue=90;
				}
			}
            return ConfiguredModel.builder()
                    .modelFile(powered ? pressed : buttonModel)
                    .uvLock(face==AttachFace.WALL)
                    .rotationX(face == AttachFace.CEILING ? 180 : (face == AttachFace.WALL ? 90 : 0))
                    .rotationY(yValue)
                    .build();
        });
		itemModels().getBuilder("lotr:" + button.getRegistryName().getPath()).parent(inventory);
	}

	private void genDoorBlock(DoorBlock door, String texture) {
		registeredBlocks.add(door);
		ResourceLocation bottom = lotrExtendedLoc(texture+"_bottom");
		ResourceLocation top = lotrExtendedLoc(texture+"_top");
		ModelFile bottomLeft = models().doorBottomLeft("lotrextended:"+door.getRegistryName().getPath() + "_bottom", bottom, top);
        ModelFile bottomRight = models().doorBottomRight("lotrextended:"+door.getRegistryName().getPath() + "_bottom_hinge", bottom, top);
        ModelFile topLeft = models().doorTopLeft("lotrextended:"+door.getRegistryName().getPath() + "_top", bottom, top);
        ModelFile topRight = models().doorTopRight("lotrextended:"+door.getRegistryName().getPath() + "_top_hinge", bottom, top);
        doorBlock(door, bottomLeft, bottomRight, topLeft, topRight);
	}

	private void genSignBlock(LOTRStandingSignBlock sign, ResourceLocation texture) {
		registeredBlocks.add(sign);
		ModelFile model = models().getBuilder("lotrextended:"+sign.getRegistryName().getPath()).texture("particle", texture);
		simpleBlock(sign, model);
	}

	private void genWallSignBlock(LOTRWallSignBlock wallSign, ResourceLocation texture) {
		registeredBlocks.add(wallSign);
		ModelFile model = models().getBuilder("lotrextended:"+wallSign.getRegistryName().getPath()).texture("particle", texture);
		simpleBlock(wallSign, model);
	}

	private void genFenceBlockItem(LOTRFenceBlock fence, ResourceLocation texture) {
		registeredBlocks.add(fence);
		ModelFile post = models().fencePost("lotrextended:"+fence.getRegistryName().getPath()+"_post", texture);
		ModelFile side = models().fenceSide("lotrextended:"+fence.getRegistryName().getPath()+"_side", texture);
		ModelFile inventory = models().fenceInventory("lotrextended:"+fence.getRegistryName().getPath()+"_inventory", texture);
        fourWayBlock(fence, post, side);
		itemModels().getBuilder("lotr:" + fence.getRegistryName().getPath()).parent(inventory);
	}

	private void genFenceGateBlockItem(LOTRFenceGateBlock gateBlock, ResourceLocation texture) {
		registeredBlocks.add(gateBlock);
        ModelFile gate = models().fenceGate("lotrextended:"+gateBlock.getRegistryName().getPath(), texture);
        ModelFile gateOpen = models().fenceGateOpen("lotrextended:"+gateBlock.getRegistryName().getPath()+"_open", texture);
        ModelFile gateWall = models().fenceGateWall("lotrextended:"+gateBlock.getRegistryName().getPath()+"_wall", texture);
        ModelFile gateWallOpen = models().fenceGateWallOpen("lotrextended:"+gateBlock.getRegistryName().getPath()+"_wall_open", texture);
        fenceGateBlock(gateBlock, gate, gateOpen, gateWall, gateWallOpen);
		itemModels().getBuilder("lotr:" + gateBlock.getRegistryName().getPath()).parent(gate);
	}

	private void genTrapdoorBlockItem(TrapDoorBlock trapdoor, String location) {
		registeredBlocks.add(trapdoor);
		ResourceLocation texture = lotrExtendedLoc(location);
		ModelFile bottom = models().trapdoorOrientableBottom("lotrextended:"+trapdoor.getRegistryName().getPath()+"_bottom", texture);
        ModelFile top = models().trapdoorOrientableTop("lotrextended:"+trapdoor.getRegistryName().getPath()+"_top", texture);
        ModelFile open = models().trapdoorOrientableOpen("lotrextended:"+trapdoor.getRegistryName().getPath()+"_open", texture);
		trapdoorBlock(trapdoor, bottom, top, open, true);
		itemModels().getBuilder("lotr:" + trapdoor.getRegistryName().getPath()).parent(bottom);
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
				if(below) {
					selectedfile = pillarMiddle;
				}
				else {
					selectedfile = pillarBottom;
				}
			}
			else {
				if(below) {
					selectedfile = pillarTop;
				}
				else {
					selectedfile = pillarBlock;
				}
			}
			return ConfiguredModel.builder()
					.modelFile(selectedfile)
					.rotationX(axis == Axis.Z ? 270 : (axis == Axis.X ? 90 : 0))
					.rotationY(axis == Axis.X ? 90 : 0)
					.build();
		});
		itemModels().getBuilder("lotr:"+pillarB.getRegistryName().getPath()).parent(pillarBlock);
	}

	private void genSpecialPillarBlockItem(ExtendedThreeTallPillarBlock pillarB, String name) {
		registeredBlocks.add(pillarB);
		ResourceLocation face = lotrExtendedLoc(name+"_face");
		ResourceLocation pillar = lotrExtendedLoc(name+"_single");
		ResourceLocation top_tall = lotrExtendedLoc(name+"_tall_top");
		ResourceLocation middle_tall = lotrExtendedLoc(name+"_tall_middle");
		ResourceLocation bottom_tall = lotrExtendedLoc(name+"_tall_bottom");
		ResourceLocation top = lotrExtendedLoc(name+"_top");
		ResourceLocation bottom = lotrExtendedLoc(name+"_bottom");

		ModelFile pillarBottom = models().cubeColumn("lotrextended:"+pillarB.getRegistryName().getPath()+"_bottom", bottom, face);
		ModelFile pillarTop = models().cubeColumn("lotrextended:"+pillarB.getRegistryName().getPath()+"_top", top, face);

		ModelFile pillarTallBottom = models().cubeColumn("lotrextended:"+pillarB.getRegistryName().getPath()+"_tall_bottom", bottom_tall, face);
		ModelFile pillarTallMiddle = models().cubeColumn("lotrextended:"+pillarB.getRegistryName().getPath()+"_tall_middle", middle_tall, face);
		ModelFile pillarTallTop = models().cubeColumn("lotrextended:"+pillarB.getRegistryName().getPath()+"_tall_top", top_tall, face);

		ModelFile pillarBlock = models().cubeColumn("lotrextended:"+pillarB.getRegistryName().getPath(), pillar, face);

		getVariantBuilder(pillarB)
		.forAllStatesExcept(state -> {
			Axis axis = state.getValue(ExtendedThreeTallPillarBlock.AXIS);
			PillarType above = state.getValue(ExtendedThreeTallPillarBlock.PILLAR_TYPE);
			ModelFile selectedfile = null;
			if(above.equals(PillarType.BOTTOM_LARGE)) {
				selectedfile = pillarTallBottom;
			}else if(above.equals(PillarType.BOTTOM_SMALL)) {
				selectedfile = pillarBottom;
			}else if(above.equals(PillarType.MIDDLE_LARGE)) {
				selectedfile = pillarTallMiddle;
			}else if(above.equals(PillarType.TOP_LARGE)) {
				selectedfile = pillarTallTop;
			}else if(above.equals(PillarType.TOP_SMALL)) {
				selectedfile = pillarTop;
			}else {
				selectedfile = pillarBlock;
			}
			return ConfiguredModel.builder()
					.modelFile(selectedfile)
					.rotationX(axis == Axis.Z ? 270 : (axis == Axis.X ? 90 : 0))
					.rotationY(axis == Axis.X ? 90 : 0)
					.build();
		});
		itemModels().getBuilder("lotr:"+pillarB.getRegistryName().getPath()).parent(pillarBlock);
	}

	private void genSlabBlockItem(SlabBlock slab, ResourceLocation texture) {
		genSlabBlockItem(slab, texture, texture, texture, texture);
	}

	private void genSlabBlockItemWOOD(SlabBlock slab, ResourceLocation texture) {
		ModelFile slabDouble = models().getExistingFile(texture);
		genSlabBlockItem(slab, texture, texture, texture, slabDouble, false, false);
	}


	private void genSlabBlockItem(SlabBlock slab, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation doubleModel) {
		ModelFile slabDouble = models().getExistingFile(doubleModel);
		genSlabBlockItem(slab, side, bottom, top, slabDouble, false, true);
	}

	private void genSlabBlockItem(SlabBlock slab, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation doubleModel, boolean tint, boolean notwood) {
		ModelFile slabDouble = models().getExistingFile(doubleModel);
		genSlabBlockItem(slab, side, bottom, top, slabDouble, tint, notwood);
	}

	private void genSlabBlockItem(SlabBlock slab, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ModelFile doubleModel, boolean tint, boolean notwood) {
		registeredBlocks.add(slab);
		ModelFile slabModel;
		ModelFile slabModelTop;
		if(tint) {
			slabModel = models().withExistingParent("lotrextended:"+slab.getRegistryName().getPath(), lotrExtendedLoc("block/tinted_slab"))
					.texture("side", side)
	                .texture("bottom", bottom)
	                .texture("top", top);
		}
		else {
			slabModel = models().slab("lotrextended:" + slab.getRegistryName().getPath(), side, bottom, top);
		}
		if(tint) {
			slabModelTop = models().withExistingParent("lotrextended:"+slab.getRegistryName().getPath()+"_top", lotrExtendedLoc("block/tinted_slab_top"))
					.texture("side", side)
	                .texture("bottom", bottom)
	                .texture("top", top);
		}
		else {
			slabModelTop = models().slabTop("lotrextended:" + slab.getRegistryName().getPath()+"_top", side, bottom, top);
		}

		getVariantBuilder(slab)
		.forAllStatesExcept(state -> {
			Axis axis = state.getValue(LOTRBlockStates.SLAB_AXIS);
			SlabType type = state.getValue(AxialSlabBlock.TYPE);
            boolean uvlock = axis != Axis.Y;
            return ConfiguredModel.builder()
                    .modelFile(type == SlabType.DOUBLE ? doubleModel : (type == SlabType.BOTTOM ? slabModel : slabModelTop))
                    .rotationX(axis == Axis.Z ? 270 : (axis == Axis.X ? 90 : 0))
                    .rotationY(axis == Axis.X ? 90 : 0)
                    .uvLock(uvlock && notwood)
                    .build();
        }, SlabBlock.WATERLOGGED);
		itemModels().getBuilder("lotr:" + slab.getRegistryName().getPath()).parent(slabModel);
	}

	private void genRopeBlock(ExtendedRopeBlock rope) {
		registeredBlocks.add(rope);
		ResourceLocation ropeTexture = lotrExtendedLoc("block/"+rope.getRegistryName().getPath());
		ModelFile modelTop = models().withExistingParent("lotrextended:"+rope.getRegistryName().getPath()+"_top", lotrExtendedLoc("block/template_rope_top"))
                .texture("0", ropeTexture)
                .texture("particle", ropeTexture);
		ModelFile modelBottom = models().withExistingParent("lotrextended:"+rope.getRegistryName().getPath()+"_bottom", lotrExtendedLoc("block/template_rope_bottom"))
                .texture("0", ropeTexture)
                .texture("particle", ropeTexture);
		ModelFile modelMiddle = models().withExistingParent("lotrextended:"+rope.getRegistryName().getPath()+"_middle", lotrExtendedLoc("block/template_rope_middle"))
                .texture("0", ropeTexture)
                .texture("particle", ropeTexture);
		ModelFile modelSingle = models().withExistingParent("lotrextended:"+rope.getRegistryName().getPath()+"_single", lotrExtendedLoc("block/template_rope_single"))
				.texture("0", ropeTexture)
				.texture("particle", ropeTexture);

		getVariantBuilder(rope)
		.forAllStatesExcept(state -> {
			Direction axis = state.getValue(ExtendedRopeBlock.FACING);
			RopeType type = state.getValue(ExtendedRopeBlock.SECTION_TYPE);
            return ConfiguredModel.builder()
                    .modelFile((type == RopeType.TOP_LARGE || type == RopeType.TOP_SMALL) ? modelTop : 
                    	((type == RopeType.BOTTOM_LARGE || type == RopeType.BOTTOM_SMALL) ? modelBottom : 
                    	type == RopeType.SINGLE ? modelSingle : modelMiddle))
                    .rotationY(axis == Direction.EAST ? 90 : (axis == Direction.WEST ? 270 : (axis == Direction.SOUTH ? 180 : 0)))
                    .build();
        }, ExtendedRopeBlock.WATERLOGGED);
	}

	private void genMillstoneBlock(ExtendedGrinderBlock stone_grinder) {
		registeredBlocks.add(stone_grinder);
		ModelFile model = models().withExistingParent("lotrextended:"+stone_grinder.getRegistryName().getPath(), lotrExtendedLoc("block/template_millstone"))
                .texture("toptexture", lotrExtendedLoc("block/"+stone_grinder.getRegistryName().getPath()+"_top"))
                .texture("basetexture", lotrExtendedLoc("block/"+stone_grinder.getRegistryName().getPath()+"_base"))
                .texture("particle", lotrExtendedLoc("block/"+stone_grinder.getRegistryName().getPath()+"_base"));
		ModelFile modelPowered = models().withExistingParent("lotrextended:"+stone_grinder.getRegistryName().getPath()+"_powered", lotrExtendedLoc("block/template_millstone"))
                .texture("toptexture", lotrExtendedLoc("block/"+stone_grinder.getRegistryName().getPath()+"_top_animated"))
                .texture("basetexture", lotrExtendedLoc("block/"+stone_grinder.getRegistryName().getPath()+"_base"))
                .texture("particle", lotrExtendedLoc("block/"+stone_grinder.getRegistryName().getPath()+"_base"));

		getVariantBuilder(stone_grinder)
		.forAllStatesExcept(state -> {
			boolean powered = state.getValue(ExtendedGrinderBlock.POWERED);
            return ConfiguredModel.builder()
                    .modelFile(powered ? modelPowered : model)
                    .build();
        }, ExtendedGrinderBlock.FACING);
		itemModels().getBuilder("lotr:" + stone_grinder.getRegistryName().getPath()).parent(model);
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
		ResourceLocation base = lotrExtendedLoc("block/"+extendedGroundFarmlandBlock.getBase().get().getRegistryName().getPath());
		ResourceLocation parent = mcLoc("block/grass_path");
		ModelFile grass = models().withExistingParent("lotrextended:"+extendedGroundFarmlandBlock.getRegistryName().getPath(), parent)
                .texture("particle", base)
                .texture("bottom", base)
                .texture("top", lotrExtendedLoc(name))
                .texture("side", base);
		simpleBlock(extendedGroundFarmlandBlock, grass);
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
		ResourceLocation down1 = lotrExtendedLoc(name+"_bottom_alt");
		ResourceLocation up1 = lotrExtendedLoc(name+"_top_alt");
		ResourceLocation north1 = lotrExtendedLoc(name+"_side_1_alt");
		ResourceLocation south1 = lotrExtendedLoc(name+"_side_1_alt");
		ResourceLocation east1 = lotrExtendedLoc(name+"_side_2_alt");
		ResourceLocation west1 = lotrExtendedLoc(name+"_side_2_alt");
		ModelFile cubeModel1 = models().cube("lotrextended:"+table.getRegistryName().getPath()+"_alt", down1, up1, north1, south1, east1, west1).texture("particle", north1);
		getVariantBuilder(table)
		.forAllStatesExcept(state -> {
			Boolean type = state.getValue(ExtendedTwoTypeCraftingTableBlock.ALTERNATITE);
            return ConfiguredModel.builder()
                    .modelFile(type ? cubeModel1 : cubeModel)
                    .build();
        });
		itemModels().getBuilder("lotr:"+table.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genHangingLanternBlockItem(ExtendedLanternBlock lantern, String name) {
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
			Boolean hanging = state.getValue(ExtendedLanternBlock.HANGING);
            return ConfiguredModel.builder()
                    .modelFile(hanging ? hangingModel : lanternModel)
                    .build();
        });
	}

	private void genVineBlock(ExtendedVineBlock vine, String name) {
		registeredBlocks.add(vine);
		ModelFile m_u = models().getExistingFile(lotrExtendedLoc(name+"_u"));
		ModelFile m_1 = models().getExistingFile(lotrExtendedLoc(name+"_1"));
		ModelFile m_1u = models().getExistingFile(lotrExtendedLoc(name+"_1u"));
		ModelFile m_2 = models().getExistingFile(lotrExtendedLoc(name+"_2"));
		ModelFile m_2_opposite = models().getExistingFile(lotrExtendedLoc(name+"_2_opposite"));
		ModelFile m_2u = models().getExistingFile(lotrExtendedLoc(name+"_2u"));
		ModelFile m_2u_opposite = models().getExistingFile(lotrExtendedLoc(name+"_2u_opposite"));
		ModelFile m_3 = models().getExistingFile(lotrExtendedLoc(name+"_3"));
		ModelFile m_3u = models().getExistingFile(lotrExtendedLoc(name+"_3u"));
		ModelFile m_4 = models().getExistingFile(lotrExtendedLoc(name+"_4"));
		ModelFile m_4u = models().getExistingFile(lotrExtendedLoc(name+"_4u"));
		getVariantBuilder(vine)
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, false).with(VineBlock.UP, false).with(VineBlock.WEST, false).modelForState().modelFile(m_1).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, false).with(VineBlock.UP, false).with(VineBlock.WEST, true).modelForState().rotationY(90).modelFile(m_1).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, false).with(VineBlock.UP, true).with(VineBlock.WEST, false).modelForState().modelFile(m_u).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, false).with(VineBlock.UP, true).with(VineBlock.WEST, true).modelForState().rotationY(90).modelFile(m_1u).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, true).with(VineBlock.UP, false).with(VineBlock.WEST, false).modelForState().modelFile(m_1).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, true).with(VineBlock.UP, false).with(VineBlock.WEST, true).modelForState().rotationY(180).modelFile(m_2).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, true).with(VineBlock.UP, true).with(VineBlock.WEST, false).modelForState().modelFile(m_1u).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, true).with(VineBlock.UP, true).with(VineBlock.WEST, true).modelForState().rotationY(180).modelFile(m_2u).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, false).with(VineBlock.UP, false).with(VineBlock.WEST, false).modelForState().rotationY(180).modelFile(m_1).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, false).with(VineBlock.UP, false).with(VineBlock.WEST, true).modelForState().rotationY(270).modelFile(m_2).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, false).with(VineBlock.UP, true).with(VineBlock.WEST, false).modelForState().rotationY(180).modelFile(m_1u).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, false).with(VineBlock.UP, true).with(VineBlock.WEST, true).modelForState().rotationY(270).modelFile(m_2u).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, true).with(VineBlock.UP, false).with(VineBlock.WEST, false).modelForState().rotationY(90).modelFile(m_2_opposite).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, true).with(VineBlock.UP, false).with(VineBlock.WEST, true).modelForState().rotationY(180).modelFile(m_3).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, true).with(VineBlock.UP, true).with(VineBlock.WEST, false).modelForState().rotationY(90).modelFile(m_2u_opposite).addModel()
		.partialState().with(VineBlock.EAST, false).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, true).with(VineBlock.UP, true).with(VineBlock.WEST, true).modelForState().rotationY(180).modelFile(m_3u).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, false).with(VineBlock.UP, false).with(VineBlock.WEST, false).modelForState().rotationY(270).modelFile(m_1).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, false).with(VineBlock.UP, false).with(VineBlock.WEST, true).modelForState().modelFile(m_2_opposite).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, false).with(VineBlock.UP, true).with(VineBlock.WEST, false).modelForState().rotationY(270).modelFile(m_1u).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, false).with(VineBlock.UP, true).with(VineBlock.WEST, true).modelForState().modelFile(m_2u_opposite).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, true).with(VineBlock.UP, false).with(VineBlock.WEST, false).modelForState().rotationY(90).modelFile(m_2).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, true).with(VineBlock.UP, false).with(VineBlock.WEST, true).modelForState().rotationY(90).modelFile(m_3).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, true).with(VineBlock.UP, true).with(VineBlock.WEST, false).modelForState().rotationY(90).modelFile(m_2u).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, false).with(VineBlock.SOUTH, true).with(VineBlock.UP, true).with(VineBlock.WEST, true).modelForState().rotationY(90).modelFile(m_3u).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, false).with(VineBlock.UP, false).with(VineBlock.WEST, false).modelForState().modelFile(m_2).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, false).with(VineBlock.UP, false).with(VineBlock.WEST, true).modelForState().rotationY(270).modelFile(m_3).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, false).with(VineBlock.UP, true).with(VineBlock.WEST, false).modelForState().modelFile(m_2u).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, false).with(VineBlock.UP, true).with(VineBlock.WEST, true).modelForState().rotationY(270).modelFile(m_3u).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, true).with(VineBlock.UP, false).with(VineBlock.WEST, false).modelForState().modelFile(m_3).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, true).with(VineBlock.UP, false).with(VineBlock.WEST, true).modelForState().modelFile(m_4).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, true).with(VineBlock.UP, true).with(VineBlock.WEST, false).modelForState().modelFile(m_3u).addModel()
		.partialState().with(VineBlock.EAST, true).with(VineBlock.NORTH, true).with(VineBlock.SOUTH, true).with(VineBlock.UP, true).with(VineBlock.WEST, true).modelForState().modelFile(m_4u).addModel();
	}

	private void genBedBlockItem(ExtendedBedBlock bed, String name) {
		registeredBlocks.add(bed);
		ModelFile modelFile = models().withExistingParent("lotrextended:"+bed.getRegistryName().getPath(), mcLoc("block/bed")).texture("particle", lotrExtendedLoc("entity/bed/"+name));
		simpleBlock(bed, modelFile);
		itemModels().getBuilder("lotr:"+bed.getRegistryName().getPath()).texture("particle", lotrExtendedLoc("entity/bed/"+name)).parent(models().getExistingFile(mcLoc("item/template_bed")));
	}

	private void genCarpetBlockItem(ExtendedWargCarpetBlock carpet, String name) {
		registeredBlocks.add(carpet);
		ModelFile modelFile = models().withExistingParent("lotrextended:"+carpet.getRegistryName().getPath(), lotrExtendedLoc("block/template_fur_carpet"))
									.texture("0", lotrExtendedLoc("block/"+name)).texture("particle", lotrExtendedLoc("block/"+name));
		simpleBlock(carpet, modelFile);
		itemModels().getBuilder("lotr:"+carpet.getRegistryName().getPath()).parent(modelFile);
	}

	private void genMossCarpetBlockItem(ExtendedMossCarpetBlock carpet) {
		registeredBlocks.add(carpet);
		ModelFile modelFile = models().withExistingParent("lotrextended:"+carpet.getRegistryName().getPath(), lotrExtendedLoc("block/template_moss_carpet"))
			.texture("block", lotrExtendedLoc("block/moss_block")).texture("particle", lotrExtendedLoc("block/moss_block"))
			.texture("side", lotrExtendedLoc("block/moss_carpet_side"));
		ModelFile modelFileDisplay = models().withExistingParent("lotrextended:"+carpet.getRegistryName().getPath()+"_display", lotrExtendedLoc("block/template_moss_carpet_display"))
			.texture("block", lotrExtendedLoc("block/moss_block_display")).texture("particle", lotrExtendedLoc("block/moss_block_display"))
			.texture("side", lotrExtendedLoc("block/moss_carpet_side_display"));
		simpleBlock(carpet, modelFile);
		itemModels().getBuilder("lotr:"+carpet.getRegistryName().getPath()).parent(modelFileDisplay);
	}

	private void genHangingBlock(ExtendedHangingBlock bed, ResourceLocation texture) {
		registeredBlocks.add(bed);
		ModelFile modelFile = models().withExistingParent("lotrextended:"+bed.getRegistryName().getPath(), lotrExtendedLoc("block/template_hanging_block_crossed"))
				.texture("particle", texture).texture("hanging", texture);
		simpleBlock(bed, modelFile);
	}

	private void genBannerBlock(ExtendedMEBannerBlock extendedMEBannerBlock, ResourceLocation texture) {
		registeredBlocks.add(extendedMEBannerBlock);
		ModelFile modelFileSmallBottom = models().withExistingParent("lotrextended:"+extendedMEBannerBlock.getRegistryName().getPath()+"_small_bottom", lotrExtendedLoc("block/template_banner_small_bottom"))
				.texture("particle", texture).texture("1", texture);
		ModelFile modelFileSmallTop = models().withExistingParent("lotrextended:"+extendedMEBannerBlock.getRegistryName().getPath()+"_small_top", lotrExtendedLoc("block/template_banner_small_top"))
				.texture("particle", texture).texture("1", texture);
		ModelFile modelFileBottom = models().withExistingParent("lotrextended:"+extendedMEBannerBlock.getRegistryName().getPath()+"_bottom", lotrExtendedLoc("block/template_banner_bottom"))
				.texture("particle", texture).texture("1", texture);
		ModelFile modelFileTop = models().withExistingParent("lotrextended:"+extendedMEBannerBlock.getRegistryName().getPath()+"_top", lotrExtendedLoc("block/template_banner_top"))
				.texture("particle", texture).texture("1", texture);
		getVariantBuilder(extendedMEBannerBlock).forAllStates(state -> {
			Direction direction = state.getValue(ExtendedMEBannerBlock.FACING);
			DoubleBlockHalf half = state.getValue(ExtendedMEBannerBlock.HALF);
			Boolean small = state.getValue(ExtendedMEBannerBlock.SMALL);
			return ConfiguredModel.builder()
                    .modelFile(small&&half==DoubleBlockHalf.LOWER ? modelFileSmallBottom : 
                    	(small&&half==DoubleBlockHalf.UPPER ? modelFileSmallTop : 
                    		(!small&&half==DoubleBlockHalf.LOWER ? modelFileBottom : modelFileTop)))
                    .rotationY(direction==Direction.EAST ? 90 : direction==Direction.SOUTH ? 180 : direction==Direction.WEST ? 270 : 0)
                    .build();
		});
	}

	private void genBannerWallBlock(ExtendedMEBannerWallBlock banner, ResourceLocation texture) {
		registeredBlocks.add(banner);
		ModelFile modelFile = models().withExistingParent("lotrextended:"+banner.getRegistryName().getPath(), lotrExtendedLoc("block/template_banner_wall"))
				.texture("particle", texture).texture("banner", texture);
		getVariantBuilder(banner).forAllStates(state -> {
			Direction direction = state.getValue(ExtendedMEBannerWallBlock.FACING);
			return ConfiguredModel.builder()
                    .modelFile(modelFile)
                    .rotationY(direction==Direction.EAST ? 90 : direction==Direction.SOUTH ? 180 : direction==Direction.WEST ? 270 : 0)
                    .build();
		});
	}

	private void genFallenLeavesBlock(FallenLeavesBlock bed, String name) {
		registeredBlocks.add(bed);
		try {
			ModelFile modelFile = models().withExistingParent("lotrextended:"+bed.getRegistryName().getPath(), lotrLoc("block/template_fallen_leaves")).texture("leaves", lotrExtendedLoc("block/"+name));
			simpleBlock(bed, modelFile);
		}catch(IllegalArgumentException e) {
			ModelFile modelFile = models().withExistingParent("lotrextended:"+bed.getRegistryName().getPath(), lotrLoc("block/template_fallen_leaves")).texture("leaves", lotrExtendedLoc("block/"+name+"_0"));
			simpleBlock(bed, modelFile);
		}
	}

	private void genChandelierBlock(ChandelierBlock chandelier, String name) {
		registeredBlocks.add(chandelier);
		try {
			ModelFile modelFile = models().withExistingParent("lotrextended:"+chandelier.getRegistryName().getPath(), lotrLoc("block/template_chandelier")).texture("chandelier", lotrExtendedLoc("block/"+name)).texture("chandelier_chain", lotrExtendedLoc("block/chandelier_chain_"+name.substring(0, name.length() - 11)));
			simpleBlock(chandelier, modelFile);
		}catch(IllegalArgumentException e) {
			System.out.println("Using default chain model for "+name+", chandelier_chain_"+name.substring(0, name.length() - 11)+" not found.");
			ModelFile modelFile = models().withExistingParent("lotrextended:"+chandelier.getRegistryName().getPath(), lotrLoc("block/template_chandelier")).texture("chandelier", lotrExtendedLoc("block/"+name));
			simpleBlock(chandelier, modelFile);
		}
	}

	private void genReedBarrelBlockItem(ExtendedReedBasketBlock extendedReedBasketBlock, String name) {
		registeredBlocks.add(extendedReedBasketBlock);
		ModelFile modelFile = models().withExistingParent("lotrextended:"+extendedReedBasketBlock.getRegistryName().getPath(), lotrExtendedLoc("block/template_reed_basket_shape")).texture("1", lotrExtendedLoc("entity/chest/"+name)).texture("particle", lotrLoc("block/reed_thatch"));
		ModelFile modelFile_open = models().withExistingParent("lotrextended:"+extendedReedBasketBlock.getRegistryName().getPath()+"_open", lotrExtendedLoc("block/template_reed_basket_shape")).texture("1", lotrExtendedLoc("entity/chest/"+name+"_open")).texture("particle", lotrLoc("block/reed_thatch"));
		getVariantBuilder(extendedReedBasketBlock).forAllStates(state -> {
			Direction direction = state.getValue(ExtendedBarrelBlock.FACING);
			Boolean open = state.getValue(ExtendedBarrelBlock.OPEN);
			return ConfiguredModel.builder()
                    .modelFile(open ? modelFile_open : modelFile)
                    .rotationX(direction==Direction.DOWN ? 180 : direction==Direction.UP ? 0 : 90)
                    .rotationY(direction==Direction.EAST ? 90 : direction==Direction.SOUTH ? 180 : direction==Direction.WEST ? 270 : 0)
                    .build();
		});
		itemModels().getBuilder("lotr:"+extendedReedBasketBlock.getRegistryName().getPath()).parent(modelFile);
	}

	private void genLargePotBlockItem(ExtendedLargePotBlock pot, String key) {
		registeredBlocks.add(pot);
		ModelFile modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_pot")).texture("pot", lotrExtendedLoc(key)).texture("particle", lotrExtendedLoc(key));
		getVariantBuilder(pot).forAllStates(state -> {
			Direction facing = state.getValue(ExtendedLargePotBlock.FACING);
			return ConfiguredModel.builder()
                    .modelFile(modelFile)
                    .rotationY(facing==Direction.EAST ? 90 : facing==Direction.SOUTH ? 180 : facing==Direction.WEST ? 270 : 0)
                    .build();
		});
		itemModels().getBuilder("lotr:"+pot.getRegistryName().getPath()).parent(modelFile);
	}

	private void genLargeFlowerPotBlockItem(ExtendedLargeFlowerPotBlock pot, String key) {
		registeredBlocks.add(pot);
		if(pot.equals(ExtendedBlocks.LARGE_FLOWER_POT.get())) {
			ModelFile modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot")).texture("pot", lotrExtendedLoc(key)).texture("particle", lotrExtendedLoc(key));
			getVariantBuilder(pot).forAllStates(state -> {
				Direction facing = state.getValue(ExtendedLargePotBlock.FACING);
				return ConfiguredModel.builder()
	                    .modelFile(modelFile)
	                    .rotationY(facing==Direction.EAST ? 90 : facing==Direction.SOUTH ? 180 : facing==Direction.WEST ? 270 : 0)
	                    .build();
			});
			itemModels().getBuilder("lotr:"+pot.getRegistryName().getPath()).parent(modelFile);
		}else {
			BlockModelBuilder modelFile = null;
			if(pot.getContent().getRegistryName().getNamespace().equals("minecraft")) {
				if(pot.getContent() instanceof CactusBlock) {
					modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_cactus"))
							.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"));
				}else if(pot.getContent() instanceof BambooBlock) {
					modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_bamboo"))
							.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"));
				}else if(pot.getContent() instanceof NetherRootsBlock) {
					modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_crossed"))
							.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("plant", new ResourceLocation("minecraft:block/"+pot.getContent().getRegistryName().getPath()+"_pot"));
				}else if(pot.getContent().getRegistryName().getPath().contains("fern")) {
					modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_crossed_tinted"))
							.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("plant", new ResourceLocation("minecraft:block/"+pot.getContent().getRegistryName().getPath()));
				}else {
					modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_crossed"))
							.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("plant", new ResourceLocation("minecraft:block/"+pot.getContent().getRegistryName().getPath()));
				}
			}else {
				if(pot.getContent().getRegistryName().getPath().contains("athelas")||pot.getContent() instanceof WildPipeweedBlock) {
					if(pot.getContent().getRegistryName().getPath().contains("athelas")) {
						modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_herb"))
								.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
								.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
								.texture("plant", new ResourceLocation("lotr:block/athelas"))
								.texture("leaf", new ResourceLocation("lotr:block/athelas_leaf_small"));
					}else {
						modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_herb"))
								.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
								.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
								.texture("plant", new ResourceLocation("lotr:block/wild_pipeweed"))
								.texture("leaf", new ResourceLocation("lotr:block/pipeweed_crop_stage1"));
					}
				}else if(pot.getContent().getRegistryName().getPath().contains("fernsprout")) {
					modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_crossed_tinted"))
							.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("plant", new ResourceLocation("lotr:block/"+pot.getContent().getRegistryName().getPath()));
				}else if(pot.getContent() instanceof ThistleBlock) {
					modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_herb_overlay"))
							.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("plant", new ResourceLocation("lotr:block/thistle"))
							.texture("leaf", new ResourceLocation("lotr:block/thistle_leaf_small"))
							.texture("plant_overlay", new ResourceLocation("lotr:block/thistle_overlay"));
				}else if(pot.getContent() instanceof NettleBlock) {
					modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_nettles"))
							.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("stem", new ResourceLocation("lotr:block/nettles_stem"))
							.texture("top", new ResourceLocation("lotr:block/nettles_top"))
							.texture("middle", new ResourceLocation("lotr:block/nettles_middle"))
							.texture("bottom", new ResourceLocation("lotr:block/nettles_bottom"));
				}else if(pot.getContent() instanceof FourLeafCloverBlock) {
					modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_clover_four"))
							.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("stem", new ResourceLocation("lotr:block/clover_stem"))
							.texture("petals", new ResourceLocation("lotr:block/clover_petals"));
				}else if(pot.getContent() instanceof ThreeLeafCloverBlock) {
					modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_clover"))
							.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
							.texture("stem", new ResourceLocation("lotr:block/clover_stem"))
							.texture("petals", new ResourceLocation("lotr:block/clover_petals"));
				}else {
					try {
						modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_crossed"))
								.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
								.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
								.texture("plant", new ResourceLocation("lotr:block/"+pot.getContent().getRegistryName().getPath()));
					}catch(IllegalArgumentException e) {
						modelFile = models().withExistingParent("lotrextended:"+pot.getRegistryName().getPath(), lotrExtendedLoc("block/template_large_flower_pot_crossed"))
								.texture("pot", new ResourceLocation("lotrextended:block/large_flower_pot"))
								.texture("particle", new ResourceLocation("lotrextended:block/large_flower_pot"))
								.texture("plant", new ResourceLocation("lotrextended:block/"+pot.getContent().getRegistryName().getPath()));
					}
				}
			}
			if(modelFile==null) {
				ExtendedLog.error("Missing Pot Model for "+pot.getContent().getRegistryName().toString());
				return;
			}
			BlockModelBuilder modelFileFinal = modelFile;
			getVariantBuilder(pot).forAllStates(state -> {
				Direction facing = state.getValue(ExtendedLargePotBlock.FACING);
				return ConfiguredModel.builder()
	                    .modelFile(modelFileFinal)
	                    .rotationY(facing==Direction.EAST ? 90 : facing==Direction.SOUTH ? 180 : facing==Direction.WEST ? 270 : 0)
	                    .build();
			});
		}
	}

	private void genAncientRuneBlockItem(ExtendedAncientStoneRuneBlock runeStone, String name) {
		registeredBlocks.add(runeStone);
		ModelFile modelFileItem = models().cubeAll("lotrextended:"+runeStone.getRegistryName().getPath()+"_1", lotrExtendedLoc("block/"+name+"_1"));
		getVariantBuilder(runeStone).forAllStates(state -> {
			int type = state.getValue(ExtendedAncientStoneRuneBlock.TYPE);
			return ConfiguredModel.builder()
                    .modelFile(models().cubeAll("lotrextended:"+runeStone.getRegistryName().getPath()+"_"+type, lotrExtendedLoc("block/"+name+"_"+type)))
                    .build();
		});
		itemModels().getBuilder("lotr:"+runeStone.getRegistryName().getPath()).parent(modelFileItem);
	}

	private void genBarrelBlockItem(ExtendedBarrelBlock extendedReedBasketBlock, String name) {
		registeredBlocks.add(extendedReedBasketBlock);
		ModelFile modelFile = models().cubeBottomTop("lotrextended:"+extendedReedBasketBlock.getRegistryName().getPath(), lotrExtendedLoc("entity/chest/"+name+"_side"), lotrExtendedLoc("entity/chest/"+name+"_bottom"), lotrExtendedLoc("entity/chest/"+name+"_top"));
		ModelFile modelFile_open = models().cubeBottomTop("lotrextended:"+extendedReedBasketBlock.getRegistryName().getPath()+"_open", lotrExtendedLoc("entity/chest/"+name+"_side"), lotrExtendedLoc("entity/chest/"+name+"_bottom"), lotrExtendedLoc("entity/chest/"+name+"_open_top"));
		getVariantBuilder(extendedReedBasketBlock).forAllStates(state -> {
			Direction direction = state.getValue(ExtendedBarrelBlock.FACING);
			Boolean open = state.getValue(ExtendedBarrelBlock.OPEN);
			return ConfiguredModel.builder()
                    .modelFile(open ? modelFile_open : modelFile)
                    .rotationX(direction==Direction.DOWN ? 180 : direction==Direction.UP ? 0 : 90)
                    .rotationY(direction==Direction.EAST ? 90 : direction==Direction.SOUTH ? 180 : direction==Direction.WEST ? 270 : 0)
                    .build();
		});
		itemModels().getBuilder("lotr:"+extendedReedBasketBlock.getRegistryName().getPath()).parent(modelFile);
	}

	private void genBedrollBlockItem(ExtendedFullBedBlock bed, String name) {
		registeredBlocks.add(bed);
		ModelFile head = models().getExistingFile(lotrExtendedLoc("block/bedroll_head"));
		ModelFile foot = models().getExistingFile(lotrExtendedLoc("block/bedroll_foot"));
		getVariantBuilder(bed).forAllStates(state -> {
			Direction bites = state.getValue(ExtendedFullBedBlock.FACING);
			BedPart part = state.getValue(ExtendedFullBedBlock.PART);
			return ConfiguredModel.builder()
                    .modelFile(part == BedPart.HEAD ? head : foot)
                    .rotationY(bites == Direction.EAST ? 270 : (bites == Direction.SOUTH ? 0 : (bites == Direction.WEST ? 90 : 180)))
                    .build();
		});
		itemModels().getBuilder("lotr:"+bed.getRegistryName().getPath()).texture("0", lotrExtendedLoc("entity/bed/"+name)).texture("particle", lotrExtendedLoc("entity/bed/"+name)).parent(models().getExistingFile(lotrExtendedLoc("item/template_bedroll")));
	}

	private void genCakeBlock(ExtendedCakeBlock cake, ResourceLocation textureBottom, ResourceLocation textureTop, ResourceLocation textureSide, ResourceLocation textureInside) {
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

	private void genSapplingBlock(LOTRSaplingBlock sapling, ResourceLocation texture) {
		registeredBlocks.add(sapling);
		ModelFile model = models().cross("lotrextended:"+sapling.getRegistryName().getPath(), texture);
		simpleBlock(sapling, model);
	}

	private void genPottedSapplingBlock(LOTRPottedPlantBlock sapling, ResourceLocation texture) {
		registeredBlocks.add(sapling);
		ResourceLocation parent = mcLoc("block/flower_pot_cross");
		ModelFile model = models().withExistingParent("lotrextended:"+sapling.getRegistryName().getPath(), parent).texture("plant", texture);
		simpleBlock(sapling, model);
	}

	private void genTorchBlockItem(ExtendedLOTRTorchBlock torch, ResourceLocation texture) {
		registeredBlocks.add(torch);
		ModelFile cubeModel = models().torch("lotrextended:"+torch.getRegistryName().getPath(), texture);
		getVariantBuilder(torch).forAllStates(state -> {
			return ConfiguredModel.builder()
                    .modelFile(cubeModel)
                    .build();
		});
	}

	private void genTorchWallBlock(ExtendedLOTRWallTorchBlock torch, ResourceLocation texture) {
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

	private void genWeaponRackBlock(ExtendedWeaponRackBlock weaponRack) {
		registeredBlocks.add(weaponRack);
		ModelFile wall = models().getExistingFile(lotrExtendedLoc("block/weapon_rack"));
		ModelFile ground = models().getExistingFile(lotrExtendedLoc("block/weapon_rack_ground"));
		ModelFile ceiling = models().getExistingFile(lotrExtendedLoc("block/weapon_rack_ceiling"));
		getVariantBuilder(weaponRack).forAllStates(state -> {
			AttachFace face = state.getValue(ExtendedWeaponRackBlock.FACE);
			Direction direction = state.getValue(ExtendedWeaponRackBlock.FACING);
			int yValue = 0;
			if(face==AttachFace.WALL) {
				if(direction==Direction.SOUTH) {
					yValue=180;
				}
				if(direction==Direction.EAST) {
					yValue=90;
				}
				if(direction==Direction.WEST) {
					yValue=270;
				}
			}
			else if(face==AttachFace.CEILING) {
				if(direction==Direction.NORTH) {
					yValue=180;
				}
				if(direction==Direction.EAST) {
					yValue=270;
				}
				if(direction==Direction.WEST) {
					yValue=90;
				}
			}
			else {
				if(direction==Direction.SOUTH) {
					yValue=180;
				}
				if(direction==Direction.EAST) {
					yValue=90;
				}
				if(direction==Direction.WEST) {
					yValue=270;
				}
			}
			return ConfiguredModel.builder()
                    .modelFile(face==AttachFace.WALL ? wall : (face==AttachFace.FLOOR ? ground : ceiling))
                    .rotationY(yValue)
                    .build();
		});
	}

	private void genHearthBlockType(ExtendedHearthBlock block, String base) {
		registeredBlocks.add(block);
		ResourceLocation top = lotrExtendedLoc(base+"_lit_top");
		ResourceLocation bottom = new ResourceLocation("lotr:block/hearth_block_bottom");
		ResourceLocation side = lotrExtendedLoc(base+"_lit_side");
		ModelFile lit = models().cubeBottomTop("lotrextended:"+block.getRegistryName().getPath()+"_lit", side, bottom, top);
		ModelFile unlit = models().getExistingFile(lotrLoc("block/hearth_block"));
		getVariantBuilder(block).forAllStates(state -> {
			Boolean litBoolean = state.getValue(ExtendedHearthBlock.LIT);
			return ConfiguredModel.builder()
                    .modelFile(litBoolean ? lit : unlit)
                    .build();
		});
	}

	private void genBeamBlockItem(WoodBeamBlock block, ResourceLocation resourceLocation) {
		registeredBlocks.add(block);
		ResourceLocation top = lotrExtendedLoc(resourceLocation.getPath()+"_top");
		ModelFile model = models().cubeColumn("lotrextended:"+block.getRegistryName().getPath(), resourceLocation, top);
		getVariantBuilder(block).forAllStates(state -> {
			Axis axis = state.getValue(WoodBeamBlock.AXIS);
			return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(axis==Axis.X ? 90 : 0)
                    .rotationX(axis==Axis.Z||axis==Axis.X ? 90 : 0)
                    .build();
		});
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(model);
	}

	private void genWoodBlockItem(StrippableWoodBlock block, ResourceLocation resourceLocation) {
		registeredBlocks.add(block);
		ModelFile model = models().cubeColumn("lotrextended:"+block.getRegistryName().getPath(), resourceLocation, resourceLocation);
		getVariantBuilder(block).forAllStates(state -> {
			Axis axis = state.getValue(StrippableWoodBlock.AXIS);
			return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(axis==Axis.X ? 90 : 0)
                    .rotationX(axis==Axis.Z||axis==Axis.X ? 90 : 0)
                    .build();
		});
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(model);
	}

	private void genConquestTableBlockItem(ExtendedConquestTableBlock block, String name) {
		registeredBlocks.add(block);
		ModelFile modelFile = models().withExistingParent("lotrextended:"+block.getRegistryName().getPath(), "lotrextended:block/template_conquest_table")
				.texture("table_top", lotrExtendedLoc("block/"+name+"_map")).texture("block", lotrExtendedLoc("block/"+name+"_block"))
				.texture("table_bottom", lotrExtendedLoc("block/"+name+"_bottom")).texture("particle", lotrExtendedLoc("block/"+name+"_map"));
		ModelFile modelSEPTA = models().withExistingParent("lotrextended:"+block.getRegistryName().getPath()+"_sepia", "lotrextended:block/template_conquest_table")
				.texture("table_top", lotrExtendedLoc("block/"+name+"_map_sepia")).texture("block", lotrExtendedLoc("block/"+name+"_block"))
				.texture("table_bottom", lotrExtendedLoc("block/"+name+"_bottom")).texture("particle", lotrExtendedLoc("block/"+name+"_map_sepia"));
		getVariantBuilder(block).forAllStates(state -> {
			Boolean sepia = state.getValue(ExtendedConquestTableBlock.SEPIA);
			Direction facing = state.getValue(ExtendedConquestTableBlock.FACING);
			return ConfiguredModel.builder()
                    .modelFile(sepia ? modelSEPTA : modelFile)
                    .rotationY(facing==Direction.EAST ? 90 : facing==Direction.SOUTH ? 180 : facing==Direction.WEST ? 270 : 0)
                    .build();
		});
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(modelFile);
	}

	private void genSkullGobletBlock(VesselDrinkBlock block) {
		registeredBlocks.add(block);
		ModelFile modelFile = models().getExistingFile(lotrExtendedLoc(block.getRegistryName().getPath()));
		getVariantBuilder(block).forAllStates(state -> {
			Direction direction = state.getValue(VesselDrinkBlock.FACING);
			return ConfiguredModel.builder()
                    .modelFile(modelFile)
                    .rotationY(direction==Direction.EAST ? 90 : direction==Direction.SOUTH ? 180 : direction==Direction.WEST ? 270 : 0)
                    .build();
		});
	}

	private void genStrippedWoodBlockItem(LOTRStrippedWoodBlock block, ResourceLocation resourceLocation) {
		registeredBlocks.add(block);
		ModelFile model = models().cubeColumn("lotrextended:"+block.getRegistryName().getPath(), resourceLocation, resourceLocation);
		getVariantBuilder(block).forAllStates(state -> {
			Axis axis = state.getValue(LOTRStrippedWoodBlock.AXIS);
			return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(axis==Axis.X ? 90 : 0)
                    .rotationX(axis==Axis.Z||axis==Axis.X ? 90 : 0)
                    .build();
		});
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(model);
	}

	private void genLogBlockItem(StrippableLogBlock block, ResourceLocation resourceLocation) {
		registeredBlocks.add(block);
		ResourceLocation top = lotrExtendedLoc(resourceLocation.getPath()+"_top");
		ModelFile model = models().cubeColumn("lotrextended:"+block.getRegistryName().getPath(), resourceLocation, top);
		getVariantBuilder(block).forAllStates(state -> {
			Axis axis = state.getValue(StrippableLogBlock.AXIS);
			return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(axis==Axis.X ? 90 : 0)
                    .rotationX(axis==Axis.Z||axis==Axis.X ? 90 : 0)
                    .build();
		});
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(model);
	}

	private void genStrippedLogBlockItem(LOTRStrippedLogBlock block, ResourceLocation resourceLocation) {
		registeredBlocks.add(block);
		ResourceLocation top = lotrExtendedLoc(resourceLocation.getPath()+"_top");
		ModelFile model = models().cubeColumn("lotrextended:"+block.getRegistryName().getPath(), resourceLocation, top);
		getVariantBuilder(block).forAllStates(state -> {
			Axis axis = state.getValue(LOTRStrippedLogBlock.AXIS);
			return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(axis==Axis.X ? 90 : 0)
                    .rotationX(axis==Axis.Z||axis==Axis.X ? 90 : 0)
                    .build();
		});
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(model);
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

	private void genEndSideNonRotatableBlockItem(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeBottomTop("lotrextended:"+block.getRegistryName().getPath(), side, bottom, top);
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

	private void genCubeTopBlockItem(Block block, ResourceLocation side, ResourceLocation top) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeBottomTop("lotrextended:"+block.getRegistryName().getPath(), side, top, top);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void genChestBlockItem(Block block, String name) {
		registeredBlocks.add(block);
		ModelFile modelFile = models().withExistingParent("lotrextended:"+block.getRegistryName().getPath(), "lotrextended:block/template_chest")
			.texture("texture", lotrExtendedLoc("entity/chest/"+name));
		simpleBlock(block, modelFile);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(modelFile);
	}

	private void genChainBlock(Block block, String name) {
		registeredBlocks.add(block);
		ModelFile modelFile = models().withExistingParent("lotrextended:"+block.getRegistryName().getPath(), "minecraft:block/chain")
			.texture("particle", lotrExtendedLoc(name)).texture("all", lotrExtendedLoc(name));
		getVariantBuilder(block).forAllStatesExcept(state -> {
			Axis axis = state.getValue(ChainBlock.AXIS);
			return ConfiguredModel.builder()
                    .modelFile(modelFile)
                    .rotationY(axis==Axis.X ? 90 : 0)
                    .rotationX(axis==Axis.Z||axis==Axis.X ? 90 : 0)
                    .build();
		}, ChainBlock.WATERLOGGED);
	}

	private void genRandomCubeBlockItem(Block block, ResourceLocation allSides) {
		registeredBlocks.add(block);
		ModelFile cubeModel = models().cubeAll("lotrextended:"+block.getRegistryName().getPath(), allSides);
		simpleBlock(block, cubeModel);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(cubeModel);
	}

	private void numberTexturedRandomBlockItem(Block block, String location, int numVariants) {
		registeredBlocks.add(block);
		if(numVariants==4) {
			ConfiguredModel[] variant1 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_1", lotrExtendedLoc(location+"_1")), 0, false);
			ConfiguredModel[] variant2 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_2", lotrExtendedLoc(location+"_2")), 0, false);
			ConfiguredModel[] variant3 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_3", lotrExtendedLoc(location+"_3")), 0, false);
			ConfiguredModel[] variant4 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_4", lotrExtendedLoc(location+"_4")), 0, false);
			getVariantBuilder(block).partialState().addModels(variant1).addModels(variant2).addModels(variant3).addModels(variant4);
			itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_1", lotrExtendedLoc(location+"_1")));
			return;
		}
		if(numVariants==3) {
			ConfiguredModel[] variant1 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_1", lotrExtendedLoc(location+"_1")), 0, false);
			ConfiguredModel[] variant2 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_2", lotrExtendedLoc(location+"_2")), 0, false);
			ConfiguredModel[] variant3 = ConfiguredModel.allYRotations(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_3", lotrExtendedLoc(location+"_3")), 0, false);
			getVariantBuilder(block).partialState().addModels(variant1).addModels(variant2).addModels(variant3);
			itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(models().cubeAll("lotrextended:"+block.getRegistryName().getPath()+"_1", lotrExtendedLoc(location+"_1")));
			return;
		}
	}

	private void numberTexturedBlockItem(Block block, int[] ints) {
		registeredBlocks.add(block);
		String name = block.getRegistryName().getPath();
		List<ConfiguredModel> variants = new ArrayList<>();
		ModelFile intModel = null;
		for(int i=0; i<ints.length; i++) {
			ModelFile model = null;
			if(name.contains("leaves_fruit")) {
				String[] parts = name.split("_");
				if(parts.length>=3) {
					String baseObject = parts[0]+"_"+parts[1];
					ResourceLocation parent = lotrLoc("block/leaves_fruit");
					model = models().withExistingParent("lotrextended:"+name+"_"+i, parent).texture("end", lotrExtendedLoc("block/"+baseObject+"_0")).texture("side", lotrExtendedLoc("block/"+name+"_"+i));
				}
			} else if(name.contains("leaves_flowers")) {
				String[] parts = name.split("_");
				if(parts.length>=3) {
					String baseObject = parts[0]+"_"+parts[1];
					ResourceLocation parent = lotrLoc("block/leaves_fruit");
					model = models().withExistingParent("lotrextended:"+name+"_"+i, parent).texture("end", lotrExtendedLoc("block/"+baseObject+"_0")).texture("side", lotrExtendedLoc("block/"+name+"_"+i));
				}
			}else {
				model = models().cubeAll("lotrextended:"+name+"_"+i, lotrExtendedLoc("block/"+name+"_"+i));
			}
			if(intModel==null) {
				intModel = model;
			}
			variants.add(new ConfiguredModel(model, 0, 0, false, ints[i]));
		}
		getVariantBuilder(block).partialState().addModels(variants.toArray(new ConfiguredModel[0]));
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(intModel);
	}

	private void genMirroredBlockItem(Block block, ResourceLocation location) {
		registeredBlocks.add(block);
		ModelFile blockModel = models().cubeAll("lotrextended:"+block.getRegistryName().getPath(), location);
		ModelFile blockModelMir = models().singleTexture("lotrextended:"+block.getRegistryName().getPath()+"_mirrored", mcLoc("block/cube_mirrored_all"), "all", location);
		ConfiguredModel Configuredblock = new ConfiguredModel(blockModel);
		ConfiguredModel ConfiguredblockMir = new ConfiguredModel(blockModel, 0, 180, false);
		ConfiguredModel Configuredblock180 = new ConfiguredModel(blockModelMir);
		ConfiguredModel ConfiguredblockMir180 = new ConfiguredModel(blockModelMir, 0, 180, false);
		simpleBlock(block, Configuredblock, ConfiguredblockMir, Configuredblock180, ConfiguredblockMir180);
		itemModels().getBuilder("lotr:"+block.getRegistryName().getPath()).parent(blockModel);
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
