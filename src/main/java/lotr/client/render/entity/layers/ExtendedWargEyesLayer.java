package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.entity.model.WargModel;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class ExtendedWargEyesLayer<T extends WargEntity> extends AbstractEyesLayer<T, WargModel<T>> {
	private static final RenderType BROWN_EYES = RenderType.eyes(new ResourceLocation("lotrextended:textures/entity/warg/eyes/brown.png"));
	private static final RenderType GRAY_EYES = RenderType.eyes(new ResourceLocation("lotrextended:textures/entity/warg/eyes/gray.png"));
	private static final RenderType BLACK_EYES = RenderType.eyes(new ResourceLocation("lotrextended:textures/entity/warg/eyes/black.png"));
	private static final RenderType SILVER_EYES = RenderType.eyes(new ResourceLocation("lotrextended:textures/entity/warg/eyes/silver.png"));
	private static final RenderType WHITE_EYES = RenderType.eyes(new ResourceLocation("lotrextended:textures/entity/warg/eyes/white.png"));


	public ExtendedWargEyesLayer(IEntityRenderer<T, WargModel<T>> p_i226039_1_) {
		super(p_i226039_1_);
	}

	@Override
	public void render(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		IVertexBuilder ivertexbuilder = pBuffer.getBuffer(this.renderType(pLivingEntity));
		this.getParentModel().renderToBuffer(pMatrixStack, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}

	public RenderType renderType(T entity) {
		if(entity.getWargType().getId()==1) {
			return GRAY_EYES;
		} else if(entity.getWargType().getId()==2) {
			return BLACK_EYES;
		} else if(entity.getWargType().getId()==3) {
			return SILVER_EYES;
		} else if(entity.getWargType().getId()==4) {
			return WHITE_EYES;
		}
		return BROWN_EYES;
	}

	// Not used as overridden
	@Override
	public RenderType renderType() {
		return BLACK_EYES;
	}
}
