package lotr.client.render.entity.model;

import lotr.common.entity.npc.ElfEntity;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ExtendedCloakingCloakedElfModel<E extends ElfEntity> extends ExtendedCloakingElfModel<E>{

	public ExtendedCloakingCloakedElfModel(boolean smallArms) {
		this(0.0F, false, smallArms);
	}

	public ExtendedCloakingCloakedElfModel(float f) {
		 this(f, true, false);
	}

	public ExtendedCloakingCloakedElfModel(float f, boolean isArmor, boolean smallArms) {
		super(f, isArmor, smallArms);
	}
	/*
    Hide elven wanderers long hair because it is not visible under the hood
    */
   @Override
   protected void createLongHairModel(float headRotationY, float f) {
       this.hat = new ModelRenderer(this, 56, 0);
   }

}
