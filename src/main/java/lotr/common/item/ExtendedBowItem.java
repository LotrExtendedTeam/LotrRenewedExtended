package lotr.common.item;

import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRMaterial;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

public class ExtendedBowItem extends BowItem {

    private double arrowDamageFactor;

    public ExtendedBowItem(LOTRMaterial material) {
        this(material, 1.0);
    }

    public ExtendedBowItem(LOTRMaterial material, double damageFactor) {
        super((new Item.Properties()).tab(LOTRItemGroups.COMBAT).durability(material.asTool().getUses()));
        this.arrowDamageFactor = damageFactor;
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
}
