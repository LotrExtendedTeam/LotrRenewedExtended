package com.github.maximuslotro.lotrExtended.common.init;

import com.github.maximuslotro.lotrExtended.LotrExtendedMod;
import com.github.maximuslotro.lotrExtended.common.datagen.DataGenValueHolder;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LotrExtendedMod.MODID);
	public static final RegistryObject<SoundEvent> HORN_BLOW = register("item.horn_blow");
	public static final RegistryObject<SoundEvent> TRADE = register("event.trade");

	public static RegistryObject<SoundEvent> register(String name) {
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.soundEventHolder.add("subtitles."+LotrExtendedMod.MODID+"."+name);
		}
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(LotrExtendedMod.MODID, name)));
	}
	public static void register(IEventBus bus) {
		SOUNDS.register(bus);
	}
}
