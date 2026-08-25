package com.github.maximuslotro.lotrextended.mixins.net.minecraft.pathfinding;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.pathfinding.PathHeap;
import net.minecraft.pathfinding.PathPoint;

@Mixin(PathHeap.class)
public interface MixinPathHeap {

	@Accessor("heap")
	PathPoint[] getHeap();
	
	@Accessor("size") 
    int getSize();
}
