package com.github.maximuslotro.lotrextended.mixins.net.minecraft.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.ITextComponent;

/**
 * Accessor mixin for {@link SignTileEntity}.
 * <p>
 * Provides direct access to the private {@code messages} field,
 * allowing external code (like structure placement injections)
 * to read and modify sign text contents.
 * </p>
 *
 * <h3>Usage:</h3>
 * <pre>
 * ITextComponent[] current = ((MixinSignTileEntityAccessor) signTile).getMessages();
 * current[0] = new StringTextComponent("Hello!");
 * ((MixinSignTileEntityAccessor) signTile).setMessages(current);
 * </pre>
 * @Mixin (SignTileEntity.class)
 */
@Mixin(SignTileEntity.class)
public interface MixinSignTileEntityAccessor {
	/**
	 * Gets the sign's text messages.
	 *
	 * @return the array of {@link ITextComponent} representing each line on the sign
	 * @Accessor ("messages")
	 */
	@Accessor("messages")
	ITextComponent[] getMessages();

	/**
	 * Sets the sign's text messages.
	 *
	 * @param messages the new array of {@link ITextComponent} lines to apply
	 * @Accessor ("messages")
	 */
	@Accessor("messages")
	public void setMessages(ITextComponent[] messages);
}
