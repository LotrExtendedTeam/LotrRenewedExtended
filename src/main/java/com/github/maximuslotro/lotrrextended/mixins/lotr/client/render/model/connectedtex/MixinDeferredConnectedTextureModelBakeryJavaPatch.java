package com.github.maximuslotro.lotrrextended.mixins.lotr.client.render.model.connectedtex;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lotr.client.render.model.BlockModelQuadsHolder;
import lotr.client.render.model.DynamicTextureRepository;
import lotr.client.render.model.connectedtex.ConnectedTexture3DContext;
import lotr.client.render.model.connectedtex.ConnectedTextureElement;
import lotr.client.render.model.connectedtex.ConnectedTextureUnbakedModel;
import lotr.client.render.model.connectedtex.TextureConnectionProperties;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.model.BlockPartFace;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;

@Mixin(ConnectedTextureUnbakedModel.DeferredConnectedTextureModelBakery.class)
public class MixinDeferredConnectedTextureModelBakeryJavaPatch {

	@Shadow(remap=false)
	@Final
	public IModelConfiguration owner;

	@Shadow(remap=false)
	@Final
	public Function<RenderMaterial, TextureAtlasSprite> spriteGetter;

	@Shadow(remap=false)
	@Final
	public IModelTransform modelTransform;

	@Shadow(remap=false)
	@Final
	public ResourceLocation modelLocation;

	@Shadow(remap=false)
	@Final
	private List<BlockPart> elements;

	@Shadow(remap=false)
	@Final
	private Map<ResourceLocation, TextureConnectionProperties> texturesConnectionProperties;

	@Shadow(remap=false)
	@Final
	private Map<Integer, BlockModelQuadsHolder> createdBakedModelsFor3dContext;

	/**
	 * @author Maximuslotro
	 * @reason Java 9+ changes how computeIfAbsent works. If any map changes, not just on current key happens, a ConcurrentModificationException is thrown
	 * This code changes the way the BlockModelQuadsHolder is generated to ensure non-concurrent changes
	 * <p>
	 * A ConcurrentModificationException in HashMap.computeIfAbsent can occur when the map is structurally modified by another thread while computeIfAbsent is in progress.
	 * This behavior changed between Java 8 and Java 9 due to a fix for JDK-8071667.
	 * In Java 8, computeIfAbsent might not throw a ConcurrentModificationException if the modification happens during the computation but doesn't interfere with the specific key being computed. 
	 * However, in Java 9 and later, the implementation is stricter. 
	 * If any modification to the HashMap occurs during the execution of the computeIfAbsent method, a ConcurrentModificationException is thrown.
	 * 
	 * This blog explains the issue: http://thisthread.blogspot.com/2017/12/hashmapcomputeifabsent-behavior-in-java.html
	 */
    //@Inject(method = "getOrCreateBakedModelFor3DContext", at = @At("HEAD"), remap=false)
	//public void getOrCreateBakedModelFor3DContext(ConnectedTexture3DContext ctx3d, CallbackInfoReturnable<BlockModelQuadsHolder> cir) {
	@Overwrite(remap=false)
	public BlockModelQuadsHolder getOrCreateBakedModelFor3DContext(ConnectedTexture3DContext ctx3d) {
		int key = ctx3d.getCombinedBitFlags();
		// Extended Start
		if(createdBakedModelsFor3dContext.containsKey(key)) {
			return createdBakedModelsFor3dContext.get(key);
		}else {
			// Extended End
			List<BakedQuad> generalQuads = Lists.newArrayList();
			Map<Direction, List<BakedQuad>> builderFaceQuads = Maps.newEnumMap(Direction.class);
			for (Direction dir : Direction.values()) {
				builderFaceQuads.put(dir, Lists.newArrayList());
			}
			for (BlockPart blockpart : this.elements) {
				for (Direction direction : blockpart.faces.keySet()) {
					BlockPartFace blockpartface = blockpart.faces.get(direction);
					RenderMaterial material = this.owner.resolveTexture(blockpartface.texture);
					ResourceLocation texture = material.texture();
					TextureConnectionProperties connectionProperties = this.texturesConnectionProperties.getOrDefault(texture, TextureConnectionProperties.defaultProps(texture));
					Set<ConnectedTextureElement> elementSet = ctx3d.getFace2DContext(direction).getTextureElements(connectionProperties.includeBaseElement());
					ResourceLocation connectedTexture = DynamicTextureRepository.INSTANCE.getConnectedTexture(connectionProperties, elementSet);
					TextureAtlasSprite icon = this.spriteGetter.apply(new RenderMaterial(material.atlasLocation(), connectedTexture));
					if (blockpartface.cullForDirection == null) {
						generalQuads.add(BlockModel.makeBakedQuad(blockpart, blockpartface, icon, direction, this.modelTransform, this.modelLocation));
						continue;
					}
					builderFaceQuads.get(this.modelTransform.getRotation().rotateTransform(blockpartface.cullForDirection)).add(BlockModel.makeBakedQuad(blockpart, blockpartface, icon, direction, this.modelTransform, this.modelLocation));
				}
			}
			// Extended Start
			BlockModelQuadsHolder holder = new BlockModelQuadsHolder(generalQuads, builderFaceQuads);
			createdBakedModelsFor3dContext.put(key, holder);
			return holder;
		}
		// Extended End
	}
}
