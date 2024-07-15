package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedDuckModel<T extends Entity> extends AgeableModel<T> {
	   private final ModelRenderer head;
	   private final ModelRenderer body;
	   private final ModelRenderer leg0;
	   private final ModelRenderer leg1;
	   private final ModelRenderer wing0;
	   private final ModelRenderer wing1;
	   private final ModelRenderer beak;

	   public ExtendedDuckModel() {
	      int i = 16;
	      this.head = new ModelRenderer(this, 0, 0);
	      this.head.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F);
	      this.head.setPos(0.0F, 15.0F, -4.0F);
	      this.beak = new ModelRenderer(this, 14, 0);
	      this.beak.addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F);
	      this.beak.setPos(0.0F, 15.0F, -4.0F);
	      this.body = new ModelRenderer(this, 0, 9);
	      this.body.addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F);
	      this.body.setPos(0.0F, 16.0F, 0.0F);
	      this.leg0 = new ModelRenderer(this, 26, 0);
	      this.leg0.addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
	      this.leg0.setPos(-2.0F, 19.0F, 1.0F);
	      this.leg1 = new ModelRenderer(this, 26, 0);
	      this.leg1.addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
	      this.leg1.setPos(1.0F, 19.0F, 1.0F);
	      this.wing0 = new ModelRenderer(this, 24, 13);
	      this.wing0.addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
	      this.wing0.setPos(-4.0F, 13.0F, 0.0F);
	      this.wing1 = new ModelRenderer(this, 24, 13);
	      this.wing1.addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
	      this.wing1.setPos(4.0F, 13.0F, 0.0F);
	   }

	   protected Iterable<ModelRenderer> headParts() {
	      return ImmutableList.of(this.head, this.beak);
	   }

	   protected Iterable<ModelRenderer> bodyParts() {
	      return ImmutableList.of(this.body, this.leg0, this.leg1, this.wing0, this.wing1);
	   }

	   public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
	      this.head.xRot = p_225597_6_ * ((float)Math.PI / 180F);
	      this.head.yRot = p_225597_5_ * ((float)Math.PI / 180F);
	      this.beak.xRot = this.head.xRot;
	      this.beak.yRot = this.head.yRot;
	      this.body.xRot = ((float)Math.PI / 2F);
	      this.leg0.xRot = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
	      this.leg1.xRot = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_;
	      this.wing0.zRot = p_225597_4_;
	      this.wing1.zRot = -p_225597_4_;
	   }
	}
