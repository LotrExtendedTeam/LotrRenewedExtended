package lotr.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class PoisonBottleItem extends Item {
    public PoisonBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
    	PlayerEntity asPlayer = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
		if (asPlayer instanceof ServerPlayerEntity) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) asPlayer, stack);
		}
        if (asPlayer == null || !asPlayer.abilities.instabuild) {
			stack.shrink(1);
			if (stack.isEmpty()) {
				super.finishUsingItem(stack, world, asPlayer);
				return new ItemStack(Items.GLASS_BOTTLE);
			}
			super.finishUsingItem(stack, world, asPlayer);
			if (asPlayer != null) {
				asPlayer.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
        return stack;
    }
}
