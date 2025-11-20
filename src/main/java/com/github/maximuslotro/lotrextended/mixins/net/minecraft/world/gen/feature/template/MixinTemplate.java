package com.github.maximuslotro.lotrextended.mixins.net.minecraft.world.gen.feature.template;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrextended.mixins.net.minecraft.tileentity.MixinSignTileEntityAccessor;
import com.github.maximuslotro.lotrrextended.common.signprocessors.ExtendedMessageEntryBasic;
import com.github.maximuslotro.lotrrextended.common.signprocessors.ExtendedSignTextList;
import com.github.maximuslotro.lotrrextended.common.signprocessors.ExtendedSignTextProcessorManager;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;
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
 * A Mixin targeting {@link Template} to customize structure placement behavior.
 * <p>
 * Specifically, this mixin enables random or dynamic sign text to be injected
 * into signs placed by structures at generation time.
 * </p>
 * @Mixin (Template.class)
 */
@Mixin(Template.class)
public class MixinTemplate {

	/**
	 * Handles tile entity customization during structure placement, with a focus
	 * on modifying {@link SignTileEntity} and {@link ExtendedStoneTextSignTileEntity}
	 * contents.
	 * <p>
	 * This injection runs immediately after vanilla applies mirroring and rotation
	 * to tile entities, ensuring the correct orientation of text and structure elements
	 * before replacing sign contents.
	 * </p>
	 *
	 * <h3>Behavior:</h3>
	 * <ul>
	 *   <li>If the tile entity is a standard {@link SignTileEntity}:
	 *     <ul>
	 *       <li>Check if the first line of text begins with {@code #}, marking it as a placeholder key.</li>
	 *       <li>Look up the key in {@link ExtendedSignTextProcessorManager}.</li>
	 *       <li>If a valid {@link ExtendedSignTextList} is found, replace the sign’s text
	 *           with randomly selected lines from the list.</li>
	 *     </ul>
	 *   </li>
	 *   <li>If the tile entity is a custom {@link ExtendedStoneTextSignTileEntity}:
	 *     <ul>
	 *       <li>Performs the same placeholder check and replacement, but fills an 8-line sign layout, 
	 *           leaving blank lines for spacing.</li>
	 *     </ul>
	 *   </li>
	 * </ul>
	 *
	 * <h3>Randomization:</h3>
	 * <ul>
	 *   <li>Each replacement is gated by a random roll: {@code pRandom.nextInt(10000) < 9999}, 
	 *       which means ~99.99% of eligible signs are replaced, leaving a tiny chance of preserving the placeholder.</li>
	 *   <li>Text may either come from a full entry ({@link ExtendedMessageEntryBasic}) or be assembled line-by-line.</li>
	 * </ul>
	 *
	 * @reason Uses a local-variable-capturing injection to gain access to the
	 *         tile entity after vanilla rotation/mirroring is applied, ensuring
	 *         correct formatting of randomized sign text.
	 *
	 * @param pServerLevel      the server-level world where placement occurs
	 * @param posStart          the starting position for the structure
	 * @param posEnd            the ending position for the structure
	 * @param pSettings         placement settings (rotation, mirroring, etc.)
	 * @param pRandom           random instance for randomized text substitution
	 * @param pFlags            block placement flags
	 * @param ci                callback returnable from injection
	 * @param list              list of block info entries for placement
	 * @param mutableboundingbox bounding box being updated
	 * @param list1             special positions for post-processing
	 * @param list2             tile entity NBT data to be applied
	 * @param i                 loop variable (X coord)
	 * @param j                 loop variable (Y coord)
	 * @param k                 loop variable (Z coord)
	 * @param l                 loop variable (X1 coord)
	 * @param i1                loop variable (Y1 coord)
	 * @param j1                loop variable (Z1 coord)
	 * @param var17             iterator over block info entries
	 * @param template$blockinfo current block info entry
	 * @param blockpos          block position being processed
	 * @param fluidstate        fluid state at this position
	 * @param blockstate        block state at this position
	 * @param tileentity1       tile entity being processed (signs are intercepted here)
	 * @Inject (method = "placeInWorld(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/template/PlacementSettings;Ljava/util/Random;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntity;rotate(Lnet/minecraft/util/Rotation;)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	 */
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
					if(!newLine.equals(ExtendedSignTextList.EMPTY) && pRandom.nextInt(10000)<9999) {
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
		}else if (tileentity1 instanceof ExtendedStoneTextSignTileEntity) {
			ITextComponent[] messages = ((ExtendedStoneTextSignTileEntity)tileentity1).getMessages();
			if(messages[0] instanceof StringTextComponent && !((StringTextComponent) messages[0]).equals(StringTextComponent.EMPTY)) {
				String contents = ((StringTextComponent) messages[0]).getContents();
				if(contents.startsWith("#")) {
					ExtendedSignTextList newLine = ExtendedSignTextProcessorManager.INSTANCE.get(contents.substring(1).trim());
					if(!newLine.equals(ExtendedSignTextList.EMPTY) && pRandom.nextInt(10000)<9999) {
						if(newLine.hasLines()) {
							ExtendedMessageEntryBasic fullLine = newLine.getLine(pRandom);
							messages[0] = new StringTextComponent(fullLine.getLine0());
							messages[1] = new StringTextComponent("");
							messages[2] = new StringTextComponent(fullLine.getLine1());
							messages[3] = new StringTextComponent("");
							messages[4] = new StringTextComponent(fullLine.getLine2());
							messages[5] = new StringTextComponent("");
							messages[6] = new StringTextComponent(fullLine.getLine3());
							messages[7] = new StringTextComponent("");
						}else {
							messages[0] = new StringTextComponent(newLine.getLine0(pRandom));
							messages[1] = new StringTextComponent("");
							messages[2] = new StringTextComponent(newLine.getLine1(pRandom));
							messages[3] = new StringTextComponent("");
							messages[4] = new StringTextComponent(newLine.getLine2(pRandom));
							messages[5] = new StringTextComponent("");
							messages[6] = new StringTextComponent(newLine.getLine3(pRandom));
							messages[7] = new StringTextComponent("");
						}
						((ExtendedStoneTextSignTileEntity)tileentity1).setMessages(messages);
					}
				}
			}
		}
	}
}
