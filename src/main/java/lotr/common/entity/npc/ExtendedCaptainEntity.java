package lotr.common.entity.npc;

import java.util.List;
import net.minecraft.util.ResourceLocation;

public interface ExtendedCaptainEntity extends ExtendedInteractableEntity {
	List<ResourceLocation> getHireableProfiles();
}