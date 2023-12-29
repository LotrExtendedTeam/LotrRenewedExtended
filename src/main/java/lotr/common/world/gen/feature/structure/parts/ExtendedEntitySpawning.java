package lotr.common.world.gen.feature.structure.parts;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotroExtended.ExtendedLog;

import lotr.common.entity.npc.HobbitBartenderEntity;
import lotr.common.init.ExtendedNpcs;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.server.ServerWorld;

public class ExtendedEntitySpawning {
	
	public static void spawnEntity(ISeedReader iseedreader, String id, BlockPos blockpos){
		spawnEntity(iseedreader, getEntityIdFromId(id), blockpos);
	}

	@SuppressWarnings("deprecation")
	public static void spawnEntity(ISeedReader iseedreader, ResourceLocation location, BlockPos blockpos) {
		if(iseedreader instanceof WorldGenRegion) {
			spawnEntity(((WorldGenRegion) iseedreader).getLevel(), location, blockpos, new CompoundNBT());
		}
		else {
			ExtendedLog.error("FOUND DIFFRENT WORLD INSTANCE TYPE! DOING UNSTABLE CAST!");
			spawnEntity(((ServerWorld) iseedreader), location, blockpos, new CompoundNBT());
		}
	}
	
	public static void spawnEntity(ServerWorld serverworld, ResourceLocation location, BlockPos blockpos, CompoundNBT nbt) {
		System.out.println("SPAWNING ENTITY: "+location.toString()+", at: "+blockpos.toString());
		if (!World.isInSpawnableBounds(blockpos)) {
			ExtendedLog.error("TRIED TO SPAWN ENTITY OUTSIDE OF BOUNDS");
		} else {
			if (!serverworld.isLoaded(blockpos)) {
				ExtendedLog.error("TRIED TO SPAWN ENTITY IN NON_LOADED_CHUNK");
			}
	        Entity entity = getEntityFromId(location, serverworld);
	        if(entity ==  null) {
	        	ExtendedLog.error("FAILED TO SPAWN ENTITY: "+location.toString()+", INVALID LOCATION!");
	        	return;
	        }
	        entity.setPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
	        if (!serverworld.addFreshEntity(entity)) {
	        	ExtendedLog.error("FAILED TO SPAWN ENTITY: "+location.toString());
	        } else {
	        	System.out.println(entity.toString());
	        	System.out.println("ENTITY SPAWNED!: "+location.toString()+", at: "+blockpos.toString());
	        }
	    }
	}

	public static ResourceLocation getEntityIdFromId(String name) {
		if(name.equals("hobbit_bartender")) {
			return ExtendedNpcs.HOBBIT_BARTENDER.getId();
		}
		ExtendedLog.error("MISSING ENTITY ID FOUND WHILE SPAWNING!");
		return new ResourceLocation("lotr", name);
	}

	@Nullable
	public static Entity getEntityFromId(ResourceLocation location, ServerWorld serverworld) {
		switch(location.toString()) {
			case "lotr:hobbit_bartender":
				return new HobbitBartenderEntity(ExtendedNpcs.HOBBIT_BARTENDER.get(), serverworld);
			case "lotr:hobbit_farmer":
				return null;
			case "lotr:hobbit_farmhand":
				return null;
			default:
				return null;
		}
	}
}
