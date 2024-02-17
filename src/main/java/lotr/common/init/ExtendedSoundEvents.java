package lotr.common.init;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrExtended.common.datagen.DataGenValueHolder;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedSoundEvents {
	private static final List<SoundEvent> toRegister = new ArrayList<SoundEvent>();

	public static final SoundEvent HORN_BLOW = preRegSound("item.horn");
	public static final SoundEvent TRADE_SOUND_1 = preRegSound("event.trade1");
	public static final SoundEvent TRADE_SOUND_2 = preRegSound("event.trade2");
	public static final SoundEvent TRADE_SOUND_3 = preRegSound("event.trade3");
	public static final SoundEvent TRADE_SOUND_4 = preRegSound("event.trade4");

	public static void register() {
		toRegister.forEach((snd) -> {
			ForgeRegistries.SOUND_EVENTS.register(snd);
		});
	}

	private static SoundEvent preRegSound(String key) {
		ResourceLocation res = new ResourceLocation("lotr", key);
		SoundEvent snd = (SoundEvent) (new SoundEvent(res)).setRegistryName(res);
		toRegister.add(snd);
		if(DatagenModLoader.isRunningDataGen()) {
			if (key.contains("event.trade")) {
				DataGenValueHolder.soundEventHolder.add("subtitles.lotr.event.trade");
			}
			else {
				DataGenValueHolder.soundEventHolder.add("subtitles.lotr."+key);
			}
		}
		return snd;
	}
}
