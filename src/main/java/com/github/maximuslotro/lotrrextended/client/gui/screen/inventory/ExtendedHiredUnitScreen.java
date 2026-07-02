package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedColorChangingButton;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredEquipmentMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredFarmhandMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredOverviewMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;

import lotr.common.entity.npc.ExtendedIFarmhand;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ExtendedHiredUnitScreen<T extends ExtendedHiredUnitContainer> extends ContainerScreen<T>{
	protected Button statsbuton;
	protected Button equipmentbuton;
	protected Button farmhandbuton;
	private static int index = 0;

	public ExtendedHiredUnitScreen(T pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
		this.passEvents = false;
		this.imageWidth = 176;
		this.imageHeight = 222;
		this.titleLabelY = 4;
		this.inventoryLabelY = 128;
		this.titleLabelX = (imageWidth/2);
	}

	protected void openOverviewMenu(int companionID) {
		if(index!=0) {
			ExtendedPacketHandler.sendToServer(new ExtendedCOpenHiredOverviewMenuPacket(companionID));
		}
	}

	protected void openEquipmentMenu(int companionID) {
		if(index!=1) {
			ExtendedPacketHandler.sendToServer(new ExtendedCOpenHiredEquipmentMenuPacket(companionID));
		}
	}

	protected void openFarmhandMenu(int companionID) {
		if(index!=2) {
			ExtendedPacketHandler.sendToServer(new ExtendedCOpenHiredFarmhandMenuPacket(companionID));
		}
	}

	@Override
	protected void init() {
		super.init();
		statsbuton = addButton(new ExtendedColorChangingButton(this.leftPos -57, this.topPos+6, 57, 20,  new TranslationTextComponent("gui.lotrextended.menu.overview"), button -> openOverviewMenu(getMenu().getEntityId()), 0.13f, 0.92f, 0.71f){
			@Override
			public boolean setColor(){
				return ExtendedHiredUnitScreen.index==0;
			}
		});
		equipmentbuton = addButton(new ExtendedColorChangingButton(this.leftPos -57, this.topPos+26, 57, 20,  new TranslationTextComponent("gui.lotrextended.menu.equipment"), button -> openEquipmentMenu(getMenu().getEntityId()), 0.13f, 0.92f, 0.71f){
			@Override
			public boolean setColor(){
				return ExtendedHiredUnitScreen.index==1;
			}
		});
		farmhandbuton = addButton(new ExtendedColorChangingButton(this.leftPos -57, this.topPos+46, 57, 20,  new TranslationTextComponent("gui.lotrextended.menu.farmhand"), button -> openFarmhandMenu(getMenu().getEntityId()), 0.13f, 0.92f, 0.71f){
			@Override
			public boolean setColor(){
				return ExtendedHiredUnitScreen.index==2;
			}
		});
		if(!(getMenu().getCompanion() instanceof ExtendedIFarmhand)) {
			farmhandbuton.active=false;
		}
	}

	protected static void setIndex(int index) {
		ExtendedHiredUnitScreen.index = index;
	}
}
