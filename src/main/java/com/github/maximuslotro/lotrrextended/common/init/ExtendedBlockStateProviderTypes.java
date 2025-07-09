package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.common.world.gen.blockstateprovider.ExtendedListTypedBlockStateProvider;
import com.mojang.serialization.Codec;

import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Registers custom {@link BlockStateProviderType} instances used for providing
 * block states during world generation and structure placement.
 */
public class ExtendedBlockStateProviderTypes {
	/**
     * Deferred register for block state provider types under the mod ID "lotrextended".
     */
	public static final DeferredRegister<BlockStateProviderType<?>> PROVIDERS = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, "lotrextended");
	/**
     * Block state provider type representing a provider that selects block states from a list of block states.
     */
	public static final RegistryObject<BlockStateProviderType<ExtendedListTypedBlockStateProvider>> BUSH_STATE_PROVIDER = register("list_state_provider", ExtendedListTypedBlockStateProvider.CODEC);
	/**
     * Registers a new {@link BlockStateProviderType} with the given name and codec.
     *
     * @param <P>   The specific type of {@link BlockStateProvider}.
     * @param pName The registry name for this provider type.
     * @param pCodec The codec used to serialize and deserialize the provider.
     * @return A {@link RegistryObject} wrapping the registered provider type.
     */
	private static <P extends BlockStateProvider> RegistryObject<BlockStateProviderType<P>> register(String pName, Codec<P> pCodec) {
		return PROVIDERS.register(pName, () -> new BlockStateProviderType<>(pCodec));
	}
	/**
     * Registers the deferred registry to the provided mod event bus.
     *
     * @param bus The mod event bus where the providers should be registered.
     */
	public static void register(IEventBus bus) {
		PROVIDERS.register(bus);
	}
}