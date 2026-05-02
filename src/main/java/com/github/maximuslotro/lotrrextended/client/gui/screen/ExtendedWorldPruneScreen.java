package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedCWorldPruneCommands;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSWorldPruneProgress;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedWorldRegenEngine;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedWorldRegenEngine.Stage;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.menu.ExtendedAbstractOptionsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedWorldPruneScreen extends ExtendedBasicAbstractScreen{
	public static final TranslationTextComponent TITLE = new TranslationTextComponent("gui.lotrextended.worldprune.title");
	private ExtendedWorldRegenEngine.Stage stage;

	private int totalChunks;
	private int deletableChunks;
	private int bannerProtectedChunks;
	private int waypointProtectedChunks;
	private int spawnProtectedChunks;
	private int errorProtectedChunks;

	private int currentRegions;
	private int totalRegions;
	private int deletedRegions;
	private int protectedRegions;
	private int loadedRegions;

	private float progress;
	private TranslationTextComponent statusText;

	private Button startButton;
	private Button cancelButton;
	private Button dryRunToggle;

	private boolean dryRun = true;
	private boolean running = false;

	private Button returnButton;

	public ExtendedWorldPruneScreen() {
		super(TITLE, 200, 256);
	}

	@Override
	public void onClose() {
		goBack();
	}

	@Override
	public void removed() {
		cancelPrune();
	}

	@Override
	public void init() {
		int centerX = this.width / 2;
		startButton = this.addButton(new Button(centerX - 100, this.height - 60, 98, 20, new TranslationTextComponent("gui.lotrextended.base.start"), btn -> startPrune()));
		cancelButton = this.addButton(new Button(centerX + 2, this.height - 60, 98, 20, new TranslationTextComponent("gui.lotrextended.base.cancel"), btn -> cancelPrune()));
		dryRunToggle = this.addButton(new Button(centerX - 100, this.height - 85, 200, 20, getDryRunText(), btn -> toggleDryRun()));
		returnButton = this.addButton(new Button(centerX - 100,  this.height - 35, 200, 20, new TranslationTextComponent("gui.lotrextended.base.return"), btn -> goBack()));

		updateButtonStates();
	}

	private void startPrune() {
		if (running) {
			return;
		}
		running = true;
		ExtendedPacketHandler.sendToServer(new ExtendedCWorldPruneCommands(dryRun, true));
		updateButtonStates();
	}

	private void cancelPrune() {
		if (!running) {
			return;
		}
		running = false;
		ExtendedPacketHandler.sendToServer(new ExtendedCWorldPruneCommands(dryRun, false));
		updateButtonStates();
	}

	private void goBack() {
		cancelPrune();
		this.minecraft.setScreen(new ExtendedAbstractOptionsScreen());
	}

	private ITextComponent getDryRunText() {
		return new TranslationTextComponent("gui.lotrextended.worldprune.dryrun.splitter", new TranslationTextComponent("gui.lotrextended.worldprune.mode.safe"),
			(dryRun ? new TranslationTextComponent("gui.lotrextended.base.on") : new TranslationTextComponent("gui.lotrextended.base.off")));
	}

	private void toggleDryRun() {
		dryRun = !dryRun;
		dryRunToggle.setMessage(getDryRunText());
	}

	private void updateButtonStates() {
		startButton.active = !running;
		dryRunToggle.active = !running;
		cancelButton.active = running;
		returnButton.active = !running;
	}

	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
		this.renderDirtBackground(0);
		updateProgress();
		drawCenteredString(stack, this.font, this.title, this.width / 2, 15, 0xFFFFFF);

		startButton.setMessage(stage == Stage.FINISHED || stage == Stage.CANCELED ? new TranslationTextComponent("gui.lotrextended.worldprune.runagain") : 
			new TranslationTextComponent("gui.lotrextended.base.start"));

		returnButton.setMessage(running ? new TranslationTextComponent("gui.lotrextended.worldprune.returndisabled") :
			new TranslationTextComponent("gui.lotrextended.base.return"));

		int left = this.width / 2 - 150;
		int right = this.width / 2 + 150;
		int top = this.height / 2 - 10;
		int bottom = top + 10;

		// STATUS TEXT (same as bossbar)
		drawCenteredString(stack, this.font, statusText, this.width / 2, top - 15, 0xA0A0A0);

		drawCenteredString(stack, font, new TranslationTextComponent("gui.lotrextended.worldprune.mode", (dryRun ? 
			new TranslationTextComponent("gui.lotrextended.worldprune.mode.safe") : new TranslationTextComponent("gui.lotrextended.worldprune.mode.live"))), 
			this.width / 2, 30, dryRun ? 0xFFFF55 : 0xFF5555);

		// BAR OUTLINE
		fill(stack, left - 1, top - 1, right + 1, bottom + 1, 0xFF000000);

		// BAR FILL
		int width = (int)((right - left) * progress);
		fill(stack, left, top, left + width, bottom, 0xFF00AAFF);

		// EXTRA INFO (like your chat messages)
		int y = top + 20;

		if (stage == Stage.CHECKING_CHUNKS) {
			drawCenteredString(stack, font, "Banner Protected Chunks: " + bannerProtectedChunks, this.width / 2, y , 0x55FF55);
			drawCenteredString(stack, font, "Spawn Protected Chunks: " + spawnProtectedChunks, this.width / 2, y + 12, 0x55FF55);
			drawCenteredString(stack, font, "Waypoint Protected Chunks: " + waypointProtectedChunks, this.width / 2, y + 24, 0x55FF55);
			drawCenteredString(stack, font, "Error Protected Chunks: " + errorProtectedChunks, this.width / 2, y + 36, 0xFFD966);
			drawCenteredString(stack, font, "Deletable Chunks: " + deletableChunks, this.width / 2, y + 48, 0xFFAA55);
		} else if (stage == Stage.DELETING_REGIONS) {
			drawCenteredString(stack, font, "Deleted "+deletedRegions + " / " + (totalRegions - protectedRegions - loadedRegions) + " Regions", this.width / 2, y, 0xFFA726);
		} else if (stage == ExtendedWorldRegenEngine.Stage.FINISHED || stage == ExtendedWorldRegenEngine.Stage.CANCELED) {
			drawCenteredString(stack, font, "Deleted Regions: " + deletedRegions, this.width / 2, y, 0xFFAA55);
			drawCenteredString(stack, font, "Protected Regions: " + protectedRegions, this.width / 2, y + 12, 0x55FF55);
			drawCenteredString(stack, font, "Skipped Regions(loaded): " + loadedRegions,  this.width / 2, y + 24, 0x47DBC9);
			drawCenteredString(stack, font, "Total Regions: " + totalRegions, this.width / 2, y + 36, 0xAAAAAA);
			if (dryRun) {
				drawCenteredString(stack, font,  "[Dry Run] No files were actually deleted", this.width / 2, y + 50, 0xFF5555);
			}
		}
		super.render(stack, mouseX, mouseY, tick);
	}

	private void updateProgress() {
		if (stage == ExtendedWorldRegenEngine.Stage.SCANNING_CHUNKS) {
			statusText = new TranslationTextComponent("gui.lotrextended.worldprune.scanning", currentRegions, totalRegions, totalChunks);
			progress = 0.001f;

		} else if (stage == ExtendedWorldRegenEngine.Stage.FINISHED) {
			progress = 1.0f;
			if (dryRun) {
				statusText = new TranslationTextComponent("gui.lotrextended.worldprune.finished.dry", deletedRegions, protectedRegions);
			} else {
				statusText = new TranslationTextComponent("gui.lotrextended.worldprune.finished", deletedRegions, protectedRegions, loadedRegions);
			}
		} else if (stage == ExtendedWorldRegenEngine.Stage.CANCELED) {
			progress = 0f;
			statusText = new TranslationTextComponent("gui.lotrextended.worldprune.canceled", deletedRegions, totalRegions);
		} else if (stage == ExtendedWorldRegenEngine.Stage.DELETING_REGIONS) {
			int done = deletedRegions + protectedRegions + loadedRegions;
			if (totalRegions > 0) {
				progress = (float) done / totalRegions;
			} else {
				progress = 0f;
			}
			int percent = (int)(progress * 100);
			statusText = new TranslationTextComponent("gui.lotrextended.worldprune.processing", percent, done, totalRegions);

		} else { // CHECKING_CHUNKS
			int done = deletableChunks + bannerProtectedChunks + waypointProtectedChunks + spawnProtectedChunks + errorProtectedChunks;
			if (totalChunks > 0) {
				progress = (float) done / totalChunks;
			} else {
				progress = 0f;
			}
			int percent = (int)(progress * 100);
			statusText = new TranslationTextComponent("gui.lotrextended.worldprune.checking", percent, done, totalChunks);
		}
	}

	public void applyProgressPacket(ExtendedSWorldPruneProgress packet) {
		// CORE
		this.stage = packet.getStage();
		this.running = !packet.isFinished();
		this.dryRun = packet.isDryRun();
		if (stage == Stage.FINISHED || stage == Stage.CANCELED) {
			running = false;
		}
		// CHUNKS
		this.totalChunks = packet.getTotalChunks();
		this.deletableChunks = packet.getDeletableChunks();
		this.bannerProtectedChunks = packet.getBannerProtectedChunks();
		this.waypointProtectedChunks = packet.getWaypointProtectedChunks();
		this.spawnProtectedChunks = packet.getSpawnProtectedChunks();
		this.errorProtectedChunks = packet.getErrorProtectedChunks();

		// REGIONS
		this.currentRegions = packet.getCurrentRegions();
		this.totalRegions = packet.getTotalRegions();
		this.deletedRegions = packet.getDeletedRegions();
		this.protectedRegions = packet.getProtectedRegions();
		this.loadedRegions = packet.getSkippedLoadedRegions();

		// Recalculate derived values
		updateProgress();
		updateButtonStates();
	}
}
