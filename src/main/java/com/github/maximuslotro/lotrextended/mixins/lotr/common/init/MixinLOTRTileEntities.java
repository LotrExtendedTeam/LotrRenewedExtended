package com.github.maximuslotro.lotrextended.mixins.lotr.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRTileEntities;
import lotr.common.tileentity.VesselDrinkTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

/**
 * Used to modify tile entities registered by Renewed
 * @Mixin (LOTRTileEntities.class)
 * */
@SuppressWarnings("deprecation")
@Mixin(LOTRTileEntities.class)
public class MixinLOTRTileEntities {
	/**
	 * Get the tileEntity DeferredRegister for Renewed's tileEntities
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES;

	/**
	 * @author maximuslotro
	 * @reason Need to change VesselTileEntityAppliedBlocks to add more drink container types
	 * @Overwrite (remap=false)
	 * @param <T> the type of module extending TileEntity
	 * @param key The id of the tileEntity
	 * @param builderSup A list of blocks to bind the tileEntity to
	 * @return A RegistryObject instance for the tileEntity
	 */
	@SuppressWarnings("unchecked")
	@Overwrite(remap=false)
	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String key, Supplier<TileEntityType.Builder<T>> builderSup) {
		Supplier<TileEntityType.Builder<T>> builderSupEXTENDED;
		if("vessel_drink".equals(key)) {
			builderSupEXTENDED = () -> (Builder<T>) Builder.of(VesselDrinkTileEntity::new, LOTRBlocks.WOODEN_MUG.get(), LOTRBlocks.CERAMIC_MUG.get(),
					LOTRBlocks.GOLDEN_GOBLET.get(), LOTRBlocks.SILVER_GOBLET.get(), LOTRBlocks.COPPER_GOBLET.get(), LOTRBlocks.WOODEN_CUP.get(),
					LOTRBlocks.ALE_HORN.get(), LOTRBlocks.GOLDEN_ALE_HORN.get(), ExtendedBlocks.SKULL_GOBLET.get(), ExtendedBlocks.GLASS_BOTTLE.get());
		}else {
			builderSupEXTENDED = builderSup;
		}

		Type<?> datafixType = null;
		try {
			datafixType = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getCurrentVersion().getWorldVersion())).getChoiceType(TypeReferences.BLOCK_ENTITY, key);
		} catch (IllegalArgumentException var4) {
			if (SharedConstants.IS_RUNNING_IN_IDE) {
				throw var4;
			}
		}
		final Type<?> datafixType2 = datafixType;
		return TILE_ENTITIES.register(key, () -> builderSupEXTENDED.get().build(datafixType2));
	}
}
