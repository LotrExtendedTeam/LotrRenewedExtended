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
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BARROW_WIGHT.get(), ExtendedBarrowWightRenderer::new);
		// Animals
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUCK.get(), ExtendedDuckRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HORSE_BOAR.get(), ExtendedBoarRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HEDGEHOG.get(), ExtendedHedgehogRenderer::new);
		// Projectiles
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUCK_EGG_ENTITY.get(), manager -> new SpriteRenderer<>(manager, itemRenderer));
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.THROWN_CONKER.get(), manager -> new SpriteRenderer<>(manager, itemRenderer));
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.THROWN_AXE.get(), ExtendedThrowableAxeRenderer::new);
		// Special
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BANNER_HOLDER.get(), ExtendedBannerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.CHAIR.get(), ExtendedNullEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), ExtendedStructureSpawnerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.RESPAWNER_ENTITY.get(), ExtendedRespawnerRenderer::new);
		// Hobbit
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_BARTENDER.get(), ExtendedHobbitBartenderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_FARMER.get(), ExtendedHobbitFarmerRenderer::new);
		// Gundabad
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_CAMP_TRADER.get(), ExtendedSmallOrcTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN.get(), ExtendedLargeOrcTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_URUK.get(), LargeOrcRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_URUK_ARCHER.get(), LargeOrcRenderer::new);
		// Bree
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_MERCHANT.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_SHERIFF.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_FARMER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_POTTER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_BAKER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_BUTCHER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_LUMBERMAN.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_STONEMASON.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_SMITH.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_INNKEEPER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_FLORIST.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_CHEESEMONGER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_LEATHERWORKER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_BREWER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_STABLEMASTER.get(), ExtendedBreeManTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_HOBBIT_POTTER.get(), ExtendedBreeHobbitTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_HOBBIT_BAKER.get(), ExtendedBreeHobbitTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_HOBBIT_BUTCHER.get(), ExtendedBreeHobbitTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_HOBBIT_INNKEEPER.get(), ExtendedBreeHobbitTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_HOBBIT_FLORIST.get(), ExtendedBreeHobbitTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BREE_HOBBIT_BREWER.get(), ExtendedBreeHobbitTraderRenderer::new);
		// Ranger North
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUNEDAIN_RANGER.get(), ExtendedDunedainRangerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUNEDAIN_RANGER_CAPTAIN.get(), ExtendedDunedainRangerCaptainRenderer::new);
		// Blue Mountains
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BLUE_MOUNTAINS_MERCHANT.get(), ExtendedBlueMountainsTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BLUE_DWARVEN_MINER.get(), ExtendedBlueMountainsTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BLUE_DWARVEN_SMITH.get(), ExtendedBlueMountainsTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BLUE_DWARVEN_COMMANDER.get(), ExtendedBlueMountainsTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BLUE_DWARVEN_BARTENDER.get(), ExtendedBlueMountainsTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BLUE_DWARVEN_BUTCHER.get(), ExtendedBlueMountainsTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BLUE_DWARVEN_STONEMASON.get(), ExtendedBlueMountainsTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BLUE_DWARVEN_AXE_THROWER.get(), BlueDwarfRenderer::new);
		// Dwarven
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DWARVEN_MINER.get(), ExtendedDurinsFolkTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DWARVEN_SMITH.get(), ExtendedDurinsFolkTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DWARVEN_COMMANDER.get(), ExtendedDurinsFolkTraderRenderer::new);
		// Rivendell
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.RIVENDELL_CAPTAIN.get(), ExtendedHighElfTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.RIVENDELL_SMITH.get(), ExtendedHighElfTraderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.IMLADRIS_GUARD.get(), HighElfRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.ELROND.get(), ExtendedElrondRenderer::new);
		// Special
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.TOLKIEN.get(), ExtendedTolkienRenderer::new);

		// Bandit
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BANDIT.get(), ExtendedBanditRenderer::new);
	}
}
