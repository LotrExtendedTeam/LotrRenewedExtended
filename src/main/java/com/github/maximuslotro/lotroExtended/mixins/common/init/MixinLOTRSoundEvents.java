package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.item.CommandHornItem;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

@Mixin(LOTRSoundEvents.class)
@SuppressWarnings("unused")
public class MixinLOTRSoundEvents {
	
	@Shadow(remap=false)
	private static SoundEvent preRegSound(String key) {return null;}
	
	public static final SoundEvent HORN_BLOW = preRegSound("item.horn");
}
