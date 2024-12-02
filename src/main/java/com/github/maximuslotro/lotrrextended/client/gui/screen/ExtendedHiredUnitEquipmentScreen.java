package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedHiredUnitEquipmentContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCPacketOpenHiredMenu;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.common.util.ExtendedHiredUnitHelper;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class ExtendedHiredUnitEquipmentScreen extends ContainerScreen<ExtendedHiredUnitEquipmentContainer> implements IHasContainer<ExtendedHiredUnitEquipmentContainer> {

    private Button backButton;

    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/hired_unit_upgrade_equipment_menu.png");
    DecimalFormat df = new DecimalFormat("#.#");
    int sidebarX;

    private ItemStack[] baseGear = null;

    private LivingEntity entity = null;

    public ExtendedHiredUnitEquipmentScreen(ExtendedHiredUnitEquipmentContainer container, PlayerInventory p_98410_, ITextComponent title) {
        super(container, p_98410_, title);

        this.passEvents = false;
        this.imageHeight = 256;
        this.inventoryLabelY = 130;
        this.imageWidth = 226;
        df.setRoundingMode(RoundingMode.CEILING);
        sidebarX = 90;

        if (p_98410_.player.level.getEntity(container.getEntityId()) != null) {
           if (p_98410_.player.level.getEntity(container.getEntityId()) instanceof LivingEntity) {
               this.entity = (LivingEntity) p_98410_.player.level.getEntity(container.getEntityId());
           }
        }

        if (entity != null) baseGear = ExtendedHiredUnitHelper.getBaseGear(entity);
    }

    @Override
    public void render(MatrixStack p_98418_, int p_98419_, int p_98420_, float p_98421_) {
        this.renderBackground(p_98418_);
        super.render(p_98418_, p_98419_, p_98420_, p_98421_);

        if (baseGear != null) {
            renderBaseGearSlot(leftPos + 25, topPos + 31, baseGear[0]);
            renderBaseGearSlot(leftPos + 25, topPos + 49, baseGear[1]);
            renderBaseGearSlot(leftPos + 25, topPos + 67, baseGear[2]);
            renderBaseGearSlot(leftPos + 25, topPos + 85, baseGear[3]);
            renderBaseGearSlot(leftPos + 61 + 18, topPos + 67, baseGear[4]);
            renderBaseGearSlot(leftPos + 61 + 18, topPos + 85, baseGear[5]);
        }

        this.renderTooltip(p_98418_, p_98419_, p_98420_);
    }

    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        backButton = this.addButton(new Button(this.leftPos + 7, this.topPos + 105, 57, 20,  new TranslationTextComponent("gui.lotrextended.menu.back"), button -> openMainMenu()));
    }

    @SuppressWarnings("deprecation")
	protected void renderBg(MatrixStack p_98413_, float p_98414_, int p_98415_, int p_98416_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND);
        int i = (this.width - this.imageWidth) / 2;
        int j = ((this.height - this.imageHeight) / 2) + 2;
        this.blit(p_98413_, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(MatrixStack matrix, int p_230451_2_, int p_230451_3_) {
        this.font.draw(matrix, this.title, this.titleLabelX, this.titleLabelY, 4210752);
        this.font.draw(matrix, this.inventory.getDisplayName(), this.inventoryLabelX, this.inventoryLabelY, 4210752);

        // show armor points
        if (this.entity != null) {
            double armor = 0.0;
            if (entity.getAttribute(Attributes.ARMOR) != null) {
                armor = Objects.requireNonNull(entity.getAttribute(Attributes.ARMOR)).getValue();
            }
            StringTextComponent armorPoints = new StringTextComponent("Armor: " + armor);
            this.font.draw(matrix, armorPoints, this.sidebarX, this.titleLabelY + 20, 4210752);
        }
    }

    @Override
    protected void renderTooltip(MatrixStack stack, int x, int y) {
        super.renderTooltip(stack, x, y);

        // render tooltips for base gear
        if (isHovering(25, 31, x, y)) {
            this.renderTooltip(stack, baseGear[0], x, y);
        }
        if (isHovering(25, 49, x, y)) {
            this.renderTooltip(stack, baseGear[1], x, y);
        }
        if (isHovering(25, 67, x, y)) {
            this.renderTooltip(stack, baseGear[2], x, y);
        }
        if (isHovering(25, 85, x, y)) {
            this.renderTooltip(stack, baseGear[3], x, y);
        }
        if (isHovering(62 + 18, 67, x, y)) {
            this.renderTooltip(stack, baseGear[4], x, y);
        }
        if (isHovering(62 + 18, 85, x, y)) {
            this.renderTooltip(stack, baseGear[5], x, y);
        }
    }

    private void openMainMenu() {
        ExtendedPacketHandler.sendToServer(new ExtendedCPacketOpenHiredMenu(menu.getEntityId()));
    }

    private void renderBaseGearSlot(int slotX, int slotY, ItemStack item) {
        // Set rendering offsets
        this.setBlitOffset(100);
        this.itemRenderer.blitOffset = 100.0F;

        // Render the item in the slot
        RenderSystem.enableDepthTest();
        this.itemRenderer.renderAndDecorateItem(this.minecraft.player, item, slotX, slotY);
        this.itemRenderer.renderGuiItemDecorations(this.font, item, slotX, slotY, null);

        // Reset rendering offsets
        this.itemRenderer.blitOffset = 0.0F;
        this.setBlitOffset(0);
    }

    private boolean isHovering(int hoverX, int hoverY, double mouseX, double mouseY) {
        // Adjust the mouse coordinates based on the position of the component
        double adjustedMouseX = mouseX - this.leftPos;
        double adjustedMouseY = mouseY - this.topPos;

        // Check if the adjusted mouse coordinates are within the bounds of the component
        boolean isWithinXBounds = adjustedMouseX >= (hoverX - 1) && adjustedMouseX < (hoverX + 16 + 1);
        boolean isWithinYBounds = adjustedMouseY >= (hoverY - 1) && adjustedMouseY < (hoverY + 16 + 1);

        return isWithinXBounds && isWithinYBounds;
    }

}
