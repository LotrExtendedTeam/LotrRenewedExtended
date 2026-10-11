package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedEmptyTextFieldWidget;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSelectHornSquadPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHornSquadScreen extends ExtendedAbstractWindowedScreen {
	private static final ResourceLocation TEXTURE = new ResourceLocation("lotrextended:textures/gui/horn_squad.png");
	private ExtendedEmptyTextFieldWidget squadTextField;
	private String newSquadName;
	private final String originalSquadName;
	private Button clearButton;
	private Button setButton;

	public ExtendedHornSquadScreen(String pOriginalText) {
		super(new TranslationTextComponent("gui.lotrextended.horn_select.squad.title"), 176, 96);
		originalSquadName = pOriginalText!=null ? pOriginalText : "";
		newSquadName = originalSquadName;
	}

	@Override
	public void init() {
		super.init();
		this.addButton(squadTextField = new ExtendedEmptyTextFieldWidget(this.font, this.leftPos + 30, this.topPos + 37, 116, 20, 
			new TranslationTextComponent("gui.lotrextended.horn_select.squad.textfield"), new TranslationTextComponent("gui.lotrextended.base.unset")));
		this.squadTextField.setMaxLength(20);
		this.squadTextField.setValue(newSquadName);
		this.squadTextField.setResponder((newValue) -> {
			this.newSquadName = newValue;
		});
		this.addButton(clearButton = new Button(this.leftPos + 149, this.topPos + 37, 20, 20, new StringTextComponent("X").withStyle(TextFormatting.RED), button -> {
			this.squadTextField.setValue("");
		}));
		this.addButton(setButton = new Button(this.leftPos + 30, this.topPos + 67, 116, 20, new TranslationTextComponent("gui.lotrextended.base.set"), button -> {
			ExtendedPacketHandler.sendToServer(new ExtendedCSelectHornSquadPacket(newSquadName));
			this.onClose();
		}));
		clearButton.active = !newSquadName.isEmpty();
		setButton.active=false;
	}

	@Override
	public void tick() {
		super.tick();
		ItemStack itemstack = this.minecraft.player.getMainHandItem();
		if (itemstack == null || itemstack.getItem() != ExtendedItems.COMMANDHORN.get()) {
			this.onClose();
			return;
		}
		squadTextField.tick();
		clearButton.active = !newSquadName.isEmpty();
		setButton.active = !originalSquadName.equals(newSquadName);
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		minecraft.getTextureManager().bind(TEXTURE);
		blit(matStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		this.font.draw(matStack, title, this.leftPos + ((this.imageWidth - font.width(title)) / 2), this.topPos + 10, 4210752);
		super.render(matStack, mouseX, mouseY, tick);

		ItemStack itemstack = ExtendedCommandHornItem.setSquad(this.minecraft.player.getMainHandItem().copy(), newSquadName);
		itemRenderer.renderGuiItem(itemstack, this.leftPos + 9, this.topPos + 54);
		if(isHovering(9, 54, 16, 16, mouseX, mouseY)) {
			this.renderTooltip(matStack, itemstack, mouseX, mouseY);
		}
	}
}
