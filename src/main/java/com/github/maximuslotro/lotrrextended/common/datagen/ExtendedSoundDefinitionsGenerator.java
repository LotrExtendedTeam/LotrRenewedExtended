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

		add(ExtendedSounds.TRADE, createMultipleSameWeight(ExtendedSounds.TRADE, 4));
		
		add(ExtendedSounds.DUCK_AMBIANT, createMultipleSameWeight(ExtendedSounds.DUCK_AMBIANT, 4));
		add(ExtendedSounds.DUCK_HURT, createMultipleSameWeight(ExtendedSounds.DUCK_HURT, 1));
		add(ExtendedSounds.DUCK_DEATH, createMultipleSameWeight(ExtendedSounds.DUCK_DEATH, 1));
		add(ExtendedSounds.DUCK_STEP, createMultipleSameWeight(ExtendedSounds.DUCK_STEP, 3));
		add(ExtendedSounds.DUCK_EGG, createCustomPathSound(ExtendedSounds.DUCK_EGG, new ResourceLocation("mob/chicken/plop")));

		add(ExtendedSounds.BASKET_OPEN, createMultipleSameWeight(ExtendedSounds.BASKET_OPEN, 1));
		add(ExtendedSounds.BASKET_CLOSE, createMultipleSameWeight(ExtendedSounds.BASKET_CLOSE, 1));

		add(ExtendedSounds.STONE_CHEST_OPEN, createMultipleSameWeight(ExtendedSounds.STONE_CHEST_OPEN, 1));
		add(ExtendedSounds.STONE_CHEST_CLOSE, createMultipleSameWeight(ExtendedSounds.STONE_CHEST_CLOSE, 1));

		add(ExtendedSounds.CHEESE_SLICE, createMultipleSameWeight(ExtendedSounds.CHEESE_SLICE, 1));
		
		add(ExtendedSounds.MILLSTONE_GRINDING, createMultipleSameWeight(ExtendedSounds.MILLSTONE_GRINDING, 2, 8));
	}

	public static SoundDefinition createSimple(RegistryObject<SoundEvent> sound) {
		return definition().subtitle("subtitles."+LotrExtendedMod.MODID+"."+sound.get().getRegistryName().getPath())
		  .with(sound(new ResourceLocation(LotrExtendedMod.MODID, sound.get().getRegistryName().getPath().replace('.', '/'))));
	}

	public static SoundDefinition createCustomPathSound(RegistryObject<SoundEvent> sound, ResourceLocation location) {
		return definition().subtitle("subtitles."+LotrExtendedMod.MODID+"."+sound.get().getRegistryName().getPath()).with(sound(location));
	}

	public static SoundDefinition createMultipleSameWeight(RegistryObject<SoundEvent> sound, int variants) {
		List<Sound> sounds = new ArrayList<>();
		for(int i=0; i<variants; i++) {
			sounds.add(sound(new ResourceLocation(LotrExtendedMod.MODID, sound.get().getRegistryName().getPath().replace('.', '/')+(Integer.toString(i+1)))));
		}
		return definition().subtitle("subtitles."+LotrExtendedMod.MODID+"."+sound.get().getRegistryName().getPath())
		  .with(sounds.toArray(new Sound[0]));
	}

	public static SoundDefinition createMultipleSameWeight(RegistryObject<SoundEvent> sound, int variants, int distance) {
		List<Sound> sounds = new ArrayList<>();
		for(int i=0; i<variants; i++) {
			sounds.add(sound(new ResourceLocation(LotrExtendedMod.MODID, sound.get().getRegistryName().getPath().replace('.', '/')+(Integer.toString(i+1)))).attenuationDistance(distance));
		}
		return definition().subtitle("subtitles."+LotrExtendedMod.MODID+"."+sound.get().getRegistryName().getPath())
		  .with(sounds.toArray(new Sound[0]));
	}

	@Override
	public String getName() {
		return "Renewed Extended Sound Definitions Provider";
	}
}
