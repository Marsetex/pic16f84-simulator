package de.marsetex.picsimulator.state;

import de.marsetex.picsimulator.Simulator;

public class SimStateNoFile implements ISimState {

	@Override
	public boolean isTransitionAllowed(ISimState state) {
		if(state instanceof SimStateIdle) {
			return true;
		}
		return false;
	}

	@Override
	public void onEnteringState(Simulator simulator) {
		// TODO: Logger
	}

	@Override
	public void onLeavingState(Simulator simulator) {
		// TODO: Logger
	}
}
