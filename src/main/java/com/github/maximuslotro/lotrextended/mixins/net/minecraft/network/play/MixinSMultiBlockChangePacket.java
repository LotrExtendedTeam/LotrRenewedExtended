package com.github.maximuslotro.lotrextended.mixins.net.minecraft.network.play;

import it.unimi.dsi.fastutil.shorts.ShortSet;
import net.minecraft.block.BlockState;
import net.minecraft.network.play.server.SMultiBlockChangePacket;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SMultiBlockChangePacket.class)
public class MixinSMultiBlockChangePacket {

    /**
     * Intercepts the getBlockState call inside the SMultiBlockChangePacket constructor.
     * This safely catches null pointers or unexpected lookups, logs full diagnostic data,
     * prints the stack trace, and provides a fallback block state to prevent server crashes.
     * @param chunkSection 
     * @param x 
     * @param y 
     * @param z 
     * @param pSectionPos 
     * @param pChangedBlocks 
     * @param pLevelChunkSection 
     * @param pSuppressLightUpdates 
     * @return A safe blockstate or throws a NPE so the issue can be caught
     */
    @Redirect(
        method = "<init>(Lnet/minecraft/util/math/SectionPos;Lit/unimi/dsi/fastutil/shorts/ShortSet;Lnet/minecraft/world/chunk/ChunkSection;Z)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/chunk/ChunkSection;getBlockState(III)Lnet/minecraft/block/BlockState;"
        )
    )
    private BlockState safeGetBlockState(
            ChunkSection chunkSection, 
            int x, int y, int z, 
            SectionPos pSectionPos, 
            ShortSet pChangedBlocks, 
            ChunkSection pLevelChunkSection, 
            boolean pSuppressLightUpdates) {
        
        try {
            if (chunkSection == null) {
                throw new NullPointerException("ChunkSection is null during packet creation!");
            }
            return chunkSection.getBlockState(x, y, z);
        } catch (Exception e) {
            // Dump exhaustive diagnostic information to your console / latest.log
            System.err.println("==================================================================");
            System.err.println("[Diagnostic Mixin] Caught Exception in SMultiBlockChangePacket!");
            System.err.println("------------------------------------------------------------------");
            System.err.println("-> SectionPos          : " + (pSectionPos != null ? pSectionPos.toString() : "NULL"));
            System.err.println("-> ChunkSection is null?: " + (chunkSection == null));
            System.err.println("-> Changed Blocks Count: " + (pChangedBlocks != null ? pChangedBlocks.size() : "NULL"));
            System.err.println("-> Relative Coords     : x=" + x + ", y=" + y + ", z=" + z);
            System.err.println("-> Suppress Light      : " + pSuppressLightUpdates);
            System.err.println("-> Exception Type      : " + e.getClass().getName());
            System.err.println("-> Exception Message   : " + e.getMessage());
            System.err.println("==================================================================");
            e.printStackTrace(); // Prints the full stack trace to pinpoint what triggered it

            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException("Wrapped exception in SMultiBlockChangePacket diagnostic mixin", e);
            }
        }
    }
}