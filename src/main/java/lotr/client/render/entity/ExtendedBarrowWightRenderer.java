package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.client.render.entity.model.ExtendedBarrowWightModel;
import lotr.common.entity.npc.ExtendedBarrowWrightEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

public class ExtendedBarrowWightRenderer extends LOTRBipedRenderer<ExtendedBarrowWrightEntity, ExtendedBarrowWightModel<ExtendedBarrowWrightEntity>> {
    public static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/dunedain_north/ranger");

    public ExtendedBarrowWightRenderer(EntityRendererManager mgr) {
        super(mgr, ExtendedBarrowWightModel::new, new ExtendedBarrowWightModel<ExtendedBarrowWrightEntity>(0.5F), new ExtendedBarrowWightModel<ExtendedBarrowWrightEntity>(1.0F), 0.5F);

    }

    @Override
    public ResourceLocation getTextureLocation(ExtendedBarrowWrightEntity man) {
        return SKINS.getRandomSkin(man);
    }

    @Override
	protected void scale(ExtendedBarrowWrightEntity entity, MatrixStack matStack, float f) {
		super.scale(entity, matStack, f);
		//matStack.scale(0.965f, 0.965F, 0.965F);
		matStack.scale(0.965f, 1.05F, 0.965f);
	}

    @Override
    @Nullable
    protected RenderType getRenderType(ExtendedBarrowWrightEntity ranger, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        ResourceLocation resourceLocation = ExtendedBarrowWightRenderer.SKINS.getRandomSkin(ranger);
        return RenderType.entityCutoutNoCull(resourceLocation);
    }

}
