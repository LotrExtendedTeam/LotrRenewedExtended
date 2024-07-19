package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.effect.ExtendedKillingPoison;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedEffects {
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, LotrExtendedMod.MODID);

	public static final Effect KILLING_POISON = new ExtendedKillingPoison(EffectType.HARMFUL, 7670042);

	public static void register() {
		EFFECTS.register("killingpoison", () -> KILLING_POISON);
	}
}
