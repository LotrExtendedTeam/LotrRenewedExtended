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
	public static final RegistryObject<SoundEvent> DUCK_STEP = register("entity.duck.step", null);
	public static final RegistryObject<SoundEvent> DUCK_EGG = register("entity.duck.egg", "Duck plops");
	public static final RegistryObject<SoundEvent> BASKET_OPEN = register("block.reed_basket.open", "Reed basket ppens");
	public static final RegistryObject<SoundEvent> BASKET_CLOSE = register("block.reed_basket.close", "Reed basket closes");
	public static final RegistryObject<SoundEvent> STONE_CHEST_OPEN = register("block.stone_chest.open", "Stone chest opens");
	public static final RegistryObject<SoundEvent> STONE_CHEST_CLOSE = register("block.stone_chest.close", "Stone chest closes");
	public static final RegistryObject<SoundEvent> MILLSTONE_GRINDING = register("block.stone_grinder.grinding", "Stone Grinder grinds");
	public static final RegistryObject<SoundEvent> CHEESE_SLICE = register("block.cheese_wheel.slice", "Cheese Wheel sliced");
	public static final RegistryObject<SoundEvent> CONQUEST_TABLE_OPEN = register("block.conquest_table.open", null);
	public static final RegistryObject<SoundEvent> POT_FILLED = register("block.pot.fill", null);

	public static final RegistryObject<SoundEvent> MOSS_BROKEN = register("block.moss.break", null);
	public static final RegistryObject<SoundEvent> MOSS_PLACED = register("block.moss.place", null);
	public static final RegistryObject<SoundEvent> MOSS_HIT = register("block.moss.hit", null);
	public static final RegistryObject<SoundEvent> MOSS_FALL = register("block.moss.fall", null);
	public static final RegistryObject<SoundEvent> MOSS_STEP = register("block.moss.step", null);

	public static final RegistryObject<SoundEvent> WIGHT_AMBIENCE = register("entity.wight.ambience", "Creepy noises");
	public static final RegistryObject<SoundEvent> WIGHT_SPAWN = register("entity.wight.spawn", "Wight spawns");
	public static final RegistryObject<SoundEvent> WIGHT_DEATH = register("entity.wight.death", "Wight death");

	public static final RegistryObject<SoundEvent> ROPE_BROKEN = register("block.rope.break", null);
	public static final RegistryObject<SoundEvent> ROPE_PLACED = register("block.rope.place", null);
	public static final RegistryObject<SoundEvent> ROPE_STEP = register("block.rope.step", null);

	public static final RegistryObject<SoundEvent> BOAR_AMBIENT = register("entity.boar.ambient", "Boar snorts");
	public static final RegistryObject<SoundEvent> BOAR_ANGRY = register("entity.boar.angry", "Boar huffs");
	public static final RegistryObject<SoundEvent> BOAR_ARMOR = register("entity.boar.armor", "Boar armor equips");
	public static final RegistryObject<SoundEvent> BOAR_DEATH = register("entity.boar.death", "Boar dies");
	public static final RegistryObject<SoundEvent> BOAR_EAT = register("entity.boar.eat", "Boar eats");
	public static final RegistryObject<SoundEvent> BOAR_HURT = register("entity.boar.hurt", "Boar hurts");
	public static final RegistryObject<SoundEvent> BOAR_STEP = register("entity.boar.footsteps", null);
	public static final RegistryObject<SoundEvent> BOAR_SADDLE = register("entity.boar.saddle", null);
	public static final RegistryObject<SoundEvent> BOAR_JUMPS = register("entity.boar.jump", "Boar jumps");

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
