package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedAnvilButton;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedRepairContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCAnvilButtonPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCRenameItemPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.AnvilScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Based off of {@link AnvilScreen}
 * */
public class ExtendedAnvilScreen extends ContainerScreen<ExtendedRepairContainer> implements IContainerListener  {
	private static final ResourceLocation ANVIL_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/gui/container/anvil.png");
	private static final TranslationTextComponent REFORGE_TEXT = new TranslationTextComponent("container.lotrextended.repair.reforge");
	private static final TranslationTextComponent ENGRAVE_TEXT = new TranslationTextComponent("container.lotrextended.repair.engrave");
	private TextFieldWidget name;
	private Button reforgeButton;
	private Button engraveButton;
	private static int ERRORTEXTCOLOR = 16736352;
	public ExtendedAnvilScreen(ExtendedRepairContainer pAnvilMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(pAnvilMenu, pPlayerInventory, pTitle);
		this.titleLabelX = 60;
		this.imageHeight = 198;
		inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	public void tick() {
		super.tick();
		this.name.tick();
	}

	@Override
	protected void init() {
		super.init();
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.name = new TextFieldWidget(this.font, i + 62, j + 24, 103, 12, new TranslationTextComponent("container.lotrextended.repair"));
		this.reforgeButton = this.addButton(new ExtendedAnvilButton(i+26, j+77, 17, 17, 204, 0, 17, ANVIL_LOCATION, 256, 256, (press)->{
			ExtendedPacketHandler.sendToServer(new ExtendedCAnvilButtonPacket(0));
		}, (button, matrixStack, x, y) -> {
			this.renderTooltip(matrixStack, this.font.split(REFORGE_TEXT, 200), x, y);
		}, StringTextComponent.EMPTY));
		this.engraveButton = this.addButton(new ExtendedAnvilButton(i+45, j+78, 15, 15, 221, 0, 15, ANVIL_LOCATION, 256, 256, (press)->{
			ExtendedPacketHandler.sendToServer(new ExtendedCAnvilButtonPacket(1));
		}, (button, matrixStack, x, y) -> {
			this.renderTooltip(matrixStack, this.font.split(ENGRAVE_TEXT, 200), x, y);
		}, StringTextComponent.EMPTY));
		this.name.setCanLoseFocus(false);
		this.name.setTextColor(-1);
		this.name.setTextColorUneditable(-1);
		this.name.setBordered(false);
		this.name.setMaxLength(35);
		this.name.setResponder(this::onNameChanged);
		this.children.add(this.name);
		this.setInitialFocus(this.name);
		this.menu.addSlotListener(this);
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		this.renderBackground(pMatrixStack);
		if(this.menu.getSlot(0).hasItem() && this.menu.getReforgeCost()>0) {
			this.reforgeButton.active=true;
		}else {
			this.reforgeButton.active=false;
		}
		if(this.menu.getSlot(0).hasItem() && this.menu.getEngraveCost()>0) {
			this.engraveButton.active=true;
		}else {
			this.engraveButton.active=false;
		}
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		RenderSystem.disableBlend();
		this.name.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pX, int pY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(ANVIL_LOCATION);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		// Render GUI
		this.blit(pMatrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
		// Render text box
		this.blit(pMatrixStack, i + 59, j + 20, 0, this.imageHeight + (this.menu.getSlot(0).hasItem() ? 0 : 16), 110, 16);
		// Render output X
		if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem() || this.menu.getSlot(2).hasItem()) && !this.menu.getSlot(3).hasItem()) {
			this.blit(pMatrixStack, i + 99, j + 56, this.imageWidth, 21, 28, 21);
		}else {
			this.blit(pMatrixStack, i + 99, j + 56, this.imageWidth, 0, 28, 21);
		}
		// Render input X
		if (this.menu.getSlot(0).hasItem() && (this.menu.getSlot(1).hasItem() || this.menu.getSlot(2).hasItem()) && this.menu.getSlot(3).hasItem()) {
			this.blit(pMatrixStack, i + 53, j + 60, 110, 211, 13, 13);
		}else {
			this.blit(pMatrixStack, i + 53, j + 60, 110, 198, 13, 13);
		}
		// Render dual-slot X
		if (this.menu.getSlot(0).hasItem() && this.menu.getSlot(1).hasItem() && !this.menu.getSlot(2).hasItem()) {
			this.blit(pMatrixStack, i + 83, j + 65, 123, 198, 3, 3);
		}else if (this.menu.getSlot(0).hasItem() && this.menu.getSlot(1).hasItem() && this.menu.getSlot(2).hasItem() && this.menu.getSlot(3).hasItem()) {
			this.blit(pMatrixStack, i + 83, j + 65, 123, 201, 3, 3);
		}
		//this.blit(pMatrixStack, i + 26, j + 77, 204, 0, 17, 17);
	}

	@Override
	public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
		String s = this.name.getValue();
		this.init(pMinecraft, pWidth, pHeight);
		this.name.setValue(s);
	}

	@Override
	public void removed() {
		super.removed();
		this.menu.removeSlotListener(this);
		this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		if (pKeyCode == 256) {
			this.minecraft.player.closeContainer();
		}

		return !this.name.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.name.canConsumeInput() ? super.keyPressed(pKeyCode, pScanCode, pModifiers) : true;
	}

	private void onNameChanged(String pNewName) {
		if (!pNewName.isEmpty()) {
			String finalName = pNewName;
			Slot slot = this.menu.getSlot(0);
			if (slot != null && slot.hasItem() && !slot.getItem().hasCustomHoverName() && pNewName.equals(slot.getItem().getHoverName().getString())) {
				finalName = "";
			}

			this.menu.setItemName(finalName);
			ExtendedPacketHandler.sendToServer(new ExtendedCRenameItemPacket(finalName));
		}
	}

	@Override
	protected void renderLabels(MatrixStack pMatrixStack, int pX, int pY) {
		RenderSystem.disableBlend();
		super.renderLabels(pMatrixStack, pX, pY);
		int repairCost = this.menu.getCost();
		int textColor = 8453920;
		ITextComponent itextcomponent = null;
		if(this.menu.getSlot(0).hasItem()) {
			if(this.reforgeButton.isHovered() && this.menu.getSlot(0).getItem().isDamageableItem() && this.menu.getReforgeCost()>0){
				if(!this.menu.getSlot(2).hasItem()) {
					itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.missing_material");
					textColor = ERRORTEXTCOLOR;
				}else {
					ItemStack inputItem = this.menu.getSlot(0).getItem();
					ItemStack resourceItem = this.menu.getSlot(2).getItem();
					if(ExtendedRepairContainer.isValidRepairMaterial(inputItem, resourceItem)) {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.reforge.cost", this.menu.getReforgeCost());
						if(resourceItem.getCount()<this.menu.getReforgeCost()) {
							textColor = ERRORTEXTCOLOR;
						}
					}else {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.invalid_material");
						textColor = ERRORTEXTCOLOR;
					}
				}
			}else if(this.engraveButton.isHovered() && this.menu.getSlot(0).getItem().isDamageableItem() && this.menu.getEngraveCost()>0){
				if(!this.menu.getSlot(2).hasItem()) {
					itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.missing_material");
					textColor = ERRORTEXTCOLOR;
				}else {
					ItemStack inputItem = this.menu.getSlot(0).getItem();
					ItemStack resourceItem = this.menu.getSlot(2).getItem();
					if(inputItem.getItem().isValidRepairItem(inputItem, resourceItem)) {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.engrave.cost", this.menu.getEngraveCost());
						if(resourceItem.getCount()<this.menu.getEngraveCost()) {
							textColor = ERRORTEXTCOLOR;
						}
					}else {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.invalid_material");
						textColor = ERRORTEXTCOLOR;
					}
				}
			}else if(repairCost > 0) {
				if (repairCost >= 40 && !this.minecraft.player.abilities.instabuild) {
					itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.expensive");
					textColor = ERRORTEXTCOLOR;
				} else if (!this.menu.getSlot(3).hasItem()) {
					if (this.menu.getIncompatable()) {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.invalid_combination");
						textColor = ERRORTEXTCOLOR;
					}else if (this.menu.getGemIncompatable()) {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.gem_used");
						textColor = ERRORTEXTCOLOR;
					}else if (this.menu.getToManyModifiers()==1) {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.too_many_modifiers");
						textColor = ERRORTEXTCOLOR;
					}else if (this.menu.getToManyModifiers()==2) {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.modifier_maxed");
						textColor = ERRORTEXTCOLOR;
					}
				} else {
					itextcomponent = new TranslationTextComponent("container.lotrextended.repair.cost", repairCost);
					if (!this.menu.getSlot(3).mayPickup(this.inventory.player)) {
						textColor = ERRORTEXTCOLOR;
					}
				}

			}else {
				if (!this.menu.getSlot(3).hasItem()) {
					if (this.menu.getIncompatable()) {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.invalid_combination");
						textColor = ERRORTEXTCOLOR;
					}else if (this.menu.getGemIncompatable()) {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.gem_used");
						textColor = ERRORTEXTCOLOR;
					}else if (this.menu.getToManyModifiers()==1) {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.too_many_modifiers");
						textColor = ERRORTEXTCOLOR;
					}else if (this.menu.getToManyModifiers()==2) {
						itextcomponent = new TranslationTextComponent("container.lotrextended.repair.info.modifier_maxed");
						textColor = ERRORTEXTCOLOR;
					}
				}

			}
		}
		if (itextcomponent != null) {
			int textCordStart = this.imageWidth - 8 - this.font.width(itextcomponent) - 2;
			fill(pMatrixStack, textCordStart - 2, 91, this.imageWidth - 8, 103, 1325400064);
			this.font.drawShadow(pMatrixStack, itextcomponent, textCordStart, 93, textColor);
		}

	}
	@Override
	public void refreshContainer(Container pContainerToSend, NonNullList<ItemStack> pItemsList) {
		this.slotChanged(pContainerToSend, 0, pContainerToSend.getSlot(0).getItem());
	}

	@Override
	public void setContainerData(Container pContainer, int pVarToUpdate, int pNewValue) {
	}

	/**
	 * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
	 * contents of that slot.
	 */
	@Override
	public void slotChanged(Container pContainerToSend, int pSlotInd, ItemStack pStack) {
		if (pSlotInd == 0) {
			this.name.setValue(pStack.isEmpty() ? "" : pStack.getHoverName().getString());
			this.name.setEditable(!pStack.isEmpty());
			this.setFocused(this.name);
		}
	}
}