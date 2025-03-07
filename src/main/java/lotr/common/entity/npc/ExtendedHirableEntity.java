package lotr.common.entity.npc;

import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfileManager;

import lotr.common.entity.npc.ai.ExtendedNPCCombatUpdater;
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

	/**
	 * Defines the hired unit inventory version
	 * DNE/0 for 1.7.1->1.7.2
	 * 1 for 1.7.3->?.?.?
	 * */
	public static final int UNIT_INVENTORY_VERSION = 1;

	/**
	 * Gets the implementing NPCEntity using this ExtendedHirableEntity so we can use entity methods inside here
	 * @return An instance of the implementing NPCEntity 
	 * */
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
     * Gets the unit profile associated with this unit
     * @see ExtendedHiredUnitProfile
     * @Nullable
     * */
    @Nullable
    default ExtendedHiredUnitProfile getUnitProfile() {
    	return ExtendedHiredUnitProfileManager.INSTANCE.get(getProfileLocation());
    }

    public float getHiredUnitEquipmentDropChance(EquipmentSlotType p_205712_1_);

    /**
     * Generated the base equipment for a hired unit from a hired unit equipment pool.
     * Don't impliment this method, instead call it in your entity finalize spawn method
     * @see ExtendedHiredUnitEquipmentPool 
     * */
	default void setupUnitEquipment() {
    	// set initial entity base equipment based upon pools
    	// Also indicates if unit was spawned incorrectly if it spawns with no base equipment
    	getCustomInventory().setItem(15, getUnitProfile().getEquipmentPools().getRandomHelmetStack(getThis().getRandom()));
    	getCustomInventory().setItem(16, getUnitProfile().getEquipmentPools().getRandomChestplateStack(getThis().getRandom()));
    	getCustomInventory().setItem(17, getUnitProfile().getEquipmentPools().getRandomLeggingsStack(getThis().getRandom()));
    	getCustomInventory().setItem(18, getUnitProfile().getEquipmentPools().getRandomBootsStack(getThis().getRandom()));
    	getCustomInventory().setItem(19, getUnitProfile().getEquipmentPools().getRandomMeleeStack(getThis().getRandom()));
    	getCustomInventory().setItem(20, getUnitProfile().getEquipmentPools().getRandomOffhandStack(getThis().getRandom()));
    	getThis().npcItemsInv.clearContent();
    	getThis().npcItemsInv.setMeleeWeapon(ItemStack.EMPTY);
    	getThis().npcItemsInv.setRangedWeapon(ItemStack.EMPTY);
    	getThis().npcItemsInv.setMeleeWeaponMounted(ItemStack.EMPTY);
    	if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()) {
    		getThis().npcItemsInv.setIdleItemsFromMeleeWeapons();
    	}else if(getUnitProfile().getEquipmentPools().isSetIdleFromRanged()){
    		getThis().npcItemsInv.setIdleItemsFromRangedWeapons();
    	}else {
        	getThis().npcItemsInv.setIdleItem(ItemStack.EMPTY);
        	getThis().npcItemsInv.setIdleItemMounted(ItemStack.EMPTY);
    	}
    	((ExtendedNPCCombatUpdater)getThis().getNPCCombatUpdater()).refreshHand();
    	// Needed to sync unit equipment on unit creation
    	setNeedsSyncing();
    }

    /**
     * Loads the hired unit's inventory from nbt, and handles inventory changes between versions
     * Don't impliment this method, instead call it in your entity load data method
     * @param tag A CompoundNBT containing the saved inventory
     * */
	default void loadHiredUnitInventory(CompoundNBT tag) {
    	if(tag.contains("unit_inventory_version")) {
    		if(tag.getInt("unit_inventory_version")==UNIT_INVENTORY_VERSION) {
    			NonNullList<ItemStack> itemsStacks = NonNullList.withSize(21, ItemStack.EMPTY);
    			ItemStackHelper.loadAllItems(tag, itemsStacks);
    			for (int i = 0; i < 21; i++) {
    				getCustomInventory().setItem(i, itemsStacks.get(i));
    			}
    		}else {
    			ExtendedLog.error("Tried loading a Hired Unit Inventory with a malformed/unknown version number, dumping nbt and loading default base inventory!");
    			ExtendedLog.error(tag.toString());
    			setupUnitEquipment();
    			return;
    		}
    	}else {
    		// Handle version 1.7.2 and before
    		// 1.7.1 can't properly upgrage with logic, just going to dump combined equipment into the player equipment slots
    		// 9 inventory slots + 6 combined equipment slots
    		// 1.7.2 Added split base and player equipment slots
    		// 9 inventory slots + 6 player equipment slots + 6 base equipment slots
    		ExtendedLog.error("Loaded Hired Unit Inventory in format [0]. Upgrading inventory to format ["+UNIT_INVENTORY_VERSION+"]");
    		NonNullList<ItemStack> itemsStacks = NonNullList.withSize(21, ItemStack.EMPTY);
    		ItemStackHelper.loadAllItems(tag, itemsStacks);
    		getCustomInventory().setItem(0, itemsStacks.get(0));
    		getCustomInventory().setItem(1, itemsStacks.get(1));
    		getCustomInventory().setItem(2, itemsStacks.get(2));
    		getCustomInventory().setItem(3, itemsStacks.get(3));
    		getCustomInventory().setItem(4, itemsStacks.get(4));
    		getCustomInventory().setItem(5, itemsStacks.get(5));
    		getCustomInventory().setItem(6, itemsStacks.get(6));
    		getCustomInventory().setItem(7, itemsStacks.get(7));
    		getCustomInventory().setItem(8, itemsStacks.get(8));

    		getCustomInventory().setItem(9, itemsStacks.get(9));
    		getCustomInventory().setItem(10, itemsStacks.get(10));
    		getCustomInventory().setItem(11, itemsStacks.get(11));
    		getCustomInventory().setItem(12, itemsStacks.get(12));
    		getCustomInventory().setItem(13, itemsStacks.get(13));
    		getCustomInventory().setItem(14, itemsStacks.get(14));

    		getCustomInventory().setItem(15, itemsStacks.get(15));
    		getCustomInventory().setItem(16, itemsStacks.get(16));
    		getCustomInventory().setItem(17, itemsStacks.get(17));
    		getCustomInventory().setItem(18, itemsStacks.get(18));
    		getCustomInventory().setItem(19, itemsStacks.get(19));
    		getCustomInventory().setItem(20, itemsStacks.get(20));
    	}
    	// Need to setup equipment on NBT load
    	setNeedsSyncing();
    }

    /**
     * Saves a hired unit's inventory to nbt, no need to handle version changes here, since its corrected by the time this is saved
     * Don't impliment this method, instead call it in your entity save data method
     * @param tag A CompoundNBT to be saved to
     * */
    default void saveHiredUnitInventory(CompoundNBT tag) {
    	tag.putInt("unit_inventory_version", UNIT_INVENTORY_VERSION);
    	// create temp NonNullList to save inventory to
    	NonNullList<ItemStack> items = NonNullList.withSize(21, ItemStack.EMPTY);
    	for (int i = 0; i < 21; i++) {
    		ItemStack item = getCustomInventory().getItem(i);
    		items.set(i, item);
    	}
    	ItemStackHelper.saveAllItems(tag, items);
    }
}