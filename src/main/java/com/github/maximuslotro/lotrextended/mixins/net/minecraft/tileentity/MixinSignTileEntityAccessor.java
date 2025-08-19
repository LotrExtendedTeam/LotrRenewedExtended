package com.github.maximuslotro.lotrextended.mixins.net.minecraft.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.ITextComponent;

@Mixin(SignTileEntity.class)
public interface MixinSignTileEntityAccessor {
	@Accessor("messages")
	ITextComponent[] getMessages();

	@Accessor("messages")
	public void setMessages(ITextComponent[] messages);
}
