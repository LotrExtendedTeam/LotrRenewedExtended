package com.github.maximuslotro.lotrrextended.common.tileentity;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTileEntities;
import lotr.common.init.ExtendedEntities;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ExtendedBirdcageTileEntity extends TileEntity {

    private Entity birdEntity = null;

    public ExtendedBirdcageTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    public ExtendedBirdcageTileEntity() {
        super(ExtendedTileEntities.BIRD_CAGE.get());
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public Entity getOrCreateBirdEntity() {
        if (this.birdEntity == null) {
            this.birdEntity = ExtendedEntities.CRABAN.get().create(this.getLevel());
        }

        return this.birdEntity;
    }
}
