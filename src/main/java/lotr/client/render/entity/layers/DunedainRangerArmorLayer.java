package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.client.render.entity.model.ExtendedRangerModel;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DunedainRangerArmorLayer extends BipedArmorLayer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>, ExtendedRangerModel<ExtendedDunedainRangerEntity>> {

    private final ExtendedRangerModel<ExtendedDunedainRangerEntity> innerModel;
    private final ExtendedRangerModel<ExtendedDunedainRangerEntity> outerModel;

    private boolean isCloaked = false;

    public DunedainRangerArmorLayer(IEntityRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>> renderer, ExtendedRangerModel<ExtendedDunedainRangerEntity> innerModel, ExtendedRangerModel<ExtendedDunedainRangerEntity> outerModel) {
        super(renderer, innerModel, outerModel);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
    }

    @Override
    public void render(MatrixStack matrix, IRenderTypeBuffer renderType, int p_225628_3_, ExtendedDunedainRangerEntity entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        this.isCloaked = entity.isCloaked();

        this.renderArmorPieceRanger(matrix, renderType, entity, EquipmentSlotType.CHEST, p_225628_3_, outerModel);
        this.renderArmorPieceRanger(matrix, renderType, entity, EquipmentSlotType.LEGS, p_225628_3_, innerModel);
        this.renderArmorPieceRanger(matrix, renderType, entity, EquipmentSlotType.FEET, p_225628_3_, outerModel);
        this.renderArmorPieceRanger(matrix, renderType, entity, EquipmentSlotType.HEAD, p_225628_3_, outerModel);
    }

    private void renderModelRanger(MatrixStack p_renderModel_1_, IRenderTypeBuffer p_renderModel_2_, int p_renderModel_3_, boolean p_renderModel_4_, ExtendedRangerModel<ExtendedDunedainRangerEntity> p_renderModel_5_, float p_renderModel_6_, float p_renderModel_7_, float p_renderModel_8_, ResourceLocation p_renderModel_9_) {
        if (isCloaked) {
            IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_renderModel_2_, RenderType.itemEntityTranslucentCull(p_renderModel_9_), false, p_renderModel_4_);
            p_renderModel_5_.renderToBuffer(p_renderModel_1_, ivertexbuilder, p_renderModel_3_, OverlayTexture.NO_OVERLAY, p_renderModel_6_, p_renderModel_7_, p_renderModel_8_, 0.6F);
        } else {
            IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_renderModel_2_, RenderType.armorCutoutNoCull(p_renderModel_9_), false, p_renderModel_4_);
            p_renderModel_5_.renderToBuffer(p_renderModel_1_, ivertexbuilder, p_renderModel_3_, OverlayTexture.NO_OVERLAY, p_renderModel_6_, p_renderModel_7_, p_renderModel_8_, 1.0F);
        }
    }

    private void renderArmorPieceRanger(MatrixStack p_241739_1_, IRenderTypeBuffer p_241739_2_, ExtendedDunedainRangerEntity p_241739_3_, EquipmentSlotType p_241739_4_, int p_241739_5_, ExtendedRangerModel<ExtendedDunedainRangerEntity> armorModel) {
        ItemStack itemstack = p_241739_3_.getItemBySlot(p_241739_4_);
        if (itemstack.getItem() instanceof ArmorItem) {
            ArmorItem armoritem = (ArmorItem)itemstack.getItem();
            if (armoritem.getSlot() == p_241739_4_) {
                armorModel = this.getArmorModelHook(p_241739_3_, itemstack, p_241739_4_, armorModel);
                this.getParentModel().copyPropertiesTo(armorModel);
                this.setPartVisibility(armorModel, p_241739_4_);
                boolean flag1 = itemstack.hasFoil();
                if (armoritem instanceof IDyeableArmorItem) {
                    int i = ((IDyeableArmorItem)armoritem).getColor(itemstack);
                    float f = (float)(i >> 16 & 255) / 255.0F;
                    float f1 = (float)(i >> 8 & 255) / 255.0F;
                    float f2 = (float)(i & 255) / 255.0F;
                    this.renderModelRanger(p_241739_1_, p_241739_2_, p_241739_5_, flag1, armorModel, f, f1, f2, this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, (String)null));
                    this.renderModelRanger(p_241739_1_, p_241739_2_, p_241739_5_, flag1, armorModel, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, "overlay"));
                } else {
                    this.renderModelRanger(p_241739_1_, p_241739_2_, p_241739_5_, flag1, armorModel, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, (String)null));
                }
            }
        }

    }

}
