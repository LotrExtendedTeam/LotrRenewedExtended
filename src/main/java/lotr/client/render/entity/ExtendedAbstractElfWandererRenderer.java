package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.render.entity.layers.ElfCapeLayer;
import lotr.client.render.entity.layers.ElfCloakLayer;
import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ExtendedCloakingElfModel;
import lotr.common.entity.npc.ExtendedAbstractCloakingElfEntity;
import lotr.common.util.CalendarUtil;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedAbstractElfWandererRenderer<E extends ExtendedAbstractCloakingElfEntity> extends LOTRBipedRenderer<E, ExtendedCloakingElfModel<E>> {

	public ExtendedAbstractElfWandererRenderer(EntityRendererManager mgr) {
		super(mgr, ExtendedCloakingElfModel::new, new ExtendedCloakingElfModel<>(0.5F), new ExtendedCloakingElfModel<>(1.0F), 0.5F);
		this.addLayer(new ElfCapeLayer<>(this));
		this.addLayer(new ExtendedHeldCoinLayer<>(this));
		this.addLayer(new ElfCloakLayer<>(this, new ExtendedCloakingElfModel<>(0.5F), new ExtendedCloakingElfModel<>(1.0F)));
	}

	@Override
	protected void scale(E elf, MatrixStack matStack, float f) {
		super.scale(elf, matStack, f);
		if (CalendarUtil.isAprilFools()) {
			float scale = 0.25F;
			matStack.scale(scale, scale, scale);
		}

	}

	@Override
	public abstract ResourceLocation getTextureLocation(ExtendedAbstractCloakingElfEntity elf);

}
