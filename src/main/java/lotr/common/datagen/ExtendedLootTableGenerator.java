package lotr.common.datagen;

import net.minecraft.data.LootTableProvider;
import net.minecraft.data.DataGenerator;

public class ExtendedLootTableGenerator extends LootTableProvider {

	public ExtendedLootTableGenerator(DataGenerator generator) {
		super(generator);
	}


	@Override
	public String getName() {
		return "Renewed Extended Loot Tables provider";
	}

}
