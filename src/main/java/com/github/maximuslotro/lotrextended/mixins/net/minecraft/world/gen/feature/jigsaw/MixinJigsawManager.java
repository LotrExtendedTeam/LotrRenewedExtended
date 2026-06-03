package com.github.maximuslotro.lotrextended.mixins.net.minecraft.world.gen.feature.jigsaw;

import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

@Mixin(JigsawManager.class)
public class MixinJigsawManager {
	@Inject(
        method = "addPieces(Lnet/minecraft/util/registry/DynamicRegistries;Lnet/minecraft/world/gen/feature/structure/AbstractVillagePiece;ILnet/minecraft/world/gen/feature/jigsaw/JigsawManager$IPieceFactory;Lnet/minecraft/world/gen/ChunkGenerator;Lnet/minecraft/world/gen/feature/template/TemplateManager;Ljava/util/List;Ljava/util/Random;)V",
        at = @At("HEAD"),
        cancellable = true,
        require = -1
    )
    private static void onAddPieces(DynamicRegistries registries, AbstractVillagePiece piece,  int maxDepth, JigsawManager.IPieceFactory pieceFactory,  ChunkGenerator chunkGenerator, TemplateManager templateManager, List<? super AbstractVillagePiece> pieces, Random random, CallbackInfo info) {
        /* Only inject for debugging
		System.out.println("Intercepted structure generation for piece: " + piece);
        info.cancel();
        ExtendedJigsawManager.addPieces(registries, piece, maxDepth, AbstractVillagePiece::new, chunkGenerator, templateManager, pieces, random);
        */
    }
}
