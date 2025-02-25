package lotr.client.gui;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedBasicAbstractScreen;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;
import com.github.maximuslotro.lotrrextended.common.network.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.common.entity.npc.ExtendedBreelandSheriffEntity;
import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.ExtendedGundabadOrcChieftain;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedCaptainMainScreen extends ExtendedBasicAbstractScreen {

    private NPCEntity entity;
    private final ExtendedCaptainEntity captainEntity;
    private final PlayerEntity player;

    private Button hireButton;

    public ExtendedCaptainMainScreen(NPCEntity entity, ExtendedCaptainEntity captainEntity, PlayerEntity player) {
        super(new StringTextComponent("Captain Menu"), 176, 256);

        this.entity = entity;
        this.captainEntity = captainEntity;
        this.player = player;
    }

    @Override
    public void init() {
    	super.init();
        this.addButton(new Button(this.leftPos + (this.imageWidth - 125) / 2, this.topPos + 115, 125, 20, new TranslationTextComponent("gui.lotrextended.trading.talk_button"), button -> talkWithNPC()));
        // 114 was 57
        // +5 was +63/then 32
        if (captainEntity instanceof ExtendedBreelandSheriffEntity) {
            hireButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 125) / 2, this.topPos + 92, 125, 20, new TranslationTextComponent("gui.lotrextended.hiring.hire_bree_guard"), button -> hireFromNPC()));
        } else if (captainEntity instanceof ExtendedGundabadOrcChieftain) {
            hireButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 125) / 2, this.topPos + 92, 125, 20, new TranslationTextComponent("gui.lotrextended.hiring.hire_gundabad_orc"), button -> hireFromNPC()));
        }
    }

    private void talkWithNPC() {
    	stopInteractingClientServer();

        ExtendedPacketHandler.sendToServer(new ExtendedCTalkWithTraderPacket(entity.getId()));
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null) {
            mc.player.closeContainer();
        }
    }

	private void hireFromNPC() {
		// Ensure the client does not disturb the server when a unit shouldn't actually be able to be hired by a player
		// TODO remove hardcoded in next update
        if (captainEntity instanceof ExtendedBreelandSheriffEntity) {
		    if (captainEntity.canHire(ExtendedHiredUnitTypes.BREE_GUARD, player)) {
                ExtendedPacketHandler.sendToServer(new ExtendedCHireUnitPacket(entity.getId()));
		    } else {
			    player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.captain_alignment_too_low"), Util.NIL_UUID);
			    stopInteractingClientServer();
			    Minecraft mc = Minecraft.getInstance();
			    if(mc.player != null) {
				    mc.player.closeContainer();
			    }
		    }
        } else if (captainEntity instanceof ExtendedGundabadOrcChieftain) {
            if (captainEntity.canHire(ExtendedHiredUnitTypes.GUNDABAD_ORC, player)) {
                ExtendedPacketHandler.sendToServer(new ExtendedCHireUnitPacket(entity.getId()));
            } else {
                player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.captain_alignment_too_low"), Util.NIL_UUID);
                stopInteractingClientServer();
                Minecraft mc = Minecraft.getInstance();
                if(mc.player != null) {
                    mc.player.closeContainer();
                }
            }
        }
	}

    @Override
    public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
        renderBackground(matStack);

        TextComponent title = new StringTextComponent(entity.getName().getString());
        this.font.draw(matStack, title, this.leftPos + ((this.imageWidth - font.width(title)) / 2), this.topPos + 67, 16777215);

        super.render(matStack, mouseX, mouseY, tick);
    }

    @Override
    public void onClose() {
        super.onClose();
        stopInteractingClientServer();
    }

    public void stopInteractingClientServer() {
    	// notify the server that player is no longer interacting with this trader
        ExtendedPacketHandler.sendToServer(new ExtendedCStopInteractingWithInteractablePacket(entity.getId()));
        captainEntity.removePlayerFromInteracting(player); // remove the player from trading on the client side as well
    }
}
