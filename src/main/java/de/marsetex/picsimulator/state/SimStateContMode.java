package de.marsetex.picsimulator.state;

import de.marsetex.picsimulator.Simulator;

public class SimStateContMode implements ISimState {

    @Override
    public boolean isTransitionAllowed(ISimState state) {
        if(state instanceof SimStateIdle) {
            return true;
        }
        return false;
    }

    @Override
    public void onEnteringState(Simulator simulator) {
        new Thread(simulator).start();
    }

    @Override
    public void onLeavingState(Simulator simulator) {

    }
}
