package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LotrExtendedMod.MODID);
	public static final RegistryObject<SoundEvent> HORN_BLOW = register("item.horn_blow", "Horn blows");
	public static final RegistryObject<SoundEvent> TRADE = register("event.trade", "Coins jingle");
	public static final RegistryObject<SoundEvent> DUCK_DEATH = register("entity.duck.death", "Duck dies");
	public static final RegistryObject<SoundEvent> DUCK_HURT = register("entity.duck.hurt", "Duck hurts");
	public static final RegistryObject<SoundEvent> DUCK_AMBIANT = register("entity.duck.idle", "Duck quacks");
	public static final RegistryObject<SoundEvent> DUCK_STEP = register("entity.duck.step", "Footsteps");
	public static final RegistryObject<SoundEvent> DUCK_EGG = register("entity.duck.egg", "Duck plops");
	public static final RegistryObject<SoundEvent> BASKET_OPEN = register("block.reed_basket.open", "Reed Basket Opens");
	public static final RegistryObject<SoundEvent> BASKET_CLOSE = register("block.reed_basket.close", "Reed Basket Closes");
	public static final RegistryObject<SoundEvent> STONE_CHEST_OPEN = register("block.stone_chest.open", "Stone Chest Opens");
	public static final RegistryObject<SoundEvent> STONE_CHEST_CLOSE = register("block.stone_chest.close", "Stone Chest Closes");
	public static final RegistryObject<SoundEvent> MILLSTONE_GRINDING = register("block.stone_grinder.grinding", "Stone Grinder Grinds");
	public static final RegistryObject<SoundEvent> CHEESE_SLICE = register("block.cheese_wheel.slice", "Cheese Wheel Sliced");
	
	public static final RegistryObject<SoundEvent> MOSS_BROKEN = register("block.moss.break", null);
	public static final RegistryObject<SoundEvent> MOSS_PLACED = register("block.moss.place", null);
	public static final RegistryObject<SoundEvent> MOSS_HIT = register("block.moss.hit", null);
	public static final RegistryObject<SoundEvent> MOSS_FALL = register("block.moss.fall", null);
	public static final RegistryObject<SoundEvent> MOSS_STEP = register("block.moss.step", null);

	public static final RegistryObject<SoundEvent> WIGHT_AMBIENCE = register("entity.wight.ambience", "Creepy Noises");

	public static final RegistryObject<SoundEvent> ROPE_BROKEN = register("block.rope.break", null);
	public static final RegistryObject<SoundEvent> ROPE_PLACED = register("block.rope.place", null);
	public static final RegistryObject<SoundEvent> ROPE_STEP = register("block.rope.step", null);
	
	public static RegistryObject<SoundEvent> register(String name, String defaultName) {
		if(DatagenModLoader.isRunningDataGen() && defaultName!=null) {
			DataGenValueHolder.soundEventHolder.put("subtitles."+LotrExtendedMod.MODID+"."+name, defaultName);
		}
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(LotrExtendedMod.MODID, name)));
	}

	public static void register(IEventBus bus) {
		SOUNDS.register(bus);
	}
}
