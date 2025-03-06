package lotr.common.util;

import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.Random;

public class ExtendedHiredUnitHelper {
    public static void die(World world, DamageSource source, ExtendedHirableEntity unit) {
        if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && unit.getUnitOwner() instanceof ServerPlayerEntity) {
            unit.getUnitOwner().sendMessage(getCompanionDeathMessage(unit, source), unit.getOwnerUUID());
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
            if (killer instanceof ExtendedHirableEntity) {
                killerName = ((ExtendedHirableEntity) killer).getHiredUnitNameAndTitle();
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

            if (unit.getUnitOwner() != null) {
                unit.getUnitOwner().sendMessage(
                        new TranslationTextComponent("chat.lotrextended.hired_companion_levelup",
                                unit.getHiredUnitNameAndTitle(), unit.getExpLvl()),
                        unit.getOwnerUUID());
            }
        } else {
            unit.setCurrentXp(newExperience);
        }
    }

    public static boolean isEntityHiredUnit(Entity entity) {
        return entity instanceof ExtendedHirableEntity;
    }

    public static ExtendedHirableEntity getExtendedHirableEntity(Entity entity) {
        if (!isEntityHiredUnit(entity)) {
        	return null;
        }

        // attempt to cast to proper unit
        if (entity instanceof ExtendedHirableEntity) {
            return (ExtendedHirableEntity) entity;
        }

        return null;
    }

    /**
     * Update a unit's equipment (currently from inventory container)
     * @param companion The NPC unit to update
     * @param slot The slot type to update
     * @param item The new {@Link ItemStack} to be placed in the slot
     * */
    public static void updateEquipmentSlot(NPCEntity companion, EquipmentSlotType slot, ItemStack item) {
        if (companion instanceof ExtendedHirableEntity) {
            ExtendedHirableEntity hirable = (ExtendedHirableEntity) companion;
            hirable.updateSlot(item, slot);
        }
    }

    public static void dropCustomDeathLoot(NPCEntity companion, ExtendedHirableEntity hirable, Random random, Inventory internalUnitInventory, int looting, boolean player) {

        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            ItemStack itemstack = companion.getItemBySlot(slot);
            float dropChance = hirable.getHiredUnitEquipmentDropChance(slot);
            boolean isEquipedItem = !(itemstack.hasTag() && itemstack.getTag().getBoolean("IsBaseArmor"));
            boolean dropUndamaged = dropChance > 1.0F || isEquipedItem;
            if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack) && (isEquipedItem || (player || dropUndamaged)
                    && Math.max(random.nextFloat() - (float) looting * 0.01F, 0.0F) < dropChance)) {
                if (!dropUndamaged && itemstack.isDamageableItem()) {
                    itemstack.setDamageValue(itemstack.getMaxDamage() - random.nextInt(1 + random.nextInt(Math.max(itemstack.getMaxDamage() - 3, 1))));
                }

                companion.spawnAtLocation(itemstack);
                companion.setItemSlot(slot, ItemStack.EMPTY);
            }
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack itemstack = internalUnitInventory.getItem(i).copy();
            companion.spawnAtLocation(itemstack);
            internalUnitInventory.setItem(i, ItemStack.EMPTY);
        }
    }
}
