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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedBreelandBakerEntity extends ExtendedBreelandTraderEntity {

	// TODO add lemons
	private final ExtendedTradeEntries BREE_BAKER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Raw materials
			new ExtendedTradeEntry(new ItemStack(Items.WHEAT, 2), 1), 
			new ExtendedTradeEntry(new ItemStack(Items.SUGAR, 2), 1), 
			new ExtendedTradeEntry(new ItemStack(Items.EGG, 2), 1), 
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.DUCK_EGG.get(), 2), 1), 
			new ExtendedTradeEntry(new ItemStack(Items.BROWN_MUSHROOM), 2),
			new ExtendedTradeEntry(new ItemStack(Items.RED_MUSHROOM), 2),
			// Used stuff
			new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_SYRUP.get()), 2), 
			new ExtendedTradeEntry(new ItemStack(Items.MILK_BUCKET), 4), 
			new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 3), 
			new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 4),
			// Ingrediants
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.BLUEBERRY.get()), 1),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.BLACKBERRY.get()), 1),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CRANBERRY.get()), 1),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.ELDERBERRY.get()), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CHERRY.get()), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.GREEN_APPLE.get()), 1),
			new ExtendedTradeEntry(new ItemStack(Items.APPLE), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.SALT.get()), 10),
			// Misc
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_PLATE.get()), 2)
	});

	// TODO add corn bread, mushroom pie, berry pie, lemon cake
	private final ExtendedTradeEntries BREE_BAKER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Food
			new ExtendedTradeEntry(new ItemStack(Items.BREAD), 5), 
			new ExtendedTradeEntry(new ItemStack(Items.PUMPKIN_PIE), 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CHERRY_PIE.get()), 8),
			new ExtendedTradeEntry(new ItemStack(Items.CAKE), 12), 
			new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_CRUMBLE.get()), 12), 
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.HOBBIT_PANCAKE.get()), 10), 
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get()), 11), 
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER.get()), 12),
			// Silverware
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FINE_PLATE.get()), 4), 
			new ExtendedTradeEntry(new ItemStack(LOTRItems.STONEWARE_PLATE.get()), 2), 
			new ExtendedTradeEntry(new ItemStack(LOTRItems.WOODEN_PLATE.get()), 2)
	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandBakerEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);

		itemsBoughtByTrader = BREE_BAKER_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_BAKER_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(Items.BREAD.getItem()));
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
