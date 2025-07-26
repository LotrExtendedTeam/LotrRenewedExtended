package com.github.maximuslotro.lotrrextended.common.datagen.entity.traderprofiles;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.json.GsonSerializers;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfile;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfileManager;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;
import com.github.maximuslotro.lotrrextended.common.traders.pools.ExtendedTraderPoolItem;
import com.google.gson.Gson;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedTraderProfileProvider implements IDataProvider {
	public static final Gson GSON_PRETTY_WRITER = GsonSerializers.createTraderSerializer().disableHtmlEscaping().setPrettyPrinting().create();
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

	public abstract void makeStructureSpawnPools(Consumer<ExtendedTraderProfile> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedTraderProfile> consumer = (spawner) -> {
			if (dataHere.put(spawner.getId(), spawner)!=null) {
				throw new IllegalStateException("Duplicate trader profile " + spawner.getId().toString());
			} else {
				for(ExtendedTraderPoolItem entry : spawner.getTraderBoughtPool()) {
					for(ItemStack stack : entry.getAllStacks()) {
						if(boughtItems.containsKey(stack)) {
							List<Integer> costs = boughtItems.get(stack);
							if(!costs.contains(entry.getAverageCost())) {
								costs.add(entry.getAverageCost());
								boughtItems.replace(stack, costs);
							}
						}else {
							boughtItems.put(stack, new ArrayList<>(Arrays.asList(entry.getAverageCost())));
						}
					}
				}
				for(ExtendedTraderPoolItem entry : spawner.getTraderSellPool()) {
					for(ItemStack stack : entry.getAllStacks()) {
						if(soldItems.containsKey(stack)) {
							List<Integer> costs = soldItems.get(stack);
							if(!costs.contains(entry.getAverageCost())) {
								costs.add(entry.getAverageCost());
								soldItems.replace(stack, costs);
							}
						}else {
							soldItems.put(stack, new ArrayList<>(Arrays.asList(entry.getAverageCost())));
						}
					}
				}
				Path path1 = createPath(path, spawner);
				try {
					save(GSON_PRETTY_WRITER, pCache, spawner, path1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save trader profile {}", path1, ioexception);
				}

			}
		};
		makeStructureSpawnPools(consumer);
		ExtendedLog.info("Built "+dataHere.size()+" trader profiles.");
		for(ResourceLocation key : ExtendedTraderTypes.TRADER_TYPES) {
			if(!dataHere.containsKey(key)) {
				LOGGER.warn("Didn't make expected trader profile type: "+key.toString());
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
								LOGGER.warn(entrySold.getKey().toString() + " tried being sold for less than the raw buy price of " + soldCost + "<" + buyCost +":fraction:"+ soldAmount + "<" + boughtAmount);
								isHigher = true;
							}
							if(soldAmount==boughtAmount) {
								if(buyCost!=1 && soldCost!=1) {
									LOGGER.warn(entrySold.getKey().toString() + " tried being sold same as the buy price of " + soldCost +":fraction:" + soldAmount);
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
	}

	private static void save(Gson pGson, DirectoryCache pCache, ExtendedTraderProfile spawner, Path pPath) throws IOException {
		try {
			String s = ExtendedTraderProfileManager.serialize(pGson, spawner);
			String s1 = SHA1.hashUnencodedChars(s).toString();
			if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
				Files.createDirectories(pPath.getParent());

				try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
					bufferedwriter.write(s);
				}
			}

			pCache.putNew(pPath, s1);
		} catch (IOException ioexception) {
			LOGGER.error("Couldn't save trader profile {}", pPath, ioexception);
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
}
