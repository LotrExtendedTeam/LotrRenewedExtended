package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedSingleItemDataHolderIInventory;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedAncientItem extends Item {
	private final ResourceLocation dropTable;

	public ExtendedAncientItem(Properties pProperties, ResourceLocation pDropTable) {
		super(pProperties);
		this.dropTable = pDropTable;
	}

	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		ItemStack heldItem = pPlayer.getItemInHand(pHand);
		ItemStack discoveredItem = generateLootTable(pPlayer, pLevel);
		if (!pPlayer.abilities.instabuild) {
			heldItem.shrink(1);
		}
		pPlayer.awardStat(Stats.ITEM_USED.get(this));
		pLevel.playSound(null, pPlayer.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.PLAYERS, 1, MathHelper.nextFloat(pLevel.random, 0.75F, 0.95F));
		if (heldItem.isEmpty()) {
			return ActionResult.sidedSuccess(discoveredItem, pLevel.isClientSide());
		} else {
			if (!pPlayer.inventory.add(discoveredItem.copy())) {
				pPlayer.drop(discoveredItem, false);
			}
			return ActionResult.sidedSuccess(heldItem, pLevel.isClientSide());
		}
	}

	public ItemStack generateLootTable(PlayerEntity pPlayer, World pLevel) {
		if (this.dropTable != null && pLevel != null && pLevel.getServer() != null) {
			LootTable loottable = pLevel.getServer().getLootTables().get(dropTable);
			if (pPlayer instanceof ServerPlayerEntity) {
				CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayerEntity) pPlayer, dropTable);
			}
			LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) pLevel)).withParameter(LootParameters.ORIGIN, pPlayer.position());
			if (pPlayer != null) {
				lootcontext$builder.withLuck(pPlayer.getLuck()).withParameter(LootParameters.THIS_ENTITY, pPlayer);
			}
			ExtendedSingleItemDataHolderIInventory placeHolder = new ExtendedSingleItemDataHolderIInventory();
			loottable.fill(placeHolder, lootcontext$builder.create(LootParameterSets.CHEST));
			return placeHolder.getItem(0);
		}
		return ItemStack.EMPTY;
	}
}
