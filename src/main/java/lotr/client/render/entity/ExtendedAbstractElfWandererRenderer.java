package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.render.entity.layers.ElfWandererCapeLayer;
import lotr.client.render.entity.layers.ElfWandererCloakLayer;
import lotr.client.render.entity.model.ExtendedElfWandererModel;
import lotr.common.entity.npc.ExtendedAbstractElfWandererEntity;
import lotr.common.util.CalendarUtil;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedAbstractElfWandererRenderer<E extends ExtendedAbstractElfWandererEntity> extends LOTRBipedRenderer<E, ExtendedElfWandererModel<E>> {

    public ExtendedAbstractElfWandererRenderer(EntityRendererManager mgr) {
        super(mgr, ExtendedElfWandererModel::new, new ExtendedElfWandererModel<>(0.5F), new ExtendedElfWandererModel<>(1.0F), 0.5F);
        this.addLayer(new ElfWandererCapeLayer<>(this));
        this.addLayer(new ElfWandererCloakLayer<>(this, new ExtendedElfWandererModel<>(0.5F), new ExtendedElfWandererModel<>(1.0F)));
    }

    protected void preRenderCallback(E elf, MatrixStack matStack, float f) {
        super.preRenderCallback(elf, matStack, f);
        if (CalendarUtil.isAprilFools()) {
            float scale = 0.25F;
            matStack.scale(scale, scale, scale);
        }

    }

    public abstract ResourceLocation getTextureLocation(ExtendedAbstractElfWandererEntity elf);

}
