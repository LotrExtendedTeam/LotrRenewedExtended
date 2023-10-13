package lotr.common.world.gen.feature.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import lotr.common.world.gen.feature.LOTRFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public abstract class ExtendedStructureBase<FC extends IFeatureConfig> extends Feature<FC>{
	public int initialX;
	public int initialY;
	public int initialZ;
	public int rot;
	public ExtendedStructureBase(Codec<FC> codec) {
		super(codec);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, FC config) {
		return placeWithRotation(world, generator, rand, pos, config, rand.nextInt(4));
	}

	public abstract boolean placeWithRotation(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, FC config, int rot);

	public void generateOffsets(ISeedReader world, Random rand, BlockPos pos, FC config, int rot) {
		this.rot = rot;
		this.initialX = pos.getX();
		this.initialY = pos.getY();
		this.initialZ = pos.getZ();
		findSurface(world, pos, rot);
	}

	public void findSurface(ISeedReader world, BlockPos pos, int rot) {
		int height =initialY;
		for (int y = 8; (initialY+y) >= 0; y--) {
		      if (!LOTRFeatures.isSurfaceBlock((IWorld) world, new BlockPos(pos.getX(), initialY+y, pos.getZ()))) {
		        break;
		      } 
		      height +=y;
		 }
		initialY = height;
	}

	public boolean isSurface(ISeedReader world, int x, int y, int z) {
		x += initialX;
	    y += initialY;
	    z += initialZ;
		return LOTRFeatures.isSurfaceBlock((IWorld) world, new BlockPos(x,y,z));
	}

	public int getTopBlock(ISeedReader world, int x, int z) {
	    x += initialX;
	    z += initialZ;
        int oldY = initialY;
        int newY = initialY;
        int precY = world.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING, new BlockPos(x,0,z)).getY();

        if (oldY != precY)
        {
            if (precY == -1)
            {
            	newY = 0;
                while (world.getBlockState(new BlockPos(x,newY,z)).getMaterial() != Material.AIR)
                {
                	newY+=1;
                }
                if (oldY == newY)
                {
                    return newY;
                }
            }
            else
            {
            	newY = precY;
            }
            return newY;
        }
        return 0;
    }

	public boolean isOpaque(ISeedReader world, int x, int y, int z) {
		x += initialX;
	    y += initialY;
	    z += initialZ;
		BlockState belowState = world.getBlockState(new BlockPos(x, y, z));
		return !belowState.isSolidRender(world, new BlockPos(x, y, z));
	}

	public void setGrassToDirt(ISeedReader world, int x, int y, int z) {
		x += initialX;
	    y += initialY;
	    z += initialZ;
	    LOTRFeatures.setGrassToDirtBelow(world, new BlockPos(x,y,z));
	}
	public void setBlockAndMetadata(ISeedReader world, int x, int y, int z, Block block, int extra) {
		x += initialX;
	    y += initialY;
	    z += initialZ;
	    world.setBlock(new BlockPos(x,y,z), block.defaultBlockState(), 0);
	}
	public void setBlockAndMetadata(ISeedReader world, BlockPos pos, Block block, int extra) {
	    world.setBlock(pos, block.defaultBlockState(), 0);
	}
}
