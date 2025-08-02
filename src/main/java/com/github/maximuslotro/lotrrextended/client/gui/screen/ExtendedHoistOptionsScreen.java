package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedCUpdateHoistPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedHoistTileEntity;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHoistOptionsScreen extends ExtendedBasicAbstractScreen {
	private int horizontalOffset;
	private int verticalOffset;
	private int verticalHeight;
	private BlockPos configurePos;

	private Button verticalOffsetButton;
	private Button horizontalOffsetButton;
	private Button verticalHeightButton;

	public ExtendedHoistOptionsScreen(ExtendedHoistTileEntity hoistTileEntity) {
		super(new StringTextComponent("HOIST"));
		horizontalOffset = hoistTileEntity.getHorizontalOffset();
		verticalOffset = hoistTileEntity.getVerticalOffset();
		verticalHeight = hoistTileEntity.getVerticalHeight();
		configurePos = hoistTileEntity.getBlockPos();
	}

	@Override
	public void init() {
		super.init();
		this.addButton(new Button(this.leftPos + 40, this.topPos + 20, 20, 20, new TranslationTextComponent("^"), button -> {
			verticalOffset+=1;
			sendUpdatePacket();
		}));
		verticalOffsetButton = this.addButton(new Button(this.leftPos + 40, this.topPos + 40, 20, 20, new StringTextComponent(Integer.toString(verticalOffset)), button -> {
			verticalOffset=0;
			sendUpdatePacket();
		}));
		this.addButton(new Button(this.leftPos + 40, this.topPos + 60, 20, 20, new TranslationTextComponent("v"), button -> {
			verticalOffset-=1;
			sendUpdatePacket();
		}));
		this.addButton(new Button(this.leftPos + 60, this.topPos + 40, 20, 20, new TranslationTextComponent("<"), button -> {
			horizontalOffset-=1;
			sendUpdatePacket();
		}));
		horizontalOffsetButton = this.addButton(new Button(this.leftPos + 80, this.topPos + 40, 20, 20, new StringTextComponent(Integer.toString(horizontalOffset)), button -> {
			horizontalOffset=0;
			sendUpdatePacket();
		}));
		this.addButton(new Button(this.leftPos + 100, this.topPos + 40, 20, 20, new TranslationTextComponent(">"), button -> {
			horizontalOffset+=1;
			sendUpdatePacket();
		}));
		this.addButton(new Button(this.leftPos + 120, this.topPos + 20, 20, 20, new TranslationTextComponent("^"), button -> {
			verticalHeight+=1;
			sendUpdatePacket();
		}));
		verticalHeightButton = this.addButton(new Button(this.leftPos + 120, this.topPos + 40, 20, 20, new StringTextComponent(Integer.toString(verticalHeight)), button -> {
			verticalHeight=0;
			sendUpdatePacket();
		}));
		this.addButton(new Button(this.leftPos + 120, this.topPos + 60, 20, 20, new TranslationTextComponent("v"), button -> {
			verticalHeight-=1;
			sendUpdatePacket();
		}));
	}

	private void sendUpdatePacket() {
		ExtendedPacketHandler.sendToServer(new ExtendedCUpdateHoistPacket(horizontalOffset, verticalOffset, verticalHeight, configurePos));
	}

	@Override
	public void tick() {
		super.tick();
		verticalOffsetButton.setMessage(new StringTextComponent(Integer.toString(verticalOffset)));
		horizontalOffsetButton.setMessage(new StringTextComponent(Integer.toString(horizontalOffset)));
		verticalHeightButton.setMessage(new StringTextComponent(Integer.toString(verticalHeight)));
	}
}
