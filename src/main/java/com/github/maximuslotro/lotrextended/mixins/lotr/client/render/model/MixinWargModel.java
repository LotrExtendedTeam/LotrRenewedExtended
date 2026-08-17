package com.github.maximuslotro.lotrextended.mixins.lotr.client.render.model;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedMountedArmorItem;
import com.google.common.collect.ImmutableList;

import lotr.client.render.entity.model.WargModel;
import lotr.common.entity.npc.ExtendedIRidableEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.item.ItemStack;

@Mixin(WargModel.class)
public abstract class MixinWargModel<E extends WargEntity> extends SegmentedModel<E>{
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer frontBody;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer backHairL;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer backHairR;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer head;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer earR;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer earL;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer maneTop;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer maneR;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer maneL;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer frontLegR;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer frontLegL;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer backBody;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer tail;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer backLegR;
	@Final
	@Mutable
	@Shadow(remap=false)
	private ModelRenderer backLegL;
	@Final
	@Mutable
	@Shadow(remap=false)
	private List<ModelRenderer> topLevelParts;

	@Inject(method = "<init>(F)V", at = @At("RETURN"), remap=false)
	private void onConstructorReturn(float f, CallbackInfo ci) {
		// Replace model renderers, cubes, children.
		frontBody = new ModelRenderer(this);
		frontBody.setPos(0.0F, 7.5F, 1.0F);
		frontBody.texOffs(0, 38).addBox(-6.0F, -5.5F, -15.0F, 12.0F, 11.0F, 15.0F, f, false);

		backHairR = new ModelRenderer(this);
		backHairR.setPos(-1.0F, -5.5F, -8.0F);
		frontBody.addChild(backHairR);
		setRotationAngle(backHairR, 0.0F, 0.0F, -0.3927F);
		backHairR.texOffs(36, 13).addBox(0.0F, -4.0F, -7.0F, 0.0F, 4.0F, 14.0F, 0.0F, true);

		backHairL = new ModelRenderer(this);
		backHairL.setPos(1.0F, -5.5F, -8.0F);
		frontBody.addChild(backHairL);
		setRotationAngle(backHairL, 0.0F, 0.0F, 0.3927F);
		backHairL.texOffs(36, 13).addBox(0.0F, -4.0F, -7.0F, 0.0F, 4.0F, 14.0F, 0.0F, true);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 3.5F, -15.0F);
		frontBody.addChild(head);
		head.texOffs(0, 0).addBox(-5.0F, -8.0F, -8.0F, 10.0F, 8.0F, 8.0F, f, false);
		head.texOffs(0, 16).addBox(-3.0F, -5.0F, -14.0F, 6.0F, 5.0F, 6.0F, f, false);

		maneR = new ModelRenderer(this);
		maneR.setPos(-5.0F, -4.0F, -3.0F);
		head.addChild(maneR);
		setRotationAngle(maneR, 0.0F, 0.7854F, 0.0F);
		maneR.texOffs(28, 0).addBox(-5.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, false);

		maneL = new ModelRenderer(this);
		maneL.setPos(5.0F, -4.0F, -3.0F);
		head.addChild(maneL);
		setRotationAngle(maneL, 0.0F, 2.3562F, 0.0F);
		maneL.texOffs(28, 0).addBox(-5.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, false);

		maneTop = new ModelRenderer(this);
		maneTop.setPos(0.0F, -8.0F, -1.0F);
		head.addChild(maneTop);
		setRotationAngle(maneTop, -0.7854F, 0.0F, 0.0F);
		maneTop.texOffs(45, 31).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 5.0F, 0.0F, 0.0F, false);

		earR = new ModelRenderer(this);
		earR.setPos(-3.3F, -9.25F, -2.6F);
		head.addChild(earR);
		setRotationAngle(earR, -0.2618F, -1.0472F, 0.0F);
		earR.texOffs(36, 4).addBox(-1.0F, -2.5F, -2.0F, 2.0F, 5.0F, 4.0F, f, true);

		earL = new ModelRenderer(this);
		earL.setPos(3.3F, -9.25F, -2.6F);
		head.addChild(earL);
		setRotationAngle(earL, -0.2618F, 1.0472F, 0.0F);
		earL.texOffs(36, 4).addBox(-1.0F, -2.5F, -2.0F, 2.0F, 5.0F, 4.0F, f, false);

		frontLegR = new ModelRenderer(this);
		frontLegR.setPos(-5.0F, -2.0F, -9.5F);
		frontBody.addChild(frontLegR);
		frontLegR.texOffs(66, 0).addBox(-2.0F, -4.5F, -4.5F, 4.0F, 11.0F, 8.0F, f, false);
		frontLegR.texOffs(70, 19).addBox(-2.0F, 6.5F, -1.5F, 4.0F, 12.0F, 4.0F, f, false);

		frontLegL = new ModelRenderer(this);
		frontLegL.setPos(5.0F, -2.0F, -9.5F);
		frontBody.addChild(frontLegL);
		frontLegL.texOffs(66, 0).addBox(-2.0F, -4.5F, -4.5F, 4.0F, 11.0F, 8.0F, f, true);
		frontLegL.texOffs(70, 19).addBox(-2.0F, 6.5F, -1.5F, 4.0F, 12.0F, 4.0F, f, true);

		backBody = new ModelRenderer(this);
		backBody.setPos(0.0F, 7.5F, 1.0F);
		backBody.texOffs(54, 39).addBox(-5.0F, -4.8F, -2.0F, 10.0F, 10.0F, 15.0F, f, false);

		tail = new ModelRenderer(this);
		tail.setPos(0.0F, -3.3F, 12.75F);
		backBody.addChild(tail);
		setRotationAngle(tail, -0.7854F, 0.0F, 0.0F);
		tail.texOffs(92, 33).addBox(-2.5F, -0.5F, -0.25F, 5.0F, 5.0F, 13.0F, f, false);

		backLegR = new ModelRenderer(this);
		backLegR.setPos(-4.0F, 1.5F, 8.0F);
		backBody.addChild(backLegR);
		backLegR.texOffs(102, 16).addBox(-2.0F, 5.0F, 0.0F, 4.0F, 10.0F, 4.0F, f, false);
		backLegR.texOffs(99, 0).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 9.0F, 7.0F, f, false);

		backLegL = new ModelRenderer(this);
		backLegL.setPos(4.0F, 1.5F, 8.0F);
		backBody.addChild(backLegL);
		backLegL.texOffs(102, 16).addBox(-2.0F, 5.0F, 0.0F, 4.0F, 10.0F, 4.0F, f, true);
		backLegL.texOffs(99, 0).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 9.0F, 7.0F, f, true);
		topLevelParts = ImmutableList.of(backBody, frontBody);
	}

	@Unique
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	/**
	 * @author Maximuslotro
	 * @param pEntity Entity to setup
	 * @param pLimbSwing Limb swing tick
	 * @param pLimbSwingAmount Limb swing tick
	 * @param pPartialTick Partial tick count
	 * @reason Allow injecting into this empty method
	 * */
	@Overwrite(remap=false)
	public void setLivingAnimations(E pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
		ItemStack itemstack = ((ExtendedIRidableEntity)pEntity).getArmor();
		if (itemstack.getItem() instanceof ExtendedMountedArmorItem) {
			backHairL.visible=false;
			backHairR.visible=false;
			maneTop.visible=false;
			maneL.visible=false;
			maneR.visible=false;
		}else {
			backHairL.visible=true;
			backHairR.visible=true;
			maneTop.visible=true;
			maneL.visible=true;
			maneR.visible=true;
		}
	}
}
