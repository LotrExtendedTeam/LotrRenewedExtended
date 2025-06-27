package lotr.common.entity.npc;

import java.util.ArrayList;

import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import lotr.common.init.LOTRItems;
import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedBreelandHobbitInnkeeperEntity extends ExtendedBreelandHobbitTraderEntity {

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandHobbitInnkeeperEntity(EntityType<? extends BreeHobbitEntity> type, World w) {
		super(type, w);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(LOTRItems.WOODEN_MUG.get()));
		return spawnData;
	}

	@Override
	public ResourceLocation getTraderProfileId() {
		return ExtendedTraderTypes.BREE_INNKEEPER;
	}
}
