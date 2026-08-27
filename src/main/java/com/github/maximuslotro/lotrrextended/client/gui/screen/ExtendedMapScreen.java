package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedSlider;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedSimpleButton;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.world.map.MapSettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedMapScreen extends ExtendedAbstractBasicScreen {
	private final static ResourceLocation MAP_ICONS = new ResourceLocation("lotrextended", "textures/gui/map_icons.png");;
	private static Minecraft mc = Minecraft.getInstance();
	private ResourceLocation MAP_TEXTURE;
	private ResourceLocation MAP_TEXTURE_OVERLAY;
	private int mapWidth; 
	private int mapHeight;
	private int mapOriginX;
	private int mapOriginZ;
	private int scaleFactor;

	private float zoom = 1.0f;
	private final float minZoom = 0.125f;
	private final float maxZoom = 5.0f;

	private double offsetX = 0;
	private double offsetZ = 0;

	private boolean isDraggingMap = false;
	private double lastMouseX;
	private double lastMouseZ;
	int conquestScaleSize = 8;
	boolean showDebug = false;
	boolean showConquest = false;
	boolean showMap = true;
	boolean showGUI = true;
	int selectedFaction = 0;

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
		ResourceLocation overlay = new ResourceLocation("lotrextended", "textures/map/middle_earth_overlay.png");
		if(mc.getResourceManager().hasResource(overlay)) {
			this.MAP_TEXTURE_OVERLAY = overlay;
		}

		if (this.minecraft != null && this.minecraft.player != null) {
			double playerWorldX = this.minecraft.player.getX();
			double playerWorldZ = this.minecraft.player.getZ();

			// Center logic: Screen Center minus scaled player position
			this.offsetX = (this.width / 2) - (cordToMapPixelX(this.minecraft.player.getX()) * zoom);
			this.offsetZ = (this.height / 2) - (cordToMapPixelZ(this.minecraft.player.getZ()) * zoom);
		}
		clampBoundaries();
		addButton(new ExtendedSimpleButton(width-100, height-120, 100, 20, new StringTextComponent("GUI"), (button)-> showGUI=!showGUI));
		addButton(new ExtendedSimpleButton(width-100, height-100, 100, 20, new StringTextComponent("MapOVERLAY"), (button)-> showMap=!showMap));
		addButton(new ExtendedSimpleButton(width-100, height-80, 100, 20, new StringTextComponent("Debug"), (button)-> showDebug=!showDebug));
		addButton(new ExtendedSimpleButton(width-100, height-60, 100, 20, new StringTextComponent("Conquest"), (button)-> showConquest=!showConquest));
		addButton(new ExtendedSlider(width-100, height-40, 100, 20, "Conquest Scale", 1, 16, 1, () -> conquestScaleSize, (v)-> conquestScaleSize =(int) v));
		//addButton(new ExtendedSlider(width-200, height-20, 200, 20, "", minZoom, maxZoom, 0.125F, () -> zoom, (v)-> zoom =(float) v));
		
		addButton(new ExtendedSlider((this.width/2)-150, height-50, 300, 14, "Selected Faction", 1, 300, 1, () -> selectedFaction, (v)-> selectedFaction =(int) v)
			.setSliderTexture(MAP_ICONS, 16).setDrawBackground(false).setSliderTextureUV(0, 14));
	}

	private double mapPixelToScreenX(double mapPixelX) {
		return (mapPixelX * this.zoom) + this.offsetX;
	}
	private double mapPixelToScreenZ(double mapPixelZ) {
		return (mapPixelZ * this.zoom) + this.offsetZ;
	}
	private double screenToMapPixelX(double screenX) {
		return (screenX - this.offsetX) / this.zoom;
	}
	private double screenToMapPixelZ(double screenZ) {
		return (screenZ - this.offsetZ) / this.zoom;
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

		if (offsetX < this.width - scaledWidth) {
			offsetX = this.width - scaledWidth;
		}
		if (offsetZ < this.height - scaledHeight) {
			offsetZ = this.height - scaledHeight;
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseZ, int button) {
		if(super.mouseClicked(mouseX, mouseZ, button)) {
			return true;
		}else if (button == 0) {
			this.isDraggingMap = true;
			this.lastMouseX = mouseX;
			this.lastMouseZ = mouseZ;
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseZ, int button) {
		if(super.mouseReleased(mouseX, mouseZ, button)) {
			return true;
		}else if (button == 0) {
			this.isDraggingMap = false;
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseZ, int button, double dragX, double dragY) {
		if(super.mouseDragged(mouseX, mouseZ, button, dragX, dragY)) {
			return true;
		}else if (this.isDraggingMap) {
			this.offsetX += (mouseX - this.lastMouseX);
			this.offsetZ += (mouseZ - this.lastMouseZ);

			this.lastMouseX = mouseX;
			this.lastMouseZ = mouseZ;

			clampBoundaries();
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseZ, double delta) {
		if(super.mouseScrolled(mouseX, mouseZ, delta)) {
			return true;
		}else {
			float zoomFactor = 1.1f;
			float oldZoom = this.zoom;

			if (delta > 0) {
				this.zoom = Math.min(maxZoom, this.zoom * zoomFactor);
			} else if (delta < 0) {
				this.zoom = Math.max(minZoom, this.zoom / zoomFactor);
			}

			// Zoom towards the mouse pointer position so it feels natural
			this.offsetX = (mouseX - (mouseX - this.offsetX) * (this.zoom / oldZoom));
			this.offsetZ = (mouseZ - (mouseZ - this.offsetZ) * (this.zoom / oldZoom));

			clampBoundaries();
			return true;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseZ, float partialTicks) {
		//renderBackground(matrixStack);
		fill(matrixStack, 0, 0, this.width, this.height, 0xFF024B75);//Render blue sea endlessly

		matrixStack.pushPose(); // Render movable map components inside pose
		matrixStack.translate(this.offsetX, this.offsetZ, 0);
		matrixStack.scale(this.zoom, this.zoom, 1.0f);
		// Render Map
		this.minecraft.getTextureManager().bind(MAP_TEXTURE);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		blit(matrixStack, 0, 0, 0, 0, mapWidth, mapHeight, mapWidth, mapHeight);
		if(showMap && MAP_TEXTURE_OVERLAY!=null) {
			this.minecraft.getTextureManager().bind(MAP_TEXTURE_OVERLAY);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			blit(matrixStack, 0, 0, 0, 0, mapWidth, mapHeight, mapWidth, mapHeight);
		}
		// Render Conquest
		if(showConquest) {
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			renderDebugConquestCheckerboard(matrixStack);
			RenderSystem.disableBlend();
		}
		// Pop scaled pose, render static non-zoomable/movable components
		matrixStack.popPose();
		// Render static components
		if(showGUI) {
			renderFactionScrollBar(matrixStack);
		}
		super.render(matrixStack, mouseX, mouseZ, partialTicks);
		if(showDebug) {
			renderDebugInfo(matrixStack, mouseX, mouseZ);
		}
	}

	private void renderFactionScrollBar(MatrixStack matrixStack) {
		minecraft.getTextureManager().bind(MAP_ICONS);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		blitFloat(matrixStack, (this.width/2)-150, height-50, 0, 0, 300, 14, 512, 512);
	}

	private void renderDebugConquestCheckerboard(MatrixStack matrixStack) {
		int colorRed = 0x80FF0000;
		for(int x = 0; x < mapWidth; x += conquestScaleSize) {
			for(int z = 0; z < mapHeight; z += conquestScaleSize) {
				if(((x / conquestScaleSize) + (z / conquestScaleSize)) % 2 == 0) {
					renderMapSquare(matrixStack, x, z, colorRed);
				}
			}
		}
	}

	private void renderMapSquare(MatrixStack matrixStack, float mapPixelX, float mapPixelZ, int colorARGB) {
		fillFloat(matrixStack, mapPixelX, mapPixelZ, (mapPixelX + conquestScaleSize), (mapPixelZ + conquestScaleSize), colorARGB);
	}

	private void renderDebugInfo(MatrixStack matrixStack, int mouseX, int mouseZ) {
		double mouseMapPixelX = screenToMapPixelX(mouseX);
		double mouseMapPixelZ = screenToMapPixelZ(mouseZ);
		double mouseWorldX = mapPixelToCordX(mouseMapPixelX);
		double mouseWorldZ = mapPixelToCordZ(mouseMapPixelZ);

		String[] debugLines = {
				"=== MAP DEBUG INFO ===",
				String.format("Mouse Position: X=%d, Y=%d", mouseX, mouseZ),
				String.format("Mouse Image Cords: X=%.1f, Z=%.1f", mouseMapPixelX, mouseMapPixelZ),
				String.format("Hover World Cords: X=%.1f, Z=%.1f", mouseWorldX, mouseWorldZ),
				String.format("Map Scale Factor: %d blocks/pixel", scaleFactor),
				String.format("Render Offset: X=%.2f, Z=%.2f", offsetX, offsetZ),
				String.format("Zoom Scale: %.3fx", zoom),
				String.format("Is Dragging: %b", isDraggingMap),
				String.format("Map Dimensions: %dx%d", mapWidth, mapHeight),
				String.format("Map Origin: X=%d, Z=%d", mapOriginX, mapOriginZ),
				String.format("Texture Path: %s", MAP_TEXTURE != null ? MAP_TEXTURE.toString() : "NULL"),
				String.format("Conquest Scale: %d", conquestScaleSize),
				String.format("Selected Faction: %d", selectedFaction)
		};

		for(int i = 0; i < debugLines.length; i++) {
			this.font.draw(matrixStack, debugLines[i], 10, 10 + (i * 11), 0xFFFF0000);
		}
	}
}
