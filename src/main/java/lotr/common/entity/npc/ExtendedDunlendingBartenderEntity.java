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

public class ExtendedDunlendingBartenderEntity extends ExtendedDunlendingTraderEntity {

	public ExtendedDunlendingBartenderEntity(EntityType<? extends DunlendingEntity> type, World w) {
		super(type, w);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.DUNLENDING_CLUB.get()));
		this.npcItemsInv.setIdleItem(new ItemStack(LOTRItems.ALE.get()));
		return spawnData;
	}

	@Override
	public ResourceLocation getTraderProfileId() {
		return ExtendedTraderTypes.DUNLENDING_BARTENDER;
	}
}