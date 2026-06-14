package com.github.maximuslotro.lotrextended.mixins.lotr.client.render.entity;

import com.github.maximuslotro.lotrrextended.client.render.entity.layers.ExtendedWargArmorLayer;
import com.github.maximuslotro.lotrrextended.client.render.entity.layers.ExtendedWargArmorStrapLayer;
import com.github.maximuslotro.lotrrextended.client.render.entity.layers.ExtendedWargEyesLayer;
import com.github.maximuslotro.lotrrextended.client.render.entity.layers.ExtendedWargSaddleLayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.client.render.entity.WargRenderer;
import lotr.client.render.entity.model.WargModel;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;

@Mixin(WargRenderer.class)
public abstract class MixinWargRenderer extends MobRenderer<WargEntity, WargModel<WargEntity>> {
	// Stripped on build
	public MixinWargRenderer(EntityRendererManager p_i50961_1_) {
		super(p_i50961_1_, null, 0.0f);
	}

	@Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererManager;)V", at = @At("TAIL"), remap=false)
	public <M extends WargRenderer> void constructor(EntityRendererManager mgr, CallbackInfo info) {
		this.addLayer(new ExtendedWargEyesLayer<>((WargRenderer)(Object)this));
		this.addLayer(new ExtendedWargSaddleLayer((WargRenderer)(Object)this));
		this.addLayer(new ExtendedWargArmorLayer((WargRenderer)(Object)this));
		this.addLayer(new ExtendedWargArmorStrapLayer((WargRenderer)(Object)this));
	}
}
