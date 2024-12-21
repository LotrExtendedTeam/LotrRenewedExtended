package com.github.maximuslotro.lotrrextended.common.datagen;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinition.Sound;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedSoundDefinitionsGenerator extends SoundDefinitionsProvider{

	protected ExtendedSoundDefinitionsGenerator(DataGenerator generator, String modId, ExistingFileHelper helper) {
		super(generator, modId, helper);
	}

	@Override
	public void registerSounds() {
		add(ExtendedSounds.HORN_BLOW, createSimple(ExtendedSounds.HORN_BLOW));

		add(ExtendedSounds.TRADE, createMultipleSameWeight(ExtendedSounds.TRADE, 4, true));
		
		add(ExtendedSounds.DUCK_AMBIANT, createMultipleSameWeight(ExtendedSounds.DUCK_AMBIANT, 4, true));
		add(ExtendedSounds.DUCK_HURT, createMultipleSameWeight(ExtendedSounds.DUCK_HURT, 1, true));
		add(ExtendedSounds.DUCK_DEATH, createMultipleSameWeight(ExtendedSounds.DUCK_DEATH, 1, true));
		add(ExtendedSounds.DUCK_STEP, createMultipleSameWeight(ExtendedSounds.DUCK_STEP, 3, true));
		add(ExtendedSounds.DUCK_EGG, createCustomPathSound(ExtendedSounds.DUCK_EGG, new ResourceLocation("mob/chicken/plop")));

		add(ExtendedSounds.BASKET_OPEN, createMultipleSameWeight(ExtendedSounds.BASKET_OPEN, 1, true));
		add(ExtendedSounds.BASKET_CLOSE, createMultipleSameWeight(ExtendedSounds.BASKET_CLOSE, 1, true));

		add(ExtendedSounds.STONE_CHEST_OPEN, createMultipleSameWeight(ExtendedSounds.STONE_CHEST_OPEN, 1, true));
		add(ExtendedSounds.STONE_CHEST_CLOSE, createMultipleSameWeight(ExtendedSounds.STONE_CHEST_CLOSE, 1, true));

		add(ExtendedSounds.CHEESE_SLICE, createMultipleSameWeight(ExtendedSounds.CHEESE_SLICE, 1, true));
		
		add(ExtendedSounds.MILLSTONE_GRINDING, createMultipleSameWeight(ExtendedSounds.MILLSTONE_GRINDING, 2, 8));
		
		add(ExtendedSounds.MOSS_BROKEN, createFull("block/moss/place", 5, 0.93f, 0.8f, 16, "subtitles.block.generic.break"));
		add(ExtendedSounds.MOSS_PLACED, createFull("block/moss/place", 5, 0.93f, 0.8f, 16, "subtitles.block.generic.place"));
		add(ExtendedSounds.MOSS_HIT, createFull("block/moss/step", 6, 0.25f, 0.25f, 16, "subtitles.block.generic.hit​"));
		add(ExtendedSounds.MOSS_FALL, createFull("block/moss/step", 6, 0.5f, 0.5f, 16, null));
		add(ExtendedSounds.MOSS_STEP, createFull("block/moss/step", 6, 0.15f, 1.0f, 16, "subtitles.block.generic.footsteps"));
		
		add(ExtendedSounds.WIGHT_AMBIENCE, createMultipleSameWeight(ExtendedSounds.WIGHT_AMBIENCE, 4, true));
		add(ExtendedSounds.WIGHT_SPAWN, createMultipleSameWeight(ExtendedSounds.WIGHT_SPAWN, 2, true));
		add(ExtendedSounds.WIGHT_DEATH, createMultipleSameWeight(ExtendedSounds.WIGHT_DEATH, 3, true));

		add(ExtendedSounds.ROPE_BROKEN, createCustomSub("block/rope/break", 2, "subtitles.block.generic.break"));
		add(ExtendedSounds.ROPE_PLACED, createCustomSub("block/rope/place", 1, "subtitles.block.generic.place"));
		add(ExtendedSounds.ROPE_STEP, createCustomSub("block/rope/step", 2, "subtitles.block.generic.footsteps"));
		
		add(ExtendedSounds.CONQUEST_TABLE_OPEN, createMultipleSameWeight(ExtendedSounds.CONQUEST_TABLE_OPEN, 1, false));
	}

	public static SoundDefinition createSimple(RegistryObject<SoundEvent> sound) {
		return definition().subtitle("subtitles."+LotrExtendedMod.MODID+"."+sound.get().getRegistryName().getPath())
		  .with(sound(new ResourceLocation(LotrExtendedMod.MODID, sound.get().getRegistryName().getPath().replace('.', '/'))));
	}

	public static SoundDefinition createCustomPathSound(RegistryObject<SoundEvent> sound, ResourceLocation location) {
		return definition().subtitle("subtitles."+LotrExtendedMod.MODID+"."+sound.get().getRegistryName().getPath()).with(sound(location));
	}

	public static SoundDefinition createMultipleSameWeight(RegistryObject<SoundEvent> sound, int variants, boolean useSub) {
		List<Sound> sounds = new ArrayList<>();
		for(int i=0; i<variants; i++) {
			sounds.add(sound(new ResourceLocation(LotrExtendedMod.MODID, sound.get().getRegistryName().getPath().replace('.', '/')+(Integer.toString(i+1)))));
		}if(useSub) {
			return definition().subtitle("subtitles."+LotrExtendedMod.MODID+"."+sound.get().getRegistryName().getPath()).with(sounds.toArray(new Sound[0]));
		}
		return definition().with(sounds.toArray(new Sound[0]));
	}

	public static SoundDefinition createMultipleSameWeight(RegistryObject<SoundEvent> sound, int variants, int distance) {
		List<Sound> sounds = new ArrayList<>();
		for(int i=0; i<variants; i++) {
			sounds.add(sound(new ResourceLocation(LotrExtendedMod.MODID, sound.get().getRegistryName().getPath().replace('.', '/')+(Integer.toString(i+1)))).attenuationDistance(distance));
		}
		return definition().subtitle("subtitles."+LotrExtendedMod.MODID+"."+sound.get().getRegistryName().getPath())
		  .with(sounds.toArray(new Sound[0]));
	}

	public static SoundDefinition createCustomSub(String name, int variants, String subKey) {
		List<Sound> sounds = new ArrayList<>();
		for(int i=0; i<variants; i++) {
			sounds.add(sound(new ResourceLocation(LotrExtendedMod.MODID, name+(Integer.toString(i+1)))));
		}
		if(subKey!=null) {
			return definition().subtitle(subKey).with(sounds.toArray(new Sound[0]));
		}
		return definition().with(sounds.toArray(new Sound[0]));
	}

	public static SoundDefinition createFull(String name, int variants, float vol, float pitch, int distance, String subKey) {
		List<Sound> sounds = new ArrayList<>();
		for(int i=0; i<variants; i++) {
			sounds.add(sound(new ResourceLocation(LotrExtendedMod.MODID, name+(Integer.toString(i+1)))).volume(vol).pitch(pitch).attenuationDistance(distance));
		}
		if(subKey!=null) {
			return definition().subtitle(subKey).with(sounds.toArray(new Sound[0]));
		}
		return definition().with(sounds.toArray(new Sound[0]));
	}

	@Override
	public String getName() {
		return "Renewed Extended Sound Definitions Provider";
	}
}
