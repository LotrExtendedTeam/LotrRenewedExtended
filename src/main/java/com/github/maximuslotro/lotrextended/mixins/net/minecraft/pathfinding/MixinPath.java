package com.github.maximuslotro.lotrextended.mixins.net.minecraft.pathfinding;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedPath;

import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.Path;

@Mixin(Path.class)
public class MixinPath implements ExtendedPath {

    @Unique
    private Set<FlaggedPathPoint> lotrExtended$targetNodes;

    @Override
    public Set<FlaggedPathPoint> getTargetNodesExtended() {
        return this.lotrExtended$targetNodes;
    }

    @Override
    public void setTargetNodesExtended(Set<FlaggedPathPoint> targetNodes) {
        this.lotrExtended$targetNodes = targetNodes;
    }
}
