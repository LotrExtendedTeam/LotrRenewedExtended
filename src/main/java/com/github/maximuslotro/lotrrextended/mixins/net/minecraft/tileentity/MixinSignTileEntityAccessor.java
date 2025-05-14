package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.ITextComponent;

@Mixin(SignTileEntity.class)
public interface MixinSignTileEntityAccessor {
	@Accessor("MESSAGES")
	ITextComponent[] getMessages();

	@Accessor("MESSAGES")
	public void setMessages(ITextComponent[] messages);
}
