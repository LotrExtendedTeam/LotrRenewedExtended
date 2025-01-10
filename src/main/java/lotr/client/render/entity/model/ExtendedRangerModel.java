package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ExtendedRangerModel<E extends ExtendedDunedainRangerEntity> extends ManModel<E> {

    private boolean isCloaked = false;

    private final ModelRenderer cloak;

    public ExtendedRangerModel(boolean smallArms) {
        this(0.0F, false, smallArms);
    }

    public ExtendedRangerModel(float f) {
        this(f, true, false);
    }

    public ExtendedRangerModel(float f, boolean isArmor, boolean smallArms) {
        super(f, isArmor, smallArms);
        this.isCloaked = false;
        if (!isArmor) {
            this.createLongHairModel(0.0F, f);
        }

        this.cloak = new ModelRenderer(this, 0, 0);
        this.cloak.setTexSize(64, 32);
        this.cloak.addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, f);
    }

    @Override
    public void setupAnim(E entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        super.setupAnim(entity, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);

        if (entity.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) {
            if (entity.isCrouching()) {
                this.cloak.z = 1.4F;
                this.cloak.y = 1.85F;
            } else {
                this.cloak.z = 0.0F;
                this.cloak.y = 0.0F;
            }
        } else if (entity.isCrouching()) {
            this.cloak.z = 0.3F;
            this.cloak.y = 0.8F;
        } else {
            this.cloak.z = -1.1F;
            this.cloak.y = -0.85F;
        }
    }

    @Override
    public void prepareMobModel(E ranger, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
        this.isCloaked = ranger.isCloaked();
        super.prepareMobModel(ranger, p_212843_2_, p_212843_3_, p_212843_4_);
    }

    public void renderCloak(E entity, MatrixStack p_228289_1_, IVertexBuilder p_228289_2_, int p_228289_3_, int p_228289_4_) {
        if (entity.isCloaked()) {
            this.cloak.render(p_228289_1_, p_228289_2_, p_228289_3_, p_228289_4_, 1.0F, 1.0F, 1.0F, 0.6F);
        } else {
            this.cloak.render(p_228289_1_, p_228289_2_, p_228289_3_, p_228289_4_);
        }
    }
    @Override
    public void renderToBuffer(MatrixStack matStack, IVertexBuilder buf, int packedLight, int packedOverlay, float r, float g, float b, float a) {
        if (isCloaked) {
            super.renderToBuffer(matStack, buf, packedLight, packedOverlay, r, g, b, 0.6F);
        } else {
            super.renderToBuffer(matStack, buf, packedLight, packedOverlay, r, g, b, a);
        }
    }

}