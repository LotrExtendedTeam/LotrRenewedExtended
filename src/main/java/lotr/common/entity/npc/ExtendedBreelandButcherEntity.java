package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedBreelandButcherEntity extends ExtendedBreelandTraderEntity {

	public ExtendedBreelandButcherEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(Items.CHICKEN));
		return spawnData;
	}

	@Override
	public ResourceLocation getTraderProfileId() {
		return ExtendedTraderTypes.BREE_BUTCHER;
	}
}
