package com.github.maximuslotro.lotrrextended.mixins.lotr.client.event;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.mojang.blaze3d.systems.RenderSystem;

import lotr.client.event.AshfallFog;
import lotr.client.event.LOTRTickHandlerClient;
import lotr.client.event.MistyMountainsMist;
import lotr.client.event.RainMist;
import lotr.client.event.SandstormFog;
import lotr.client.render.world.LOTRDimensionRenderInfo;
import lotr.common.entity.npc.ExtendedBarrowWightEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.FogRenderer.FogType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mixin(LOTRTickHandlerClient.class)
public class MixinLOTRTickHandlerClient {

	@Shadow(remap=false)
	public static float renderPartialTick;

	@Shadow(remap=false)
	@Final
	private RainMist rainMist;
	@Shadow(remap=false)
	@Final
	private MistyMountainsMist mountainsMist;
	@Shadow(remap=false)
	@Final
	private SandstormFog sandstormFog;
	@Shadow(remap=false)
	@Final
	private AshfallFog ashfallFog;
	@Shadow(remap=false)
	@Final
	private Minecraft mc;

	private float wightFogModifier;
	private float prevWightFogModifier;

	@Inject(method = "onClientTick(Lnet/minecraftforge/event/TickEvent$ClientTickEvent;)V",
		at = @At(value = "INVOKE", target = "Llotr/client/event/DateDisplay;update()V", shift = At.Shift.AFTER),
		locals = LocalCapture.CAPTURE_FAILHARD, remap=false)
	public void injectWightFogLogic(ClientTickEvent event, CallbackInfo ci) {
		ClientPlayerEntity player = this.mc.player;
		Entity viewer = this.mc.cameraEntity;
		ClientWorld world = (ClientWorld) player.level;
		prevWightFogModifier = wightFogModifier;
		boolean noWights = world.getEntitiesOfClass(ExtendedBarrowWightEntity.class, viewer.getBoundingBox().inflate(32.0D)).isEmpty();
		if(!noWights) {
			if(wightFogModifier<100) {
				wightFogModifier++;
			}
		}else if (wightFogModifier>0) {
			wightFogModifier--;
		}
	}

	/**
	 * @author Maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems
	 */
	@Overwrite(remap=false)
	@SubscribeEvent
	public void onRenderFog(RenderFogEvent event) {
		Entity viewer = event.getInfo().getEntity();
		World world = viewer.level;
		DimensionType dimension = world.dimensionType();
		world.getBiome(viewer.blockPosition());
		float farPlane = event.getFarPlaneDistance();
		FogType fogType = event.getType();
		DimensionRenderInfo dimRenderInfo = DimensionRenderInfo.forType(dimension);
		if (dimRenderInfo instanceof LOTRDimensionRenderInfo) {
			LOTRDimensionRenderInfo lDimRenderInfo = (LOTRDimensionRenderInfo) dimRenderInfo;
			float[] fogStartEnd = lDimRenderInfo.modifyFogIntensity(farPlane, fogType, viewer);
			float fogStart = fogStartEnd[0];
			float fogEnd = fogStartEnd[1];
			float rain = rainMist.getRainMistStrength(renderPartialTick);
			float mountainsMistFactor;
			float sandFog;
			float wightFog;
			if (rain > 0.0F) {
				mountainsMistFactor = 0.95F;
				sandFog = 0.2F;
				fogStart -= fogStart * rain * mountainsMistFactor;
				fogEnd -= fogEnd * rain * sandFog;
			}

			mountainsMistFactor = mountainsMist.getCurrentMistFactor(viewer, renderPartialTick);
			float ashFog;
			if (mountainsMistFactor > 0.0F) {
				sandFog = 0.95F;
				ashFog = 0.7F;
				fogStart -= fogStart * mountainsMistFactor * sandFog;
				fogEnd -= fogEnd * mountainsMistFactor * ashFog;
			}

			sandFog = sandstormFog.getWeatherFogStrength(renderPartialTick);
			float ashOpacityStart;
			if (sandFog > 0.0F) {
				ashFog = 0.99F;
				ashOpacityStart = 0.75F;
				fogStart -= fogStart * sandFog * ashFog;
				fogEnd -= fogEnd * sandFog * ashOpacityStart;
			}

			ashFog = ashfallFog.getWeatherFogStrength(renderPartialTick);
			if (ashFog > 0.0F) {
				ashOpacityStart = 0.95F;
				float ashOpacityEnd = 0.6F;
				fogStart -= fogStart * ashFog * ashOpacityStart;
				fogEnd -= fogEnd * ashFog * ashOpacityEnd;
			}
			wightFog = this.prevWightFogModifier + (wightFogModifier - prevWightFogModifier) * renderPartialTick;
			wightFog /= 100.0F;
			if (wightFog > 0.0F) {
				fogStart -= fogStart * wightFog * 0.97F;
				fogEnd -= fogEnd * wightFog * 0.75F;
			}

			RenderSystem.fogStart(fogStart);
			RenderSystem.fogEnd(fogEnd);
		}

	}
}
