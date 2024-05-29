package com.github.maximuslotro.lotrrextended.common.item;

import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;

import lotr.common.init.LOTRItemGroups;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ExtendedBrandingIronItem extends Item {
	public static final String isHot = "heated";
	public static final String UserUUID = "TempererName";
	public static final String Name = "TempererName";
	public static final String USES_LEFT = "Uses";

	public ExtendedBrandingIronItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	public ExtendedBrandingIronItem() {
		super(new Properties().stacksTo(1).tab(LOTRItemGroups.TOOLS).durability(100));
	}

	@Override
	public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack p_82789_2_) {
		return p_82789_2_.getItem() == Items.IRON_INGOT;
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack item, PlayerEntity player, LivingEntity entity, Hand p_111207_4_) {
		if (item.getDamageValue() >= item.getMaxDamage() - 1) {
	         return ActionResultType.FAIL;
	      }
		if(isTempered(player.getMainHandItem())&&getTemperedName(player.getMainHandItem())!=null) {
			if(entity instanceof AnimalEntity) {
				AnimalEntity animal = ((AnimalEntity) entity);
				if(!animal.hasCustomName() || !animal.getCustomName().getString().equals(getTemperedName(player.getMainHandItem()))) {
					animal.setCustomName(new StringTextComponent(getTemperedName(player.getMainHandItem())));
					animal.setPersistenceRequired();
					animal.playAmbientSound();
					animal.getJumpControl().jump();
					entity.level.playSound(player, entity.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1, 1);
					if(!entity.level.isClientSide()) {
						setTempererName(entity, player.getUUID());
						damageItem(player.getMainHandItem());
						player.getMainHandItem().setDamageValue(player.getMainHandItem().getDamageValue()+1);
					}
					player.awardStat(ExtendedStats.ANIMALS_BRANDED);
					ActionResultType.sidedSuccess(entity.level.isClientSide);
				}
			}
		}
		return super.interactLivingEntity(item, player, entity, p_111207_4_);
	}

	@Override
	public ActionResultType useOn(ItemUseContext p_195939_1_) {
		BlockState blockstate = p_195939_1_.getLevel().getBlockState(p_195939_1_.getClickedPos());
		if (blockstate.is(BlockTags.FIRE)||blockstate.is(BlockTags.CAMPFIRES)) {
			World world = p_195939_1_.getLevel();
			if (!world.isClientSide) {
				setTempered(p_195939_1_.getItemInHand(), true);
			}
			return ActionResultType.sidedSuccess(world.isClientSide);
		} else {
			return super.useOn(p_195939_1_);
		}
	}

	//Item Heated Handling
	public static boolean isTempered(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return compoundnbt != null && compoundnbt.contains(isHot) && compoundnbt.getBoolean(isHot);
	}

	public static void setTempered(ItemStack itemStack, boolean type) {
		if(!itemStack.hasTag()) {
			itemStack.setTag(new CompoundNBT());
		}
		itemStack.getTag().putBoolean(isHot, type);
		if(type) {
			setUses(itemStack, 5);
		}
		else {
			setUses(itemStack, 0);
		}
	}

	//Item Uses Data Handling
	public static int getUses(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		if(compoundnbt != null && compoundnbt.contains(USES_LEFT)) {
			return compoundnbt.getInt(USES_LEFT);
		}
		return 0;
	}

	public static void setUses(ItemStack itemStack, int number) {
		if(!itemStack.hasTag()) {
			itemStack.setTag(new CompoundNBT());
		}
		itemStack.getTag().putInt(USES_LEFT, number);
	}

	public static void damageItem(ItemStack itemStack) {
		if(!itemStack.hasTag()) {
			setUses(itemStack, 5);
			return;
		}
		if(getUses(itemStack)!=0) {
			if(getUses(itemStack)-1<=0) {
				setTempered(itemStack, false);
				itemStack.getTag().remove(USES_LEFT);
			}
			else {
				setUses(itemStack, getUses(itemStack)-1);
			}
		}
	}

	//Item Renamed Data Handling
	@Nullable
	public static String getTemperedName(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		if(compoundnbt != null && compoundnbt.contains(Name)) {
			return compoundnbt.getString(Name);
		}
		if(itemStack.hasCustomHoverName()) {
			return itemStack.getHoverName().getString();
		}
		return null;
	}

	public static void setTemperedName(ItemStack itemStack, String name) {
		if(!itemStack.hasTag()) {
			itemStack.setTag(new CompoundNBT());
		}
		itemStack.getTag().putString(Name, name);
	}

	//Entity Master Data Handling
	@Nullable
	public static UUID getTempererName(Entity entity) {
		CompoundNBT compoundnbt = entity.getPersistentData();
		if(compoundnbt != null && compoundnbt.contains(UserUUID)) {
			return UUID.fromString(compoundnbt.getString(UserUUID));
		}
		return null;
	}

	public static void setTempererName(Entity entity, UUID master) {
		entity.getPersistentData().putString(UserUUID, master.toString());
	}
}
