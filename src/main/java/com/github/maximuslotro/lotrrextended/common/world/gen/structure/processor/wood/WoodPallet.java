package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.wood;

import net.minecraft.block.Block;

public class WoodPallet {
	public static WoodPallet EMPTY = new WoodPallet();
	private Block LOG;
	private Block STRIPPED_LOG;
	private Block LOG_SLAB;
	private Block STRIPPED_LOG_SLAB;
	private Block WOOD;
	private Block STRIPPED_WOOD;
	private Block WOOD_SLAB;
	private Block STRIPPED_WOOD_SLAB;
	private Block WOOD_STAIRS;
	private Block STRIPPED_WOOD_STAIRS;
	private Block BRANCH;
	private Block STRIPPED_BRANCH;
	private Block PLANKS;
	private Block SLAB;
	private Block STAIRS;
	private Block BEAM;
	private Block BEAM_SLAB;
	private Block FENCE;
	private Block FENCE_GATE;
	private Block DOOR;
	private Block TRAPDOOR;
	private Block PRESSURE_PLATE;
	private Block BUTTON;
	private Block SIGN;
	private Block WALL_SIGN;
	private Block LEAVES;
	private boolean isEmpty = false;

	private WoodPallet() {
		isEmpty = true;
	}

	public WoodPallet(Block lOG, Block sTRIPPED_LOG, Block lOG_SLAB,Block sTRIPPED_LOG_SLAB, Block wOOD, Block sTRIPPED_WOOD,
			Block wOOD_SLAB, Block sTRIPPED_WOOD_SLAB, Block wOOD_STAIRS,Block sTRIPPED_WOOD_STAIRS, Block bRANCH, Block sTRIPPED_BRANCH,
			Block pLANKS, Block sLAB, Block sTAIRS, Block bEAM, Block bEAM_SLAB,Block fENCE, Block fENCE_GATE, Block dOOR, Block tRAPDOOR,
			Block pRESSURE_PLATE, Block bUTTON, Block sIGN, Block wALL_SIGN,Block lEAVES) {
		LOG = lOG;
		STRIPPED_LOG = sTRIPPED_LOG;
		LOG_SLAB = lOG_SLAB;
		STRIPPED_LOG_SLAB = sTRIPPED_LOG_SLAB;
		WOOD = wOOD;
		STRIPPED_WOOD = sTRIPPED_WOOD;
		WOOD_SLAB = wOOD_SLAB;
		STRIPPED_WOOD_SLAB = sTRIPPED_WOOD_SLAB;
		WOOD_STAIRS = wOOD_STAIRS;
		STRIPPED_WOOD_STAIRS = sTRIPPED_WOOD_STAIRS;
		BRANCH = bRANCH;
		STRIPPED_BRANCH = sTRIPPED_BRANCH;
		PLANKS = pLANKS;
		SLAB = sLAB;
		STAIRS = sTAIRS;
		BEAM = bEAM;
		BEAM_SLAB = bEAM_SLAB;
		FENCE = fENCE;
		FENCE_GATE = fENCE_GATE;
		DOOR = dOOR;
		TRAPDOOR = tRAPDOOR;
		PRESSURE_PLATE = pRESSURE_PLATE;
		BUTTON = bUTTON;
		SIGN = sIGN;
		WALL_SIGN = wALL_SIGN;
		LEAVES = lEAVES;
	}

	public boolean isEmpty() {
		return isEmpty;
	}
}
