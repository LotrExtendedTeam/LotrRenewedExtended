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

public class ExtendedBreelandStoneMasonEntity extends ExtendedBreelandTraderEntity {

	// TODO add cooked venison
	private final ExtendedTradeEntries BREE_STONEMASON_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Materials
			new ExtendedTradeEntry(new ItemStack(Items.COAL, 2), 1), 
			new ExtendedTradeEntry(new ItemStack(Items.TORCH, 16), 2),
			new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 3),
			new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 4),
			// Tools
			new ExtendedTradeEntry(new ItemStack(Items.STONE_PICKAXE), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_PICKAXE.get()), 6),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_PICKAXE), 8),
			new ExtendedTradeEntry(new ItemStack(Items.STONE_SHOVEL), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SHOVEL.get()), 6),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_SHOVEL), 8),
			// Food
			new ExtendedTradeEntry(new ItemStack(Items.BREAD), 2),
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_BEEF), 3),
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_CHICKEN), 3),
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_MUTTON), 3),
			new ExtendedTradeEntry(new ItemStack(Items.COOKED_RABBIT), 3),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.DUCK.get()), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.PIPEWEED.get(), 2), 1),
	});

	private final ExtendedTradeEntries BREE_STONEMASON_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Stone
			new ExtendedTradeEntry(new ItemStack(Items.STONE, 8), 2),
			new ExtendedTradeEntry(new ItemStack(Items.COBBLESTONE, 8), 1),
			new ExtendedTradeEntry(new ItemStack(Items.STONE_BRICKS, 8), 1),
			new ExtendedTradeEntry(new ItemStack(Items.BRICKS, 8), 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.DRYSTONE.get(), 8), 3),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.COBBLED_DRYSTONE.get(), 8), 3),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CUT_DRYSTONE.get(), 8), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.STONE_PILLAR.get(), 4), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRICK_PILLAR.get(), 4), 5),
			// Tables
			new ExtendedTradeEntry(new ItemStack(Items.STONECUTTER), 10),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BREE_CRAFTING_TABLE.get()), 100)
	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandStoneMasonEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);
		itemsBoughtByTrader = BREE_STONEMASON_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_STONEMASON_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(Items.IRON_PICKAXE));
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
