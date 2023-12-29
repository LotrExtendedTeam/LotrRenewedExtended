package lotr.common.world.gen.feature.structure.parts;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotroExtended.ExtendedLog;

import lotr.common.init.ExtendedBlocks;
import lotr.common.tileentitie.spawners.EntitySpawnTypes;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

public class ExtendedEntitySpawning {
	
	public static void spawnEntity(ISeedReader iseedreader, String id, BlockPos blockpos){
		spawnEntity(iseedreader, getEntityIdFromId(id), blockpos);
	}

	@SuppressWarnings("deprecation")
	public static void spawnEntity(ISeedReader iseedreader, Block block, BlockPos blockpos) {
		if(iseedreader instanceof WorldGenRegion) {
			spawnEntity(((WorldGenRegion) iseedreader).getLevel(), block, blockpos);
		}
		else {
			ExtendedLog.error("FOUND DIFFRENT WORLD INSTANCE TYPE! DOING UNSTABLE CAST!");
			spawnEntity(((ServerWorld) iseedreader), block, blockpos);
		}
	}
	
	public static void spawnEntity(ServerWorld serverworld, Block block, BlockPos blockpos) {
		if(block!=null) {
			serverworld.setBlock(blockpos, block.defaultBlockState(), Constants.BlockFlags.DEFAULT);
			System.out.println("CREATED ENTITY SPAWNER: "+block.getRegistryName().toString()+", at: "+blockpos.toString());
			return;
		}
	}

	@Nullable
	public static Block getEntityIdFromId(String name) {
		if(name.equals("hobbit_bartender")) {
			return ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(EntitySpawnTypes.HOBBIT_BARTENDER.getSerializedName()).get();
		}
		ExtendedLog.error("MISSING INTERNAL ENTITY ID FOUND WHILE SPAWNING!");
		if(ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(name)!=null) {
			ExtendedLog.error("FOUND MATCHING ID, TRYING INSTEAD!");
			return ExtendedBlocks.ENTITYSPAWNERBLOCKS.get(name).get();
		}
		return null;
	}
}
