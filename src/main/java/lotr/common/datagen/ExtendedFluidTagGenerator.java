package lotr.common.datagen;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedFluidTagGenerator extends FluidTagsProvider {

	public ExtendedFluidTagGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, LotrExtendedMod.MODID, existingFileHelper);
	}

	@Override
    protected void addTags() {
		
	}

	@Override
	public String getName() {
		return "Extended Renewed Fluid Tags Generator";
	}
}
