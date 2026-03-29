package com.github.maximuslotro.lotrextended.mixins.net.minecraft.item;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierType;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * A Mixin targeting {@link ItemStack} to extend and customize item durability behavior.
 * <p>
 * This mixin modifies the way durability is reduced on items, incorporating both 
 * vanilla Unbreaking enchantments and extended's modifiers (such as extended durability).
 * </p>
 * @Mixin (ItemStack.class)
 */
@Mixin(ItemStack.class)
public class MixinItemStack {
	/**
	 * Shadowed method from {@link ItemStack}.
	 *
	 * @return {@code true} if the item can take durability damage, {@code false} otherwise
	 * @Shadow
	 */
	@Shadow
	public boolean isDamageableItem() {return false;}
	/**
	 * Shadowed method from {@link ItemStack}.
	 *
	 * @return the {@link Item} instance held by this stack
	 * @Shadow
	 */
	@Shadow
	public Item getItem() {return null;}
	/**
	 * Shadowed method from {@link ItemStack}.
	 *
	 * @return the current damage value (durability consumed) of the item
	 * @Shadow
	 */
	@Shadow
	public int getDamageValue() {return 0;}
	/**
	 * Shadowed method from {@link ItemStack}.
	 *
	 * @param damageAmount the new damage value to assign to this item
	 * v
	 */
	@Shadow
	public void setDamageValue(int damageAmount) {}
	/**
	 * Shadowed method from {@link ItemStack}.
	 *
	 * @return the maximum durability (damage threshold) this item can withstand
	 * @Shadow
	 */
	@Shadow
	public int getMaxDamage() {return 0;}
	/**
	 * Injects into {@link ItemStack#hurt(int, Random, ServerPlayerEntity)} 
	 * at the head of the method to override vanilla durability loss logic.
	 * <p>
	 * This method extends vanilla durability by:
	 * <ul>
	 *   <li>Checking for custom durability modifiers via {@link ExtendedModifierUtils}</li>
	 *   <li>Factoring in Unbreaking enchantment levels</li>
	 *   <li>Allowing durability to be reduced at a modified probability scale</li>
	 *   <li>Triggering advancement criteria if durability changes for a player</li>
	 * </ul>
	 * </p>
	 *
	 * @param pAmount the number of durability points to reduce
	 * @param pRandom random source for probabilistic reduction checks
	 * @param pUser   the player responsible for the damage, may be {@code null}
	 * @param cir     callback to set return value and cancel vanilla behavior
	 * @Inject (method="hurt(ILjava/util/Random;Lnet/minecraft/entity/player/ServerPlayerEntity;)Z", at = @At("HEAD"), cancellable=true)
	 */
	@Inject(method="hurt(ILjava/util/Random;Lnet/minecraft/entity/player/ServerPlayerEntity;)Z", 
		at = @At("HEAD"), cancellable=true)
	private void hurt$Extended(int pAmount, Random pRandom, @Nullable ServerPlayerEntity pUser, CallbackInfoReturnable<Boolean> cir) {
		if (!isDamageableItem() || ExtendedModifierUtils.getModifierByTypeSafe((ItemStack)(Object)this, ExtendedModifierType.DURABILITY).isUnknown()) {
			return;
		} else {
			if (pAmount > 0) {
				float modAmount = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, (ItemStack)(Object)this);
				modAmount += ExtendedModifierUtils.getModifierByTypeSafe((ItemStack)(Object)this, ExtendedModifierType.DURABILITY).getModifierEffect();
				int removeAmount = 0;

				for(int k = 0; modAmount > 0 && k < pAmount; ++k) {
					if (shouldIgnoreDurabilityDrop$Extended((ItemStack)(Object)this, modAmount, pRandom)) {
						++removeAmount;
					}
				}

				pAmount -= removeAmount;
				if (pAmount <= 0) {
					cir.setReturnValue(false);
				}
			}

			if (pUser != null && pAmount != 0) {
				CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(pUser, (ItemStack)(Object)this, getDamageValue() + pAmount);
			}

			int l = getDamageValue() + pAmount;
			setDamageValue(l);
			cir.setReturnValue(l >= getMaxDamage());
		}
	}
	/**
	 * Determines whether an item’s durability loss should be ignored, extending the logic of
	 * {@link UnbreakingEnchantment#shouldIgnoreDurabilityDrop}.
	 * <p>
	 * Rules:
	 * <ul>
	 *   <li>Armor has a fixed 40% chance to always take durability loss</li>
	 *   <li>Other items use a probability based on the Unbreaking level + custom modifiers</li>
	 * </ul>
	 * </p>
	 *
	 * <p>The core probability check is:</p>
	 * <pre>
	 * return pRand.nextFloat() > (1.0f / (pLevel + 1));
	 * </pre>
	 * <p>
	 * This means:
	 * <ul>
	 *   <li>{@code pRand.nextFloat()} produces a random value between 0.0 and 1.0.</li>
	 *   <li>The threshold {@code (1.0f / (pLevel + 1))} decreases as {@code pLevel} increases.</li>
	 *   <li>Higher {@code pLevel} → lower threshold → easier for the random roll to be greater → more chances to ignore durability loss.</li>
	 *   <li>At {@code pLevel = 0}, threshold = 1.0  → durability loss is always done.</li>
	 *   <li>At {@code pLevel = 1}, threshold = 0.5  → ~50% chance to ignore durability loss.</li>
	 *   <li>At {@code pLevel = 2}, threshold = 0.33 → ~33% chance to ignore durability loss.</li>
	 *   <li>At {@code pLevel = 3}, threshold = 0.25 → ~25% chance to ignore durability loss.</li>
	 *   <li>At higher {@code pLevel}, the probability approaches 100% of ignoring durability damage as the threshold decreases.</li>
	 * </ul>
	 * </p>
	 *
	 * @param pStack the {@link ItemStack} being damaged
	 * @param pLevel combined durability reduction factor (enchantments + modifiers)
	 * @param pRand  random instance used to roll probability checks
	 * @return {@code true} if the durability damage should be ignored, {@code false} otherwise
	 * @Unique
	 */
	@Unique
	private static boolean shouldIgnoreDurabilityDrop$Extended(ItemStack pStack, float pLevel, Random pRand) {
		if (pStack.getItem() instanceof ArmorItem && pRand.nextFloat() < 0.6F) {
			return false;
		} else {
			//Keeps parity with the durability scale present in vanilla
			return pRand.nextFloat() > (1.0f/(pLevel+1));
		}
	}

	@Inject(method = "getHoverName", at = @At("TAIL"), cancellable = true)
	private void injectModifiersIntoName(CallbackInfoReturnable<ITextComponent> cir) {
		ItemStack stack = (ItemStack)(Object)this;

		if (!stack.hasCustomHoverName()) {
			List<ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierList(stack);
			if (!modifiers.isEmpty()) {
				ITextComponent baseName = stack.getItem().getName(stack);
				for (ExtendedModifier modifier : modifiers) {
					ITextComponent newName = new TranslationTextComponent("enchant.lotrextended." + modifier.getLocalKey())
						.append(" ").withStyle(style -> style.withItalic(false)).append(baseName.copy().withStyle(style -> style.withItalic(false)));
					baseName = newName;
				}

				cir.setReturnValue(baseName);
			}
		}
	}
}