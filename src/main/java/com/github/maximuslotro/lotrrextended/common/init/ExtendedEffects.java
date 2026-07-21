package com.github.maximuslotro.lotrrextended.common.init;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.github.maximuslotro.lotrrextended.common.effect.ExtendedDeadlyPoison;

import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedEffects {
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, LotrExtendedMod.MODID);
	public static final RegistryObject<ExtendedDeadlyPoison> DEADLY_POISON = register("deadly_poison", "Deadly Poison", () -> new ExtendedDeadlyPoison());

	private static <T extends Effect> RegistryObject<T> register(String name, String defaultName, Supplier<T> sup) {
		if(DatagenModLoader.isRunningDataGen() && defaultName!=null) {
    		DataGenValueHolder.effectsHolders.put("effect."+LotrExtendedMod.MODID+"."+name, defaultName);
    	}
		return EFFECTS.register(name, sup);
	}

	public static void register(IEventBus bus) {
		EFFECTS.register(bus);
	}
}
