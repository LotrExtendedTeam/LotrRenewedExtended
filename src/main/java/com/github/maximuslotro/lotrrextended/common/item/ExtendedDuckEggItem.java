package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedDuckEggEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ExtendedDuckEggItem extends Item {
   public ExtendedDuckEggItem(Item.Properties properties) {
      super(properties);
   }

   /**
    * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
    * {@link #onItemUse}.
    */
   public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
      ItemStack itemstack = pPlayer.getItemInHand(pHand);
      pLevel.playSound((PlayerEntity)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
      if (!pLevel.isClientSide) {
    	  ExtendedDuckEggEntity eggentity = new ExtendedDuckEggEntity(pLevel, pPlayer);
         eggentity.setItem(itemstack);
         eggentity.shootFromRotation(pPlayer, pPlayer.xRot, pPlayer.yRot, 0.0F, 1.5F, 1.0F);
         pLevel.addFreshEntity(eggentity);
      }

      pPlayer.awardStat(Stats.ITEM_USED.get(this));
      if (!pPlayer.abilities.instabuild) {
         itemstack.shrink(1);
      }

      return ActionResult.sidedSuccess(itemstack, pLevel.isClientSide());
   }
}