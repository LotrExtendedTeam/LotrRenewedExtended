package lotr.common.util;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.ExtendedINPCEntity;

import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedEquipmentSlotType;

public class ExtendedHiredUnitHelper {
    public static void die(ExtendedNpcHiredUnitInfoHolder unitInfo, DamageSource source) {
        if (!unitInfo.getThisEntity().level.isClientSide && unitInfo.getThisEntity().level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && unitInfo.getUnitOwner() instanceof ServerPlayerEntity) {
        	if (((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) unitInfo.getUnitOwner())).getExtendedMiscData().shouldShowHiredUnitDeathMessages()){
        		boolean fancy = ((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) unitInfo.getUnitOwner())).getExtendedMiscData().doHighContrastMessages();
        		unitInfo.getUnitOwner().sendMessage(getCompanionDeathMessage(unitInfo, source, fancy), unitInfo.getOwnerUUID());
        	}
        }
    }

    private static TranslationTextComponent getCompanionDeathMessage(ExtendedNpcHiredUnitInfoHolder unitInfo, DamageSource source, boolean fancy) {
        LivingEntity killerEntity = unitInfo.getThisEntity().getKillCredit();

        String s = "death.attack." + source.msgId;

        TextComponent unitName = unitInfo.getHiredUnitNameAndTitle();
        if(fancy) {
        	unitName.withStyle(TextFormatting.AQUA);
        }
        TranslationTextComponent companionName = new TranslationTextComponent("chat.lotrextended.hired_unit.death", unitName);
        if(fancy) {
        	companionName.withStyle(TextFormatting.YELLOW);
        }

        IFormattableTextComponent killerName = new StringTextComponent("");
        if (killerEntity != null) {
        	if (killerEntity instanceof NPCEntity && ((ExtendedINPCEntity)killerEntity).getUnitInfo().isNpcActiveDuty()) {
                killerName = ((ExtendedINPCEntity)killerEntity).getUnitInfo().getHiredUnitNameAndTitle();
            }
        	else {
        		if(killerEntity.getDisplayName() instanceof IFormattableTextComponent) {
            		killerName = (IFormattableTextComponent) killerEntity.getDisplayName();
            	}else {
            		killerName = new StringTextComponent(killerEntity.getDisplayName().getString());
            	}
        	}
        	if(fancy) {
            	killerName.withStyle(TextFormatting.RED);
            }
        }
        TranslationTextComponent output = killerEntity != null ? new TranslationTextComponent(s, companionName, killerName) : new TranslationTextComponent(s, companionName);
        if(fancy) {
        	output.withStyle(TextFormatting.YELLOW);
        }
        return output;
    }

    public static void giveExperiencePoints(ExtendedNpcHiredUnitInfoHolder unitHolder, float points) {
        float newExperience = unitHolder.getCurrentXp() + points;
        if (newExperience >= unitHolder.getMaxXp()) {
        	unitHolder.setUnitLevel(unitHolder.getUnitLevel() + 1);
            float difference = newExperience - unitHolder.getMaxXp();
            unitHolder.setCurrentXp(difference);
            unitHolder.setMaxXp(unitHolder.getMaxXp() + 2);
            unitHolder.setHiredUnitHealth(unitHolder.getHiredUnitHealth() + 2);
            unitHolder.setBaseHealth(unitHolder.getBaseHealth() + 2);

            if (unitHolder.getUnitOwner() != null) {
            	boolean fancy = ((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) unitHolder.getUnitOwner())).getExtendedMiscData().doHighContrastMessages();
            	TextComponent unitName = unitHolder.getHiredUnitNameAndTitle();
            	TextComponent newlevel = new StringTextComponent(Integer.toString(unitHolder.getUnitLevel()));
            	if(fancy) {
            		unitName.withStyle(TextFormatting.AQUA);
            		newlevel.withStyle(TextFormatting.LIGHT_PURPLE);
                }
            	TranslationTextComponent output = new TranslationTextComponent("chat.lotrextended.hired_unit.levelup.combat", unitName, newlevel);
                if(fancy) {
                	output.withStyle(TextFormatting.YELLOW);
                }
                unitHolder.getUnitOwner().sendMessage(output, unitHolder.getOwnerUUID());
            }
        } else {
        	unitHolder.setCurrentXp(newExperience);
        }
        unitHolder.markDirty();
    }

    /**
     * Update a unit's equipment (currently from inventory container)
     * @param companion The NPC unit to update
     * @param slot The slot type to update
     * @param item The new ItemStack to be placed in the slot
     * */
    public static void updateEquipmentSlot(NPCEntity companion, ExtendedEquipmentSlotType slot, ItemStack item) {
        if(!companion.level.isClientSide() && ((ExtendedINPCEntity)companion).getUnitInfo().isNpcActiveDuty()) {
        	((ExtendedINPCEntity)companion).getUnitInfo().updateSlot(item, slot);
        }
    }

    public static void dropCustomDeathLoot(ExtendedNpcHiredUnitInfoHolder unitInfo, Random random, int looting) {
    	// Disabled due to NPCEntity using new logic we don't want for unit drops
        //super.dropCustomDeathLoot(damageSource, looting, player);
    	// Only try dropping player equipment and general inventory space
        for (int i = 0; i < 19; ++i) {
        	if(!unitInfo.getInternalUnitInventory().getItem(i).isEmpty()) {
        		ItemStack itemstack = unitInfo.getInternalUnitInventory().getItem(i).copy();
        		boolean isEquipedItem = !(itemstack.hasTag() && itemstack.getTag().getBoolean("IsBaseArmor"));
        		if(isEquipedItem && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
        			unitInfo.getThisEntity().spawnAtLocation(itemstack);
        			unitInfo.getInternalUnitInventory().setItem(i, ItemStack.EMPTY);
        		}
        	}
        }
    }
}
