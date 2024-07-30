package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedEntitySpawnerItem extends Item{

	public ExtendedEntitySpawnerItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}
	 
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClientSide() && player.isCreative()) {
			ExtendedStructureSpawnerEntity conker = new ExtendedStructureSpawnerEntity(world);
			BlockPos setPos = new BlockPos(player.getX(), player.getEyeY(), player.getZ());
			conker.moveTo(setPos.relative(player.getDirection(), 1), 0.0F, 0.0F);
			world.addFreshEntity(conker);
		}
		ItemStack heldItem = player.getItemInHand(hand);
		world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.GLASS_PLACE, SoundCategory.NEUTRAL, 0.7F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		player.awardStat(Stats.ITEM_USED.get(this));
		return ActionResult.sidedSuccess(heldItem, world.isClientSide());
	}

	 
	@Override
	public ActionResultType useOn(ItemUseContext pContext) {
		ActionResultType actionresulttype = this.place(new BlockItemUseContext(pContext));
	    return !actionresulttype.consumesAction() ? this.use(pContext.getLevel(), pContext.getPlayer(), pContext.getHand()).getResult() : actionresulttype;
	}

	private ActionResultType place(BlockItemUseContext pContext) {
		if (!pContext.canPlace()) {
			return ActionResultType.FAIL;
		} else {
			BlockPos blockpos = pContext.getClickedPos();
			World world = pContext.getLevel();
			PlayerEntity playerentity = pContext.getPlayer();
			if (!pContext.getLevel().isClientSide() && playerentity.isCreative()) {
				ExtendedStructureSpawnerEntity conker = new ExtendedStructureSpawnerEntity(world);
				conker.moveTo(blockpos.getX()+0.5D, blockpos.getY(), blockpos.getZ()+0.5D, 0.0F, 0.0F);
				world.addFreshEntity(conker);
			}
			world.playSound(playerentity, blockpos, SoundEvents.GLASS_PLACE, SoundCategory.NEUTRAL, 0.7F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
			playerentity.awardStat(Stats.ITEM_USED.get(this));
			ItemStack itemstack = pContext.getItemInHand();
			if (playerentity == null || !playerentity.abilities.instabuild) {
				itemstack.shrink(1);
			}
			return ActionResultType.sidedSuccess(world.isClientSide());
		}
	}

	   @OnlyIn(Dist.CLIENT)
	   public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		   super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
		   if (pStack.hasTag() && pStack.getTag().contains("setSpawnerType")) {
			   pTooltip.add(new StringTextComponent("Spawner Type: "+pStack.getTag().getString("setSpawnerType")));
		   }
	   }
}