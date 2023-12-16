package com.github.maximuslotro.lotroExtended.mixins.common.init;

import lotr.common.entity.npc.HobbitBartenderEntity;
import lotr.common.init.ExtendedNpcs;
import lotr.common.init.LOTREntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraftforge.fml.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(LOTREntities.class)
public class MixinLOTREntities {

    @Shadow(remap=false)
    private static <T extends lotr.common.entity.npc.NPCEntity> RegistryObject<EntityType<T>> regNPC(String key, EntityType.IFactory<T> factory, Supplier<AttributeModifierMap.MutableAttribute> attribFactory, LOTREntities.EntitySizeHolder size, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors) {
       return null;
    }

    @Inject(method = "register()V", at = @At("HEAD"), remap=false)
    private static void bootstrapExtendedItems(CallbackInfo ci) {
        ExtendedNpcs.HOBBIT_BARTENDER = regNPC("hobbit_bartender", HobbitBartenderEntity::new, HobbitBartenderEntity::regAttrs, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16752511, 8010275));
    }

}
