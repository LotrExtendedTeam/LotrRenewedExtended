package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
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

public class ExtendedBreelandPotterEntity extends ExtendedBreelandTraderEntity {

	private final ExtendedTradeEntries BREE_POTTER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Raw materials
			new ExtendedTradeEntry(new ItemStack(Items.CLAY_BALL, 4), 1, 1), 
			new ExtendedTradeEntry(new ItemStack(Items.CLAY), 1, 1), 
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(new ItemStack(Items.BLACK_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.BLUE_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.BROWN_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.CYAN_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.GRAY_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.GREEN_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.LIGHT_BLUE_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.LIGHT_GRAY_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.LIME_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.MAGENTA_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.ORANGE_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.PINK_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.PURPLE_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.RED_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.WHITE_DYE), 1),
					new ExtendedTradeEntry(new ItemStack(Items.YELLOW_DYE), 1)}),
			// Used stuff
			new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 2, 4), 
			new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 3, 5),
			// Tools
			new ExtendedTradeEntry(new ItemStack(Items.WOODEN_SHOVEL), 1, 2),
			new ExtendedTradeEntry(new ItemStack(Items.STONE_SHOVEL), 1, 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SHOVEL.get()), 2, 6),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_SHOVEL), 2, 8)
	});

	private final ExtendedTradeEntries BREE_POTTER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Produced stuff
			new ExtendedTradeEntry(new ItemStack(Items.TERRACOTTA, 4), 4, 6),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(new ItemStack(Items.BLACK_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.BLUE_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.BROWN_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.CYAN_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.GRAY_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.GREEN_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.LIGHT_BLUE_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.LIGHT_GRAY_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.LIME_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.MAGENTA_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.ORANGE_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.PINK_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.PURPLE_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.RED_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.WHITE_TERRACOTTA, 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(Items.YELLOW_TERRACOTTA, 4), 4, 6)}),
			new ExtendedTradeEntry(new ItemStack(Items.FLOWER_POT), 5, 8),
			new ExtendedTradeEntry(new ItemStack(Items.BRICK, 2), 2, 4), 
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.LARGE_POT.get()), 6, 8),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.LARGE_FLOWER_POT.get()), 6, 8), 
			new ExtendedTradeEntry(new ItemStack(Items.BRICKS, 4), 5, 7),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.WATTLE_AND_DAUB.get(), 4), 5, 8), 
			new ExtendedTradeEntry(new ItemStack(LOTRItems.WATTLE_AND_DAUB_PILLAR.get(), 3), 3, 6),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.CROSSED_WATTLE_AND_DAUB.get(), 4), 6, 8), 
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_MUG.get()), 2, 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_PLATE.get(), 2), 1, 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CERAMIC_MUG.get()), 3, 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.STONEWARE_PLATE.get(), 2), 2, 4),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_TILING.get(), 4), 4, 6),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(new ItemStack(LOTRItems.BLACK_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.BROWN_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.CYAN_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.GRAY_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.GREEN_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.LIGHT_BLUE_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.LIGHT_GRAY_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.LIME_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.MAGENTA_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.ORANGE_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.PINK_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.PURPLE_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.RED_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.WHITE_CLAY_TILING.get(), 4), 4, 6),
					new ExtendedTradeEntry(new ItemStack(LOTRItems.YELLOW_CLAY_TILING.get(), 4), 4, 6)})

	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandPotterEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);

		itemsBoughtByTrader = BREE_POTTER_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_POTTER_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(Items.CLAY_BALL));
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
