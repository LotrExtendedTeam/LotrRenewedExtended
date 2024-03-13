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

@Mixin(MiddleEarthFactionsScreen.class)
public class MixinMiddleEarthFactionsScreen extends MiddleEarthMenuScreen {

	public MixinMiddleEarthFactionsScreen(ITextComponent titleComponent) {
		super(titleComponent);
	}

	@Shadow(remap = false)
	private static FactionRegion currentRegion;

	@Shadow(remap = false)
	private static RegistryKey<World> currentDimension;

	@Shadow(remap = false)
	private FactionSettings currentLoadedFactions;

	@Shadow(remap = false)
	private ScrollPane scrollPaneAlliesEnemies;

	@Shadow(remap = false)
	private boolean isPledging;

	@Shadow(remap = false)
	private boolean isUnpledging;

	@Shadow(remap = false)
	private Button buttonRegions;

	@Shadow(remap = false)
	private int scrollBarY;

	@Shadow(remap = false)
	private void updateCurrentDimensionAndFaction() {}

	@Shadow(remap = false)
	private void setCurrentScrollFromFaction() {}

	@Inject(method = "tick()V", at = @At("TAIL"))
	public void tick(CallbackInfo cl) {
		if(currentRegion.getName().toString().equals("lotr:special")) {
			List<FactionRegion> regionList = currentLoadedFactions.getRegionsForDimension(currentDimension);
			int i = regionList.indexOf(currentRegion);
			++i;
			i = IntMath.mod(i, regionList.size());
			currentRegion = (FactionRegion) regionList.get(i);
			updateCurrentDimensionAndFaction();
			setCurrentScrollFromFaction();
			scrollPaneAlliesEnemies.resetScroll();
			isPledging = false;
			isUnpledging = false;
		}
	}

	@Inject(method = "init()V", at = @At("TAIL"))
	public void init(CallbackInfo cl) {
		buttonRegions = this.addButton(new RedBookButton(guiLeft + xSize / 2 - 60, guiTop + scrollBarY + 20, 120, 20, StringTextComponent.EMPTY, button -> {
			List<FactionRegion> regionList = currentLoadedFactions.getRegionsForDimension(currentDimension);
			if (!regionList.isEmpty()) {
				int i = regionList.indexOf(currentRegion);
				++i;
				i = IntMath.mod(i, regionList.size());
				if(regionList.get(i).getName().toString().equals("lotr:special")) {
					System.out.println("Changing region");
					++i;
					i = IntMath.mod(i, regionList.size());
				}
				currentRegion = (FactionRegion) regionList.get(i);
				updateCurrentDimensionAndFaction();
				setCurrentScrollFromFaction();
				scrollPaneAlliesEnemies.resetScroll();
				isPledging = false;
				isUnpledging = false;
			}

		}));
	}
}
