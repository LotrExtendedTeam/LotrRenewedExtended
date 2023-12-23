package lotr.common.init;

import lotr.common.effect.ExtendedKillingPoison;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedEffects {
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, "lotr");

	public static final Effect KILLING_POISON = new ExtendedKillingPoison(EffectType.HARMFUL, 990000);

	public static void register() {
		EFFECTS.register("killingpoison", () -> KILLING_POISON);
	}
}
