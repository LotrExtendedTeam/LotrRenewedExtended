package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.entity.npc.ExtendedBarrowWrightEntity;

public class ExtendedBarrowWightModel<E extends ExtendedBarrowWrightEntity> extends LOTRBipedModel<E> {

    public ExtendedBarrowWightModel(boolean smallArms) {
        this(0.0F, false, smallArms);
    }

    public ExtendedBarrowWightModel(float f) {
        this(f, true, false);
    }

    public ExtendedBarrowWightModel(float f, boolean isArmor, boolean smallArms) {
        super(f, 0.0f, isArmor, smallArms);
        if (!isArmor) {
            this.createLongHairModel(0.0F, f);
        }
    }

    @Override
    public void setupAnim(E entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        super.setupAnim(entity, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
    }

    @Override
    public void prepareMobModel(E ranger, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
        super.prepareMobModel(ranger, p_212843_2_, p_212843_3_, p_212843_4_);
    }

    @Override
    public void renderToBuffer(MatrixStack matStack, IVertexBuilder buf, int packedLight, int packedOverlay, float r, float g, float b, float a) {
    	super.renderToBuffer(matStack, buf, packedLight, packedOverlay, r, g, b, a);
    }

}