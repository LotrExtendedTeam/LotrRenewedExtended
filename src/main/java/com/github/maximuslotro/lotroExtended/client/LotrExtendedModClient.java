package com.github.maximuslotro.lotroExtended.client;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.Priority;
import net.minecraft.resources.ResourcePackList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LotrExtendedModClient {
	
	public static void clientSetup() {
		if(Minecraft.getInstance()==null) {
			return;
		}
		createPack("legacy", "Legacy TexturePack");
		createPack("renewed", "Remastered Renewed TexturePack");
	}

	public static void createPack(String namespace, String name) {
		ResourcePackList packs =Minecraft.getInstance().getResourcePackRepository();
		packs.addPackFinder((consumer, iFactory) -> consumer.accept(ResourcePackInfo.create(
				LotrExtendedMod.MODID+":"+namespace+"_textures", 
				false, () -> new AlternateResourcePacks(LotrExtendedMod.INSTANCE.getModfile(), namespace, name), 
				iFactory, Priority.TOP, IPackNameDecorator.passThrough())));
	}
}
