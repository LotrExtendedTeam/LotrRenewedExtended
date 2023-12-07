package lotr.common.world.gen.feature.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class HobbitHoleFeature extends ExtendedStructureBase<HobbitHoleFeatureConfig> {
	public HobbitHoleFeature(Codec<HobbitHoleFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean placeWithRotation(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			HobbitHoleFeatureConfig config, int rot) {
		generateOffsets(world, rand, pos, config, rot);
		int radius = 16;
		int height = 7;
		int extraRadius = 2;

		int i3;
	    for (i3 = -radius; i3 <= radius; i3++) {
	      for (int i36 = -radius; i36 <= radius; i36++) {
	        for (int i37 = height; i37 >= 0; i37--) {
	          int i38 = i3;
	          int j2 = i37 + radius - height;
	          int k2 = i36;
	          if (i38 * i38 + j2 * j2 + k2 * k2 < (radius + extraRadius) * (radius + extraRadius)) {
	            boolean bool = isOpaque(world, i3, i37 + 1, i36);
	            setBlockAndMetadata(world,i3, i37, i36, bool ? Blocks.GRASS_BLOCK : Blocks.DIRT);
	            setGrassToDirt(world, i3, i37, i36);
	          } 
	        } 
	      } 
	    } 
	    for (i3 = -radius; i3 <= radius; i3++) {
	      for (int i36 = -radius; i36 <= radius; i36++) {
	        if (i3 * i3 + i36 * i36 < radius * radius)
	          for (int i37 = -1; isOpaque(world, i3, i37, i36) && (i37+initialY) >= 0; i37--) {
	            boolean bool = isOpaque(world, i3, i37 + 1, i36);
	            setBlockAndMetadata(world,i3, i37, i36, bool ? Blocks.GRASS_BLOCK : Blocks.DIRT);
	            setGrassToDirt(world, i3, i37, i36);
	          }  
	      } 
	    }
	    setGrassToDirt(world, 0, 7, 0);
	    setBlockAndMetadata(world, 0, 8, 0, Blocks.BRICKS);
	    setBlockAndMetadata(world, 0, 9, 0, Blocks.BRICKS);
	    setBlockAndMetadata(world, 0, 10, 0, Blocks.FLOWER_POT);
	    for (int i2 = -16; i2 <= -13; i2++) {
	      for (int i36 = 1; i36 <= 4; i36++) {
	        for (int i37 = -3; i37 <= 3; i37++)
	        	setBlockAndMetadata(world, i37, i36, i2, Blocks.AIR); 
	      } 
	    } 
	    for (int j1 = 1; j1 <= 3; j1++) {
	      for (int i36 = -2; i36 <= 2; i36++)
	    	  setBlockAndMetadata(world, i36, j1, -12, Blocks.AIR); 
	    } 
	    int n;
	    for (n = -17; n <= -13; n++) {
	      for (int i36 = -5; i36 <= 5; i36++) {
	        int i37;
	        for (i37 = 0; i37 == 0 || (isOpaque(world, i36, i37, n) && (i37+initialY) >= 0); i37--) {
	          boolean bool = (i37 == 0);
	          setBlockAndMetadata(world, i36, i37, n, bool ? Blocks.GRASS_BLOCK : Blocks.DIRT);
	        } 
	        for (i37 = 1; i37 <= 3; i37++)
	        	setBlockAndMetadata(world, i36, i37, n, Blocks.AIR); 
	      } 
	    } 
	    for (n = -16; n <= -13; n++) {
	      setBlockAndMetadata(world, 4, 1, n, Blocks.OAK_FENCE);
	      setBlockAndMetadata(world, -4, 1, n, Blocks.OAK_FENCE);
	      setBlockAndMetadata(world, 0, 0, n, Blocks.GRASS_PATH);
	    } 

	    
		//if (LOTRFeatures.isSurfaceBlock((IWorld) world, pos.below())) {
			int up = 4 + rand.nextInt(3);
			BlockPos tablePos = pos.above(up);
			setBlockAndMetadata(world, tablePos, config.block.getBlock());
			//world.setBlock(tablePos, config.block, 2);
			return true;
		//}
	}
}
