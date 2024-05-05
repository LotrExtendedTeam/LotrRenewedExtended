package lotr.common.item;

import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRMaterial;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
}
