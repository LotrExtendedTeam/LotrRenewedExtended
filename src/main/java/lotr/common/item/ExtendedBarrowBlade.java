package lotr.common.item;

import lotr.common.init.LOTRMaterial;
import lotr.common.util.LOTREnchantUtils;
import lotr.common.util.representation.LOTREnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class ExtendedBarrowBlade extends ExtendedDaggerItem {

    public ExtendedBarrowBlade() {
        super(LOTRMaterial.ARNOR);
    }

    // Somewhat hacky way to get the enchant to always be added to items once
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide && entity instanceof PlayerEntity) {
            CompoundNBT tag = stack.getOrCreateTag();
            if (!tag.getBoolean("WightBaneEnchantAdded")) {
                tag.putBoolean("WightBaneEnchantAdded", true);
                LOTREnchantUtils.enchant(stack, LOTREnchantment.WIGHT_BANE, 1, false);
            }
        }
    }

}
