package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedCrabanEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class ExtendedCrabanModel<T extends ExtendedCrabanEntity> extends EntityModel<T> {
    private final ModelRenderer Root, body, bodyntail, body_r1, tail, right_wing, left_wing, right_leg, left_leg, head, longbeak_r1;

    public ExtendedCrabanModel() {
        texWidth=32; texHeight=32;
        Root = new ModelRenderer(this);
        Root.setPos(0F,17.5F,-4F);

        body = new ModelRenderer(this);
        body.setPos(0F,-1F,3F);
        Root.addChild(body);

        bodyntail = new ModelRenderer(this);
        bodyntail.setPos(0F,2.5F,-2.5F);
        body.addChild(bodyntail);

        body_r1 = new ModelRenderer(this);
        body_r1.setPos(0F,2F,6F);
        body_r1.texOffs(0,0).addBox(-1.5F,-7.5F,-2.5F,3F,6F,3F,0F,false);
        bodyntail.addChild(body_r1);
        setRotationAngle(body_r1,1.0472F,0F,0F);

        tail = new ModelRenderer(this);
        tail.setPos(0F,1.1F,4.7F);
        setRotationAngle(tail,1.1781F,0F,0F);
        tail.texOffs(12,5).addBox(-1F,-1F,0F,2F,4F,0F,0F,false);
        bodyntail.addChild(tail);

        right_wing = new ModelRenderer(this);
        right_wing.setPos(-1.5F,-0.1F,0.7F);
        bodyntail.addChild(right_wing);
        setRotationAngle(right_wing,1.1781F,0F,0F);
        right_wing.texOffs(0,9).addBox(-0.5F,-0.7071F,-1.4289F,1F,5F,3F,0F,false);

        left_wing = new ModelRenderer(this);
        left_wing.setPos(1.5F,-0.1F,0.7F);
        bodyntail.addChild(left_wing);
        setRotationAngle(left_wing,1.1781F,0F,0F);
        left_wing.texOffs(8,9).addBox(-0.5F,-0.7071F,-1.4289F,1F,5F,3F,0F,false);

        right_leg = new ModelRenderer(this);
        right_leg.setPos(1F,3F,3F);
        bodyntail.addChild(right_leg);
        right_leg.texOffs(16,11).addBox(-0.5F,0F,0F,1F,2F,0F,0F,false);
        right_leg.texOffs(16,7).addBox(-0.5F,2F,-1F,1F,0F,1F,0F,false);

        left_leg = new ModelRenderer(this);
        left_leg.setPos(-1F,3F,3F);
        bodyntail.addChild(left_leg);
        left_leg.texOffs(16,8).addBox(-0.5F,2F,-1F,1F,0F,1F,0F,false);
        left_leg.texOffs(16,9).addBox(-0.5F,0F,0F,1F,2F,0F,0F,false);

        head = new ModelRenderer(this);
        head.setPos(0F,2F,-3F);
        body.addChild(head);
        head.texOffs(12,0).addBox(-1F,-2.5F,-1F,2F,3F,2F,0F,false);
        head.texOffs(16,5).addBox(-0.5F,-2F,-2F,1F,1F,1F,0F,false);

        longbeak_r1 =new ModelRenderer(this);
        longbeak_r1.setPos(0F,-1.5F,-1.25F);
        head.addChild(longbeak_r1);
        setRotationAngle(longbeak_r1,0.48F,0F,0F);
        longbeak_r1.texOffs(20,4).addBox(-0.5F,-0.5F,-1.45F,1F,1F,2F,0F,false);
    }


    @Override
    public void setupAnim(T e, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        float wing = 0.0F;
        if (!e.isOnGround() && e.isFlying()) {
            wing = MathHelper.sin(pAgeInTicks * 1.2F) * 0.6F +.2F;
        }
        setupPose(pLimbSwing, pLimbSwingAmount, pNetHeadYaw, pHeadPitch, wing);
    }

    public void setupCageAnim(double ageInTicks) {
        float wing = (float) Math.sin(ageInTicks * 1.2D) * 0.6F + 0.2F;
        setupPose(0.0F, 0.0F, 0.0F, 0.0F, wing);
    }

    private void setupPose(float pLimbSwing, float pLimbSwingAmount, float pNetHeadYaw, float pHeadPitch, float wing) {
        this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
        this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
        this.left_leg.xRot = MathHelper.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
        this.right_leg.xRot = MathHelper.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;

        this.right_wing.zRot = wing;
        this.left_wing.zRot = -wing;
    }

    public void setRotationAngle(ModelRenderer m,float x,float y,float z){
        m.xRot=x;m.yRot=y;m.zRot=z;
    }

    @Override
    public void renderToBuffer(MatrixStack pMatrixStack, IVertexBuilder pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        Root.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }
}
