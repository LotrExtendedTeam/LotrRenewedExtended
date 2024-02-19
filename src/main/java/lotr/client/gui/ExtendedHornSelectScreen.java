package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import com.github.maximuslotro.lotrExtended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrExtended.common.network.ExtendedSPacketSelectHorn;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.init.ExtendedItems;
import lotr.common.item.ExtendedCommandHornItem;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHornSelectScreen extends BasicIngameScreen{
	public ExtendedHornSelectScreen() {
		super(new StringTextComponent("HORNSELECT"));
	}

	private static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/horn_select.png");
	  
	  private int xSize = 176;
	  
	  private int ySize = 256;
	  
	  private int guiLeft;
	  
	  private int guiTop;
	  
	  @Override
	  public void init() {
	    this.guiLeft = (this.width - this.xSize) / 2;
	    this.guiTop = (this.height - this.ySize) / 2;
	    this.addButton(new Button(this.guiLeft + 40, this.guiTop + 40, 120, 20, new TranslationTextComponent("gui.lotrextended.horn_select.option_halt_reddy"), button -> setHorn(1.0F)));
	    this.addButton(new Button(this.guiLeft + 40, this.guiTop + 75, 120, 20, new TranslationTextComponent("gui.lotrextended.horn_select.option_summon"), button -> setHorn(3.0F)));
	  }
	  
	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		minecraft.textureManager.bind(guiTexture);
		blit(matStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		TextComponent title = new TranslationTextComponent("gui.lotrextended.horn_select.title");
		this.font.draw(matStack, title, guiLeft + (85 - (font.width(title) / 2)), guiTop + 11, 4210752);
		super.render(matStack, mouseX, mouseY, tick);
		for (int k = 0; k < this.buttons.size(); k++) {
			Widget button = this.buttons.get(k);
			itemRenderer.renderGuiItem(new ItemStack(ExtendedItems.COMMANDHORN.get(), 1), button.x - 22, button.y + 2);
		}
	}

	public void setHorn(float type) {
		ExtendedPacketHandler.sendToServer(new ExtendedSPacketSelectHorn(type));
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
