package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.player;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Mixin for {@link PlayerEntity} that changes some player behaviors.
 * <p>
 * New logic includes:
 * <ul>
 *     <li>Adjusting block-breaking speed through extended modifiers.</li>
 *     <li>Allowing custom behavior for when the player can eat food.</li>
 * </ul>
 * @Mixin (PlayerEntity.class)
 */
@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
	/**
	 * Constructor required for mixins that extend a superclass with non-default constructors.
	 * <p>
	 * This constructor is only needed at compile-time for the mixin system and is stripped
	 * out during runtime. It will never actually be called in the final game.
	 *
	 * @param type  the entity type
	 * @param world the world the entity exists in
	 */
	protected MixinPlayerEntity(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
	}
	/**
     * Shadowed player inventory to access the currently held items and their properties.
     * @Shadow
     * @Final
     */
	@Shadow
	@Final
	public PlayerInventory inventory;
	/**
     * Injects into {@link PlayerEntity#getDigSpeed(BlockState, BlockPos)} to add
     * extended block efficiency from modifiers and preserve vanilla behavior.
     * <p>
     * Extended functionality:
     * <ul>
     *     <li>Fetch additional block-breaking speed via {@link ExtendedModifierUtils#getBlockEfficiencyFloat(LivingEntity)}.</li>
     *     <li>Add the squared value of the modifier plus one to the current destroy speed.</li>
     * </ul>
     * 
     * @param pState the block state being mined
     * @param pos    the position of the block being mined, nullable
     * @param cir    callback info to override the method return value
     * @Inject (method="getDigSpeed(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)F", at = @At("HEAD"), remap=false, cancellable = true)
     */
	@Inject(method="getDigSpeed(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)F", 
			at = @At("HEAD"), remap=false, cancellable = true)
	public void getDigSpeed(BlockState pState, @Nullable BlockPos pos, CallbackInfoReturnable<Float> cir) {
		float itemDestroySpeed = inventory.getDestroySpeed(pState);
		if (itemDestroySpeed > 1.0F) {
			// Extended Start
			float additionalSpeed = ExtendedModifierUtils.getBlockEfficiencyFloat(this);
			// Extended End
			ItemStack itemstack = getMainHandItem();
			if (additionalSpeed > 0 && !itemstack.isEmpty()) {
				// Extended Start
				itemDestroySpeed += (additionalSpeed * additionalSpeed + 1);
				// Extended End
			}
		}

		if (EffectUtils.hasDigSpeed(this)) {
			itemDestroySpeed *= 1.0F + (float)(EffectUtils.getDigSpeedAmplification(this) + 1) * 0.2F;
		}

		if (hasEffect(Effects.DIG_SLOWDOWN)) {
			float slowDownModifier;
			switch(getEffect(Effects.DIG_SLOWDOWN).getAmplifier()) {
				case 0:
					slowDownModifier = 0.3F;
					break;
				case 1:
					slowDownModifier = 0.09F;
					break;
				case 2:
					slowDownModifier = 0.0027F;
					break;
				case 3:
				default:
					slowDownModifier = 8.1E-4F;
			}

			itemDestroySpeed *= slowDownModifier;
		}

		if (isEyeInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
			itemDestroySpeed /= 5.0F;
		}

		if (!onGround) {
			itemDestroySpeed /= 5.0F;
		}

		itemDestroySpeed = net.minecraftforge.event.ForgeEventFactory.getBreakSpeed((PlayerEntity)(Object)this, pState, itemDestroySpeed, pos);
		cir.setReturnValue(itemDestroySpeed);
	}
	/**
     * Injects into {@link PlayerEntity#canEat(boolean)} to optionally enable a
     * "feast mode" as seen in legacy, allowing players to eat regardless of hunger level.
     * <p>
     * This only activates when {@link ExtendedServerConfig#enableLegacyFeastMode} is true
     * and the player is not riding an entity.
     *
     * @param ignoreHunger whether vanilla logic should ignore hunger restrictions
     * @param cir          callback info for returning the modified result
     */
    @Inject(method = "canEat(Z)Z", at = @At("HEAD"), cancellable = true)
    private void feastMode(boolean ignoreHunger, CallbackInfoReturnable<Boolean> cir) {
        if(ExtendedServerConfig.enableLegacyFeastMode.get() && !((PlayerEntity)(Object)(this)).isVehicle()) {
            cir.setReturnValue(true);
        }
    }
}
