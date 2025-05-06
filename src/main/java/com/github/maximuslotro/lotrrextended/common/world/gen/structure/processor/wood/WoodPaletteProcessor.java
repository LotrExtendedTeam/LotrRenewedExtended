package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.wood;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedStructureProcessors;
import com.github.maximuslotro.lotrrextended.common.utils.BlockStateUtils;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lotr.common.block.LOTRBlockStates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.registries.ForgeRegistries;

public class WoodPaletteProcessor extends StructureProcessor{
	public static final Codec<WoodPaletteProcessor> CODEC = RecordCodecBuilder.create((processorInstance) -> processorInstance.group(Codec.STRING.fieldOf("built_pallet").forGetter(WoodPaletteProcessor::getBasePalletName),
            Codec.STRING.fieldOf("picked_pallet").orElse("null").forGetter(WoodPaletteProcessor::getPickedPalletName),
            Codec.mapPair(Codec.STRING.fieldOf("wood_type"), Codec.INT.fieldOf("weight")).codec().listOf().promotePartial(Util.prefix("Pool element: ", ExtendedLog::error))
            .fieldOf("woodTypes").forGetter(WoodPaletteProcessor::getRawPallets)).apply(processorInstance, WoodPaletteProcessor::new));
	private WoodPallet pickedPallet;
	private final WoodPallet basePallet;
	private final List<Pair<String, Integer>> rawPallets;
	public final List<String> pallets;
	private boolean flaged = false;
	private boolean applied = false;
	public WoodPaletteProcessor(String defaultName, String selectedpallet, List<Pair<String, Integer>> rawPallets) {
		this.rawPallets = rawPallets;
		this.pallets = Lists.newArrayList();

		for(Pair<String, Integer> pair : rawPallets) {
			String pallet = pair.getFirst();
			for(int i = 0; i < pair.getSecond(); ++i) {
				this.pallets.add(pallet);
			}
		}
		if(!selectedpallet.equals("null")) {
			pickedPallet = WoodPallets.getPalletForname(selectedpallet);
		}
		basePallet = WoodPallets.getPalletForname(defaultName);
	}

	public boolean isInitialized() {
		return pickedPallet!=null;
	}

	private WoodPallet getRandomTemplate(Random pRandom) {
		return WoodPallets.getPalletForname(this.pallets.get(pRandom.nextInt(this.pallets.size())));
	}

	public void generateAndSetPallet(Random random) {
		pickedPallet = this.getRandomTemplate(random);
	}

	@Override
	public Template.BlockInfo process(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoWorld, PlacementSettings placmentData, @Nullable Template template) {
		if(!isInitialized()) {
			ExtendedLog.warn("Needed to set WoodPallet on BlockProcess, this should not happen! Please report this and what you did!");
			generateAndSetPallet(placmentData.getRandom(blockInfoWorld.pos));
			if(!flaged) {
				IllegalStateException e =  new IllegalStateException("WoodPaller set on runTime!");
				e.printStackTrace();
				flaged = true;
			}
		}
		if(!applied) {
			ExtendedLog.infoHideable("Replacing "+basePallet.getName()+" pallet with "+pickedPallet.getName(), ExtendedServerConfig.enableExtendedLogging.get());
			applied = true;
		}
		if(pickedPallet.isEmpty() || pickedPallet.equals(basePallet)) {
			return blockInfoWorld;
		}
		BlockState blockstate = blockInfoWorld.state;
		if(blockstate.getBlock().equals(Blocks.AIR)) {
			return blockInfoWorld;
		}
		if(basePallet.getBlockMap().containsValue(blockstate.getBlock().getRegistryName())) {
			String key = getKey(basePallet.getBlockMap(), blockstate.getBlock().getRegistryName());
			Block pickedBlock = ForgeRegistries.BLOCKS.getValue(pickedPallet.getBlockMap().get(key));
			//Handle Vanilla Spoofed Vertical Slabs selecting non-vanilla vertical slabs
			if("SLAB_VERT".equals(key) && pickedBlock.equals(Blocks.AIR)) {
				Block newPickedBlock = ForgeRegistries.BLOCKS.getValue(pickedPallet.getBlockMap().get("SLAB"));
				if(pickedBlock.equals(Blocks.AIR)) {
					return blockInfoWorld;
				}
				pickedBlock=newPickedBlock;
			}
			//Handle vertical slabs picking slab
			else if("SLAB".equals(key) && blockstate.hasProperty(LOTRBlockStates.SLAB_AXIS) && blockstate.getValue(LOTRBlockStates.SLAB_AXIS)!=Axis.Y) {
				Block newPickedBlock = ForgeRegistries.BLOCKS.getValue(pickedPallet.getBlockMap().get("SLAB_VERT"));
				if(pickedBlock.equals(Blocks.AIR)) {
					return blockInfoWorld;
				}
				//Replace picked vanilla slab with its special vertical version
				pickedBlock=newPickedBlock;
			}
			//Handle Missing Block Instances
			if(pickedBlock==null) {
				ExtendedLog.error("Found Missing Block in WoodPallet: "+pickedPallet.getName()+", KEY: "+key);
				return blockInfoWorld;
			}
			//ExtendedLog.info("Replaced "+blockstate.getBlock().getRegistryName().toString() +" with"+ pickedBlock.getRegistryName().toString());
			return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(pickedBlock, blockstate), null);
		}
		return blockInfoWorld;
	}

	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.WOOD_PALETTE_PROCESSOR;
	}

	@Nullable
	public WoodPallet getPickedPallet() {
		return pickedPallet;
	}

	public String getPickedPalletName() {
		if(!isInitialized()) {
			return "null";
		}
		return pickedPallet.getName();
	}

	public WoodPallet getBasePallet() {
		return basePallet;
	}

	public String getBasePalletName() {
		return basePallet.getName();
	}

	private <K, V> K getKey(Map<K, V> map, V value) {
	    for (Entry<K, V> entry : map.entrySet()) {
	        if (entry.getValue().equals(value)) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}

	public List<Pair<String, Integer>> getRawPallets() {
		return rawPallets;
	}
}