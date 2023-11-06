package lotr.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

public class PoisonBottleItem extends PotionItem {
    public PoisonBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        PlayerEntity playerentity = entity instanceof PlayerEntity ? (PlayerEntity)entity : null;
        if (playerentity instanceof ServerPlayerEntity) {
           CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerentity, stack);
        }

        if (!world.isClientSide) {
        	entity.addEffect(new EffectInstance(Effects.POISON, 1200, 4));
        }

        if (playerentity != null) {
           playerentity.awardStat(Stats.ITEM_USED.get(this));
           if (!playerentity.abilities.instabuild) {
              stack.shrink(1);
           }
        }

        if (playerentity == null || !playerentity.abilities.instabuild) {
           if (stack.isEmpty()) {
              return new ItemStack(Items.GLASS_BOTTLE);
           }

           if (playerentity != null) {
              playerentity.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
           }
        }

        return stack;
     }

}
