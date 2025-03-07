package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc.ai;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.entity.npc.ai.AttackMode;
import lotr.common.entity.npc.ai.ExtendedNPCCombatUpdater;
import lotr.common.entity.npc.ai.NPCCombatUpdater;

/**
 * @reason Need to be able to trigger an 
 * @Mixin(NPCCombatUpdater.class)
 * */
@Mixin(NPCCombatUpdater.class)
public class MixinNPCCombatUpdater implements ExtendedNPCCombatUpdater {
	/**
	 * @reason Gets current attack mode
	 * @Shadow (remap=false)
	 * */
	@Shadow(remap=false)
	private AttackMode currentAttackMode;
	/**
	 * @reason Gets if riding mount
	 * @Shadow (remap=false)
	 * */
	@Shadow(remap=false)
	private boolean ridingMount;
	
	/**
	 * @reason Used to refresh unit hand item after equipment update
	 * @Shadow (remap=false)
	 * @param newMode The attack mode to update hands for
	 * @param newRiding Lets the method know if the entity is riding a mount
	 * */
	@Shadow(remap=false)
	private void onAttackModeChange(AttackMode newMode, boolean newRiding) {}

	@Override
	public void refreshHand() {
		onAttackModeChange(currentAttackMode, ridingMount);
	}
}
