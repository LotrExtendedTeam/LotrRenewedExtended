package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;

import java.util.Arrays;
import java.util.List;

import lotr.common.entity.npc.ai.goal.ExtendedShieldSwapMeleeAttackGoal;
import lotr.common.entity.npc.data.NPCGenderProvider;
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

public class ExtendedDunlendingWarlordEntity extends DunlendingWarriorEntity implements ExtendedNPCEntitySettings, ExtendedCaptainEntity {

    public ExtendedDunlendingWarlordEntity(EntityType<? extends ExtendedDunlendingWarlordEntity> type, World w) {
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
        return BreeManEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0D);
    }

    @Override
    protected Goal createAttackGoal() {
        return new ExtendedShieldSwapMeleeAttackGoal(this, 1.6, new ItemStack(Items.SHIELD), ItemStack.EMPTY);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.DUNLENDING_CLUB.get()));
        this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.DUNLENDING_BOOTS.get()));
		this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.DUNLENDING_LEGGINGS.get()));
		this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.DUNLENDING_CHESTPLATE.get()));
		this.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
        return spawnData;
    }

    @Override
	protected NPCGenderProvider getGenderProvider() {
        return NPCGenderProvider.MALE;
    }

	@Override
	public List<ResourceLocation> getHireableProfiles() {
		return Arrays.asList(ExtendedHiredUnitTypes.DUNLENDING, 
			ExtendedHiredUnitTypes.DUNLENDING_WARRIOR, 
			ExtendedHiredUnitTypes.DUNLENDING_BOWMAN);
	}

	@Override
	public boolean addExtendedGoals() {
		return true;
	}
}