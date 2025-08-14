package com.github.maximuslotro.lotrrextended.client.render.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;

public class ExtendedAbstractPlaneRenderer {

	public static void renderCube(MatrixStack matStack, IVertexBuilder vb, TextureAtlasSprite icon, int uvMin,
		int uvMax, float x0, float y0, float z0, float x1, float y1, float z1, int light, int overlay) {

		// Top (+Y)
		renderFace(matStack, vb, icon, uvMin, uvMax, x0, y1, z0, x1, y1, z1, 0, 1, 0, light, overlay);

		// Bottom (-Y)
		renderFace(matStack, vb, icon, uvMin, uvMax, x0, y0, z1, x1, y0, z0, 0, -1, 0, light, overlay);

		// North (-Z)
		renderFace(matStack, vb, icon, uvMin, uvMax, x1, y0, z0, x0, y1, z0, 0, 0, -1, light, overlay);

		// South (+Z)
		renderFace(matStack, vb, icon, uvMin, uvMax, x0, y0, z1, x1, y1, z1, 0, 0, 1, light, overlay);

		// West (-X)
		renderFace(matStack, vb, icon, uvMin, uvMax, x0, y0, z1, x0, y1, z0, -1, 0, 0, light, overlay);

		// East (+X)
		//renderFace(matStack, vb, icon, uvMin, uvMax, x1, y0, z0, x1, y1, z1, 1, 0, 0, light, overlay);
	}

	public static void renderFaceUp(MatrixStack matStack, IVertexBuilder vb, TextureAtlasSprite icon,
		float uvMin, float uvMax, float x0, float z0, float x1, float z1, float y, int light, int overlay) {
		ExtendedAbstractPlaneRenderer.renderFace(matStack, vb, icon, uvMin, uvMax, x0, y, z0, x1, y, z1, 0, 1, 0, light, overlay);
	}

	public static void renderFace(MatrixStack matStack, IVertexBuilder vb, TextureAtlasSprite icon,
		float uvMin, float uvMax, float x0, float y0, float z0, float x1, float y1, float z1, float nx,
		float ny, float nz, int light, int overlay) {

		float minU = icon.getU(uvMin);
		float maxU = icon.getU(uvMax);
		float minV = icon.getV(uvMin);
		float maxV = icon.getV(uvMax);

		Matrix4f mat = matStack.last().pose();
		Matrix3f normal = matStack.last().normal();

		vb.vertex(mat, x0, y0, z1).color(255, 255, 255, 255).uv(minU, maxV).overlayCoords(overlay).uv2(light).normal(normal, nx, ny, nz).endVertex();
		vb.vertex(mat, x1, y0, z1).color(255, 255, 255, 255).uv(maxU, maxV).overlayCoords(overlay).uv2(light).normal(normal, nx, ny, nz).endVertex();
		vb.vertex(mat, x1, y1, z0).color(255, 255, 255, 255).uv(maxU, minV).overlayCoords(overlay).uv2(light).normal(normal, nx, ny, nz).endVertex();
		vb.vertex(mat, x0, y1, z0).color(255, 255, 255, 255).uv(minU, minV).overlayCoords(overlay).uv2(light).normal(normal, nx, ny, nz).endVertex();
	}
}
