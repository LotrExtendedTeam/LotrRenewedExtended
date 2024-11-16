package com.github.maximuslotro.lotrrextended.client;

import com.github.maximuslotro.lotrrextended.client.particle.BarrowWightJumpscareParticle;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

public class ExtendedClientParticles {

	public static void register(ParticleFactoryRegisterEvent event) {
		Minecraft mc = Minecraft.getInstance();
		ParticleManager particleEngine = mc.particleEngine;
		particleEngine.register(ExtendedParticles.MORGUL_FLAME.get(), FlameParticle.Factory::new);
		particleEngine.register(ExtendedParticles.BARROW_WIGHT_JUMPSCARE.get(), new BarrowWightJumpscareParticle.Factory());
	}

}
