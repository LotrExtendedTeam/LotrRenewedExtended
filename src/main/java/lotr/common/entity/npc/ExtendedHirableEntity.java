package lotr.common.entity.npc;

import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfileManager;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public interface ExtendedHirableEntity {

	NPCEntity getThis();

	boolean isTame();

    @Nullable
    default public LivingEntity getUnitOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : getThis().level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    boolean isFollowing();

    boolean isInventoryOpen();

    ItemStack checkFood();

    Inventory getCustomInventory();

    boolean wantsToAttack(LivingEntity p_142018_1_, LivingEntity p_142018_2_);

    boolean isStationary();

    void setMobKills(int kills);

    void giveExperiencePoints(float points);

    int getMobKills();

    default ITextComponent getHiredUnitName() {
    	if(getUnitProfile().stringsAreTranslated()) {
    		if(this instanceof NPCEntity) {
    			return new TranslationTextComponent(getUnitProfile().getUnitShorthandName(),
    	            new StringTextComponent(((NPCEntity)this).getPersonalInfo().getName()));
    		}
    		return new TranslationTextComponent(getUnitProfile().getUnitShorthandName(),
                new StringTextComponent("UNKNOWNNPC"));
    	}
    	return new StringTextComponent(getUnitProfile().getUnitShorthandName());
    }

    default ITextComponent getHiredUnitNameAndTitle() {
    	if(getUnitProfile().stringsAreTranslated()) {
    		if(this instanceof NPCEntity) {
    			return new TranslationTextComponent(getUnitProfile().getUnitTitle(),
    	            new StringTextComponent(((NPCEntity)this).getPersonalInfo().getName()));
    		}
    		return new TranslationTextComponent(getUnitProfile().getUnitTitle(),
                new StringTextComponent("UNKNOWNNPC"));
    	}
    	return new StringTextComponent(getUnitProfile().getUnitShorthandName());
    }

    boolean isPatrolling();

    float getHiredUnitMaxHealth();

    float getHiredUnitHealth();

    int getExpLvl();

    float getMaxXp();

    float getCurrentXp();

    boolean isAlert();

    default int getHiredUnitId() {
    	return getThis().getId();
    }

    UUID getOwnerUUID();

    void setMaxXp(float maxXp);

    void setHiredUnitHealth(float p_70606_1_);

    void setExpLvl(int lvl);

    void setCurrentXp(float currentXp);

    void setBaseHealth(int health);

    int getBaseHealth();

    void setStationary(boolean stationary);

    default void updateSlot(ItemStack stack, EquipmentSlotType slot) {
    	if (slot == EquipmentSlotType.HEAD) {
    		getCustomInventory().setItem(9, stack);
    		setNeedsSyncing();
        } else if (slot == EquipmentSlotType.CHEST) {
        	getCustomInventory().setItem(10, stack);
        	setNeedsSyncing();
        } else if (slot == EquipmentSlotType.LEGS) {
        	getCustomInventory().setItem(11, stack);
        	setNeedsSyncing();
        } else if (slot == EquipmentSlotType.FEET) {
        	getCustomInventory().setItem(12, stack);
        	setNeedsSyncing();
        } else if (slot == EquipmentSlotType.MAINHAND) {
        	getCustomInventory().setItem(13, stack);
        	setNeedsSyncing();
        } else if (slot == EquipmentSlotType.OFFHAND) {
        	getCustomInventory().setItem(14, stack);
        	setNeedsSyncing();
        }
    }

    void setNeedsSyncing();

    boolean isEquipmentOpen();
   
    void setEquipmentOpen(boolean isOpen);

    void setInventoryOpen(boolean isOpen);

    ResourceLocation getProfileLocation();

    /**
     * Gets the {@Link ExtendedHiredUnitProfile} associated with this unit
     * @Nullable
     * */
    @Nullable
    default ExtendedHiredUnitProfile getUnitProfile() {
    	return ExtendedHiredUnitProfileManager.INSTANCE.get(getProfileLocation());
    }

    public float getHiredUnitEquipmentDropChance(EquipmentSlotType p_205712_1_);

    default void setupBaseEquipment() {
    	// set initial entity base equipment based upon pools
    	// Also indicates if unit was spawned incorrectly if it spawns with no base equipment
    	getCustomInventory().setItem(15, getUnitProfile().getEquipmentPools().getRandomHelmetStack(getThis().getRandom()));
    	getCustomInventory().setItem(16, getUnitProfile().getEquipmentPools().getRandomChestplateStack(getThis().getRandom()));
    	getCustomInventory().setItem(17, getUnitProfile().getEquipmentPools().getRandomLeggingsStack(getThis().getRandom()));
    	getCustomInventory().setItem(18, getUnitProfile().getEquipmentPools().getRandomBootsStack(getThis().getRandom()));
    	getCustomInventory().setItem(19, getUnitProfile().getEquipmentPools().getRandomMeleeStack(getThis().getRandom()));
    	getCustomInventory().setItem(20, getUnitProfile().getEquipmentPools().getRandomOffhandStack(getThis().getRandom()));
    	// Needed to sync unit equipment on unit creation
    	setNeedsSyncing();
    }

    default void loadHiredUnitInventory(CompoundNBT tag) {
    	NonNullList<ItemStack> itemsStacks = NonNullList.withSize(21, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, itemsStacks);
        for (int i = 0; i < 21; i++) {
        	getCustomInventory().setItem(i, itemsStacks.get(i));
        }

        // Need to setup equipment on NBT load
        setNeedsSyncing();
    }

    default void saveHiredUnitInventory(CompoundNBT tag) {
    	// create temp NonNullList to save inventory to
    	NonNullList<ItemStack> items = NonNullList.withSize(21, ItemStack.EMPTY);
    	for (int i = 0; i < 21; i++) {
    		ItemStack item = getCustomInventory().getItem(i);
    		items.set(i, item);
    	}
    	ItemStackHelper.saveAllItems(tag, items);
    }
}