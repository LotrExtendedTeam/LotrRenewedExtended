package com.github.maximuslotro.lotrrextended.common.item;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ExtendedTapestryType extends ForgeRegistryEntry<ExtendedTapestryType>{
	public static Supplier<IForgeRegistry<ExtendedTapestryType>> TAPESTRIE_REGISTRY;
	public static final DeferredRegister<ExtendedTapestryType> TAPESTRIES = DeferredRegister.create(ExtendedTapestryType.class, LotrExtendedMod.MODID);
	public static final RegistryObject<ExtendedTapestryType> LAST_ALLIANCE = register("last_alliance", 80, 48);
	private final int width;
	private final int height;
	
	public static RegistryObject<ExtendedTapestryType> register(String pKey, int pWidth, int pHeight) {
		return TAPESTRIES.register(pKey, ()->new ExtendedTapestryType(pWidth, pHeight));
	}

	public ExtendedTapestryType(int pWidth, int pHeight) {
		this.width = pWidth;
		this.height = pHeight;
	}

	public static void register(IEventBus bus) {
		TAPESTRIE_REGISTRY = TAPESTRIES.makeRegistry("tapestries", () -> new RegistryBuilder<>());
		TAPESTRIES.register(bus);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
