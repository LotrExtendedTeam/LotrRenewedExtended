package lotr.common.item;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedModifiers;

import lotr.common.init.LOTRMaterial;
import lotr.common.util.LOTREnchantUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ExtendedBarrowBlade extends ExtendedDaggerItem {

    public ExtendedBarrowBlade() {
        super(LOTRMaterial.ARNOR);
    }

    // Somewhat hacky way to get the enchant to always be added to items once
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide && entity instanceof PlayerEntity) {
            if (!stack.hasTag() || !stack.getTag().getBoolean("WightBaneEnchantAdded")) {
            	stack.getTag().putBoolean("WightBaneEnchantAdded", true);
                LOTREnchantUtils.enchant(stack, ExtendedModifiers.WIGHT_BANE);
            }
        }
    }

}
