package lotr.common.entity.npc;

import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEquipmentSlotType;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfileManager;

import lotr.common.entity.npc.ai.ExtendedNPCCombatUpdater;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BowItem;
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
	public static final int INVENTORY_SIZE = 29;

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

    default void updateSlot(ItemStack stack, ExtendedEquipmentSlotType slot) {
    	if (slot == ExtendedEquipmentSlotType.HEAD) {
    		getCustomInventory().setItem(9, stack);
    		getThis().setItemSlot(EquipmentSlotType.HEAD, stack);
        } else if (slot == ExtendedEquipmentSlotType.CHEST) {
        	getCustomInventory().setItem(10, stack);
        	getThis().setItemSlot(EquipmentSlotType.CHEST, stack);
        } else if (slot == ExtendedEquipmentSlotType.LEGS) {
        	getCustomInventory().setItem(11, stack);
        	getThis().setItemSlot(EquipmentSlotType.LEGS, stack);
        } else if (slot == ExtendedEquipmentSlotType.FEET) {
        	getCustomInventory().setItem(12, stack);
        	getThis().setItemSlot(EquipmentSlotType.FEET, stack);
        } else if (slot == ExtendedEquipmentSlotType.MELEE) {
        	getCustomInventory().setItem(13, stack);
        	getThis().npcItemsInv.setMeleeWeapon(stack);
        	if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()) {
        		getCustomInventory().setItem(16, stack);
        	}
        } else if (slot == ExtendedEquipmentSlotType.RANGED) {
        	getCustomInventory().setItem(14, stack);
        	getThis().npcItemsInv.setRangedWeapon(stack);
        	if(getUnitProfile().getEquipmentPools().isSetIdleFromRanged()) {
        		getCustomInventory().setItem(16, stack);
        		getCustomInventory().setItem(17, stack);
        	}
        } else if (slot == ExtendedEquipmentSlotType.MOUNTED) {
        	getCustomInventory().setItem(15, stack);
        	getThis().npcItemsInv.setMeleeWeaponMounted(stack);
        	if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()) {
        		getCustomInventory().setItem(17, stack);
        	}
        } else if (slot == ExtendedEquipmentSlotType.IDLE) {
        	//getCustomInventory().setItem(16, stack);
        	//getThis().npcItemsInv.setIdleItem(stack);
        } else if (slot == ExtendedEquipmentSlotType.IDLE_MOUNTED) {
        	//getCustomInventory().setItem(17, stack);
        	//getThis().npcItemsInv.setIdleItemMounted(stack);
        }  else if (slot == ExtendedEquipmentSlotType.OFFHAND) {
        	getCustomInventory().setItem(18, stack);
        	getThis().setItemSlot(EquipmentSlotType.OFFHAND, stack);
        }
    	if(slot.isRenewedHandling()) {
    		getThis().npcItemsInv.setIdleItem(getCustomInventory().getItem(16).isEmpty() ? getCustomInventory().getItem(26) : getCustomInventory().getItem(16));
            getThis().npcItemsInv.setIdleItemMounted(getCustomInventory().getItem(17).isEmpty() ? getCustomInventory().getItem(27) : getCustomInventory().getItem(17));
    		getThis().getNPCCombatUpdater().refreshCurrentAttackMode();
    	}
    }

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

    default public Inventory makeUnitInventory() {
    	// 9 inventory slots + 10 player equipment slots + 10 base equipment slots
        return new Inventory(INVENTORY_SIZE);
    }
    /**
     * Generated the base equipment for a hired unit from a hired unit equipment pool.
     * Don't impliment this method, instead call it in your entity finalize spawn method
     * @see ExtendedHiredUnitEquipmentPool 
     * */
	default void setupUnitEquipment() {
    	// set initial entity base equipment based upon pools
    	// Also indicates if unit was spawned incorrectly if it spawns with no base equipment
    	getCustomInventory().setItem(19, getUnitProfile().getEquipmentPools().getRandomHelmetStack(getThis().getRandom()));
    	getCustomInventory().setItem(20, getUnitProfile().getEquipmentPools().getRandomChestplateStack(getThis().getRandom()));
    	getCustomInventory().setItem(21, getUnitProfile().getEquipmentPools().getRandomLeggingsStack(getThis().getRandom()));
    	getCustomInventory().setItem(22, getUnitProfile().getEquipmentPools().getRandomBootsStack(getThis().getRandom()));
    	getCustomInventory().setItem(23, getUnitProfile().getEquipmentPools().getRandomMeleeStack(getThis().getRandom()));
    	getCustomInventory().setItem(24, getUnitProfile().getEquipmentPools().getRandomRangedStack(getThis().getRandom()));
    	getCustomInventory().setItem(25, getUnitProfile().getEquipmentPools().getRandomMountedStack(getThis().getRandom()));
    	getCustomInventory().setItem(26, getUnitProfile().getEquipmentPools().getRandomIdleStack(getThis().getRandom()));
    	getCustomInventory().setItem(27, getUnitProfile().getEquipmentPools().getRandomIdleMountedStack(getThis().getRandom()));
    	getCustomInventory().setItem(28, getUnitProfile().getEquipmentPools().getRandomOffhandStack(getThis().getRandom()));
    	getThis().setItemSlot(EquipmentSlotType.HEAD, getCustomInventory().getItem(19));
    	getThis().setItemSlot(EquipmentSlotType.CHEST, getCustomInventory().getItem(20));
    	getThis().setItemSlot(EquipmentSlotType.LEGS, getCustomInventory().getItem(21));
    	getThis().setItemSlot(EquipmentSlotType.FEET, getCustomInventory().getItem(22));
    	getThis().npcItemsInv.clearContent();
    	getThis().npcItemsInv.setMeleeWeapon(getCustomInventory().getItem(23));
    	getThis().npcItemsInv.setRangedWeapon(getCustomInventory().getItem(24));
    	getThis().npcItemsInv.setMeleeWeaponMounted(getCustomInventory().getItem(25));
    	if(getUnitProfile().getEquipmentPools().isSetIdleFromPool()) {
    		getThis().npcItemsInv.setIdleItem(getCustomInventory().getItem(26));
    		getThis().npcItemsInv.setIdleItemMounted(getCustomInventory().getItem(27));
    	}else if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()){
    		getCustomInventory().setItem(26, getCustomInventory().getItem(23));
        	getCustomInventory().setItem(27, getCustomInventory().getItem(25));
    		getThis().npcItemsInv.setIdleItemsFromMeleeWeapons();
    	}else {
    		getCustomInventory().setItem(26, getCustomInventory().getItem(24));
        	getCustomInventory().setItem(27, getCustomInventory().getItem(24));
    		getThis().npcItemsInv.setIdleItemsFromRangedWeapons();
    	}
    	getThis().setItemSlot(EquipmentSlotType.OFFHAND, getCustomInventory().getItem(28));
    	((ExtendedNPCCombatUpdater)getThis().getNPCCombatUpdater()).refreshHand();
    }

    /**
     * Loads the hired unit's inventory from nbt, and handles inventory changes between versions
     * Don't impliment this method, instead call it in your entity load data method
     * @param tag A CompoundNBT containing the saved inventory
     * */
	default void loadHiredUnitInventory(CompoundNBT tag) {
    	if(tag.contains("HiredUnitInventory")) {
    		CompoundNBT unitInventory = tag.getCompound("HiredUnitInventory");
    		if(unitInventory.isEmpty()) {
    			ExtendedLog.error("Tried loading a Hired Unit Inventory with a empty inventory, dumping nbt and loading default base inventory!");
    			ExtendedLog.error(tag.toString());
    			setupUnitEquipment();
    			return;
    		}
    		if(unitInventory.getInt("unit_inventory_version")==UNIT_INVENTORY_VERSION) {
    			// 1.7.3 Added ranged, mounted, idle and idle mounted slots
        		// 9 inventory slots + 10 player equipment slots + 10 base equipment slots
    			NonNullList<ItemStack> itemsStacks = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
    			ItemStackHelper.loadAllItems(unitInventory, itemsStacks);
    			for (int i = 0; i < INVENTORY_SIZE; i++) {
    				getCustomInventory().setItem(i, itemsStacks.get(i));
    			}
    		}else {
    			ExtendedLog.error("Tried loading a Hired Unit Inventory with a malformed/unknown version number, dumping nbt and loading default base inventory!");
    			ExtendedLog.error(unitInventory.toString());
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
    		// Load old item slot index into new positions
    		// Food
    		getCustomInventory().setItem(0, itemsStacks.get(0));
    		getCustomInventory().setItem(1, itemsStacks.get(1));
    		getCustomInventory().setItem(2, itemsStacks.get(2));
    		getCustomInventory().setItem(3, itemsStacks.get(3));
    		getCustomInventory().setItem(4, itemsStacks.get(4));
    		getCustomInventory().setItem(5, itemsStacks.get(5));
    		getCustomInventory().setItem(6, itemsStacks.get(6));
    		getCustomInventory().setItem(7, itemsStacks.get(7));
    		getCustomInventory().setItem(8, itemsStacks.get(8));
    		// Equipment
    		getCustomInventory().setItem(9, itemsStacks.get(9)); //Player Helmet
    		getCustomInventory().setItem(10, itemsStacks.get(10)); //Player Chestplate
    		getCustomInventory().setItem(11, itemsStacks.get(11)); //Player Leggings
    		getCustomInventory().setItem(12, itemsStacks.get(12)); //Player Boots
    		getCustomInventory().setItem(13, itemsStacks.get(13)); //Player Melee
    		// Player 14-17 (14:ranged, 15:mounted, 16:idle, 17:idle_mounted empty for player equipment)
    		// Catch bows in melee slot from 1.7.2 and before and put in ranged slot
    		if(getCustomInventory().getItem(13).getItem() instanceof BowItem) {
    			getCustomInventory().setItem(14, getCustomInventory().getItem(13)); //Player Ranged
    			getCustomInventory().setItem(13, ItemStack.EMPTY);
    		}
    		// Move player offhand item to new index
    		getCustomInventory().setItem(18, itemsStacks.get(14)); //Player Offhand
    		// Try to catch updaing unit inventory from 1.7.1 when base equipment will just be empty
    		getCustomInventory().setItem(19, itemsStacks.get(15).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomHelmetStack(getThis().getRandom()) : itemsStacks.get(15));
    		getCustomInventory().setItem(20, itemsStacks.get(16).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomChestplateStack(getThis().getRandom()) : itemsStacks.get(16));
    		getCustomInventory().setItem(21, itemsStacks.get(17).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomLeggingsStack(getThis().getRandom()) : itemsStacks.get(17));
    		getCustomInventory().setItem(22, itemsStacks.get(18).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomBootsStack(getThis().getRandom()) : itemsStacks.get(18));
    		getCustomInventory().setItem(23, itemsStacks.get(19).isEmpty() ? getUnitProfile().getEquipmentPools().getRandomMeleeStack(getThis().getRandom()) : itemsStacks.get(19));
    		// Catch base bows in base melee slot from 1.7.2 and update
    		if(getCustomInventory().getItem(23).getItem() instanceof BowItem) {
    			getCustomInventory().setItem(23, getUnitProfile().getEquipmentPools().getRandomMeleeStack(getThis().getRandom()));
    		}
    		// Update inventory from 1.7.2 by generating missing fields
    		getCustomInventory().setItem(24, getUnitProfile().getEquipmentPools().getRandomRangedStack(getThis().getRandom()));
    		getCustomInventory().setItem(25, getUnitProfile().getEquipmentPools().getRandomMountedStack(getThis().getRandom()));
    		getCustomInventory().setItem(26, getUnitProfile().getEquipmentPools().getRandomIdleStack(getThis().getRandom()));
    		getCustomInventory().setItem(27, getUnitProfile().getEquipmentPools().getRandomIdleMountedStack(getThis().getRandom()));
    		// Handle idle item logic
    		if(getUnitProfile().getEquipmentPools().isSetIdleFromMelee()){
    			getCustomInventory().setItem(26, getCustomInventory().getItem(23));
    			getCustomInventory().setItem(27, getCustomInventory().getItem(25));
    		}else if(getUnitProfile().getEquipmentPools().isSetIdleFromRanged()){
    			getCustomInventory().setItem(26, getCustomInventory().getItem(24));
    			getCustomInventory().setItem(27, getCustomInventory().getItem(24));
    		}
    		// Finally move base offhand item to new index
    		getCustomInventory().setItem(28, itemsStacks.get(20));
    	}
    	// Lastly update the entity's equipment slots and renewed item inventory with upgraded inventory layout
    	getThis().setItemSlot(EquipmentSlotType.HEAD, getCustomInventory().getItem(9).isEmpty() ? getCustomInventory().getItem(19) : getCustomInventory().getItem(9));
    	getThis().setItemSlot(EquipmentSlotType.CHEST, getCustomInventory().getItem(10).isEmpty() ? getCustomInventory().getItem(20) : getCustomInventory().getItem(10));
    	getThis().setItemSlot(EquipmentSlotType.LEGS, getCustomInventory().getItem(11).isEmpty() ? getCustomInventory().getItem(21) : getCustomInventory().getItem(11));
    	getThis().setItemSlot(EquipmentSlotType.FEET, getCustomInventory().getItem(12).isEmpty() ? getCustomInventory().getItem(22) : getCustomInventory().getItem(12));
    	getThis().npcItemsInv.clearContent();
    	getThis().npcItemsInv.setMeleeWeapon(getCustomInventory().getItem(13).isEmpty() ? getCustomInventory().getItem(23) : getCustomInventory().getItem(13));
    	getThis().npcItemsInv.setRangedWeapon(getCustomInventory().getItem(14).isEmpty() ? getCustomInventory().getItem(24) : getCustomInventory().getItem(14));
    	getThis().npcItemsInv.setMeleeWeaponMounted(getCustomInventory().getItem(15).isEmpty() ? getCustomInventory().getItem(25) : getCustomInventory().getItem(15));
    	getThis().npcItemsInv.setIdleItem(getCustomInventory().getItem(16).isEmpty() ? getCustomInventory().getItem(26) : getCustomInventory().getItem(16));
        getThis().npcItemsInv.setIdleItemMounted(getCustomInventory().getItem(17).isEmpty() ? getCustomInventory().getItem(27) : getCustomInventory().getItem(17));
    	getThis().setItemSlot(EquipmentSlotType.OFFHAND, getCustomInventory().getItem(18).isEmpty() ? getCustomInventory().getItem(28) : getCustomInventory().getItem(18));
    	((ExtendedNPCCombatUpdater)getThis().getNPCCombatUpdater()).refreshHand();
    }

    /**
     * Saves a hired unit's inventory to nbt, no need to handle version changes here, since its corrected by the time this is saved
     * Don't impliment this method, instead call it in your entity save data method
     * @param tag A CompoundNBT to be saved to
     * */
    default void saveHiredUnitInventory(CompoundNBT tag) {
    	// create temp NonNullList to save inventory to
    	NonNullList<ItemStack> items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
    	for (int i = 0; i < INVENTORY_SIZE; i++) {
    		ItemStack item = getCustomInventory().getItem(i);
    		items.set(i, item);
    	}
    	CompoundNBT unitInventory = new CompoundNBT();
    	unitInventory.putInt("unit_inventory_version", UNIT_INVENTORY_VERSION);
    	ItemStackHelper.saveAllItems(unitInventory, items);
    	tag.put("HiredUnitInventory", unitInventory);
    }
}