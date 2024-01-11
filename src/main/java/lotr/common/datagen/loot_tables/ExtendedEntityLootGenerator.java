package lotr.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;

import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem;
import lotr.common.loot.functions.ExtendedSetWargFurType;
import lotr.common.loot.functions.SetNPCDrinkPotency;
import lotr.common.loot.functions.SetPouchColorFromEntityFaction;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class ExtendedEntityLootGenerator extends EntityLootTables  {

	@Override
	protected void addTables() {
		ResourceLocation wargFur = makeQuickyBasePart("warg_fur", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.FUR.get(), 1, 3)
				.apply(ExtendedSetWargFurType.setWargFurTypeBuilder())));
		ResourceLocation orcBones = makeQuickyBasePart("orc_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.ORC_BONE.get(), 0, 1)));
		ResourceLocation manBones = makeQuickyBasePart("man_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(Items.BONE, 0, 1)));
		ResourceLocation elfBones = makeQuickyBasePart("elf_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.ELF_BONE.get(), 0, 1)));
		ResourceLocation hobbitBones = makeQuickyBasePart("hobbit_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.HOBBIT_BONE.get(), 0, 1)));
		ResourceLocation dwarfBones = makeQuickyBasePart("dwarf_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.DWARF_BONE.get(), 0, 1)));
		ResourceLocation wargBones = makeQuickyBasePart("warg_bones", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.WARG_BONE.get(), 0, 3)));
		ResourceLocation arrows = makeQuickyBasePart("arrows", LootTable.lootTable().withPool(genSingleQuickyBuilder(Items.ARROW, 1, 2)));
		ResourceLocation lembas = makeQuickyBasePart("elf_lembas", LootTable.lootTable().withPool(genSingleQuickyBuilder(LOTRItems.LEMBAS.get())));
		ResourceLocation rairDwarfDrops = makeQuickyBasePart("dwarf_rare_drops", LootTable.lootTable().withPool(genSingleQuickyBuilder(Items.IRON_INGOT)
				.add(genQuickyItemBuilder(LOTRItems.DWARVEN_STEEL_INGOT.get()))
				.add(genQuickyItemBuilder(Items.GOLD_NUGGET, 1, 3))
				.add(genQuickyItemBuilder(LOTRItems.SILVER_NUGGET.get(), 1, 3))));
		ResourceLocation pouch = makeQuickyBasePart("pouch", LootTable.lootTable().withPool(makeQuickyPoolBuilder(1)
				.add(genQuickyItemBuilder(LOTRItems.SMALL_POUCH.get()).setWeight(6))
				.add(genQuickyItemBuilder(LOTRItems.MEDIUM_POUCH.get()).setWeight(3))
				.add(genQuickyItemBuilder(LOTRItems.LARGE_POUCH.get()).setWeight(1))
				.apply(SetPouchColorFromEntityFaction.setPouchColorFromEntityFactionBuilder(0.5F))
				.when(KilledByPlayer.killedByPlayer())
				.when(RandomChance.randomChance(0.016666F))));
		ResourceLocation orcBase = makeQuickyBasePart("orc", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(orcBones))
				.withPool(addAnotherQuickyPoolRefrence(pouch))
				.withPool(genSingleQuickyBuilder(Items.ROTTEN_FLESH, 0, 2))
				.withPool(genSingleQuickyBuilder(LOTRItems.MAGGOTY_BREAD.get(), 1, 2)
						.when(RandomChance.randomChance(0.1F)))
				.withPool(createQuickyDrinkBuilder(LOTRItems.ORC_DRAUGHT.get())
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F))));
		ResourceLocation orcWithOrcSteel = makeQuickyBasePart("orc_with_orc_steel", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(orcBase))
				.withPool(genSingleQuickyBuilder(LOTRItems.ORC_STEEL_INGOT.get(), 1, 2)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F))));
		ResourceLocation orcWithUrukSteel = makeQuickyBasePart("orc_with_uruk_steel", LootTable.lootTable()
				.withPool(addAnotherQuickyPoolRefrence(orcBase))
				.withPool(genSingleQuickyBuilder(LOTRItems.URUK_STEEL_INGOT.get(), 1, 2)
						.when(KilledByPlayer.killedByPlayer())
						.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.025F, 0.008F))));
	}

	public ResourceLocation makeQuickyBasePart(String name, Builder builder) {
		ResourceLocation loc = new ResourceLocation("lotr", "entities/common/parts/" + name);
		add(loc, builder);
		return loc;
	}
	@Override
	protected Iterable<EntityType<?>> getKnownEntities() {
		List<EntityType<?>> filtered = new ArrayList<>();
		//for(RegistryObject<EntityType<?>> bl : LOTREntities.ENTITIES.getEntries()) {
		//	filtered.add(bl.get());
		//}
        return filtered.stream()::iterator;
	}

	public LootPool.Builder makeQuickyPoolBuilder(int numRoles){
		return LootPool.lootPool().setRolls(ConstantRange.exactly(numRoles));
	}
	
	public LootPool.Builder genSingleQuickyBuilder(IItemProvider item, float minCount, float maxCount){
		return makeQuickyPoolBuilder(1).add(genQuickyItemBuilder(item, minCount, maxCount));
	}

	public LootPool.Builder genSingleQuickyBuilder(IItemProvider item){
		return makeQuickyPoolBuilder(1).add(genQuickyItemBuilder(item));
	}

	public ItemLootEntry.Builder<?> genQuickyItemBuilder(IItemProvider item){
		return ItemLootEntry.lootTableItem(item);
	}
	
	public ItemLootEntry.Builder<?> genQuickyItemBuilder(IItemProvider item, float minCount, float maxCount){
		return genQuickyItemBuilder(item, minCount, maxCount, 0.0F, 1.0F);
	}

	public LootPool.Builder addAnotherQuickyPoolRefrence(ResourceLocation quickyLocation) {
		return makeQuickyPoolBuilder(1).add(TableLootEntry.lootTableReference(quickyLocation));
	}
	
	public ItemLootEntry.Builder<?> genQuickyItemBuilder(IItemProvider item, float minCount, float maxCount, float minloot, float maxloot){
		return genQuickyItemBuilder(item)
				.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))
				.apply(SetCount.setCount(RandomValueRange.between(minCount, maxCount)));
	}
	
	public LootPool.Builder createQuickyDrinkBuilder(IItemProvider item) {
		if ((item.asItem() instanceof VesselDrinkItem) && ((VesselDrinkItem) item.asItem()).hasPotencies) {
			return makeQuickyPoolBuilder(1).add(genQuickyItemBuilder(item).apply(SetNPCDrinkPotency.setNPCDrinkPotencyBuilder()));
		}
		throw new IllegalArgumentException("INCORRECT SYNTAX WITH DRINK ITEM: "+item.asItem().getRegistryName());
	}
}