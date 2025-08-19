package com.github.maximuslotro.lotrextended.mixins.lotr.client.render.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.client.render.player.LOTRPlayerRendering;
import lotr.common.config.LOTRConfig;
import lotr.common.data.LOTRLevelData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Unique;

/**
 * Renewed does not respect entity team nameplate visibility
 * @Mixin (LOTRPlayerRendering.class)
 * */
@Mixin(LOTRPlayerRendering.class)
public class MixinLOTRPlayerRendering {

	/**
	 * Helper method to get if regular should nameplate render
	 * @param player The player we are rendering their alignment bar
	 * @return Get regular HUD show logic
	 * @Shadow (remap=false)
	 * */
	@Shadow(remap=false)
	private boolean shouldRenderPlayerHUD(PlayerEntity player) {return false;}

	/**
	 * @author maximuslotro
	 * @reason Need to add support for hiding alignment bar by Team
	 * @param player The player we are rendering their alignment bar
	 * @return if we should render their alignment bar
	 */
	@Overwrite(remap=false)
	private boolean shouldRenderAlignment(PlayerEntity player) {
		if (LOTRConfig.CLIENT.displayAlignmentAboveHead.get() && shouldRenderPlayerHUD(player)/*Extended Start*/ && shouldRenderTeam(player)/*Extended End*/) {
			return LOTRLevelData.clientInstance().getData(player).getAlignmentData().displayAlignmentAboveHead();
		}
		return false;
	}

	/**
	 * Extended method to fix Renewed rendering alignment bars even if player teams don't allow for nameplate rendering
	 * @param player The player we are rendering their alignment bar
	 * @return if their and our team allows for nameplate rendering
	 */
	@Unique
	private boolean shouldRenderTeam(PlayerEntity player) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientPlayerEntity clientplayerentity = minecraft.player;
		if (clientplayerentity!=null && player != clientplayerentity) {
			Team team = player.getTeam();
			Team team1 = clientplayerentity.getTeam();
			if (team != null) {
				Team.Visible nameViability = team.getNameTagVisibility();
				switch(nameViability) {
					case NEVER:
						return false;
					case HIDE_FOR_OTHER_TEAMS:
						return team1 == null || team.isAlliedTo(team1) && team.canSeeFriendlyInvisibles();
					case HIDE_FOR_OWN_TEAM:
						return team1 == null || !team.isAlliedTo(team1);
					default:
						return true;
				}
			}
		}
		return true;
	}
}
