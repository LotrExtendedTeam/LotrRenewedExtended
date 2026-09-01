package com.github.maximuslotro.lotrrextended.client.gui.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedSlider;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedSimpleButton;
import com.github.maximuslotro.lotrrextended.client.render.utils.ExtendedColorUtil;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedConquestDataHolder;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestPos;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestRegion;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCRequestConquestDataPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.fac.Faction;
import lotr.common.fac.FactionRegion;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedMapScreen extends ExtendedAbstractBasicScreen {
	private final static ResourceLocation MAP_ICONS = new ResourceLocation("lotrextended", "textures/gui/map_icons.png");
	private final static int MAP_ICONS_SIZE = 512;
	private final static int CONQUEST_SQUARE_RED = 0x80FF0000;
	private final static int CONQUEST_SQUARE_GREEN = 0x8000FF00;
	private final static int CONQUEST_SQUARE_GRAY = 0x80808080;
	private final static int MAP_BACKGROUND = 0xFF024B75;
	private static Minecraft mc = Minecraft.getInstance();
	private ResourceLocation MAP_TEXTURE;
	private ResourceLocation MAP_TEXTURE_OVERLAY;
	// Loaded map settings
	private int mapWidth; 
	private int mapHeight;
	private int mapOriginX;
	private int mapOriginZ;
	private int mapScale;
	// Zoom settings
	private float zoom = 1.0f;
	private final float minZoom = 0.125f;
	private final float maxZoom = 5.0f;
	// Map render data
	private double offsetX = 0;
	private double offsetZ = 0;
	private boolean isDraggingMap = false;
	private double lastMouseX;
	private double lastMouseY;
	private boolean showDebug = false;
	private boolean showConquest = false;
	private boolean showMap = true;
	private boolean showGUI = true;
	private boolean showConquestDEBUG = false;
	private int conquestScaleSize = 8;

	// Factions
	private @Nullable Faction selectedFaction;
	private @Nullable FactionRegion selectedRegion;
	private @Nonnull List<Faction> regionFactions = new ArrayList<>();
	private final FactionSettings factionSettings;
	// Conquest
	private final ExtendedConquestDataHolder conquestData;
	private @Nonnull Map<ExtendedConquestPos, ExtendedConquestRegion> conquestRegions = new HashMap<>();

	public ExtendedMapScreen() {
		super(new StringTextComponent("CONQUEST"));
		factionSettings = FactionSettingsManager.clientInstance().getCurrentLoadedFactions();
		conquestData = ExtendedLevelData.clientInstance().getConquestData();
	}

	@Override
	protected void init() {
		super.init();
		MapSettingsManager mapSettings = MapSettingsManager.clientInstance();
		this.mapOriginX = mapSettings.getCurrentLoadedMap().getOriginX();
		this.mapOriginZ = mapSettings.getCurrentLoadedMap().getOriginZ();
		this.mapWidth = mapSettings.getCurrentLoadedMap().getWidth();
		this.mapHeight = mapSettings.getCurrentLoadedMap().getHeight();
		this.mapScale = 1 << mapSettings.getCurrentLoadedMap().getScalePower();
		this.MAP_TEXTURE = new ResourceLocation("lotr", "textures/map/loaded_map.png");
		ResourceLocation overlay = new ResourceLocation("lotrextended", "textures/map/middle_earth_overlay.png");
		if(mc.getResourceManager().hasResource(overlay)) {
			this.MAP_TEXTURE_OVERLAY = overlay;
		}

		List<FactionRegion> regions = factionSettings.getRegionsForDimension(mc.level.dimension());
		if(regions!=null && !regions.isEmpty()) {
			selectedRegion= regions.get(0);
			List<Faction> factions = factionSettings.getFactionsForRegion(selectedRegion);
			regionFactions = factions;
			if(factions!=null && !factions.isEmpty()) {
				setSelectedFaction(factions.get(0));
			}
		}

		if (this.minecraft != null && this.minecraft.player != null) {
			centerMapOnCords(this.minecraft.player.getX(), this.minecraft.player.getZ());
		}
		clampMapBoundaries();
		addButton(new ExtendedSimpleButton(width-100, height-120, 100, 20, new StringTextComponent("GUI"), (button)-> showGUI=!showGUI));
		addButton(new ExtendedSimpleButton(width-100, height-100, 100, 20, new StringTextComponent("MapOVERLAY"), (button)-> showMap=!showMap));
		addButton(new ExtendedSimpleButton(width-100, height-80, 100, 20, new StringTextComponent("Debug"), (button)-> showDebug=!showDebug));
		addButton(new ExtendedSimpleButton(width-100, height-60, 100, 20, new StringTextComponent("Conquest"), (button)-> showConquest=!showConquest));
		addButton(new ExtendedSimpleButton(width-100, height-40, 100, 20, new StringTextComponent("ConquestDEBUG"), (button)-> showConquestDEBUG=!showConquestDEBUG));
		addButton(new ExtendedSlider(width-100, height-20, 100, 20, "Conquest Scale", 1, 16, 1, () -> conquestScaleSize, (v)-> conquestScaleSize =(int) v));
		//addButton(new ExtendedSlider(width-200, height-20, 200, 20, "", minZoom, maxZoom, 0.125F, () -> zoom, (v)-> zoom =(float) v));

		addButton(new ExtendedSlider((this.width/2)-150, height-50, 300, 14, "", 0F, 1.0F, 0, () -> 0, this::handleFactionSlider)
			.setSliderTexture(MAP_ICONS, 16).setDrawBackground(false).setSliderTextureUV(0, 14).setTextureSize(MAP_ICONS_SIZE, MAP_ICONS_SIZE).disableMessage());
	}

	public void setSelectedFaction(Faction pFaction) {
		Faction oldFaction = selectedFaction;
		this.selectedFaction = pFaction;
		if(selectedFaction!=null && oldFaction!=selectedFaction) {
			ExtendedPacketHandler.sendToServer(new ExtendedCRequestConquestDataPacket(selectedFaction.getName()));
		}
	}

	private void centerMapOnCords(double x, double z) {
		// Center= Screen Center minus scaled player position
		this.offsetX = (this.width / 2) - (cordToMapPixelX(x) * zoom);
		this.offsetZ = (this.height / 2) - (cordToMapPixelZ(z) * zoom);
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
		return (cord / mapScale) + this.mapOriginX;
	}
	private double cordToMapPixelZ(double cord) {
		return (cord / mapScale) + this.mapOriginZ;
	}
	private double mapPixelToCordX(double mapPixel) {
		return (mapPixel - this.mapOriginX) * mapScale;
	}
	private double mapPixelToCordZ(double mapPixel) {
		return (mapPixel - this.mapOriginZ) * mapScale;
	}
	private void clampMapBoundaries() {
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
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(super.mouseClicked(mouseX, mouseY, button)) {
			return true;
		}else if (button == 0) {
			this.isDraggingMap = true;
			this.lastMouseX = mouseX;
			this.lastMouseY = mouseY;
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if(super.mouseReleased(mouseX, mouseY, button)) {
			return true;
		}else if (button == 0) {
			this.isDraggingMap = false;
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if(super.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
			return true;
		}else if (this.isDraggingMap) {
			this.offsetX += (mouseX - this.lastMouseX);
			this.offsetZ += (mouseY - this.lastMouseY);
			this.lastMouseX = mouseX;
			this.lastMouseY = mouseY;
			clampMapBoundaries();
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		if(super.mouseScrolled(mouseX, mouseY, delta)) {
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
			this.offsetZ = (mouseY - (mouseY - this.offsetZ) * (this.zoom / oldZoom));
			clampMapBoundaries();
			return true;
		}
	}

	public void handleFactionSlider(double percent) {
		if (regionFactions.isEmpty()) {
			return;
		}
		int index = Math.round((float)percent * (regionFactions.size() - 1));
		setSelectedFaction(regionFactions.get(index));
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		//renderBackground(matrixStack);
		fill(matrixStack, 0, 0, this.width, this.height, MAP_BACKGROUND);

		matrixStack.pushPose(); // Render movable map components inside pose
		// Render pose in map pixel space
		matrixStack.translate(this.offsetX, this.offsetZ, 0);
		// Handle zoom into map pixel space
		matrixStack.scale(this.zoom, this.zoom, 1.0f);
		this.minecraft.getTextureManager().bind(MAP_TEXTURE);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		blit(matrixStack, 0, 0, 0, 0, mapWidth, mapHeight, mapWidth, mapHeight);
		if(showMap && MAP_TEXTURE_OVERLAY!=null) {
			this.minecraft.getTextureManager().bind(MAP_TEXTURE_OVERLAY);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			blit(matrixStack, 0, 0, 0, 0, mapWidth, mapHeight, mapWidth, mapHeight);
		}
		// Render Conquest
		if(showConquest && selectedFaction!=null) {
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			renderConquest(matrixStack);
			RenderSystem.disableBlend();
		}
		matrixStack.popPose();// Pop scaled pose, render static non-zoomable/movable components

		// Render static components
		if(showGUI) {
			renderFactionScrollBar(matrixStack);
			renderSelectedFaction(matrixStack);
		}
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		if(showDebug) {
			renderDebugInfo(matrixStack, mouseX, mouseY);
		}
		if(showConquest) {
			renderConquestTooltip(matrixStack, mouseX, mouseY);
		}
	}

	private void renderSelectedFaction(MatrixStack matrixStack) {
		if(selectedFaction!=null) {
			IFormattableTextComponent faction = selectedFaction.getColoredDisplayName();
			float minText = (this.width / 2) - (font.width(faction) / 2);
			float maxText = minText + font.width(faction);
			fillFloat(matrixStack, minText-5, 28, maxText+5, 41, 0xFFFFD800);
			fillFloat(matrixStack, minText-3, 30, maxText+3, 39, 0xFFFFFFFF);
			this.font.draw(matrixStack, faction, minText, 31, selectedFaction.getColor());
		}
	}

	private void renderFactionScrollBar(MatrixStack matrixStack) {
		minecraft.getTextureManager().bind(MAP_ICONS);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		int scrollBarLeft = (this.width / 2) - 150;
		int scrollBarTop = height - 50;
		blitFloat(matrixStack, scrollBarLeft, scrollBarTop, 0, 0, 300, 14, MAP_ICONS_SIZE, MAP_ICONS_SIZE);
		if (regionFactions.isEmpty()) {
			return;
		}
		float factionRenderWidth = 298.0F / regionFactions.size();
		for(Faction faction : regionFactions) {
			int factionIndex = regionFactions.indexOf(faction);
			float renderFactionLeft = scrollBarLeft + 1 + (factionIndex * factionRenderWidth);
			fillFloat(matrixStack, renderFactionLeft, scrollBarTop + 1, renderFactionLeft + factionRenderWidth, scrollBarTop + 13, ExtendedColorUtil.applyAlpha(faction.getColor(), 200));
		}
	}

	private void renderConquest(MatrixStack matrixStack) {
		if (selectedFaction == null || conquestRegions.isEmpty()) {
			return;
		}
		for(Entry<ExtendedConquestPos, ExtendedConquestRegion> region : conquestRegions.entrySet()) {
			float levelFaction = region.getValue().getConquest(selectedFaction.getName());
			if(levelFaction>0) {
				int mapPixelX = conquestData.convertConquestToMapPixelX(region.getKey().getPosX());
				int mapPixelZ = conquestData.convertConquestToMapPixelZ(region.getKey().getPosZ());
				renderMapSquare(matrixStack, mapPixelX, mapPixelZ, conquestData.getConquestSizeInMapPixels(), CONQUEST_SQUARE_RED);
			}
		}
		if(showConquestDEBUG) {
			for(int x = 0; x < mapWidth; x += conquestScaleSize) {
				for(int z = 0; z < mapHeight; z += conquestScaleSize) {
					if(((x / conquestScaleSize) + (z / conquestScaleSize)) % 2 == 0) {
						renderMapSquare(matrixStack, x, z, conquestScaleSize, CONQUEST_SQUARE_RED);
					}
				}
			}
		}
	}

	private void renderMapSquare(MatrixStack matrixStack, float mapPixelX, float mapPixelZ, int scaleSize, int colorARGB) {
		fillFloat(matrixStack, mapPixelX, mapPixelZ, (mapPixelX + scaleSize), (mapPixelZ + scaleSize), colorARGB);
	}

	private void renderConquestTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
		if (selectedFaction == null || conquestRegions.isEmpty()) {
			return;
		}
		double mouseMapPixelX = screenToMapPixelX(mouseX);
		double mouseMapPixelZ = screenToMapPixelZ(mouseY);
		int conquestPixels = conquestData.getConquestSizeInMapPixels();
		for (Entry<ExtendedConquestPos, ExtendedConquestRegion> entry : conquestRegions.entrySet()) {
			int mapPixelX = conquestData.convertConquestToMapPixelX(entry.getKey().getPosX());
			int mapPixelZ = conquestData.convertConquestToMapPixelZ(entry.getKey().getPosZ());
			float levelFaction = entry.getValue().getConquest(selectedFaction.getName());
			if (levelFaction>0 && mouseMapPixelX >= mapPixelX && mouseMapPixelX < (mapPixelX + conquestPixels) 
				&& mouseMapPixelZ >= mapPixelZ && mouseMapPixelZ < (mapPixelZ + conquestPixels)) {
				renderTooltip(matrixStack, new StringTextComponent(String.format("Conquest Strength: %.2f", levelFaction)), mouseX, mouseY);
				break;
			}
		}
	}

	private void renderDebugInfo(MatrixStack matrixStack, int mouseX, int mouseY) {
		double mouseMapPixelX = screenToMapPixelX(mouseX);
		double mouseMapPixelZ = screenToMapPixelZ(mouseY);
		double mouseWorldX = mapPixelToCordX(mouseMapPixelX);
		double mouseWorldZ = mapPixelToCordZ(mouseMapPixelZ);

		String[] debugLines = {
				"=== MAP DEBUG INFO ===",
				String.format("Mouse Position: X=%d, Y=%d", mouseX, mouseY),
				String.format("Mouse Image Cords: X=%.1f, Z=%.1f", mouseMapPixelX, mouseMapPixelZ),
				String.format("Hover World Cords: X=%.1f, Z=%.1f", mouseWorldX, mouseWorldZ),
				String.format("Map Scale Factor: %d blocks/pixel", mapScale),
				String.format("Render Offset: X=%.2f, Z=%.2f", offsetX, offsetZ),
				String.format("Zoom Scale: %.3fx", zoom),
				String.format("Is Dragging: %b", isDraggingMap),
				String.format("Map Dimensions: %dx%d", mapWidth, mapHeight),
				String.format("Map Origin: X=%d, Z=%d", mapOriginX, mapOriginZ),
				String.format("Texture Path: %s", MAP_TEXTURE != null ? MAP_TEXTURE.toString() : "NULL"),
				String.format("Conquest Scale: %d", conquestData.getConquestSizeInMapPixels()),
				String.format("Selected Faction Region: %s", selectedRegion!=null ? selectedRegion.getName().toString() : "NULL"),
				String.format("Selected Faction: %s", selectedFaction!=null ? selectedFaction.getName().toString() : "NULL"),
				String.format("Loaded Conquest Regions: %d", conquestRegions.size())
		};
		for(int i = 0; i < debugLines.length; i++) {
			this.font.draw(matrixStack, debugLines[i], 10, 10 + (i * 11), 0xFFFF0000);
		}
	}

	public void refreshConquestData() {
		this.conquestRegions = new HashMap<>(conquestData.getConquestMap());
	}
}
