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

public class ExtendedBreelandLeatherworkerEntity extends ExtendedBreelandTraderEntity {

	// TODO add lemons
	private final ExtendedTradeEntries BREE_LEATHERWORKER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Dyes
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
			// Materials
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(new ItemStack(Items.BLACK_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.BLUE_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.BROWN_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.CYAN_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.GRAY_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.GREEN_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.LIGHT_BLUE_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.LIGHT_GRAY_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.LIME_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.MAGENTA_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.ORANGE_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.PINK_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.PURPLE_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.RED_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.WHITE_WOOL), 2),
					new ExtendedTradeEntry(new ItemStack(Items.YELLOW_WOOL), 2)}),
			new ExtendedTradeEntry(new ItemStack(Items.STRING, 3), 1),
			new ExtendedTradeEntry(new ItemStack(Items.LEATHER), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FUR.get()), 2),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.BLACK_FUR.get()), 2),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.GRAY_FUR.get()), 2),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.SILVER_FUR.get()), 3),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.WHITE_FUR.get()), 50),
			new ExtendedTradeEntry(new ItemStack(Items.RABBIT_HIDE), 1),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.BROWN_PACKED_FUR.get()), 18),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.BLACK_PACKED_FUR.get()), 18),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.GRAY_PACKED_FUR.get()), 18),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.SILVER_PACKED_FUR.get()), 27),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.WHITE_PACKED_FUR.get()), 400),
			// Tools
			new ExtendedTradeEntry(new ItemStack(Items.SHEARS), 5)
	});

	// TODO add coloring to leather armor and hat, add banners, carpets, fur armor, beds
	private final ExtendedTradeEntries BREE_LEATHERWORKER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Food
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(new ItemStack(Items.BLACK_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.BLUE_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.BROWN_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.CYAN_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.GRAY_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.GREEN_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.LIGHT_BLUE_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.LIGHT_GRAY_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.LIME_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.MAGENTA_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.ORANGE_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.PINK_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.PURPLE_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.RED_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.WHITE_WOOL), 4),
					new ExtendedTradeEntry(new ItemStack(Items.YELLOW_WOOL), 4)}),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.LEATHER_HAT.get()), 10),
			new ExtendedTradeEntry(new ItemStack(Items.LEATHER_HELMET), 7),
			new ExtendedTradeEntry(new ItemStack(Items.LEATHER_CHESTPLATE), 12),
			new ExtendedTradeEntry(new ItemStack(Items.LEATHER_LEGGINGS), 11),
			new ExtendedTradeEntry(new ItemStack(Items.LEATHER_BOOTS), 6),
			new ExtendedTradeEntry(new ItemStack(Items.LEATHER_HORSE_ARMOR), 15),


	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandLeatherworkerEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);
		itemsBoughtByTrader = BREE_LEATHERWORKER_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_LEATHERWORKER_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(Items.LEATHER));
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
