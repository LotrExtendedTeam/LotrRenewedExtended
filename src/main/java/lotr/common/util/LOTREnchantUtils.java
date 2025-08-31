package lotr.common.util;

import lotr.common.entity.npc.*;
import lotr.common.init.ExtendedItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedModifierRarity;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedModifierTypes;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedSmithScroll;

public class LOTREnchantUtils {

    public static void enchant(ItemStack stack, ExtendedModifiers enchantment) {
        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTREnchantments", 9)) {
            tag.put("LOTREnchantments", new ListNBT());
        }
        ListNBT listnbt = tag.getList("LOTREnchantments", 10);
        CompoundNBT compoundnbt = new CompoundNBT();
        compoundnbt.putString("key", enchantment.getNbtKey());
        listnbt.add(compoundnbt);
    }

    public static void setEnchantments(ItemStack stack, List<ExtendedModifiers> enchantments) {
    	clearEnchants(stack);
        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.contains("LOTREnchantments", 9)) {
            tag.put("LOTREnchantments", new ListNBT());
        }
        ListNBT listnbt = tag.getList("LOTREnchantments", 10);
        for(ExtendedModifiers modifier : enchantments) {
        	CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putString("key", modifier.getNbtKey());
            listnbt.add(compoundnbt);
        }
    }

    public static void removeEnchant(ItemStack stack, ExtendedModifiers enchantment) {
        if (!getEnchantList(stack).contains(enchantment)) {
            return;
        }

        ListNBT listnbt = stack.getTag().getList("LOTREnchantments", 10); //Null tag already validated by getEnchantList
        List<CompoundNBT> entriesToRemove = new ArrayList<>();
        for (INBT nbt : listnbt) {
            if (!(nbt instanceof CompoundNBT)) continue;

            CompoundNBT entry = (CompoundNBT) nbt;
            if (entry.getString("key").equals(enchantment.getNbtKey())) entriesToRemove.add(entry);
        }

        listnbt.removeAll(entriesToRemove);
        if(listnbt.isEmpty()) {
        	stack.getTag().remove("LOTREnchantments");
        	 if(stack.getTag().isEmpty()) {
             	stack.setTag(null);
             }
        }
    }

    public static boolean clearEnchants(ItemStack stack) {
        if (getEnchantList(stack).isEmpty()) {
            return false;
        }
        CompoundNBT tag = stack.getTag(); //Null tag already validated by getEnchantList
        tag.remove("LOTREnchantments");
        if(tag.isEmpty()) {
        	stack.setTag(null);
        }
        return true;
    }

    public static void incrementBaneProgress(ItemStack stack, Entity entity, PlayerEntity player) {
        // exit the method if the item already has a bane
        if (containsBane(getEnchantList(stack))) {
            return;
        }

        // exit the method if there is applicable bane for that entity
        ExtendedModifiers enchantment = getBaneByEntity(entity);
        // Only allow earned banes to be progressed
        if (enchantment == null || enchantment.getRarity()!=ExtendedModifierRarity.EARNED) {
            return;
        }

        CompoundNBT tag = stack.getOrCreateTag(); // Use here as we want to make a tag
        if (!tag.contains("LOTRBaneProgress", 9)) {
            tag.put("LOTRBaneProgress", new ListNBT());
        }

        ListNBT listnbt = tag.getList("LOTRBaneProgress", 10); // Already filters for non CompoundNBT
        int updatedKillCount = 0;
        boolean containsProgress = false;
        for (INBT nbt : listnbt) {
            CompoundNBT entry = (CompoundNBT) nbt;
            if (entry.getString("key").equals(enchantment.getNbtKey())) {
                containsProgress = true;
                updatedKillCount = entry.getInt("kills") + 1;
                entry.putInt("kills", updatedKillCount);
            }
        }

        if (!containsProgress) {
            updatedKillCount = 1;
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putString("key", enchantment.getNbtKey());
            compoundnbt.putInt("kills", updatedKillCount);
            listnbt.add(compoundnbt);
        }

        if (updatedKillCount >= 100) {
            // announce in chat that the item has acquired the bane
            if (player != null) {
                player.sendMessage(new TranslationTextComponent("enchant.lotrextended.bane.earn", stack.getDisplayName(), new TranslationTextComponent("enchant.lotrextended."+ enchantment.getLocalKey() + ".race")), Util.NIL_UUID);
                for (ServerPlayerEntity cPlayer : player.getServer().getPlayerList().getPlayers()) {
                    if (cPlayer.getUUID() == player.getUUID()) continue;
                    cPlayer.sendMessage(new TranslationTextComponent("enchant.lotrextended.bane.earn_name", player.getName(), stack.getDisplayName(), new TranslationTextComponent("enchant.lotrextended."+ enchantment.getLocalKey() + ".race")), Util.NIL_UUID);
                }
            }

            LOTREnchantUtils.enchant(stack, enchantment);
            tag.remove("LOTRBaneProgress");
            if(tag.isEmpty()) {
            	stack.setTag(null);
            }
        }
    }

    public static String getBaneData(ItemStack stack) {
        StringBuilder result = new StringBuilder();

        if (!stack.hasTag() || !stack.getTag().contains("LOTRBaneProgress", 9)) {
            return "";
        }

        ListNBT listnbt = stack.getTag().getList("LOTRBaneProgress", 10); // Already filters for non CompoundNBT
        for (INBT nbt : listnbt) {
            CompoundNBT entry = (CompoundNBT) nbt;
            result.append(entry.getString("key")).append(": ").append(entry.getInt("kills") + ", ");
        }

        if (result.length() > 2) {
            return result.toString().substring(0, result.length() - 2);
        } else {
            return result.toString();
        }
    }

    public static boolean containsBane(List<ExtendedModifiers> enchantments) {
        return enchantments.contains(ExtendedModifiers.DWARF_BANE) ||
                enchantments.contains(ExtendedModifiers.ELF_BANE) ||
                enchantments.contains(ExtendedModifiers.ORC_BANE) ||
                enchantments.contains(ExtendedModifiers.WIGHT_BANE) ||
                enchantments.contains(ExtendedModifiers.WARG_BANE);
    }

    public static List<ExtendedModifiers> getEnchantList(ItemStack stack) {
    	if(stack.getItem() == ExtendedItems.MODIFIER_SCROLL.get()){
    		List<ExtendedModifiers> result = new ArrayList<>();
    		if(!ExtendedSmithScroll.getModifier(stack).isUnknown()) {
    			result.add(ExtendedSmithScroll.getModifier(stack));
    		}
    		return result;
    	}else {
    		if (!stack.hasTag() || !stack.getTag().contains("LOTREnchantments", 9)) {
    			return new ArrayList<>();
    		}
    		List<ExtendedModifiers> result = new ArrayList<>();
    		ListNBT listnbt = stack.getTag().getList("LOTREnchantments", 10);
    		for (INBT nbt : listnbt) {
    			if (!(nbt instanceof CompoundNBT)) continue;
    			CompoundNBT entry = (CompoundNBT) nbt;
    			String key = entry.getString("key");
    			ExtendedModifiers enchant = null; //TODO simplify logic
    			if (ExtendedModifiers.MODIFIER_MAP.containsKey(key)) {
    				enchant = ExtendedModifiers.MODIFIER_MAP.get(key);
    			}

    			if (enchant != null) {
    				result.add(enchant);
    			}
    		}
    		return result;
    	}
    }

    public static Map<ExtendedModifierTypes, Integer> getEnchantListByLevel(ItemStack stack) {
    	List<ExtendedModifiers> mergableModifiers = LOTREnchantUtils.getEnchantList(stack);
    	Map<ExtendedModifierTypes, Integer> map = new LinkedHashMap<>();
    	for(ExtendedModifiers modifier : mergableModifiers) {
    		map.put(modifier.getModifierType(), modifier.getModifierLevel());
    	}
    	return map;
    }

    public static boolean isBaneEffectiveAgainst(ExtendedModifiers enchantment, Entity entity) {
        return getBaneByEntity(entity) == enchantment;
    }

    public static ExtendedModifiers getBaneByEntity(Entity entity) {
        ExtendedModifiers bane = null;
        if (entity instanceof OrcEntity) {
            bane = ExtendedModifiers.ORC_BANE;
        } else if (entity instanceof ElfEntity) {
            bane = ExtendedModifiers.ELF_BANE;
        } else if (entity instanceof DwarfEntity) {
            bane = ExtendedModifiers.DWARF_BANE;
        } else if (entity instanceof WargEntity) {
            bane = ExtendedModifiers.WARG_BANE;
        } else if (entity instanceof ExtendedBarrowWightEntity) {
            bane = ExtendedModifiers.WIGHT_BANE;
        }
        return bane;
    }
}
