package lotr.common.datagen;

import com.github.maximuslotro.lotroExtended.common.loot.ExtendedLootModifiers;
import com.github.maximuslotro.lotroExtended.common.loot.WargFurColorizer;

import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ExtendedGlobalLootModifierGenerator extends GlobalLootModifierProvider{

	public ExtendedGlobalLootModifierGenerator(DataGenerator gen, String modid) {
		super(gen, modid);
	}

	@Override
	protected void start() {
		add("warg_fur_colorizer", ExtendedLootModifiers.WARG_FUR.get(), new WargFurColorizer(new ILootCondition[0]));
	}

}
