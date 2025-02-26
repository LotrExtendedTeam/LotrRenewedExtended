package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.data.name.NPCNameGenerator;

/**
 * @reason To retrieve information from NPCEntity
 * @Mixin (NPCEntity.class)
 * */
@Mixin(NPCEntity.class)
public interface MixinNPCEntityInvoker {
	/**
	 * @reason Used to retrieve the getNameGenerator Method from NPCEntity since we can't make it public with AccessTransformers
	 * @Invoker (remap = false, value = "getNameGenerator")
	 * @return The NPCNameGenerator instance for this NPCEntity
	 * */
	@Invoker(remap = false, value = "getNameGenerator")
	NPCNameGenerator invokeNameGenerator();

	/**
	 * @reason Used to retrieve the getGenderProvider Method from NPCEntity since we can't make it public with AccessTransformers
	 * @Invoker (remap = false, value = "getGenderProvider")
	 * @return The NPCGenderProvider instance for this NPCEntity
	 * */
	@Invoker(remap = false, value = "getGenderProvider")
	NPCGenderProvider invokeGenderProvider();
}
