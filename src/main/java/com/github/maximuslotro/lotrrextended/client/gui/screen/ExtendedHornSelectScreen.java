package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitHornTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSelectHornPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHornSelectScreen extends ExtendedBasicAbstractScreen {
	private static final ResourceLocation TEXTURE = new ResourceLocation("lotrextended:textures/gui/horn_select.png");

	public ExtendedHornSelectScreen() {
		super(new TranslationTextComponent("gui.lotrextended.horn_select.title"), 176, 113);
	}

	@Override
	public void init() {
		super.init();
		this.addButton(new Button(this.leftPos + 40, this.topPos + 40, 120, 20, new TranslationTextComponent("gui.lotrextended.horn_select.option_halt_reddy"), button -> {
			ExtendedPacketHandler.sendToServer(new ExtendedCSelectHornPacket(ExtendedUnitHornTypes.HALT));
			this.minecraft.setScreen(null);
		}));
		this.addButton(new Button(this.leftPos + 40, this.topPos + 75, 120, 20, new TranslationTextComponent("gui.lotrextended.horn_select.option_summon"), button -> {
			ExtendedPacketHandler.sendToServer(new ExtendedCSelectHornPacket(ExtendedUnitHornTypes.SUMMON));
			this.minecraft.setScreen(null);
		}));
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		minecraft.getTextureManager().bind(TEXTURE);
		blit(matStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		this.font.draw(matStack, title, leftPos + ((this.imageWidth - font.width(title)) / 2), topPos + 11, 4210752);
		super.render(matStack, mouseX, mouseY, tick);
		ItemStack halt = ExtendedCommandHornItem.setType(this.minecraft.player.getMainHandItem().copy(), ExtendedUnitHornTypes.HALT);
		ItemStack summon = ExtendedCommandHornItem.setType(this.minecraft.player.getMainHandItem().copy(), ExtendedUnitHornTypes.SUMMON);
		itemRenderer.renderGuiItem(halt, this.leftPos + 18, this.topPos + 42);
		itemRenderer.renderGuiItem(summon, this.leftPos + 18, this.topPos + 77);
		if(isHovering(18, 42, 16, 16, mouseX, mouseY)) {
			this.renderTooltip(matStack, halt, mouseX, mouseY);
		}
		if(isHovering(18, 77, 16, 16, mouseX, mouseY)) {
			this.renderTooltip(matStack, summon, mouseX, mouseY);
		}
	}

	@Override
	public void tick() {
		super.tick();
		ItemStack itemstack = this.minecraft.player.getMainHandItem();
		if (itemstack == null || itemstack.getItem() != ExtendedItems.COMMANDHORN.get() || !ExtendedCommandHornItem.getType(itemstack).isUnset()) {
			this.minecraft.setScreen(null);
			return;
		}
	}
}
