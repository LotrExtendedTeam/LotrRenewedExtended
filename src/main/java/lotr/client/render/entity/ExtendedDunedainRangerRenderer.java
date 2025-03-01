package lotr.client.render.entity;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.client.render.entity.layers.DunedainRangerCapeLayer;
import lotr.client.render.entity.model.ExtendedRangerModel;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedDunedainRangerRenderer extends LOTRBipedRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>> {
	public static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/dunedain_north/ranger");

	public ExtendedDunedainRangerRenderer(EntityRendererManager mgr) {
		super(mgr, ExtendedRangerModel::new, new ExtendedRangerModel<>(0.5F), new ExtendedRangerModel<>(1.0F), 0.5F);
		this.addLayer(new DunedainRangerCapeLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedDunedainRangerEntity man) {
		return SKINS.getRandomSkin(man);
	}

	@Override
	public void render(ExtendedDunedainRangerEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
		// Update Ranger model with cloaked state
		this.model.isCloaked = pEntity.isCloaked();
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	@Override
	protected void scale(ExtendedDunedainRangerEntity entity, MatrixStack matStack, float f) {
		super.scale(entity, matStack, f);
		//matStack.scale(0.965f, 0.965F, 0.965F);
		matStack.scale(0.965f, 1.05F, 0.965f);
	}

	@Override
	@Nullable
	protected RenderType getRenderType(ExtendedDunedainRangerEntity ranger, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
		ResourceLocation resourceLocation = ExtendedDunedainRangerRenderer.SKINS.getRandomSkin(ranger);
		if (ranger.isCloaked()) {
			return RenderType.itemEntityTranslucentCull(resourceLocation);
		} else {
			return RenderType.entityCutoutNoCull(resourceLocation);
		}
	}
}