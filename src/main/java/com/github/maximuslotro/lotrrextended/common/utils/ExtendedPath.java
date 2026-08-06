package com.github.maximuslotro.lotrrextended.common.utils;

import java.util.Set;

import net.minecraft.pathfinding.FlaggedPathPoint;

public interface ExtendedPath {

    Set<FlaggedPathPoint> getTargetNodesExtended();
    void setTargetNodesExtended(Set<FlaggedPathPoint> targetNodes);
}
