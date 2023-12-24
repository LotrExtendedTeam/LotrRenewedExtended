package lotr.common.world.gen.feature.structure.parts;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotroExtended.ExtendedLog;

import lotr.common.init.ExtendedNpcs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;

public class ExtendedEntitySpawning {
	
	public static void spawnEntity(ISeedReader serverworld, String id, BlockPos blockpos){
		spawnEntity(serverworld, getEntityFromId(id), blockpos);
	}

	public static void spawnEntity(ISeedReader serverworld, ResourceLocation location, BlockPos blockpos){
		spawnEntity(serverworld, location, blockpos, new CompoundNBT());
	}
	
	public static void spawnEntity(ISeedReader serverworld, ResourceLocation location, BlockPos blockpos, CompoundNBT nbt) {
		if (!World.isInSpawnableBounds(blockpos)) {
			ExtendedLog.error("TRIED TO SPAWN ENTITY OUTSIDE OF BOUNDS");
		} else if (location==null) {
			ExtendedLog.error("NULL ENTITY ID FOUND WHILE SPAWNING!");
		} else {
			CompoundNBT compoundnbt = nbt.copy();
	        compoundnbt.putString("id", location.toString());
	        Entity entity = EntityType.loadEntityRecursive(compoundnbt, serverworld.getLevel(), (entity1) -> {
	        	entity1.moveTo(blockpos.getX(), blockpos.getY(), blockpos.getZ(), entity1.yRot, entity1.xRot);
	            return entity1;
	        });
	        if (entity == null) {
	        	ExtendedLog.error("FAILED TO SPAWN ENTITY: "+location.toString());
	        } else {
	        	if (entity instanceof MobEntity) {
	        		((MobEntity)entity).finalizeSpawn(serverworld, serverworld.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
	        	}
	        	if (!serverworld.getLevel().tryAddFreshEntityWithPassengers(entity)) {
	        		ExtendedLog.error("FAILED TO SPAWN ENTITY: "+location.toString()+", DUPLICATE UUID FOUND!");
	            }
	        }
	    }
	}

	@Nullable
	public static ResourceLocation getEntityFromId(String name) {
		if(name.equals("hobbit_bartender")) {
			return ExtendedNpcs.HOBBIT_BARTENDER.getId();
		}
		return null;
	}
}
