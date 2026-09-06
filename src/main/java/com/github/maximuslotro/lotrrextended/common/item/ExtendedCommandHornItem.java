package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedHiredFollowGoal;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitHornTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.utils.client.ExtendedClientsideSeparatedScreenHandler;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ExtendedCommandHornItem extends Item {
	public static final String HORNTYPE = "usage_type";
	public static final String SQUAD = "squad";

	public ExtendedCommandHornItem(Properties properties) {
		super(properties);
	}

	@Override
	public UseAction getUseAnimation(ItemStack p_77661_1_) {
		return UseAction.BOW;
	}

	@Override
	public int getUseDuration(ItemStack p_77626_1_) {
		return 40;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, World world, LivingEntity entityplayer) {
		ExtendedUnitHornTypes type = getType(itemstack);
		if(type.isUnset()) {
			return itemstack;
		}
		if(!world.isClientSide()) {
			AxisAlignedBB area = new AxisAlignedBB(entityplayer.blockPosition()).inflate(64);
			if(type.isSummon()) {
				List<NPCEntity> teleportableUnits = world.getEntitiesOfClass(NPCEntity.class, area, unit -> isValidUnit(unit, itemstack, entityplayer, ExtendedUnitOrders.FOLLOW_PLAYER));
				for(NPCEntity unit : teleportableUnits) {
					ExtendedHiredFollowGoal.teleportToOwner(unit, entityplayer, true);
				}
			}
			if(type.isHalt()) {
				List<NPCEntity> followingUnits = world.getEntitiesOfClass(NPCEntity.class, area, unit -> isValidUnit(unit, itemstack, entityplayer, ExtendedUnitOrders.FOLLOW_PLAYER));
				for(NPCEntity unit : followingUnits) {
					((ExtendedINPCEntity)unit).getUnitInfo().setOrders(ExtendedUnitOrders.STATIONARY);
				}
				setType(itemstack, ExtendedUnitHornTypes.READY);
			}
			if(type.isReady()) {
				List<NPCEntity> stationaryUnits = world.getEntitiesOfClass(NPCEntity.class, area, unit -> isValidUnit(unit, itemstack, entityplayer, ExtendedUnitOrders.STATIONARY));
				for(NPCEntity unit : stationaryUnits) {
					((ExtendedINPCEntity)unit).getUnitInfo().setOrders(ExtendedUnitOrders.FOLLOW_PLAYER);
				}
				setType(itemstack, ExtendedUnitHornTypes.HALT);
			}
			world.playSound(null, entityplayer.blockPosition(), ExtendedSounds.HORN_BLOW.get(), SoundCategory.PLAYERS, 4.0F, 1.0F);
		}else {
			if(type.isHalt()) {
				setType(itemstack, ExtendedUnitHornTypes.READY);
			}
			if(type.isReady()) {
				setType(itemstack, ExtendedUnitHornTypes.HALT);
			}
		}
		return itemstack;
	}

	private boolean isValidUnit(NPCEntity entity, ItemStack itemstack, LivingEntity player, ExtendedUnitOrders requiredOrders) {
		if(entity instanceof ExtendedINPCEntity) {
			ExtendedNpcHiredUnitInfoHolder unitInfo = ((ExtendedINPCEntity) entity).getUnitInfo();
			return unitInfo.isNpcActiveDuty() && unitInfo.getOwnerUUID().equals(player.getUUID()) 
				&& unitInfo.getOrders() == requiredOrders && unitInfo.isSquad(getSquad(itemstack));
		}
		return false;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack heldItem = player.getItemInHand(hand);
		if(getType(heldItem).isUnset()) {
			openHornSelectRedial(world);
		}else {
			if(player.isShiftKeyDown()) {
				openHornSelectRedial(world);
			}else {
				player.startUsingItem(hand);
			}
		}
		return ActionResult.sidedSuccess(heldItem, world.isClientSide());
	}

	private void openHornSelectRedial(World world) {
		if(world.isClientSide()) {
			ExtendedClientsideSeparatedScreenHandler.openHornModeSelect();
		}
	}

	@Override
    public ActionResultType onItemUseFirst(ItemStack pStack, ItemUseContext context) {
        if(context.getLevel().getBlockState(context.getClickedPos()).getBlock() == ExtendedBlocks.CONQUEST_TABLE.get()) {
        	if(context.getLevel().isClientSide()) {
            	ExtendedClientsideSeparatedScreenHandler.openHornSquadSelect(getSquad(pStack));
        	}
            return ActionResultType.sidedSuccess(context.getLevel().isClientSide());
        }
        return ActionResultType.PASS;
    }

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		IFormattableTextComponent modeName;
		if(!getType(pStack).isUnset()) {
			modeName = new TranslationTextComponent(getType(pStack).getType()).withStyle(getType(pStack).getFormatting());
		}else {
			modeName = new TranslationTextComponent("gui.lotrextended.base.unset").withStyle(TextFormatting.GRAY);
		}
		pTooltip.add(1, new TranslationTextComponent("gui.lotrextended.horn_of_command.type", modeName).withStyle(TextFormatting.WHITE));
		IFormattableTextComponent squadName;
		if(getSquad(pStack)!=null) {
			squadName = new StringTextComponent(getSquad(pStack)).withStyle(TextFormatting.WHITE);
		}else {
			squadName = new TranslationTextComponent("gui.lotrextended.base.unset").withStyle(TextFormatting.GRAY);
		}
		pTooltip.add(2, new TranslationTextComponent("gui.lotrextended.horn_of_command.squad", squadName).withStyle(TextFormatting.WHITE));
	}

	public static ExtendedUnitHornTypes getType(ItemStack itemStack) {
		if(!itemStack.hasTag() || !itemStack.getTag().contains(HORNTYPE)) {
			return ExtendedUnitHornTypes.NOTSET;
		}
		return ExtendedUnitHornTypes.getByUnchangingIndex(itemStack.getTag().getInt(HORNTYPE));
	}

	public static ItemStack setType(ItemStack itemStack, ExtendedUnitHornTypes type) {
		if(type==ExtendedUnitHornTypes.NOTSET) {
			itemStack.removeTagKey(HORNTYPE);
		}else {
			itemStack.getOrCreateTag().putInt(HORNTYPE, type.getUnchangingIndex());
		}
		return itemStack;
	}

	public static @Nullable String getSquad(ItemStack itemStack) {
		if(!itemStack.hasTag() || !itemStack.getTag().contains(SQUAD)) {
			return null;
		}
		return itemStack.getTag().getString(SQUAD);
	}

	public static ItemStack setSquad(ItemStack itemStack, @Nullable String pSquadName) {
		if(pSquadName==null || pSquadName.isEmpty()) {
			itemStack.removeTagKey(SQUAD);
		}else {
			itemStack.getOrCreateTag().putString(SQUAD, pSquadName);
		}
		return itemStack;
	}
}
