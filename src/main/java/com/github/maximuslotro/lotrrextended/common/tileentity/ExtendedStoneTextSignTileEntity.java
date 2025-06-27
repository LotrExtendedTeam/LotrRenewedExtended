package com.github.maximuslotro.lotrrextended.common.tileentity;

import java.util.function.Function;

import javax.annotation.Nullable;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedStoneTextSignTileEntity extends TileEntity {
	private final ITextComponent[] messages = new ITextComponent[]{StringTextComponent.EMPTY, StringTextComponent.EMPTY, StringTextComponent.EMPTY, StringTextComponent.EMPTY, StringTextComponent.EMPTY, StringTextComponent.EMPTY, StringTextComponent.EMPTY, StringTextComponent.EMPTY};
	private boolean isEditable = true;
	private PlayerEntity playerWhoMayEdit;
	private final IReorderingProcessor[] renderMessages = new IReorderingProcessor[8];
	private DyeColor color = DyeColor.BLACK;

	public ExtendedStoneTextSignTileEntity() {
		super(ExtendedTileEntities.STONE_SIGN.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);

		for(int i = 0; i < 8; ++i) {
			String s = ITextComponent.Serializer.toJson(this.messages[i]);
			pCompound.putString("Text" + (i + 1), s);
		}

		pCompound.putString("Color", this.color.getName());
		return pCompound;
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
		this.isEditable = false;
		super.load(p_230337_1_, p_230337_2_);
		this.color = DyeColor.byName(p_230337_2_.getString("Color"), DyeColor.BLACK);

		for(int i = 0; i < 8; ++i) {
			String s = p_230337_2_.getString("Text" + (i + 1));
			ITextComponent itextcomponent = ITextComponent.Serializer.fromJson(s.isEmpty() ? "\"\"" : s);
			if (this.level instanceof ServerWorld) {
				/*
				try {
					this.messages[i] = TextComponentUtils.updateForEntity(this.createCommandSourceStack((ServerPlayerEntity)null), itextcomponent, (Entity)null, 0);
				} catch (CommandSyntaxException commandsyntaxexception) {
					this.messages[i] = itextcomponent;
				}*/
				// Extended Start
				this.messages[i] = itextcomponent;
				// Extended End
			} else {
				this.messages[i] = itextcomponent;
			}

			this.renderMessages[i] = null;
		}

	}

	@OnlyIn(Dist.CLIENT)
	public ITextComponent getMessage(int p_212366_1_) {
		return this.messages[p_212366_1_];
	}

	public void setMessage(int pLine, ITextComponent pSignText) {
		this.messages[pLine] = pSignText;
		this.renderMessages[pLine] = null;
	}

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public IReorderingProcessor getRenderMessage(int p_242686_1_, Function<ITextComponent, IReorderingProcessor> p_242686_2_) {
		if (this.renderMessages[p_242686_1_] == null && this.messages[p_242686_1_] != null) {
			this.renderMessages[p_242686_1_] = p_242686_2_.apply(this.messages[p_242686_1_]);
		}

		return this.renderMessages[p_242686_1_];
	}

	/**
	 * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
	 * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
	 */
	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, -1, this.getUpdateTag());
	}


	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
		this.load(level.getBlockState(pkt.getPos()), pkt.getTag());
	}
	
	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
	 * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
	 */
	@Override
	public CompoundNBT getUpdateTag() {
		return this.save(new CompoundNBT());
	}

	/**
	 * Checks if players can use this tile entity to access operator (permission level 2) commands either directly or
	 * indirectly, such as give or setblock. A similar method exists for entities at {@link
	 * net.minecraft.entity.Entity#ignoreItemEntityData()}.<p>For example, {@link
	 * net.minecraft.tileentity.TileEntitySign#onlyOpsCanSetNbt() signs} (player right-clicking) and {@link
	 * net.minecraft.tileentity.TileEntityCommandBlock#onlyOpsCanSetNbt() command blocks} are considered
	 * accessible.</p>@return true if this block entity offers ways for unauthorized players to use restricted commands
	 */
	@Override
	public boolean onlyOpCanSetNbt() {
		return true;
	}

	public boolean isEditable() {
		return this.isEditable;
	}

	/**
	 * Sets the sign's isEditable flag to the specified parameter.
	 */
	@OnlyIn(Dist.CLIENT)
	public void setEditable(boolean pIsEditable) {
		this.isEditable = pIsEditable;
		if (!pIsEditable) {
			this.playerWhoMayEdit = null;
		}

	}

	public void setAllowedPlayerEditor(PlayerEntity p_145912_1_) {
		this.playerWhoMayEdit = p_145912_1_;
	}

	public PlayerEntity getPlayerWhoMayEdit() {
		return this.playerWhoMayEdit;
	}

	/*
	public boolean executeClickCommands(PlayerEntity p_174882_1_) {
		for(ITextComponent itextcomponent : this.messages) {
			Style style = itextcomponent == null ? null : itextcomponent.getStyle();
			if (style != null && style.getClickEvent() != null) {
				ClickEvent clickevent = style.getClickEvent();
				if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
					p_174882_1_.getServer().getCommands().performCommand(this.createCommandSourceStack((ServerPlayerEntity)p_174882_1_), clickevent.getValue());
				}
			}
		}

		return true;
	}

	public CommandSource createCommandSourceStack(@Nullable ServerPlayerEntity pPlayer) {
		String s = pPlayer == null ? "StoneSign" : pPlayer.getName().getString();
		ITextComponent itextcomponent = (ITextComponent)(pPlayer == null ? new StringTextComponent("StoneSign") : pPlayer.getDisplayName());
		return new CommandSource(ICommandSource.NULL, Vector3d.atCenterOf(this.worldPosition), Vector2f.ZERO, (ServerWorld)this.level, 2, s, itextcomponent, this.level.getServer(), pPlayer);
	}*/

	public DyeColor getColor() {
		return this.color;
	}

	public boolean setColor(DyeColor pColor) {
		if (pColor != this.getColor()) {
			this.color = pColor;
			this.setChanged();
			this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
			return true;
		} else {
			return false;
		}
	}
}
