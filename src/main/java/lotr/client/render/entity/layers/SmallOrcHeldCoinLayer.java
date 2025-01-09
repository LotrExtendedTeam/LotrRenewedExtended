package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.render.entity.model.OrcModel;
import lotr.common.entity.npc.OrcEntity;
import lotr.common.init.ExtendedItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;

public class SmallOrcHeldCoinLayer extends LayerRenderer<OrcEntity, OrcModel<OrcEntity>> {
    public SmallOrcHeldCoinLayer(IEntityRenderer<OrcEntity, OrcModel<OrcEntity>> renderer) {
        super(renderer);
    }

    @Override
	public void render(MatrixStack matStack, IRenderTypeBuffer buf, int packedLight, OrcEntity orc, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matStack.pushPose();

        this.getParentModel().translateToHand(HandSide.LEFT, matStack);
        matStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
        matStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        matStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        matStack.translate(0.06, 0.02, -0.4);

        matStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        ItemStack heldItem = new ItemStack(ExtendedItems.SILVER_COIN_ONE.get(), 1);
        matStack.translate((double)((float)(1) / 16.0F), -0.3D, -0.05D);
        Minecraft.getInstance().getItemInHandRenderer().renderItem(orc, heldItem, ItemCameraTransforms.TransformType.GROUND, false, matStack, buf, packedLight);
        matStack.popPose();
    }

}
