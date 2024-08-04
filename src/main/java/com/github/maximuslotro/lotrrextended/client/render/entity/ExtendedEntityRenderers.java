package com.github.maximuslotro.lotrrextended.client.render.entity;

import lotr.client.render.entity.ExtendedDunedainRangerRenderer;
import lotr.client.render.entity.ExtendedGaladhrimWandererRenderer;
import lotr.client.render.entity.ExtendedHobbitBartenderRenderer;
import lotr.client.render.entity.ExtendedHobbitFarmerRenderer;
import lotr.client.render.entity.ExtendedOddmentCollectorRenderer;
import lotr.client.render.entity.ExtendedRivendellWandererRenderer;
import lotr.common.init.ExtendedEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ExtendedEntityRenderers {
	public static void registerEntityRenderers() {
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUNEDAIN_RANGER.get(), ExtendedDunedainRangerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GALADHRIM_WANDERER.get(), ExtendedGaladhrimWandererRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_BARTENDER.get(), ExtendedHobbitBartenderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_FARMER.get(), ExtendedHobbitFarmerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.ODDMENT_COLLECTOR.get(), ExtendedOddmentCollectorRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.RIVENDELL_WANDERER.get(), ExtendedRivendellWandererRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.THROWN_CONKER.get(), manager -> new SpriteRenderer<>(manager, itemRenderer));
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUCK.get(), ExtendedDuckRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUCK_EGG_ENTITY.get(), manager -> new SpriteRenderer<>(manager, itemRenderer));
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), ExtendedStructureSpawnerRenderer::new);
	}
}
