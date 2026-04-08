package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config;

import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedWallsFeatureConfig;

public class ExtendedStructureSettings {

    public static final ExtendedStructureSettings EMPTY = new Builder().build();

    private final ExtendedWallsFeatureConfig wallsConfig;

    private ExtendedStructureSettings(Builder builder) {
        this.wallsConfig = builder.wallsConfig;
    }

    public ExtendedWallsFeatureConfig getWallsConfig() {
        return wallsConfig;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ExtendedWallsFeatureConfig wallsConfig;

        private Builder() {}

        public Builder walls(ExtendedWallsFeatureConfig config) {
            this.wallsConfig = config;
            return this;
        }

        public ExtendedStructureSettings build() {
            return new ExtendedStructureSettings(this);
        }
    }
}