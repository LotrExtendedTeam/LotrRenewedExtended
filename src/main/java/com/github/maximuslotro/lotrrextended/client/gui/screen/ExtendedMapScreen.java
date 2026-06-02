package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.world.map.MapSettingsManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedMapScreen extends Screen {
	private ResourceLocation MAP_TEXTURE;

	private int mapWidth; 
	private int mapHeight;
	private int mapOriginX;
	private int mapOriginZ;
	private int scaleFactor;

	private float zoom = 1.0f;
	private final float minZoom = 0.125f;
	private final float maxZoom = 5.0f;

	private float offsetX = 0;
	private float offsetZ = 0;

	private boolean isDragging = false;
	private double lastMouseX;
	private double lastMouseY;

	public ExtendedMapScreen() {
		super(new StringTextComponent("CONQUEST"));
	}

	@Override
	protected void init() {
		super.init();
		MapSettingsManager mapSettings = MapSettingsManager.clientInstance();
		this.mapOriginX = mapSettings.getCurrentLoadedMap().getOriginX();
		this.mapOriginZ = mapSettings.getCurrentLoadedMap().getOriginZ();
		this.mapWidth = mapSettings.getCurrentLoadedMap().getWidth();
		this.mapHeight = mapSettings.getCurrentLoadedMap().getHeight();
		this.scaleFactor = mapSettings.getCurrentLoadedMap().getScaleFactor();
		this.MAP_TEXTURE = new ResourceLocation("lotr", "textures/map/loaded_map.png");

		if (this.minecraft != null && this.minecraft.player != null) {
			double playerWorldX = this.minecraft.player.getX();
			double playerWorldZ = this.minecraft.player.getZ();

			// Center logic: Screen Center minus scaled player position
			this.offsetX = (this.width / 2) - (float)(cordToMapPixelX(this.minecraft.player.getX()) * zoom);
			this.offsetZ = (this.height / 2) - (float)(cordToMapPixelZ(this.minecraft.player.getZ()) * zoom);
		}

		clampBoundaries();
	}

	private double cordToMapPixelX(double cord) {
		return this.mapOriginX + (cord / scaleFactor);
	}
	private double cordToMapPixelZ(double cord) {
		return this.mapOriginZ + (cord / scaleFactor);
	}
	private double mapPixelToCordX(double mapPixel) {
		return (mapPixel - this.mapOriginX) * scaleFactor;
	}
	private double mapPixelToCordZ(double mapPixel) {
		return (mapPixel - this.mapOriginZ) * scaleFactor;
	}
	private void clampBoundaries() {
		float scaledWidth = mapWidth * zoom;
		float scaledHeight = mapHeight * zoom;

		/*
		if (offsetX < this.width - scaledWidth) {
			offsetX = this.width - scaledWidth;
		}
		if (offsetZ < this.height - scaledHeight) {
			offsetZ = this.height - scaledHeight;
		}*/
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) {
			this.isDragging = true;
			this.lastMouseX = mouseX;
			this.lastMouseY = mouseY;
			return true;
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (button == 0) {
			this.isDragging = false;
			return true;
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (this.isDragging) {
			this.offsetX += (mouseX - this.lastMouseX);
			this.offsetZ += (mouseY - this.lastMouseY);

			this.lastMouseX = mouseX;
			this.lastMouseY = mouseY;

			clampBoundaries();
			return true;
		}
		return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		float zoomFactor = 1.1f;
		float oldZoom = this.zoom;

		if (delta > 0) {
			this.zoom = Math.min(maxZoom, this.zoom * zoomFactor);
		} else if (delta < 0) {
			this.zoom = Math.max(minZoom, this.zoom / zoomFactor);
		}

		// Zoom towards the mouse pointer position so it feels natural
		this.offsetX = (float) (mouseX - (mouseX - this.offsetX) * (this.zoom / oldZoom));
		this.offsetZ = (float) (mouseY - (mouseY - this.offsetZ) * (this.zoom / oldZoom));

		clampBoundaries();
		return true;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		//renderBackground(matrixStack);
		fill(matrixStack, 0, 0, this.width, this.height, 0xFF024B75);

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(MAP_TEXTURE);
		matrixStack.pushPose();
		matrixStack.translate(this.offsetX, this.offsetZ, 0);
		matrixStack.scale(this.zoom, this.zoom, 1.0f);

		blit(matrixStack, 0, 0, 0, 0, mapWidth, mapHeight, mapWidth, mapHeight);


		matrixStack.popPose();
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderDebugInfo(matrixStack, mouseX, mouseY);
	}

	private void renderDebugInfo(MatrixStack matrixStack, int mouseX, int mouseY) {
		int startX = 10;
		int startY = 10;
		int lineHeight = 11;
		int textColor = 0xFFFF0000; // Bright Red text (Format: 0xAARRGGBB)

		double mouseMapPixelX = (mouseX - this.offsetX) / this.zoom;
		double mouseMapPixelZ = (mouseY - this.offsetZ) / this.zoom;
		double mouseWorldX = mapPixelToCordX(mouseMapPixelX);
		double mouseWorldZ = mapPixelToCordZ(mouseMapPixelZ);

		String[] debugLines = {
				"=== MAP DEBUG INFO ===",
				String.format("Mouse Position: X=%d, Y=%d", mouseX, mouseY),
				String.format("Mouse Image Cords: X=%.1f, Z=%.1f", mouseMapPixelX, mouseMapPixelZ),
				String.format("Hover World Cords: X=%.1f, Z=%.1f", mouseWorldX, mouseWorldZ),
				String.format("Map Scale Factor: %d blocks/pixel", scaleFactor),
				String.format("Render Offset: X=%.2f, Z=%.2f", offsetX, offsetZ),
				String.format("Zoom Scale: %.3fx", zoom),
				String.format("Is Dragging: %b", isDragging),
				String.format("Map Dimensions: %dx%d", mapWidth, mapHeight),
				String.format("Map Origin: X=%d, Z=%d", mapOriginX, mapOriginZ),
				String.format("Texture Path: %s", MAP_TEXTURE != null ? MAP_TEXTURE.toString() : "NULL")
		};

		for (int i = 0; i < debugLines.length; i++) {
			this.font.draw(matrixStack, debugLines[i], startX, startY + (i * lineHeight), textColor);
		}
	}
}
