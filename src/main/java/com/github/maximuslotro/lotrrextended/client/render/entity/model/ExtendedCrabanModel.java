package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ExtendedCrabanModel<T extends Entity> extends EntityModel<T> {
    private final ModelRenderer Root, body, bodyntail, body_r1, tail, right_wing, left_wing, right_leg, left_leg, head, longbeak_r1;

    public ExtendedCrabanModel() {
        texWidth=32; texHeight=32;

        Root=new ModelRenderer(this); Root.setPos(0F,17.5F,-4F);
        body=new ModelRenderer(this); body.setPos(0F,-1F,3F); Root.addChild(body);
        bodyntail=new ModelRenderer(this); bodyntail.setPos(0F,2.5F,-2.5F); body.addChild(bodyntail);

        body_r1=new ModelRenderer(this);
        body_r1.setPos(0F,2F,6F);
        bodyntail.addChild(body_r1);
        setRotationAngle(body_r1,1.0472F,0F,0F);
        body_r1.texOffs(0,0).addBox(-1.5F,-7.5F,-2.5F,3F,6F,3F,0F,false);

        tail=new ModelRenderer(this);
        tail.setPos(0F,1.1F,4.7F);
        bodyntail.addChild(tail);
        setRotationAngle(tail,1.1781F,0F,0F);
        tail.texOffs(12,5).addBox(-1F,-1F,0F,2F,4F,0F,0F,false);

        right_wing=new ModelRenderer(this);
        right_wing.setPos(-1.5F,-0.1F,0.7F);
        bodyntail.addChild(right_wing);
        setRotationAngle(right_wing,1.1781F,0F,0F);
        right_wing.texOffs(0,9).addBox(-0.5F,-0.7071F,-1.4289F,1F,5F,3F,0F,false);

        left_wing=new ModelRenderer(this);
        left_wing.setPos(1.5F,-0.1F,0.7F);
        bodyntail.addChild(left_wing);
        setRotationAngle(left_wing,1.1781F,0F,0F);
        left_wing.texOffs(8,9).addBox(-0.5F,-0.7071F,-1.4289F,1F,5F,3F,0F,false);

        right_leg=new ModelRenderer(this);
        right_leg.setPos(1F,3F,3F);
        bodyntail.addChild(right_leg);
        right_leg.texOffs(16,11).addBox(-0.5F,0F,0F,1F,2F,0F,0F,false);
        right_leg.texOffs(16,7).addBox(-0.5F,2F,-1F,1F,0F,1F,0F,false);

        left_leg=new ModelRenderer(this);
        left_leg.setPos(-1F,3F,3F);
        bodyntail.addChild(left_leg);
        left_leg.texOffs(16,8).addBox(-0.5F,2F,-1F,1F,0F,1F,0F,false);
        left_leg.texOffs(16,9).addBox(-0.5F,0F,0F,1F,2F,0F,0F,false);

        head=new ModelRenderer(this);
        head.setPos(0F,2F,-3F);
        body.addChild(head);
        head.texOffs(12,0).addBox(-1F,-2.5F,-1F,2F,3F,2F,0F,false);
        head.texOffs(16,5).addBox(-0.5F,-2F,-2F,1F,1F,1F,0F,false);

        longbeak_r1=new ModelRenderer(this);
        longbeak_r1.setPos(0F,-1.5F,-1.25F);
        head.addChild(longbeak_r1);
        setRotationAngle(longbeak_r1,0.48F,0F,0F);
        longbeak_r1.texOffs(20,4).addBox(-0.5F,-0.5F,-1.45F,1F,1F,2F,0F,false);
    }

    protected Iterable<ModelRenderer> headParts(){ return ImmutableList.of(head); }
    protected Iterable<ModelRenderer> bodyParts(){ return ImmutableList.of(Root); }

    @Override public void setupAnim(T e,float ls,float lsa,float age,float yaw,float pitch) {

    }

    public void setRotationAngle(ModelRenderer m,float x,float y,float z){
        m.xRot=x;m.yRot=y;m.zRot=z;
    }

    @Override
    public void renderToBuffer(MatrixStack pMatrixStack, IVertexBuilder pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.headParts().forEach((p_228228_8_) -> {
            p_228228_8_.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        });
        this.bodyParts().forEach((p_228227_8_) -> {
            p_228227_8_.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        });
    }
}
