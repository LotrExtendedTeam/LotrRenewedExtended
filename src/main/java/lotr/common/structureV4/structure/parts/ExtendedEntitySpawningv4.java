package lotr.common.structureV4.structure.parts;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.enums.ExtendedEntitySpawnTypes;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraftforge.common.util.Constants;

public class ExtendedEntitySpawningv4 {
	
	public static void spawnEntity(ISeedReader iseedreader, String id, BlockPos blockpos, MutableBoundingBox box){
		spawnEntity(iseedreader, getEntityIdFromId(id), blockpos, id, box);
	}
	
	public static void spawnEntity(ISeedReader iseedreader, Block block, BlockPos blockpos, String debugName, MutableBoundingBox box) {
		if(block!=null && box.isInside(blockpos)) {
			iseedreader.setBlock(blockpos, block.defaultBlockState(), Constants.BlockFlags.DEFAULT);
			return;
		}
	}

	@Nullable
	// TODO make this better
	public static Block getEntityIdFromId(String name) {
		if(name.equals("hobbit_bartender")) {
			return ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(ExtendedEntitySpawnTypes.HOBBIT_BARTENDER.getSerializedName()).get();
		}
		if(name.equals("hobbit_farmer")) {
			return ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(ExtendedEntitySpawnTypes.HOBBIT_FARMER.getSerializedName()).get();
		}
		ExtendedLog.error("MISSING INTERNAL ENTITY ID FOUND WHILE SPAWNING! "+name);
		if(ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(name)!=null) {
			ExtendedLog.error("FOUND MATCHING ID, TRYING INSTEAD!");
			return ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(name).get();
		}
		return null;
	}
}
