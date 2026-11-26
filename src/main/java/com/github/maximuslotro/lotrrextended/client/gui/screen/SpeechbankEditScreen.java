package com.github.maximuslotro.lotrrextended.client.gui.screen;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.menu.ExtendedAbstractOptionsScreen;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SpeechbankEditScreen extends ExtendedAbstractBasicScreen {
	public static final TranslationTextComponent TITLE = new TranslationTextComponent("gui.lotrextended.speechbank.editor");
	//Positioning
	private static final int TOP_BAR_HEIGHT = 21;
	private static final int TAB_BAR_HEIGHT = 18;
	private static final int TAB_SCROLL_BTN_WIDTH = 14;
	private static final int TAB_SCROLL_BTN_GAP = 2;
	private static final int TAB_MAX_WIDTH = 140;
	private static final int DIVIDER_WIDTH = 4;
	private static final int MIN_LEFT_PANEL_WIDTH = 120;
	private static final int MIN_RIGHT_PANEL_WIDTH = 200;
	//Colors
	private static final int COLOR_TOP_BAR_BG = 0xE6181818;
	private static final int COLOR_TAB_BAR_BG = 0xE6222222;
	private static final int COLOR_LEFT_PANEL_BG = 0xCC1E1E1E;
	private static final int COLOR_RIGHT_PANEL_BG = 0xCC161616;
	private static final int COLOR_DIVIDER = 0xFF3A3A3A;
	private static final int COLOR_DIVIDER_ACTIVE = 0xFF7AA2F7;
	private static final int COLOR_BORDER = 0xFF2D2D2D;
	private static final int COLOR_TAB_ACTIVE = 0xFF2E3440;
	private static final int COLOR_TAB_INACTIVE = 0xFF1A1D24;
	private final List<SpeechbankTab> tabs = new ArrayList<>();
	private int activeTabIndex = -1;
	private int tabScrollOffset = 0;
	private double splitRatio = 0.30D;
	private boolean draggingDivider = false;
	private boolean hoveringDivider = false;
	private int tabBarTop;
	private int contentTop;
	private int contentBottom;
	private int contentHeight;
	private int rightPanelX0;
	private int rightPanelWidth;
	private int dividerX0;
	private int leftPanelX0;
	private int leftPanelWidth;
	private Button fileButton;
	private Button optionsButton;
	private Button helpButton;
	@Nullable
	public SpeechbankEditleftScreen leftPanel;
	private final List<IGuiEventListener> tabListeners = new ArrayList<>();
	@Nullable
	private SpeechbankEditRightScreen boundSubScreen;

	public SpeechbankEditScreen() {
		super(TITLE);
	}

	@Override
	protected void init() {
		super.init();
		this.leftPanel = new SpeechbankEditleftScreen(this);
		this.calculatePositions();
		this.fileButton = this.addButton(new Button(4, 0, 44, 20, new StringTextComponent("Open"), (btn) -> this.onOpenMenuClicked()));
		this.optionsButton = this.addButton(new Button(52, 0, 44, 20, new StringTextComponent("New"), (btn) -> this.onNewMenuClicked()));
		this.helpButton = this.addButton(new Button(100, 0, 44, 20, new StringTextComponent("Help"), (btn) -> this.onHelpMenuClicked()));
		this.addButton(new Button(width - 50, 0, 50, 20, new TranslationTextComponent("gui.lotrextended.base.return"), btn -> this.minecraft.setScreen(new ExtendedAbstractOptionsScreen())));
		this.children.add(leftPanel);
		this.children.add(new TabScrollControlsListener());
		this.children.add(new DeviderListener());
		this.rebuildTabListeners();
		this.refreshTabs(true);
		this.syncLeftPanel();
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
	}

	private void rebuildTabListeners() {
		this.children.removeAll(this.tabListeners);
		this.tabListeners.clear();
		for (int i = 0; i < this.tabs.size(); ++i) {
			TabHitRegion hitRegion = new TabHitRegion(i);
			this.tabListeners.add(hitRegion);
			this.children.add(hitRegion);
		}
	}

	private void refreshTabs(boolean forceScreenRefresh) {
		leftPanel.refreshLayout(this.leftPanelX0, this.contentTop, this.leftPanelWidth, this.contentBottom - this.contentTop);
		SpeechbankTab active = this.getActiveTab();
		SpeechbankEditRightScreen newSubScreen = active != null ? active.subScreen : null;
		if(this.boundSubScreen != null && this.boundSubScreen != newSubScreen) {
			this.children.remove(this.boundSubScreen);
			this.boundSubScreen = null;
		}

		if(newSubScreen != null) {
			if(forceScreenRefresh || this.boundSubScreen != newSubScreen) {
				newSubScreen.refreshLayout(this.rightPanelWidth, this.contentHeight, this.rightPanelX0, this.contentTop);
				if(!this.children.contains(newSubScreen)) {
					this.children.add(newSubScreen);
				}
				this.boundSubScreen = newSubScreen;
			} else {
				newSubScreen.refreshLayout(this.rightPanelWidth, this.contentHeight, this.rightPanelX0, this.contentTop);
			}
		}
	}

	public void calculatePositions() {
		this.tabBarTop = TOP_BAR_HEIGHT;
		this.contentTop = TOP_BAR_HEIGHT + TAB_BAR_HEIGHT;
		this.contentBottom = this.height;
		this.contentHeight = Math.max(0, this.contentBottom - this.contentTop);
		int usableWidth = Math.max(DIVIDER_WIDTH, this.width - DIVIDER_WIDTH);

		int minLeft = Math.min(MIN_LEFT_PANEL_WIDTH, usableWidth / 2);
		int minRight = Math.min(MIN_RIGHT_PANEL_WIDTH, usableWidth / 2);
		int maxLeft = Math.max(minLeft, usableWidth - minRight);

		int desiredLeft = (int)Math.round(usableWidth * this.splitRatio);
		this.leftPanelWidth = MathHelper.clamp(desiredLeft, minLeft, maxLeft);
		if(usableWidth > 0) {
			this.splitRatio = (double)this.leftPanelWidth / (double)usableWidth;
		}

		this.leftPanelX0 = 0;
		this.dividerX0 = this.leftPanelX0 + this.leftPanelWidth;
		this.rightPanelX0 = this.dividerX0 + DIVIDER_WIDTH;
		this.rightPanelWidth = Math.max(0, this.width - this.rightPanelX0);
	}

	private int getTabWidth(int index) {
		SpeechbankTab tab = this.tabs.get(index);
		int desired = this.font.width(tab.getDisplayTitle()) + 14;
		return MathHelper.clamp(desired, 64, TAB_MAX_WIDTH);
	}

	private int getTotalTabsWidth() {
		int total = 0;
		for(int i = 0; i < this.tabs.size(); ++i) {
			total += getTabWidth(i);
			if(i + 1 < this.tabs.size()) {
				total += 2;
			}
		}
		return total;
	}

	private boolean needsTabScrollButtons() {
		int availableWidth = Math.max(0, this.width - 8);
		return getTotalTabsWidth() > availableWidth;
	}

	private int getTabStripMaxRight() {
		if(!needsTabScrollButtons()) {
			return this.width - 4;
		}
		int buttonsTotalWidth = TAB_SCROLL_BTN_WIDTH * 2 + TAB_SCROLL_BTN_GAP;
		return this.width - 4 - buttonsTotalWidth - 4;
	}

	private int getMaxTabScrollOffset() {
		if(this.tabs.isEmpty() || !needsTabScrollButtons()) {
			return 0;
		}
		int maxRight = getTabStripMaxRight();
		int offset = this.tabs.size() - 1;
		int usedWidth = 0;
		for(int i = this.tabs.size() - 1; i >= 0; --i) {
			int w = getTabWidth(i) + (usedWidth > 0 ? 2 : 0);
			if(4 + usedWidth + w > maxRight) {
				break;
			}
			usedWidth += w;
			offset = i;
		}
		return MathHelper.clamp(offset, 0, Math.max(0, this.tabs.size() - 1));
	}

	private void clampTabScrollOffset() {
		this.tabScrollOffset = MathHelper.clamp(this.tabScrollOffset, 0, getMaxTabScrollOffset());
	}

	private void ensureTabVisible(int index) {
		if(index < 0 || index >= this.tabs.size()) {
			return;
		}
		clampTabScrollOffset();
		if(index < this.tabScrollOffset) {
			this.tabScrollOffset = index;
			clampTabScrollOffset();
			return;
		}
		int maxRight = getTabStripMaxRight();
		while(this.tabScrollOffset < index) {
			int tabX = 4;
			boolean fits = false;
			for(int i = this.tabScrollOffset; i <= index; ++i) {
				int tabW = getTabWidth(i);
				if(i == index) {
					fits = (tabX + tabW <= maxRight);
				}
				tabX += tabW + 2;
			}
			if(fits) {
				break;
			}
			this.tabScrollOffset++;
		}
		clampTabScrollOffset();
	}

	public void scrollTabsLeft() {
		if(this.tabScrollOffset > 0) {
			this.tabScrollOffset--;
			clampTabScrollOffset();
		}
	}

	public void scrollTabsRight() {
		int maxOffset = getMaxTabScrollOffset();
		if(this.tabScrollOffset < maxOffset) {
			this.tabScrollOffset++;
			clampTabScrollOffset();
		}
	}

	private String truncateTitle(String text, int maxTextWidth) {
		if(maxTextWidth <= 0 || this.font.width(text) <= maxTextWidth) {
			return text;
		}
		String dots = "...";
		int dotsWidth = this.font.width(dots);
		if(maxTextWidth <= dotsWidth) {
			return this.font.plainSubstrByWidth(text, maxTextWidth);
		}
		String trimmed = this.font.plainSubstrByWidth(text, maxTextWidth - dotsWidth);
		return trimmed + dots;
	}

	public int addTab(SpeechbankTab session) {
		this.tabs.add(session);
		this.activeTabIndex = this.tabs.size() - 1;
		calculatePositions();
		ensureTabVisible(this.activeTabIndex);
		rebuildTabListeners();
		refreshTabs(true);
		syncLeftPanel();
		return this.activeTabIndex;
	}

	public void selectTab(int index) {
		if(index >= 0 && index < this.tabs.size() && index != this.activeTabIndex) {
			this.activeTabIndex = index;
			calculatePositions();
			ensureTabVisible(this.activeTabIndex);
			rebuildTabListeners();
			refreshTabs(true);
			syncLeftPanel();
		}
	}

	public void closeTab(int index) {
		if(index < 0 || index >= this.tabs.size()) {
			return;
		}
		this.tabs.remove(index);
		if(this.tabs.isEmpty()) {
			this.tabs.add(new SpeechbankTab(this, new ResourceLocation("speech/new_speechbank")));
			this.activeTabIndex = 0;
			this.tabScrollOffset = 0;
		} else {
			this.activeTabIndex = MathHelper.clamp(this.activeTabIndex, 0, this.tabs.size() - 1);
			ensureTabVisible(this.activeTabIndex);
		}
		calculatePositions();
		rebuildTabListeners();
		refreshTabs(true);
		syncLeftPanel();
	}

	@Nullable
	public SpeechbankTab getActiveTab() {
		if(this.activeTabIndex >= 0 && this.activeTabIndex < this.tabs.size()) {
			return this.tabs.get(this.activeTabIndex);
		}
		return null;
	}

	public void onOpenMenuClicked() {}

	public void onNewMenuClicked() {
		addTab(new SpeechbankTab(this, new ResourceLocation("speech/new_speechbank")));
	}

	public void onHelpMenuClicked() {}

	@Override
	public void tick() {
		SpeechbankTab active = this.getActiveTab();
		if(active != null) {
			active.subScreen.tick();
		}
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.hoveringDivider = this.isOverDivider(mouseX, mouseY);
		//Background
		renderBackground(matrixStack);
		//fill(matrixStack, 0, 0, this.width, this.height, COLOR_RIGHT_PANEL_BG);
		//Top Bar
		fill(matrixStack, 0, 0, this.width, TOP_BAR_HEIGHT, COLOR_TOP_BAR_BG);
		fill(matrixStack, 0, TOP_BAR_HEIGHT - 1, this.width, TOP_BAR_HEIGHT, COLOR_BORDER);
		//Tabs
		this.renderTabStrip(matrixStack, mouseX, mouseY);
		//Content Window
		fill(matrixStack, this.leftPanelX0, this.contentTop, this.leftPanelX0 + this.leftPanelWidth, this.contentBottom, COLOR_LEFT_PANEL_BG);
		fill(matrixStack, this.rightPanelX0, this.contentTop, this.rightPanelX0 + this.rightPanelWidth, this.contentBottom, COLOR_RIGHT_PANEL_BG);
		leftPanel.render(matrixStack, mouseX, mouseY, partialTicks);
		SpeechbankTab active = this.getActiveTab();
		if(active != null) {
			active.subScreen.render(matrixStack, mouseX, mouseY, partialTicks);
		}
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		//Divider
		this.renderDivider(matrixStack, mouseX, mouseY);
		if(this.hoveringDivider || this.draggingDivider) {
			String indicator = "<->";
			int textWidth = this.font.width(indicator);
			int cursorX = MathHelper.clamp(mouseX - textWidth / 2, 2, this.width - textWidth - 2);
			int cursorY = MathHelper.clamp(mouseY - 12, TOP_BAR_HEIGHT + TAB_BAR_HEIGHT + 2, this.height - 12);
			fill(matrixStack, cursorX - 2, cursorY - 1, cursorX + textWidth + 2, cursorY + 9, 0xE6111111);
			drawString(matrixStack, this.font, indicator, cursorX, cursorY, WHITE_TEXT);
		}
	}

	private void renderTabStrip(MatrixStack matrixStack, int mouseX, int mouseY) {
		fill(matrixStack, 0, this.tabBarTop, this.width, this.tabBarTop + TAB_BAR_HEIGHT, COLOR_TAB_BAR_BG);
		fill(matrixStack, 0, this.tabBarTop + TAB_BAR_HEIGHT - 1, this.width, this.tabBarTop + TAB_BAR_HEIGHT, COLOR_BORDER);
		clampTabScrollOffset();
		int tabX = 4;
		int tabY = this.tabBarTop + 2;
		int tabH = TAB_BAR_HEIGHT - 3;
		int maxRight = getTabStripMaxRight();
		for(int i = this.tabScrollOffset; i < this.tabs.size(); ++i) {
			SpeechbankTab tab = this.tabs.get(i);
			int tabW = getTabWidth(i);
			if(tabX + tabW > maxRight) {
				break;
			}

			String displayLabel = truncateTitle(tab.getDisplayTitle(), tabW - 12);

			boolean isActive = (i == this.activeTabIndex);
			boolean isHovered = mouseX >= tabX && mouseX < tabX + tabW && mouseY >= tabY && mouseY < tabY + tabH;
			int bg = isActive ? COLOR_TAB_ACTIVE : (isHovered ? 0xFF252A34 : COLOR_TAB_INACTIVE);
			fill(matrixStack, tabX, tabY, tabX + tabW, tabY + tabH, bg);
			if(isActive) {
				fill(matrixStack, tabX, tabY, tabX + tabW, tabY + 2, COLOR_DIVIDER_ACTIVE);
			}
			int textColor = isActive ? WHITE_TEXT : 0xFFB0B0B0;
			drawString(matrixStack, this.font, displayLabel, tabX + 6, tabY + (tabH - 8) / 2, textColor);

			tabX += tabW + 2;
		}

		if(needsTabScrollButtons()) {
			int rightBtnX = this.width - 4 - TAB_SCROLL_BTN_WIDTH;
			int leftBtnX = rightBtnX - TAB_SCROLL_BTN_GAP - TAB_SCROLL_BTN_WIDTH;
			int maxOffset = getMaxTabScrollOffset();
			boolean canLeft = this.tabScrollOffset > 0;
			boolean canRight = this.tabScrollOffset < maxOffset;
			boolean hoverLeft = canLeft && mouseX >= leftBtnX && mouseX < leftBtnX + TAB_SCROLL_BTN_WIDTH && mouseY >= tabY && mouseY < tabY + tabH;
			boolean hoverRight = canRight && mouseX >= rightBtnX && mouseX < rightBtnX + TAB_SCROLL_BTN_WIDTH && mouseY >= tabY && mouseY < tabY + tabH;
			int leftBg = !canLeft ? COLOR_TAB_INACTIVE : (hoverLeft ? 0xFF2E3440 : 0xFF252A34);
			int rightBg = !canRight ? COLOR_TAB_INACTIVE : (hoverRight ? 0xFF2E3440 : 0xFF252A34);
			fill(matrixStack, leftBtnX, tabY, leftBtnX + TAB_SCROLL_BTN_WIDTH, tabY + tabH, leftBg);
			fill(matrixStack, rightBtnX, tabY, rightBtnX + TAB_SCROLL_BTN_WIDTH, tabY + tabH, rightBg);
			int leftColor = canLeft ? WHITE_TEXT : 0xFF606060;
			int rightColor = canRight ? WHITE_TEXT : 0xFF606060;
			drawCenteredString(matrixStack, this.font, "<", leftBtnX + TAB_SCROLL_BTN_WIDTH / 2, tabY + (tabH - 8) / 2, leftColor);
			drawCenteredString(matrixStack, this.font, ">", rightBtnX + TAB_SCROLL_BTN_WIDTH / 2, tabY + (tabH - 8) / 2, rightColor);
		}
	}

	private void renderDivider(MatrixStack matrixStack, int mouseX, int mouseY) {
		int divColor = (this.draggingDivider || this.hoveringDivider) ? COLOR_DIVIDER_ACTIVE : COLOR_DIVIDER;
		fill(matrixStack, this.dividerX0, this.contentTop, this.dividerX0 + DIVIDER_WIDTH, this.contentBottom, divColor);
		int midY = (this.contentTop + this.contentBottom) / 2;
		int gripX = this.dividerX0 + 1;
		for(int offset = -8; offset <= 8; offset += 4) {
			fill(matrixStack, gripX, midY + offset, gripX + 2, midY + offset + 2, WHITE_TEXT);
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	private final class TabScrollControlsListener implements IGuiEventListener {
		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if(button != 0 || !needsTabScrollButtons()) {
				return false;
			}
			int tabY = tabBarTop + 2;
			int tabH = TAB_BAR_HEIGHT - 3;
			if(mouseY < tabY || mouseY >= tabY + tabH) {
				return false;
			}
			int rightBtnX = width - 4 - TAB_SCROLL_BTN_WIDTH;
			int leftBtnX = rightBtnX - TAB_SCROLL_BTN_GAP - TAB_SCROLL_BTN_WIDTH;

			if(mouseX >= leftBtnX && mouseX < leftBtnX + TAB_SCROLL_BTN_WIDTH) {
				scrollTabsLeft();
				return true;
			}
			if(mouseX >= rightBtnX && mouseX < rightBtnX + TAB_SCROLL_BTN_WIDTH) {
				scrollTabsRight();
				return true;
			}
			return false;
		}
	}

	private final class TabHitRegion implements IGuiEventListener {
		private final int index;

		private TabHitRegion(int index) {
			this.index = index;
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if(button == 0 && mouseY >= tabBarTop + 2 && mouseY < tabBarTop + TAB_BAR_HEIGHT - 1) {
				if(this.index < tabScrollOffset) {
					return false;
				}
				int tabX = 4;
				int maxRight = getTabStripMaxRight();
				for(int i = tabScrollOffset; i <= this.index; ++i) {
					int tabW = getTabWidth(i);
					if(tabX + tabW > maxRight) {
						return false;
					}
					if(i == this.index) {
						if(mouseX >= tabX && mouseX < tabX + tabW) {
							selectTab(this.index);
							return true;
						}
						return false;
					}
					tabX += tabW + 2;
				}
			}
			return false;
		}
	}

	private boolean isOverDivider(double mouseX, double mouseY) {
		return mouseY >= this.contentTop && mouseY <= this.contentBottom && mouseX >= this.dividerX0 - 1 && mouseX <= this.dividerX0 + DIVIDER_WIDTH + 1;
	}

	@Override
	public boolean mouseReleased(double pMouseX, double pMouseY, int button) {
		if(button == 0 && draggingDivider) {
			draggingDivider = false;
		}
		return super.mouseReleased(pMouseX, pMouseY, button);
	}

	private final class DeviderListener implements IGuiEventListener {
		@Override
		public boolean mouseClicked(double pMouseX, double pMouseY, int button) {
			if(button == 0 && isOverDivider(pMouseX, pMouseY)) {
				draggingDivider = true;
				return true;
			}
			return false;
		}

		@Override
		public boolean isMouseOver(double pMouseX, double pMouseY) {
			return isOverDivider(pMouseX, pMouseY);
		}

		@Override
		public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
			int usableWidth = Math.max(DIVIDER_WIDTH, width - DIVIDER_WIDTH);
			int minLeft = Math.min(MIN_LEFT_PANEL_WIDTH, usableWidth / 2);
			int minRight = Math.min(MIN_RIGHT_PANEL_WIDTH, usableWidth / 2);
			int maxLeft = Math.max(minLeft, usableWidth - minRight);
			int clampedLeft = MathHelper.clamp((int)Math.round(mouseX), minLeft, maxLeft);
			splitRatio = (double)clampedLeft / (double)usableWidth;
			calculatePositions();
			refreshTabs(false);
			return true;
		}

	}

	private void syncLeftPanel() {
		leftPanel.syncFromTab(this.getActiveTab());
	}

	public void onActiveTabModified(SpeechbankEditRightScreen source) {
		SpeechbankTab active = this.getActiveTab();
		if (active != null && active.subScreen == source) {
			active.dirty = true;
			syncLeftPanel();
		}
	}

	public static String getFileName(ResourceLocation location) {
		if (location == null) {
			return "";
		}
		String path = location.getPath();
		int slashIndex = path.lastIndexOf('/');
		return slashIndex >= 0 ? path.substring(slashIndex + 1) : path;
	}

	protected static class SpeechbankTab {
		public boolean dirty;
		public SpeechbankEditRightScreen subScreen;

		public SpeechbankTab(SpeechbankEditScreen pParentScreen, ResourceLocation filePath) {
			this.subScreen = new SpeechbankEditRightScreen(pParentScreen, filePath);
		}

		public String getDisplayTitle() {
			return this.dirty ? getFileName(this.subScreen.getFilePath()) + "*" : getFileName(this.subScreen.getFilePath());
		}
	}

	public interface PanelResize extends IRenderable {
		void refreshLayout(int pXOffset, int pYOffset, int pWidth, int pHeight);
	}
}
