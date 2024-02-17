package com.github.maximuslotro.lotrExtended.common.datagen;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrExtended.LotrExtendedMod;
import com.github.maximuslotro.lotrExtended.common.init.ExtendedSounds;

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
	}

	public static SoundDefinition createSimple(RegistryObject<SoundEvent> sound) {
		return definition().subtitle("subtitles."+LotrExtendedMod.MODID+"."+sound.get().getRegistryName().getPath())
		  .with(sound(new ResourceLocation(LotrExtendedMod.MODID, sound.get().getRegistryName().getPath().replace('.', '/'))));
	}

	public static SoundDefinition createMultipleSameWeight(RegistryObject<SoundEvent> sound, int variants) {
		List<Sound> sounds = new ArrayList<>();
		for(int i=0; i<variants; i++) {
			sounds.add(sound(new ResourceLocation(LotrExtendedMod.MODID, sound.get().getRegistryName().getPath().replace('.', '/')+(Integer.toString(i+1)))));
		}
		return definition().subtitle("subtitles."+LotrExtendedMod.MODID+"."+sound.get().getRegistryName().getPath())
		  .with(sounds.toArray(new Sound[0]));
	}

	@Override
	public String getName() {
		return "Renewed Extended Sound Definitions Provider";
	}
}
