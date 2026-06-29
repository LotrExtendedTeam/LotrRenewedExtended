package lotr.common.entity.npc.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedTradeType;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSNpcTraderInfoPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSTradeRefreshPacket;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfile;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfileManager;
import com.github.maximuslotro.lotrrextended.common.traders.pools.ExtendedTraderPoolItem;
import com.github.maximuslotro.lotrrextended.common.traders.pools.ItemStackAndPrice;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.ExtendedMerchantEntity;
import lotr.common.entity.npc.ExtendedWanderingTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.ExtendedTraderHelper;
import lotr.common.util.TradeUtils;
import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ExtendedNpcTraderInfoHolder implements ExtendedNpcDataInterface {
	private final NPCEntity thisEntity;
	// All traders
	private List<ExtendedTrade> itemsBoughtByTrader;
	private List<ExtendedTrade> itemsSoldByTrader;
	private ExtendedTraderProfile traderProfileHolder;
	private int tradingAlignmentRequired = 1659; // February 16 – The first known check (400 pounds) is written.
	private boolean shouldRefreshTrades = true;
	private int refreshTradesAtValue = 0; // Zero is invalid, runs refresh on next trade, unless on first load
	private int currentTraderSoldAmount = 0; // Current value of items trader sold to player
	private int totalTraderSoldAmount = 0; // Total value of items trader sold to player
	private int currentTraderBoughtAmount = 0; // Current value of items trader bought from player
	private int totalTraderBoughtAmount = 0; // Total value of items trader bought from player
	private int lockTicksAfterRefresh = 0;
	private int currentTraderLockTicksAmount = 0;
	private int wanderingTraderAliveTime = 0;
	private boolean isWanderingTrader = false;
	private boolean resendData = false;
	private boolean loadedData = false;
	/**
	 * Tick count used for item tick backs
	 * */
	private int lastTickCooldownReduced = 0;

	public ExtendedNpcTraderInfoHolder(NPCEntity thisEntity) {
		this.thisEntity = thisEntity;
	}

	public List<ExtendedTrade> getBuys() {
		return itemsBoughtByTrader;
	}

	public List<ExtendedTrade> getSells() {
		return itemsSoldByTrader;
	}

	public List<ExtendedTrade> generateTrades(Random rand, ExtendedTradeType type) {
		List<ExtendedTrade> outputTrades = new ArrayList<>();
		List<ExtendedTraderPoolItem> profileTradePool = new ArrayList<>(getProfile().getTraderPool(type));

		// Number of trades that should generate, handle  pools smaller than generated trade count or larger than 9 slots
		int tradeCount = Math.min(Math.min(rand.nextInt(getProfile().getMaximumAdditionalTrades()+1) + getProfile().getMinimumTrades(), profileTradePool.size()), 9);
		// Get random list of trade pool items
		List<ExtendedTraderPoolItem> randomElements = new ArrayList<>();
		for (int i = 0; i < tradeCount; i++) {
		    ExtendedTraderPoolItem chosen = WeightedRandom.getRandomItem(rand, profileTradePool);
		    // Catch end of list
		    if (chosen == null) {
		    	ExtendedLog.warn("Profile: "+getProfile().getId() + " is trying to generate null pool items :(");
		    	break;
		    }
		    randomElements.add(chosen);
		    profileTradePool.remove(chosen);
		}
		// Generate a random price for each entry
		randomElements.forEach(entry -> {
			// Doing a bit of math from (https://lotrminecraftmod.fandom.com/wiki/Trading) results in all items
			// having a average price such that the minimum price shown is 0.75 of the average and 
			// the maximum price shown is 1.25 of the average, both values rounded to the nearest whole number, with a minimum of 1 approximately
			ItemStackAndPrice trade = entry.getStackAndPrice(rand);
			float priceModifier = (rand.nextFloat() * 0.5F) + 0.75F;
			// Multiply with average entry price to get final float price
			float finalFloatPrice = trade.getAverageCost() * priceModifier;
			// Convert to whole number and ensure minimum of price of 1
			int nicePrice = Math.max(Math.round(finalFloatPrice), 1);
			outputTrades.add(new ExtendedTrade(trade.getItemStack(), nicePrice, entry.getMaxTransferQuantity(), entry.getMaxTransferQuantity(), entry.shouldTradePersist()));
		});

		return outputTrades;
	}

	/**
	 * SHOULD ONLY BE CALLED ON THE SERVER, TRADER PROFILES ARE NOT AVAILABLE CLIENT SIDE!
	 * IM LOOKING AT YOU MAX! (MYSELF) (I keep making the mistake)
	 * @return A {@link ExtendedTraderProfile} instance
	 * */
	public ExtendedTraderProfile getProfile() {
		if(traderProfileHolder!=null) {
			return traderProfileHolder;
		}
		if(shouldLoad()) {
			traderProfileHolder = ExtendedTraderProfileManager.INSTANCE.get(((ExtendedMerchantEntity)thisEntity).getTraderProfileId());
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

	/**
	 * Gets the amount of alignment needed to trade.
	 * Synced to the client
	 * @return The integer value of the needed alignment
	 * */
	public int getTradingAlignmentRequiredDualSided() {
		return tradingAlignmentRequired;
	}

	/**
	 * Checks if this trader currently has his trades locked out
	 * @return True is player is locked out
	 * */
	public boolean isTradesLocked() {
		return currentTraderLockTicksAmount>0;
	}

	/**
	 * Increment the traders counter for trades sold to player
	 * @param amount The coin amount that was sold to player
	 * */
	public void incrementTradeSoldToPlayerCount(int amount) {
		currentTraderSoldAmount += amount;
		totalTraderSoldAmount += amount;
	}

	/**
	 * Increment the traders counter for trades bought from player
	 * @param amount The coin amount that was bought from player
	 * */
	public void incrementTradeBoughtFromPlayerCount(int amount) {
		currentTraderBoughtAmount += amount;
		totalTraderBoughtAmount += amount;
	}

	/**
	 * Refreshes the trader's trades, resets the current stock sold counter
	 * */
	public void refreshTrades() {
		itemsBoughtByTrader = generateTrades(thisEntity.getRandom(), ExtendedTradeType.BUY_FROM_PLAYER);
		itemsSoldByTrader = generateTrades(thisEntity.getRandom(), ExtendedTradeType.SELL_TO_PLAYER);
		currentTraderSoldAmount = 0; // Reset trader sold counter
		currentTraderBoughtAmount = 0; // Reset trader bought counter
		refreshTradesAtValue = refreshTradesAtValue==0 ? getProfile().getRefreshTradesAtValue() : refreshTradesAtValue; //Just handle invalid values after init
		markDirty();
		ExtendedSTradeRefreshPacket packet = new ExtendedSTradeRefreshPacket(thisEntity.getId());
		ExtendedPacketHandler.sendToAllTrackingEntity(packet, thisEntity);
	}

	/**
	 * Generate new trader data if not loaded, respecting previously set values
	 * */
	@Override
	public void tick() {
		if (!thisEntity.level.isClientSide) {
			// Handle loading init data or fixing broken data
			if(loadedData==false) {
				itemsBoughtByTrader = generateTrades(thisEntity.getRandom(), ExtendedTradeType.BUY_FROM_PLAYER);
				itemsSoldByTrader = generateTrades(thisEntity.getRandom(), ExtendedTradeType.SELL_TO_PLAYER);
				tradingAlignmentRequired = tradingAlignmentRequired==1659 ? getProfile().getTradingAlignmentRequired() : tradingAlignmentRequired;
				shouldRefreshTrades = !shouldRefreshTrades ? false : getProfile().shouldRefreshTrades();
				refreshTradesAtValue = refreshTradesAtValue==0 ? getProfile().getRefreshTradesAtValue() : refreshTradesAtValue;
				lockTicksAfterRefresh = lockTicksAfterRefresh==0 ? getProfile().getLockTicksAfterRefresh() : lockTicksAfterRefresh;
				currentTraderSoldAmount = 0;
				currentTraderBoughtAmount = 0;
				currentTraderLockTicksAmount=0;
				loadedData = true;
				markDirty();
			}
			// Handle traded amount being above max trade limit
			if(shouldRefreshTrades && (currentTraderSoldAmount+currentTraderBoughtAmount)>refreshTradesAtValue && currentTraderLockTicksAmount==0) {
				currentTraderLockTicksAmount=1; // Start trades lockout
				markDirty();
			}
			// Handle trader lock countdown
			if(currentTraderLockTicksAmount>0) {
				if(currentTraderLockTicksAmount>=lockTicksAfterRefresh) {
					currentTraderLockTicksAmount=0;
					refreshTrades();
				}else {
					currentTraderLockTicksAmount+=1;
				}
			}
			// Handle trade pool ticks
			// every 1 minute allow one more trade in both buys and sells to be done again
	        // TODO: Add support for different trades to tick back at different rates - e.g. a rare powerful sword should take
	        //  20-30 minutes to become available again whereas selling another individual pipe-weed might be just a few seconds
	        if (lastTickCooldownReduced >= 1200) {
	            for (ExtendedTrade trade : getSells()) {
	            	if(trade.shouldTradePersist() && (trade.getQuantityLeft() < trade.getOriginalQuantity())) {
	            		trade.incrementQuantityLeft();
	            	}
	            }

	            for (ExtendedTrade trade : getBuys()) {
	                if (trade.shouldTradePersist() && (trade.getQuantityLeft() < trade.getOriginalQuantity())) {
	                	trade.incrementQuantityLeft();
	                }
	            }
	            lastTickCooldownReduced = 0;
	            markDirty();
	        } else {
	        	lastTickCooldownReduced+=1;
	        }
			// Re-send data if changed
			if (resendData) {
				sendDataToAllBeings();
				resendData = false;
			}
			tickWanderingTrader(thisEntity);
		}
	}

	@Override
	public void markDirty() {
		resendData = true;
	}

	@Override
	public void loadFromNbt(CompoundNBT nbt) {
		if(nbt.contains("TraderInfo")) {
			CompoundNBT holder = nbt.getCompound("TraderInfo");
			loadedData = holder.getBoolean("HasLoaded");
			List<ExtendedTrade> buys = TradeUtils.loadAllTrades(ExtendedTradeType.BUY_FROM_PLAYER, holder);
			List<ExtendedTrade> sells = TradeUtils.loadAllTrades(ExtendedTradeType.SELL_TO_PLAYER, holder);
			tradingAlignmentRequired = holder.contains("TradingAlignmentRequired") ? holder.getInt("TradingAlignmentRequired") : 1659;
			shouldRefreshTrades = holder.contains("ShouldLockTrades") ? holder.getBoolean("ShouldLockTrades") : true;
			refreshTradesAtValue = holder.getInt("LockTradeAtValue");
			lockTicksAfterRefresh = holder.getInt("LockValueDecayTicks");
			currentTraderSoldAmount = holder.getInt("CurrentTraderSoldAmount");
			currentTraderBoughtAmount = holder.getInt("CurrentTraderBoughtAmount");
			totalTraderSoldAmount = holder.getInt("TotalTraderSoldAmount");
			totalTraderBoughtAmount = holder.getInt("TotalTraderBoughtAmount");
			wanderingTraderAliveTime = holder.getInt("WanderingTraderTimeLeft"); // Defaults to zero (despawn if wandering if broken)
			isWanderingTrader = holder.getBoolean("IsWanderingTrader"); //Defaults to false, (not wandering trader)
			if(!isWanderingTrader && wanderingTraderAliveTime>0) {
				if(getProfile().isTypicallyWandering()) {
					ExtendedLog.warn("Fixed an non-ticking wandering trader: "+getProfile().getId().toString());
					isWanderingTrader = true;
				} else {
					wanderingTraderAliveTime = 0;
				}
			}
			currentTraderLockTicksAmount = holder.getInt("CurrentTraderLockTicksAmount"); // Defaults to zero (no lock activated)
			if (buys.isEmpty() || sells.isEmpty() || refreshTradesAtValue==0 || lockTicksAfterRefresh==0 || tradingAlignmentRequired==1659) {
				ExtendedLog.info("Generating new trader for for invalid nbt: "+getProfile().getId().toString());
				ExtendedLog.info(holder.getAsString());
				loadedData=false;
			}else {
				itemsBoughtByTrader = buys;	
				itemsSoldByTrader = sells;
				loadedData=true;
			}
		}else {
			loadedData=false;
		}
		// Handle pre-1.7.4 wandering traders
		if(nbt.contains("isWanderingTrader") || nbt.contains("IsWanderingTrader")) {
			isWanderingTrader = nbt.getBoolean("IsWanderingTrader");
			wanderingTraderAliveTime = nbt.getInt("WanderingTraderTimeLeft");
		}
	}

	@Override
	public void writeToNbt(CompoundNBT nbt) {
		try {
			CompoundNBT holder = new CompoundNBT();
			holder.putBoolean("HasLoaded", loadedData);
			holder.putInt("TradingAlignmentRequired", tradingAlignmentRequired);
			holder.putBoolean("ShouldLockTrades", shouldRefreshTrades);
			holder.putInt("LockTradeAtValue", refreshTradesAtValue);
			holder.putInt("LockValueDecayTicks", lockTicksAfterRefresh);
			holder.putInt("CurrentTraderSoldAmount", currentTraderSoldAmount);
			holder.putInt("CurrentTraderBoughtAmount", currentTraderBoughtAmount);
			holder.putInt("TotalTraderSoldAmount", totalTraderSoldAmount);
			holder.putInt("TotalTraderBoughtAmount", totalTraderBoughtAmount);
			holder.putInt("WanderingTraderTimeLeft", wanderingTraderAliveTime);
			holder.putBoolean("IsWanderingTrader", isWanderingTrader);
			holder.putInt("CurrentTraderLockTicksAmount", currentTraderLockTicksAmount);
			TradeUtils.saveAllTrades(ExtendedTradeType.BUY_FROM_PLAYER, getBuys(), holder);
			TradeUtils.saveAllTrades(ExtendedTradeType.SELL_TO_PLAYER, getSells(), holder);
			nbt.put("TraderInfo", holder);
		} catch (NullPointerException e) {
			// The only place a NPE can come from is saving the trader pools when they don't exist.
			// As they are the most important part of the trader information, as they can't save just reset the trader data.
		}
	}

	/**
	 * Load data is this is a trader
	 * */
	@Override
	public boolean shouldLoad() {
		return shouldTick();
	}
	/**
	 * Check if this NPC is a trader
	 * */
	@Override
	public boolean shouldTick() {
		return thisEntity instanceof ExtendedMerchantEntity;
	}

	/**
	 * Sync trader alignment level data
	 * */
	@Override
	public PacketBuffer createDataPacketToClient(PacketBuffer buf) { 
		buf.writeVarInt(thisEntity.getId());
		buf.writeVarInt(tradingAlignmentRequired);
		buf.writeBoolean(isTradesLocked());
		return buf;
	}

	/**
	 * Sync trader alignment level data
	 * */
	@Override
	public void sendDataToPlayer(ServerPlayerEntity player) {
		ExtendedSNpcTraderInfoPacket packet = new ExtendedSNpcTraderInfoPacket(this);
		ExtendedPacketHandler.sendToClient(packet, player);
	}

	/**
	 * Sync trader alignment level data
	 * */
	@Override
	public void sendDataToAllBeings() {
		ExtendedSNpcTraderInfoPacket packet = new ExtendedSNpcTraderInfoPacket(this);
		ExtendedPacketHandler.sendToAllTrackingEntity(packet, thisEntity);
	}

	/*
	 * Handle data packets from the server
	 * */
	public static void readDataPacketFromServer(PacketBuffer buf, World world) {
		int entityId = buf.readVarInt();
		Entity entity = world.getEntity(entityId);
		if (entity instanceof NPCEntity) {
			NPCEntity npc = (NPCEntity) entity;
			ExtendedNpcTraderInfoHolder traderInfo = ((ExtendedNPCEntity) npc).getTraderInfo();
			if(traderInfo!=null) {
				traderInfo.tradingAlignmentRequired = buf.readVarInt();
				traderInfo.currentTraderLockTicksAmount = buf.readBoolean() ? 1 : 0;
			}
		}
	}

	/**
	 * Gets if this trader is wandering. Should ONLY be set to true if instance of ExtendedWanderingTraderEntity
	 * Even then, ExtendedWanderingTraderEntity can be false when set from an egg.
	 * @return True if should handle NPC as wandering.
	 * */
	public boolean isTraderCurrentlyWandering() {
		return isWanderingTrader;
	}

	/**
	 * Set to mark this trader as wandering, as opposed to a trader that does not despawn
	 * @param isWanderingTrader Sets the wandering state
	 * */
	public void setWanderingTrader(boolean isWanderingTrader) {
		this.isWanderingTrader = isWanderingTrader;
	}

	/**
	 * Sets the time that a wandering trader can be alive for
	 * @param wanderingTraderAliveTime The number of ticks to be alive for
	 * */
	public void setWanderingTraderAliveTime(int wanderingTraderAliveTime) {
		this.wanderingTraderAliveTime = wanderingTraderAliveTime;
	}

	private void tickWanderingTrader(NPCEntity npc) {
		if (isTraderCurrentlyWandering()) {
			if (wanderingTraderAliveTime < 1) {
				// notify players the traveling trader has departed
				Style npcNameStyle = Style.EMPTY.withColor(Color.fromRgb(((ExtendedWanderingTraderEntity) npc).getFactionColor()));
				for (PlayerEntity player : npc.level.players()) {
					if(((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(player)).getExtendedMiscData().doHighContrastMessages()) {
						player.sendMessage(new TranslationTextComponent("chat.wandering_trader.left_the_area", npc.getName().copy().withStyle(npcNameStyle)), Util.NIL_UUID);
					}else {
						player.sendMessage(new TranslationTextComponent("chat.wandering_trader.left_the_area", npc.getName().copy().withStyle(TextFormatting.YELLOW)), Util.NIL_UUID);
					}
				}

                npc.remove();

                if (((ExtendedWanderingTraderEntity)npc).getEscortUUIDs() != null && !((ExtendedWanderingTraderEntity)npc).getEscortUUIDs().isEmpty()) {
                    AxisAlignedBB initial = new AxisAlignedBB(npc.getX(), npc.getY(), npc.getZ(),
                            npc.getX() + 1, npc.getY() + 1, npc.getZ() + 1);
                    List<NPCEntity> potentialEscorts = npc.level.getEntitiesOfClass(NPCEntity.class, initial.inflate(256));

                    for (NPCEntity entity : potentialEscorts) {
                        if (((ExtendedWanderingTraderEntity)npc).getEscortUUIDs().contains(entity.getUUID())) {
                            entity.remove();
                        }
                    }
                }

            } else {
            	wanderingTraderAliveTime-=1;
                // warn players nearby at 4 minutes left
                if (wanderingTraderAliveTime == 2400) {
                    AxisAlignedBB initial = new AxisAlignedBB(npc.getX(), npc.getY(), npc.getZ(), npc.getX() + 1, npc.getY() + 1, npc.getZ() + 1);
                    List<PlayerEntity> playersInRange = npc.level.getEntitiesOfClass(PlayerEntity.class, initial.inflate(80));
                    Style npcNameStyle = Style.EMPTY.withColor(Color.fromRgb(((ExtendedWanderingTraderEntity) npc).getFactionColor()));
                    for (PlayerEntity player : playersInRange) {
                    	if(((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(player)).getExtendedMiscData().doHighContrastMessages()) {
    						player.sendMessage(new TranslationTextComponent(((ExtendedWanderingTraderEntity)npc).getDepartureLine(), npc.getName().copy().withStyle(npcNameStyle), player.getName()), Util.NIL_UUID);
    					}else {
    						player.sendMessage(new TranslationTextComponent(((ExtendedWanderingTraderEntity)npc).getDepartureLine(), npc.getName().copy().withStyle(TextFormatting.YELLOW), player.getName()), Util.NIL_UUID);
    					}
                    }
                }
            }
        }
    }
}
