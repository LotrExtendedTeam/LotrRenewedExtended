package com.github.maximuslotro.lotrrextended.client.gui.widget;


import com.github.maximuslotro.lotrrextended.client.render.ExtendedBannerTextureRegion;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionDetector;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerProtectionLevels;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;
import com.github.maximuslotro.lotrrextended.common.eventhandlers.BannerEventHandlers;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

import static lotr.client.util.LOTRClientUtil.blit;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ExtendedBannerWarningWidget  {

    private static final Map<ExtendedBannerTypes, ExtendedBannerTextureRegion> bannerType = new HashMap();



    static {
        bannerType.put(ExtendedBannerTypes.DURINSFOLK,
                new ExtendedBannerTextureRegion(new ResourceLocation("lotrextended", "textures/block/banner/durins_folk_wall_banner.png"),
                        0, 0, 19, 36));

        bannerType.put(ExtendedBannerTypes.SHIRE,
                new ExtendedBannerTextureRegion(new ResourceLocation("lotrextended", "textures/block/banner/hobbit_wall_banner.png"),
                        0, 0, 19, 36));

        bannerType.put(ExtendedBannerTypes.DUNEDAIN,
                new ExtendedBannerTextureRegion(new ResourceLocation("lotrextended", "textures/block/banner/dunedain_wall_banner.png"),
                        0, 0, 19, 36));

        bannerType.put(ExtendedBannerTypes.BLUEMOUNTIANS,
                new ExtendedBannerTextureRegion(new ResourceLocation("lotrextended", "textures/block/banner/blue_mountains_wall_banner.png"),
                        0, 0, 19, 36));

        bannerType.put(ExtendedBannerTypes.GUNDABAD_ORC,
                new ExtendedBannerTextureRegion(new ResourceLocation("lotrextended", "textures/block/banner/gundabad_orc_wall_banner.png"),
                        0, 0, 19, 36));

        bannerType.put(ExtendedBannerTypes.BREE,
                new ExtendedBannerTextureRegion(new ResourceLocation("lotrextended", "textures/block/banner/bree_wall_banner.png"),
                        0, 0, 19, 36));
    }

    public static void renderBannerWarningWidget(MatrixStack matStack, Minecraft mc, float x, float y) {
        ExtendedBannerProtectionLevels level = BannerEventHandlers.getProtectionLevelNear(mc.player);
        ExtendedBannerTypes type = ExtendedBannerProtectionDetector.getNearbyBannerType(mc.player, level);
        ExtendedBannerTextureRegion blueMountains = bannerType.get(ExtendedBannerTypes.BLUEMOUNTIANS);
        ExtendedBannerTextureRegion durinsFolk = bannerType.get(ExtendedBannerTypes.DURINSFOLK);
        ExtendedBannerTextureRegion shire = bannerType.get(ExtendedBannerTypes.SHIRE);
        ExtendedBannerTextureRegion dunedain = bannerType.get(ExtendedBannerTypes.DUNEDAIN);
        ExtendedBannerTextureRegion gundabadOrc = bannerType.get(ExtendedBannerTypes.GUNDABAD_ORC);
        ExtendedBannerTextureRegion bree = bannerType.get(ExtendedBannerTypes.BREE);





        if (level != null) {
            if (type == ExtendedBannerTypes.BLUEMOUNTIANS) {
                mc.getTextureManager().bind(blueMountains.texture);
                blit(matStack, (int)x, (int)y, blueMountains.srcX, blueMountains.srcY, blueMountains.width, blueMountains.height);

            }

        }

        if (level != null) {
            if (type == ExtendedBannerTypes.DURINSFOLK) {
                mc.getTextureManager().bind(durinsFolk.texture);
                blit(matStack, (int)x, (int)y, durinsFolk.srcX, durinsFolk.srcY, durinsFolk.width, durinsFolk.height);

            }

        }

        if (level != null) {
            if (type == ExtendedBannerTypes.SHIRE) {
                mc.getTextureManager().bind(shire.texture);
                blit(matStack, (int)x, (int)y, shire.srcX, shire.srcY, shire.width, shire.height);

            }

        }

        if (level != null) {
            if (type == ExtendedBannerTypes.DUNEDAIN) {
                mc.getTextureManager().bind(dunedain.texture);
                blit(matStack, (int)x, (int)y, dunedain.srcX, dunedain.srcY, dunedain.width, dunedain.height);

            }

        }

        if (level != null) {
            if (type == ExtendedBannerTypes.GUNDABAD_ORC) {
                mc.getTextureManager().bind(gundabadOrc.texture);
                blit(matStack, (int)x, (int)y, gundabadOrc.srcX, gundabadOrc.srcY, gundabadOrc.width, gundabadOrc.height);

            }

        }
        if (level != null) {
            if (type == ExtendedBannerTypes.BREE) {
                mc.getTextureManager().bind(bree.texture);
                blit(matStack, (int)x, (int)y, bree.srcX, bree.srcY, bree.width, bree.height);

            }

        }


    }



    }

