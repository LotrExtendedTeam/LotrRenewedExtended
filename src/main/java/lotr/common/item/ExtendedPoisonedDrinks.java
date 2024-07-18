package lotr.common.item;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedEffects;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class ExtendedPoisonedDrinks {

	public static void severlyPoisonPlayer(PlayerEntity player) {
		player.addEffect(new EffectInstance(ExtendedEffects.KILLING_POISON, 300));
	}
}
