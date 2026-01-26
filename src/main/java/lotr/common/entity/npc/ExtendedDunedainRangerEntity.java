package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.ExtendedRangerRangedAttackGoal;
import lotr.common.entity.npc.ai.goal.FriendlyNPCConversationGoal;
import lotr.common.entity.npc.ai.goal.NPCDrinkGoal;
import lotr.common.entity.npc.ai.goal.NPCEatGoal;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.ai.goal.StargazingGoal;
import lotr.common.entity.npc.ai.goal.TalkToCurrentGoal;
import lotr.common.entity.npc.ai.goal.WatchSunriseSunsetGoal;
import lotr.common.entity.npc.data.ExtendedFoodPools;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRAttributes;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedDunedainRangerEntity extends ManEntity implements ExtendedNPCEntitySettings {

    private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(LOTRItems.ARNOR_SWORD, LOTRItems.BRONZE_SWORD, Items.IRON_SWORD, LOTRItems.ARNOR_DAGGER, LOTRItems.BRONZE_DAGGER, LOTRItems.IRON_DAGGER);
    private static final SpawnEquipmentTable BOWS = SpawnEquipmentTable.of(ExtendedItems.DUNEDAIN_BOW, ExtendedItems.RANGER_BOW, ExtendedItems.LONGBOW, Items.BOW);

    private static final DataParameter<Integer> CLOAK_COOLDOWN = EntityDataManager.defineId(ExtendedDunedainRangerEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> AUTO_DECLOAK_IN = EntityDataManager.defineId(ExtendedDunedainRangerEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> IS_CLOAKED = EntityDataManager.defineId(ExtendedDunedainRangerEntity.class,DataSerializers.BOOLEAN);

    public double xCloakO;
    public double yCloakO;
    public double zCloakO;
    public double xCloak;
    public double yCloak;
    public double zCloak;

    public float oBob;
    public float bob;

	@SuppressWarnings("unchecked")
	public ExtendedDunedainRangerEntity(EntityType<? extends ExtendedDunedainRangerEntity> type, World w) {
        super(type, w);
        this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.meleeRangedSwitching());
        this.spawnRequiresSurfaceBlock = true;

        // chance of the ranger being female
        if (this.random.nextInt(100) < 4) {
            personalInfo.setMale(false);
        } else {
            personalInfo.setMale(true);
        }
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
        this.npcItemsInv.setRangedWeapon(BOWS.getRandomItem(this.random));
        this.npcItemsInv.setIdleItemsFromRangedWeapons();

        if (this.random.nextFloat() < 0.5F) {
            this.setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(ExtendedItems.DUNEDAIN_SHIELD.get()));
        }

        // setup armor
        this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.RANGER_NORTH_BOOTS.get()));
        this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.RANGER_NORTH_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.RANGER_NORTH_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(LOTRItems.RANGER_NORTH_HELMET.get()));

        return spawnData;
    }

    @Override
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

    @Override
	protected NPCFoodPool getEatPool() {
        return ExtendedFoodPools.RANGER;
    }

    @Override
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

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return NPCEntity.registerBaseNPCAttributes()
                .add(Attributes.MAX_HEALTH, 25.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(LOTRAttributes.NPC_RANGED_INACCURACY.get(), 0.5)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    // TODO: Make sure these values match Legacy
    protected Goal createRangerRangedAttackGoal() {
        return new ExtendedRangerRangedAttackGoal(this, 1.25, 20, 16.0F);
    }

    protected Goal createRangerMeleeAttackGoal() {
        return new NPCMeleeAttackGoal(this, 1.4);
    }

    @Override
    protected void addNPCTargetingAI() {
        this.addAggressiveTargetingGoals();
    }

    public ResourceLocation getCloakTextureLocation() {
        return new ResourceLocation("lotrextended", "textures/capes/ranger.png");
    }

	@Override
	public boolean addExtendedGoals() {
		return true;
	}
}
