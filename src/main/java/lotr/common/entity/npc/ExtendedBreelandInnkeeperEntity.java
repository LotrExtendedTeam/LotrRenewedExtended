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

public class ExtendedBreelandInnkeeperEntity extends ExtendedBreelandTraderEntity {

	// TODO add raw venison, leek
	private final ExtendedTradeEntries BREE_INNKEEPER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Raw Food
			new ExtendedTradeEntry(new ItemStack(Items.BEEF), 3),
			new ExtendedTradeEntry(new ItemStack(Items.CHICKEN), 3),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.RAW_DUCK.get()), 3),
			new ExtendedTradeEntry(new ItemStack(Items.MUTTON), 3),
			new ExtendedTradeEntry(new ItemStack(Items.RABBIT), 3),
			new ExtendedTradeEntry(new ItemStack(Items.POTATO), 1),
			new ExtendedTradeEntry(new ItemStack(Items.SALMON), 2), 
			new ExtendedTradeEntry(new ItemStack(Items.COD), 2),
			new ExtendedTradeEntry(new ItemStack(Items.WHEAT, 2), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PIPEWEED_LEAF.get()), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.SALT.get()), 10),
			// Raw silverware
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_MUG.get()), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_PLATE.get()), 1),
			// Tools
			new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 3),
			new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 4),
			new ExtendedTradeEntry(new ItemStack(Items.COD_BUCKET), 6),
			new ExtendedTradeEntry(new ItemStack(Items.SALMON_BUCKET), 6),
	});

	// TODO add mushroom pie, leek soup, cooked venison
	private final ExtendedTradeEntries BREE_INNKEEPER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Food
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_BEEF), 7), 
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_CHICKEN), 6),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.DUCK.get()), 6),
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_MUTTON), 7), 
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_PORKCHOP), 7),
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_RABBIT), 6), 
			new ExtendedTradeEntry(new ItemStack(Items.BREAD), 5), 
			new ExtendedTradeEntry(new ItemStack(Items.BAKED_POTATO, 2), 7),
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_SALMON), 6), 
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_COD), 6),
			new ExtendedTradeEntry(new ItemStack(Items.MUSHROOM_STEW), 10),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.GAMMON.get()), 7), 
			// Drinks
			new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_JUICE.get()), 6),
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
			// Silverware
			new ExtendedTradeEntry(new ItemStack(LOTRItems.WOODEN_MUG.get()), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CERAMIC_MUG.get()), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.WOODEN_CUP.get()), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.WOODEN_PLATE.get()), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.STONEWARE_PLATE.get()), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FINE_PLATE.get()), 4),
			// Smoking
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PIPEWEED.get(), 4), 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.SMOKING_PIPE.get()), 25),
	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandInnkeeperEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);

		itemsBoughtByTrader = BREE_INNKEEPER_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_INNKEEPER_SOLD.generateTrades();
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
