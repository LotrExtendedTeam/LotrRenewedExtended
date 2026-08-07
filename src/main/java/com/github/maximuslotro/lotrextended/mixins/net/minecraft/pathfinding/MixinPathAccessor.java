package com.github.maximuslotro.lotrextended.mixins.net.minecraft.pathfinding;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;

@Mixin(Path.class)
public interface MixinPathAccessor {

	@Accessor("nodes")
	List<PathPoint> getNodes();

	@Accessor("openSet")
	PathPoint[] getOpenSetExtended();

	@Accessor("closedSet")
	PathPoint[] getClosedSetExtended();

	@Accessor("openSet")
	void setOpenSet(PathPoint[] openSet);

	@Accessor("closedSet")
	void setClosedSet(PathPoint[] closedSet);
}
