package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor;

import java.util.List;

import com.mojang.serialization.Codec;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
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
		/*
		Rotation rotation = placementSettings.getRotation();
		if (rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90) {
			if (nbt.contains("Rotation", 9)) {
				ListNBT rotationList = nbt.getList("Rotation", 5);
				float yaw = rotationList.getFloat(0);
				boolean needsFlip = false;
				// Initialize or fetch the ArmorItems list
			    ListNBT armorItems = nbt.contains("ArmorItems", 9) ? nbt.getList("ArmorItems", 10) : new ListNBT();
			    // Ensure the list has 4 elements so index 3 (head) is safe to modify
			    while (armorItems.size() < 4) {
			        armorItems.add(new CompoundNBT());
			    }
				if (rotation == Rotation.COUNTERCLOCKWISE_90) {
					if (yaw < 0 && yaw >= -180.0F) {
						needsFlip = true;
						ItemStack debugItem = new ItemStack(Items.DIAMOND_HELMET);
				        armorItems.set(3, debugItem.serializeNBT());
					}
				} else if (rotation == Rotation.CLOCKWISE_90) {
					if (yaw > 0.0F && yaw < 180.0F) {
						needsFlip = true;
						ItemStack debugItem = new ItemStack(Items.GOLDEN_HELMET);
				        armorItems.set(3, debugItem.serializeNBT());
					}
				}
				// Only 90 degree rotation of pieces seem to cause invalid rotations
				if (needsFlip) {
					float correctedYaw = MathHelper.wrapDegrees(yaw + 180.0F);
					rotationList.set(0, FloatNBT.valueOf(correctedYaw));
					// Write the modified equipment back to the root NBT
				    nbt.put("ArmorItems", armorItems);
				    return entityInfo;
				}
			}
		}*/
		if (nbt.contains("ForgeData", 10)) {
			CompoundNBT forgeData = nbt.getCompound("ForgeData");
			if (forgeData.contains("StandLootTable", 8) && world instanceof IServerWorld) {
				ServerWorld serverWorld = ((IServerWorld) world).getLevel();
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