package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedHiredSitFollowGoal;

import lotr.common.entity.npc.AbstractMannishEntity;
import lotr.common.entity.npc.HobbitEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.World;

/**
 * Mixin class that modifies the behavior of {@link HobbitEntity}.
 * This class extends {@link AbstractMannishEntity} and is used to inject
 * custom AI goals, specifically making Hobbit entities avoid Warg entities.
 * @Mixin (HobbitEntity.class)
 */
@Mixin(HobbitEntity.class)
public abstract class MixinHobbitEntity extends AbstractMannishEntity {

	/**
	 * Required constructor for mixin classes that extend other classes.
	 * This constructor is only present at compile time and will be removed at runtime.
	 *
	 * @param type The entity type, typically passed to the superclass.
	 * @param w    The world in which this entity exists.
	 */
	public MixinHobbitEntity(EntityType<? extends AbstractMannishEntity> type, World w) {
		super(type, w);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Injected method to extend the NPC AI of Hobbit entities.
	 * This ensures that Hobbits will now actively avoid Wargs, which wasn't handled before.
	 * 
	 * <p>
	 * Injects at the end (TAIL) of the {@code addNPCAI} method to append an
	 * {@link AvoidEntityGoal} for {@link WargEntity}.
	 * 
	 * This method also adds an {@link ExtendedHiredSitFollowGoal} to the NPC's goal selector,
     * enabling it to follow and sit with enhanced behavior parameters.
	 * </p>
	 * 
	 * @param ci CallbackInfo instance provided by the mixin injector; not used here,
	 *           but available for cancelling or modifying behavior if needed.
	 * 
	 * 	@Inject (method = "addNPCAI()V", at = @At("TAIL"), remap=false)
	 */
	@Inject(method = "addNPCAI()V", at = @At("TAIL"), remap=false)
	protected void addNPCAIExtended(CallbackInfo ci) {
		goalSelector.addGoal(1, new AvoidEntityGoal<>(this, WargEntity.class, 12.0F, 1.5D, 1.8D));
		goalSelector.addGoal(2, new ExtendedHiredSitFollowGoal((NPCEntity)(Object)this, 1.3D, 8.0F, 3.0F, false));

	}
}
