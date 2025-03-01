package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedModelLeatherHat<E extends LivingEntity> extends ExtendedSpecialArmorModel<E> {

	public ExtendedModelLeatherHat(BipedModel<E> referenceBipedModel) {
		this(referenceBipedModel, 1.0F);
	}
	public ExtendedModelLeatherHat(BipedModel<E> referenceBipedModel, float f) {
		super(referenceBipedModel, f);
		clearNonHelmetParts();
		texWidth = 64;
		texHeight = 64;

		head = new ModelRenderer(this, 0, 0);
		head.setPos(0.0F, 0.0F, 0.0F);

		//  below is reference from legacy code but for some reason it seems too big
		//head.addBox(-6.0F, -9.0F, -6.0F, 12.0F, 1.0F, 12.0F, f);
		//head.texOffs(0,14).addBox(-4.0F, -13.0F, -4.0F, 8.0F, 4.0F, 8.0F, f);
		// New model code
		head.texOffs(0, 13).addBox(-4.0F, -12.31F, -4.0F, 8.0F, 4.0F, 8.0F, f+0.2F);
		head.texOffs(0, 0).addBox(-6.0F, -7.0F, -6.0F, 12.0F, 0.5F, 12.0F, f);
		head.texOffs(0, 25).addBox(-4.5F, -10.0F, -4.5F, 9.0F, 2.0F, 9.0F, f);

	}

	// TODO: Add feather as option to render based on item NBT data (will require adding a new class
	//  for the Leather Hat) below is 1.7.10 feather rendering code:
	/*
    if (LOTRItemLeatherHat.hasFeather(this.hatItem)) {
      this.field_78116_c.func_78794_c(f5);
      GL11.glScalef(0.375F, 0.375F, 0.375F);
      GL11.glRotatef(130.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.25F, 1.5F, 0.75F);
      GL11.glRotatef(-45.0F, 0.0F, 0.0F, 1.0F);
      int featherColor = LOTRItemLeatherHat.getFeatherColor(this.hatItem);
      r = (featherColor >> 16 & 0xFF) / 255.0F;
      g = (featherColor >> 8 & 0xFF) / 255.0F;
      b = (featherColor & 0xFF) / 255.0F;
      GL11.glColor3f(r, g, b);
      if (entity instanceof EntityLivingBase) {
        RenderManager.field_78727_a.field_78721_f.func_78443_a((EntityLivingBase)entity, feather, 0);
      } else {
        RenderManager.field_78727_a.field_78721_f.func_78443_a((EntityLivingBase)(Minecraft.func_71410_x()).field_71439_g, feather, 0);
      }
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }
	 */
}
