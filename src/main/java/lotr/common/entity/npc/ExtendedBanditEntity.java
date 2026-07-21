package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedBanditEscapeGoal;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedBanditFollowAndStealGoal;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.ai.goal.TalkToCurrentGoal;
import lotr.common.entity.npc.ai.goal.WatchSunriseSunsetGoal;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class ExtendedBanditEntity extends ManEntity implements ExtendedINPCEntitySettings {
    private final ItemStackHandler inventory = new ItemStackHandler(9); // 9 slots
    private final List<ItemStack> stolenItems = new ArrayList<>();
    private boolean hasStolen = false;
    private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(LOTRItems.BRONZE_DAGGER, LOTRItems.IRON_DAGGER);
    @SuppressWarnings("unchecked")
    public ExtendedBanditEntity(EntityType<? extends ManEntity> type, World w) {
        super(type, w);
        this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.meleeOnly());
    }

    public void addStolenItem(ItemStack itemStack) {
        this.stolenItems.add(itemStack);
    }

    @Override
    protected void addNPCAI() {
        ((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
        this.getNavigation().setCanFloat(true);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.initialiseAttackGoals(this.getAttackGoalsHolder());
        this.addNPCTargetingAI();

        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.addAttackGoal(2);
        this.goalSelector.addGoal(3, new ExtendedBanditFollowAndStealGoal(this, this.getNavigation()));
        this.goalSelector.addGoal(4, new ExtendedBanditEscapeGoal(this, this.getNavigation()));
        this.goalSelector.addGoal(5, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(6, new TalkToCurrentGoal(this));
        this.goalSelector.addGoal(7, new WatchSunriseSunsetGoal(this, 0.005F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, (double)1.0F));
        this.addConsumingGoals(9);
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(12, new LookRandomlyGoal(this));
    }

    @Override
    protected Goal createAttackGoal() {
        return new NPCMeleeAttackGoal(this, 1.3);
    }

    @Override
    protected NPCFoodPool getEatPool() {
        return NPCFoodPools.DUNLENDING;
    }

    @Override
    protected NPCFoodPool getDrinkPool() {
        return NPCFoodPools.DUNLENDING_DRINK;
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(random));
        npcItemsInv.setIdleItemsFromMeleeWeapons();

        // 50% chance they spawn with a hat
        if (random.nextBoolean()) {
            setItemSlot(EquipmentSlotType.HEAD, ExtendedItemStackUtils.dyeItemStack(new ItemStack(ExtendedItems.LEATHER_HAT.get()), TextFormatting.BLACK.getColor()));
        }

        return spawnData;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Inventory", inventory.serializeNBT());
        compound.putBoolean("hasStolen", hasStolen);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        hasStolen = compound.getBoolean("hasStolen"); // defaults to false
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack.copy());
            }
        }
    }

    @Override
    protected void addNPCTargetingAI() {
        this.addAggressiveTargetingGoals();
    }

    public boolean hasStolen() {
        return hasStolen;
    }

    public void setStolen() {
        this.hasStolen = true;
    }

    @Override
    protected float getVoicePitch() {
        return super.getVoicePitch() * 0.75F;
    }

    public static MutableAttribute createAttributes() {
        return AbstractMannishEntity.regAttrs().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.22D);
    }
}
