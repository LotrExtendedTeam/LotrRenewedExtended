package lotr.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ExtendedPoisonBottleItem extends Item {
	public ExtendedPoisonBottleItem(Properties properties) {
		super(properties.craftRemainder(Items.GLASS_BOTTLE));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
		PlayerEntity playerentity = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
		if (playerentity instanceof ServerPlayerEntity) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerentity, stack);
		}

		if (!world.isClientSide && playerentity != null && !playerentity.abilities.instabuild) {
			ExtendedPoisonedDrinks.severlyPoisonPlayer(playerentity);
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

	@Override
	public int getUseDuration(ItemStack p_77626_1_) {
		return 32;
	}

	@Override
	public UseAction getUseAnimation(ItemStack p_77661_1_) {
		return UseAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
	      return DrinkHelper.useDrink(p_77659_1_, p_77659_2_, p_77659_3_);
	}
}
