package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedHiredUnitContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredEquipmentMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSetHiredUnitStationaryPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.ExtendedHiredBreeGuard;
import lotr.common.entity.npc.ExtendedHiredGundabadOrc;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHiredUnitScreen extends ContainerScreen<ExtendedHiredUnitContainer> implements IHasContainer<ExtendedHiredUnitContainer> {

    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/hired_unit_overview_menu.png");
    private static final ResourceLocation PATROL_BUTTON = new ResourceLocation(LotrExtendedMod.MODID, "textures/gui" +
            "/patrol_button.png");
    private static final ResourceLocation STATIONARY_BUTTON = new ResourceLocation(LotrExtendedMod.MODID, "textures/gui" +
            "/stationary_button.png");

    private final ExtendedHirableEntity companion;
    DecimalFormat df = new DecimalFormat("#.#");
    int sidebarX;
    int row1;
    int col1;

    // buttons
    private CompanionButton patrolButton;
    private CompanionButton stationaryButton;

    private Button equipmentButton;

    public ExtendedHiredUnitScreen(ExtendedHiredUnitContainer container, PlayerInventory p_98410_, ITextComponent title) {
        super(container, p_98410_, title);

        if (p_98410_.player.level.getEntity(container.getEntityId()) != null) {
            if (p_98410_.player.level.getEntity(container.getEntityId()) instanceof ExtendedHirableEntity) {
                this.companion = (ExtendedHirableEntity) p_98410_.player.level.getEntity(container.getEntityId());
            } else {
                companion = null;
            }
        } else {
            companion = null;
        }

        this.passEvents = false;
        this.imageHeight = 256;
        this.inventoryLabelY = 130;
        this.imageWidth = 226;
        df.setRoundingMode(RoundingMode.CEILING);
        sidebarX = 80;
    }

    @Override
    public void render(MatrixStack p_98418_, int p_98419_, int p_98420_, float p_98421_) {
        this.renderBackground(p_98418_);
        super.render(p_98418_, p_98419_, p_98420_, p_98421_);

        this.renderTooltip(p_98418_, p_98419_, p_98420_);
    }

    @Override
	protected void init() {
        super.init();

        row1 = topPos + 42;
        col1 = leftPos + sidebarX + 62;

        // TODO: At some point add a guard mode
        this.patrolButton = addButton(new CompanionButton("patrolling", col1, row1,16, 12,
                0, 0
                ,13,
                PATROL_BUTTON,
                btn -> {})
        );
        this.stationaryButton = addButton(new CompanionButton("stationary", col1, row1 + 14,
                16, 12, 0, 0, 13,
                STATIONARY_BUTTON,
                btn -> {
                    ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitStationaryPacket(companion.getHiredUnitId()));
                })
        );

        equipmentButton = this.addButton(new Button(this.leftPos + 8, this.topPos + 18, 57, 20,  new TranslationTextComponent("gui.lotrextended.menu.equipment"), button -> openEquipmentMenu()));
    }

    private void openEquipmentMenu() {
        ExtendedPacketHandler.sendToServer(new ExtendedCOpenHiredEquipmentMenuPacket(companion.getHiredUnitId()));
    }

	@Override
	protected void renderBg(MatrixStack p_98413_, float p_98414_, int p_98415_, int p_98416_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND);
        int i = (this.width - this.imageWidth) / 2;
        int j = ((this.height - this.imageHeight) / 2) + 2;
        this.blit(p_98413_, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
	protected void renderLabels(MatrixStack matrix, int p_230451_2_, int p_230451_3_) {
        this.font.draw(matrix, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(matrix, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);

        this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.companion").withStyle(TextFormatting.UNDERLINE), sidebarX, this.titleLabelY + 12, 4210752);

        if (companion instanceof ExtendedHiredBreeGuard) {
            this.font.draw(matrix, new TranslationTextComponent("entity_unit_type.lotrextended.hired_bree_guard"), sidebarX, this.titleLabelY + 22, 4210752);
        }
        if (companion instanceof ExtendedDunedainRangerEntity) {
            this.font.draw(matrix, new TranslationTextComponent("entity_unit_type.lotrextended.hired_dunedain_ranger"), sidebarX, this.titleLabelY + 22, 4210752);
        }
        if (companion instanceof ExtendedHiredGundabadOrc) {
            this.font.draw(matrix, new TranslationTextComponent("entity_unit_type.lotrextended.hired_gundabad_orc"), sidebarX, this.titleLabelY + 22, 4210752);
        }

        this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.health").withStyle(TextFormatting.UNDERLINE), sidebarX, this.titleLabelY + 32, 4210752);
        this.font.draw(matrix, new StringTextComponent(df.format(companion.getHiredUnitHealth()) + "/" + (int) companion.getHiredUnitMaxHealth()), sidebarX, this.titleLabelY + 42, 4210752);
        this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.level", companion.getExpLvl()), sidebarX, this.titleLabelY + 56, 4210752);
        this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.xp", companion.getCurrentXp() + "/" + companion.getMaxXp()), sidebarX, this.titleLabelY + 64, 4210752);
        this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.mob_kills", companion.getMobKills()), sidebarX, this.titleLabelY + 73, 4210752);
    }

    @Override
    protected void renderTooltip(MatrixStack stack, int x, int y) {
        super.renderTooltip(stack, x, y);

        if (this.patrolButton.isHovered()) {
            List<ITextComponent> tooltips = new ArrayList<>();
            if (this.companion.isFollowing()) {
                tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.follows_on_title"));
                tooltips.add(
                        new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.follows_on_description")
                                .withStyle(TextFormatting.GRAY)
                                .withStyle(TextFormatting.ITALIC)
                );
            } else if (this.companion.isPatrolling()){
                // TODO: Note for now these features disabled
                /*
                tooltips.add(new StringTextComponent("Patrol"));
                tooltips.add(new StringTextComponent("Patrols a 4 block radius").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
                */
            } else {
                // TODO: Note for now these features disabled
                /*
                tooltips.add(new StringTextComponent("Guard"));
                tooltips.add(new StringTextComponent("Stands at its position ready for action").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
                 */
            }

            this.renderComponentTooltip(stack, tooltips, x, y);
        }

        if (this.stationaryButton.isHovered()) {
            List<ITextComponent> tooltips = new ArrayList<>();
            if (this.companion.isStationary()) {
                tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_on_title"));
                tooltips.add(
                        new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_on_description")
                                .withStyle(TextFormatting.GRAY)
                                .withStyle(TextFormatting.ITALIC)
                );
            } else {
                tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_off_title"));
                tooltips.add(
                        new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_off_description")
                                .withStyle(TextFormatting.GRAY)
                                .withStyle(TextFormatting.ITALIC)
                );
            }

            this.renderComponentTooltip(stack, tooltips, x, y);
        }
    }


    class CompanionButton extends ImageButton {

        private String name;

        public CompanionButton(String name, int p_94269_, int p_94270_, int p_94271_, int p_94272_, int p_94273_,
                               int p_94274_,
                               int p_94275_, ResourceLocation p_94276_,
                               IPressable p_94277_) {
            super(p_94269_, p_94270_, p_94271_, p_94272_, p_94273_, p_94274_, p_94275_, p_94276_, p_94277_);
            this.name = name;
        }

        @Override
        public void renderButton(MatrixStack p_94282_, int p_94283_, int p_94284_, float p_94285_) {
            // TODO: Ideally this would be nice to show the enabled vs disabled
            //  stationary icon but currently doesn't work because xTexStart is private
            //  - should fix this eventually
            /*if (this.name.equals("alert")) {
                if (ExtendedHiredUnitScreen.this.companion.isAlert()) {
                    this.xTexStart = 0;
                } else {
                    this.xTexStart = 17;
                }
            } else if (this.name.equals("patrolling")) {
                if (ExtendedHiredUnitScreen.this.companion.isFollowing()) {
                    this.xTexStart = 0;
                } else if (ExtendedHiredUnitScreen.this.companion.isPatrolling()){
                    this.xTexStart = 17;
                } else {
                    this.xTexStart = 34;
                }
            } else
            if (this.name.equals("stationary")) {
                assert ExtendedHiredUnitScreen.this.companion != null;
                if (ExtendedHiredUnitScreen.this.companion.isStationary()) {
                    this.xTexStart = 0;
                } else {
                    this.xTexStart = 17;
                }
            }*/
            RenderSystem.enableBlend();
            super.renderButton(p_94282_, p_94283_, p_94284_, p_94285_);
            RenderSystem.disableBlend();
        }
    }

    private void renderBaseGearSlot(int slotX, int slotY, ItemStack item) {
        String itemStackCountText = null;

        // Set rendering offsets
        this.setBlitOffset(100);
        this.itemRenderer.blitOffset = 100.0F;

        // Render the item in the slot
        RenderSystem.enableDepthTest();
        this.itemRenderer.renderAndDecorateItem(this.minecraft.player, item, slotX, slotY);
        this.itemRenderer.renderGuiItemDecorations(this.font, item, slotX, slotY, itemStackCountText);

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
