package lotr.common.item;

import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRMaterial;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.List;

public class ExtendedBowItem extends BowItem {

    private double arrowDamageFactor;

    public ExtendedBowItem(LOTRMaterial material) {
        this(material, 1.0);
    }

    public ExtendedBowItem(LOTRMaterial material, double damageFactor) {
        super((new Item.Properties()).tab(LOTRItemGroups.COMBAT).durability(material == LOTRMaterial.RANGER_NORTH ? 525 : 400));
        this.arrowDamageFactor = damageFactor;
    }

    public ExtendedBowItem(int durability, double damageFactor) {
        super((new Item.Properties()).tab(LOTRItemGroups.COMBAT).durability(durability));
        this.arrowDamageFactor = damageFactor;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new StringTextComponent("Draw Speed: 100%").withStyle(TextFormatting.DARK_GREEN));
        tooltip.add(new StringTextComponent("Ranged Damage: " + String.format("%.0f%%", arrowDamageFactor * 100)).withStyle(TextFormatting.DARK_GREEN));
        tooltip.add(new StringTextComponent("Range: " + String.format("%.0f%%", arrowDamageFactor * 100)).withStyle(TextFormatting.DARK_GREEN));
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

                float f = getPowerForTime(i);
                if (f >= 0.1) {
                    boolean flag1 = playerentity.abilities.instabuild || itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, bow, playerentity);
                    if (!p_77615_2_.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(p_77615_2_, itemstack, playerentity);
                        abstractarrowentity = this.customArrow(abstractarrowentity);
                        abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * 3.0F * getLaunchSpeedFactor(bow), 1.0F);
                        if (f == 1.0F) {
                            abstractarrowentity.setCritArrow(true);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bow);
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

                        bow.hurtAndBreak(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
                        });
                        if (flag1 || playerentity.abilities.instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        p_77615_2_.addFreshEntity(abstractarrowentity);
                    }

                    p_77615_2_.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
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

    public static float getLaunchSpeedFactor(ItemStack itemstack) {
        float f = 1.0F;
        if (itemstack != null) {
            if (itemstack.getItem() instanceof ExtendedBowItem) {
                f = (float)(f * ((ExtendedBowItem)itemstack.getItem()).arrowDamageFactor);
            }
            // TODO: When we add smithing scrolls back in we can use this code
            //f*= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
        }
        return f;
    }

    public static void registerBowItemModelProperties(Item item) {
        ItemModelsProperties.register(item, new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
            if (p_239429_2_ == null) {
                return 0.0F;
            } else {
                return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getTicksUsingItem()) / 20.0F;
            }
        });

        ItemModelsProperties.register(item, new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> {
            return p_239428_2_ != null //&& p_239428_2_.getUseItem() != null
            && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F;
        });
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
        //return (int) Math.ceil(15 * arrowDamageFactor);
    }
}
