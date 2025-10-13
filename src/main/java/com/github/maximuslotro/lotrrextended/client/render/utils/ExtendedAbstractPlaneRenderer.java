package com.github.maximuslotro.lotrrextended.client.render.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;

public class ExtendedAbstractPlaneRenderer {

	public static void renderFaceUp(MatrixStack ms, IVertexBuilder vb, TextureAtlasSprite icon, float uvMin, float uvMax, float x0, float z0, float x1, float z1, float y, int light, int overlay) {
		faceQuad(ms, vb, icon, uvMin, uvMax, x0, y, z1, x1, y, z1, x1, y, z0, x0, y, z0, 0, 1, 0, light, overlay);
	}

	public static void renderFaceDown(MatrixStack ms, IVertexBuilder vb, TextureAtlasSprite icon, float uvMin, float uvMax, float x0, float z0, float x1, float z1, float y, int light, int overlay) {
		faceQuad(ms, vb, icon, uvMin, uvMax, x0, y, z0, x1, y, z0, x1, y, z1, x0, y, z1, 0, -1, 0, light, overlay);
	}

	public static void renderFaceNorth(MatrixStack ms, IVertexBuilder vb, TextureAtlasSprite icon, float uvMin, float uvMax, float x0, float y0, float x1, float y1, float z, int light, int overlay) {
		faceQuad(ms, vb, icon, uvMin, uvMax, x1, y0, z, x0, y0, z, x0, y1, z, x1, y1, z, 0, 0, -1, light, overlay);
	}

	public static void renderFaceSouth(MatrixStack ms, IVertexBuilder vb, TextureAtlasSprite icon, float uvMin, float uvMax, float x0, float y0, float x1, float y1, float z, int light, int overlay) {
		faceQuad(ms, vb, icon, uvMin, uvMax, x0, y0, z, x1, y0, z, x1, y1, z, x0, y1, z, 0, 0, 1, light, overlay);
	}

	public static void renderFaceWest(MatrixStack ms, IVertexBuilder vb, TextureAtlasSprite icon, float uvMin, float uvMax, float z0, float y0, float z1, float y1, float x, int light, int overlay) {
		faceQuad(ms, vb, icon, uvMin, uvMax, x, y0, z0, x, y0, z1, x, y1, z1, x, y1, z0, -1, 0, 0, light, overlay);
	}

	public static void renderFaceEast(MatrixStack ms, IVertexBuilder vb, TextureAtlasSprite icon, float uvMin, float uvMax, float z0, float y0, float z1, float y1, float x, int light, int overlay) {
		faceQuad(ms, vb, icon, uvMin, uvMax, x, y0, z1, x, y0, z0, x, y1, z0, x, y1, z1, 1, 0, 0, light, overlay);
	}

	private static void faceQuad(MatrixStack ms, IVertexBuilder vb, TextureAtlasSprite icon, float uvMin, float uvMax, float ax, float ay, float az, float bx, float by, float bz, float cx, float cy, float cz, float dx, float dy, float dz, float nx, float ny, float nz, int light, int overlay) {

		Matrix4f mat = ms.last().pose();
		Matrix3f normal = ms.last().normal();

		float minU = icon.getU(uvMin), maxU = icon.getU(uvMax);
		float minV = icon.getV(uvMin), maxV = icon.getV(uvMax);
	
		quad(mat, normal, vb, minU, maxU, minV, maxV, ax, ay, az, bx, by, bz, cx, cy, cz, dx, dy, dz, nx, ny, nz, light, overlay);
	}

	public static void renderCube(MatrixStack ms, IVertexBuilder vb, TextureAtlasSprite icon, int uvMin, int uvMax, float x0, float y0, float z0, float x1, float y1, float z1, int light, int overlay) {

		// normalize just in case inputs are swapped
		float minX = Math.min(x0, x1), maxX = Math.max(x0, x1);
		float minY = Math.min(y0, y1), maxY = Math.max(y0, y1);
		float minZ = Math.min(z0, z1), maxZ = Math.max(z0, z1);

		Matrix4f pos = ms.last().pose();
		Matrix3f normal = ms.last().normal();

		float minU = icon.getU(uvMin), maxU = icon.getU(uvMax);
		float minV = icon.getV(uvMin), maxV = icon.getV(uvMax);

		// Top (+Y)
		quad(pos, normal, vb, minU, maxU, minV, maxV, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, maxY, minZ, minX, maxY, minZ, 0, 1, 0, light, overlay);
		// Bottom (-Y)
		quad(pos, normal, vb, minU, maxU, minV, maxV, minX, minY, minZ, maxX, minY, minZ, maxX, minY, maxZ, minX, minY, maxZ, 0, -1, 0, light, overlay);
		// South (+Z)
		quad(pos, normal, vb, minU, maxU, minV, maxV, minX, minY, maxZ, maxX, minY, maxZ, maxX, maxY, maxZ, minX, maxY, maxZ, 0, 0, 1, light, overlay);
		// North (-Z)
		quad(pos, normal, vb, minU, maxU, minV, maxV, maxX, minY, minZ, minX, minY, minZ, minX, maxY, minZ, maxX, maxY, minZ, 0, 0, -1, light, overlay);
		// East (+X)
		quad(pos, normal, vb, minU, maxU, minV, maxV, maxX, minY, maxZ, maxX, minY, minZ, maxX, maxY, minZ, maxX, maxY, maxZ, 1, 0, 0, light, overlay);
		// West (-X)
		quad(pos, normal, vb, minU, maxU, minV, maxV, minX, minY, minZ, minX, minY, maxZ, minX, maxY, maxZ, minX, maxY, minZ, -1, 0, 0, light, overlay);
	}

	private static void quad(Matrix4f pos, Matrix3f normal, IVertexBuilder vb, float minU, float maxU, float minV, float maxV, float ax, float ay, float az, float bx, float by, float bz, float cx, float cy, float cz, float dx, float dy, float dz, float nx, float ny, float nz, int light, int overlay) {
		// CCW order, outward-facing
		vb.vertex(pos, ax, ay, az).color(255, 255, 255, 255).uv(minU, maxV).overlayCoords(overlay).uv2(light).normal(normal, nx, ny, nz).endVertex();
		vb.vertex(pos, bx, by, bz).color(255, 255, 255, 255).uv(maxU, maxV).overlayCoords(overlay).uv2(light).normal(normal, nx, ny, nz).endVertex();
		vb.vertex(pos, cx, cy, cz).color(255, 255, 255, 255).uv(maxU, minV).overlayCoords(overlay).uv2(light).normal(normal, nx, ny, nz).endVertex();
		vb.vertex(pos, dx, dy, dz).color(255, 255, 255, 255).uv(minU, minV).overlayCoords(overlay).uv2(light).normal(normal, nx, ny, nz).endVertex();
	}
}
