package com.github.maximuslotro.lotrrextended.common.datagen.entity;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfile;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfileManager;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;
import com.github.maximuslotro.lotrrextended.common.traders.pools.ExtendedTraderPoolItem;
import com.github.maximuslotro.lotrrextended.common.traders.pools.ItemStackAndPrice;
import com.google.gson.Gson;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedTraderProfileProvider implements IDataProvider {
	public static final Gson GSON = ExtendedTraderProfileManager.GSON;
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedTraderProfile> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	protected final String modId;

	private final Map<ItemStack, List<Integer>> soldItems = new HashMap<>();
	private final Map<ItemStack, List<Integer>> boughtItems = new HashMap<>();

	public ExtendedTraderProfileProvider(DataGenerator pDataGenerator, String pModId) {
		this.dataGenerator = pDataGenerator;
		this.modId = pModId;
	}

	public abstract void makeTraderProfiles(Consumer<ExtendedTraderProfile> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedTraderProfile> consumer = (profile) -> {
			if (dataHere.put(profile.getId(), profile)!=null) {
				throw new IllegalStateException("Duplicate trader profile " + profile.getId().toString());
			} else {
				populateCheckingList(profile.getTraderBoughtPool(), boughtItems);
				populateCheckingList(profile.getTraderSellPool(), soldItems);
				Path path1 = createPath(path, profile);
				try {
					IDataProvider.save(GSON, pCache, GSON.toJsonTree(profile), path1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save trader profile {}", path1, ioexception);
				}

			}
		};
		makeTraderProfiles(consumer);
		ExtendedLog.info("Built "+dataHere.size()+" trader profiles.");
		if(modId.equals(LotrExtendedMod.MODID)) {
			for(ResourceLocation key : ExtendedTraderTypes.TRADER_TYPES) {
				if(!dataHere.containsKey(key)) {
					LOGGER.warn("Didn't make expected trader profile type: "+key.toString());
				}
			}
		}
		for(Entry<ItemStack, List<Integer>> entrySold : soldItems.entrySet()) {
			for(Entry<ItemStack, List<Integer>> entryBought : boughtItems.entrySet()) {
				if(ItemStack.isSame(entrySold.getKey(), entryBought.getKey())) {
					boolean isHigher = false;
					for (Integer buyCost : entryBought.getValue()) {
						for (Integer soldCost : entrySold.getValue()) {
							float soldAmount = soldCost / (float)entrySold.getKey().getCount();
							float boughtAmount = buyCost / (float)entryBought.getKey().getCount();
							if(soldAmount<boughtAmount) {
								LOGGER.warn(entrySold.getKey().toString() + " tried being sold for less than the raw buy price of " + buyCost +":["+ soldAmount + "<" + boughtAmount+"]");
								isHigher = true;
							}
							if(soldAmount==boughtAmount) {
								if(buyCost!=1 && soldCost!=1) {
									LOGGER.warn(entrySold.getKey().toString() + " tried being sold same as the buy price of " + soldCost +":[" + soldAmount+"]");
								}
							}
							// TODO better min-max price validation 
							//if(soldAmount*0.75<boughtAmount*1.25) {
							//	LOGGER.warn(entrySold.getKey().toString() + " tried being sold for less than the min-max buy price of " + soldCost + "<" + buyCost +":fraction:"+ soldAmount*0.75 + "<" + boughtAmount*1.25);
							//}
						}
					}
				}
			}
		}
		//printPriceRanges();
	}

	private static void populateCheckingList(List<ExtendedTraderPoolItem> pool, Map<ItemStack, List<Integer>> list) {
		for(ExtendedTraderPoolItem entry : pool) {
			for(ItemStackAndPrice stackAndPrice : entry.getAllStacksAndPrices()) {
				if(list.containsKey(stackAndPrice.getItemStack())) {
					List<Integer> costs = list.get(stackAndPrice.getItemStack());
					if(!costs.contains(stackAndPrice.getAverageCost())) {
						costs.add(stackAndPrice.getAverageCost());
						list.replace(stackAndPrice.getItemStack(), costs);
					}
				}else {
					list.put(stackAndPrice.getItemStack(), new ArrayList<>(Arrays.asList(stackAndPrice.getAverageCost())));
				}
			}
		}
	}

	private static Path createPath(Path pPath, ExtendedTraderProfile profile) {
		return pPath.resolve("data/" + profile.getId().getNamespace()+ "/trader_profiles/" + profile.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Trader Profile Provider";
	}

	public ExtendedTraderProfile.Builder make(int pTradingAlignmentRequired) {
		return new ExtendedTraderProfile.Builder(pTradingAlignmentRequired);
	}

	private void printPriceRanges() {
		Map<String, Integer> maxSellMap = new HashMap<>();
		Map<String, Integer> maxBuyMap = new HashMap<>();
		// Collect max sell prices
		for (Entry<ItemStack, List<Integer>> entry : soldItems.entrySet()) {
			String key = entry.getKey().getItem().getRegistryName().toString();
			int max = entry.getValue().stream().mapToInt(Integer::intValue).max().orElse(0);
			maxSellMap.merge(key, max, Math::max);
		}
		// Collect max buy prices
		for (Entry<ItemStack, List<Integer>> entry : boughtItems.entrySet()) {
			String key = entry.getKey().getItem().getRegistryName().toString();
			int max = entry.getValue().stream().mapToInt(Integer::intValue).max().orElse(0);
			maxBuyMap.merge(key, max, Math::max);
		}
		LOGGER.info("=== MAX SELL PRICES ===");
		maxSellMap.entrySet().stream().sorted((a, b) -> Integer.compare(b.getValue(), a.getValue())).forEach(entry -> LOGGER.info("{} -> {}", entry.getKey(), entry.getValue()));
		LOGGER.info("=== MAX BUY PRICES ===");
		maxBuyMap.entrySet().stream().sorted((a, b) -> Integer.compare(b.getValue(), a.getValue())).forEach(entry -> LOGGER.info("{} -> {}", entry.getKey(), entry.getValue()));
	}
}
