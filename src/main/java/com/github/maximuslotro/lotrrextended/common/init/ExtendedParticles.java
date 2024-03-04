package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedParticles {
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, LotrExtendedMod.MODID);
	public static final RegistryObject<BasicParticleType> MORGUL_FLAME = PARTICLES.register("morgul_flame", () -> new BasicParticleType(false));

	public static void register(IEventBus bus) {
		PARTICLES.register(bus);
	}
}
