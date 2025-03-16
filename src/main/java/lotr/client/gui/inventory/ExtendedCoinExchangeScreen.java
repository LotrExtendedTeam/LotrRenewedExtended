package lotr.client.gui.inventory;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedCExchangeCoinsPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCStopInteractingWithInteractablePacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.inventory.ExtendedCoinExchangeContainer;
import lotr.common.util.CoinUtils;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * @author RichardB122
 */
public class ExtendedCoinExchangeScreen extends ContainerScreen<ExtendedCoinExchangeContainer> {

	public static final ResourceLocation GUI_TEXTURE = new ResourceLocation("lotr:gui/coin_exchange.png");

	ExtendedCoinExchangeContainer container;

	PlayerEntity player;

	public ExtendedCoinExchangeScreen(ExtendedCoinExchangeContainer container, PlayerInventory p_i51105_2_, ITextComponent title) {
		super(container, p_i51105_2_, title);

		this.container = container;
		this.player = p_i51105_2_.player;
		this.imageHeight = 188;
		this.imageWidth = 176;
	}

	@Override
	public void render(MatrixStack p_98418_, int p_98419_, int p_98420_, float p_98421_) {
		this.renderBackground(p_98418_);
		super.render(p_98418_, p_98419_, p_98420_, p_98421_);
		this.renderTooltip(p_98418_, p_98419_, p_98420_);
	}

	@Override
	protected void renderBg(MatrixStack matrix, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(GUI_TEXTURE);
		int i = (this.width - this.imageWidth) / 2;
		int j = ((this.height - this.imageHeight) / 2);
		this.blit(matrix, i, j, 0, 0, this.imageWidth, this.imageHeight);

		if (!this.menu.leftOutputSlot.getItem().isEmpty()) {
			this.blit(matrix, i + 21, j + 41, 176, 51, 26, 26);
		}

		if (!this.menu.rightOutputSlot.getItem().isEmpty()) {
			this.blit(matrix, i + 129, j + 41, 176, 51, 26, 26);
		}
	}

	@Override
	protected void renderLabels(MatrixStack matrix, int p_230451_2_, int p_230451_3_) {
		// render coin amount in inventory
		fill(matrix, 180, 105, 180 + 19 + this.font.width(String.valueOf(CoinUtils.totalValueInPlayerInventory(player.inventory))), 120, 0x99000000);
		this.minecraft.getTextureManager().bind(GUI_TEXTURE);
		blit(matrix, 179, 104, 176, 77, 18, 18);
		this.font.draw(matrix, String.valueOf(CoinUtils.totalValueInPlayerInventory(player.inventory)), 196, 109, 0xFFFFFFFF);

		this.font.draw(matrix, this.title, this.titleLabelX, this.titleLabelY, 4210752);
		this.font.draw(matrix, this.inventory.getDisplayName(), this.inventoryLabelX, this.inventoryLabelY, 4210752);
	}

	@Override
	public void init() {
		super.init();
		this.imageHeight = 188;
		this.imageWidth = 176;

		this.titleLabelY = 7;
		this.inventoryLabelY = this.imageHeight - 95;

		int i = (this.width - this.imageWidth) / 2;
		int j = ((this.height - this.imageHeight) / 2);

		addButton(new ImageButton(i + 44,  j + 45, 32, 16, 176, 0, 16, GUI_TEXTURE, btn -> {attemptToExchange(0);}));
		addButton(new ImageButton( i + 102,  j + 45, 32, 16, 208, 0, 16, GUI_TEXTURE, btn -> {attemptToExchange(1);}));
	}

	private void attemptToExchange(int mode) {
		ExtendedPacketHandler.sendToServer(new ExtendedCExchangeCoinsPacket(mode));
	}

	@Override
	public void onClose() {
		super.onClose();
		// notify the server that player is no longer interacting with this trader
		ExtendedPacketHandler.sendToServer(new ExtendedCStopInteractingWithInteractablePacket(((NPCEntity)container.trader).getId()));
		container.trader.removePlayerFromInteracting(player); // remove the player from trading on the client side as well
	}
}