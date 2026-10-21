package com.github.maximuslotro.lotrrextended.client.gui.screen.settings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.menu.ExtendedAbstractOptionsScreen;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedDevelopmentScreen extends AbstractExtendedOptionsScreen {
	public List<SettingsCatagory> catagories = new ArrayList<>();
	private boolean isExporting = false;
	private ExtendedBlockIconRenderer itemRenderer16;
	private ExtendedBlockIconRenderer itemRenderer32;
	private ExtendedBlockIconRenderer itemRenderer64;
	private List<Item> itemsToExport;
	private int currentItemIndex = 0;
	private final AtomicInteger completedItemsRenderScaled = new AtomicInteger(0);
	private int failedItemCount = 0;
	private boolean make16Itemicons= false;
	private boolean make32Itemicons= false;
	private boolean make64Itemicons= false;
	private boolean make16Blockicons= false;
	private boolean make32Blockicons= false;
	private boolean make64Blockicons= false;
	private String output = "No output";
	public static int optionDevelopment = 0;

	public ExtendedDevelopmentScreen() {
		super(new StringTextComponent("Development"), SettingsPage.DEVELOPMENT);
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
		addButton(itemGen.addButtonNew("16x Item Icons: %s", button -> {
			make16Itemicons = !make16Itemicons;
		}, (button, pd) -> {return make16Itemicons;}, "gui.lotrextended.base.true", "gui.lotrextended.base.false", "Enable 16x item image generation"));
		addButton(itemGen.addButtonNew("32x Item Icons: %s", button -> {
			make32Itemicons = !make32Itemicons;
		}, (button, pd) -> {return make32Itemicons;}, "gui.lotrextended.base.true", "gui.lotrextended.base.false", "Enable 32x item image generation"));
		addButton(itemGen.addButtonNew("64x Item Icons: %s", button -> {
			make64Itemicons = !make64Itemicons;
		}, (button, pd) -> {return make64Itemicons;}, "gui.lotrextended.base.true", "gui.lotrextended.base.false", "Enable 64x item image generation"));
		addButton(itemGen.addButtonNew("16x Block Icons: %s", button -> {
			make16Blockicons = !make16Blockicons;
		}, (button, pd) -> {return make16Blockicons;}, "gui.lotrextended.base.true", "gui.lotrextended.base.false", "Enable 16x block image generation"));
		addButton(itemGen.addButtonNew("32x Block Icons: %s", button -> {
			make32Blockicons = !make32Blockicons;
		}, (button, pd) -> {return make32Blockicons;}, "gui.lotrextended.base.true", "gui.lotrextended.base.false", "Enable 32x block image generation"));
		addButton(itemGen.addButtonNew("64x Block Icons: %s", button -> {
			make64Blockicons = !make64Blockicons;
		}, (button, pd) -> {return make64Blockicons;}, "gui.lotrextended.base.true", "gui.lotrextended.base.false", "Enable 64x block image generation"));
		addButton(itemGen.addButtonNew("Generate Icons: %s", button -> {
			boolean checkAny = make16Itemicons || make32Itemicons || make64Itemicons || make16Blockicons || make32Blockicons || make64Blockicons;
			if(isExporting || !checkAny) {
				return;
			}
			itemsToExport= new ArrayList<>();
			for(Item item : ForgeRegistries.ITEMS) {
				if(item != Items.AIR) {
					boolean isBlock = item instanceof BlockItem;
					if((isBlock && (make16Blockicons || make32Blockicons || make64Blockicons)) || (!isBlock && (make16Itemicons || make32Itemicons || make64Itemicons))) {
						this.itemsToExport.add(item);
					}
				}
			}
			isExporting = true;
			failedItemCount = 0;
			if(itemRenderer16 != null) {
				itemRenderer16.close();
			}
			if(itemRenderer32 != null) {
				itemRenderer32.close();
			}
			if(itemRenderer64 != null) {
				itemRenderer64.close();
			}
			if(make16Itemicons || make16Blockicons) {
				itemRenderer16 = new ExtendedBlockIconRenderer(16);
			}
			if(make32Itemicons || make32Blockicons) {
				itemRenderer32 = new ExtendedBlockIconRenderer(32);
			}
			if(make64Itemicons || make64Blockicons) {
				itemRenderer64 = new ExtendedBlockIconRenderer(64);
			}
			currentItemIndex = 0;
			completedItemsRenderScaled.set(0);
		}, (button, pd) -> {return (make16Itemicons || make32Itemicons || make64Itemicons || make16Blockicons || make32Blockicons || make64Blockicons) && !isExporting;}, "", "", "May crash, report any issues!"));
		addButton(itemGen.addButtonNew(output, button -> {
		}, (button, pd) -> {
			button.setMessage(new StringTextComponent(output));
			return null;
		}, "", "", "Item generation output"));
		addButton(itemGen.addButtonNew("Make items.json", button -> {
			Minecraft mc = Minecraft.getInstance();
			generateInfoboxJson(new File(mc.gameDirectory, "lotrextended_item_images"));
		}, (button, pd) -> true, "", "", "May crash, report any issues!"));
		catagories.add(itemGen);
		SettingsCatagory spawnMaps = new SettingsCatagory(this, 1, x, y, xTabs);
		addButton(spawnMaps.addCatagoryButton("Distribution Maps"));
		catagories.add(spawnMaps);
		SettingsCatagory speechbank = new SettingsCatagory(this, 2, x, y, xTabs);
		addButton(speechbank.addCatagoryButton("Speechbank Tools"));
		catagories.add(speechbank);
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
		if(isExporting) {
			List<ExtendedBlockIconRenderer> renderers = new ArrayList<>();

			if(itemRenderer16 != null) renderers.add(itemRenderer16);
			if(itemRenderer32 != null) renderers.add(itemRenderer32);
			if(itemRenderer64 != null) renderers.add(itemRenderer64);
			if(currentItemIndex < itemsToExport.size()) {
				List<ItemStack> batch16 = new ArrayList<>();
				List<ItemStack> batch32 = new ArrayList<>();
				List<ItemStack> batch64 = new ArrayList<>();
				int batchEnd = Math.min(currentItemIndex + 16, itemsToExport.size());
				for(int i = currentItemIndex; i < batchEnd; i++) {
					Item item = itemsToExport.get(i);
					boolean isBlock = item instanceof BlockItem;
					ItemStack stack = new ItemStack(item);
					if(isBlock) {
						if(make16Blockicons && itemRenderer16 != null) batch16.add(stack);
						if(make32Blockicons && itemRenderer32 != null) batch32.add(stack);
						if(make64Blockicons && itemRenderer64 != null) batch64.add(stack);
					} else {
						if(make16Itemicons && itemRenderer16 != null) batch16.add(stack);
						if(make32Itemicons && itemRenderer32 != null) batch32.add(stack);
						if(make64Itemicons && itemRenderer64 != null) batch64.add(stack);
					}
				}
				if(!batch16.isEmpty() && itemRenderer16 != null) {
					itemRenderer16.exportItemsBatch(batch16, completedItemsRenderScaled);
				}
				if(!batch32.isEmpty() && itemRenderer32 != null) {
					itemRenderer32.exportItemsBatch(batch32, completedItemsRenderScaled);
				}
				if(!batch64.isEmpty() && itemRenderer64 != null) {
					itemRenderer64.exportItemsBatch(batch64, completedItemsRenderScaled);
				}
				currentItemIndex = batchEnd;

				int totalImages = 0;
				for(Item item : itemsToExport) {
					boolean blockItem = item instanceof BlockItem;
					if (blockItem) {
						if(make16Blockicons) totalImages++;
						if(make32Blockicons) totalImages++;
						if(make64Blockicons) totalImages++;
					} else {
						if(make16Itemicons) totalImages++;
						if(make32Itemicons) totalImages++;
						if(make64Itemicons) totalImages++;
					}
				}
				output = "Exporting: " + completedItemsRenderScaled.get() + "/" + totalImages + " images";
			} else {
				isExporting = false;
				for(ExtendedBlockIconRenderer renderer : renderers) {
					List<ItemStack> currentFailedItems = renderer.getFailedExports();
					if (!currentFailedItems.isEmpty()) {
						//this.finishedWithErrors = true;
						this.failedItemCount += currentFailedItems.size();
					}
				}
				if(failedItemCount<=0) {
					output="Export completed!";
					ExtendedLog.info("Export completed! Exported {} items", itemsToExport.size());
				} else {
					output="Export finished with failures.";
					ExtendedLog.warn("Export finished with {} failures.", this.failedItemCount);
				}
			}
		}
	}

	public static void generateInfoboxJson(File outputDir) {
		Map<String, Map<String, Object>> masterMap = new HashMap<>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		for(Item item : ForgeRegistries.ITEMS) {
			ResourceLocation resourceName = item.getRegistryName();
			Map<String, Object> entryData = new HashMap<>();
			entryData.put("name", item.getName(new ItemStack(item)).getString());
			entryData.put("image", "items/"+resourceName.getNamespace() + "/" + resourceName.getPath() + ".png");
			if (item.getItemCategory() != null) {
				entryData.put("tab", item.getItemCategory().getDisplayName().getString());
			}
			entryData.put("rarity", item.getRarity(new ItemStack(item)).toString());
			if(item instanceof BlockItem) {
				Block block = ((BlockItem) item).getBlock();
				entryData.put("hardness", block.defaultBlockState().getDestroySpeed(null, null));
				entryData.put("resistance", block.getExplosionResistance());
				entryData.put("transparency", !block.defaultBlockState().isSolidRender(null, null));
				entryData.put("flammable", false);
				entryData.put("renewable", "No");
				int lightEmission = block.defaultBlockState().getLightEmission();
				entryData.put("luminous", lightEmission > 0 ? String.valueOf(lightEmission) : "No");
			} else if(item instanceof ArmorItem) {
				ArmorItem armor = (ArmorItem) item;
				entryData.put("armor", armor.getDefense());
				entryData.put("toughness", armor.getToughness());
			}
			entryData.put("stack", item.getMaxStackSize());

			entryData.put("renewable", "No");
			if(item.isEdible()) {
				Food food = item.getFoodProperties();
				if(food != null) {
					entryData.put("hunger", food.getNutrition());
					entryData.put("saturation", food.getSaturationModifier());
				}
			}
			masterMap.put(resourceName.toString(), entryData);
		}
		try {
			if(!outputDir.exists()) {
				outputDir.mkdirs();
			}
			File outFile = new File(outputDir, "infobox_data.json");
			try(FileWriter writer = new FileWriter(outFile)) {
				gson.toJson(masterMap, writer);
			}
		} catch(IOException e) {
			e.printStackTrace();
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
		private ExtendedDevelopmentScreen screen;
		private final int index;
		private final int placementXOffset;
		private final int placementYOffset;
		private final int catagoryXOffset;
		private int offsetAmount=0;
		private final List<ExtendedTogglableTitleButton<Boolean>> bottons = new ArrayList<>();
		private final List<ExtendedSlider> sliders = new ArrayList<>();
		private Widget catagoryButton;

		public SettingsCatagory(ExtendedDevelopmentScreen pScreen, int pIndex, int xStart, int yStart, int pCatagoryXOffset) {
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
						ExtendedDevelopmentScreen.optionDevelopment=index;
					}, 0.13f, 0.92f, 0.71f) {
				@Override
				public boolean setColor(){
					return ExtendedDevelopmentScreen.optionDevelopment==index;
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
			if(ExtendedClientOptionsScreen.optionClient==index) {
				for(ExtendedTogglableTitleButton<Boolean> widget : bottons) {
					widget.onTick(true);
				}
			}
		}
		public void checkVisability(){
			for(Widget widget : bottons) {
				widget.visible = ExtendedDevelopmentScreen.optionDevelopment==index; // Hide/Show widget if this category is selected
			}
			for(Widget widget : sliders) {
				widget.visible = ExtendedDevelopmentScreen.optionDevelopment==index; // Hide/Show widget if this category is selected
			}
		}
	}
}
