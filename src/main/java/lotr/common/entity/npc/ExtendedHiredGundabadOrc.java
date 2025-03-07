package lotr.common.entity.npc;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;

import lotr.common.entity.npc.ai.goal.ExtendedHiredFollowPlayerGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredPlayerHurtByTargetGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredPlayerHurtTargetGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredSitGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredWaterAvoidingRandomWalkingGoal;
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
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedHiredGundabadOrc extends GundabadOrcEntity implements ExtendedHirableEntity {

    private static final int HEAL_RATE = 80;
    private int lastHealed = 0;

    protected static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class, DataSerializers.BYTE);
    protected static final DataParameter<Optional<UUID>> DATA_OWNERUUID_ID = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<Integer> LVL = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.INT);
    private static final DataParameter<Float> CURRENT_XP = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.FLOAT);

    private static final DataParameter<Integer> BASE_HEALTH = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.INT);
    private static final DataParameter<Float> MAX_XP = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.FLOAT);

    private static final DataParameter<Integer> KILLS = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class, DataSerializers.INT);

    private static final DataParameter<Boolean> FOLLOWING = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> STATIONARY = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PATROLLING = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALERT = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GUARDING = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.BOOLEAN);

    private static final DataParameter<Float> TMP_LAST_HEALTH = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.FLOAT);
    private boolean tmpHealthLoaded = false;
    private boolean healthUpdateFromTmpHealth = false;

    private static final DataParameter<Boolean> INVENTORY_OPEN = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class,DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> EQUIPMENT_OPEN = EntityDataManager.defineId(ExtendedHiredGundabadOrc.class, DataSerializers.BOOLEAN);

    // 9 inventory slots + 6 player equipment slots + 6 base equipment slots
    private Inventory internalUnitInventory = new Inventory(21);
    // Use to initiate equipment sync. This is done on creation, nbt load, and on inventory change
    private boolean inventoryNeedsSetupSyncing = false;

    public ExtendedHiredGundabadOrc(EntityType<? extends GundabadOrcEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();
        setTame(false);
    }

    public void tame(PlayerEntity p_193101_1_) {
        this.setTame(true);
        this.setOwnerUUID(p_193101_1_.getUUID());
        this.setFollowing(true);
    }

    @Override
    public float getHiredUnitMaxHealth() {
        return getMaxHealth();
    }

    @Override
    public float getHiredUnitHealth() {
        return getHealth();
    }

    @Override
    public boolean isAlert() {
        return this.entityData.get(ALERT);
    }

    @Override
    public boolean isStationary() {
        return this.entityData.get(STATIONARY);
    }

    @Override
    public boolean isPatrolling() {
        return this.entityData.get(PATROLLING);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        setupUnitEquipment();
        return spawnData;
    }

    @Override
    public boolean wantsToAttack(LivingEntity p_142018_1_, LivingEntity p_142018_2_) {
        return true;
    }

    @Override
    public void giveExperiencePoints(float points) {
        ExtendedHiredUnitHelper.giveExperiencePoints(this, points);
    }

    @Override
    public void setExpLvl(int lvl) {
        this.entityData.set(LVL, lvl);
    }

    @Override
    public int getExpLvl() {
        return this.entityData.get(LVL);
    }

    @Override
    public void setMobKills(int kills) {
        this.entityData.set(KILLS, kills);
    }

    @Override
    public int getMobKills() {
        return this.entityData.get(KILLS);
    }

    @Override
    public Inventory getCustomInventory() {
        return internalUnitInventory;
    }

    @Override
    public ItemStack checkFood() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemstack = this.internalUnitInventory.getItem(i);
            if (itemstack.isEdible()) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isTame() {
        return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
    }

    @Override
    public void setCurrentXp(float currentXp) {
        this.entityData.set(CURRENT_XP, currentXp);
    }

    @Override
    public float getCurrentXp() {
        return this.entityData.get(CURRENT_XP);
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
    public void setMaxXp(float maxXp) {
        this.entityData.set(MAX_XP, maxXp);
    }

    @Override
    public void setHiredUnitHealth(float health) {
        setHealth(health);
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
    public float getMaxXp() {
        return this.entityData.get(MAX_XP);
    }

    @Override
    public void setBaseHealth(int health) {
        this.entityData.set(BASE_HEALTH, health);
    }

    @Override
    public int getBaseHealth() {
        return this.entityData.get(BASE_HEALTH);
    }

    @Override
    public boolean isFollowing() {
        return this.entityData.get(FOLLOWING);
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            if (this.isAlliedTo(player)) {
                if (this.level.isClientSide()) {
                    ExtendedPacketHandler.sendToServer(new ExtendedCOpenHiredMenuPacket(getId()));
                }
            }
            return ActionResultType.sidedSuccess(this.level.isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    private void setPatrolling(boolean patrolling) {
        this.entityData.set(PATROLLING, patrolling);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
        this.entityData.define(DATA_OWNERUUID_ID, Optional.empty());
        this.entityData.define(LVL, 1);
        this.entityData.define(CURRENT_XP, 0f);
        this.entityData.define(MAX_XP, 1f);
        this.entityData.define(KILLS, 0);
        this.entityData.define(FOLLOWING, false);
        this.entityData.define(GUARDING, false);
        this.entityData.define(PATROLLING, false);
        this.entityData.define(ALERT, false);
        this.entityData.define(STATIONARY, false);
        this.entityData.define(BASE_HEALTH, 18);
        this.entityData.define(INVENTORY_OPEN, false);
        this.entityData.define(EQUIPMENT_OPEN, false);
        this.entityData.define(TMP_LAST_HEALTH, 18f);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return OrcEntity.regAttrs()
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.MAX_HEALTH, 18.0D);
    }

    public void setTame(boolean isTamed) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (isTamed) {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 4));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -5));
        }
    }

    public void setFollowing(boolean following) {
        this.entityData.set(FOLLOWING, following);
    }

    @Override
    public boolean isAlliedTo(Entity p_184191_1_) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getUnitOwner();
            if (p_184191_1_ == livingentity) {
                return true;
            }

            if (livingentity != null) {
                return livingentity.isAlliedTo(p_184191_1_);
            }
        }

        return super.isAlliedTo(p_184191_1_);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
    	super.addAdditionalSaveData(tag);
    	if (this.getOwnerUUID() != null) {
    		tag.putUUID("Owner", this.getOwnerUUID());
    	}

        saveHiredUnitInventory(tag);

    	tag.putBoolean("following", this.isFollowing());
    	tag.putBoolean("stationary", this.isStationary());
    	tag.putInt("mob_kills", this.getMobKills());
    	tag.putInt("xp_level", this.getExpLvl());
    	tag.putFloat("current_xp", this.getCurrentXp());
    	tag.putFloat("max_xp", this.getMaxXp());
    	tag.putInt("base_health", this.getBaseHealth());
    	tag.putFloat("tmp_last_health", this.getHealth());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);

        UUID uuid;
        if (tag.hasUUID("Owner")) {
            uuid = tag.getUUID("Owner");
        } else {
            String ownerString = tag.getString("Owner");
            uuid = PreYggdrasilConverter.convertMobOwnerIfNecessary(this.getServer(), ownerString);
        }

        if (uuid != null) {
            try {
                this.setOwnerUUID(uuid);
                this.setTame(true);
            } catch (Throwable var4) {
                this.setTame(false);
            }
        }

        loadHiredUnitInventory(tag);

        if (tag.contains("following")) {
            this.setFollowing(tag.getBoolean("following"));
        }
        if (tag.contains("xp_level")) {
            this.setExpLvl(tag.getInt("xp_level"));
        }
        if (tag.contains("current_xp")) {
            this.setCurrentXp(tag.getInt("current_xp"));
        }
        if (tag.contains("max_xp")) {
            this.setMaxXp(tag.getInt("max_xp"));
        }
        if (tag.contains("mob_kills")) {
            this.setMobKills(tag.getInt("mob_kills"));
        }
        if (tag.contains("base_health")) {
            this.setBaseHealth(tag.getInt("base_health"));
        }
        if (tag.contains("tmp_last_health")) {
            this.entityData.set(TMP_LAST_HEALTH, tag.getFloat("tmp_last_health"));
            this.setHealth(tag.getFloat("tmp_last_health"));
            tmpHealthLoaded = true;
        }

        if (tag.contains("stationary")) {
            this.setStationary(tag.getBoolean("stationary"));
        }
    }

    @Override
    public void tick() {
        // heal
        if (lastHealed >= HEAL_RATE)  {
            // Ensure that entity is not dead before healing
            if (this.getHealth() < this.getMaxHealth() && this.isAlive()) {
                this.setHealth(this.getHealth() + 1);
                lastHealed = 0;
            }
        } else {
            lastHealed++;
        }

        checkStats();
        synchronizeEquipment();

        if (tmpHealthLoaded && !healthUpdateFromTmpHealth) {
            this.setHealth(getTmpLastHealth());
            healthUpdateFromTmpHealth = true;
        }

        super.tick();
    }

    private float getTmpLastHealth() {
        return this.entityData.get(TMP_LAST_HEALTH);
    }

    /**
     * This method is run every tick on both the server to synchronize equipment
     * Server will only sync equipment when changes are detected
     */
    public void synchronizeEquipment() {
        // only sync inventory when it actualy needs syncing
        if(!level.isClientSide && inventoryNeedsSetupSyncing) {
        	setItemSlot(EquipmentSlotType.HEAD, internalUnitInventory.getItem(9).isEmpty() ? internalUnitInventory.getItem(15) : internalUnitInventory.getItem(9));
            setItemSlot(EquipmentSlotType.CHEST, internalUnitInventory.getItem(10).isEmpty() ? internalUnitInventory.getItem(16) : internalUnitInventory.getItem(10));
            setItemSlot(EquipmentSlotType.LEGS, internalUnitInventory.getItem(11).isEmpty() ? internalUnitInventory.getItem(17) : internalUnitInventory.getItem(11));
            setItemSlot(EquipmentSlotType.FEET, internalUnitInventory.getItem(12).isEmpty() ? internalUnitInventory.getItem(18) : internalUnitInventory.getItem(12));
            setItemSlot(EquipmentSlotType.MAINHAND, internalUnitInventory.getItem(13).isEmpty() ? internalUnitInventory.getItem(19) : internalUnitInventory.getItem(13));
            setItemSlot(EquipmentSlotType.OFFHAND, internalUnitInventory.getItem(14).isEmpty() ? internalUnitInventory.getItem(20) : internalUnitInventory.getItem(14));
            inventoryNeedsSetupSyncing=false;
        }
    }

    public void setOwnerUUID(@Nullable UUID p_184754_1_) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(p_184754_1_));
    }

    @Override
    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNERUUID_ID).orElse((UUID)null);
    }

    public void checkStats() {
        if ((int) this.getMaxHealth() != getBaseHealth()) {
            modifyMaxHealth(getBaseHealth() - 18, "Base Health from current level", false);
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int looting, boolean player) {
        // Disabled due to NPCEntity using new logic we don't want for unit drops
        //super.dropCustomDeathLoot(damageSource, looting, player);
        ExtendedHiredUnitHelper.dropCustomDeathLoot(this, this, random, internalUnitInventory, looting, player);
    }

    public void modifyMaxHealth(int change, String name, boolean permanent) {
        ModifiableAttributeInstance attributeInstance = this.getAttribute(Attributes.MAX_HEALTH);
        Set<AttributeModifier> modifiers = attributeInstance.getModifiers();
        if (!modifiers.isEmpty()) {
            for (AttributeModifier attributeModifier : modifiers) {
                if (attributeModifier != null && attributeModifier.getName().equals(name)) {
                    this.getAttribute(Attributes.MAX_HEALTH).removeModifier(attributeModifier);
                }
            }
        }
        AttributeModifier HEALTH_MODIFIER = new AttributeModifier(name, change, AttributeModifier.Operation.ADDITION);
        if (permanent) {
            attributeInstance.addPermanentModifier(HEALTH_MODIFIER);
        } else {
            attributeInstance.addTransientModifier(HEALTH_MODIFIER);
        }
    }

    @Override
    public float getHiredUnitEquipmentDropChance(EquipmentSlotType slotType) {
    	return getEquipmentDropChance(slotType);
    }

    @Override
    public ResourceLocation getProfileLocation() {
        return ExtendedHiredUnitTypes.GUNDABAD_ORC;
    }

    @Override
    public void setStationary(boolean stationary) {
        this.entityData.set(STATIONARY, stationary);
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
	public NPCEntity getThis() {
		return this;
	}

	@Override
	public void setNeedsSyncing() {
		this.inventoryNeedsSetupSyncing=true;
	}
}
