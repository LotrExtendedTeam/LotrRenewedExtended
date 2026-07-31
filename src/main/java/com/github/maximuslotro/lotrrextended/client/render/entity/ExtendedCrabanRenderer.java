package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedCrabanModel;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedCrabanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedCrabanRenderer extends ExtendedMobRenderer<ExtendedCrabanEntity, ExtendedCrabanModel<ExtendedCrabanEntity>> {

    private static final ResourceLocation LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/birds/craban.png");

    public ExtendedCrabanRenderer(EntityRendererManager p_i4entityRendererManager) {
        super(p_i4entityRendererManager, new ExtendedCrabanModel<>(), 0.3F);
    }

    @Override
    public ResourceLocation getTexture(ExtendedCrabanEntity craban) {
        return LOCATION;
    }

}
