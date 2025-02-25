package com.github.maximuslotro.lotrrextended.mixins.lotr.client;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.mixinhelpers.ExtendedAlignmentKeyAction;
import com.google.common.collect.ImmutableMap;
import com.google.common.math.IntMath;

import lotr.client.LOTRKeyHandler;
import lotr.client.gui.MiddleEarthMasterMenuScreen;
import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionRegion;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.init.LOTRDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;

/**
 * @reason need to hide special factions from rendering and stuff
 * @Mixin (LOTRKeyHandler.class)
 * */
@Mixin(LOTRKeyHandler.class)
public abstract class MixinLOTRKeyHandler{

	/**
	 * @Shadow (remap = false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap = false)
	public static KeyBinding KEY_BIND_MENU;
	/**
	 * @Shadow (remap = false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap = false)
	public static KeyBinding KEY_BIND_ALIGNMENT_PREVIOUS;
	/**
	 * @Shadow (remap = false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap = false)
	public static KeyBinding KEY_BIND_ALIGNMENT_NEXT;
	/**
	 * @Shadow (remap = false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap = false)
	public static KeyBinding KEY_BIND_ALIGNMENT_GROUP_PREVIOUS;
	/**
	 * @Shadow (remap = false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap = false)
	public static KeyBinding KEY_BIND_ALIGNMENT_GROUP_NEXT;

	/**
	 * An intractable map of keybindings to use for override logic
	 * */
	private static final Map<KeyBinding, ExtendedAlignmentKeyAction> ExtendedALIGNMENT_KEY_ACTIONS = ImmutableMap.of(KEY_BIND_ALIGNMENT_PREVIOUS, new ExtendedAlignmentKeyAction(-1, 0), KEY_BIND_ALIGNMENT_NEXT, new ExtendedAlignmentKeyAction(1, 0), KEY_BIND_ALIGNMENT_GROUP_PREVIOUS, new ExtendedAlignmentKeyAction(0, -1), KEY_BIND_ALIGNMENT_GROUP_NEXT, new ExtendedAlignmentKeyAction(0, 1));

	/**
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private static int alignmentChangeTick;

	/**
	 * @author maximuslotro
	 * @reason need to hide special factions from rendering and stuff
	 * @Inject (method = "onKeyInput(Lnet/minecraftforge/client/event/InputEvent$KeyInputEvent;)V", at = @At("HEAD"), remap = false, cancellable = true)
	 * @param event The captured KeyInputEvent from the parent handler
	 * @param cl The CallbackInfo for canceling the event
	 * */
	@Inject(method = "onKeyInput(Lnet/minecraftforge/client/event/InputEvent$KeyInputEvent;)V", at = @At("HEAD"), remap = false, cancellable = true)
	public void onKeyInput(KeyInputEvent event, CallbackInfo cl) {
		cl.cancel();
		int key = event.getKey();
		int scancode = event.getScanCode();
		int action = event.getAction();
		Minecraft mc = Minecraft.getInstance();
		if (KEY_BIND_MENU.matches(key, scancode) && mc.screen == null && mc.player != null) {
			Screen menuScreen = MiddleEarthMasterMenuScreen.openMenu(mc.player);
			if (menuScreen != null) {
				mc.setScreen(menuScreen);
			}
		}

		if (mc.screen == null && mc.player != null && (action == 1 || action == 2) && alignmentChangeTick <= 0) {
			Optional<ExtendedAlignmentKeyAction> optAlignmentAction = ExtendedALIGNMENT_KEY_ACTIONS.entrySet().stream().filter(e -> (e.getKey()).matches(key, scancode)).map(Map.Entry::getValue).findFirst();

			optAlignmentAction.ifPresent(alignmentAction -> {
				FactionSettings factionSettings = FactionSettingsManager.clientInstance().getCurrentLoadedFactions();
				AlignmentDataModule alignData = LOTRLevelData.clientInstance().getData(mc.player).getAlignmentData();
				boolean changedAlignmentView = false;
				int factionShift = alignmentAction.factionShift;
				int groupShift = alignmentAction.groupShift;
				RegistryKey<World> currentDimension = LOTRDimensions.getCurrentLOTRDimensionOrFallback(mc.level);
				Faction currentFaction = alignData.getCurrentViewedFaction();
				if (currentFaction != null) {
					FactionRegion currentRegion = currentFaction.getRegion();
					List<FactionRegion> regionList = factionSettings.getRegionsForDimension(currentDimension);
					List<Faction> factionList = factionSettings.getFactionsForRegion(currentRegion);
					int i;
					if (factionShift != 0) {
						i = factionList.indexOf(currentFaction);
						i += factionShift;
						i = IntMath.mod(i, factionList.size());
						alignData.setCurrentViewedFaction(factionList.get(i));
						changedAlignmentView = true;
					}

					if (groupShift != 0 && regionList != null && currentRegion != null) {
						alignData.setRegionLastViewedFaction(currentRegion, currentFaction);
						i = regionList.indexOf(currentRegion);
						i += groupShift;
						i = IntMath.mod(i, regionList.size());
						if(regionList.get(i).getName().toString().equals("lotr:special")) {
							i += groupShift;
							i = IntMath.mod(i, regionList.size());
						}
						alignData.setCurrentViewedFaction(alignData.getRegionLastViewedFaction(regionList.get(i)));
						changedAlignmentView = true;
					}
				}

				if (changedAlignmentView) {
					alignData.sendViewedFactionsToServer();
					alignmentChangeTick = 2;
				}

			});
		}

	}
}
