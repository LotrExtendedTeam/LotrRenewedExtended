package lotr.common.util;

import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.ExtendedHiredBreeGuard;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ExtendedHiredUnitHelper {

    public static final ItemStack BREE_GUARD_HEAD = new ItemStack(Items.IRON_HELMET).setHoverName(new TranslationTextComponent("item.lotrextended.base_gear.helmet").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
    public static final ItemStack BREE_GUARD_CHEST = new ItemStack(Items.LEATHER_CHESTPLATE).setHoverName(new TranslationTextComponent("item.lotrextended.base_gear.chestplate").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
    public static final ItemStack BREE_GUARD_LEGS = new ItemStack(Items.CHAINMAIL_LEGGINGS).setHoverName(new TranslationTextComponent("item.lotrextended.base_gear.leggings").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
    public static final ItemStack BREE_GUARD_FEET = new ItemStack(Items.CHAINMAIL_BOOTS).setHoverName(new TranslationTextComponent("item.lotrextended.base_gear.boots").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
    public static final ItemStack BREE_GUARD_MAINHAND  = new ItemStack(LOTRItems.IRON_SPEAR.get()).setHoverName(new TranslationTextComponent("item.lotrextended.base_gear.weapon").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
    public static final ItemStack BREE_GUARD_OFFHAND = new ItemStack(Items.SHIELD).setHoverName(new TranslationTextComponent("item.lotrextended.base_gear.shield").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));

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

        TranslationTextComponent companionName = new TranslationTextComponent("chat.lotrextended.hired_companion_prefix", unit.getHiredUnitNameAndTitle());

        ITextComponent killerName = new StringTextComponent("");
        if (killer != null) {
            killerName = killer.getDisplayName();
            if (killer instanceof ExtendedHiredBreeGuard) {
                killerName = ((ExtendedHiredBreeGuard) killer).getHiredUnitNameAndTitle();
            }
        }


        return killer != null ? new TranslationTextComponent(s, companionName, killerName) : new TranslationTextComponent(s, companionName);
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
                        new TranslationTextComponent("chat.lotrextended.hired_companion_levelup",
                                unit.getHiredUnitNameAndTitle(), unit.getExpLvl()),
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

    public static boolean updateEquipmentSlot(NPCEntity companion, EquipmentSlotType slot, ItemStack item) {
        boolean result = false;

        if (companion instanceof ExtendedHirableEntity) {
            ExtendedHirableEntity hirable = (ExtendedHirableEntity) companion;

            if (slot == EquipmentSlotType.FEET) {
                hirable.updateFeetSlot(item);
                result = true;
            } else if (slot == EquipmentSlotType.HEAD) {
                hirable.updateHeadSlot(item);
                result = true;
            } else if (slot == EquipmentSlotType.CHEST) {
                hirable.updateChestSlot(item);
                result = true;
            } else if (slot == EquipmentSlotType.LEGS) {
                hirable.updateLegsSlot(item);
                result = true;
            } else if (slot == EquipmentSlotType.MAINHAND) {
                hirable.updateMainhandSlot(item);
                result = true;
            } else if (slot == EquipmentSlotType.OFFHAND) {
                hirable.updateOffhandSlot(item);
                result = true;
            }
        }

        return result;
    }

    public static ItemStack[] getBaseGear(Entity entity) {
        ItemStack[] baseGear = new ItemStack[6];

        if (entity instanceof ExtendedHiredBreeGuard) {
            baseGear[0] = BREE_GUARD_HEAD;
            baseGear[1] = BREE_GUARD_CHEST;
            baseGear[2] = BREE_GUARD_LEGS;
            baseGear[3] = BREE_GUARD_FEET;
            baseGear[4] = BREE_GUARD_MAINHAND;
            baseGear[5] = BREE_GUARD_OFFHAND;
        } else {
            baseGear[0] = ItemStack.EMPTY;
            baseGear[1] = ItemStack.EMPTY;
            baseGear[2] = ItemStack.EMPTY;
            baseGear[3] = ItemStack.EMPTY;
            baseGear[4] = ItemStack.EMPTY;
            baseGear[5] = ItemStack.EMPTY;
        }

        return baseGear;
    }

}
