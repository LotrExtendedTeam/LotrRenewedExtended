package lotr.common.entity.npc;

/**
 * Interface defining additional settings for NPC entities.
 */
public interface ExtendedNPCEntitySettings {

    /**
     * Determines whether the NPC should have extended targeting goals injected.
     *
     * @return {@code true} if extended goals should be added, {@code false} otherwise
     */
    boolean addExtendedGoals();
}