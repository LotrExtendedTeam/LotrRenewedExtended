package lotr.common.util;

import lotr.common.util.representation.LOTREnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.List;

public class LOTREnchantUtils {

    public static void enchant(ItemStack stack, LOTREnchantment enchantment, int pLevel) {
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

}
