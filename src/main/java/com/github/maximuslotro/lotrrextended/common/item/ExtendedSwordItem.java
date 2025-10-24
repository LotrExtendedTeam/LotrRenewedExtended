package com.github.maximuslotro.lotrrextended.common.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ForgeMod;

public class ExtendedSwordItem extends SwordItem {
	// An extension of the defaultModifiers field 
	private Multimap<Attribute, AttributeModifier> additionalDefaultModifiers;
	protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("7b101572-d7a0-489c-b0f1-3cd7d78e8375");
	private final float reachDistanceModifier;

	public ExtendedSwordItem(IItemTier pItemTeir, int pAttackDamage, float pAttackSpeed, float pAttackReach, Properties pProperties) {
		super(pItemTeir, pAttackDamage, pAttackSpeed, pProperties);
		this.reachDistanceModifier = pAttackReach;
	}

	public ExtendedSwordItem(IItemTier pItemTeir, Properties pProperties) {
		this(pItemTeir, 3, -2.4F, 0.0f, pProperties);
	}

	public ExtendedSwordItem(IItemTier pItemTeir, ItemGroup pCategory) {
		this(pItemTeir, 3, -2.4F, 0.0f, new Properties().tab(pCategory));
	}

	//Defer forge reach distance additions until this step, as the registries are not built at item (learned this the hard way)
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		if(slot == EquipmentSlotType.MAINHAND) {
			if(additionalDefaultModifiers==null) {
				Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
				Multimap<Attribute, AttributeModifier> defaultModifiers = super.getDefaultAttributeModifiers(EquipmentSlotType.MAINHAND);
				builder.putAll(defaultModifiers);
				builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", reachDistanceModifier, Operation.ADDITION));
				additionalDefaultModifiers = builder.build();
			}
			return this.additionalDefaultModifiers;
		}
		else{
			return super.getDefaultAttributeModifiers(slot);
		}
	}
}
