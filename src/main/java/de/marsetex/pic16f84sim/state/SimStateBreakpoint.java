package de.marsetex.pic16f84sim.state;

import de.marsetex.pic16f84sim.simulator.Simulator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimStateBreakpoint implements ISimState {

    private static final Logger LOGGER = LogManager.getLogger(SimStateBreakpoint.class);

    @Override
    public boolean isTransitionAllowed(ISimState state) {
        if(state instanceof SimStateStepMode) {
            return true;
        }
        return false;
    }

    @Override
    public void onEnteringState(Simulator simulator) {
        LOGGER.info("Entering state 'SimStateBreakpoint'");
        simulator.getDebugConsole().onNext("Entering state 'SimStateBreakpoint'");
    }

    @Override
    public void onLeavingState(Simulator simulator) {
        LOGGER.info("Leaving state 'SimStateBreakpoint'");
        simulator.getDebugConsole().onNext("Leaving state 'SimStateBreakpoint'");
    }
}
