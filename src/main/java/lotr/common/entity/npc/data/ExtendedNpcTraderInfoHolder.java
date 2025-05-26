package lotr.common.entity.npc.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedTradeType;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfile;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfileManager;
import com.github.maximuslotro.lotrrextended.common.traders.pools.ExtendedTraderPoolItem;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.ExtendedTraderHelper;
import lotr.common.util.TradeUtils;
import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ExtendedNpcTraderInfoHolder implements ExtendedNpcDataInterface {
	private final NPCEntity thisEntity;
	private List<ExtendedTrade> itemsBoughtByTrader;
	private List<ExtendedTrade> itemsSoldByTrader;
	private ExtendedTraderProfile traderProfileHolder;

	public ExtendedNpcTraderInfoHolder(NPCEntity thisEntity) {
		this.thisEntity = thisEntity;
	}

	public List<ExtendedTrade> getBuys() {
		if(itemsBoughtByTrader==null) {
			itemsBoughtByTrader = generateTrades(thisEntity.getRandom(), ExtendedTradeType.BUY_FROM_PLAYER);
		}
		return itemsBoughtByTrader;
	}

	public List<ExtendedTrade> getSells() {
		if(itemsSoldByTrader==null) {
			itemsSoldByTrader = generateTrades(thisEntity.getRandom(), ExtendedTradeType.SELL_TO_PLAYER);
		}
		return itemsSoldByTrader;
	}

	public List<ExtendedTrade> generateTrades(Random rand, ExtendedTradeType type) {
		List<ExtendedTrade> outputTrades = new ArrayList<>();
		List<ExtendedTraderPoolItem> profileTradePool = getProfile().getTraderPool(type);

		// Number of trades that should generate, handle  pools smaller than generated trade count or larger than 9 slots
		int tradeCount = Math.min(Math.min(rand.nextInt(getProfile().getMaximumAdditionalTrades()+1) + getProfile().getMinimumTrades(), profileTradePool.size()), 9);
		// Get random list of trade pool items
		List<ExtendedTraderPoolItem> randomElements = rand
			.ints(0, profileTradePool.size())
			.distinct()
			.limit(tradeCount)
			.mapToObj(profileTradePool::get)
			.collect(Collectors.toList());

		// Generate a random price for each entry
		randomElements.forEach(entry -> {
			// Doing a bit of math from (https://lotrminecraftmod.fandom.com/wiki/Trading) results in all items
			// having a average price such that the minimum price shown is 0.75 of the average and 
			// the maximum price shown is 1.25 of the average, both values rounded to the nearest whole number, with a minimum of 1
			float priceModifier = (rand.nextFloat() * 0.5F) + 0.75F;
			// Multiply with average entry price to get final float price
			float finalFloatPrice = entry.getAverageCost() * priceModifier;
			// Convert to whole number and ensure minimum of price of 1
			int nicePrice = Math.max(Math.round(finalFloatPrice), 1);
			outputTrades.add(new ExtendedTrade(entry.getStack(rand), nicePrice, entry.getMaxTransferQuantity(), entry.getMaxTransferQuantity()));
		});

		return outputTrades;
	}

	public ExtendedTraderProfile getProfile() {
		if(traderProfileHolder!=null) {
			return traderProfileHolder;
		}
		if(isActive()) {
			traderProfileHolder = ExtendedTraderProfileManager.INSTANCE.get(((ExtendedTraderEntity)thisEntity).getTraderProfileId());
			return traderProfileHolder;
		}
		ExtendedLog.error("ERROR ERROR! TRIED GETTING A TRADER PROFILE FROM A NON TRADER NPC");
		try {
			throw new Exception("Dumping stack trace and returning null!");
		} catch (Exception e) {
			e.printStackTrace(); // Prints the stack trace to System.err
		}
		return null;
	}

	public Inventory getDisplayInventory() {
		return TradeUtils.generateDisplayInventory(getSells(), getBuys());
	}

	public int[] getBoughtPrices() {
		return ExtendedTraderHelper.getBoughtPrices(getBuys());
	}

	public int[] getBoughtQuantities() {
		return ExtendedTraderHelper.getBoughtQuantity(getBuys());
	}

	public int[] getSoldPrices() {
		return ExtendedTraderHelper.getSoldPrices(getSells());
	}

	public int[] getSoldQuantities() {
		return ExtendedTraderHelper.getSoldQuantity(getSells());
	}

	public int[] getSoldOriginalQuantities() {
		return ExtendedTraderHelper.getSoldOriginalQuantity(getSells());
	}

	public int[] getBoughtOriginalQuantities() {
		return ExtendedTraderHelper.getBoughtOriginalQuantity(getBuys());
	}

	@Override
	public void tick() {
		if (!thisEntity.level.isClientSide) {
		}
	}

	@Override
	public void markDirty() {}

	@Override
	public void loadFromNbt(CompoundNBT nbt) {
		if(nbt.contains("traderInfo")) {
			CompoundNBT holder = nbt.getCompound("traderInfo");
			List<ExtendedTrade> buys = TradeUtils.loadAllTrades(ExtendedTradeType.BUY_FROM_PLAYER, holder);
			List<ExtendedTrade> sells = TradeUtils.loadAllTrades(ExtendedTradeType.SELL_TO_PLAYER, holder);
			if (!buys.isEmpty()) {
				itemsBoughtByTrader = buys;
			}else {
				ExtendedLog.infoShown("Generating new Trades for invalid nbt: "+getProfile().getId().toString());
				itemsBoughtByTrader = generateTrades(thisEntity.getRandom(), ExtendedTradeType.BUY_FROM_PLAYER);
			}
			if (!sells.isEmpty()) {
				itemsSoldByTrader = sells;
			}else {
				ExtendedLog.infoShown("Generating new Trades for invalid nbt: "+getProfile().getId().toString());
				itemsSoldByTrader = generateTrades(thisEntity.getRandom(), ExtendedTradeType.SELL_TO_PLAYER);
			}
		}else {
			ExtendedLog.infoShown("Generating new Trades for older trader: "+getProfile().getId().toString());
			itemsBoughtByTrader = generateTrades(thisEntity.getRandom(), ExtendedTradeType.BUY_FROM_PLAYER);
			itemsSoldByTrader = generateTrades(thisEntity.getRandom(), ExtendedTradeType.SELL_TO_PLAYER);
		}
	}

	@Override
	public void writeToNbt(CompoundNBT nbt) {
		CompoundNBT holder = new CompoundNBT();
		TradeUtils.saveAllTrades(ExtendedTradeType.BUY_FROM_PLAYER, getBuys(), holder);
		TradeUtils.saveAllTrades(ExtendedTradeType.SELL_TO_PLAYER, getSells(), holder);
		nbt.put("traderInfo", holder);
	}

	/*
	 * Only enable this module if its attached to a trader
	 * */
	@Override
	public boolean isActive() {
		return thisEntity instanceof ExtendedTraderEntity;
	}

	/*
	 * Not needed for traders, as players should not know trader data.
	 * Maybe use for syncing trades later?
	 * */
	@Override
	public PacketBuffer createDataPacketToClient(PacketBuffer buf) { return buf;}

	/*
	 * Not needed for traders, as players should not know trader data.
	 * Maybe use for syncing trades later?
	 * */
	@Override
	public void sendDataToPlayer(ServerPlayerEntity player) {}

	/*
	 * Not needed for traders, as players should not know trader data.
	 * Maybe use for syncing trades later?
	 * */
	@Override
	public void sendDataToAllBeings() {}
}
