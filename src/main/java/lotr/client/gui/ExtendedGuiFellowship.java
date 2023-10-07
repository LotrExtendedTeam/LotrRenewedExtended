package lotr.client.gui;

import java.util.UUID;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.widget.ScrollPane;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class ExtendedGuiFellowship extends MiddleEarthMenuScreen {
	public enum ACTIONTYPE {
		LIST, CREATE, FELLOWSHIP, INVITE, DISBAND, LEAVE, REMOVE, OP, DEOP, TRANSFER, RENAME, INVITATIONS, ACCEPT_INVITE_RESULT;
	}

	public static final ResourceLocation iconsTextures = new ResourceLocation(
			"lotr:gui/fellowships.png");

	private ACTIONTYPE action = ACTIONTYPE.LIST;

	private UUID removingPlayer;

	private UUID oppingPlayer;

	private UUID deoppingPlayer;

	private UUID transferringPlayer;

	private Button buttonCreate;

	private Button buttonCreateThis;

	//private LOTRGuiButtonFsOption buttonInvitePlayer;

	private Button buttonInviteThis;

	//private LOTRGuiButtonFsOption buttonDisband;

	private Button buttonDisbandThis;

	private Button buttonLeave;

	private Button buttonLeaveThis;

	//private LOTRGuiButtonFsOption buttonSetIcon;

	private Button buttonRemove;

	private Button buttonTransfer;

	//private LOTRGuiButtonFsOption buttonRename;

	private Button buttonRenameThis;

	private Button buttonBack;

	private Button buttonInvites;

	//private LOTRGuiButtonFsOption buttonPVP;

	//private LOTRGuiButtonFsOption buttonHiredFF;

	//private LOTRGuiButtonFsOption buttonMapShow;

	private Button buttonOp;

	private Button buttonDeop;

	private TextFieldWidget textFieldName;

	private TextFieldWidget textFieldPlayer;

	private TextFieldWidget textFieldRename;

	private static final int MAX_NAME_LENGTH = 40;

	public static final int entrySplit = 5;

	public static final int entryBorder = 10;

	public static final int selectBorder = 2;

	private int scrollWidgetWidth = 9;

	private int scrollWidgetHeight = 8;

	private int scrollBarX = this.xSize + 2 + 1;

	private ScrollPane scrollPaneLeading = new ScrollPane(
			this.scrollWidgetWidth, this.scrollWidgetHeight);

	private ScrollPane scrollPaneOther = new ScrollPane(
			this.scrollWidgetWidth, this.scrollWidgetHeight);

	private ScrollPane scrollPaneMembers = new ScrollPane(
			this.scrollWidgetWidth, this.scrollWidgetHeight);

	private ScrollPane scrollPaneInvites = new ScrollPane(
			this.scrollWidgetWidth, this.scrollWidgetHeight);

	private static final int maxDisplayedFellowships = 12;

	private int displayedFellowshipsLeading;

	private int displayedFellowshipsOther;

	private static final int maxDisplayedMembers = 11;

	private int displayedMembers;

	private static final int maxDisplayedInvites = 15;

	private int displayedInvites;

	public ExtendedGuiFellowship() {
		super(new StringTextComponent("FELLOWSHIPS"));
	}


	@Override
	public void init() {
		super.init();
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		TextComponent title = new StringTextComponent("Middle-earth Fellowships");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop - 10), 16777215);
		super.render(matStack, mouseX, mouseY, tick);
	}
}
