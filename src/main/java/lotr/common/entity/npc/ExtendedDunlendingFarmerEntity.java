package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedDunlendingFarmerEntity extends ExtendedDunlendingTraderEntity implements ExtendedICaptainEntity {

	public ExtendedDunlendingFarmerEntity(EntityType<? extends DunlendingEntity> type, World w) {
		super(type, w);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(LOTRItems.BRONZE_HOE.get(), 1));
		setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get(), 1));
		return spawnData;
	}

	@Override
	public ResourceLocation getTraderProfileId() {
		return ExtendedTraderTypes.DUNLENDING_FARMER;
	}

	@Override
	public ResourceLocation getCaptainProfile() {
		return ExtendedCaptainTypes.DUNLENDING_FARMER;
	}
}