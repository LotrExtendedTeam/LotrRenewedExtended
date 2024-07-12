package com.github.maximuslotro.lotrrextended.client;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.ExtendedColorHelper;
import com.github.maximuslotro.lotrrextended.client.render.block.BlockRendererSetup;
import com.github.maximuslotro.lotrrextended.client.render.item.ExtendedItemModelsProperties;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.mixins.MIXINConfig;
import com.google.common.collect.ImmutableSet;

import lotr.common.block.ExtendedSignTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.Priority;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class LotrExtendedModClient {

	public static void staticClientSetup() {
		if(Minecraft.getInstance()==null) {
			return;
		}
		new LotrExtendedModClient();
	}

	public static LotrExtendedModClient INSTANCE;

	public LotrExtendedMod getParentInstance() {
		return LotrExtendedMod.INSTANCE;
	}

	public LotrExtendedModClient() {
		INSTANCE = this;
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(this::login);
		forgeBus.addListener(this::commandevent);
		modBus.addListener(this::onParticleRegistry);
		modBus.addListener(this::clientStartupEvent);
		modBus.addListener(BlockRendererSetup::addStupidChestTexturesTookMeSeveralDaysToGetThisRight);
		modBus.addListener(ExtendedColorHelper::makeBlockColors);
		modBus.addListener(ExtendedColorHelper::makeItemsColors);
		ExtendedClientConfig.register();
		//Moved to Mixin
		//createPack("legacy", "Legacy TexturePack");
		//createPack("renewed", "Remastered Renewed TexturePack");
	}

    /**
     * A fix since forge does not have client commands in 1.16.5
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

	public static void createLecacyPack(ResourcePackList packs) {
		packs.addPackFinder((consumer, iFactory) -> consumer.accept(ResourcePackInfo.create(
				LotrExtendedMod.MODID+":legacy_textures", 
				false, () -> new AlternateResourcePacks(INSTANCE.getParentInstance().getModfile(), "legacy", "Legacy TexturePack"), 
				iFactory, Priority.TOP, IPackNameDecorator.BUILT_IN)));
	}

	public static void createRenewedPack(ResourcePackList packs) {
		packs.addPackFinder((consumer, iFactory) -> consumer.accept(ResourcePackInfo.create(
				LotrExtendedMod.MODID+":renewed_textures", 
				false, () -> new AlternateResourcePacks(INSTANCE.getParentInstance().getModfile(), "renewed", "Extended Renewed Textures"), 
				iFactory, Priority.TOP, IPackNameDecorator.BUILT_IN)));
	}

	public void clientStartupEvent(final FMLClientSetupEvent event) {
		BlockRendererSetup.setupBlockRenderers();
		event.enqueueWork(()->{
			ExtendedSignTypes.forEach(sign -> Atlases.addWoodType(sign));
			ExtendedItemModelsProperties.registerAlternativeHandheldTextures();
		});
		BlockRendererSetup.regModels();

		startVersionChecks();
    }

    public void login(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof ClientPlayerEntity)
        {
            VersionChecker.CheckResult result = VersionChecker.getResult(ModList.get().getModContainerById(LotrExtendedMod.MODID).get().getModInfo());
            if (result != null && (result.status == VersionChecker.Status.OUTDATED || result.status == VersionChecker.Status.BETA_OUTDATED))
            {
            	TextComponent base = new StringTextComponent("[LotrRenewed Extended]:");
            	base.withStyle(TextFormatting.DARK_GREEN);
            	base.append(new TranslationTextComponent("command.lotrextended.version.left")).withStyle(TextFormatting.RESET);
            	base.append(new StringTextComponent(" (")).withStyle(TextFormatting.RESET);
            	base.append(new StringTextComponent(result.target.toString())).withStyle(TextFormatting.DARK_AQUA);
            	base.append(new StringTextComponent(") ")).withStyle(TextFormatting.RESET);
            	base.append(new TranslationTextComponent("command.lotrextended.version.right")).withStyle(TextFormatting.RESET);
                event.getEntity().sendMessage(base, event.getEntity().getUUID());
            }
        }
    }

	public void onParticleRegistry(ParticleFactoryRegisterEvent event) {
		ExtendedClientParticles.register(event);
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
		}
	}
}
