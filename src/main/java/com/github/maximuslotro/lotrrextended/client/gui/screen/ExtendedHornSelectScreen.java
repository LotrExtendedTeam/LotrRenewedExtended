package com.github.maximuslotro.lotrrextended.client.gui.screen;

import org.lwjgl.opengl.GL11;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCPacketSelectHorn;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.init.ExtendedItems;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHornSelectScreen extends ExtendedBasicAbstractScreen {
	private static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/horn_select.png");

	public ExtendedHornSelectScreen() {
		super(new StringTextComponent("HORNSELECT"), 176, 256);
	}

	@Override
	public void init() {
		this.addButton(new Button(this.leftPos + 40, this.topPos + 40, 120, 20, new TranslationTextComponent("gui.lotrextended.horn_select.option_halt_reddy"), button -> setHorn(1.0F, button)));
		this.addButton(new Button(this.leftPos + 40, this.topPos + 75, 120, 20, new TranslationTextComponent("gui.lotrextended.horn_select.option_summon"), button -> setHorn(3.0F, button)));
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		minecraft.textureManager.bind(guiTexture);
		blit(matStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		TextComponent title = new TranslationTextComponent("gui.lotrextended.horn_select.title");
		this.font.draw(matStack, title, leftPos + (85 - (font.width(title) / 2)), topPos + 11, 4210752);
		super.render(matStack, mouseX, mouseY, tick);
		for (int k = 0; k < this.buttons.size(); k++) {
			Widget button = this.buttons.get(k);
			itemRenderer.renderGuiItem(new ItemStack(ExtendedItems.COMMANDHORN.get(), 1), button.x - 22, button.y + 2);
		}
	}

	public void setHorn(float type, Button button) {
		ExtendedPacketHandler.sendToServer(new ExtendedCPacketSelectHorn(type));
		//button.playDownSound(Minecraft.getInstance().getSoundManager());
		this.minecraft.player.closeContainer();
	}

	@Override
	public void tick() {
		super.tick();
		ItemStack itemstack = this.minecraft.player.getMainHandItem();
		if (itemstack == null || itemstack.getItem() != ExtendedItems.COMMANDHORN.get()) {
			this.minecraft.player.closeContainer();
			this.onClose();
		}
		if(ExtendedCommandHornItem.isNotEmpty(itemstack)){
			this.minecraft.player.closeContainer();
			this.onClose();
			return;
		}
	}
}
