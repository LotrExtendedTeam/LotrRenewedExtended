package lotr.common.item;

import lotr.common.fac.FoodAlignmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BerryItem extends Item {
    public BerryItem(Properties properties) {
        super(properties);
    }

    public static final ResourceLocation WILDBERRY_ID = new ResourceLocation("lotr", "wildberry");

    public static boolean isWildberry(Item item) {
        return item.getRegistryName().equals(WILDBERRY_ID);
    }

    public static void givePoison(LivingEntity entity) {
        if (!entity.level.isClientSide) {
            entity.addEffect(new EffectInstance(Effects.POISON, 10, 1));
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        if (!world.isClientSide) {
            if (isWildberry(stack.getItem())) {
                givePoison(entity); // Give poison if it's a wildberry
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }
}
