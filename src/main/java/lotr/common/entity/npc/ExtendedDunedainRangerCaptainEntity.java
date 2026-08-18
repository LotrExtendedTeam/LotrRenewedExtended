package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.entity.npc.ai.goal.ExtendedShieldSwapMeleeAttackGoal;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedDunedainRangerCaptainEntity extends ExtendedDunedainRangerEntity implements ExtendedICaptainEntity{

	public ExtendedDunedainRangerCaptainEntity(EntityType<? extends ExtendedDunedainRangerEntity> type, World w) {
		super(type, w);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return ExtendedDunedainRangerEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0);
	}

	@Override
	protected void addNPCTargetingAI() {
		if(ExtendedServerConfig.enableLegacyCaptainPassiveBehavior.get()) {
			this.addNonAggressiveTargetingGoals();
		}else {
			this.addAggressiveTargetingGoals();
		}
	}


    @Override
	protected Goal createRangerMeleeAttackGoal() {
    	return new ExtendedShieldSwapMeleeAttackGoal(this, 1.4D, new ItemStack(ExtendedItems.DUNEDAIN_SHIELD.get()), ItemStack.EMPTY);
    }
   
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);

		setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.RANGER_NORTH_BOOTS.get()));
		setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.RANGER_NORTH_LEGGINGS.get()));
		setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.RANGER_NORTH_CHESTPLATE.get()));
		setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
		setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.IRON_SWORD));
		npcItemsInv.setRangedWeapon(new ItemStack(ExtendedItems.DUNEDAIN_BOW.get()));
		npcItemsInv.setIdleItemsFromMeleeWeapons();

		return spawnData;
	}

	@Override
	public ResourceLocation getCaptainProfile() {
		return ExtendedCaptainTypes.DUNEDAIN_CAPTAIN;
	}
}
