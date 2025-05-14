package lotr.common.util;

import lotr.common.util.representation.LOTREnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

public class LOTREnchantUtils {

    public static void enchant(ItemStack stack, LOTREnchantment enchantment, int pLevel) {
        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTREnchantments", 9)) {
            tag.put("LOTREnchantments", new ListNBT());
        }

        ListNBT listnbt = tag.getList("LOTREnchantments", 10);
        CompoundNBT compoundnbt = new CompoundNBT();
        compoundnbt.putString("id", enchantment.toString());
        compoundnbt.putShort("lvl", (byte) pLevel);
        listnbt.add(compoundnbt);
    }

    // TODO: This is a temp method for verifying it applied it
    public static String getEnchantList(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTREnchantments", 9)) {
            tag.put("LOTREnchantments", new ListNBT());
            return "";
        }

        ListNBT listnbt = tag.getList("LOTREnchantments", 10);
        String response = "";
        for (INBT entry : listnbt) {
            response += entry.getAsString() + " ";
        }
        return response.trim();
    }
}
