package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.data.name.NPCNameGenerator;

@Mixin(NPCEntity.class)
public interface MixinNPCEntityInvoker {
	@Invoker(remap = false, value = "getNameGenerator")
	NPCNameGenerator invokeNameGenerator();

	@Invoker(remap = false, value = "getGenderProvider")
	NPCGenderProvider invokeGenderProvider();
}
