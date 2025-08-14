package com.github.maximuslotro.lotrextended.mixins.lotr.client.render.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

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
	 * Renders a flat (horizontal) surface of liquid within a container.
	 * Used for drink vessels like mugs or goblets.
	 *
	 * @param matStack The current matrix stack.
	 * @param vb The vertex builder for rendering.
	 * @param icon The texture to use for the liquid.
	 * @param uvMin Minimum UV coordinate.
	 * @param uvMax Maximum UV coordinate.
	 * @param x0 Starting X coordinate.
	 * @param z0 Starting Z coordinate.
	 * @param x1 Ending X coordinate.
	 * @param z1 Ending Z coordinate.
	 * @param y Height at which to render the liquid surface.
	 * @param light Combined light value.
	 * @param overlay Overlay light texture value.
	 * @Shadow (remap=false)
	 */
	@Shadow(remap=false)
	private void renderLiquidSurface(MatrixStack matStack, IVertexBuilder vb, TextureAtlasSprite icon, int uvMin, int uvMax, float x0, float z0, float x1, float z1, float y, int light, int overlay) {}

	/**
	 * Renders a vertical or angled surface of liquid.
	 * This variant is typically used for angled or complex shaped vessels.
	 *
	 * @param matStack The current matrix stack.
	 * @param vb The vertex builder for rendering.
	 * @param icon The texture to use for the liquid.
	 * @param uvMin Minimum UV coordinate.
	 * @param uvMax Maximum UV coordinate.
	 * @param width Width of the rendered surface.
	 * @param y Height at which to render the liquid surface.
	 * @param light Combined light value.
	 * @param overlay Overlay light texture value.
	 * @Shadow (remap=false
	 */
	@Shadow(remap=false)
	private void renderLiquidSurface(MatrixStack matStack, IVertexBuilder vb, TextureAtlasSprite icon, int uvMin, int uvMax, float width, float y, int light, int overlay) {}

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
				this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 10, 3.0F, 5.25F, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.GOLDEN_GOBLET || vesselType == VesselType.SILVER_GOBLET || vesselType == VesselType.COPPER_GOBLET || vesselType == VesselType.WOODEN_CUP) {
				this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 9, 2.25F, 6.0F, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.ALE_HORN || vesselType == VesselType.GOLDEN_ALE_HORN) {
				this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 9, 9.5F, 6.875F, 12.5F, 9.125F, 6.375F, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.valueOf("SKULL_GOBLET")) {
				this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 12, 6.0F, 9.0F, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.valueOf("GLASS_BOTTLE")) {
				this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 10, 6.5F, 4.5F, combinedLight, combinedOverlay);
				this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 9, 9.5F, 6.875F, 12.5F, 9.125F, 6.375F, combinedLight, combinedOverlay);

			}
			matStack.popPose();
			RenderSystem.disableBlend();
			RenderSystem.enableCull();
			RenderSystem.disableRescaleNormal();
		}

	}
}
