package lotr.common.entity.npc;

import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfileManager;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public interface ExtendedHirableEntity {
    @Nullable
    LivingEntity getOwner();

    boolean isFollowing();

    boolean isInventoryOpen();

    ItemStack checkFood();

    Inventory getCustomInventory();

    boolean isTame();

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

    int getHiredUnitId();

    UUID getOwnerUUID();

    void setMaxXp(float maxXp);

    void setHiredUnitHealth(float p_70606_1_);

    void setExpLvl(int lvl);

    void setCurrentXp(float currentXp);

    void setBaseHealth(int health);

    int getBaseHealth();

    void setStationary(boolean stationary);

    void updateFeetSlot(ItemStack stack);

    void updateHeadSlot(ItemStack stack);

    void updateChestSlot(ItemStack stack);

    void updateLegsSlot(ItemStack stack);

    void updateMainhandSlot(ItemStack stack);

    void updateOffhandSlot(ItemStack stack);

    boolean isEquipmentOpen();
    void setEquipmentOpen(boolean isOpen);

    void setInventoryOpen(boolean isOpen);

    ResourceLocation getProfileLocation();

    /**
     * Gets the {@Link ExtendedHiredUnitProfile} assoicated with this unit
     * @Nullable
     * */
    @Nullable
    default ExtendedHiredUnitProfile getUnitProfile() {
    	return ExtendedHiredUnitProfileManager.INSTANCE.get(getProfileLocation());
    }
}