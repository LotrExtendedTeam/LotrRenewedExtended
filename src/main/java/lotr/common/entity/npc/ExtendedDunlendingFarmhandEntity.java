package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming.ExtendedFarmingGoalHolder;

import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedDunlendingFarmhandEntity extends DunlendingEntity implements ExtendedIFarmhand {

	public ExtendedDunlendingFarmhandEntity(EntityType<? extends DunlendingEntity> type, World w) {
		super(type, w);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return DunlendingEntity.regAttrs();
	}

	@Override
	protected void addNPCAI() {
		super.addNPCAI();
		this.goalSelector.addGoal(3, new ExtendedFarmingGoalHolder(this, 1.0D).standardOrder());
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(LOTRItems.BRONZE_HOE.get(), 1));
		return spawnData;
	}
}