package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedStructureSpawnerModel;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Renderer for the {@link ExtendedStructureSpawnerEntity}, responsible for rendering
 * the custom model with texture and optional debug bounding boxes in development mode.
 * <p>
 * This renderer handles visibility checks to hide the entity if it is invisible to the player
 * or when the GUI is hidden. In developer mode, it visualizes the entity's check and spawn areas
 * as colored bounding boxes using line rendering.
 * </p>
 */
@OnlyIn(Dist.CLIENT)
public class ExtendedStructureSpawnerRenderer extends EntityRenderer<ExtendedStructureSpawnerEntity> {
    /** The texture resource location for the entity model. */
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/imgnazgulscreamman.png");
    /** The model instance used for rendering the spawner entity. */
	private ExtendedStructureSpawnerModel model =  new ExtendedStructureSpawnerModel();
	/**
     * Constructs a new renderer for the ExtendedStructureSpawnerEntity.
     *
     * @param entityRenderDispatcher The entity render manager handling this renderer.
     */
	public ExtendedStructureSpawnerRenderer(EntityRendererManager entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}

	/**
     * Renders the spawner entity with its model and texture.
     * <p>
     * If the entity is invisible to the player or the GUI is hidden, the model is not rendered.
     * When developer mode is enabled, renders bounding boxes for the check area and spawn area.
     * </p>
     *
     * @param pEntity The spawner entity to render.
     * @param pEntityYaw The yaw rotation of the entity.
     * @param pPartialTicks Partial tick time for smooth animation interpolation.
     * @param pMatrixStack The matrix stack for rendering transformations.
     * @param pBuffer The buffer source for vertex builders.
     * @param pPackedLight Packed light information for rendering brightness.
     */
	@Override
	public void render(ExtendedStructureSpawnerEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
		pMatrixStack.pushPose();
		Minecraft minecraft = Minecraft.getInstance();
		boolean shouldHide = pEntity.isInvisibleTo(minecraft.player) || minecraft.options.hideGui;
		RenderType rendertype = shouldHide ? null : model.renderType(TEXTURE_LOCATION);
		if (rendertype != null) {
			IVertexBuilder ivertexbuilder = pBuffer.getBuffer(rendertype);
			this.model.renderExtendedToBuffer(pEntity, pMatrixStack, ivertexbuilder, pPartialTicks, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			if (ExtendedClientConfig.enableClientDevMode.get()) {
				pMatrixStack.pushPose();
				//RenderSystem.disableDepthTest();
				RenderSystem.disableCull();
				// Setup rendering with lines
				IVertexBuilder ivertexBuilder = pBuffer.getBuffer(RenderType.lines());
				RenderSystem.lineWidth(10.0f);
				IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
				if (buffer == null)
					return;
				IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);
				// Translate to entity frame
				pMatrixStack.translate(-pEntity.getX(), -pEntity.getY(), -pEntity.getZ());
				// Render check box
				pMatrixStack.pushPose();
				renderLineBox(pMatrixStack, builder, pEntity.getCheckArea(), 51, 204, 255, 255);
				pMatrixStack.popPose();
				// Render spawn box
				pMatrixStack.pushPose();
				renderLineBox(pMatrixStack, builder, pEntity.getSpawnArea(), 255, 255, 0, 255);
				pMatrixStack.popPose();
				RenderSystem.disableDepthTest();
				//RenderSystem.disableCull();
				buffer.endBatch(RenderType.LINES);
				pMatrixStack.popPose();
			}
		}
		pMatrixStack.popPose();
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	/**
     * Gets the texture location for the given spawner entity.
     *
     * @param entity The spawner entity instance.
     * @return The texture resource location used for rendering the entity.
     */
	@Override
	public ResourceLocation getTextureLocation(ExtendedStructureSpawnerEntity entity) {
		return TEXTURE_LOCATION;
	}

	/**
     * Determines whether the entity's name tag should be shown.
     * The name tag is hidden if the entity is invisible to the player or the GUI is hidden.
     *
     * @param pEntity The spawner entity.
     * @return {@code true} if the name tag should be shown; {@code false} otherwise.
     */
	@Override
	protected boolean shouldShowName(ExtendedStructureSpawnerEntity pEntity) {
		Minecraft minecraft = Minecraft.getInstance();
		return ExtendedClientConfig.enableClientDevMode.get() && !minecraft.options.hideGui;
	}

	/**
     * Renders a colored bounding box around an {@link AxisAlignedBB} using integer RGBA colors.
     * This method is a modified version of {@link net.minecraft.client.renderer.WorldRenderer#renderLineBox} to support integer colors.
     *
     * @param pMatrixStack The matrix stack for transformations.
     * @param pBuffer The vertex builder to output vertices to.
     * @param pAabb The axis-aligned bounding box to render.
     * @param pRed Red color component (0-255).
     * @param pGreen Green color component (0-255).
     * @param pBlue Blue color component (0-255).
     * @param pAlpha Alpha transparency component (0-255).
     */
	public static void renderLineBox(MatrixStack pMatrixStack, IVertexBuilder pBuffer, AxisAlignedBB pAabb, int pRed, int pGreen, int pBlue, int pAlpha) {
		renderLineBox(pMatrixStack, pBuffer, pAabb.minX, pAabb.minY, pAabb.minZ, pAabb.maxX, pAabb.maxY, pAabb.maxZ, pRed, pGreen, pBlue, pAlpha);
	}

	/**
     * Renders a colored bounding box defined by min and max coordinates using integer RGBA colors.
     * This method is a modified version of {@link net.minecraft.client.renderer.WorldRenderer#renderLineBox} to support integer colors.
     *
     * @param pMatrixStack The matrix stack for transformations.
     * @param pBuffer The vertex builder to output vertices to.
     * @param pMinX Minimum X coordinate.
     * @param pMinY Minimum Y coordinate.
     * @param pMinZ Minimum Z coordinate.
     * @param pMaxX Maximum X coordinate.
     * @param pMaxY Maximum Y coordinate.
     * @param pMaxZ Maximum Z coordinate.
     * @param pRed Red color component (0-255).
     * @param pGreen Green color component (0-255).
     * @param pBlue Blue color component (0-255).
     * @param pAlpha Alpha transparency component (0-255).
     */
	public static void renderLineBox(MatrixStack pMatrixStack, IVertexBuilder pBuffer, double pMinX, double pMinY, double pMinZ, double pMaxX, double pMaxY, double pMaxZ, int pRed, int pGreen, int pBlue, int pAlpha) {
		Matrix4f matrix4f = pMatrixStack.last().pose();
		float f = (float)pMinX;
		float f1 = (float)pMinY;
		float f2 = (float)pMinZ;
		float f3 = (float)pMaxX;
		float f4 = (float)pMaxY;
		float f5 = (float)pMaxZ;
		pBuffer.vertex(matrix4f, f, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
	}
}