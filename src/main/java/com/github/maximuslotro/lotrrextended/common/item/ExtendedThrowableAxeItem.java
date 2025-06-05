package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedThrowableAxeEntity;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTRMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ExtendedThrowableAxeItem extends Item {

    private LOTRMaterial axeMaterial;

    public ExtendedThrowableAxeItem(Properties properties) {
        super(properties);
    }

    public ExtendedThrowableAxeItem(Properties properties, LOTRMaterial material) {
        super(properties);
        this.axeMaterial = material;
    }

    public void setAxeMaterial(LOTRMaterial material) {
        this.axeMaterial = material;
    }

    public LOTRMaterial getAxeMaterial() {
        return axeMaterial;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        if (!world.isClientSide) {
            // Create and throw the axe entity
            ExtendedThrowableAxeEntity thrownAxe = new ExtendedThrowableAxeEntity(
            ExtendedEntities.IRON_THROWN_AXE.get(), // You'll need the appropriate entity type
            player, 
            world, 
            itemstack.copy()
        );
            
            // Set the velocity based on player's look direction
            thrownAxe.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 1.5F, 1.0F);
            
            // Add the entity to the world
            world.addFreshEntity(thrownAxe);
            
            // Play throwing sound
            world.playSound(null, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 
                0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
        }
        
        // Update player stats
        player.awardStat(Stats.ITEM_USED.get(this));
        
        // Consume the item if not in creative mode
        if (!player.abilities.instabuild) {
            itemstack.shrink(1);
        }
        
        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000; // Same as bow
    }

    /**
     * Get the ranged damage multiplier for this throwing axe
     * @param itemstack The itemstack being thrown
     * @param shooter The entity throwing the axe
     * @param target The target being hit
     * @return The damage multiplier
     */
    public float getRangedDamageMultiplier(ItemStack itemstack, LivingEntity shooter, Entity target) {
        if (axeMaterial == null) {
            return 4.0F; // Default damage
        }

        // Base damage based on material
        float baseDamage = getBaseDamageForMaterial(axeMaterial);
        
        // Apply any additional modifiers here if needed
        // For example, enchantments, durability, etc.
        
        return baseDamage;
    }

    /**
     * Get base damage for the axe material
     */
    private float getBaseDamageForMaterial(LOTRMaterial material) {
        switch (material) {
            case BRONZE:
                return 6.0F;
            case BLUE_DWARVEN:
                return 8.5F;
            case DWARVEN:
                return 9.0F;
            case GONDOR:
                return 8.0F;
            default:
                return 4.0F;
        }
    }

    /**
     * Get the throwing range of this axe
     */
    public float getThrowingRange() {
        if (axeMaterial == null) {
            return 16.0F;
        }
        
        switch (axeMaterial) {
            case BRONZE:
                return 14.0F;
            case BLUE_DWARVEN:
                return 18.0F; // Dwarven craftsmanship provides better balance
            case DWARVEN:
                return 20.0F; // Superior dwarven engineering
            case GONDOR:
                return 17.0F; // Well-crafted but not as specialized as dwarven
            default:
                return 16.0F;
        }
    }

    /**
     * Check if this axe can be enchanted
     */
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    /**
     * Get enchantability of this axe
     */
    @Override
    public int getEnchantmentValue() {
        if (axeMaterial == null) {
            return 1;
        }
        
        switch (axeMaterial) {
            case BRONZE:
                return 8;
            case BLUE_DWARVEN:
                return 12; // Good enchantability but not the highest
            case DWARVEN:
                return 16; // Excellent enchantability due to superior craftsmanship
            case GONDOR:
                return 15; // High quality human craftsmanship
            default:
                return 1;
        }
    }

    /**
     * Get the durability of this axe when used as a throwing weapon
     */
    public int getThrowingDurability() {
        if (axeMaterial == null) {
            return 100;
        }
        
        switch (axeMaterial) {
            case BRONZE:
                return 180; // Better than iron in some ways
            case BLUE_DWARVEN:
                return 400; // Dwarven quality but not the finest
            case DWARVEN:
                return 600; // Exceptional durability
            case GONDOR:
                return 350; // High quality human craftsmanship
            default:
                return 100;
        }
    }
}
