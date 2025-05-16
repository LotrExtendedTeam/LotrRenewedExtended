package lotr.common.util;

import lotr.common.entity.npc.*;
import lotr.common.util.representation.LOTREnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class LOTREnchantUtils {

    public static void enchant(ItemStack stack, LOTREnchantment enchantment, int pLevel) {
        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTREnchantments", 9)) {
            tag.put("LOTREnchantments", new ListNBT());
        }

        // TODO: When other LOTREnchants are introduced support them to have multiple names
        //  currently this overrides names from any previous enchants
        // rename item if not already renamed
        if (!stack.hasCustomHoverName()) {
            ITextComponent baseName = stack.getItem().getName(stack);
            ITextComponent newName = new TranslationTextComponent("enchant.lotrextended." + enchantment.getKey())
                    .append(" ").withStyle(style -> style.withItalic(false))
                    .append(baseName.copy().withStyle(style -> style.withItalic(false)));
            stack.setHoverName(newName);
        }

        ListNBT listnbt = tag.getList("LOTREnchantments", 10);
        CompoundNBT compoundnbt = new CompoundNBT();
        compoundnbt.putString("key", enchantment.getKey());
        compoundnbt.putShort("lvl", (byte) pLevel);
        listnbt.add(compoundnbt);
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
        if (enchantment.equals(LOTREnchantment.ORC_BANE)) {
            return entity instanceof OrcEntity;
        } else if(enchantment.equals(LOTREnchantment.ELF_BANE)) {
            return entity instanceof ElfEntity;
        } else if(enchantment.equals(LOTREnchantment.DWARF_BANE)) {
            return entity instanceof DwarfEntity;
        } else if(enchantment.equals(LOTREnchantment.WARG_BANE)) {
            return entity instanceof WargEntity;
        } else if(enchantment.equals(LOTREnchantment.WIGHT_BANE)) {
            return entity instanceof ExtendedBarrowWightEntity;
        } else {
            return false;
        }
    }

}
