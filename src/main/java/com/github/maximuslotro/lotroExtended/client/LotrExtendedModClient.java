package com.github.maximuslotro.lotroExtended.client;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.Priority;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;

@OnlyIn(Dist.CLIENT)
public class LotrExtendedModClient {
	
	public static LotrExtendedModClient INSTANCE;

	public LotrExtendedModClient() {
		MinecraftForge.EVENT_BUS.register(this);
		INSTANCE = this;
		createPack("legacy", "Legacy TexturePack");
		createPack("renewed", "Remastered Renewed TexturePack");
	}
	public LotrExtendedMod getParentInstance() {
		return LotrExtendedMod.INSTANCE;
	}
	
	public static void clientSetup() {
		if(Minecraft.getInstance()==null) {
			return;
		}
		new LotrExtendedModClient();
	}

	public static void createPack(String namespace, String name) {
		ResourcePackList packs =Minecraft.getInstance().getResourcePackRepository();
		packs.addPackFinder((consumer, iFactory) -> consumer.accept(ResourcePackInfo.create(
				LotrExtendedMod.MODID+":"+namespace+"_textures", 
				false, () -> new AlternateResourcePacks(INSTANCE.getParentInstance().getModfile(), namespace, name), 
				iFactory, Priority.TOP, IPackNameDecorator.passThrough())));
	}
	
	@SubscribeEvent
    public void login(EntityJoinWorldEvent event)
    {
		System.out.println("FOUND UPDATE!");
        if (event.getEntity() instanceof ClientPlayerEntity)
        {
            VersionChecker.CheckResult result = VersionChecker.getResult(ModList.get().getModContainerById(LotrExtendedMod.MODID).get().getModInfo());
            if (result != null && (result.status == VersionChecker.Status.OUTDATED || result.status == VersionChecker.Status.BETA_OUTDATED))
            {
                event.getEntity().sendMessage(new StringTextComponent("\u00A72[LotrRenewed Extended]:\u00A7r A new version (\u00A73" + result.target.toString () + "\u00A7r) was found"), event.getEntity().getUUID());
            }
        }
    }
}
