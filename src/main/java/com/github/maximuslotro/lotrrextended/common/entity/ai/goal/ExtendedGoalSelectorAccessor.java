package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

public interface ExtendedGoalSelectorAccessor {
	/**
	 * Stops and removes all AI goals matching the specified priority.
	 * 
	 * @param priority Target priority integer to remove
	 */
	void removeGoalByPriority$Extended(int priority);
}
