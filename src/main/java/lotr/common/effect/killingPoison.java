package lotr.common.effect;

import lotr.common.init.LOTRDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class killingPoison extends Effect{

	public killingPoison(EffectType p_i50391_1_, int p_i50391_2_) {
		super(p_i50391_1_, p_i50391_2_);
	}

	@Override
	public void applyEffectTick(LivingEntity p_76394_1_, int p_76394_2_) {
		p_76394_1_.hurt(LOTRDamageSources.POISON_DRINK, 1.0F);
	}

	@Override
	public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
		return true;
	}
}
