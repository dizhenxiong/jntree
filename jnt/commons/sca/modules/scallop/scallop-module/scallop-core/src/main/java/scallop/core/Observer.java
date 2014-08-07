package scallop.core;

import scallop.core.exception.ScallopRemoteException;

public interface Observer {
	void update(boolean isFromSchedule) throws ScallopRemoteException;

}
