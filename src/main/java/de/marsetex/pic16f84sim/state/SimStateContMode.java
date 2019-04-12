package de.marsetex.pic16f84sim.state;

import de.marsetex.pic16f84sim.simulator.Simulator;

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
