package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.common.world.gen.blockstateprovider.ExtendedListTypedBlockStateProvider;
import com.mojang.serialization.Codec;

import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedBlockStateProviderTypes {
	public static final DeferredRegister<BlockStateProviderType<?>> PROVIDERS = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, "lotrextended");
	public static final RegistryObject<BlockStateProviderType<ExtendedListTypedBlockStateProvider>> BUSH_STATE_PROVIDER = register("list_state_provider", ExtendedListTypedBlockStateProvider.CODEC);

	private static <P extends BlockStateProvider> RegistryObject<BlockStateProviderType<P>> register(String pName, Codec<P> pCodec) {
		return PROVIDERS.register(pName, () -> new BlockStateProviderType<>(pCodec));
	}

	public static void register(IEventBus bus) {
		PROVIDERS.register(bus);
	}
}