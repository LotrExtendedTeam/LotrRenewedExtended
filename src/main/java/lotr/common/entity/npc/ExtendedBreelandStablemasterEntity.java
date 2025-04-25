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

public class ExtendedBreelandStablemasterEntity extends ExtendedBreelandTraderEntity {

	// TODO add lettuce, turnip
	private final ExtendedTradeEntries BREE_STABLEMASTER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			new ExtendedTradeEntry(new ItemStack(Items.WHEAT, 3), 1),
			new ExtendedTradeEntry(new ItemStack(Items.SUGAR, 3), 1),
			new ExtendedTradeEntry(new ItemStack(Items.CARROT), 1),
			new ExtendedTradeEntry(new ItemStack(Items.APPLE), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.GREEN_APPLE.get()), 2),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.PLUM.get()), 2),
			new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 4),
			new ExtendedTradeEntry(new ItemStack(Items.HAY_BLOCK), 3)
	});

	private final ExtendedTradeEntries BREE_STABLEMASTER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.LEATHER_HAT.get()), 10),
			new ExtendedTradeEntry(new ItemStack(Items.STRING), 2),
			new ExtendedTradeEntry(new ItemStack(Items.SADDLE), 10),
			new ExtendedTradeEntry(new ItemStack(Items.LEAD), 8),
			new ExtendedTradeEntry(new ItemStack(Items.LEATHER_HORSE_ARMOR), 15),
	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandStablemasterEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);
		itemsBoughtByTrader = BREE_STABLEMASTER_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_STABLEMASTER_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(Items.LEAD));
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
