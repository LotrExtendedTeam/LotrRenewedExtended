package com.github.maximuslotro.lotrrextended.common.effect;

import lotr.common.init.LOTRDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class ExtendedKillingPoison extends Effect{

	public ExtendedKillingPoison(EffectType pCategory, int pColor) {
		super(pCategory, pColor);
	}

	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		pLivingEntity.hurt(LOTRDamageSources.POISON_DRINK, 1.0F);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return true;
	}
}
