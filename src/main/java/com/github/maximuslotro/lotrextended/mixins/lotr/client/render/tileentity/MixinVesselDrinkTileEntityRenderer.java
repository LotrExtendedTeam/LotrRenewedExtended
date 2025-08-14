package com.github.maximuslotro.lotrextended.mixins.lotr.client.render.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.client.render.utils.ExtendedAbstractPlaneRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.model.vessel.VesselDrinkModel;
import lotr.client.render.tileentity.VesselDrinkTileEntityRenderer;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.item.VesselType;
import lotr.common.tileentity.VesselDrinkTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
/**
 * Mixin to extend and customize the rendering of {@link VesselDrinkTileEntityRenderer} to support additional
 * drink container types and custom rendering logic.
 *
 * <p>This mixin enables rendering support for vessel types not originally handled due to hardcoded logic in the base renderer.</p>
 *
 * @param <T> the type of {@link VesselDrinkTileEntity} being rendered
 * @Mixin (VesselDrinkTileEntityRenderer.class)
 */
@Mixin(VesselDrinkTileEntityRenderer.class)
public abstract class MixinVesselDrinkTileEntityRenderer<T extends VesselDrinkTileEntity> extends TileEntityRenderer<T> {

	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param dispatcher A placeholder variable instance of TileEntityRendererDispatcher
	 * */
	public MixinVesselDrinkTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	/**
	 * Returns the correct rotation direction for aligning the rendered vessel model
	 * to match the blockstate's JSON configuration.
	 *
	 * @param stateFacing The direction the blockstate is facing.
	 * @return The adjusted facing direction.
	 * @Shadow (remap=false)
	 */
	@Shadow(remap=false)
	private Direction getRotationToMatchBlockstateJson(Direction stateFacing) {return null;}

	/**
	 * Overwrites the base renderer to add logic for new and custom vessel types.
	 * This handles rendering of liquid surfaces based on the {@link VesselType}.
	 *
	 * <p>This overwrite removes the hardcoded dependency on a fixed set of vessel types in the original renderer.</p>
	 * 
	 * @author maximuslotro
	 * @reason Adds logic for rendering new drink containers, as VesselType is hardcoded
	 * @param vessel The tile entity instance representing the vessel.
	 * @param partialTicks Tick delta used for interpolation.
	 * @param matStack The current matrix stack.
	 * @param buffer The rendering buffer.
	 * @param combinedLight The combined light value.
	 * @param combinedOverlay The overlay texture value.
	 * @Overwrite (remap=false)
	 */
	@Override
	@Overwrite(remap=false)
	public void render(T vessel, float partialTicks, MatrixStack matStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		if (!vessel.isEmpty()) {
			ItemStack vesselItem = vessel.getVesselItem();
			VesselType vesselType = vessel.getVesselType();
			RenderSystem.enableRescaleNormal();
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			matStack.pushPose();
			matStack.translate(0.5D, 0.0D, 0.5D);
			Direction rotateDir = getRotationToMatchBlockstateJson(vessel.getBlockState().getValue(VesselDrinkBlock.FACING));
			matStack.mulPose(Vector3f.YP.rotationDegrees(rotateDir.toYRot()));
			matStack.translate(-0.5D, 0.0D, -0.5D);
			matStack.scale(0.0625F, 0.0625F, 0.0625F);
			TextureAtlasSprite liquidIcon = VesselDrinkModel.getLiquidIconFor(vesselItem);
			RenderType renderType = RenderType.entitySolid(liquidIcon.atlas().location());
			IVertexBuilder vb = buffer.getBuffer(renderType);
			if(vesselType == VesselType.WOODEN_MUG || vesselType == VesselType.CERAMIC_MUG) {
				ExtendedAbstractPlaneRenderer.renderFaceUp(matStack, vb, liquidIcon, 3, 5, 6.5f, 6.5f, 9.5f, 9.5f, 5.25f, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.GOLDEN_GOBLET || vesselType == VesselType.SILVER_GOBLET || vesselType == VesselType.COPPER_GOBLET || vesselType == VesselType.WOODEN_CUP) {
				ExtendedAbstractPlaneRenderer.renderFaceUp(matStack, vb, liquidIcon, 3, 4.5f, 6.875f, 6.875f, 9.125f, 9.125f, 6.0f, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.ALE_HORN || vesselType == VesselType.GOLDEN_ALE_HORN) {
				ExtendedAbstractPlaneRenderer.renderFaceUp(matStack, vb, liquidIcon, 3, 4.5f, 9.5F, 6.875F, 12.5F, 9.125F, 6.375F, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.valueOf("SKULL_GOBLET")) {
				ExtendedAbstractPlaneRenderer.renderFaceUp(matStack, vb, liquidIcon, 3, 6, 5, 5, 11, 11, 9, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.valueOf("GLASS_BOTTLE")) {
				ExtendedAbstractPlaneRenderer.renderCube(matStack, vb, liquidIcon, 3, 5, 6.0f, 0.5f, 6.0f, 10.0f, 3.5f, 10.0f, combinedLight, combinedOverlay);
				ExtendedAbstractPlaneRenderer.renderCube(matStack, vb, liquidIcon, 3, 4, 7.0f, 3.5f, 7.0f, 9.0f, 5.0f, 9.0f, combinedLight, combinedOverlay);
				//ExtendedAbstractPlaneRenderer.renderCube(matStack, vb, liquidIcon, 3, 5, 6.5f, 5.0f, 6.5f, 9.5f, 5.75f, 9.5f, combinedLight, combinedOverlay);
			}
			matStack.popPose();
			RenderSystem.disableBlend();
			RenderSystem.enableCull();
			RenderSystem.disableRescaleNormal();
		}

	}
}
