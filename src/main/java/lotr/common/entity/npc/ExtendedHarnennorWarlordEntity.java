package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainTypes;

import lotr.common.entity.npc.ai.goal.ExtendedShieldSwapMeleeAttackGoal;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedHarnennorWarlordEntity extends HarnennorWarriorEntity implements ExtendedINPCEntitySettings, ExtendedICaptainEntity {

    public ExtendedHarnennorWarlordEntity(EntityType<? extends ExtendedHarnennorWarlordEntity> type, World w) {
        super(type, w);
    }

    @Override
    protected void addNPCTargetingAI() {
    	if(ExtendedServerConfig.enableLegacyCaptainPassiveBehavior.get()) {
    		this.addNonAggressiveTargetingGoals();
    	}else {
    		this.addAggressiveTargetingGoals();
    	}
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return HarnennorWarriorEntity.regAttrs();
    }

    @Override
    protected Goal createAttackGoal() {
        return new ExtendedShieldSwapMeleeAttackGoal(this, 1.5, new ItemStack(LOTRItems.HARNENNOR_SHIELD.get()), ItemStack.EMPTY);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.HARAD_SPEAR.get()));
        this.npcItemsInv.setIdleItemsFromMeleeWeapons();
        this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.HARNENNOR_BOOTS.get()));
        this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.HARNENNOR_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.HARNENNOR_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
        return spawnData;
    }

	@Override
	public ResourceLocation getCaptainProfile() {
		return ExtendedCaptainTypes.HARNENNOR_WARLORD;
	}
}