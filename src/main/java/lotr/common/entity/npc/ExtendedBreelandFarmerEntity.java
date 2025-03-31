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

public class ExtendedBreelandFarmerEntity extends ExtendedBreelandTraderEntity {

	private final ExtendedTradeEntries BREE_FARMER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Farm Stuff
			new ExtendedTradeEntry(new ItemStack(Items.BONE_MEAL, 6), 1),
			new ExtendedTradeEntry(16, new ItemStack(Items.WATER_BUCKET), 4),
			new ExtendedTradeEntry(16, new ItemStack(Items.BUCKET), 3), 
			// Tools
			new ExtendedTradeEntry(4, new ItemStack(Items.WOODEN_HOE), 3), 
			new ExtendedTradeEntry(32, new ItemStack(Items.STONE_HOE), 1),
			new ExtendedTradeEntry(16, new ItemStack(LOTRItems.BRONZE_HOE.get()), 6),
			new ExtendedTradeEntry(4, new ItemStack(Items.IRON_HOE), 8), 
	});

	private final ExtendedTradeEntries BREE_FARMER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Crops
			new ExtendedTradeEntry(50, new ItemStack(Items.CARROT), 2, 4), 
			new ExtendedTradeEntry(64, new ItemStack(LOTRItems.LETTUCE.get()), 2, 4),
			new ExtendedTradeEntry(64, new ItemStack(Items.POTATO), 2, 3),
			new ExtendedTradeEntry(128, new ItemStack(Items.WHEAT_SEEDS), 1, 1), 
			new ExtendedTradeEntry(64, new ItemStack(Items.WHEAT), 2, 3),
			new ExtendedTradeEntry(64, new ItemStack(LOTRItems.PIPEWEED_LEAF.get()), 3, 5),
			new ExtendedTradeEntry(64, new ItemStack(LOTRItems.PIPEWEED_SEEDS.get()), 2, 4), 
			// Produced
			new ExtendedTradeEntry(64, new ItemStack(Items.HAY_BLOCK), 9, 15),
			new ExtendedTradeEntry(8, new ItemStack(Items.LEAD), 6, 10), 
			new ExtendedTradeEntry(10, new ItemStack(Items.MILK_BUCKET), 6, 10), 
			new ExtendedTradeEntry(33, new ItemStack(Items.APPLE), 2, 3), 
			new ExtendedTradeEntry(32, new ItemStack(LOTRItems.GREEN_APPLE.get()), 2, 3),
			new ExtendedTradeEntry(128, new ItemStack(Items.WHITE_WOOL), 2,3),
			// Tools
			new ExtendedTradeEntry(8, new ItemStack(ExtendedItems.BRANDING_IRON.get()), 20, 30)
	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandFarmerEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);

		itemsBoughtByTrader = BREE_FARMER_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_FARMER_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setIdleItem(new ItemStack(Items.IRON_HOE, 1));
		setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get(), 1));
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

	@Override
	public int setLastTickCooldownReduced() {
		return lastTickCooldownReduced;
	}

	@Override
	public void getLastTickCooldownReduced(int cooldown) {
		lastTickCooldownReduced = cooldown;
	}
}
