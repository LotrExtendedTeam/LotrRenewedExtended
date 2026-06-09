package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedStructureProcessors;
import com.mojang.serialization.Codec;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;

public class ExtendedArmorStandProcessor extends StructureProcessor {
	/** Singleton instance of the processor. */
	public static final ExtendedArmorStandProcessor INSTANCE = new ExtendedArmorStandProcessor();
	/** Codec for serialization and deserialization of this processor. */
	public static final Codec<ExtendedArmorStandProcessor> CODEC = Codec.unit(() -> INSTANCE);

	/** Private constructor to enforce singleton usage. */
	private ExtendedArmorStandProcessor() {}

	@Override
	public Template.EntityInfo processEntity(IWorldReader world, BlockPos seedPos, Template.EntityInfo rawEntityInfo, Template.EntityInfo entityInfo, PlacementSettings placementSettings, Template template) {
		CompoundNBT nbt = entityInfo.nbt;
		String entityId = nbt.getString("id");

		// Only process if the entity is an armor stand
		if (!"minecraft:armor_stand".equals(entityId)) {
			return entityInfo;
		}

		Rotation rotation = placementSettings.getRotation();
		Mirror mirror = placementSettings.getMirror();
		if (nbt.contains("Rotation", 9)) {
			ListNBT rotationList = nbt.getList("Rotation", 5);
			float yaw = rotationList.getFloat(0);
			int yawSteps = Math.floorMod(Math.round(yaw), 360);
			yawSteps = mirror.mirror(yawSteps, 360);
			yawSteps = rotation.rotate(yawSteps, 360);
			rotationList.set(0, FloatNBT.valueOf(yawSteps));
		}

		if (nbt.contains("ForgeData", 10)) {
			CompoundNBT forgeData = nbt.getCompound("ForgeData");

			if (forgeData.contains("StandLootTable", 8) && world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				String lootTableStr = forgeData.getString("StandLootTable");
				ResourceLocation lootTableId = new ResourceLocation(forgeData.getString("StandLootTable"));
				forgeData.remove("StandLootTable");
				LootTable lootTable = serverWorld.getServer().getLootTables().get(lootTableId);
				LootContext context = new LootContext.Builder(serverWorld).withRandom(serverWorld.random).create(LootParameterSets.EMPTY);
				List<ItemStack> generatedItems = lootTable.getRandomItems(context);
				NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
				//NonNullList<ItemStack> handItems = NonNullList.withSize(4, ItemStack.EMPTY);
				for (ItemStack stack : generatedItems) {
					if (stack.isEmpty()) continue;
					if (stack.getItem() instanceof ArmorItem) {
						armorItems.set(((ArmorItem)stack.getItem()).getSlot().getIndex(), stack);
					}
					//TODO handle items
				}
				ListNBT armorList = new ListNBT();
				for(ItemStack itemstack : armorItems) {
					CompoundNBT itemNbt = new CompoundNBT();
					if (!itemstack.isEmpty()) {
						itemstack.save(itemNbt);
					}
					armorList.add(itemNbt);
				}
				nbt.put("ArmorItems", armorList);
				/*
				ListNBT handList = new ListNBT();
				for(ItemStack itemstack : handItems) {
					CompoundNBT itemNbt = new CompoundNBT();
					if (!itemstack.isEmpty()) {
						itemstack.save(itemNbt);
					}
					handList.add(itemNbt);
				}
				nbt.put("HandItems", handList);
				 */
			}
		}
		return entityInfo;
	}

	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.ARMOR_STAND_PROCESSOR;
	}
}