package lotr.common.entity.npc;

import lotr.common.entity.npc.data.ExtendedFoodPools;
import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.*;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.LOTRAttributes;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedDunedainRangerEntity extends ManEntity {

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
        moveCloak();
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
        double d3 = 10.0;
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

    // TODO: This might cause problems?
    @Override
    protected Goal createAttackGoal() {
        return createRangerRangedAttackGoal();
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
        return new NPCRangedAttackGoal(this, 1.25, 20, 16.0F);
    }

    protected Goal createRangerMeleeAttackGoal() {
        return new NPCMeleeAttackGoal(this, 1.4);
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
        // TODO: Once the Barrow Dagger is an item add a chance of them spawning with it
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.IRON_DAGGER.get()));
        this.npcItemsInv.setRangedWeapon(new ItemStack(Items.BOW));
        this.npcItemsInv.setIdleItemsFromRangedWeapons();

        // setup armor
        this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.RANGER_NORTH_BOOTS.get()));
        this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.RANGER_ITHILIEN_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.RANGER_NORTH_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(LOTRItems.RANGER_NORTH_HELMET.get()));

        return spawnData;
    }

    public ResourceLocation getCloakTextureLocation() {
        return new ResourceLocation("lotr", "textures/cape/ranger.png");
    }

    // TODO: Add support for Ranger Sneaking
    //  Code from 1.7.10
    // Looks like 1.7.10's equivalent of DataParameter
    // /*     */   public boolean isRangerSneaking() {
    ///*  55 */     return (this.dataWatcher.getWatchableObjectByte(17) == 1);
    ///*     */   }
    ///*     */   public void setRangerSneaking(boolean flag) {
    ///*  60 */     this.field_70180_af.func_75692_b(17, Byte.valueOf(flag ? 1 : 0));
    ///*  61 */     if (flag)
    ///*     */     {
    ///*  63 */       this.sneakCooldown = 20;
    ///*     */     }
    ///*     */   }
}
