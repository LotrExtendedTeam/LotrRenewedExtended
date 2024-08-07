package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.wood;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class WoodPallet {
	public static WoodPallet EMPTY = new WoodPallet();
//	private Block LOG;
//	private Block STRIPPED_LOG;
//	private Block LOG_SLAB;
//	private Block STRIPPED_LOG_SLAB;
//	private Block WOOD;
//	private Block STRIPPED_WOOD;
//	private Block WOOD_SLAB;
//	private Block STRIPPED_WOOD_SLAB;
//	private Block WOOD_STAIRS;
//	private Block STRIPPED_WOOD_STAIRS;
//	private Block BRANCH;
//	private Block STRIPPED_BRANCH;
//	private Block PLANKS;
//	private Block SLAB;
//	private Block STAIRS;
//	private Block BEAM;
//	private Block BEAM_SLAB;
//	private Block FENCE;
//	private Block FENCE_GATE;
//	private Block DOOR;
//	private Block TRAPDOOR;
//	private Block PRESSURE_PLATE;
//	private Block BUTTON;
//	private Block SIGN;
//	private Block WALL_SIGN;
//	private Block LEAVES;
	private boolean isEmpty = false;
	private String name;
	Map<String, ResourceLocation> blockMap = new HashMap<>();
	private WoodPallet() {
		isEmpty = true;
		name = "minecraft:empty";
	}

	public WoodPallet(String pName, Block lOG, Block sTRIPPED_LOG, Block lOG_SLAB,Block sTRIPPED_LOG_SLAB, Block wOOD, Block sTRIPPED_WOOD,
			Block wOOD_SLAB, Block sTRIPPED_WOOD_SLAB, Block wOOD_STAIRS,Block sTRIPPED_WOOD_STAIRS, Block bRANCH, Block sTRIPPED_BRANCH,
			Block pLANKS, Block sLAB, Block sTAIRS, Block bEAM, Block bEAM_SLAB,Block fENCE, Block fENCE_GATE, Block dOOR, Block tRAPDOOR,
			Block pRESSURE_PLATE, Block bUTTON, Block sIGN, Block wALL_SIGN,Block lEAVES) {
		name = pName;
        blockMap.put("LOG", lOG.getRegistryName());
        blockMap.put("STRIPPED_LOG", sTRIPPED_LOG.getRegistryName());
        blockMap.put("LOG_SLAB", lOG_SLAB.getRegistryName());
        blockMap.put("STRIPPED_LOG_SLAB", sTRIPPED_LOG_SLAB.getRegistryName());
        blockMap.put("WOOD", wOOD.getRegistryName());
        blockMap.put("STRIPPED_WOOD", sTRIPPED_WOOD.getRegistryName());
        blockMap.put("WOOD_SLAB", wOOD_SLAB.getRegistryName());
        blockMap.put("STRIPPED_WOOD_SLAB", sTRIPPED_WOOD_SLAB.getRegistryName());
        blockMap.put("WOOD_STAIRS", wOOD_STAIRS.getRegistryName());
        blockMap.put("STRIPPED_WOOD_STAIRS", sTRIPPED_WOOD_STAIRS.getRegistryName());
        blockMap.put("BRANCH", bRANCH.getRegistryName());
        blockMap.put("STRIPPED_BRANCH", sTRIPPED_BRANCH.getRegistryName());
        blockMap.put("PLANKS", pLANKS.getRegistryName());
        blockMap.put("SLAB", sLAB.getRegistryName());
        blockMap.put("STAIRS", sTAIRS.getRegistryName());
        blockMap.put("BEAM", bEAM.getRegistryName());
        blockMap.put("BEAM_SLAB", bEAM_SLAB.getRegistryName());
        blockMap.put("FENCE", fENCE.getRegistryName());
        blockMap.put("FENCE_GATE", fENCE_GATE.getRegistryName());
        blockMap.put("DOOR", dOOR.getRegistryName());
        blockMap.put("TRAPDOOR", tRAPDOOR.getRegistryName());
        blockMap.put("PRESSURE_PLATE", pRESSURE_PLATE.getRegistryName());
        blockMap.put("BUTTON", bUTTON.getRegistryName());
        blockMap.put("SIGN", sIGN.getRegistryName());
        blockMap.put("WALL_SIGN", wALL_SIGN.getRegistryName());
        blockMap.put("LEAVES", lEAVES.getRegistryName());
//        blockMap.put("LOG", lOG);
//        blockMap.put("STRIPPED_LOG", sTRIPPED_LOG);
//        blockMap.put("LOG_SLAB", lOG_SLAB);
//        blockMap.put("STRIPPED_LOG_SLAB", sTRIPPED_LOG_SLAB);
//        blockMap.put("WOOD", wOOD);
//        blockMap.put("STRIPPED_WOOD", sTRIPPED_WOOD);
//        blockMap.put("WOOD_SLAB", wOOD_SLAB);
//        blockMap.put("STRIPPED_WOOD_SLAB", sTRIPPED_WOOD_SLAB);
//        blockMap.put("WOOD_STAIRS", wOOD_STAIRS);
//        blockMap.put("STRIPPED_WOOD_STAIRS", sTRIPPED_WOOD_STAIRS);
//        blockMap.put("BRANCH", bRANCH);
//        blockMap.put("STRIPPED_BRANCH", sTRIPPED_BRANCH);
//        blockMap.put("PLANKS", pLANKS);
//        blockMap.put("SLAB", sLAB);
//        blockMap.put("STAIRS", sTAIRS);
//        blockMap.put("BEAM", bEAM);
//        blockMap.put("BEAM_SLAB", bEAM_SLAB);
//        blockMap.put("FENCE", fENCE);
//        blockMap.put("FENCE_GATE", fENCE_GATE);
//        blockMap.put("DOOR", dOOR);
//        blockMap.put("TRAPDOOR", tRAPDOOR);
//        blockMap.put("PRESSURE_PLATE", pRESSURE_PLATE);
//        blockMap.put("BUTTON", bUTTON);
//        blockMap.put("SIGN", sIGN);
//        blockMap.put("WALL_SIGN", wALL_SIGN);
//        blockMap.put("LEAVES", lEAVES);
//        this.LOG = blockMap.get("LOG");
//        this.STRIPPED_LOG = blockMap.get("STRIPPED_LOG");
//        this.LOG_SLAB = blockMap.get("LOG_SLAB");
//        this.STRIPPED_LOG_SLAB = blockMap.get("STRIPPED_LOG_SLAB");
//        this.WOOD = blockMap.get("WOOD");
//        this.STRIPPED_WOOD = blockMap.get("STRIPPED_WOOD");
//        this.WOOD_SLAB = blockMap.get("WOOD_SLAB");
//        this.STRIPPED_WOOD_SLAB = blockMap.get("STRIPPED_WOOD_SLAB");
//        this.WOOD_STAIRS = blockMap.get("WOOD_STAIRS");
//        this.STRIPPED_WOOD_STAIRS = blockMap.get("STRIPPED_WOOD_STAIRS");
//        this.BRANCH = blockMap.get("BRANCH");
//        this.STRIPPED_BRANCH = blockMap.get("STRIPPED_BRANCH");
//        this.PLANKS = blockMap.get("PLANKS");
//        this.SLAB = blockMap.get("SLAB");
//        this.STAIRS = blockMap.get("STAIRS");
//        this.BEAM = blockMap.get("BEAM");
//        this.BEAM_SLAB = blockMap.get("BEAM_SLAB");
//        this.FENCE = blockMap.get("FENCE");
//        this.FENCE_GATE = blockMap.get("FENCE_GATE");
//        this.DOOR = blockMap.get("DOOR");
//        this.TRAPDOOR = blockMap.get("TRAPDOOR");
//        this.PRESSURE_PLATE = blockMap.get("PRESSURE_PLATE");
//        this.BUTTON = blockMap.get("BUTTON");
//        this.SIGN = blockMap.get("SIGN");
//        this.WALL_SIGN = blockMap.get("WALL_SIGN");
//        this.LEAVES = blockMap.get("LEAVES");
    }

	public boolean isEmpty() {
		return isEmpty;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object pallet) {
		return super.equals(pallet) || (pallet instanceof WoodPallet ? this.name.equals(((WoodPallet)pallet).name) : false);
	}

	public Map<String, ResourceLocation> getBlockMap() {
		return blockMap;
	}
}
