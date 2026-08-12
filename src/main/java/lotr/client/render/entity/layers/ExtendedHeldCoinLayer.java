package lotr.client.render.entity.layers;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.render.entity.model.LOTRBipedModel;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.CalendarUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedHeldCoinLayer<E extends NPCEntity, M extends LOTRBipedModel<E>> extends LayerRenderer<E, M> {

	public ExtendedHeldCoinLayer(IEntityRenderer<E, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(MatrixStack matStack, IRenderTypeBuffer buf, int packedLight, E entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entity.getItemBySlot(EquipmentSlotType.OFFHAND).isEmpty()) {
			return;
		}
		boolean rightHanded = !entity.isLeftHanded();
		matStack.pushPose();
		// Setup hand render stack
		this.getParentModel().translateToHand(rightHanded ? HandSide.LEFT : HandSide.RIGHT, matStack);
		matStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
		matStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		// Change render pos on hand for coins
		matStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
		if(rightHanded) {
			matStack.translate(0.06, 0.02, -0.4);
		}else {
			matStack.translate(0.18, -0.125, -0.43);
		}
		matStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
		// Main item hand pos setup
		ItemStack heldItem = new ItemStack(ExtendedItems.SILVER_COIN_ONE.get(), 1);
		if(CalendarUtil.isAprilFools()) {
			heldItem = new ItemStack(ExtendedItems.SILVER_COIN_HUNDRED.get(), 1);
			heldItem.enchant(Enchantments.VANISHING_CURSE, 1);
		}
		matStack.translate((rightHanded ? 1 : -1) / 16.0F, -0.3D, -0.05D);
		Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, heldItem, ItemCameraTransforms.TransformType.GROUND, !rightHanded, matStack, buf, packedLight);
		matStack.popPose();
	}

}
