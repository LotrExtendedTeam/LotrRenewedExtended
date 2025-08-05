package lotr.common.entity.npc;


import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedBanditEscapeGoal;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedBanditFollowGoal;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedBanditStealGoal;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.init.ExtendedItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class ExtendedBanditEntity extends ManEntity {
    private final ItemStackHandler inventory = new ItemStackHandler(9); // 9 slotów
    private final List<ItemStack> stolenItems = new ArrayList<>();
    private boolean afterStealing = false;
    private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(
            ExtendedItems.GUNDABAD_URUK_CLEAVER, ExtendedItems.GUNDABAD_URUK_CLEAVER,
            ExtendedItems.GUNDABAD_URUK_CLEAVER, ExtendedItems.GUNDABAD_URUK_CLEAVER,
            ExtendedItems.GUNDABAD_URUK_CLEAVER, ExtendedItems.GUNDABAD_URUK_CLEAVER,
            ExtendedItems.GUNDABAD_URUK_DAGGER, ExtendedItems.GUNDABAD_URUK_DAGGER,
            ExtendedItems.GUNDABAD_URUK_SPEAR);
    private MobEntity ExtendedBanditEntity;

    public ExtendedBanditEntity(EntityType<? extends ManEntity> type, World w) {
        super(type, w);
    }


    public void addStolenItem(ItemStack itemStack) {
        this.stolenItems.add(itemStack);
    }

    @Override
    protected void addNPCAI() {
        super.addNPCAI();
        this.goalSelector.addGoal(0, new ExtendedBanditFollowGoal(this, this.getNavigation()));
        this.goalSelector.addGoal(1, new ExtendedBanditStealGoal(this, this.getNavigation()));
        this.goalSelector.addGoal(2, new ExtendedBanditEscapeGoal(this, this.getNavigation()));

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
        setItemSlot(EquipmentSlotType.FEET, new ItemStack(ExtendedItems.GUNDABAD_URUK_BOOTS.get()));
        setItemSlot(EquipmentSlotType.LEGS, new ItemStack(ExtendedItems.GUNDABAD_URUK_LEGGINGS.get()));
        setItemSlot(EquipmentSlotType.CHEST, new ItemStack(ExtendedItems.GUNDABAD_URUK_CHESTPLATE.get()));
        setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.GUNDABAD_URUK_HELMET.get()));
        setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(ExtendedItems.GUNDABAD_SHIELD.get()));
        return spawnData;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Inventory", inventory.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
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


    public boolean hasAlreadyStolen() {
        return afterStealing;
    }

    public void setHasStolen(boolean stolen) {
        this.afterStealing = stolen;
    }


    @Override
    protected float getVoicePitch() {
        return super.getVoicePitch() * 0.75F;
    }

    public static MutableAttribute createAttributes() {
        return OrcEntity.regAttrs().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.22D);
    }
}
