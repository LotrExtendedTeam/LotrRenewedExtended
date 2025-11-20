package com.github.maximuslotro.lotrextended.mixins.net.minecraft.client.gui.advancements;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.advancements.Advancement;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.advancements.AdvancementEntryGui;
import net.minecraft.client.gui.advancements.AdvancementTabGui;
import net.minecraft.util.math.MathHelper;


/**
 * Mixin class for {@link AdvancementTabGui} that applies a fix for Minecraft bug MC-167448.
 * <p>
 * This class modifies tooltip rendering logic in advancement tabs by adjusting the Z-depth
 * translation to prevent tooltips from being hidden or improperly rendered.
 * </p>
 *
 * @see <a href="https://bugs.mojang.com/browse/MC/issues/MC-167448">MC-167448</a>
 * @see <a href="https://bugs-legacy.mojang.com/browse/MC-167448">MC-167448 (Legacy tracker)</a>
 * @Mixin (AdvancementTabGui.class)
 */
@Mixin(AdvancementTabGui.class)
public abstract class MixinAdvancementTabGui extends AbstractGui {
	/** The current horizontal scroll offset within the advancement tab. */
	@Shadow 
	private double scrollX;
	/** The current vertical scroll offset within the advancement tab. */
	@Shadow 
	private double scrollY;
	/** Fade factor controlling tooltip transparency and visibility transitions. */
	@Shadow 
	private float fade;
	/** Map containing all {@link AdvancementEntryGui} widgets displayed within this tab. */
	@Shadow
	@Final
	private Map<Advancement, AdvancementEntryGui> widgets;

	/**
     * Overwrites the vanilla tooltip rendering method to fix the rendering order issue
     * (MC-167448) by adjusting the Z-level translation.
     *
     * <p>The fix ensures that tooltips render correctly above other GUI elements by pushing
     * the matrix and applying a negative Z translation.</p>
     *
     * @reason Fix MC-167448
     * @author Maximuslotro
     * @param pMatrixStack The current {@link MatrixStack} for rendering transformations.
     * @param pMouseX      The current mouse X-coordinate.
     * @param pMouseY      The current mouse Y-coordinate.
     * @param pWidth       The width of the tooltip area.
     * @param pHeight      The height of the tooltip area.
     * @Overwrite
     */
	@Overwrite
	public void drawTooltips(MatrixStack pMatrixStack, int pMouseX, int pMouseY, int pWidth, int pHeight) {
		RenderSystem.pushMatrix();
		// Extended Start
		RenderSystem.translatef(0.0F, 0.0F, -200.0F);
		// Extended End
		//RenderSystem.translatef(0.0F, 0.0F, 200.0F);
		fill(pMatrixStack, 0, 0, 234, 113, MathHelper.floor(this.fade * 255.0F) << 24);
		boolean flag = false;
		int i = MathHelper.floor(this.scrollX);
		int j = MathHelper.floor(this.scrollY);
		if (pMouseX > 0 && pMouseX < 234 && pMouseY > 0 && pMouseY < 113) {
			for(AdvancementEntryGui advancemententrygui : this.widgets.values()) {
				if (advancemententrygui.isMouseOver(i, j, pMouseX, pMouseY)) {
					flag = true;
					advancemententrygui.drawHover(pMatrixStack, i, j, this.fade, pWidth, pHeight);
					break;
				}
			}
		}
		RenderSystem.popMatrix();
		if (flag) {
			this.fade = MathHelper.clamp(this.fade + 0.02F, 0.0F, 0.3F);
		} else {
			this.fade = MathHelper.clamp(this.fade - 0.04F, 0.0F, 1.0F);
		}
	}
}