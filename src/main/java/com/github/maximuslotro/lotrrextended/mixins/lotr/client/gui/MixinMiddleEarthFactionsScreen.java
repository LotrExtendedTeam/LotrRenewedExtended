package com.github.maximuslotro.lotrrextended.mixins.lotr.client.gui;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.math.IntMath;

import lotr.client.gui.MiddleEarthFactionsScreen;
import lotr.client.gui.MiddleEarthMenuScreen;
import lotr.client.gui.widget.ScrollPane;
import lotr.client.gui.widget.button.RedBookButton;
import lotr.common.fac.FactionRegion;
import lotr.common.fac.FactionSettings;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

/**
 * @reason need to hide special factions from rendering and stuff
 * @Mixin (MiddleEarthFactionsScreen.class)
 * */
@Mixin(MiddleEarthFactionsScreen.class)
public class MixinMiddleEarthFactionsScreen extends MiddleEarthMenuScreen {

	public MixinMiddleEarthFactionsScreen(ITextComponent titleComponent) {
		super(titleComponent);
	}

	/**
	 * Get current FactionRegion
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private static FactionRegion currentRegion;
	/**
	 * Get the current dimension
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private static RegistryKey<World> currentDimension;
	/**
	 * Get the current loaded factions
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private FactionSettings currentLoadedFactions;
	/**
	 * Get the current scrollPlane
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private ScrollPane scrollPaneAlliesEnemies;
	/**
	 * Get if player is pledged
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private boolean isPledging;
	/**
	 * Get the player is unpledging
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private boolean isUnpledging;
	/**
	 * Get the region button
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private Button buttonRegions;
	/**
	 * Get the current ScrollBar height
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private int scrollBarY;
	/**
	 * Get the parent class method for updating the faction data
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private void updateCurrentDimensionAndFaction() {}
	/**
	 * Get the parent class method for getting the current Faction from scroll
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private void setCurrentScrollFromFaction() {}

	/**
	 * @reason Need to remove special faction from view on client tick
	 * @Inject (method = "tick()V", at = @At("TAIL"))
	 * @param cl The associated CallbackInfo with this inject
	 * */
	@Inject(method = "tick()V", at = @At("TAIL"))
	public void tick(CallbackInfo cl) {
		if(currentRegion.getName().toString().equals("lotr:special")) {
			List<FactionRegion> regionList = currentLoadedFactions.getRegionsForDimension(currentDimension);
			int i = regionList.indexOf(currentRegion);
			++i;
			i = IntMath.mod(i, regionList.size());
			currentRegion = regionList.get(i);
			updateCurrentDimensionAndFaction();
			setCurrentScrollFromFaction();
			scrollPaneAlliesEnemies.resetScroll();
			isPledging = false;
			isUnpledging = false;
		}
	}

	/**
	 * @reason Need to remove special faction from view on client tick
	 * @Inject (method = "tick()V", at = @At("TAIL"))
	 * @param cl The associated CallbackInfo with this inject
	 * */
	@Inject(method = "init()V", at = @At("TAIL"))
	public void init(CallbackInfo cl) {
		buttonRegions = this.addButton(new RedBookButton(guiLeft + xSize / 2 - 60, guiTop + scrollBarY + 20, 120, 20, StringTextComponent.EMPTY, button -> {
			List<FactionRegion> regionList = currentLoadedFactions.getRegionsForDimension(currentDimension);
			if (!regionList.isEmpty()) {
				int i = regionList.indexOf(currentRegion);
				++i;
				i = IntMath.mod(i, regionList.size());
				if(regionList.get(i).getName().toString().equals("lotr:special")) {
					++i;
					i = IntMath.mod(i, regionList.size());
				}
				currentRegion = regionList.get(i);
				updateCurrentDimensionAndFaction();
				setCurrentScrollFromFaction();
				scrollPaneAlliesEnemies.resetScroll();
				isPledging = false;
				isUnpledging = false;
			}

		}));
	}
}
