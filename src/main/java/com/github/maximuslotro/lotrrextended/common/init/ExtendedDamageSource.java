package com.github.maximuslotro.lotrrextended.common.init;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class ExtendedDamageSource {

        public static DamageSource causeThrownAxeDamage(Entity axe, @Nullable Entity shooter) {
        return (new IndirectEntityDamageSource("lotrextended.thrownAxe", axe, shooter)).setProjectile();
    }

}
