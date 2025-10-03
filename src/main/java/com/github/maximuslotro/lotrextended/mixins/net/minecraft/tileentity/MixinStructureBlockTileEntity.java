package com.github.maximuslotro.lotrextended.mixins.net.minecraft.tileentity;

import org.spongepowered.asm.mixin.Mixin;

import com.github.maximuslotro.lotrextended.mixinhelpers.MixinConstants;

import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Client-side mixin for {@link StructureBlockTileEntity}.
 * <p>
 * This mixin modifies the behavior of structure block tile entities
 * to allow a configurable render distance rather than relying on
 * the fixed vanilla value.
 * </p>
 *
 * <h2>Key Modifications:</h2>
 * <ul>
 *   <li>Overrides {@link #getViewDistance()} to return a dynamic value
 *       from {@link MixinConstants#changeableNonConfigCrashableStrBlockRenderDistance}.</li>
 * </ul>
 * @Mixin (StructureBlockTileEntity.class)
 */
@OnlyIn(Dist.CLIENT)
@Mixin(StructureBlockTileEntity.class)
public abstract class MixinStructureBlockTileEntity extends TileEntity {

	/**
	 * Required constructor for mixins that extend classes.
	 * <p>
	 * Mixin targets that extend a class must define a matching constructor,
	 * even if it is never actually called. At runtime, this constructor will be
	 * stripped out during transformation, so it will never exist in the final class.
	 * </p>
	 *
	 * @param tileEntity placeholder parameter for {@link TileEntityType}
	 */
	public MixinStructureBlockTileEntity(TileEntityType<?> tileEntity) {
		super(tileEntity);
	}
	/**
	 * Overrides the default render distance for {@link StructureBlockTileEntity}.
	 * <p>
	 * Instead of using the vanilla fixed distance, this delegates to a configurable
	 * constant stored in {@link MixinConstants#changeableNonConfigCrashableStrBlockRenderDistance}.
	 * This allows the render distance of structure blocks to be changed dynamically
	 * for debugging, optimization, or extended client features.
	 * </p>
	 *
	 * @return the custom view distance for rendering structure blocks
	 */
	@Override
	public double getViewDistance() {
		return MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance;
	}
}
