package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
