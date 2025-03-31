package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
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

public class ExtendedBreelandFloristEntity extends ExtendedBreelandTraderEntity {

	private final ExtendedTradeEntries BREE_INNKEEPER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Growers
			new ExtendedTradeEntry(new ItemStack(Items.BONE_MEAL, 15), 1),
			// Tools
			new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 3),
			new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 4),
			new ExtendedTradeEntry(new ItemStack(Items.STONE_HOE), 1),
			new ExtendedTradeEntry(new ItemStack(Items.WOODEN_HOE), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_HOE.get()), 6),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_HOE), 8),
			new ExtendedTradeEntry(new ItemStack(Items.STONE_SHOVEL), 1),
			new ExtendedTradeEntry(new ItemStack(Items.WOODEN_SHOVEL), 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SHOVEL.get()), 5),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_SHOVEL), 7),
	});

	private final ExtendedTradeEntries BREE_INNKEEPER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
            // Flowers
            new ExtendedTradeEntry(new ItemStack(Items.POPPY), 3),
            new ExtendedTradeEntry(new ItemStack(Items.BLUE_ORCHID), 8),
            new ExtendedTradeEntry(new ItemStack(Items.ALLIUM), 6),
            new ExtendedTradeEntry(new ItemStack(Items.AZURE_BLUET), 3),
            new ExtendedTradeEntry(new ItemStack(Items.RED_TULIP), 3),
            new ExtendedTradeEntry(new ItemStack(Items.ORANGE_TULIP), 3),
            new ExtendedTradeEntry(new ItemStack(Items.WHITE_TULIP), 3),
            new ExtendedTradeEntry(new ItemStack(Items.PINK_TULIP), 3),
            new ExtendedTradeEntry(new ItemStack(Items.OXEYE_DAISY), 3),
            new ExtendedTradeEntry(new ItemStack(Items.DANDELION), 3),
            new ExtendedTradeEntry(new ItemStack(Items.SUNFLOWER), 10),
            new ExtendedTradeEntry(new ItemStack(Items.LILAC), 8),
            new ExtendedTradeEntry(new ItemStack(Items.ROSE_BUSH), 8),
            new ExtendedTradeEntry(new ItemStack(Items.PEONY), 8),
            new ExtendedTradeEntry(new ItemStack(Items.CORNFLOWER), 4),
            new ExtendedTradeEntry(new ItemStack(Items.LILY_OF_THE_VALLEY), 5),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.MARIGOLD.get()), 3),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUEBELL.get()), 3),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.LAVENDER.get()), 3),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.YELLOW_IRIS.get()), 10),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.SHIRE_HEATHER.get()), 6)
       });

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandFloristEntity(EntityType<? extends BreeManEntity> type, World w) {
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
