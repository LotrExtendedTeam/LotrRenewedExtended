package lotr.common.world.gen.feature.structure;

import java.util.List;
import java.util.Random;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import lotr.common.LOTRLog;
import lotr.common.structure.RelativePosition;
import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureFileHandler;
import lotr.common.tileentity.KegTileEntity;
import lotr.common.world.gen.feature.LOTRFeatures;
import lotr.common.world.gen.feature.structure.parts.KegDrinks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.item.Item;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
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

	//TODO deal with rotation
	public void generateOffsets(ISeedReader world, Random rand, BlockPos pos, int rot, RelativePosition offset) {
		this.rot = rot;
		this.initialX = (pos.getX()-((int) Math.ceil(offset.X/2)));
		this.initialY = pos.getY();
		this.initialZ = (pos.getZ()-((int) Math.ceil(offset.Z/2)));
		findSurface(world);
	}

	public void findSurface(ISeedReader world, BlockPos pos) {
		int height =initialY;
		for (int y = 8; (initialY+y) >= 0; y--) {
		      if (!LOTRFeatures.isSurfaceBlock((IWorld) world, new BlockPos(pos.getX(), initialY+y, pos.getZ()))) {
		        break;
		      } 
		      height +=y;
		 }
		initialY = height;
	}

	public void setAllBelowToBlock(ISeedReader world, int x, int y, int z, Block block) {
		for (int level = y-1; isNotSolid(world, x, level, z) && (level+initialY) >= 0; level--) {
			setBlockAndMetadata(world, x, level, z, block);
		}
	}

	public boolean setBelowToBlockIfBlock(ISeedReader world, int x, int y, int z, Block newBlock, Block oldBlock) {
		x += initialX;
		y += initialY;
		z += initialZ;
		BlockPos pos = new BlockPos(x, y, z);
		if(world.getBlockState(pos.below()).getBlock().equals(oldBlock)) {
			setBlockAndMetadata(world, pos, newBlock);
			return true;
		}
		return false;
	}

	public void fixBelowBlocks(ISeedReader world, int x, int y, int z, Block newBlock, Block oldBlock) {
		if(!setBelowToBlockIfBlock(world, x, y, z, newBlock, oldBlock)) {
			setAllBelowToBlock(world, x, y, z, oldBlock);
		}
	}
	
	public boolean isNotSolid(ISeedReader world, int x, int y, int z) {
		x += initialX;
		y += initialY;
		z += initialZ;
		BlockState belowState = world.getBlockState(new BlockPos(x, y, z));
		return belowState.getBlock().equals(Blocks.AIR)||(belowState.getBlock() instanceof BushBlock);
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
			if(random.nextInt(5)==0) { return; }
			((KegTileEntity)tileentity).fillBrewedWith(KegDrinks.SetDrink(random, pool));
		}
		else {
			LOTRLog.error("FAILED TO SET BREW FOR: " + pos.toString() + ", " + ", NOT INSTANCE OF " + KegTileEntity.class.getName());
		}
	}
}
