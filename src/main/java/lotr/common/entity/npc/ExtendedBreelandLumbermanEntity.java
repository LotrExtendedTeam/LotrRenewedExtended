package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import lotr.common.util.representation.ExtendedTradeEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedBreelandLumbermanEntity extends ExtendedBreelandTraderEntity {

	private final ExtendedTradeEntries BREE_LUMBERMAN_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Materials
			new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 3),
			new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 4),
			new ExtendedTradeEntry(new ItemStack(Items.BONE_MEAL, 6), 1),
			// Tools
			new ExtendedTradeEntry(new ItemStack(Items.STONE_AXE), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_AXE.get()), 6),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_AXE), 8),
			new ExtendedTradeEntry(new ItemStack(Items.SHEARS), 5)
	});

	// TODO add willow wood
	private final ExtendedTradeEntries BREE_LUMBERMAN_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Special
			new ExtendedTradeEntry(new ItemStack(Items.PETRIFIED_OAK_SLAB), 1000),
			new ExtendedTradeEntry(new ItemStack(Items.STICK, 4), 1),
			new ExtendedTradeEntry(new ItemStack(Items.APPLE), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.GREEN_APPLE.get()), 3),
			// Oak
			new ExtendedTradeEntry(new ItemStack(Items.OAK_LOG), 2),
			new ExtendedTradeEntry(new ItemStack(Items.OAK_PLANKS, 4), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.OAK_BEAM.get(), 3), 2),
			new ExtendedTradeEntry(new ItemStack(Items.OAK_LEAVES), 2),
			new ExtendedTradeEntry(new ItemStack(Items.OAK_WOOD), 3),
			new ExtendedTradeEntry(new ItemStack(Items.OAK_SAPLING), 4),
			// Spruce
			new ExtendedTradeEntry(new ItemStack(Items.SPRUCE_LOG), 4),
			new ExtendedTradeEntry(new ItemStack(Items.SPRUCE_PLANKS, 4), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.SPRUCE_BEAM.get(), 3), 4),
			new ExtendedTradeEntry(new ItemStack(Items.SPRUCE_LEAVES), 4),
			new ExtendedTradeEntry(new ItemStack(Items.SPRUCE_WOOD), 5),
			new ExtendedTradeEntry(new ItemStack(Items.SPRUCE_SAPLING), 8),
			// Birch
			new ExtendedTradeEntry(new ItemStack(Items.BIRCH_LOG), 3),
			new ExtendedTradeEntry(new ItemStack(Items.BIRCH_PLANKS, 4), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BIRCH_BEAM.get(), 3), 3),
			new ExtendedTradeEntry(new ItemStack(Items.BIRCH_LEAVES), 3),
			new ExtendedTradeEntry(new ItemStack(Items.BIRCH_WOOD), 4),
			new ExtendedTradeEntry(new ItemStack(Items.BIRCH_SAPLING), 6),
			// Dark Oak
			new ExtendedTradeEntry(new ItemStack(Items.DARK_OAK_LOG), 6),
			new ExtendedTradeEntry(new ItemStack(Items.DARK_OAK_PLANKS, 4), 6),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.DARK_OAK_BEAM.get(), 3), 6),
			new ExtendedTradeEntry(new ItemStack(Items.DARK_OAK_LEAVES), 6),
			new ExtendedTradeEntry(new ItemStack(Items.DARK_OAK_WOOD), 7),
			new ExtendedTradeEntry(new ItemStack(Items.DARK_OAK_SAPLING), 12),
			// Pine
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PINE_LOG.get()), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PINE_PLANKS.get(), 4), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PINE_BEAM.get(), 3), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PINE_LEAVES.get()), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PINE_WOOD.get()), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PINE_SAPLING.get()), 8),
			// Apple
			new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_LOG.get()), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_PLANKS.get(), 4), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_BEAM.get(), 3), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_LEAVES.get()), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_WOOD.get()), 6),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_SAPLING.get()), 10),
			// Pear
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PEAR_LOG.get()), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PEAR_PLANKS.get(), 4), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PEAR_BEAM.get(), 3), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PEAR_LEAVES.get()), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PEAR_WOOD.get()), 6),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PEAR_SAPLING.get()), 10),
			// Beech
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BEECH_LOG.get()), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BEECH_PLANKS.get(), 4), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BEECH_BEAM.get(), 3), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BEECH_LEAVES.get()), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BEECH_WOOD.get()), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BEECH_SAPLING.get()), 6),
			// Holly
			new ExtendedTradeEntry(new ItemStack(LOTRItems.HOLLY_LOG.get()), 6),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.HOLLY_PLANKS.get(), 4), 6),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.HOLLY_BEAM.get(), 3), 6),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.HOLLY_LEAVES.get()), 6),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.HOLLY_WOOD.get()), 7),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.HOLLY_SAPLING.get()), 12),
			// Maple
			new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_LOG.get()), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_PLANKS.get(), 4), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_BEAM.get(), 3), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_LEAVES.get()), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_WOOD.get()), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_SAPLING.get()), 8),
			// Larch
			new ExtendedTradeEntry(new ItemStack(LOTRItems.LARCH_LOG.get()), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.LARCH_PLANKS.get(), 4), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.LARCH_BEAM.get(), 3), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.LARCH_LEAVES.get()), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.LARCH_WOOD.get()), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.LARCH_SAPLING.get()), 8),
			// Chestnut
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CHESTNUT_LOG.get()), 3),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CHESTNUT_PLANKS.get(), 4), 3),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CHESTNUT_BEAM.get(), 3), 3),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CHESTNUT_LEAVES.get()), 3),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CHESTNUT_WOOD.get()), 4),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CHESTNUT_SAPLING.get()), 6),
			// Fir
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FIR_LOG.get()), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FIR_PLANKS.get(), 4), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FIR_BEAM.get(), 3), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FIR_LEAVES.get()), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FIR_WOOD.get()), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FIR_SAPLING.get()), 8),
			// Willow
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.WILLOW_LOG.get()), 4),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.WILLOW_PLANKS.get(), 4), 4),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.WILLOW_BEAM.get(), 3), 4),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.WILLOW_LEAVES.get()), 4),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.WILLOW_WOOD.get()), 5),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.WILLOW_SAPLING.get()), 8),
			// Aspen
			new ExtendedTradeEntry(new ItemStack(LOTRItems.ASPEN_LOG.get()), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.ASPEN_PLANKS.get(), 4), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.ASPEN_BEAM.get(), 3), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.ASPEN_LEAVES.get()), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.ASPEN_WOOD.get()), 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.ASPEN_SAPLING.get()), 6),
			// Plum
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.PLUM_LOG.get()), 5),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.PLUM_PLANKS.get(), 4), 5),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.PLUM_BEAM.get(), 3), 5),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.PLUM_LEAVES.get()), 5),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.PLUM_WOOD.get()), 6),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.PLUM_SAPLING.get()), 10),

	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandLumbermanEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);
		itemsBoughtByTrader = BREE_LUMBERMAN_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_LUMBERMAN_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.IRON_AXE));
		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));
		return spawnData;
	}

	@Override
	public List<ExtendedTrade> getBuys() {
		return itemsBoughtByTrader;
	}

	@Override
	public List<ExtendedTrade> getSells() {
		return itemsSoldByTrader;
	}

	@Override
	public void setBuys(ArrayList<ExtendedTrade> trades) {
		itemsBoughtByTrader = trades;
	}

	@Override
	public void setSells(ArrayList<ExtendedTrade> trades) {
		itemsSoldByTrader = trades;
	}
}
