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

public class ExtendedBreelandButcherEntity extends ExtendedBreelandTraderEntity {

	private final ExtendedTradeEntries BREE_BUTCHER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Materials
			new ExtendedTradeEntry(new ItemStack(Items.WHEAT, 3), 1), 
			new ExtendedTradeEntry(new ItemStack(LOTRItems.SALT.get()), 10),
			new ExtendedTradeEntry(new ItemStack(Items.STRING, 3), 1),
			// Misc
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.IRON_DAGGER.get()), 3),
			new ExtendedTradeEntry(new ItemStack(Items.LEAD), 4),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_INGOT), 3),
	});

	// TODO add raw venison
	private final ExtendedTradeEntries BREE_BUTCHER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Meat cuts
			new ExtendedTradeEntry(new ItemStack(Items.BEEF), 5), 
			new ExtendedTradeEntry(new ItemStack(Items.CHICKEN), 5), 
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.RAW_DUCK.get()), 4), 
			new ExtendedTradeEntry(new ItemStack(LOTRItems.GAMMON.get()), 6), 
			new ExtendedTradeEntry(new ItemStack(Items.MUTTON), 5), 
			new ExtendedTradeEntry(new ItemStack(Items.PORKCHOP), 5), 
			new ExtendedTradeEntry(new ItemStack(Items.RABBIT), 4), 
			// By-products
			new ExtendedTradeEntry(new ItemStack(Items.LEATHER), 3), 
			new ExtendedTradeEntry(new ItemStack(Items.FEATHER), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.HORN.get()), 6),
	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandButcherEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);

		itemsBoughtByTrader = BREE_BUTCHER_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_BUTCHER_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(Items.CHICKEN));
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
