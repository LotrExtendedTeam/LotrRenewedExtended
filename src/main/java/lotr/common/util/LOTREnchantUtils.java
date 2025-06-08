package lotr.common.util;

import lotr.common.entity.npc.*;
import lotr.common.util.representation.LOTREnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class LOTREnchantUtils {

    public static void enchant(ItemStack stack, LOTREnchantment enchantment, int pLevel) {
       enchant(stack, enchantment, pLevel, true);
    }

    public static void enchant(ItemStack stack, LOTREnchantment enchantment, int pLevel, boolean renameItem) {
        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTREnchantments", 9)) {
            tag.put("LOTREnchantments", new ListNBT());
        }
        ListNBT listnbt = tag.getList("LOTREnchantments", 10);
        CompoundNBT compoundnbt = new CompoundNBT();
        compoundnbt.putString("key", enchantment.getKey());
        compoundnbt.putShort("lvl", (byte) pLevel);
        listnbt.add(compoundnbt);
    }

    public static void removeEnchant(ItemStack stack, LOTREnchantment enchantment) {
        if (!getEnchantList(stack).contains(enchantment)) {
            return;
        }

        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTREnchantments", 9)) {
            tag.put("LOTREnchantments", new ListNBT());
        }
        ListNBT listnbt = tag.getList("LOTREnchantments", 10);
        List<CompoundNBT> entriesToRemove = new ArrayList<>();
        for (INBT nbt : listnbt) {
            if (!(nbt instanceof CompoundNBT)) continue;

            CompoundNBT entry = (CompoundNBT) nbt;
            if (entry.getString("key").equals(enchantment.getKey())) entriesToRemove.add(entry);
        }

        listnbt.removeAll(entriesToRemove);
    }

    public static boolean clearEnchants(ItemStack stack) {
        if (getEnchantList(stack).isEmpty()) {
            return false;
        }
        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTREnchantments", 9)) {
            tag.put("LOTREnchantments", new ListNBT());
        }

        tag.remove("LOTREnchantments");
        tag.remove("LOTREnchantsNamedOnAnvil");
        return true;
    }

    public static void incrementBaneProgress(ItemStack stack, Entity entity, PlayerEntity player) {
        // exit the method if the item already has a bane
        if (containsBane(getEnchantList(stack))) {
            return;
        }

        // exit the method if there is applicable bane for that entity
        LOTREnchantment enchantment = getBaneByEntity(entity);
        if (enchantment == null) {
            return;
        }

        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTRBaneProgress", 9)) {
            tag.put("LOTRBaneProgress", new ListNBT());
        }

        ListNBT listnbt = tag.getList("LOTRBaneProgress", 10);
        int updatedKillCount = 0;
        boolean containsProgress = false;
        for (INBT nbt : listnbt) {
            if (!(nbt instanceof CompoundNBT)) continue;

            CompoundNBT entry = (CompoundNBT) nbt;
            if (entry.getString("key").equals(enchantment.getKey())) {
                containsProgress = true;
                updatedKillCount = entry.getInt("kills") + 1;
                entry.putInt("kills", updatedKillCount);
            }
        }

        if (!containsProgress) {
            updatedKillCount = 1;
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putString("key", enchantment.getKey());
            compoundnbt.putInt("kills", updatedKillCount);
            listnbt.add(compoundnbt);
        }

        if (updatedKillCount >= 100) {
            // announce in chat that the item has acquired the bane
            if (player != null) {
                player.sendMessage(new TranslationTextComponent("enchant.lotrextended." + enchantment.getKey().toLowerCase() + ".earn", stack.getDisplayName()), Util.NIL_UUID);
                for (ServerPlayerEntity cPlayer : player.getServer().getPlayerList().getPlayers()) {
                    if (cPlayer.getUUID() == player.getUUID()) continue;
                    cPlayer.sendMessage(new TranslationTextComponent("enchant.lotrextended." + enchantment.getKey().toLowerCase() + ".earn_name", player.getName(), stack.getDisplayName()), Util.NIL_UUID);
                }
            }

            LOTREnchantUtils.enchant(stack, enchantment, 1);
            tag.remove("LOTRBaneProgress");
        }
    }

    public static String getBaneData(ItemStack stack) {
        StringBuilder result = new StringBuilder();

        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTRBaneProgress", 9)) {
            return "";
        }

        ListNBT listnbt = tag.getList("LOTRBaneProgress", 10);
        for (INBT nbt : listnbt) {
            if (!(nbt instanceof CompoundNBT)) continue;

            CompoundNBT entry = (CompoundNBT) nbt;
            result.append(entry.getString("key")).append(": ").append(entry.getInt("kills") + ", ");
        }

        if (result.length() > 2) {
            return result.toString().substring(0, result.length() - 2);
        } else {
            return result.toString();
        }
    }

    public static boolean containsBane(List<LOTREnchantment> enchantments) {
        return enchantments.contains(LOTREnchantment.DWARF_BANE) ||
                enchantments.contains(LOTREnchantment.ELF_BANE) ||
                enchantments.contains(LOTREnchantment.ORC_BANE) ||
                enchantments.contains(LOTREnchantment.WIGHT_BANE) ||
                enchantments.contains(LOTREnchantment.WARG_BANE);
    }

    public static List<LOTREnchantment> getEnchantList(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTREnchantments", 9)) {
            tag.put("LOTREnchantments", new ListNBT());
            return new ArrayList<>();
        }

        ListNBT listnbt = tag.getList("LOTREnchantments", 10);
        List<LOTREnchantment> result = new ArrayList<>();
        for (INBT nbt : listnbt) {
            if (!(nbt instanceof CompoundNBT)) continue;
            CompoundNBT entry = (CompoundNBT) nbt;
            String key = entry.getString("key");
            LOTREnchantment enchant = null;
            if (LOTREnchantment.ENCHANT_MAP.containsKey(key)) {
                enchant = LOTREnchantment.ENCHANT_MAP.get(key);
            }

            if (enchant != null) {
                result.add(enchant);
            }
        }
        return result;
    }

    public static boolean isBaneEffectiveAgainst(LOTREnchantment enchantment, Entity entity) {
        return getBaneByEntity(entity) == enchantment;
    }

    public static LOTREnchantment getBaneByEntity(Entity entity) {
        LOTREnchantment bane = null;
        if (entity instanceof OrcEntity) {
            bane = LOTREnchantment.ORC_BANE;
        } else if (entity instanceof ElfEntity) {
            bane = LOTREnchantment.ELF_BANE;
        } else if (entity instanceof DwarfEntity) {
            bane = LOTREnchantment.DWARF_BANE;
        } else if (entity instanceof WargEntity) {
            bane = LOTREnchantment.WARG_BANE;
        } else if (entity instanceof WitchEntity) {
            bane = LOTREnchantment.WIGHT_BANE;
        }
        return bane;
    }

}
