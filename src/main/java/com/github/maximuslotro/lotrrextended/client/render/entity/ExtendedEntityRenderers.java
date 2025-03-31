package com.github.maximuslotro.lotrrextended.client.render.entity;

import lotr.client.render.entity.*;
import lotr.common.init.ExtendedEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ExtendedEntityRenderers {
	public static void registerEntityRenderers() {
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GALADHRIM_WANDERER.get(), ExtendedGaladhrimWandererRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.ODDMENT_COLLECTOR.get(), ExtendedOddmentCollectorRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.RIVENDELL_WANDERER.get(), ExtendedRivendellWandererRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BLUE_MOUNTAINS_MERCHANT.get(), ExtendedBlueMountainsMerchantRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BARROW_WIGHT.get(), ExtendedBarrowWightRenderer::new);
		// Animals
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUCK.get(), ExtendedDuckRenderer::new);
		// Projectiles
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUCK_EGG_ENTITY.get(), manager -> new SpriteRenderer<>(manager, itemRenderer));
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.THROWN_CONKER.get(), manager -> new SpriteRenderer<>(manager, itemRenderer));
		// Special
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BANNER_HOLDER.get(), ExtendedBannerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.CHAIR.get(), ExtendedNullEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), ExtendedStructureSpawnerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.TRADER_RESPAWNER_ENTITY.get(), ExtendedTraderRespawnerRenderer::new);
		// Hobbit
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_BARTENDER.get(), ExtendedHobbitBartenderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_FARMER.get(), ExtendedHobbitFarmerRenderer::new);
		// Gundabad
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_CAMP_TRADER.get(), ExtendedGundabadOrcScroungerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN.get(), ExtendedGundabadOrcChieftainRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_URUK.get(), LargeOrcRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_URUK_ARCHER.get(), LargeOrcRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HIRED_GUNDABAD_ORC.get(), ExtendedHiredGundabadOrcRenderer::new);
		// Bree
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_MERCHANT.get(), ExtendedBreeMerchantRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HIRED_BREE_GUARD.get(), ExtendedHiredBreeGuardRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_SHERIFF.get(), ExtendedBreeCaptainRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_FARMER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_POTTER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_BAKER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_BUTCHER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_LUMBERMAN.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_STONEMASON.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_SMITH.get(), ExtendedBreeSmithRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_INNKEEPER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_FLORIST.get(), ExtendedBreeManTraderRenderer::new);
		//RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_CHEESEMONGER.get(), ExtendedBreeManTraderRenderer::new);
		//RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_LEATHERWORKER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_BREWER.get(), ExtendedBreeManTraderRenderer::new);
		// Ranger North
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUNEDAIN_RANGER.get(), ExtendedDunedainRangerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HIRED_DUNEDAIN_RANGER.get(), ExtendedDunedainRangerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUNEDAIN_RANGER_CAPTAIN.get(), ExtendedDunedainRangerCaptainRenderer::new);
	}
}
