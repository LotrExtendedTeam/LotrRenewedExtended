package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.dispenser.ExtendedDispenserThrowingAxe;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedThrowableAxeEntity;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRMaterial;
import net.minecraft.block.DispenserBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity.PickupStatus;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ExtendedThrowableAxeItem extends TieredItem {

	public ExtendedThrowableAxeItem(IItemTier tier) {
		super(tier, new Properties().tab(LOTRItemGroups.COMBAT));
		DispenserBlock.registerBehavior(this, new ExtendedDispenserThrowingAxe());
	}

	public ExtendedThrowableAxeItem(LOTRMaterial material) {
		this((IItemTier)material.asTool());
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public int getUseDuration(ItemStack pStack) {
		return 1;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (canPlayerUseItem(player, hand)) {
			if (!world.isClientSide) {
				ItemStack axe = itemstack.copy();
				axe.setCount(1);
				ExtendedThrowableAxeEntity axeEntity = new ExtendedThrowableAxeEntity(world, player, axe);

				// Calculate offset to the right of the player
				double offsetDistance = 0.5; // Adjust this value to control how far right
				double rightX = -Math.sin(Math.toRadians(player.yRot + 90.0)); // Right vector X
				double rightZ = Math.cos(Math.toRadians(player.yRot + 90.0));  // Right vector Z

				// Apply the offset to the axe position
				axeEntity.setPos(
					player.getX() + rightX * offsetDistance,
					player.getEyeY() - 0.1, // Slightly below eye level
					player.getZ() + rightZ * offsetDistance
					);

				axeEntity.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 2.0F, 1.0F);
				axeEntity.setCritArrow(true);

				axeEntity.pickup = this.getPickupStatusForplayer(player);
				world.addFreshEntity(axeEntity);
			}

			world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));
			if (!player.abilities.instabuild) {
				itemstack.shrink(1);
				if (itemstack.isEmpty()) {
					player.inventory.removeItem(itemstack);
				}
			}

			player.awardStat(Stats.ITEM_USED.get(this));

			return ActionResult.success(itemstack);
		} else {
			return ActionResult.fail(itemstack);
		}
	}

	private boolean canPlayerUseItem(PlayerEntity player, Hand hand) {
		if (hand == Hand.MAIN_HAND) {
			ItemStack offhandItem = player.getOffhandItem();
			if (offhandItem.getUseAnimation() == UseAction.BLOCK) {
				return player.isShiftKeyDown();
			}
		}
		return true;
	}

	private AbstractArrowEntity.PickupStatus getPickupStatusForplayer(LivingEntity player) {
		if (player instanceof PlayerEntity) {
			return ((PlayerEntity)player).abilities.instabuild ? PickupStatus.CREATIVE_ONLY : PickupStatus.ALLOWED;
		} else {
			return PickupStatus.DISALLOWED;
		}
	}

	public static void applyStandardEnchantmentsFromBow(ItemStack stack, AbstractArrowEntity projectile) {
		float power = ExtendedModifierUtils.getItemEnchantmentLevelFloat(Enchantments.POWER_ARROWS, stack);
		if (power > 0) {
			projectile.setBaseDamage(projectile.getBaseDamage() + power * 0.5 + 0.5);
		}

		int punch = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
		if (punch > 0) {
			projectile.setKnockback(punch);
		}

		if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
			projectile.setSecondsOnFire(100);
		}

	}
}