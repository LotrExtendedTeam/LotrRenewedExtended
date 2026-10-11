package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedHiredFollowGoal;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedHiredSitGoal;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.ElfEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

/**
 * A Mixin class that injects additional behavior into {@link ElfEntity}.
 * @Mixin (ElfEntity.class)
 */
@Mixin(ElfEntity.class)
public abstract class MixinElfEntity extends NPCEntity {
	/**
	 * Required constructor for Mixins extending other classes.
	 * <p>
	 * Although required for compilation, this constructor will be stripped at runtime and is not executed.
	 *
	 * @param type  A placeholder {@link EntityType} for {@link ElfEntity}, required for superclass initialization.
	 * @param world A placeholder {@link World} instance, required for superclass initialization.
	 */
	public MixinElfEntity(EntityType<? extends ElfEntity> type, World world) {
		super(type, world);
	}
	/**
	 * Injects a custom AI goal at the end of the {@code addNPCAI} method execution.
	 * 
	 * @param ci The {@link CallbackInfo} provided by the Mixin injector.
	 * @Inject (method = "addNPCAI()V", at = @At("TAIL"), remap = false)
	 */
	@Inject(method = "addNPCAI()V", at = @At("TAIL"), remap = false)
	protected void addNPCAIExtended(CallbackInfo ci) {
		this.goalSelector.addGoal(1, new ExtendedHiredSitGoal((NPCEntity)(Object)this));
		this.goalSelector.addGoal(3, new ExtendedHiredFollowGoal((NPCEntity)(Object)this, 1.3D, 8.0F, 6.0F));
	}
}
