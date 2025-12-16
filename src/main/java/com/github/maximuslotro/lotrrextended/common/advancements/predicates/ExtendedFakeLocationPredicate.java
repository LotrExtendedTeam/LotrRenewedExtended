package com.github.maximuslotro.lotrrextended.common.advancements.predicates;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;

import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.FluidPredicate;
import net.minecraft.advancements.criterion.LightPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFakeLocationPredicate {
	private static final Logger LOGGER = LogManager.getLogger();
	public static final ExtendedFakeLocationPredicate ANY = new ExtendedFakeLocationPredicate(MinMaxBounds.FloatBound.ANY, MinMaxBounds.FloatBound.ANY, MinMaxBounds.FloatBound.ANY, (RegistryKey<Biome>)null, (ResourceLocation)null, (RegistryKey<World>)null, (Boolean)null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
	private final MinMaxBounds.FloatBound x;
	private final MinMaxBounds.FloatBound y;
	private final MinMaxBounds.FloatBound z;
	@Nullable
	private final RegistryKey<Biome> biome;
	@Nullable
	private final ResourceLocation feature;
	@Nullable
	private final RegistryKey<World> dimension;
	@Nullable
	private final Boolean smokey;
	private final LightPredicate light;
	private final BlockPredicate block;
	private final FluidPredicate fluid;

	public ExtendedFakeLocationPredicate(MinMaxBounds.FloatBound p_i241961_1_, MinMaxBounds.FloatBound p_i241961_2_, MinMaxBounds.FloatBound p_i241961_3_, @Nullable RegistryKey<Biome> p_i241961_4_, @Nullable ResourceLocation p_i241961_5_, @Nullable RegistryKey<World> p_i241961_6_, @Nullable Boolean p_i241961_7_, LightPredicate p_i241961_8_, BlockPredicate p_i241961_9_, FluidPredicate p_i241961_10_) {
		this.x = p_i241961_1_;
		this.y = p_i241961_2_;
		this.z = p_i241961_3_;
		this.biome = p_i241961_4_;
		this.feature = p_i241961_5_;
		this.dimension = p_i241961_6_;
		this.smokey = p_i241961_7_;
		this.light = p_i241961_8_;
		this.block = p_i241961_9_;
		this.fluid = p_i241961_10_;
	}

	public static ExtendedFakeLocationPredicate inBiome(RegistryKey<Biome> pBiome) {
		return new ExtendedFakeLocationPredicate(MinMaxBounds.FloatBound.ANY, MinMaxBounds.FloatBound.ANY, MinMaxBounds.FloatBound.ANY, pBiome, (ResourceLocation)null, (RegistryKey<World>)null, (Boolean)null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
	}

	public static ExtendedFakeLocationPredicate inDimension(RegistryKey<World> pDimension) {
		return new ExtendedFakeLocationPredicate(MinMaxBounds.FloatBound.ANY, MinMaxBounds.FloatBound.ANY, MinMaxBounds.FloatBound.ANY, (RegistryKey<Biome>)null, (ResourceLocation)null, pDimension, (Boolean)null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
	}

	public static ExtendedFakeLocationPredicate inFeature(ResourceLocation pFeature) {
		return new ExtendedFakeLocationPredicate(MinMaxBounds.FloatBound.ANY, MinMaxBounds.FloatBound.ANY, MinMaxBounds.FloatBound.ANY, (RegistryKey<Biome>)null, pFeature, (RegistryKey<World>)null, (Boolean)null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
	}

	public boolean matches(ServerWorld pLevel, double pX, double pY, double pZ) {
		return this.matches(pLevel, (float)pX, (float)pY, (float)pZ);
	}

	public boolean matches(ServerWorld p_193453_1_, float p_193453_2_, float p_193453_3_, float p_193453_4_) {
		return false;
	}

	public JsonElement serializeToJson() {
		if (this == ANY) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonobject = new JsonObject();
			if (!this.x.isAny() || !this.y.isAny() || !this.z.isAny()) {
				JsonObject jsonobject1 = new JsonObject();
				jsonobject1.add("x", this.x.serializeToJson());
				jsonobject1.add("y", this.y.serializeToJson());
				jsonobject1.add("z", this.z.serializeToJson());
				jsonobject.add("position", jsonobject1);
			}

			if (this.dimension != null) {
				World.RESOURCE_KEY_CODEC.encodeStart(JsonOps.INSTANCE, this.dimension).resultOrPartial(LOGGER::error).ifPresent((p_235307_1_) -> {
					jsonobject.add("dimension", p_235307_1_);
				});
			}

			if (this.feature != null) {
				jsonobject.addProperty("feature", this.feature.toString());
			}

			if (this.biome != null) {
				jsonobject.addProperty("biome", this.biome.location().toString());
			}

			if (this.smokey != null) {
				jsonobject.addProperty("smokey", this.smokey);
			}

			jsonobject.add("light", this.light.serializeToJson());
			jsonobject.add("block", this.block.serializeToJson());
			jsonobject.add("fluid", this.fluid.serializeToJson());
			return jsonobject;
		}
	}

	public static ExtendedFakeLocationPredicate fromJson(@Nullable JsonElement pElement) {
		if (pElement != null && !pElement.isJsonNull()) {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(pElement, "location");
			JsonObject jsonobject1 = JSONUtils.getAsJsonObject(jsonobject, "position", new JsonObject());
			MinMaxBounds.FloatBound minmaxbounds$floatbound = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("x"));
			MinMaxBounds.FloatBound minmaxbounds$floatbound1 = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("y"));
			MinMaxBounds.FloatBound minmaxbounds$floatbound2 = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("z"));
			RegistryKey<World> registrykey = jsonobject.has("dimension") ? ResourceLocation.CODEC.parse(JsonOps.INSTANCE, jsonobject.get("dimension")).resultOrPartial(LOGGER::error).map((p_235310_0_) -> {
				return RegistryKey.create(Registry.DIMENSION_REGISTRY, p_235310_0_);
			}).orElse((RegistryKey<World>)null) : null;
			ResourceLocation structure = jsonobject.has("feature") ? new ResourceLocation(JSONUtils.getAsString(jsonobject, "feature")) : null;
			RegistryKey<Biome> registrykey1 = null;
			if (jsonobject.has("biome")) {
				ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getAsString(jsonobject, "biome"));
				registrykey1 = RegistryKey.create(Registry.BIOME_REGISTRY, resourcelocation);
			}

			Boolean obool = jsonobject.has("smokey") ? jsonobject.get("smokey").getAsBoolean() : null;
			LightPredicate lightpredicate = LightPredicate.fromJson(jsonobject.get("light"));
			BlockPredicate blockpredicate = BlockPredicate.fromJson(jsonobject.get("block"));
			FluidPredicate fluidpredicate = FluidPredicate.fromJson(jsonobject.get("fluid"));
			return new ExtendedFakeLocationPredicate(minmaxbounds$floatbound, minmaxbounds$floatbound1, minmaxbounds$floatbound2, registrykey1, structure, registrykey, obool, lightpredicate, blockpredicate, fluidpredicate);
		} else {
			return ANY;
		}
	}
}