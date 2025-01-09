package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.entity.model.ExtendedElfWandererModel;
import lotr.common.entity.npc.ExtendedAbstractElfWandererEntity;
import lotr.common.entity.npc.ExtendedGaladhrimWandererEntity;
import lotr.common.entity.npc.ExtendedRivendellWandererEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class ElfWandererCloakLayer<E extends ExtendedAbstractElfWandererEntity> extends BipedArmorLayer<E, ExtendedElfWandererModel<E>, ExtendedElfWandererModel<E>> {

    private final ExtendedElfWandererModel<E> innerModel;
    private final ExtendedElfWandererModel<E> outerModel;

    public ElfWandererCloakLayer(IEntityRenderer<E, ExtendedElfWandererModel<E>> renderer, ExtendedElfWandererModel<E> innerModel, ExtendedElfWandererModel<E> outerModel) {
        super(renderer, innerModel, outerModel);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
    }

    @Override
    public void render(MatrixStack matrix, IRenderTypeBuffer renderType, int p_225628_3_, E entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        this.renderCloak(matrix, renderType, entity, EquipmentSlotType.CHEST, p_225628_3_, outerModel);
        this.renderCloak(matrix, renderType, entity, EquipmentSlotType.LEGS, p_225628_3_, innerModel);
        this.renderCloak(matrix, renderType, entity, EquipmentSlotType.FEET, p_225628_3_, outerModel);
        this.renderCloak(matrix, renderType, entity, EquipmentSlotType.HEAD, p_225628_3_, outerModel);
    }

    private void renderModelElf(MatrixStack p_renderModel_1_, IRenderTypeBuffer p_renderModel_2_, int p_renderModel_3_, boolean p_renderModel_4_, ExtendedElfWandererModel<E> p_renderModel_5_, float p_renderModel_6_, float p_renderModel_7_, float p_renderModel_8_, ResourceLocation p_renderModel_9_) {
        IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_renderModel_2_, RenderType.armorCutoutNoCull(p_renderModel_9_), false, p_renderModel_4_);
        p_renderModel_5_.renderToBuffer(p_renderModel_1_, ivertexbuilder, p_renderModel_3_, OverlayTexture.NO_OVERLAY, p_renderModel_6_, p_renderModel_7_, p_renderModel_8_, 1.0F);
    }

    private void renderCloak(MatrixStack p_241739_1_, IRenderTypeBuffer p_241739_2_, E p_241739_3_, EquipmentSlotType p_241739_4_, int p_241739_5_, ExtendedElfWandererModel<E> armorModel) {
        this.getParentModel().copyPropertiesTo(armorModel);
        this.setPartVisibility(armorModel, p_241739_4_);
        if (p_241739_3_ instanceof ExtendedRivendellWandererEntity) {
            this.renderModelElf(p_241739_1_, p_241739_2_, p_241739_5_, false, armorModel, 1.0F, 1.0F, 1.0F, new ResourceLocation("lotr", "textures/entity/elf/rivendell_trader_cloak.png"));
        } else if (p_241739_3_ instanceof ExtendedGaladhrimWandererEntity) {
            this.renderModelElf(p_241739_1_, p_241739_2_, p_241739_5_, false, armorModel, 1.0F, 1.0F, 1.0F, new ResourceLocation("lotr", "textures/entity/elf/galadhrim_trader_cloak.png"));
        }
    }

}
