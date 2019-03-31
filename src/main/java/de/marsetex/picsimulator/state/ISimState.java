package de.marsetex.picsimulator.state;

import de.marsetex.picsimulator.Simulator;

public interface ISimState {

	public boolean isTransitionAllowed(ISimState state);
	public void onEnteringState(Simulator simulator);
	public void onLeavingState(Simulator simulator);
}
