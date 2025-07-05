package lotr.common.entity.npc;

import java.util.List;
import net.minecraft.util.ResourceLocation;

public interface ExtendedCaptainEntity extends ExtendedInteractableEntity {
	List<ResourceLocation> getHireableProfiles();
	
	// Default implementation for backward compatibility
	default ResourceLocation getHireableProfile() {
		List<ResourceLocation> profiles = getHireableProfiles();
		return profiles.isEmpty() ? null : profiles.get(0);
	}
}