package lotr.common.init;

import net.minecraft.item.Food;
import net.minecraft.item.Food.Builder;
import net.minecraft.potion.*;

public class ExtendedFoods {
    public static final Food BLUEBERRY = new Builder().nutrition(4).saturationMod(0.4F).build();
    public static final Food BLACKBERRY = new Builder().nutrition(4).saturationMod(0.4F).build();
    public static final Food RASPBERRY = new Builder().nutrition(4).saturationMod(0.4F).build();
    public static final Food CRANBERRY = new Builder().nutrition(4).saturationMod(0.4F).build();
    public static final Food ELDERBERRY = new Builder().nutrition(4).saturationMod(0.4F).build();

    public static final Food WILDBERRY = new Builder().nutrition(4).saturationMod(0.4F).effect(() -> new EffectInstance(Effects.POISON, 2, 1), 0.3F).build();

    public static final Food POISON_BOTTLE = new Builder().effect(() -> new EffectInstance(Effects.POISON, 100, 2), 1.0F).build();
}
