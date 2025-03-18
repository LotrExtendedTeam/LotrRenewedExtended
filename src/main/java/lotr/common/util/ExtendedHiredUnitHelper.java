package lotr.common.util;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEquipmentSlotType;

public class ExtendedHiredUnitHelper {
    public static void die(World world, DamageSource source, ExtendedHirableEntity unit) {
        if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && unit.getUnitOwner() instanceof ServerPlayerEntity) {
        	if (((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) unit.getUnitOwner())).getExtendedMiscData().shouldShowHiredUnitDeathMessages()){
        		boolean fancy = ((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) unit.getUnitOwner())).getExtendedMiscData().doHighContrastMessages();
        		unit.getUnitOwner().sendMessage(getCompanionDeathMessage(unit, source, fancy), unit.getOwnerUUID());
        	}
        }
    }

    private static TranslationTextComponent getCompanionDeathMessage(ExtendedHirableEntity unit, DamageSource source, boolean fancy) {
        LivingEntity killer = null;
        if (unit instanceof LivingEntity) {
            killer = ((LivingEntity) unit).getKillCredit();
        }
        String s = "death.attack." + source.msgId;

        TextComponent unitName = unit.getHiredUnitNameAndTitle();
        if(fancy) {
        	unitName.withStyle(TextFormatting.AQUA);
        }
        TranslationTextComponent companionName = new TranslationTextComponent("chat.lotrextended.hired_companion_prefix", unitName);
        if(fancy) {
        	companionName.withStyle(TextFormatting.YELLOW);
        }

        IFormattableTextComponent killerName = new StringTextComponent("");
        if (killer != null) {
        	if (killer instanceof ExtendedHirableEntity) {
                killerName = ((ExtendedHirableEntity) killer).getHiredUnitNameAndTitle();
            }
        	else {
        		if(killer.getDisplayName() instanceof IFormattableTextComponent) {
            		killerName = (IFormattableTextComponent) killer.getDisplayName();
            	}else {
            		killerName = new StringTextComponent(killer.getDisplayName().getString());
            	}
        	}
        	if(fancy) {
            	killerName.withStyle(TextFormatting.RED);
            }
        }
        TranslationTextComponent output = killer != null ? new TranslationTextComponent(s, companionName, killerName) : new TranslationTextComponent(s, companionName);
        if(fancy) {
        	output.withStyle(TextFormatting.YELLOW);
        }
        return output;
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
            	boolean fancy = ((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) unit.getUnitOwner())).getExtendedMiscData().doHighContrastMessages();
            	TextComponent unitName = unit.getHiredUnitNameAndTitle();
            	TextComponent newlevel = new StringTextComponent(Integer.toString(unit.getExpLvl()));
            	if(fancy) {
            		unitName.withStyle(TextFormatting.AQUA);
            		newlevel.withStyle(TextFormatting.LIGHT_PURPLE);
                }
            	TranslationTextComponent output = new TranslationTextComponent("chat.lotrextended.hired_companion_levelup", unitName, newlevel);
                if(fancy) {
                	output.withStyle(TextFormatting.YELLOW);
                }
                unit.getUnitOwner().sendMessage(output, unit.getOwnerUUID());
            }
        } else {
            unit.setCurrentXp(newExperience);
        }
    }

    /**
     * Checks if an entity is a hired unit, also handles checks where the entity is null
     * @param entity The Entity to check
     * @return True if the entity is instance of ExtendedHirableEntity
     * */
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
     * @param item The new ItemStack to be placed in the slot
     * */
    public static void updateEquipmentSlot(NPCEntity companion, ExtendedEquipmentSlotType slot, ItemStack item) {
        if (companion instanceof ExtendedHirableEntity) {
            ExtendedHirableEntity hirable = (ExtendedHirableEntity) companion;
            hirable.updateSlot(item, slot);
        }
    }

    public static void dropCustomDeathLoot(NPCEntity companion, ExtendedHirableEntity hirable, Random random, Inventory internalUnitInventory, int looting, boolean player) {
    	// Disabled due to NPCEntity using new logic we don't want for unit drops
        //super.dropCustomDeathLoot(damageSource, looting, player);
    	// Only try dropping player equipment and general inventory space
        for (int i = 0; i < 19; ++i) {
        	if(!internalUnitInventory.getItem(i).isEmpty()) {
        		ItemStack itemstack = internalUnitInventory.getItem(i).copy();
        		boolean isEquipedItem = !(itemstack.hasTag() && itemstack.getTag().getBoolean("IsBaseArmor"));
        		if(isEquipedItem && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
        			companion.spawnAtLocation(itemstack);
                    internalUnitInventory.setItem(i, ItemStack.EMPTY);
        		}
        	}
        }
    }
}
