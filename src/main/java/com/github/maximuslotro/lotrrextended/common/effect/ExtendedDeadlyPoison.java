package com.github.maximuslotro.lotrrextended.common.effect;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedDamageSourceRepository;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class ExtendedDeadlyPoison extends Effect {

	public ExtendedDeadlyPoison() {
		super(EffectType.HARMFUL, 5149489);
	}

	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		pLivingEntity.hurt(ExtendedDamageSourceRepository.POISON_DRINK, 1.0F);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		// Damage on every damage immunity frame off-tick
		int j = 10 >> pAmplifier;
		if (j > 0) {
			return pDuration % j == 0;
		} else {
			return true;
		}
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		return new ArrayList<ItemStack>();
	}
}
