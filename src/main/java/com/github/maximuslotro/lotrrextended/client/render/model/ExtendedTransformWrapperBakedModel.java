package com.github.maximuslotro.lotrrextended.client.render.model;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

// TODO add ability to handle each transform type separately
public class ExtendedTransformWrapperBakedModel implements IDynamicBakedModel {
	private static final ItemOverrideList SHARED_OVERRIDE_LIST = new ItemOverrideList() {
		@Override
		public IBakedModel resolve(IBakedModel model, ItemStack stack, ClientWorld world, LivingEntity entity) {
			if(model instanceof ExtendedTransformWrapperBakedModel) {
				ExtendedTransformWrapperBakedModel wrapper = (ExtendedTransformWrapperBakedModel) model;
				IBakedModel resolvedGUI = wrapper.guiModel.getOverrides().resolve(wrapper.guiModel, stack, world, entity);
				IBakedModel resolvedHand = wrapper.heldModel.getOverrides().resolve(wrapper.heldModel, stack, world, entity);
				if(resolvedGUI != wrapper.guiModel || resolvedHand != wrapper.heldModel) {
					return new ExtendedTransformWrapperBakedModel(resolvedGUI, resolvedHand);
				}
			}
			return model;
		}
	};
	private final IBakedModel guiModel;
	private final IBakedModel heldModel;

	public ExtendedTransformWrapperBakedModel(IBakedModel guiModel, IBakedModel heldModel) {
		this.guiModel = guiModel;
		this.heldModel = heldModel;
	}

	@Override 
	public ItemOverrideList getOverrides() { 
		return SHARED_OVERRIDE_LIST;
	}

	@Override
	public IBakedModel handlePerspective(ItemCameraTransforms.TransformType pTransformType, MatrixStack pMatrixStack) {
		IBakedModel targetModel;
		switch (pTransformType) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
			targetModel = heldModel;
			break;
		default:
			targetModel = guiModel;
			break;
		}
		return ForgeHooksClient.handlePerspective(targetModel, pTransformType, pMatrixStack);
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData) {
		return guiModel.getQuads(state, side, rand, extraData);
	}

	@Override public boolean useAmbientOcclusion() { return guiModel.useAmbientOcclusion(); }
	@Override public boolean isGui3d() { return guiModel.isGui3d(); }
	@Override public boolean usesBlockLight() { return guiModel.usesBlockLight(); }
	@Override public boolean isCustomRenderer() { return false; }
	@Override @Nonnull public TextureAtlasSprite getParticleIcon() { return guiModel.getParticleIcon(); }
	@Override @Nonnull public ItemCameraTransforms getTransforms() { return guiModel.getTransforms(); }
}