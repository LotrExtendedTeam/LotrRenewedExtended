package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.wood;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

/**
 * Represents a collection ("pallet") of related wood-based blocks grouped under a common theme or wood type.
 * <p>
 * Stores the resource locations of various wood-related blocks such as logs, planks, slabs, stairs, fences, doors, and others.
 * This allows referencing blocks by name keys without directly holding the Block instances.
 */
public class WoodPallet {
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
	/**
	 * Indicates whether this wood pallet is empty or uninitialized.
	 * Defaults to {@code false}. Can be used to check if the pallet contains valid block data.
	 * Currently unused.
	 */
	private boolean isEmpty = false;
	/**
	 * The unique name identifier for this wood pallet.
	 * Typically corresponds to the wood type or theme represented by this pallet.
	 */
	private String name;
	/**
	 * A mapping from block type keys (e.g., "LOG", "PLANKS", "DOOR") to their
	 * corresponding {@link ResourceLocation} instances.
	 * This allows easy lookup of block resources by standardized keys.
	 */
	private Map<String, ResourceLocation> blockMap = new HashMap<>();
	/**
     * Constructs a new WoodPallet with a given name and a comprehensive set of wood-related blocks.
     * Each block's resource location is stored in the internal map with a descriptive key.
     *
     * @param pName                The name identifying this wood pallet.
     * @param lOG                  The log block.
     * @param sTRIPPED_LOG         The stripped log block.
     * @param lOG_SLAB             The log slab block.
     * @param sTRIPPED_LOG_SLAB    The stripped log slab block.
     * @param wOOD                 The wood block.
     * @param sTRIPPED_WOOD        The stripped wood block.
     * @param wOOD_SLAB            The wood slab block.
     * @param sTRIPPED_WOOD_SLAB   The stripped wood slab block.
     * @param wOOD_STAIRS          The wood stairs block.
     * @param sTRIPPED_WOOD_STAIRS The stripped wood stairs block.
     * @param bRANCH               The branch block.
     * @param sTRIPPED_BRANCH      The stripped branch block.
     * @param pLANKS               The planks block.
     * @param sLAB                 The slab block.
     * @param sTAIRS               The stairs block.
     * @param bEAM                 The beam block.
     * @param bEAM_SLAB            The beam slab block.
     * @param fENCE                The fence block.
     * @param fENCE_GATE           The fence gate block.
     * @param dOOR                 The door block.
     * @param tRAPDOOR             The trapdoor block.
     * @param pRESSURE_PLATE       The pressure plate block.
     * @param bUTTON               The button block.
     * @param sIGN                 The sign block.
     * @param wALL_SIGN            The wall sign block.
     * @param lEAVES               The leaves block.
     * @param vSLAB                The vertical slab block.
     */
	public WoodPallet(String pName, Block lOG, Block sTRIPPED_LOG, Block lOG_SLAB,Block sTRIPPED_LOG_SLAB, Block wOOD, Block sTRIPPED_WOOD,
			Block wOOD_SLAB, Block sTRIPPED_WOOD_SLAB, Block wOOD_STAIRS,Block sTRIPPED_WOOD_STAIRS, Block bRANCH, Block sTRIPPED_BRANCH,
			Block pLANKS, Block sLAB, Block sTAIRS, Block bEAM, Block bEAM_SLAB,Block fENCE, Block fENCE_GATE, Block dOOR, Block tRAPDOOR,
			Block pRESSURE_PLATE, Block bUTTON, Block sIGN, Block wALL_SIGN,Block lEAVES, Block vSLAB) {
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
        blockMap.put("SLAB_VERT", vSLAB.getRegistryName());
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
	 /**
     * Returns whether this pallet is marked as empty.
     *
     * @return true if empty, false otherwise.
     */
	public boolean isEmpty() {
		return isEmpty;
	}
	/**
     * Returns the name identifying this wood pallet.
     *
     * @return The pallet name.
     */
	public String getName() {
		return name;
	}
	/**
     * Compares this WoodPallet to another object for equality.
     * Two WoodPallets are considered equal if they have the same name.
     *
     * @param pallet The object to compare against.
     * @return true if equal, false otherwise.
     */
	@Override
	public boolean equals(Object pallet) {
		return super.equals(pallet) || (pallet instanceof WoodPallet && this.name.equals(((WoodPallet) pallet).name));
	}
	/**
     * Returns the map of block resource locations keyed by block type name.
     *
     * @return A map with keys like "LOG", "PLANKS", "DOOR", etc. and their corresponding resource locations.
     */
	public Map<String, ResourceLocation> getBlockMap() {
		return blockMap;
	}
}
