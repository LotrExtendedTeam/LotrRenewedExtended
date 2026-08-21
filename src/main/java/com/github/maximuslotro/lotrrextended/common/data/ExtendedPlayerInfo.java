package com.github.maximuslotro.lotrrextended.common.data;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import javax.annotation.Nonnull;


import net.minecraft.nbt.CompoundNBT;


public class ExtendedPlayerInfo {
	private final @Nonnull UUID id;
	private Instant firstLogin = Instant.now();
	private Instant lastLogout;
	private boolean combatLoggedLastSession = false;
	private boolean jailed = false;

	public ExtendedPlayerInfo(@Nonnull UUID uuid) {
		this.id = uuid;
	}

	public UUID getId() {
		return id;
	}

	public Instant getFirstLogin() {
		return firstLogin;
	}

	protected void setFirstLogin(Instant firstLogin) {
		this.firstLogin = firstLogin;
	}

	public Instant getLastLogout() {
		return lastLogout;
	}

	protected void setLastLogout(Instant lastLogout) {
		this.lastLogout = lastLogout;
	}

	public boolean didCombatLog() {
		return combatLoggedLastSession;
	}

	protected void setCombatLogged(boolean value) {
		this.combatLoggedLastSession = value;
	}

	public boolean isJailed() {
		return jailed;
	}

	protected void setJailed(boolean jail) {
		jailed = jail;
	}

	public CompoundNBT save() {
		CompoundNBT pCompound = new CompoundNBT();
		pCompound.putUUID("id", id);
		pCompound.putString("firstLogin", firstLogin.toString());
		if(lastLogout!=null) {
			pCompound.putString("lastLogout", lastLogout.toString());
		}
		pCompound.putBoolean("combatLoggedLastSession", combatLoggedLastSession);
		pCompound.putBoolean("jailed", jailed);
		return pCompound;
	}

	public static ExtendedPlayerInfo load(CompoundNBT pCompound) {
		UUID id = pCompound.getUUID("id");
		ExtendedPlayerInfo info = new ExtendedPlayerInfo(id);
		if (pCompound.contains("firstLogin")) {
            try {
                info.setFirstLogin(Instant.parse(pCompound.getString("firstLogin")));
            } catch (DateTimeParseException ignored) {}
        }

        if (pCompound.contains("lastLogout")) {
            try {
                info.setLastLogout(Instant.parse(pCompound.getString("lastLogout")));
            } catch (DateTimeParseException ignored) {}
        }
		info.setCombatLogged(pCompound.getBoolean("combatLoggedLastSession"));
		info.setJailed(pCompound.getBoolean("jailed"));
		return info;
	}
}
