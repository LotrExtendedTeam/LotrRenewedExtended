package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.ExtendedHiredFollowPlayerGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredPlayerHurtByTargetGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredPlayerHurtTargetGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredSitGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredWaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class ExtendedHiredBreeGuard extends BreeGuardEntity {

    private static final DataParameter<Boolean> INVENTORY_OPEN = EntityDataManager.defineId(ExtendedHiredBreeGuard.class,DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> EQUIPMENT_OPEN = EntityDataManager.defineId(ExtendedHiredBreeGuard.class, DataSerializers.BOOLEAN);

    public ExtendedHiredBreeGuard(EntityType<? extends BreeGuardEntity> type, World w) {
        super(type, w);
    }

    @Override
	protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(INVENTORY_OPEN, false);
        this.entityData.define(EQUIPMENT_OPEN, false);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return AbstractMannishEntity.regAttrs()
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.32D);
    }

    @Override
	public boolean isInventoryOpen() {
        return this.entityData.get(INVENTORY_OPEN);
    }

    @Override
	public boolean isEquipmentOpen() {
        return this.entityData.get(EQUIPMENT_OPEN);
    }

    @Override
    public void setInventoryOpen(boolean isOpen) {
        this.entityData.set(INVENTORY_OPEN, isOpen);
    }

    @Override
	public void setEquipmentOpen(boolean isOpen) {
        this.entityData.set(EQUIPMENT_OPEN, isOpen);
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
	public boolean wantsToAttack(LivingEntity p_142018_1_, LivingEntity p_142018_2_) {
        return true;
    }
}
