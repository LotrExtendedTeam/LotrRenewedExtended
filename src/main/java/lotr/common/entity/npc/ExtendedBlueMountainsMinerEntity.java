package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedBlueMountainsMinerEntity extends ExtendedBlueMountainsTraderEntity {

	public ExtendedBlueMountainsMinerEntity(EntityType<? extends ExtendedBlueMountainsMinerEntity> type, World w) {
		super(type, w);
	}

	@Override
	protected boolean canDwarfSpawnAboveGround() {
		return false;
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.BLUE_DWARVEN_PICKAXE.get()));
		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
		return spawnData;
	}

	@Override
	public ResourceLocation getTraderProfileId() {
		return ExtendedTraderTypes.BLUE_MOUNTAINS_MINER;
	}

}

