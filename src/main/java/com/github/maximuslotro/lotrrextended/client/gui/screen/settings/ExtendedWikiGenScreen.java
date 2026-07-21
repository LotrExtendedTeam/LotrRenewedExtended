package com.github.maximuslotro.lotrrextended.client.gui.screen.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.maximuslotro.lotrextended.mixinhelpers.MixinConstants;
import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedSlider;
import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedSlider.IGetValue;
import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedSlider.ISetValue;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedColorChangingButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTogglableTitleButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTogglableTitleButton.IOnTick;
import com.github.maximuslotro.lotrrextended.client.render.ExtendedBlockIconRenderer;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.menu.ExtendedAbstractOptionsScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedWikiGenScreen extends AbstractExtendedOptionsScreen {
	public List<SettingsCatagory> catagories = new ArrayList<>();
	private boolean isExporting = false;
	private ExtendedBlockIconRenderer itemRenderer16;
	private ExtendedBlockIconRenderer itemRenderer32;
	private ExtendedBlockIconRenderer itemRenderer64;
	private List<Item> itemsToExport;
	private int currentItemIndex = 0;
	private final AtomicInteger completedItemsRenderScaled = new AtomicInteger(0);
	private int failedItemCount = 0;
	private boolean make16icons= false;
	private boolean make32icons= false;
	private boolean make64icons= false;
	private boolean makeItemIcons= false;
	private String output = "No output";

	public ExtendedWikiGenScreen() {
		super(new StringTextComponent("Wiki Generation"), SettingsPage.WIKI);
	}

	@Override
	public void onClose() {
		MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance = ExtendedClientConfig.structureBlockOutlineViewDistance.get();
		if (this.itemRenderer16 != null) {
			this.itemRenderer16.close();
		}
		if (this.itemRenderer32 != null) {
			this.itemRenderer32.close();
		}
		if (this.itemRenderer64 != null) {
			this.itemRenderer64.close();
		}
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		ExtendedAbstractOptionsScreen.lastSettingScreen=this.getClass();
		int x=leftPos+90;
		int xTabs=leftPos-35;
		int y=topPos+40;

		for(SettingsCatagory catagories : catagories) {
			catagories.clean();
		}
		catagories.clear();

		SettingsCatagory itemGen = new SettingsCatagory(this, 0, x, y, xTabs);
		addButton(itemGen.addCatagoryButton("Wiki Image Generation"));
		addButton(itemGen.addButtonNew("16x Icons: %s", button -> {
			make16icons = !make16icons;
		}, (button, pd) -> {return make16icons;}, "gui.lotrextended.base.true", "gui.lotrextended.base.false", "Enable 16x item image generation"));
		addButton(itemGen.addButtonNew("32x Icons: %s", button -> {
			make32icons = !make32icons;
		}, (button, pd) -> {return make32icons;}, "gui.lotrextended.base.true", "gui.lotrextended.base.false", "Enable 32x item image generation"));
		addButton(itemGen.addButtonNew("64x Icons: %s", button -> {
			make64icons = !make64icons;
		}, (button, pd) -> {return make64icons;}, "gui.lotrextended.base.true", "gui.lotrextended.base.false", "Enable 64x item image generation"));
		addButton(itemGen.addButtonNew("Gen item textures: %s", button -> {
			makeItemIcons = !makeItemIcons;
		}, (button, pd) -> {return makeItemIcons;}, "gui.lotrextended.base.true", "gui.lotrextended.base.false", "In addition to block textures, gen item textures"));
		addButton(itemGen.addButtonNew("Generate Icons: %s", button -> {
			if(isExporting || (!make16icons && !make32icons && !make64icons)) {
				return;
			}
			itemsToExport= new ArrayList<>();
			for (Item item : ForgeRegistries.ITEMS) {
				if (item != Items.AIR && (makeItemIcons||item instanceof BlockItem)) {
					this.itemsToExport.add(item);
				}
			}
			isExporting = true;
			failedItemCount = 0;
			if (itemRenderer16 != null) {
				itemRenderer16.close();
			}
			if (itemRenderer32 != null) {
				itemRenderer32.close();
			}
			if (itemRenderer64 != null) {
				itemRenderer64.close();
			}
			if(make16icons) {
				itemRenderer16 = new ExtendedBlockIconRenderer(16);
			}
			if(make32icons) {
				itemRenderer32 = new ExtendedBlockIconRenderer(32);
			}
			if(make64icons) {
				itemRenderer64 = new ExtendedBlockIconRenderer(64);
			}
			currentItemIndex = 0;
			completedItemsRenderScaled.set(0);
		}, (button, pd) -> {return (make16icons || make32icons || make64icons) && !isExporting;}, "", "", "May crash, report any issues!"));
		addButton(itemGen.addButtonNew(output, button -> {
		}, (button, pd) -> {
			button.setMessage(new StringTextComponent(output));
			return null;
		}, "", "", "Item generation output"));
		catagories.add(itemGen);
		SettingsCatagory spawnMaps = new SettingsCatagory(this, 1, x, y, xTabs) {
		};
		addButton(spawnMaps.addCatagoryButton("Distribution Maps"));
		catagories.add(spawnMaps);
		for(SettingsCatagory catagory : catagories) {
			catagory.checkVisability();
		}
	}
	/**
	 * This method contains substantial portions of code derived from BlockExporter
	 * (https://github.com/KazuOfficial/blockexporter), which is licensed under the MIT License.
	 *
	 * Original work by KazuOfficial.
	 *
	 * Modifications have been made to integrate the code into this project and adapt it
	 * for extended functionality within a Minecraft 1.16.5 environment.
	 *
	 * The original MIT License terms apply to the reused portions of this code.
	 */
	@Override
	public void tick() {
		super.tick();
		if (isExporting) {
			List<ExtendedBlockIconRenderer> renderers = new ArrayList<>();

			if (itemRenderer16 != null) renderers.add(itemRenderer16);
			if (itemRenderer32 != null) renderers.add(itemRenderer32);
			if (itemRenderer64 != null) renderers.add(itemRenderer64);
			if (currentItemIndex < itemsToExport.size()) {
				List<ItemStack> batch = new ArrayList<>();
				int batchEnd = Math.min(currentItemIndex + 16, itemsToExport.size());

				for (int i = currentItemIndex; i < batchEnd; i++) {
					batch.add(new ItemStack(itemsToExport.get(i)));
				}
				if (!batch.isEmpty()) {
				    for (ExtendedBlockIconRenderer renderer : renderers) {
				        renderer.exportItemsBatch(batch, completedItemsRenderScaled);
				    }
				}
				currentItemIndex = batchEnd;
				output = "Exporting: " + completedItemsRenderScaled.get() + "/" + itemsToExport.size() * renderers.size() + " images";
			} else {
				isExporting = false;
				for (ExtendedBlockIconRenderer renderer : renderers) {
					List<ItemStack> currentFailedItems = renderer.getFailedExports();
					if (!currentFailedItems.isEmpty()) {
						//this.finishedWithErrors = true;
						this.failedItemCount += currentFailedItems.size();
					}
			    }
				if (failedItemCount<=0) {
					output="Export completed!";
					ExtendedLog.info("Export completed! Exported {} items", itemsToExport.size());
				} else {
					output="Export finished with failures.";
					ExtendedLog.warn("Export finished with {} failures.", this.failedItemCount);
				}
			}
		}
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		// Render titles
		TextComponent title = new TranslationTextComponent("gui.lotrextended.settings.title");
		this.font.draw(matStack, title, leftPos+(100-(this.font.width(title)/2)), (topPos+10), 16777215);
		for(SettingsCatagory catagories : catagories) {
			catagories.checkVisability();
			catagories.handleUpdates();
		}
		super.render(matStack, mouseX, mouseY, tick);
	}

	public static class SettingsCatagory {
		private ExtendedWikiGenScreen screen;
		private final int index;
		private final int placementXOffset;
		private final int placementYOffset;
		private final int catagoryXOffset;
		private int offsetAmount=0;
		private final List<ExtendedTogglableTitleButton<Boolean>> bottons = new ArrayList<>();
		private final List<ExtendedSlider> sliders = new ArrayList<>();
		private Widget catagoryButton;

		public SettingsCatagory(ExtendedWikiGenScreen pScreen, int pIndex, int xStart, int yStart, int pCatagoryXOffset) {
			this.screen = pScreen;
			this.index = pIndex;
			this.placementXOffset = xStart;
			this.placementYOffset = yStart;
			this.catagoryXOffset = pCatagoryXOffset;
		}

		public Widget addCatagoryButton(String pMessage) {
			if(catagoryButton!=null) {
				throw new IllegalStateException("Can't register more than one category button for index: "+index);
			}
			catagoryButton = new ExtendedColorChangingButton(catagoryXOffset, placementYOffset + 24 * index, 120, 20, new TranslationTextComponent(pMessage), 
				button -> {
					ExtendedWikiGenScreen.option=index;
				}, 0.13f, 0.92f, 0.71f) {
				@Override
				public boolean setColor(){
					return ExtendedWikiGenScreen.option==index;
				}
			};
			return catagoryButton;
		}
		public Widget addButtonNew(String stringMessageKey, IPressable pOnPress, IOnTick<Boolean> pOnTick, String enabled, String disabled, String tooltip) {
			ExtendedTogglableTitleButton<Boolean> button = new ExtendedTogglableTitleButton<>(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, stringMessageKey, pOnPress, 
				(wdget, matrixStack, pMouseX, pMouseY) -> {
					screen.renderTooltip(matrixStack, screen.font.split(new StringTextComponent(tooltip), 200), pMouseX, pMouseY);
				}, pOnTick, enabled, disabled);
			bottons.add(button);
			offsetAmount+=1;
			return button;
		}
		public ExtendedSlider addSlider(String localKey, double minVal, double maxVal, float steps, IGetValue valueGetter, ISetValue valueSetter) {
			ExtendedSlider slider = new ExtendedSlider(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, localKey, minVal, maxVal, steps, valueGetter, valueSetter);
			sliders.add(slider);
			offsetAmount+=1;
			return slider;
		}

		public void clean() {
			screen=null;
			catagoryButton=null;
			bottons.clear();
			sliders.clear();
		}
		public void handleUpdates() {
			if(ExtendedClientOptionsScreen.option==index) {
				for(ExtendedTogglableTitleButton<Boolean> widget : bottons) {
					widget.onTick(true);
				}
			}
		}
		public void checkVisability(){
			for(Widget widget : bottons) {
				widget.visible = ExtendedWikiGenScreen.option==index; // Hide/Show widget if this category is selected
			}
			for(Widget widget : sliders) {
				widget.visible = ExtendedWikiGenScreen.option==index; // Hide/Show widget if this category is selected
			}
		}
	}
}
