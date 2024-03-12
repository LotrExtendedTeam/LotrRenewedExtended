package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.*;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.LOTRAttributes;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedDunedainRangerEntity extends ManEntity {

    public ExtendedDunedainRangerEntity(EntityType<? extends ExtendedDunedainRangerEntity> type, World w) {
        super(type, w);
        this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.meleeRangedSwitching());
        this.spawnRequiresSurfaceBlock = true;
    }

    // TODO: Add Ranger eat and drink from Legacy
    //  RANGER = new LOTRFoods(new ItemStack[] { new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(Items.field_151101_aQ), new ItemStack(LOTRMod.muttonCooked),
    //  new ItemStack(LOTRMod.deerCooked), new ItemStack(Items.field_151168_bH), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(Items.field_151025_P),
    //  new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry), new ItemStack(LOTRMod.cranberry), new ItemStack(LOTRMod.raspberry), new ItemStack(LOTRMod.elderberry) });
    //  RANGER_DRINK = (new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugAppleJuice)
    protected NPCFoodPool getEatPool() {
        return NPCFoodPools.GONDOR;
    }

    protected NPCFoodPool getDrinkPool() {
        return NPCFoodPools.GONDOR_DRINK;
    }

    @Override
    protected void initialiseAttackGoals(AttackGoalsHolder holder) {
        holder.setMeleeAttackGoal(this.createRangerMeleeAttackGoal());
        holder.setRangedAttackGoal(this.createRangerRangedAttackGoal());
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
