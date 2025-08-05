package lotr.common.entity.npc;


import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedBanditEscapeGoal;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedBanditFollowAndStealGoal;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
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
    private boolean hasStolen = false;
    private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(LOTRItems.BRONZE_DAGGER, LOTRItems.IRON_DAGGER);

    public ExtendedBanditEntity(EntityType<? extends ManEntity> type, World w) {
        super(type, w);
    }

    public void addStolenItem(ItemStack itemStack) {
        this.stolenItems.add(itemStack);
    }

    @Override
    protected void addNPCAI() {
    	super.addNPCAI();
    	this.goalSelector.addGoal(2, new ExtendedBanditFollowAndStealGoal(this, this.getNavigation()));
    	this.goalSelector.addGoal(3, new ExtendedBanditEscapeGoal(this, this.getNavigation()));

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
        setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));
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
        return OrcEntity.regAttrs().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.22D);
    }
}
