package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.render.entity.model.ExtendedElfWandererModel;
import lotr.common.entity.npc.ExtendedAbstractElfWandererEntity;
import lotr.common.init.ExtendedItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;

public class ElfHeldCoinLayer<E extends ExtendedAbstractElfWandererEntity> extends LayerRenderer<E, ExtendedElfWandererModel<E>> {

    public ElfHeldCoinLayer(IEntityRenderer<E, ExtendedElfWandererModel<E>> p_i50926_1_) {
        super(p_i50926_1_);
    }

    @Override
	public void render(MatrixStack matStack, IRenderTypeBuffer buf, int packedLight, ExtendedAbstractElfWandererEntity elf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matStack.pushPose();

        this.getParentModel().translateToHand(HandSide.LEFT, matStack);
        matStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
        matStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        matStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        matStack.translate(0.06, 0.02, -0.4);

        matStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        ItemStack heldItem = new ItemStack(ExtendedItems.SILVER_COIN_ONE.get(), 1);
        matStack.translate((1) / 16.0F, -0.3D, -0.05D);
        Minecraft.getInstance().getItemInHandRenderer().renderItem(elf, heldItem, ItemCameraTransforms.TransformType.GROUND, false, matStack, buf, packedLight);
        matStack.popPose();
    }

}
