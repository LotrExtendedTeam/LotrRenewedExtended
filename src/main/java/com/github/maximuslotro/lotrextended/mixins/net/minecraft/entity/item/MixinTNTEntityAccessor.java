package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;

@Mixin(TNTEntity.class)
public interface MixinTNTEntityAccessor {
	@Accessor("owner")
	public void setOwner(LivingEntity pOwner);
}
