package de.marsetex.picsimulator.state;

import de.marsetex.picsimulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimStateNoFile implements ISimState {

	private static final Logger LOGGER = LogManager.getLogger(SimStateNoFile.class);

	@Override
	public boolean isTransitionAllowed(ISimState state) {
		if(state instanceof SimStateIdle) {
			return true;
		}
		return false;
	}

	@Override
	public void onEnteringState(Simulator simulator) {
		LOGGER.info("Entering state 'SimStateNoFile'");
	}

	@Override
	public void onLeavingState(Simulator simulator) {
		LOGGER.info("Leaving state 'SimStateNoFile'");
	}
}
