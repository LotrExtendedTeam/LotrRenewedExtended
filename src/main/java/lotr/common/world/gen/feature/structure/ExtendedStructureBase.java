package lotr.common.world.gen.feature.structure;

import java.util.List;
import java.util.Random;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import lotr.common.LOTRLog;
import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureFileHandler;
import lotr.common.tileentity.KegTileEntity;
import lotr.common.world.gen.feature.LOTRFeatures;
import lotr.common.world.gen.feature.structure.parts.KegDrinks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public abstract class ExtendedStructureBase{
	public int initialX;
	public int initialY;
	public int initialZ;
	public int rot;

	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos) {
		return placeWithRotation(world, generator, rand, pos, rand.nextInt(4));
	}

	public abstract boolean placeWithRotation(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, int rot);

	public void generateOffsets(ISeedReader world, Random rand, BlockPos pos, int rot) {
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

	public void setAllBelowToBlock(ISeedReader world, int x, int y, int z, Block block) {
		for (int level = y-1; isOpaque(world, x, level, z) && (level+initialY) >= 0; level--) {
			setBlockAndMetadata(world, x, level, z, block);
		}
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

	public void setBlockAndMetadata(ISeedReader world, int x, int y, int z, Block block) {
		x += initialX;
		y += initialY;
		z += initialZ;
		setBlockAndMetadata(world, new BlockPos(x,y,z), block);
	}
	public void setBlockAndMetadata(ISeedReader world, BlockPos pos, Block block) {
		setBlockAndMetadata(world, pos, block.defaultBlockState());
	}

	public void setBlockAndMetadata(ISeedReader world, int x, int y, int z, BlockState blockstate) {
		x += initialX;
	    y += initialY;
	    z += initialZ;
	    setBlockAndMetadata(world, new BlockPos(x,y,z), blockstate);
	}
	public void setBlockAndMetadata(ISeedReader world, BlockPos pos, BlockState blockstate) {
	    world.setBlock(pos, blockstate, 3);
	}

	public StructureFile getStructure(String name) {
		return StructureFileHandler.getStructureFile(name, ServerLifecycleHooks.getCurrentServer());
	}

	public void setLockableLootTileEntityContents(IBlockReader world, Random random, int x, int y, int z, String table) {
		x += initialX;
		y += initialY;
		z += initialZ;
		setLockableLootTileEntityContents(world, random, new BlockPos(x,y,z), table);
	}
	
	public void setLockableLootTileEntityContents(IBlockReader world, Random random, BlockPos pos, String table) {
		TileEntity tileentity = world.getBlockEntity(pos);
		ResourceLocation resource = makeReasourcePath(table);
		if (tileentity instanceof LockableLootTileEntity) {
			((LockableLootTileEntity)tileentity).setLootTable(resource, random.nextLong());
		}
		else {
			LOTRLog.error("FAILED TO SET LOOTTABLE FOR: " + pos.toString() + ", " + resource + ", NOT INSTANCE OF " + LockableLootTileEntity.class.getName());
		}
	}

	private ResourceLocation makeReasourcePath(String part) {
		return new ResourceLocation(LotrExtendedMod.MODID, "chests/" + part);
	}
	
	public void setKegTileEntityContents(IBlockReader world, Random random, int x, int y, int z, List<RegistryObject<Item>> pool) {
		x += initialX;
		y += initialY;
		z += initialZ;
		setKegTileEntityContents(world, random, new BlockPos(x,y,z), pool);
	}

	public void setKegTileEntityContents(IBlockReader world, Random random, BlockPos pos, List<RegistryObject<Item>> pool) {
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof KegTileEntity) {
			((KegTileEntity)tileentity).fillBrewedWith(KegDrinks.SetDrink(random, pool));
		}
		else {
			LOTRLog.error("FAILED TO SET BREW FOR: " + pos.toString() + ", " + ", NOT INSTANCE OF " + KegTileEntity.class.getName());
		}
	}
}
