package com.github.maximuslotro.lotrrextended.client.render.entity.ai.goal;

import net.minecraft.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;

public class ExtendedTamingGoal extends RunAroundLikeCrazyGoal{

	public ExtendedTamingGoal(AbstractHorseEntity horse, double speed) {
		super(horse, speed);
	}
}
