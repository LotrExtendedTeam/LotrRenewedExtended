package lotr.common.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedSoundEvents {
	private static final List<SoundEvent> toRegister = new ArrayList<SoundEvent>();

	public static final SoundEvent HORN_BLOW = preRegSound("item.horn");

	public static void register() {
		toRegister.forEach((snd) -> {
			ForgeRegistries.SOUND_EVENTS.register(snd);
		});
	}

	private static SoundEvent preRegSound(String key) {
		ResourceLocation res = new ResourceLocation("lotr", key);
		SoundEvent snd = (SoundEvent) (new SoundEvent(res)).setRegistryName(res);
		toRegister.add(snd);
		return snd;
	}
}
