package com.github.maximuslotro.lotroExtended.mixins.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.client.gui.BasicIngameScreen;
import lotr.client.gui.MiddleEarthFactionsScreen;
import lotr.client.gui.MiddleEarthMasterMenuScreen;
import lotr.client.gui.map.MiddleEarthMapScreen;
import lotr.client.gui.widget.button.MiddleEarthMenuButton;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

@Mixin(MiddleEarthMasterMenuScreen.class)
public class MixinMiddleEarthMasterMenuScreen extends BasicIngameScreen {

	public MixinMiddleEarthMasterMenuScreen(ITextComponent titleIn) {
		super(titleIn);
	}

	@Shadow(remap=false)
	public static void resetLastMenuScreen() {}
	
	/**
	 * @author maximuslotr
	 * @reason injections were to hard
	 * */
	@Overwrite
	public void init() {
		super.init();
		resetLastMenuScreen();
		int midX = width / 2;
		int midY = height / 2;
		int buttonGap = 10;
		int buttonSize = 32;

		addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Achievements"), 2, 65));
		addButton(new MiddleEarthMenuButton(0, 0, MiddleEarthMapScreen.class, new TranslationTextComponent("gui.lotr.menu.map"), 3, 77));
		addButton(new MiddleEarthMenuButton(0, 0, MiddleEarthFactionsScreen.class, new TranslationTextComponent("gui.lotr.menu.factions"), 4, 70));
		addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("?"), 0, -1));
		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Fellowships"), 6, 71));
		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Titles"), 7, 84));
		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Shields"), 5, 83));
		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Options"), 1, 79));

		List<MiddleEarthMenuButton> menuButtonsToArrange = new ArrayList<MiddleEarthMenuButton>();

		for (Widget widget : buttons) {
			if (widget instanceof MiddleEarthMenuButton) {
				MiddleEarthMenuButton menuButton = (MiddleEarthMenuButton) widget;
				menuButton.active = menuButton.canDisplayMenu();
				menuButtonsToArrange.add(menuButton);
			}
		}

		int numButtons = menuButtonsToArrange.size();
		int numTopRowButtons = (numButtons - 1) / 2 + 1;
		int numBtmRowButtons = numButtons - numTopRowButtons;
		int topRowLeft = midX - (numTopRowButtons * buttonSize + (numTopRowButtons - 1) * buttonGap) / 2;
		int btmRowLeft = midX - (numBtmRowButtons * buttonSize + (numBtmRowButtons - 1) * buttonGap) / 2;

		for (int l = 0; l < numButtons; ++l) {
			MiddleEarthMenuButton button = (MiddleEarthMenuButton) menuButtonsToArrange.get(l);
			if (l < numTopRowButtons) {
				button.x = topRowLeft + l * (buttonSize + buttonGap);
				button.y = midY - buttonGap / 2 - buttonSize;
			} else {
				button.x = btmRowLeft + (l - numTopRowButtons) * (buttonSize + buttonGap);
				button.y = midY + buttonGap / 2;
			}
		}

	}
	
//	@Inject(method = "init()V", 
//			at = @At(value = "INVOKE", 
//			target = "Llotr/client/gui/MiddleEarthMasterMenuScreen;resetLastMenuScreen()V", 
//			remap=false,
//			shift = At.Shift.AFTER))
//	public void addAchevmentButton(CallbackInfo ci) {
//		//((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Achievements"), 2, 65));
//		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Fellowships"), 6, 71));
//		//((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Titles"), 7, 84));
//		//((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Shields"), 5, 83));
//		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Options"), 1, 79));
//	}
//	@Inject(method = "init()V", 
//			at = @At(value = "NEW", 
//			target = "Ljava/util/ArrayList;",
//			shift = At.Shift.AFTER))
//	public void addFellowshipButton(CallbackInfo ci) {
//		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Fellowships"), 6, 71));
//		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Titles"), 7, 84));
//		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Shields"), 5, 83));
//		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Options"), 1, 79));
//	}
}
