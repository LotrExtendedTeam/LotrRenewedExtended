package lotr.client.gui.menu;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedColorChangingButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTogglableTitleButton;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCServerSettingsPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCServerSettingsPacket.ExtendedSettingToggles;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.MiddleEarthMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.client.gui.widget.Slider;
import net.minecraftforge.fml.client.gui.widget.Slider.ISlider;

public class ExtendedServerOptionsScreen extends MiddleEarthMenuScreen {
	public int option = 0;
	public List<SettingsCatagory> catagories = new ArrayList<>();

	public ExtendedServerOptionsScreen() {
		super(new StringTextComponent("OPTIONS"));
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		this.addButton(new Button(Math.max(0, this.guiLeft - 140), this.guiTop + 20 + (this.ySize + 20) / 4, 100, 20, 
				new TranslationTextComponent("gui.lotrextended.settings.button"), 
				button -> {
					this.minecraft.setScreen(new ExtendedOptionsScreen());
				}) {
		});
		this.addButton(new ExtendedColorChangingButton(Math.max(0, this.guiLeft - 140), this.guiTop + 40 + (this.ySize + 20) / 4, 100, 20, 
				new TranslationTextComponent("gui.lotrextended.serversettings.button"), 
				button -> {
					this.minecraft.setScreen(new ExtendedServerOptionsScreen());
				}, 0.13f, 0.92f, 0.71f) {
			@Override
			public boolean setColor(){
				return true;
			}
		});
		int x=guiLeft+90;
		int xTabs=guiLeft-35;
		int y=guiTop+40;

		for(SettingsCatagory catagories : catagories) {
			catagories.clean();
		}
		catagories.clear();

		SettingsCatagory gameplay = new SettingsCatagory(this, 0, x, y, xTabs);
		addButton(gameplay.addCatagoryButton("gui.lotrextended.serversettings.category.gameplay"));
		addButton(gameplay.addButton("gui.lotrextended.serversettings.bannerprotection.setting", 
				ExtendedServerConfig.enableBannerProtection, ExtendedSettingToggles.BANNER_PROTECTION, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableBannerProtection"));
		catagories.add(gameplay);

		for(SettingsCatagory catagory : catagories) {
			catagory.checkVisability();
		}
	}

	private static void toggleValue(ForgeConfigSpec.BooleanValue configSpec, ExtendedSettingToggles type) {
		//configSpec.next(); TODO be able to access BuilderContext for localKey and range context
		ExtendedPacketHandler.sendToServer(new ExtendedCServerSettingsPacket(type, !configSpec.get()));
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		// Render titles
		TextComponent title = new TranslationTextComponent("gui.lotrextended.serversettings.title");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop+10), 16777215);
		for(SettingsCatagory catagories : catagories) {
			catagories.checkVisability();
		}
		for(SettingsCatagory catagories : catagories) {
			catagories.handleUpdates();
		}
		for(Widget button : this.buttons) {
			if(button.isHovered() && button.visible) {
				button.renderToolTip(matStack, mouseX, mouseY);
			}
		}
		super.render(matStack, mouseX, mouseY, tick);
	}

	public static class SettingsCatagory {
		private ExtendedServerOptionsScreen screen;
		private final int index;
		private final int placementXOffset;
		private final int placementYOffset;
		private final int catagoryXOffset;
		private int offsetAmount=0;
		private final List<ExtendedTogglableTitleButton<ExtendedServerConfig>> bottons = new ArrayList<>();
		private final List<Widget> widgets = new ArrayList<>();
		private Widget catagoryButton;

		public SettingsCatagory(ExtendedServerOptionsScreen pScreen, int pIndex, int xStart, int yStart, int pCatagoryXOffset) {
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
						screen.option=index;
					}, 0.13f, 0.92f, 0.71f) {
				@Override
				public boolean setColor(){
					return screen.option==index;
				}
			};
			return catagoryButton;
		}
		public Widget addButton(String pMessage, ForgeConfigSpec.BooleanValue configSpec, ExtendedSettingToggles type, String enabled, String disabled, String configKey) {
			ExtendedTogglableTitleButton<ExtendedServerConfig> button = new ExtendedTogglableTitleButton<>(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, new TranslationTextComponent(pMessage), 
					wdget -> toggleValue(configSpec, type), (wdget, matrixStack, pMouseX1, pMouseY1) -> {
						screen.renderTooltip(matrixStack, screen.font.split(new TranslationTextComponent(String.format("config.%s.%s", LotrExtendedMod.MODID, configKey)), 200), pMouseX1, pMouseY1);
					}, (wdget, tickData) -> configSpec.get(), enabled, disabled);
			bottons.add(button);
			offsetAmount+=1;
			return button;
		}

		public Slider addSlider(String prefix, String suffix, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, IPressable handler, @Nullable ISlider par) {
			Slider slider = new Slider(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, new TranslationTextComponent(prefix), new TranslationTextComponent(suffix), 
					minVal, maxVal, currentVal, showDec, drawStr , handler, par);
			widgets.add(slider);
			offsetAmount+=1;
			return slider;
		}

		public void clean() {
			screen=null;
			catagoryButton=null;
			bottons.clear();
			widgets.clear();
		}

		public void handleUpdates() {
			if(screen.option==index) {
				for(ExtendedTogglableTitleButton<ExtendedServerConfig> widget : bottons) {
					widget.onTick(null);
					widget.visible = true;
				}
				for(Widget widget : widgets) {
					widget.visible = true;
				}
			}
		}
		public void checkVisability(){
			for(Widget widget : bottons) {
				widget.visible = screen.option==index; // Hide/Show widget if this category is selected
			}
			for(Widget widget : widgets) {
				widget.visible = screen.option==index; // Hide/Show widget if this category is selected
			}
		}

	}
}
