package com.github.maximuslotro.lotrrextended.common.tileentity;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.Constants;

public class ExtendedBirdcageTileEntity extends TileEntity {

    private String birdType = "";

    public ExtendedBirdcageTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    public ExtendedBirdcageTileEntity() {
        super(ExtendedTileEntities.BIRD_CAGE.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);
        tag.putString("BirdType", birdType);
        return tag;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        birdType = tag.getString("BirdType");
    }

    public String getBird() {
        return birdType;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state, tag);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getBlockPos(), -1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager network, SUpdateTileEntityPacket packet) {
        load(getBlockState(), packet.getTag());
    }

    public void setBird(String bird) {
        if (birdType.equals(bird)) {
            return;
        }
        this.birdType = bird;
        setChanged();
        if (level != null && !level.isClientSide()) {
            BlockState state = getBlockState();
            level.sendBlockUpdated(getBlockPos(), state, state, Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

}
