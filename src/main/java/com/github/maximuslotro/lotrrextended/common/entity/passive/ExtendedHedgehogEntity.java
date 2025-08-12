package com.github.maximuslotro.lotrrextended.common.entity.passive;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedEntities;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedHedgehogEntity extends AnimalEntity{
	public ExtendedHedgehogEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}

	public static AttributeModifierMap.MutableAttribute createHedgehogAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8).add(Attributes.ARMOR, 2).add(Attributes.MOVEMENT_SPEED, 0.25F);
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return ExtendedEntities.HEDGEHOG.get().create(pServerLevel);
	}

	@Override
	public boolean isFood(ItemStack pStack) {
		return ExtendedTags.Items.HEDGEHOG_FOOD.contains(pStack.getItem());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(ExtendedTags.Items.HEDGEHOG_FOOD), false));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.6D, 1.4D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, NPCEntity.class, 8.0F, 1.6D, 1.4D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, WolfEntity.class, 10.0F, 1.6D, 1.4D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, MonsterEntity.class, 4.0F, 1.6D, 1.4D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
		this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 10.0F));
	}
}