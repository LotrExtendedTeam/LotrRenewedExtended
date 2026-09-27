package com.github.maximuslotro.lotrrextended.client.gui.banner;

import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedScrollList;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedProtectionList extends ExtendedScrollList<ExtendedProtectionListEntry> {
	private final ExtendedBannerConfigurationScreen bannerScreen;
	private final Minecraft minecraft;
	private final List<ExtendedProtectionListEntry> protections = new ArrayList<>();

	public ExtendedProtectionList(ExtendedBannerConfigurationScreen pParentScreen, Minecraft pMinecraft, int pWindowHeight, int pX0, int pScrollPlaneWidth, int pY0, int pY1, int pItemHeight) {
		super(pMinecraft, pWindowHeight, pX0, pScrollPlaneWidth, pY0, pY1, pItemHeight);
		this.bannerScreen = pParentScreen;
		this.minecraft = pMinecraft;
		this.setRenderBackground(false);
		this.setRenderTopAndBottom(false);
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		double d0 = this.minecraft.getWindow().getGuiScale();
		RenderSystem.enableScissor((int)((double)this.getRowLeft() * d0), (int)((double)(this.height - this.y1) * d0), (int)((double)(this.getScrollbarPosition() + 6) * d0), (int)((double)(this.y1 - this.y0) * d0));
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		RenderSystem.disableScissor();
	}

	public void updateProtections(List<ProtectionEntry> whitelist, double scrollAmount) {
		this.protections.clear();
		for(ProtectionEntry protectionEntry : whitelist) {
			this.protections.add(new ExtendedProtectionListEntry(this.minecraft, this.bannerScreen, protectionEntry));
		}
		this.replaceEntries(this.protections);
		this.setScrollAmount(scrollAmount);
	}

	public boolean isEmpty() {
		return this.protections.isEmpty();
	}

	public void tick() {
		this.protections.removeIf(entry -> {
			entry.tick();
			if(entry.isRemoved()) {
				this.removeEntry(entry);
				bannerScreen.getEditableData().whitelist.removeIf(p -> p.getClientID().equals(entry.protection.getClientID()));
				return true;
			}
			return false;
		});
	}

	public void addProtection(ProtectionEntry protectionEntry) {
		ExtendedProtectionListEntry newEntry = new ExtendedProtectionListEntry(this.minecraft, this.bannerScreen, protectionEntry);
		this.addEntry(newEntry);
		this.protections.add(newEntry);
	}
}