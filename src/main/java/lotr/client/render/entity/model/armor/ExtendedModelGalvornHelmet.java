package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedModelGalvornHelmet<E extends LivingEntity> extends ExtendedSpecialArmorModel<E> {
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r10;
	private final ModelRenderer cube_r11;
	private final ModelRenderer cube_r12;
	private final ModelRenderer cube_r13;

	public ExtendedModelGalvornHelmet(BipedModel<E> referenceBipedModel) {
		this(referenceBipedModel, 1.0F);
	}
	
	public ExtendedModelGalvornHelmet(BipedModel<E> referenceBipedModel, float f) {
		super(referenceBipedModel, f, 64, 32);
		clearNonHelmetParts();

		head = new ModelRenderer(this, 0, 0);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 9.0F, 8.0F, 1.0F, false);
		head.texOffs(4, 4).addBox(-0.5F, -11.5F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		head.texOffs(0, 5).addBox(-0.5F, -11.0F, 3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		head.texOffs(0, 5).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		head.texOffs(1, 1).addBox(-0.5F, -6.0F, 5.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		head.texOffs(42, 1).addBox(-1.0F, -12.9641F, -1.0967F, 2.0F, 2.0F, 4.0F, 0.0F, false);
		head.texOffs(32, 4).addBox(0.0F, -15.0F, -2.0F, 0.0F, 3.0F, 9.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, -4.0F, 8.0F);
		head.addChild(cube_r1);
		setRotationAngle(cube_r1, -1.7017F, 0.0F, 0.0F);
		cube_r1.texOffs(36, 14).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 5.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, -9.0F, 8.0F);
		head.addChild(cube_r2);
		setRotationAngle(cube_r2, -1.1345F, 0.0F, 0.0F);
		cube_r2.texOffs(35, 10).addBox(0.0F, -1.0F, -3.0F, 0.0F, 3.0F, 6.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.5F, -9.5F, -6.43F);
		head.addChild(cube_r3);
		setRotationAngle(cube_r3, 2.3562F, 0.0F, 0.0F);
		cube_r3.texOffs(24, 2).addBox(-1.0F, -0.0607F, -0.9487F, 1.0F, 0.0F, 1.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(0.5F, -8.75F, -5.75F);
		head.addChild(cube_r4);
		setRotationAngle(cube_r4, 1.1345F, 0.0F, 0.0F);
		cube_r4.texOffs(24, 2).addBox(-1.0F, -0.0607F, -0.8787F, 1.0F, 0.0F, 1.0F, 0.03F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(0.0F, 1.7371F, 6.4082F);
		head.addChild(cube_r5);
		setRotationAngle(cube_r5, -1.5708F, 0.0F, 0.0F);
		cube_r5.texOffs(40, -4).addBox(0.0F, 0.0F, -1.7F, 0.0F, 1.0F, 4.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(0.5F, -1.7629F, 6.9082F);
		head.addChild(cube_r6);
		setRotationAngle(cube_r6, -1.8326F, 0.0F, 0.0F);
		cube_r6.texOffs(52, 11).addBox(-1.0F, 0.0F, -1.7F, 1.0F, 1.0F, 4.0F, 0.03F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(0.5F, -5.7933F, 7.4951F);
		head.addChild(cube_r7);
		setRotationAngle(cube_r7, -1.6581F, 0.0F, 0.0F);
		cube_r7.texOffs(52, 6).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 1.0F, 4.0F, 0.25F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(0.0F, -9.5221F, 6.1019F);
		head.addChild(cube_r8);
		setRotationAngle(cube_r8, -1.2217F, 0.0F, 0.0F);
		cube_r8.texOffs(44, 7).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 4.0F, 0.0F, false);

		cube_r9 = new ModelRenderer(this);
		cube_r9.setPos(0.0F, -11.4239F, 3.8402F);
		head.addChild(cube_r9);
		setRotationAngle(cube_r9, -0.4363F, 0.0F, 0.0F);
		cube_r9.texOffs(32, 7).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 4.0F, 0.0F, false);

		cube_r10 = new ModelRenderer(this);
		cube_r10.setPos(0.0F, -11.3511F, -3.2527F);
		head.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.2618F, 0.0F, 0.0F);
		cube_r10.texOffs(30, 1).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 4.0F, 0.0F, false);

		cube_r11 = new ModelRenderer(this);
		cube_r11.setPos(0.0F, -11.0F, -5.0F);
		head.addChild(cube_r11);
		setRotationAngle(cube_r11, 0.7854F, 0.0F, 0.0F);
		cube_r11.texOffs(50, 0).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 3.0F, 0.12F, false);
		cube_r11.texOffs(24, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 3.0F, 0.12F, false);

		cube_r12 = new ModelRenderer(this);
		cube_r12.setPos(0.0F, -12.0F, -5.0F);
		head.addChild(cube_r12);
		setRotationAngle(cube_r12, 0.7854F, 0.0F, 0.0F);
		cube_r12.texOffs(32, -4).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 4.0F, 0.0F, false);

		cube_r13 = new ModelRenderer(this);
		cube_r13.setPos(0.5F, -10.75F, -6.5F);
		head.addChild(cube_r13);
		setRotationAngle(cube_r13, 0.7854F, 0.0F, 0.0F);
		cube_r13.texOffs(25, 5).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.25F, false);
	}
}