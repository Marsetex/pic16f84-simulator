package de.marsetex.pic16f84sim.state;

import de.marsetex.pic16f84sim.simulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimStateStepMode implements ISimState {

    private static final Logger LOGGER = LogManager.getLogger(SimStateStepMode.class);

    @Override
    public boolean isTransitionAllowed(ISimState state) {
        if(state instanceof SimStateIdle) {
            return true;
        }
        if(state instanceof SimStateStepMode) {
            onStayingInState(Simulator.getInstance());
            return false;
        }
        if(state instanceof SimStateContMode) {
            return true;
        }
        return false;
    }

    @Override
    public void onEnteringState(Simulator simulator) {
        LOGGER.info("Entering state 'SimStateStepMode'");
        simulator.getDebugConsole().onNext("Entering state 'SimStateStepMode'");

        simulator.executeSingleInstruction();
    }

    private void onStayingInState(Simulator simulator) {
        simulator.executeSingleInstruction();
    }

    @Override
    public void onLeavingState(Simulator simulator) {
        LOGGER.info("Leaving state 'SimStateStepMode'");
        simulator.getDebugConsole().onNext("Leaving state 'SimStateStepMode'");
    }
}
