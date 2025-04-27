package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem.Potency;
import lotr.common.util.DrinkUtils;
import lotr.common.util.representation.ExtendedMultitradeEntry;
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

public class ExtendedBreelandBrewerEntity extends ExtendedBreelandTraderEntity {

	private final ExtendedTradeEntries BREE_BREWER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Raw Ingrediants
			new ExtendedTradeEntry(new ItemStack(Items.WHEAT, 2), 3),
			new ExtendedTradeEntry(new ItemStack(Items.APPLE), 1),
			new ExtendedTradeEntry(new ItemStack(Items.POTATO, 2), 1),
			new ExtendedTradeEntry(new ItemStack(Items.CARROT, 2), 1),
			new ExtendedTradeEntry(new ItemStack(Items.MELON_SLICE, 3), 1),
			new ExtendedTradeEntry(new ItemStack(Items.SUGAR, 2), 1),
			new ExtendedTradeEntry(new ItemStack(Items.SUGAR_CANE, 2), 1),
			new ExtendedTradeEntry(new ItemStack(Items.HONEY_BOTTLE), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.GREEN_APPLE.get()), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PEAR.get()), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CHERRY.get()), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_SYRUP.get()), 1), 
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.PLUM.get()), 1),
			// Tools
			new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 3),
			new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 4)
	});

	// TODO add berry juices and kegs
	private final ExtendedTradeEntries BREE_BREWER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Drinks
			new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_JUICE.get()), 6),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.SWEET_BERRY_JUICE.get()), 6),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.ALE.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.ALE.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.ALE.get(), Potency.STRONG), 8, 13)}),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.CIDER.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.CIDER.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.CIDER.get(), Potency.STRONG), 8, 13)}),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.PERRY.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.PERRY.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.PERRY.get(), Potency.STRONG), 8, 13)}),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MEAD.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MEAD.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MEAD.get(), Potency.STRONG), 8, 13)}),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MAPLE_BEER.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MAPLE_BEER.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MAPLE_BEER.get(), Potency.STRONG), 8, 13)}),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(ExtendedItems.PLUM_KVASS.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(ExtendedItems.PLUM_KVASS.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(ExtendedItems.PLUM_KVASS.get(), Potency.STRONG), 8, 13)}),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.CHERRY_LIQUEUR.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.CHERRY_LIQUEUR.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.CHERRY_LIQUEUR.get(), Potency.STRONG), 8, 13)}),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MELON_LIQUEUR.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MELON_LIQUEUR.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MELON_LIQUEUR.get(), Potency.STRONG), 8, 13)}),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.RUM.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.RUM.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.RUM.get(), Potency.STRONG), 8, 13)}),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.VODKA.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.VODKA.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.VODKA.get(), Potency.STRONG), 8, 13)}),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.CARROT_WINE.get(), Potency.LIGHT), 5, 8), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.CARROT_WINE.get(), Potency.MODERATE), 6, 10), 
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.CARROT_WINE.get(), Potency.STRONG), 8, 13)}),
			// Vessels
			new ExtendedTradeEntry(new ItemStack(LOTRItems.WOODEN_MUG.get()), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CERAMIC_MUG.get()), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.WOODEN_CUP.get()), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.COPPER_GOBLET.get()), 10),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.SILVER_GOBLET.get()), 25),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.GOLDEN_GOBLET.get()), 50),
	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandBrewerEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);
		itemsBoughtByTrader = BREE_BREWER_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_BREWER_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(LOTRItems.WOODEN_MUG.get()));
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
