package com.github.maximuslotro.lotrrextended.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class ExtendedRenderTypes extends RenderType {

    // Required constructor for protected RenderType members
    private ExtendedRenderTypes(String name, VertexFormat format, int drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
        super(name, format, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
        throw new IllegalStateException("This class must not be instantiated");
    }

    public static final RenderType THROUGH_WALL_QUADS = create("extended_quads", DefaultVertexFormats.POSITION_COLOR, GL11.GL_QUADS, 256, 
    	RenderType.State.builder().setDepthTestState(RenderState.NO_DEPTH_TEST).setWriteMaskState(RenderState.COLOR_WRITE).setCullState(RenderState.NO_CULL).setTransparencyState(RenderState.NO_TRANSPARENCY).createCompositeState(false)
    );
}