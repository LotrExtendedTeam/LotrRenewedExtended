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

public class ExtendedBreelandCheesemongerEntity extends ExtendedBreelandTraderEntity {

	// TODO add lemons
	private final ExtendedTradeEntries BREE_CHEESEMONGER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Raw materials
			new ExtendedTradeEntry(new ItemStack(LOTRItems.SALT.get()), 10),
			new ExtendedTradeEntry(new ItemStack(Items.BROWN_MUSHROOM), 2),
			new ExtendedTradeEntry(new ItemStack(Items.RED_MUSHROOM), 2),
			new ExtendedTradeEntry(new ItemStack(Items.MILK_BUCKET), 4),
			// Tools
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.IRON_DAGGER.get()), 3),
			new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 3),
			new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 4),
	});

	private final ExtendedTradeEntries BREE_CHEESEMONGER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Food
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CHEESE_CURDS.get()), 5), 
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CHEESE_SLICE.get()), 8), 
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CHEESE_WHEEL.get()), 50),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.AGED_CHEESE_SLICE.get()), 12), 
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.AGED_CHEESE_WHEEL.get()), 60),
	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandCheesemongerEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);

		itemsBoughtByTrader = BREE_CHEESEMONGER_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_CHEESEMONGER_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(ExtendedItems.CHEESE_SLICE.get()));
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
