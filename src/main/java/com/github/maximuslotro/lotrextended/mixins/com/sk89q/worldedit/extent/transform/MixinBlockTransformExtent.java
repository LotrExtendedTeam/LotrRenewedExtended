package com.github.maximuslotro.lotrextended.mixins.com.sk89q.worldedit.extent.transform;

import java.util.List;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.sk89q.worldedit.extent.transform.BlockTransformExtent;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.registry.state.EnumProperty;
import com.sk89q.worldedit.registry.state.Property;
import com.sk89q.worldedit.util.Direction.Flag;
import com.sk89q.worldedit.world.block.BlockStateHolder;

import lotr.common.block.AxialSlabBlock;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

@Mixin(BlockTransformExtent.class)
public class MixinBlockTransformExtent {

	@Shadow(remap=false)
	private static Vector3 getNewStateValue(List<com.sk89q.worldedit.util.Direction> allowedStates, Transform transform, Vector3 oldDirection) { return null; }

	@Inject(method = "transform", at = @At("HEAD"), cancellable = true, remap = false)
	private static <B extends BlockStateHolder<B>> void injectAxialSlab(B block, Transform transform, CallbackInfoReturnable<B> cir) {
		if(!(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(block.getBlockType().getId())) instanceof AxialSlabBlock)) {
			return;
		}
		List<? extends Property<?>> properties = block.getBlockType().getProperties();		
		// Find axis and type properties
        EnumProperty axisProp = null;
        EnumProperty typeProp = null;

        for (Property<?> property : properties) {
            if (property instanceof EnumProperty) {
                if (property instanceof EnumProperty && property.getName().equals("axis")) {
                	axisProp = (EnumProperty) property;
                }
                else if (property instanceof EnumProperty && property.getName().equals("type")) {
                	typeProp = (EnumProperty) property;
                }
            }
        }

        if (axisProp == null || typeProp == null) {
        	return;
        }
 
        String typeStr = block.getState(typeProp);
        SlabType slabType = SlabType.valueOf(typeStr.toUpperCase());
		Axis axis = Axis.byName(block.getState(axisProp));
		// Renewed Start
		AxisDirection axisDir = slabType == SlabType.BOTTOM ? AxisDirection.NEGATIVE : AxisDirection.POSITIVE;
		Direction dir = Direction.fromAxisAndDirection(axis, axisDir);
		// Renewed End
		Vector3 newValue1 = getNewStateValue(com.sk89q.worldedit.util.Direction.valuesOf(Flag.UPRIGHT | Flag.CARDINAL), transform, adaptEnumFacing(dir).toVector());
		if (newValue1 == null) {
        	return;
		}
		com.sk89q.worldedit.util.Direction newDir = com.sk89q.worldedit.util.Direction.findClosest(newValue1, Flag.UPRIGHT | Flag.CARDINAL);
		Direction mcDir =adapt(newDir);
		// Renewed Start
		Axis rotatedAxis = mcDir.getAxis();
		AxisDirection rotatedAxisDir = mcDir.getAxisDirection();
		if (axisProp.getValues().contains(rotatedAxis.getName())) {
			SlabType rotatedSlabType = slabType == SlabType.DOUBLE ? slabType : 
				(rotatedAxisDir == AxisDirection.NEGATIVE ?  SlabType.BOTTOM : 
					(rotatedAxisDir == AxisDirection.POSITIVE ? SlabType.TOP : slabType));
			// Renewed End
			B result = block;
			result = result.with(axisProp, rotatedAxis.getName());
			result = result.with(typeProp, rotatedSlabType.getSerializedName());
			cir.setReturnValue(result); 
		} else {
			return;
		}
	}

	/**
	 * Adapts a WorldEdit {@link com.sk89q.worldedit.util.Direction} into this
	 * project's internal {@link Direction} enum.
	 * <p>
	 * These methods were copied from WorldEdit's Forge adapter implementation to
	 * remove the runtime dependency on the {@code worldedit-forge} mod. The Forge
	 * adapter depends on {@code worldedit-core}, but during IDE client/server runs
	 * only the Forge mod JAR is typically loaded, causing class loading failures
	 * and crashes.
	 * </p>
	 * <p>
	 * To avoid this issue, the project now depends only on
	 * {@code worldedit-core} at compile time and provides its own lightweight
	 * adapter methods.
	 * </p>
	 *
	 * @param face the WorldEdit direction to adapt
	 * @return the corresponding internal {@link Direction}
	 * @Unique
	 */
	@Unique
	private static Direction adapt(com.sk89q.worldedit.util.Direction face) {
		switch (face) {
			case NORTH :
				return Direction.NORTH;
			case SOUTH :
				return Direction.SOUTH;
			case WEST :
				return Direction.WEST;
			case EAST :
				return Direction.EAST;
			case DOWN :
				return Direction.DOWN;
			case UP :
			default :
				return Direction.UP;
		}
	}
	/**
	 * Adapts this project's internal {@link Direction} enum into a
	 * WorldEdit {@link com.sk89q.worldedit.util.Direction}.
	 * <p>
	 * This method exists to replace the equivalent adapter methods from
	 * {@code com.sk89q.worldedit.forge.ForgeAdapter} so the project does not
	 * require the {@code worldedit-forge} mod at runtime.
	 * </p>
	 *
	 * @param face the internal direction to adapt, or {@code null}
	 * @return the corresponding WorldEdit direction, or {@code null} if the input was {@code null}
	 * @Unique
	 */
	@Unique
	private static com.sk89q.worldedit.util.Direction adaptEnumFacing(@Nullable Direction face) {
		if (face == null) {
			return null;
		} else {
			switch (face) {
				case NORTH :
					return com.sk89q.worldedit.util.Direction.NORTH;
				case SOUTH :
					return com.sk89q.worldedit.util.Direction.SOUTH;
				case WEST :
					return com.sk89q.worldedit.util.Direction.WEST;
				case EAST :
					return com.sk89q.worldedit.util.Direction.EAST;
				case DOWN :
					return com.sk89q.worldedit.util.Direction.DOWN;
				case UP :
				default :
					return com.sk89q.worldedit.util.Direction.UP;
			}
		}
	}
}
