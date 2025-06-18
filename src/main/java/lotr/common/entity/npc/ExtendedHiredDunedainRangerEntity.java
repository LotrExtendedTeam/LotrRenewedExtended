package lotr.common.entity.npc;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitOrders;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;

import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.goal.ExtendedHiredFollowPlayerGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredPlayerHurtByTargetGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredPlayerHurtTargetGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredSitGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredWaterAvoidingRandomWalkingGoal;
import lotr.common.entity.npc.ai.goal.ExtendedRangerMeleeAttackGoal;
import lotr.common.entity.npc.ai.goal.ExtendedRangerRangedAttackGoal;
import lotr.common.util.ExtendedHiredUnitHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedHiredDunedainRangerEntity extends ExtendedDunedainRangerEntity {

    private static final DataParameter<Boolean> INVENTORY_OPEN = EntityDataManager.defineId(ExtendedHiredDunedainRangerEntity.class,DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> EQUIPMENT_OPEN = EntityDataManager.defineId(ExtendedHiredDunedainRangerEntity.class, DataSerializers.BOOLEAN);

	public ExtendedHiredDunedainRangerEntity(EntityType<? extends ExtendedHiredDunedainRangerEntity> type, World w) {
        super(type, w);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new ExtendedHiredSitGoal(this));
        this.goalSelector.addGoal(3, new ExtendedHiredFollowPlayerGoal( this,1.3D, 8.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new ExtendedHiredWaterAvoidingRandomWalkingGoal(this,1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(8, new OpenDoorGoal(this, true));
        // TODO: Re-enable food eating one when I figure out how to get it to not mess up
        //  their attack animations
        //this.goalSelector.addGoal(9, new LowHealthGoal(this,this));
        //this.goalSelector.addGoal(10, new EatGoal(this,this));
        this.targetSelector.addGoal(1, new ExtendedHiredPlayerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new ExtendedHiredPlayerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BASE_HEALTH, 18);
        this.entityData.define(INVENTORY_OPEN, false);
        this.entityData.define(EQUIPMENT_OPEN, false);
        this.entityData.define(TMP_LAST_HEALTH, 18f);
    }

    @Override
    protected void initialiseAttackGoals(AttackGoalsHolder holder) {
        holder.setMeleeAttackGoal(this.createRangerMeleeAttackGoal());
        holder.setRangedAttackGoal(this.createRangerRangedAttackGoal());
    }

    @Override
    protected Goal createAttackGoal() {
        return createRangerRangedAttackGoal();
    }
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return ExtendedDunedainRangerEntity.regAttrs()
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.MAX_HEALTH, 25.0D);
    }

    // TODO: Make sure these values match Legacy
    @Override
	protected Goal createRangerRangedAttackGoal() {
        return new ExtendedRangerRangedAttackGoal(this, 1.25, 20, 16.0F);
    }

    @Override
	protected Goal createRangerMeleeAttackGoal() {
        return new ExtendedRangerMeleeAttackGoal(this, 1.4);
    }

	@Override
	public boolean isInventoryOpen() {
		return this.entityData.get(INVENTORY_OPEN);
	}

	@Override
	public boolean wantsToAttack(LivingEntity p_142018_1_, LivingEntity p_142018_2_) {
		return true;
	}

	@Override
	public boolean isEquipmentOpen() {
		 return this.entityData.get(EQUIPMENT_OPEN);
	}

	@Override
	public void setEquipmentOpen(boolean isOpen) {
		this.entityData.set(EQUIPMENT_OPEN, isOpen);
	}

	@Override
	public void setInventoryOpen(boolean isOpen) {
		 this.entityData.set(INVENTORY_OPEN, isOpen);
	}

}
