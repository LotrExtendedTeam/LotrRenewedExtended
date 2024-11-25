package lotr.common.util;

import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.ExtendedHiredBreeGuard;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ExtendedHiredUnitHelper {

    public static final ItemStack BREE_GUARD_HEAD = new ItemStack(Items.IRON_HELMET).setHoverName(new StringTextComponent("Base Helmet").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
    public static final ItemStack BREE_GUARD_CHEST = new ItemStack(Items.LEATHER_CHESTPLATE).setHoverName(new StringTextComponent("Base Chestplate").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
    public static final ItemStack BREE_GUARD_LEGS = new ItemStack(Items.CHAINMAIL_LEGGINGS).setHoverName(new StringTextComponent("Base Leggings").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
    public static final ItemStack BREE_GUARD_FEET = new ItemStack(Items.CHAINMAIL_BOOTS).setHoverName(new StringTextComponent("Base Boots").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
    public static final ItemStack BREE_GUARD_MAINHAND  = new ItemStack(LOTRItems.IRON_SPEAR.get()).setHoverName(new StringTextComponent("Base Weapon").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
    public static final ItemStack BREE_GUARD_OFFHAND = new ItemStack(Items.SHIELD).setHoverName(new StringTextComponent("Base Shield").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));

    public static void die(World world, DamageSource source, ExtendedHirableEntity unit) {
        if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && unit.getOwner() instanceof ServerPlayerEntity) {
            unit.getOwner().sendMessage(getCompanionDeathMessage(unit, source), unit.getOwnerUUID());
        }
    }

    private static TranslationTextComponent getCompanionDeathMessage(ExtendedHirableEntity unit, DamageSource source) {
        LivingEntity killer = null;
        if (unit instanceof LivingEntity) {
            killer = ((LivingEntity) unit).getKillCredit();
        }
        String s = "death.attack." + source.msgId;

        // TODO: This translation text needs to be brought in and updated
        TranslationTextComponent companionName = new TranslationTextComponent("notification.hired_companion_prefix", unit.getHiredUnitName());

        return killer != null ? new TranslationTextComponent(s, companionName, killer.getDisplayName()) : new TranslationTextComponent(s, companionName);
    }

    public static void giveExperiencePoints(ExtendedHirableEntity unit, float points) {
        float newExperience = unit.getCurrentXp() + points;
        if (newExperience >= unit.getMaxXp()) {
            unit.setExpLvl(unit.getExpLvl() + 1);
            float difference = newExperience - unit.getMaxXp();
            unit.setCurrentXp(difference);
            unit.setMaxXp(unit.getMaxXp() + 2);
            unit.setHiredUnitHealth(unit.getHiredUnitHealth() + 2);
            unit.setBaseHealth(unit.getBaseHealth() + 2);

            if (unit.getOwner() != null) {
                unit.getOwner().sendMessage(
                        new StringTextComponent("Your hired companion ").
                                append(unit.getHiredUnitName()).
                                append(" has reached level " + unit.getExpLvl() + "!"),
                        unit.getOwnerUUID());
            }
        } else {
            unit.setCurrentXp(newExperience);
        }
    }

    public static boolean isEntityHiredUnit(Entity entity) {
        return entity instanceof ExtendedHiredBreeGuard;
    }

    public static ExtendedHirableEntity getExtendedHirableEntity(Entity entity) {
        if (!isEntityHiredUnit(entity)) return null;

        // attempt to cast to proper unit
        if (entity instanceof ExtendedHirableEntity) {
            return (ExtendedHirableEntity) entity;
        }

        return null;
    }

}
