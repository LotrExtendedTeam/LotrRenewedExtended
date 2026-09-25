package com.github.maximuslotro.lotrrextended.client.gui.banner;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedProtectionList extends AbstractOptionList<ExtendedProtectionListEntry> {
	private final ExtendedBannerConfigurationScreen bannerScreen;
	private final Minecraft minecraft;
	private final List<ExtendedProtectionListEntry> protections = Lists.newArrayList();

	public ExtendedProtectionList(ExtendedBannerConfigurationScreen pParentScreen, Minecraft pMinecraft, int pWidth, int pHeight, int pY0, int pY1, int pItemHeight) {
		super(pMinecraft, pWidth, pHeight, pY0, pY1, pItemHeight);
		this.bannerScreen = pParentScreen;
		this.minecraft = pMinecraft;
		this.setRenderBackground(false);
		this.setRenderTopAndBottom(false);
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		double d0 = this.minecraft.getWindow().getGuiScale();
		RenderSystem.enableScissor((int)((double)this.getRowLeft() * d0), (int)((this.height - this.y1) * d0), (int)((this.getScrollbarPosition() + 6) * d0), (int)((this.height - (this.height - this.y1) - this.y0 - 4) * d0));
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
		protections.forEach(ExtendedProtectionListEntry::tick);
	}

	public void addProtection(ProtectionEntry protectionEntry) {
		ExtendedProtectionListEntry newEntry = new ExtendedProtectionListEntry(this.minecraft, this.bannerScreen, protectionEntry);
		this.addEntry(newEntry);
		this.protections.add(newEntry);
	}
}