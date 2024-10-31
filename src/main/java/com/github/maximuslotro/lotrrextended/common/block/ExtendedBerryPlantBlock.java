package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import lotr.common.init.LOTRBlocks;

public class ExtendedBerryPlantBlock extends SweetBerryBushBlock {
    private final RegistryObject<Item> berryItem;

    public ExtendedBerryPlantBlock(RegistryObject<Item> berryItem) {
    	super(Properties.of(Material.PLANT, MaterialColor.COLOR_GREEN)
    			.randomTicks()
    			.sound(SoundType.SWEET_BERRY_BUSH)
    			.harvestTool(ToolType.HOE)
    			.noCollission()
    			.noOcclusion()
    			.isRedstoneConductor(LOTRBlocks::posPredicateFalse)
    			.isSuffocating(LOTRBlocks::posPredicateFalse)
    			.isViewBlocking(LOTRBlocks::posPredicateFalse));
        this.berryItem = berryItem;
        registerDefaultState(stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(berryItem.get());
     }
    @Override
    protected boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
    	if(p_200014_1_.getBlock() instanceof ExtendedBerryBushBlock || p_200014_1_.getBlock() instanceof ExtendedGroundFarmlandBlock) {
    		return true;
    	}
    	return super.mayPlaceOn(p_200014_1_, p_200014_2_, p_200014_3_);
     }

    @Override
    public void entityInside(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {}

    @Override
    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        int i = p_225533_1_.getValue(AGE);
        boolean flag = i == 3;
        if (!flag && p_225533_4_.getItemInHand(p_225533_5_).getItem() == Items.BONE_MEAL) {
           return ActionResultType.PASS;
        } else if (i > 2) {
           popResource(p_225533_2_, p_225533_3_, new ItemStack(berryItem.get(), flag ? p_225533_2_.random.nextInt(3) + 1 : 0));
           p_225533_2_.playSound((PlayerEntity)null, p_225533_3_, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + p_225533_2_.random.nextFloat() * 0.4F);
           p_225533_2_.setBlock(p_225533_3_, p_225533_1_.setValue(AGE, Integer.valueOf(1)), 2);
           return ActionResultType.sidedSuccess(p_225533_2_.isClientSide);
        } else {
           return ActionResultType.PASS;
        }
     }

    public Item getBerryItem() {
		return berryItem.get();
    	
    }
}
