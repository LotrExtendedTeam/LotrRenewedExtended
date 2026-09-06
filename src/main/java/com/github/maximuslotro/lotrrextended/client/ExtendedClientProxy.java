package com.github.maximuslotro.lotrrextended.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.maximuslotro.lotrextended.mixins.MIXINConfig;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.eventhandlers.ExtendedRendererEventHandler;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedContainerScreenRegisterHandler;
import com.github.maximuslotro.lotrrextended.client.render.ExtendedBlockItemColors;
import com.github.maximuslotro.lotrrextended.client.render.block.ExtendedBlockRenderer;
import com.github.maximuslotro.lotrrextended.client.render.entity.ExtendedEntityRenderers;
import com.github.maximuslotro.lotrrextended.client.render.item.ExtendedItemModelsProperties;
import com.github.maximuslotro.lotrrextended.client.render.tileentity.ExtendedTileEntityRenderers;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedSignTypes;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.google.common.collect.ImmutableSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.Priority;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class ExtendedClientProxy {
	private Minecraft MC;

	public static void staticClientSetup() {
		if(Minecraft.getInstance()==null) {
			return;
		}
		new ExtendedClientProxy();
	}

	public static ExtendedClientProxy INSTANCE;

	public static UUID usersUUID = null;

	public long lastTimeBossBarRendered = -1;

	public LotrExtendedMod getParentInstance() {
		return LotrExtendedMod.INSTANCE;
	}

	public ExtendedClientProxy() {
		INSTANCE = this;
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		// Forge bus
		forgeBus.addListener(this::login);
		forgeBus.addListener(this::commandevent);
		forgeBus.addListener(ExtendedRendererEventHandler::renderHpBar);
		forgeBus.addListener(ExtendedRendererEventHandler::onPreRenderGameOverlay);
		forgeBus.addListener(ExtendedRendererEventHandler::addTooltipsForModifiers);
		forgeBus.addListener(ExtendedRendererEventHandler::onPostRenderGameOverlay);
		forgeBus.addListener(ExtendedRendererEventHandler::onPostRenderEntity);
		// Mod bus
		modBus.addListener(ExtendedClientParticles::register);
		modBus.addListener(this::clientStartupEvent);
		modBus.addListener(ExtendedBlockRenderer::addStupidChestTexturesTookMeSeveralDaysToGetThisRight);
		modBus.addListener(ExtendedBlockItemColors::makeBlockColors);
		modBus.addListener(ExtendedItemModelsProperties::addGuiItemSprites);
		modBus.addListener(ExtendedBlockItemColors::makeItemsColors);
		ExtendedClientConfig.register();
		MC = Minecraft.getInstance();
		//Moved to Mixin
		//createPack("legacy", "Legacy TexturePack");
		//createPack("renewed", "Remastered Renewed TexturePack");
	}

	public PlayerEntity getClientPlayerEntity() {
		return MC.player;
	}
    /**
     * A fix since forge does not have client commands in 1.16.5
     * @param event The chat event on the client
     */
    public void commandevent(ClientChatEvent event)
    {
        if (event.getOriginalMessage().equals("!lotrExtendedInfoClient"))
        {
            event.setCanceled(true);
            Minecraft instance = Minecraft.getInstance();
            instance.gui.getChat().addMessage(new TranslationTextComponent("command.lotrextended.info.listPatches"));
        	TextComponent prefex = new StringTextComponent("-");
        	prefex.withStyle(TextFormatting.GREEN);
            for (String patch : MIXINConfig.getInjectedPatches()) {
                TextComponent patchName = new StringTextComponent(patch);
                patchName.withStyle(TextFormatting.GOLD);
                TextComponent message = new TranslationTextComponent("%s %s", prefex, patchName);
                instance.gui.getChat().addMessage(message);
            }
        }
    }

	public static void createAdditionalPack(ResourcePackList packs, String id, String folder, String title, boolean forced) {
		packs.addPackFinder((consumer, iFactory) -> consumer.accept(ResourcePackInfo.create(
				LotrExtendedMod.MODID+":"+id,
				forced, () -> new ExtendedAlternateResourcePacks(INSTANCE.getParentInstance().getModfile(), folder, title),
				iFactory, Priority.TOP, IPackNameDecorator.BUILT_IN)));
	}

	public void clientStartupEvent(final FMLClientSetupEvent event) {
		ExtendedBlockRenderer.setupBlockRenderers();
		event.enqueueWork(()->{
			ExtendedSignTypes.forEach(Atlases::addWoodType);
			ExtendedItemModelsProperties.registerAlternativeHandheldTextures();
			setupUsersUUID();
		});
		ExtendedContainerScreenRegisterHandler.register();
		ExtendedEntityRenderers.registerEntityRenderers();
		ExtendedTileEntityRenderers.registerTileEntityRenderers();

		startVersionChecks();
    }

    public void login(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof ClientPlayerEntity && ExtendedClientConfig.enableClientVersionCheck.get())
        {
            VersionChecker.CheckResult result = VersionChecker.getResult(ModList.get().getModContainerById(LotrExtendedMod.MODID).get().getModInfo());
            if (result != null && (result.status == VersionChecker.Status.OUTDATED || result.status == VersionChecker.Status.BETA_OUTDATED))
            {
            	TextComponent base = new StringTextComponent("[LotrRenewed Extended]: ");
            	base.withStyle(TextFormatting.DARK_GREEN);
            	base.append(new TranslationTextComponent("command.lotrextended.version.left")).withStyle(TextFormatting.RESET);
            	base.append(new StringTextComponent(" (")).withStyle(TextFormatting.RESET);
            	base.append(new StringTextComponent(result.target.toString())).withStyle(TextFormatting.DARK_AQUA);
            	base.append(new StringTextComponent(") ")).withStyle(TextFormatting.RESET);
            	base.append(new TranslationTextComponent("command.lotrextended.version.right")).withStyle(TextFormatting.RESET);
                event.getEntity().sendMessage(base, Util.NIL_UUID);
            }
        }
    }

	public void startVersionChecks() {
		String lastVersion = ExtendedClientConfig.lastClientBootVersion.get();
		String currentVersion = ModList.get().getModContainerById(LotrExtendedMod.MODID).get().getModInfo().getVersion().toString();
		if(!lastVersion.equals(currentVersion)) {
			ExtendedClientConfig.lastClientBootVersion.set(currentVersion);
			//Handle version change resource pack load
			ResourcePackList rpl = Minecraft.getInstance().getResourcePackRepository();
			if(!rpl.getSelectedIds().contains("lotrextended:renewed_textures") && rpl.isAvailable("lotrextended:renewed_textures")) {
				//Catch Minecraft using ImmutableSet!
				List<String> packs = new ArrayList<>(rpl.getSelectedIds());
				packs.add("lotrextended:renewed_textures");
				//Return ImmutableSet!
				rpl.setSelected(packs.stream().collect(ImmutableSet.toImmutableSet()));
			}
			if(!rpl.getSelectedIds().contains("lotrextended:vanilla_textures") && rpl.isAvailable("lotrextended:vanilla_textures")) {
				//Catch Minecraft using ImmutableSet!
				List<String> packs = new ArrayList<>(rpl.getSelectedIds());
				packs.add("lotrextended:renewed_textures");
				//Return ImmutableSet!
				rpl.setSelected(packs.stream().collect(ImmutableSet.toImmutableSet()));
			}
		}
	}

	private void setupUsersUUID() {
		String userUUIDString = Minecraft.getInstance().getUser().getUuid();
		if (!userUUIDString.isEmpty()) userUUIDString = addDashesToUUID(userUUIDString);
		try {
			usersUUID = UUID.fromString(userUUIDString);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}

	private static String addDashesToUUID(String uuid) {
		return uuid.replaceFirst(
				"(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
				"$1-$2-$3-$4-$5"
		);
	}
}
