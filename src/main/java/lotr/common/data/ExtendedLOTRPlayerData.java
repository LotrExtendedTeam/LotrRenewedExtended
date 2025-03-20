package lotr.common.data;

/**
 * An interface intended to be able to retrieve information from Mixin classes on runtime via casting
 * */
public interface ExtendedLOTRPlayerData {
	/**
	 * Gets a ExtendedFellowshipDataModule from a Mixin class on runtime
	 * @return An ExtendedFellowshipDataModule from the implemented class
	 * */
	public ExtendedFellowshipDataModule getFellowshipData();
	/**
	 * Gets a ExtendedMiscDataModule from a Mixin class on runtime
	 * @return An ExtendedMiscDataModule from the implemented class
	 * */
	public ExtendedMiscDataModule getExtendedMiscData();
}
