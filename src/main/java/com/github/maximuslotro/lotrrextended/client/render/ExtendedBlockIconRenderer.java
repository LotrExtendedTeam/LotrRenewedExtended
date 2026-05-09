package com.github.maximuslotro.lotrrextended.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is largely derived from BlockExporter
 * (https://github.com/KazuOfficial/blockexporter), licensed under the MIT License.
 *
 * Original implementation by KazuOfficial.
 *
 * Most non-Minecraft-specific methods in this class are based on or adapted from that project,
 * with modifications for integration and extended functionality in this mod.
 *
 * The original MIT License applies to the reused portions from BlockExporter.
 * See LICENSE file for full details.
 */
public class ExtendedBlockIconRenderer implements AutoCloseable {
	private final int textureSize;
	private final File exportDirectory;
	private final Minecraft client;
	private final ExecutorService fileWriteExecutor;
	private final ConcurrentLinkedQueue<ItemStack> failedExports;
	private final Framebuffer framebuffer;
	/**
	 * Creates a new renderer for exporting item icons to disk.
	 *
	 * This constructor is largely derived from BlockExporter
	 * 
	 * <p>This initializes:
	 * <ul>
	 *   <li>A dedicated {@link Framebuffer} for off-screen rendering</li>
	 *   <li>A thread pool for asynchronous file writing</li>
	 *   <li>An export directory inside the Minecraft game directory</li>
	 * </ul>
	 *
	 * @param textureSize The width and height (in pixels) of the exported item textures.
	 *                    Typical values are 16, 32, 64, or higher for higher resolution exports.
	 *
	 * @throws RuntimeException if the export directory cannot be created
	 */
	public ExtendedBlockIconRenderer(int textureSize) {
		this.textureSize = textureSize;
		this.client = Minecraft.getInstance();
		this.exportDirectory = new File(client.gameDirectory, "lotrextended_item_images");
		int coreCount = Runtime.getRuntime().availableProcessors();
		this.fileWriteExecutor = Executors.newFixedThreadPool(Math.max(2, coreCount / 2));
		this.failedExports = new ConcurrentLinkedQueue<>();

		try {
			if (!exportDirectory.exists()) {
				exportDirectory.mkdirs();
			}
			ExtendedLog.info("Created export directory: {}", exportDirectory.getAbsolutePath());
		} catch (Exception e) {
			ExtendedLog.error("Failed to create export directory: {}", exportDirectory.getAbsolutePath(), e);
			throw new RuntimeException("Failed to create export directory", e);
		}

		this.framebuffer = new Framebuffer(this.textureSize, this.textureSize, true, false);
		this.framebuffer.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}
	/**
	 * Exports a batch of {@link ItemStack} icons.
	 *
	 * This method is largely derived from BlockExporter
	 * 
	 * <p>Each item is rendered sequentially on the render thread, while file writing
	 * is delegated asynchronously to a thread pool for performance.
	 *
	 * @param stacks The list of item stacks to export
	 * @param completionCounter A thread-safe counter incremented after each item finishes processing
	 */
	public void exportItemsBatch(List<ItemStack> stacks, AtomicInteger completionCounter) {
		if (stacks == null || stacks.isEmpty()) {
			return;
		}

		for (ItemStack stack : stacks) {
			if (stack == null || stack.isEmpty()) {
				completionCounter.incrementAndGet();
				continue;
			}
			exportSingleItem(stack, completionCounter);
		}
	}
	/**
	 * Renders and exports a single {@link ItemStack} to a PNG image.
	 *
	 *	This method is largely derived from BlockExporter
	 *
	 * <p>This method performs:
	 * <ol>
	 *   <li>Framebuffer binding and OpenGL state setup</li>
	 *   <li>Item rendering using Minecraft's GUI render pipeline</li>
	 *   <li>Pixel readback via {@link GL11#glReadPixels}</li>
	 *   <li>Asynchronous disk write</li>
	 * </ol>
	 *
	 * <p><b>Important:</b> This method must be called on the render thread due to OpenGL usage.
	 *
	 * @param stack The item stack to render
	 * @param completionCounter A counter incremented when processing completes
	 */
	private void exportSingleItem(ItemStack stack, AtomicInteger completionCounter) {
		ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
		if (id == null) {
			ExtendedLog.warn("Could not get registry name for item: {}", stack.getItem());
			this.failedExports.add(stack);
			completionCounter.incrementAndGet();
			return;
		}

		try {
			this.framebuffer.bindWrite(true);

			RenderSystem.viewport(0, 0, this.textureSize, this.textureSize);

			RenderSystem.clearColor(0.0f, 0.0f, 0.0f, 0.0f);
			RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, false);

			RenderSystem.enableDepthTest();
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

			RenderSystem.matrixMode(GL11.GL_PROJECTION);
			RenderSystem.pushMatrix();
			RenderSystem.loadIdentity();
			RenderSystem.ortho(0.0, this.textureSize, this.textureSize, 0.0, -1000.0, 1000.0);

			RenderSystem.matrixMode(GL11.GL_MODELVIEW);
			RenderSystem.pushMatrix();
			RenderSystem.loadIdentity();

			try {
				this.renderGuiItem(stack);
			} catch (Exception e) {
				ExtendedLog.warn("Failed to render item {}: {}", id, e.getMessage());
				this.failedExports.add(stack);
				completionCounter.incrementAndGet();
				return;
			}

			RenderSystem.popMatrix();
			RenderSystem.matrixMode(GL11.GL_PROJECTION);
			RenderSystem.popMatrix();
			RenderSystem.matrixMode(GL11.GL_MODELVIEW);

			ByteBuffer buffer = BufferUtils.createByteBuffer(this.textureSize * this.textureSize * 4);
			GL11.glReadPixels(0, 0, this.textureSize, this.textureSize, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

			BufferedImage image = new BufferedImage(this.textureSize, this.textureSize, BufferedImage.TYPE_INT_ARGB);
			for (int y = 0; y < this.textureSize; y++) {
				for (int x = 0; x < this.textureSize; x++) {
					int index = (y * this.textureSize + x) * 4;
					int r = buffer.get(index) & 0xFF;
					int g = buffer.get(index + 1) & 0xFF;
					int b = buffer.get(index + 2) & 0xFF;
					int a = buffer.get(index + 3) & 0xFF;
					int argb = (a << 24) | (r << 16) | (g << 8) | b;
					image.setRGB(x, this.textureSize - y - 1, argb);
				}
			}

			this.fileWriteExecutor.execute(() -> {
				try {
					File filePath = new File(new File(exportDirectory, id.getNamespace()+"_"+textureSize), id.getPath() + ".png");
					filePath.getParentFile().mkdirs();
					ImageIO.write(image, "PNG", filePath);
				} catch (IOException e) {
					ExtendedLog.error("Failed to save exported item image: {}", id, e);
					this.failedExports.add(stack);
				} finally {
					completionCounter.incrementAndGet();
				}
			});

			this.framebuffer.unbindWrite();

		} catch (Exception e) {
			ExtendedLog.error("Failed to export item: {}", id, e);
			this.failedExports.add(stack);
			completionCounter.incrementAndGet();

			this.framebuffer.unbindWrite();
		}
	}

	/**
	 * Resolves the {@link IBakedModel} for the given {@link ItemStack}.
	 *
	 * <p>This method is required because Minecraft's implementation of it is not static.</p>
	 *
	 * @param pStack The item stack
	 * @return The resolved baked model, or the missing model if resolution fails
	 */
	public IBakedModel getModel(ItemStack pStack) {
		Minecraft mc = Minecraft.getInstance();
		Item item = pStack.getItem();
		IBakedModel ibakedmodel;
		if (item == Items.TRIDENT) {
			ibakedmodel = mc.getItemRenderer().itemModelShaper.getModelManager().getModel(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
		} else {
			ibakedmodel = mc.getItemRenderer().itemModelShaper.getItemModel(pStack);
		}

		IBakedModel ibakedmodel1 = ibakedmodel.getOverrides().resolve(ibakedmodel, pStack, null, null);
		return ibakedmodel1 == null ? mc.getItemRenderer().itemModelShaper.getModelManager().getMissingModel() : ibakedmodel1;
	}
	/**
	 * Renders an item using Minecraft's GUI rendering pipeline into the active framebuffer.
	 *
	 * <p>This method is adapted from Minecraft's {@code ItemRenderer} with modifications:
	 * <ul>
	 *   <li>Custom translation to center the item in the framebuffer</li>
	 *   <li>Scaling based on {@code textureSize}</li>
	 *   <li>Adjusted transformations for consistent export resolution</li>
	 * </ul>
	 *
	 * @param pStack The item stack to render
	 */
	protected void renderGuiItem(ItemStack pStack) {
		IBakedModel pBakedmodel = getModel(pStack);
		Minecraft mc = Minecraft.getInstance();
		RenderSystem.pushMatrix();
		mc.getItemRenderer().textureManager.bind(AtlasTexture.LOCATION_BLOCKS);
		mc.getItemRenderer().textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
		RenderSystem.enableRescaleNormal();
		RenderSystem.enableAlphaTest();
		RenderSystem.defaultAlphaFunc();
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		// Extended Start
		RenderSystem.translatef(this.textureSize / 2.0f, this.textureSize / 2.0f, 100.0F );
		//RenderSystem.translatef(8.0F, 8.0F, 0.0F);
		// Extended End
		RenderSystem.scalef(1.0F, -1.0F, 1.0F);
		// Extended Start
		RenderSystem.scalef(this.textureSize, this.textureSize, this.textureSize);
		// Extended End
		MatrixStack matrixstack = new MatrixStack();
		IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
		boolean flag = !pBakedmodel.usesBlockLight();
		if (flag) {
			RenderHelper.setupForFlatItems();
		}

		mc.getItemRenderer().render(pStack, ItemCameraTransforms.TransformType.GUI, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, pBakedmodel);
		irendertypebuffer$impl.endBatch();
		RenderSystem.enableDepthTest();
		if (flag) {
			RenderHelper.setupFor3DItems();
		}

		RenderSystem.disableAlphaTest();
		RenderSystem.disableRescaleNormal();
		RenderSystem.popMatrix();
	}
	/**
	 * Returns a snapshot of all item stacks that failed to export.
	 *
	 * <p>This includes failures from:
	 * <ul>
	 *   <li>Rendering errors</li>
	 *   <li>File write errors</li>
	 *   <li>Invalid registry names</li>
	 * </ul>
	 *
	 * @return A new list containing failed item stacks
	 */
	public List<ItemStack> getFailedExports() {
		return new ArrayList<>(this.failedExports);
	}
	/**
	 * Shuts down this renderer and releases allocated resources.
	 *
	 * <p>This includes:
	 * <ul>
	 *   <li>Stopping the file writing thread pool</li>
	 *   <li>Destroying the framebuffer</li>
	 * </ul>
	 *
	 * <p>Once closed, this instance should not be reused.
	 */
	@Override
	public void close() {
		this.fileWriteExecutor.shutdown();

		if (this.framebuffer != null) {
			this.framebuffer.destroyBuffers();
		}
	}
} 