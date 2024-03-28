package com.github.maximuslotro.lotrrextended.client;

import org.apache.maven.artifact.versioning.ComparableVersion;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.block.BlockRendererSetup;
import com.github.maximuslotro.lotrrextended.client.render.item.ExtendedItemModelsProperties;
import com.github.maximuslotro.lotrrextended.mixins.MIXINConfig;

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
				iFactory, Priority.TOP, IPackNameDecorator.passThrough())));
	}

	public static void createRenewedPack(ResourcePackList packs) {
		packs.addPackFinder((consumer, iFactory) -> consumer.accept(ResourcePackInfo.create(
				LotrExtendedMod.MODID+":renewed_textures", 
				false, () -> new AlternateResourcePacks(INSTANCE.getParentInstance().getModfile(), "renewed", "Remastered Renewed TexturePack"), 
				iFactory, Priority.TOP, IPackNameDecorator.passThrough())));
	}

	public void clientStartupEvent(final FMLClientSetupEvent event) {
		BlockRendererSetup.setupBlockRenderers();
		event.enqueueWork(()->{
			ExtendedSignTypes.forEach(sign -> Atlases.addWoodType(sign));
		});
		ExtendedItemModelsProperties.registerAlternativeHandheldTextures();
		BlockRendererSetup.regModels();
    }

    public void login(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof ClientPlayerEntity)
        {
            VersionChecker.CheckResult result = VersionChecker.getResult(ModList.get().getModContainerById(LotrExtendedMod.MODID).get().getModInfo());
            if (result != null && (result.status == VersionChecker.Status.OUTDATED || result.status == VersionChecker.Status.BETA_OUTDATED))
            {
                event.getEntity().sendMessage(new StringTextComponent("\u00A72[LotrRenewed Extended]:\u00A7r A new version (\u00A73" + result.target.toString () + "\u00A7r) was found"), event.getEntity().getUUID());
            }
        }
    }

	public void onParticleRegistry(ParticleFactoryRegisterEvent event) {
		ExtendedClientParticles.register(event);
	}
}
