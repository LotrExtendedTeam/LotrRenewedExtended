package lotr.common.entity.npc;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.UUID;

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

    void giveExperiencePoints(int points);

    int getMobKills();

    ITextComponent getHiredUnitName();

    boolean isPatrolling();
    float getHiredUnitMaxHealth();

    float getHiredUnitHealth();

    int getExpLvl();

    int getMaxXp();

    int getCurrentXp();

    boolean isAlert();

    int getHiredUnitId();

    UUID getOwnerUUID();

    void setMaxXp(int maxXp);

    void setHiredUnitHealth(float p_70606_1_);

    void setExpLvl(int lvl);

    void setCurrentXp(int currentXp);

    void setBaseHealth(int health);

    int getBaseHealth();

    void setStationary(boolean stationary);

}
