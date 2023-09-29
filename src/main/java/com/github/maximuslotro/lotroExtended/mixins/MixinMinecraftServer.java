package com.github.maximuslotro.lotroExtended.mixins;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
	 static int newField; //will get added to the target class
}
