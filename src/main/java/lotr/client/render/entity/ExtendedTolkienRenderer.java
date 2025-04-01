package lotr.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import lotr.client.render.RandomTextureVariants;
import lotr.common.entity.npc.ExtendedTolkienEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedTolkienRenderer extends AbstractManRenderer<ExtendedTolkienEntity> {
	public static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList(new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/tolkien"));
	public static final RandomTextureVariants SKINS_MUMMY = RandomTextureVariants.loadSkinsList(new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/barrow_wight"));

	public ExtendedTolkienRenderer(EntityRendererManager mgr) {
		super(mgr);
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedTolkienEntity tolkien) {
		if(tolkien.getRandom().nextInt(10000)==1973) {
			return SKINS_MUMMY.getRandomSkin(tolkien);
		}
		return SKINS.getRandomSkin(tolkien);
	}
}
