package lotr.common.entity.npc;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public interface ExtendedWanderingTraderEntity extends ExtendedTraderEntity {

	TranslationTextComponent getDepartureLine(PlayerEntity player);

	List<UUID> getEscortUUIDs();

	void addEscort(UUID uuid);

	NPCEntity createEscort();

    int getFactionColor();

}
