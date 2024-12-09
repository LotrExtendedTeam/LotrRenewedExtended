package lotr.common.fellowship;

import java.util.UUID;

// TODO add fellowship name data move with invites
public class ExtendedFellowshipInvite {
	public final UUID fellowshipID;
	public final UUID Invitor;
	//public final String Fname;
	public ExtendedFellowshipInvite(UUID fellowshipUUID, UUID invitor) {
		fellowshipID = fellowshipUUID;
		Invitor = invitor;
//		if (fname.length() > 20) {
//			fname = fname.substring(0, 20);
//		}
//		Fname = fname;
	}
}
