package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedHiredSitFollowGoal;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitHornTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.item.clientside.ExtendedCommandHornClientSide;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.LOTRItemGroups;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ExtendedCommandHornItem extends Item {
	public static final String NBTvalue = "usage_type";

	public ExtendedCommandHornItem(Properties properties) {
		super(properties);
	}

	public ExtendedCommandHornItem() {
		super(new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT));
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
		if(type.isSummon()) {
			List<NPCEntity> teleportableUnits = world.getEntitiesOfClass(NPCEntity.class, new AxisAlignedBB(entityplayer.blockPosition()).inflate(64), (entity)->{
				return ((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty() && ((ExtendedINPCEntity)entity).getUnitInfo().getOwnerUUID().equals(entityplayer.getUUID())
					&& ((ExtendedINPCEntity)entity).getUnitInfo().getOrders()==ExtendedUnitOrders.FOLLOW_PLAYER;
			});
			for(NPCEntity unit : teleportableUnits) {
				ExtendedHiredSitFollowGoal.teleportToOwner(unit, entityplayer, true);
			}
		}
		if(type.isHalt()) {
			List<NPCEntity> followingUnits = world.getEntitiesOfClass(NPCEntity.class, new AxisAlignedBB(entityplayer.blockPosition()).inflate(64), (entity)->{
				return ((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty() && ((ExtendedINPCEntity)entity).getUnitInfo().getOwnerUUID().equals(entityplayer.getUUID())
					&& ((ExtendedINPCEntity)entity).getUnitInfo().getOrders()==ExtendedUnitOrders.FOLLOW_PLAYER;
			});
			for(NPCEntity unit : followingUnits) {
				((ExtendedINPCEntity)unit).getUnitInfo().setOrders(ExtendedUnitOrders.STATIONARY);
			}
			setType(itemstack, ExtendedUnitHornTypes.READY);
		}
		if(type.isReady()) {
			List<NPCEntity> stationaryUnits = world.getEntitiesOfClass(NPCEntity.class, new AxisAlignedBB(entityplayer.blockPosition()).inflate(64), (entity)->{
				return ((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty() && ((ExtendedINPCEntity)entity).getUnitInfo().getOwnerUUID().equals(entityplayer.getUUID())
					&& ((ExtendedINPCEntity)entity).getUnitInfo().getOrders()==ExtendedUnitOrders.STATIONARY;
			});
			for(NPCEntity unit : stationaryUnits) {
				((ExtendedINPCEntity)unit).getUnitInfo().setOrders(ExtendedUnitOrders.FOLLOW_PLAYER);
			}
			setType(itemstack, ExtendedUnitHornTypes.HALT);
		}
		world.playSound(null, entityplayer.blockPosition(), ExtendedSounds.HORN_BLOW.get(), SoundCategory.PLAYERS, 4.0F, 1.0F);
		return itemstack;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack heldItem = player.getItemInHand(hand);
		if (getType(heldItem).isUnset()) {
			if (world.isClientSide()) {
				new ExtendedCommandHornClientSide().openMenu();
			}
			return ActionResult.sidedSuccess(heldItem, world.isClientSide());
		}else {
			player.startUsingItem(hand);
			return ActionResult.sidedSuccess(heldItem, world.isClientSide());
		}
	}

	@Override
	public String getDescriptionId(ItemStack itemstack) {
		return super.getDescriptionId()+getType(itemstack).getDisc();
	}

	public static ExtendedUnitHornTypes getType(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		if(compoundnbt == null || !compoundnbt.contains(NBTvalue)) {
			return ExtendedUnitHornTypes.NOTSET;
		}
		return ExtendedUnitHornTypes.getByUnchangingIndex(compoundnbt.getInt(NBTvalue));
	}

	public static void setType(ItemStack itemStack, ExtendedUnitHornTypes type) {
		if(!itemStack.hasTag()) {
			itemStack.setTag(new CompoundNBT());
		}
		itemStack.getTag().putInt(NBTvalue, type.getUnchangingIndex());
	}
}
