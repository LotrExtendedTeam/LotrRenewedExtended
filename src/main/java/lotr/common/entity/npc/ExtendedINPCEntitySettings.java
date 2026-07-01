package lotr.common.entity.npc;

/**
 * Interface defining additional settings for NPC entities.
 */
public interface ExtendedINPCEntitySettings {

    /**
     * Determines whether the NPC should have extended targeting goals injected.
     * Defaults to true
     *
     * @return {@code true} if extended goals should be added, {@code false} otherwise
     */
    default boolean useExtendedGoals() {
    	return true;
    }
}