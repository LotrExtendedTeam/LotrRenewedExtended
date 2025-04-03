package com.github.maximuslotro.lotrrextended.mixins.lotr.client.render.entity;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.AbstractOrcRenderer;
import lotr.common.entity.npc.OrcEntity;
import lotr.common.util.CalendarUtil;
import net.minecraft.util.ResourceLocation;

@Mixin(AbstractOrcRenderer.class)
public abstract class MixinAbstractOrcRenderer{
	@Unique
	private static final RandomTextureVariants SKINS_APRIL = RandomTextureVariants.loadSkinsList("lotrextended", "textures/entity/orc/orc_april");

	@Final
	@Shadow(remap=false)
	private static RandomTextureVariants SKINS;
	/**
	 * @author maximuslotro
	 * @reason Add April fools orc skins
	 * */
	@Overwrite(remap=false)
	public ResourceLocation getEntityTexture(OrcEntity orc) {
		if(CalendarUtil.isAprilFools()) {
			return SKINS_APRIL.getRandomSkin(orc);
		}
		return SKINS.getRandomSkin(orc);
	}
}
