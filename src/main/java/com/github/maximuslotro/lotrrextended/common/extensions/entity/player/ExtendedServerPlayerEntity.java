package com.github.maximuslotro.lotrrextended.common.extensions.entity.player;

import com.github.maximuslotro.lotrrextended.common.codex.ExtendedPlayerCodexes;

/**
 * Interface accessor for {@Link ServerPlayerEntity}
 * Used to get runtime information added by {@Link MixinServerPlayerEntity}
 * */
public interface ExtendedServerPlayerEntity {
	/**
	 * Get the {@Link ExtendedPlayerCodexes} instance for this {@Link ServerPlayerEntity}
	 * @return The {@Link ExtendedPlayerCodexes} instance generated this {@Link ServerPlayerEntity}
	 * */
	public ExtendedPlayerCodexes getPlayerCodexes();
}
