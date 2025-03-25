package com.github.maximuslotro.lotrrextended.mixins.lotr.common.event;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.event.LOTREventHandler;
import lotr.common.init.LOTRDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @reason Renewed used reflection to handle fox spawn types and this breaks in Java 9+
 * @Mixin (LOTREventHandler.class)
 * */
@Mixin(LOTREventHandler.class)
public class MixinLOTREventHandlerJavaPatch {

	/**
	 * @author Maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems
	 * @Overwrite (remap=false)
	 * @param event The LivingUpdateEvent checked for foxes with no biome check
	 */
    //@Inject(method = "onLivingUpdate(Lnet/minecraftforge/event/entity/living/LivingEvent$LivingUpdateEvent;)V", at = @At("HEAD"), remap=false)
	@Overwrite(remap=false)
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.level;
		if (!world.isClientSide && entity instanceof FoxEntity && LOTRDimensions.isModDimension(world)) {
			FoxEntity fox = (FoxEntity) entity;
			String biomeCheckKey = String.format("%s:%s", "lotr", "FoxBiomeCheck");
			if (!fox.getPersistentData().getBoolean(biomeCheckKey)) {
				FoxEntity.Type initialFoxType = fox.getFoxType();
				FoxEntity.Type newFoxType = initialFoxType;
				BlockPos entityPos = entity.blockPosition();
				Biome biome = world.getBiome(entity.blockPosition());
				if (biome.getTemperature(entityPos) < 0.15F) {
					newFoxType = FoxEntity.Type.SNOW;
				}

				if (newFoxType != initialFoxType) {
					// Extended Start
					fox.setFoxType(newFoxType);
					// Extended End
				}
				fox.getPersistentData().putBoolean(biomeCheckKey, true);
			}
		}
	}
}
