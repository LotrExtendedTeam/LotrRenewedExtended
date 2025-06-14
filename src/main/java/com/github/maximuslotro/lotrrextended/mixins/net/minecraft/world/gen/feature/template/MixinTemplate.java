package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.world.gen.feature.template;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.signprocessors.ExtendedMessageEntryBasic;
import com.github.maximuslotro.lotrrextended.common.signprocessors.ExtendedSignTextList;
import com.github.maximuslotro.lotrrextended.common.signprocessors.ExtendedSignTextProcessorManager;
import com.github.maximuslotro.lotrrextended.mixins.net.minecraft.tileentity.MixinSignTileEntityAccessor;
import com.mojang.datafixers.util.Pair;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.Template.BlockInfo;

/**
 * Handle random sign text in structures on generate
 * @Mixin (Template.class)
 * */
@Mixin(Template.class)
public class MixinTemplate {

	/**
	 * @reason Use wacky injection with locals to add tile entity callback after mirror and rotate parms
	 * @Inject (method = "placeInWorld(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/template/PlacementSettings;Ljava/util/Random;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntity;rotate(Lnet/minecraft/util/Rotation;)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	 * @param pServerLevel The server-level world where placement occurs
	 * @param posStart The starting position for the structure
	 * @param posEnd The ending position for the structure
	 * @param pSettings Placement settings, including rotation, mirroring, etc.
	 * @param pRandom A random instance for randomized elements like sign text
	 * @param pFlags Flags used during block placement
	 * @param ci The callback returnable used by the injector
	 * @param list The list of block info entries to place
	 * @param mutableboundingbox The bounding box that is being updated during placement
	 * @param list1 A list of positions, possibly for special block types or post-processing
	 * @param list2 A list of tile entities and their associated NBT data to apply after placement
	 * @param i X coordinate value used during iteration
	 * @param j Y coordinate value used during iteration
	 * @param k Z coordinate value used during iteration
	 * @param l X1 coordinate value used during iteration
	 * @param i1 Y1 coordinate value used during iteration
	 * @param j1 Z1 coordinate value used during iteration
	 * @param var17 Iterator over block info entries
	 * @param template$blockinfo A single block info being processed
	 * @param blockpos The current position of the block being processed
	 * @param fluidstate The fluid state at the block position
	 * @param blockstate The block state at the position
	 * @param tileentity1 The tile entity at the block position, if present
	 * */
	@Inject(method = "placeInWorld(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;"
		+ "Lnet/minecraft/world/gen/feature/template/PlacementSettings;Ljava/util/Random;I)Z", 
		at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntity;rotate(Lnet/minecraft/util/Rotation;)V", shift = At.Shift.AFTER),
		locals = LocalCapture.CAPTURE_FAILHARD)
	public void handleTileEntities(IServerWorld pServerLevel, BlockPos posStart, BlockPos posEnd, PlacementSettings pSettings, Random pRandom, int pFlags, CallbackInfoReturnable<Boolean> ci,
		List<Template.BlockInfo> list, MutableBoundingBox mutableboundingbox, List<BlockPos> list1, List<Pair<BlockPos, CompoundNBT>> list2, int i, int j, int k, int l, int i1, int j1,
		Iterator<BlockInfo> var17, BlockInfo template$blockinfo, BlockPos blockpos, FluidState fluidstate, BlockState blockstate, TileEntity tileentity1) {
		if (tileentity1 instanceof SignTileEntity) {
			ITextComponent[] messages = ((MixinSignTileEntityAccessor)tileentity1).getMessages();
			if(messages[0] instanceof StringTextComponent && !((StringTextComponent) messages[0]).equals(StringTextComponent.EMPTY)) {
				String contents = ((StringTextComponent) messages[0]).getContents();
				if(contents.startsWith("#")) {
					ExtendedSignTextList newLine = ExtendedSignTextProcessorManager.INSTANCE.get(contents.substring(1).trim());
					if(!newLine.equals(ExtendedSignTextList.EMPTY)) {
						if(newLine.hasLines()) {
							ExtendedMessageEntryBasic fullLine = newLine.getLine(pRandom);
							messages[0] = new StringTextComponent(fullLine.getLine0());
							messages[1] = new StringTextComponent(fullLine.getLine1());
							messages[2] = new StringTextComponent(fullLine.getLine2());
							messages[3] = new StringTextComponent(fullLine.getLine3());
						}else {
							messages[0] = new StringTextComponent(newLine.getLine0(pRandom));
							messages[1] = new StringTextComponent(newLine.getLine1(pRandom));
							messages[2] = new StringTextComponent(newLine.getLine2(pRandom));
							messages[3] = new StringTextComponent(newLine.getLine3(pRandom));
						}
						((MixinSignTileEntityAccessor)tileentity1).setMessages(messages);
					}
				}
			}
		}
	}
}
