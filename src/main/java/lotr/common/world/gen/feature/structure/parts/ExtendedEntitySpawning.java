package lotr.common.world.gen.feature.structure.parts;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotroExtended.ExtendedLog;

import lotr.common.init.ExtendedBlocks;
import lotr.common.tileentitie.spawners.ExtendedEntitySpawnTypes;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraftforge.common.util.Constants;

public class ExtendedEntitySpawning {
	
	public static void spawnEntity(ISeedReader iseedreader, String id, BlockPos blockpos){
		spawnEntity(iseedreader, getEntityIdFromId(id), blockpos);
	}
	
	public static void spawnEntity(ISeedReader iseedreader, Block block, BlockPos blockpos) {
		if(block!=null) {
			iseedreader.setBlock(blockpos, block.defaultBlockState(), Constants.BlockFlags.DEFAULT);
			return;
		}
	}

	@Nullable
	public static Block getEntityIdFromId(String name) {
		if(name.equals("hobbit_bartender")) {
			return ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(ExtendedEntitySpawnTypes.HOBBIT_BARTENDER.getSerializedName()).get();
		}
		if(name.equals("hobbit_farmer")) {
			return ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(ExtendedEntitySpawnTypes.HOBBIT_FARMER.getSerializedName()).get();
		}
		ExtendedLog.error("MISSING INTERNAL ENTITY ID FOUND WHILE SPAWNING!");
		if(ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(name)!=null) {
			ExtendedLog.error("FOUND MATCHING ID, TRYING INSTEAD!");
			return ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(name).get();
		}
		return null;
	}
}
