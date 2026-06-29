package lotr.common.entity.npc;

import java.util.List;
import java.util.UUID;

public interface ExtendedWanderingTraderEntity extends ExtendedMerchantEntity {

	String getDepartureLine();

	List<UUID> getEscortUUIDs();

	void addEscort(UUID uuid);

	NPCEntity createEscort();

    int getFactionColor();

}
