package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBowMaterial;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import lotr.common.init.LOTRItemGroups;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ExtendedBowItem extends BowItem {

	private final ExtendedBowMaterial bowMaterial;

	public ExtendedBowItem(ExtendedBowMaterial material) {
		super((new Item.Properties()).tab(LOTRItemGroups.COMBAT).durability(material.getDurability()));
		this.bowMaterial = material;
	}

    @Override
    public void releaseUsing(ItemStack bow, World p_77615_2_, LivingEntity p_77615_3_, int p_77615_4_) {
        if (p_77615_3_ instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)p_77615_3_;
            boolean flag = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow) > 0;
            ItemStack itemstack = playerentity.getProjectile(bow);
            int i = this.getUseDuration(bow) - p_77615_4_;
            i = ForgeEventFactory.onArrowLoose(bow, p_77615_2_, playerentity, i, !itemstack.isEmpty() || flag);
            if (i < 0) {
                return;
            }

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float charge = i / (float)bowMaterial.getDrawSpeedTicks();
				charge = (charge * charge + charge * 2.0F) / 3.0F;
				if (charge > 1.0F) {
					charge = 1.0F;
				}
                if (charge >= 0.1D) {
                    boolean flag1 = playerentity.abilities.instabuild || itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, bow, playerentity);
                    if (!p_77615_2_.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(p_77615_2_, itemstack, playerentity);
                        abstractarrowentity = this.customArrow(abstractarrowentity);
                        abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, charge * 3.0F * getLaunchSpeedFactor(bow), 1.0F);
                        if (charge == 1.0F) {
                            abstractarrowentity.setCritArrow(true);
                        }

                        float j = ExtendedModifierUtils.getItemEnchantmentLevelFloat(Enchantments.POWER_ARROWS, bow);
                        if (j > 0) {
                            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double)j * 0.5 + 0.5);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, bow);
                        if (k > 0) {
                            abstractarrowentity.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bow) > 0) {
                            abstractarrowentity.setSecondsOnFire(100);
                        }

                        bow.hurtAndBreak(1, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));
                        if (flag1 || playerentity.abilities.instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        p_77615_2_.addFreshEntity(abstractarrowentity);
                    }

                    p_77615_2_.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
                    if (!flag1 && !playerentity.abilities.instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.removeItem(itemstack);
                        }
                    }

                    playerentity.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }

    }

    public float getLaunchSpeedFactor(ItemStack itemstack) {
        float f = 1.0F;
        if (!itemstack.isEmpty()) {
            if (itemstack.getItem() instanceof ExtendedBowItem) {
                f *= bowMaterial.getAttackDamageBonusScale();
            }
            float additionalSpeed = ExtendedModifierUtils.getItemEnchantmentLevelFloat(Enchantments.POWER_ARROWS, itemstack);
            if(additionalSpeed!=0) {
            	f *= additionalSpeed;
            }
        }
        return f;
    }

    @Override
    public int getDefaultProjectileRange() {
       return (int) Math.ceil(15 * bowMaterial.getAttackDamageBonusScale());
    }

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	@Override
	public int getEnchantmentValue() {
		return this.bowMaterial.getEnchantabilityValue();
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
		return this.bowMaterial.getRepairIngredient().test(pRepair) || super.isValidRepairItem(pToRepair, pRepair);
	}

	public ExtendedBowMaterial getMaterial() {
		return this.bowMaterial;
	}
}
