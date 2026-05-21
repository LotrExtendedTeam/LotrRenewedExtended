package com.github.maximuslotro.lotrrextended.client.eventhandlers;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.client.ExtendedClientProxy;
import com.github.maximuslotro.lotrrextended.client.render.ExtendedHiredUnitHpBar;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBowMaterial;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCrossbowMaterial;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCrossbowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedModifierScrollItem;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierType;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.LOTRDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ExtendedRendererEventHandler {

	public static void renderHpBar(RenderNameplateEvent event) {
		if (event.getEntity() instanceof NPCEntity && ((ExtendedNPCEntity)event.getEntity()).getUnitInfo().isNpcActiveDuty()) {
			if (ExtendedClientProxy.usersUUID.equals(null) || !ExtendedClientProxy.usersUUID.equals(((ExtendedNPCEntity)event.getEntity()).getUnitInfo().getOwnerUUID())) {
				return;
			}

			Minecraft minecraft = Minecraft.getInstance();
			if(minecraft.options.hideGui) {
				return;
			}
			MatrixStack matrixStackIn = event.getMatrixStack();

			double d0 = minecraft.getEntityRenderDispatcher().distanceToSqr(event.getEntity());
			FontRenderer fontrenderer = event.getEntityRenderer().getFont();
			IRenderTypeBuffer.Impl buffer = minecraft.renderBuffers().bufferSource();
			Matrix4f matrix4f = matrixStackIn.last().pose();

			if (ForgeHooksClient.isNameplateInRenderDistance(event.getEntity(), d0)) {
				float f = event.getEntity().getBbHeight() + 1.2F;
				matrixStackIn.pushPose();
				matrixStackIn.translate(0.0D, f, 0.0D);
				matrixStackIn.mulPose(minecraft.getEntityRenderDispatcher().cameraOrientation());
				matrixStackIn.scale(-0.025F, -0.025F, 0.025F);

				ExtendedHiredUnitHpBar.draw((LivingEntity) event.getEntity(),
					100 / 100.0F, // text scale default 1X
					matrixStackIn,
					fontrenderer
					);

				matrixStackIn.popPose();
				RenderSystem.disableDepthTest();
			}
		}
	}

	public static void onPreRenderGameOverlay(final RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.level != null && LOTRDimensions.isModDimension(mc.level)) {
			if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE && !ExtendedServerConfig.enableVanillaXp.get()) {
				event.setCanceled(true);
				return;
			}
		}
	}

	public static void onPostRenderGameOverlay(final RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.level != null && LOTRDimensions.isModDimension(mc.level)) {
			if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO) {
				ExtendedClientProxy.INSTANCE.lastTimeBossBarRendered = System.currentTimeMillis();
			}
		}
	}

	public static void addTooltipsForModifiers(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if(ExtendedModifierUtils.hasModifiers(stack)) {
			List<ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierList(stack);
			if (!stack.hasCustomHoverName() && !modifiers.isEmpty()) {
				ITextComponent baseName = stack.getItem().getName(stack);
				for(ExtendedModifier enchantment : modifiers) {
					ITextComponent newName = new TranslationTextComponent("enchant.lotrextended." + enchantment.getLocalKey())
						.append(" ").withStyle(style -> style.withItalic(false)).append(baseName.copy().withStyle(style -> style.withItalic(false)));
					baseName = newName;
				}
				event.getToolTip().set(0, baseName);
			}
			List<ITextComponent> entries = new ArrayList<>();
			for (ExtendedModifier modifier : modifiers) {
				if(modifier.getModifierType().equals(ExtendedModifierType.BANE)) {
					entries.add(new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()).append(": ")
						.append(new StringTextComponent((modifier.getModifierEffect()>0 ? modifier.getModifierType().getFrontValue() : "")+String.valueOf(modifier.getModifierEffect())+ " "))
						.append(new TranslationTextComponent(modifier.getModifierType().getKeyLocal(), new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()+ ".race")))
						.withStyle(TextFormatting.GRAY));
				}else if(modifier.getModifierType().getFrontValue().isEmpty()){
					entries.add(new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()).append(": ")
						.append(new TranslationTextComponent(modifier.getModifierType().getKeyLocal()))
						.withStyle(TextFormatting.GRAY));
				}else {
					entries.add(new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()).append(": ")
						.append(new StringTextComponent((modifier.getModifierEffect()>0 ? modifier.getModifierType().getFrontValue() : "")+String.valueOf(modifier.getModifierEffect())+ " "))
						.append(new TranslationTextComponent(modifier.getModifierType().getKeyLocal()))
						.withStyle(TextFormatting.GRAY));
				}
			}
			event.getToolTip().addAll(1, entries);
		}else if(ExtendedModifierScrollItem.hasModifier(stack)) {
			ExtendedModifier modifier = ExtendedModifierScrollItem.getModifier(stack);
			List<ITextComponent> entries = new ArrayList<>();
			if(modifier.getModifierType().getFrontValue().isEmpty()){
				entries.add(new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()).append(": ")
					.append(new TranslationTextComponent(modifier.getModifierType().getKeyLocal()))
					.withStyle(TextFormatting.GRAY));
			}else {
				entries.add(new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()).append(": ")
					.append(new StringTextComponent((modifier.getModifierEffect()>0 ? modifier.getModifierType().getFrontValue() : "")+String.valueOf(modifier.getModifierEffect())+ " "))
					.append(new TranslationTextComponent(modifier.getModifierType().getKeyLocal()))
					.withStyle(TextFormatting.GRAY));
			}
			event.getToolTip().addAll(1, entries);
		}
		if(stack.getItem() instanceof BowItem) {
			int drawSpeed = 20;
			float damage = 1.0f;
			if(stack.getItem() instanceof ExtendedBowItem) {
				ExtendedBowMaterial material = ((ExtendedBowItem)stack.getItem()).getMaterial();
				drawSpeed = material.getDrawSpeedTicks();
				damage = material.getAttackDamageBonusScale();
			}
			if(!ExtendedModifierUtils.getModifierByTypeSafe(stack, ExtendedModifierType.RANGED_SPEED).isUnknown()) {
				damage = damage * ExtendedModifierUtils.getModifierByTypeSafe(stack, ExtendedModifierType.RANGED_SPEED).getModifierEffect();
			}
			event.getToolTip().add(StringTextComponent.EMPTY);
			event.getToolTip().add(new TranslationTextComponent("gui.lotrextended.bow.speed", (int)((1.0f/(drawSpeed/20.0F)) * 100)+"%").withStyle(TextFormatting.DARK_GREEN));
			event.getToolTip().add(new TranslationTextComponent("gui.lotrextended.bow.damage", String.format("%.0f%%", damage * 100)).withStyle(TextFormatting.DARK_GREEN));
			event.getToolTip().add(new TranslationTextComponent("gui.lotrextended.bow.range", String.format("%.0f%%", damage * 100)).withStyle(TextFormatting.DARK_GREEN));
		}
		if(stack.getItem() instanceof CrossbowItem) {
			int drawSpeed = 25;
			float damage = 1.0f;
			if(stack.getItem() instanceof ExtendedCrossbowItem) {
				ExtendedCrossbowMaterial material = ((ExtendedCrossbowItem)stack.getItem()).getMaterial();
				drawSpeed = material.getDrawSpeedTicks();
				damage = material.getLaunchSpeedFactor();
			}
			if(!ExtendedModifierUtils.getModifierByTypeSafe(stack, ExtendedModifierType.RANGED_SPEED).isUnknown()) {
				damage = damage * ExtendedModifierUtils.getModifierByTypeSafe(stack, ExtendedModifierType.RANGED_SPEED).getModifierEffect();
			}
			event.getToolTip().add(StringTextComponent.EMPTY);
			event.getToolTip().add(new TranslationTextComponent("gui.lotrextended.bow.speed", (int)((1.0f/(drawSpeed/25.0F)) * 100)+"%").withStyle(TextFormatting.DARK_GREEN));
			event.getToolTip().add(new TranslationTextComponent("gui.lotrextended.bow.damage", String.format("%.0f%%", damage * 100)).withStyle(TextFormatting.DARK_GREEN));
			event.getToolTip().add(new TranslationTextComponent("gui.lotrextended.bow.range", String.format("%.0f%%", damage * 100)).withStyle(TextFormatting.DARK_GREEN));
		}
		if(stack.getItem() instanceof HorseArmorItem) {
			event.getToolTip().add(StringTextComponent.EMPTY);
			event.getToolTip().add((new TranslationTextComponent("item.lotrextended.modifiers.horse")).withStyle(TextFormatting.GRAY));
			event.getToolTip().add((new TranslationTextComponent("attribute.modifier.plus.0", ((HorseArmorItem)stack.getItem()).getProtection(), new TranslationTextComponent("attribute.name.generic.armor"))).withStyle(TextFormatting.BLUE));
		}
	}
}