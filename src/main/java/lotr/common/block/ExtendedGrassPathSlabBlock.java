package lotr.common.block;

import net.minecraft.block.Block;

//TODO implement non-full block VoxelBox size, specified by axis and SlabType
//TODO implement a new ExtendedBlockstateModelGenerator method to generate the models for this new looking slab model
//TODO implement logic from grass-paths to turn this block into regular dirt slabs when covered from the top
public class ExtendedGrassPathSlabBlock extends ExtendedStemableAxialSlabBlock{

	public ExtendedGrassPathSlabBlock(Block block) {
		super(block);
	}

}
