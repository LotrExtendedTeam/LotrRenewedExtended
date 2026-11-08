package com.github.maximuslotro.lotrrextended.common.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap.Builder;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;

public class ExtendedLanceItem extends ExtendedSwordItem {
	protected static final UUID LANCE_KNOCKBACK_UUID = UUID.fromString("d48c3058-49e0-4223-a6d0-8ca12d7cbd7c");
	public ExtendedLanceItem(IItemTier pItemTeir, int pAttackDamage, float pAttackSpeed, float pAttackReach, Properties pProperties) {
		super(pItemTeir, pAttackDamage, pAttackSpeed, pAttackReach, pProperties);
	}

	public ExtendedLanceItem(IItemTier pItemTeir, Properties pProperties) {
		this(pItemTeir, 3, -3.5F, 2.0F, pProperties);
	}

	public ExtendedLanceItem(IItemTier pItemTeir, ItemGroup pCategory) {
		this(pItemTeir, new Properties().tab(pCategory));
	}

	@Override
	public void addAdditionalAttributes(Builder<Attribute, AttributeModifier> builder) {
		super.addAdditionalAttributes(builder);
		builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(LANCE_KNOCKBACK_UUID, "Weapon modifier", 1.0, AttributeModifier.Operation.ADDITION));
	}
}
