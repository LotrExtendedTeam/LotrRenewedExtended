package com.github.maximuslotro.lotrrextended.common.bannerprotection;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerWallBlock;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerProtectionLevels;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ExtendedBannerProtectionDetector {
    public PlayerEntity player;
    public BlockPos bannerPos;
    ExtendedBannerProtectionLevels range;

    public static boolean isPlayerNearAnyBannerProtection(PlayerEntity player, ExtendedBannerProtectionLevels range) {
        World world = player.level;
        BlockPos playerPos = player.blockPosition();
        int radius = range.getProtectionRadius();

        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        for (int x = playerPos.getX() - radius; x <= playerPos.getX() + radius; x++) {
            for (int y = playerPos.getY() - radius; y <= playerPos.getY() + radius; y++) {
                for (int z = playerPos.getZ() - radius; z <= playerPos.getZ() + radius; z++) {
                    mutablePos.set(x, y, z);
                    Block block = world.getBlockState(mutablePos).getBlock();

                    if (block instanceof ExtendedMEBannerBlock || block instanceof ExtendedMEBannerWallBlock) {
                        int dx = playerPos.getX() - x;
                        int dy = playerPos.getY() - y;
                        int dz = playerPos.getZ() - z;

                        if (Math.abs(dx) <= radius && Math.abs(dy) <= radius && Math.abs(dz) <= radius) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }



    @Nullable
    public static ExtendedBannerTypes getNearbyBannerType(PlayerEntity player, ExtendedBannerProtectionLevels range) {
        World world = player.level;
        BlockPos playerPos = player.blockPosition();
        int radius = range.getProtectionRadius();

        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        for (int x = playerPos.getX() - radius; x <= playerPos.getX() + radius; x++) {
            for (int y = playerPos.getY() - radius; y <= playerPos.getY() + radius; y++) {
                for (int z = playerPos.getZ() - radius; z <= playerPos.getZ() + radius; z++) {
                    mutablePos.set(x, y, z);
                    Block block = world.getBlockState(mutablePos).getBlock();

                    for (ExtendedBannerTypes type : ExtendedBannerTypes.values()) {
                        Block standing = type.getStandingBlock().get();
                        Block hanging = type.getHangingBlock().get();

                        if (block == standing || block == hanging) {
                            return type;
                        }
                    }
                }
            }
        }

        return null;
    }

}
