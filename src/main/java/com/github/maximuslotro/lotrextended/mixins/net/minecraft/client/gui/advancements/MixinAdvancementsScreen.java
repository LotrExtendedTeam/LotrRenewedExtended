package com.github.maximuslotro.lotrextended.mixins.net.minecraft.client.gui.advancements;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

/**
 * A Mixin class for modifying the behavior of {@link net.minecraft.client.gui.advancements.AdvancementsScreen}.
 * <p>
 * This Mixin addresses an issue where advancement tabs do not render correctly by injecting additional
 * behavior into the {@code render} method.
 * <p>
 * Related issue: <a href="https://github.com/MinecraftForge/MinecraftForge/issues/9628">Forge Issue #9628</a>
 * 
 * @Mixin (AdvancementsScreen.class)
 */
@Mixin(AdvancementsScreen.class)
public abstract class MixinAdvancementsScreen extends Screen {
	/**
     * Constructor required by Mixins when extending a class.
     * Although it exists in source, this constructor will be stripped at runtime.
     *
     * @param pTitle A placeholder {@link net.minecraft.util.text.ITextComponent} passed to the superclass.
     */
	protected MixinAdvancementsScreen(ITextComponent pTitle) {
		super(pTitle);
	}

	/**
     * Injects additional rendering behavior at the end of the {@code render} method of {@link AdvancementsScreen}.
     * <p>
     * This fix ensures that advancement tabs display correctly when the screen is rendered.
     *
     * @param pMatrixStack The current {@link com.mojang.blaze3d.matrix.MatrixStack} used for rendering.
     * @param pMouseX      The current X position of the mouse.
     * @param pMouseY      The current Y position of the mouse.
     * @param pPartialTicks The partial ticks for smooth rendering.
     * @param ci 			The callback info handler for this injection
     * 
     * @Inject (method = "render", at = @At("TAIL"))
     */
	@Inject(method = "render", at = @At("TAIL"))
	public void renderExtended(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks, CallbackInfo ci) {
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
	}
}
