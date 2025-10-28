package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.entity.model.ExtendedCloakingElfModel;
import lotr.common.entity.npc.ExtendedAbstractCloakingElfEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class ElfCloakLayer<E extends ExtendedAbstractCloakingElfEntity> extends BipedArmorLayer<E, ExtendedCloakingElfModel<E>, ExtendedCloakingElfModel<E>> {

    private final ExtendedCloakingElfModel<E> innerModel;
    private final ExtendedCloakingElfModel<E> outerModel;

    public ElfCloakLayer(IEntityRenderer<E, ExtendedCloakingElfModel<E>> renderer, ExtendedCloakingElfModel<E> innerModel, ExtendedCloakingElfModel<E> outerModel) {
        super(renderer, innerModel, outerModel);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
    }

    @Override
    public void render(MatrixStack matrix, IRenderTypeBuffer renderType, int p_225628_3_, E entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if(entity.getCloakTextureLocation()==null) {
        	return;
        }
    	this.renderCloak(matrix, renderType, entity, EquipmentSlotType.CHEST, p_225628_3_, outerModel);
        this.renderCloak(matrix, renderType, entity, EquipmentSlotType.LEGS, p_225628_3_, innerModel);
        this.renderCloak(matrix, renderType, entity, EquipmentSlotType.FEET, p_225628_3_, outerModel);
        this.renderCloak(matrix, renderType, entity, EquipmentSlotType.HEAD, p_225628_3_, outerModel);
    }

    private void renderModelElf(MatrixStack p_renderModel_1_, IRenderTypeBuffer p_renderModel_2_, int p_renderModel_3_, boolean p_renderModel_4_, ExtendedCloakingElfModel<E> p_renderModel_5_, float p_renderModel_6_, float p_renderModel_7_, float p_renderModel_8_, ResourceLocation p_renderModel_9_) {
        IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_renderModel_2_, RenderType.armorCutoutNoCull(p_renderModel_9_), false, p_renderModel_4_);
        p_renderModel_5_.renderToBuffer(p_renderModel_1_, ivertexbuilder, p_renderModel_3_, OverlayTexture.NO_OVERLAY, p_renderModel_6_, p_renderModel_7_, p_renderModel_8_, 1.0F);
    }

    private void renderCloak(MatrixStack p_241739_1_, IRenderTypeBuffer p_241739_2_, E p_241739_3_, EquipmentSlotType p_241739_4_, int p_241739_5_, ExtendedCloakingElfModel<E> armorModel) {
        this.getParentModel().copyPropertiesTo(armorModel);
        this.setPartVisibility(armorModel, p_241739_4_);
        this.renderModelElf(p_241739_1_, p_241739_2_, p_241739_5_, false, armorModel, 1.0F, 1.0F, 1.0F, p_241739_3_.getCloakTextureLocation());
    }

}
