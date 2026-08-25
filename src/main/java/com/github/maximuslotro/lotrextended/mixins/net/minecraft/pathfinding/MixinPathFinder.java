package com.github.maximuslotro.lotrextended.mixins.net.minecraft.pathfinding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerDebugToggles;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedPath;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathHeap;
import net.minecraft.pathfinding.PathPoint;

@Mixin(PathFinder.class)
public class MixinPathFinder {

	@Final
	@Shadow
	private PathHeap openSet;

	/* Old inject missing closed nodes set
	@Inject(method = "findPath", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
	public void findPathExtended(Region pRegion, MobEntity pMob, Set<BlockPos> pTargetPositions, float pMaxRange, int pAccuracy, float pSearchDepthMultiplier,
		CallbackInfoReturnable<Path> info, PathPoint pathpoint,  Map<FlaggedPathPoint, BlockPos> map) {
		MixinPathAccessor pathAcc= (MixinPathAccessor)info.getReturnValue();
		pathAcc.setOpenSet(((MixinPathHeap)openSet).getHeap());
		pathAcc.setClosedSet(new PathPoint[0]);
		pathAcc.setTargetNodes(map.keySet());
	}*/

	// Allow closed set to be modifiable
	@ModifyVariable(
        method = "findPath(Lnet/minecraft/pathfinding/PathPoint;Ljava/util/Map;FIF)Lnet/minecraft/pathfinding/Path;",
        at = @At("STORE"),
        ordinal = 1
    )
    private Set<PathPoint> initializeClosedSetExtended(Set<PathPoint> original) {
        if (ExtendedServerDebugToggles.renderNavPaths) {
            return new HashSet<>();
        }
        return original;
    }
	// Add closed nodes to closed set
	@Inject(
	    method = "findPath(Lnet/minecraft/pathfinding/PathPoint;Ljava/util/Map;FIF)Lnet/minecraft/pathfinding/Path;",
	    at = @At(
	        value = "INVOKE",
	        target = "Ljava/util/Set;isEmpty()Z",
	        remap = false,
	        ordinal = 0
	    )
	)
	private void recordNodeExtended(CallbackInfoReturnable<Path> cir, @Local(ordinal = 1) Set<PathPoint> closedSet,
		@Local(ordinal = 2) Set<FlaggedPathPoint> reachedTargets, @Local(ordinal = 1) PathPoint pathpoint) {
	    if (ExtendedServerDebugToggles.renderNavPaths && reachedTargets.isEmpty()) {
	        closedSet.add(pathpoint);
	    }
	}
	// Add nodes to path
	@Inject(
		method = "findPath(Lnet/minecraft/pathfinding/PathPoint;Ljava/util/Map;FIF)Lnet/minecraft/pathfinding/Path;", 
		at = @At("RETURN")
		)
	private void findPathReturnExtended(CallbackInfoReturnable<Path> cir, @Local(ordinal = 0) Set<FlaggedPathPoint> set, @Local(ordinal = 1) Set<PathPoint> closedSet) {
		Path path = cir.getReturnValue();
		if (path != null&& ExtendedServerDebugToggles.renderNavPaths) {
			MixinPathAccessor pathAcc = (MixinPathAccessor) path;
			MixinPathHeap heapAcc = (MixinPathHeap) this.openSet;
			pathAcc.setOpenSet(Arrays.copyOf(heapAcc.getHeap(), heapAcc.getSize()));
			pathAcc.setClosedSet(closedSet.toArray(new PathPoint[0]));
			((ExtendedPath)path).setTargetNodesExtended(set);
		}
	}
}