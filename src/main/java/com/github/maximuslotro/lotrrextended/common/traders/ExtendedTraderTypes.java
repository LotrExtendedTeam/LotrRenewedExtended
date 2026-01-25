package com.github.maximuslotro.lotrrextended.common.traders;

import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DatagenModLoader;

/**
 * Stores IDs for built-in trader profiles.
 */
public class ExtendedTraderTypes {
	public static final Set<ResourceLocation> TRADER_TYPES = Sets.newHashSet();

	// Hobbit
	public static final ResourceLocation HOBBIT_BARTENDER = make("hobbit_bartender");
	public static final ResourceLocation HOBBIT_FARMER = make("hobbit_farmer");
	// Bree
	public static final ResourceLocation BREE_BAKER = make("bree_baker");
	public static final ResourceLocation BREE_BREWER = make("bree_brewer");
	public static final ResourceLocation BREE_BUTCHER = make("bree_butcher");
	public static final ResourceLocation BREE_CHEESEMONGER = make("bree_cheesemonger");
	public static final ResourceLocation BREE_FARMER = make("bree_farmer");
	public static final ResourceLocation BREE_FLORIST = make("bree_florist");
	public static final ResourceLocation BREE_INNKEEPER = make("bree_innkeeper");
	public static final ResourceLocation BREE_LEATHERWORKER = make("bree_leatherworker");
	public static final ResourceLocation BREE_LUMBERMAN = make("bree_lumberman");
	public static final ResourceLocation BREE_MERCHANT = make("bree_merchant");
	public static final ResourceLocation BREE_POTTER = make("bree_potter");
	public static final ResourceLocation BREE_SMITH = make("bree_smith");
	public static final ResourceLocation BREE_STABLEMASTER = make("bree_stablemaster");
	public static final ResourceLocation BREE_STONEMASON = make("bree_stonemason");
	// Gundabad
	public static final ResourceLocation GUNDABAD_TRADER = make("gundabad_trader");
	// Blue Mountains
	public static final ResourceLocation BLUE_MOUNTAINS_MINER = make("blue_mountains_miner");
	public static final ResourceLocation BLUE_MOUNTAINS_SMITH = make("blue_mountains_smith");
	public static final ResourceLocation BLUE_MOUNTAINS_MERCHANT = make("blue_mountains_merchant");
	public static final ResourceLocation BLUE_MOUNTAINS_BARTENDER = make("blue_mountains_bartender");
	public static final ResourceLocation BLUE_MOUNTAINS_BUTCHER = make("blue_mountains_butcher");
	public static final ResourceLocation BLUE_MOUNTAINS_STONEMASON = make("blue_mountains_stonemason");

	// Rivendell
	public static final ResourceLocation RIVENDELL_WANDERER = make("rivendell_wanderer");
	public static final ResourceLocation RIVENDELL_SMITH = make("rivendell_smith");
	public static final ResourceLocation RIVENDELL_WEAVER = make("rivendell_weaver");
	// Lindon
	public static final ResourceLocation LINDON_SMITH = make("lindon_smith");
	public static final ResourceLocation LINDON_WEAVER = make("lindon_weaver");
	// Galadhrim
	public static final ResourceLocation GALADHRIM_WANDERER = make("galadhrim_wanderer");
	// Misc
	public static final ResourceLocation ODDMENT_COLLECTOR = make("oddment_collector");
	// Dwarven
	public static final ResourceLocation DWARVEN_MINER = make("dwarven_miner");
	public static final ResourceLocation DWARVEN_SMITH = make("dwarven_smith");

	private static ResourceLocation make(String pId) {
		return validate(new ResourceLocation(LotrExtendedMod.MODID, pId));
	}

	private static ResourceLocation validate(ResourceLocation pId) {
		if(DatagenModLoader.isRunningDataGen()) {
			if (TRADER_TYPES.add(pId)) {
				return pId;
			} else {
				throw new IllegalArgumentException(pId + " is already a registered built-in extended trader type");
			}
		}
		return pId;
	}
}
