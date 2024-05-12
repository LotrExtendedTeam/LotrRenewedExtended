package lotr.common.entity.npc;

import lotr.common.entity.npc.data.ExtendedFoodPools;
import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.*;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRAttributes;
import lotr.common.init.LOTRItems;
import lotr.common.item.ExtendedBowItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedDunedainRangerEntity extends ManEntity {

    private static final SpawnEquipmentTable WEAPONS;

    private static final DataParameter<Integer> CLOAK_COOLDOWN = EntityDataManager.defineId(
            ExtendedDunedainRangerEntity.class, DataSerializers.INT);

    private static final DataParameter<Integer> AUTO_DECLOAK_IN = EntityDataManager.defineId(
            ExtendedDunedainRangerEntity.class, DataSerializers.INT
    );

    private static final DataParameter<Boolean> IS_CLOAKED = EntityDataManager.defineId(ExtendedDunedainRangerEntity.class,
            DataSerializers.BOOLEAN);

    public double xCloakO;
    public double yCloakO;
    public double zCloakO;
    public double xCloak;
    public double yCloak;
    public double zCloak;

    public float oBob;
    public float bob;

    public ExtendedDunedainRangerEntity(EntityType<? extends ExtendedDunedainRangerEntity> type, World w) {
        super(type, w);
        this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.meleeRangedSwitching());
        this.spawnRequiresSurfaceBlock = true;
    }

    public void tick() {
        super.tick();

        if (getCooldownLeft() > 0) {
            decrementCooldown();
        }

        if (getAutoDecloak() > 0 && isCloaked()) {
           decrementAutoDecloak();
           if (getAutoDecloak() == 0) unCloak();
        }

        moveCloak();
    }

    @Override
    public void swing(Hand p_184609_1_) {
        if (isCloaked()) unCloak();
        super.swing(p_184609_1_);
    }

    public void performRangedAttack(LivingEntity target, float charge) {
        this.rangerArrowAttack(target, charge);
    }

    /**
     * New method that actually takes into account the additional damage factor of ExtendedBowItem items
     * @param target
     * @param charge
     */
    private void rangerArrowAttack(LivingEntity target, float charge) {
        ItemStack heldItem = this.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, (item) -> {
            return item instanceof BowItem;
        }));
        ItemStack ammoItem = this.getProjectile(heldItem);
        AbstractArrowEntity arrow = ProjectileHelper.getMobArrow(this, ammoItem, charge);
        if (heldItem.getItem() instanceof BowItem) {
            arrow = ((BowItem)heldItem.getItem()).customArrow(arrow);
        }

        double dx = target.getX() - this.getX();
        double dy = target.getY(0.3333333333333333) - arrow.getY();
        double dz = target.getZ() - this.getZ();
        double dxzSq = MathHelper.sqrt(dx * dx + dz * dz);

        // this section right here has been updated
        float power = 2.0F;
        if (heldItem.getItem() instanceof ExtendedBowItem) {
            power *= ExtendedBowItem.getLaunchSpeedFactor(heldItem);
        }
        arrow.shoot(dx, dy + dxzSq * 0.20000000298023224, dz, power, (float)this.getAttributeValue(LOTRAttributes.NPC_RANGED_INACCURACY.get()));

        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(arrow);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_CLOAKED, false);
        this.entityData.define(CLOAK_COOLDOWN, 0);
        this.entityData.define(AUTO_DECLOAK_IN, 40);
    }

    public boolean isCloaked() {
        return this.entityData.get(IS_CLOAKED);
    }

    public int getCooldownLeft() {
        return this.entityData.get(CLOAK_COOLDOWN);
    }

    public void resetCooldown() {
        this.entityData.set(CLOAK_COOLDOWN, 100);
    }

    public void decrementCooldown() {
        this.entityData.set(CLOAK_COOLDOWN, entityData.get(CLOAK_COOLDOWN) - 1);
    }

    public void decrementAutoDecloak() {
        this.entityData.set(AUTO_DECLOAK_IN, entityData.get(AUTO_DECLOAK_IN) - 1);
    }

    public int getAutoDecloak() {
        return this.entityData.get(AUTO_DECLOAK_IN);
    }

    protected NPCFoodPool getEatPool() {
        return ExtendedFoodPools.RANGER;
    }

    protected NPCFoodPool getDrinkPool() {
        return ExtendedFoodPools.RANGER_DRINK;
    }

    @Override
    protected void initialiseAttackGoals(AttackGoalsHolder holder) {
        holder.setMeleeAttackGoal(this.createRangerMeleeAttackGoal());
        holder.setRangedAttackGoal(this.createRangerRangedAttackGoal());
    }

    // from Minecraft PlayerEntity class
    private void moveCloak() {
        this.xCloakO = this.xCloak;
        this.yCloakO = this.yCloak;
        this.zCloakO = this.zCloak;
        double d0 = this.getX() - this.xCloak;
        double d1 = this.getY() - this.yCloak;
        double d2 = this.getZ() - this.zCloak;
        if (d0 > 10.0) {
            this.xCloak = this.getX();
            this.xCloakO = this.xCloak;
        }

        if (d2 > 10.0) {
            this.zCloak = this.getZ();
            this.zCloakO = this.zCloak;
        }

        if (d1 > 10.0) {
            this.yCloak = this.getY();
            this.yCloakO = this.yCloak;
        }

        if (d0 < -10.0) {
            this.xCloak = this.getX();
            this.xCloakO = this.xCloak;
        }

        if (d2 < -10.0) {
            this.zCloak = this.getZ();
            this.zCloakO = this.zCloak;
        }

        if (d1 < -10.0) {
            this.yCloak = this.getY();
            this.yCloakO = this.yCloak;
        }

        this.xCloak += d0 * 0.25;
        this.zCloak += d2 * 0.25;
        this.yCloak += d1 * 0.25;
    }

    @Override
    protected Goal createAttackGoal() {
        return createRangerRangedAttackGoal();
    }

    public void cloak() {
        this.entityData.set(IS_CLOAKED, true);
        this.entityData.set(AUTO_DECLOAK_IN, 40);
    }

    public void renewCloak() {
        this.entityData.set(AUTO_DECLOAK_IN, 40);
    }

    public void unCloak() {
        this.entityData.set(IS_CLOAKED, false);
        this.resetCooldown();
    }

    @Override
    protected NPCNameGenerator getNameGenerator() {
        return NPCNameGenerators.GONDOR;
    }

    public static AttributeModifierMap.MutableAttribute regAttrs() {
        return NPCEntity.registerBaseNPCAttributes()
                .add(Attributes.MAX_HEALTH, 25.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(LOTRAttributes.NPC_RANGED_INACCURACY.get(), 0.5)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    // TODO: Make sure these values match Legacy
    protected Goal createRangerRangedAttackGoal() {
        return new ExtendedRangerRangedAttackGoal(this, 1.25, 20, 16.0F);
    }

    protected Goal createRangerMeleeAttackGoal() {
        return new ExtendedRangerMeleeAttackGoal(this, 1.4);
    }

    @Override
    protected void addNPCTargetingAI() {
        this.addAggressiveTargetingGoals();
    }

    @Override
    protected void addNPCAI() {
        super.addNPCAI();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.addAttackGoal(2);
        this.goalSelector.addGoal(4, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(5, new TalkToCurrentGoal(this));
        this.goalSelector.addGoal(6, new FriendlyNPCConversationGoal(this, 0.001F));
        this.goalSelector.addGoal(7, new WatchSunriseSunsetGoal(this, 0.01F));
        this.goalSelector.addGoal(8, new StargazingGoal(this, 4.0E-4F));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(10, new NPCEatGoal(this, this.getEatPool(), 12000));
        this.goalSelector.addGoal(10, new NPCDrinkGoal(this, this.getDrinkPool(), 8000));
        this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(12, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(13, new LookRandomlyGoal(this));
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);

        // setup weapons
        this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.random));
        this.npcItemsInv.setRangedWeapon(new ItemStack(ExtendedItems.DUNEDAIN_BOW.get()));
        this.npcItemsInv.setIdleItemsFromRangedWeapons();

        // setup armor
        this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.RANGER_NORTH_BOOTS.get()));
        this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.RANGER_NORTH_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.RANGER_NORTH_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(LOTRItems.RANGER_NORTH_HELMET.get()));

        return spawnData;
    }

    public ResourceLocation getCloakTextureLocation() {
        return new ResourceLocation("lotr", "textures/cape/ranger.png");
    }

    static {
        // TODO: Once the Barrow Dagger is an item add a chance of them spawning with it
        WEAPONS = SpawnEquipmentTable.of(LOTRItems.IRON_SPEAR, LOTRItems.ARNOR_SPEAR, LOTRItems.ARNOR_SWORD, LOTRItems.ARNOR_SPEAR);
    }

}
