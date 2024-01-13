package lotr.common.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ExtendedSoundDefinitionsGenerator extends SoundDefinitionsProvider{

	protected ExtendedSoundDefinitionsGenerator(DataGenerator generator, String modId, ExistingFileHelper helper) {
		super(generator, modId, helper);
	}

	@Override
	public void registerSounds() {
//		add(ExtendedSoundEvents.HORN_BLOW, definition()
//				.subtitle("subtitles.lotr.item.horn")
//				.with(ExtendedSoundEvents.HORN_BLOW.getLocation().toString())
//				);
	}

	@Override
	public String getName() {
		return "Renewed Extended Sound Definitions Provider";
	}
}
