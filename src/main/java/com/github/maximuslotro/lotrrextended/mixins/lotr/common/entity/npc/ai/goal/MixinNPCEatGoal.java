package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc.ai.goal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.ai.goal.NPCConsumeGoal;
import lotr.common.entity.npc.ai.goal.NPCEatGoal;
import net.minecraft.item.ItemStack;

/**
 * A Mixin that modifies the behavior of the {@link NPCEatGoal} class to allow custom consumable logic
 * for NPC entities by injecting into the {@code createConsumable} method.
 * <p>
 * This Mixin targets NPC behavior to return alternative food items defined in {@link ExtendedNPCEntity}.
 * </p>
 * @Mixin (NPCEatGoal.class)
 */
@Mixin(NPCEatGoal.class)
public abstract class MixinNPCEatGoal extends NPCConsumeGoal {
	/**
     * Required constructor for Mixins extending classes with constructors.
     * <p>
     * This constructor exists only to satisfy the Java compiler and will be stripped at runtime by the Mixin system.
     * </p>
     *
     * @param entity The NPC entity this goal is associated with (not used directly in the Mixin).
     * @param chance The chance parameter from the original goal constructor (placeholder only).
     */
	public MixinNPCEatGoal(NPCEntity entity, int chance) {
		super(entity, chance);
	}
	/**
     * Injects into the {@code createConsumable} method of {@link NPCEatGoal} before any code runs.
     * <p>
     * This override checks if the NPC has a valid alternative food item via {@link ExtendedNPCEntity#getValidOtherFood()}.
     * If a valid item is found, it sets the return value to that item and cancels further execution.
     * </p>
     *
     * @Inject (method = "createConsumable()Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), remap=false, cancellable = true)
     * @param cir Callback info returnable used to set the method's return value and cancel execution.
     */
	@Inject(method = "createConsumable()Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), remap=false, cancellable = true)
	protected void createConsumableUnit(CallbackInfoReturnable<ItemStack> cir) {
		ItemStack potentialOtherItem = ((ExtendedNPCEntity)this.theEntity).getValidOtherFood();
		if(!potentialOtherItem.isEmpty()) {
			cir.setReturnValue(potentialOtherItem);
		}
	}
}
