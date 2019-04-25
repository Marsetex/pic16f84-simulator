package de.marsetex.pic16f84sim.state;

import java.io.File;
import java.util.List;

import de.marsetex.pic16f84sim.parser.LstParser;
import de.marsetex.pic16f84sim.simulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimStateIdle implements ISimState {

	private static final Logger LOGGER = LogManager.getLogger(SimStateIdle.class);

	@Override
	public boolean isTransitionAllowed(ISimState state) {
		if(state instanceof SimStateIdle) {
			return true;
		}
		if(state instanceof SimStateContMode) {
			return true;
		}
		if(state instanceof SimStateStepMode) {
			return true;
		}
		return false;
	}

	@Override
	public void onEnteringState(Simulator simulator) {
		LOGGER.info("Entering state 'SimStateIdle'");
		simulator.getDebugConsole().onNext("Entering state 'SimStateIdle'");

		LstParser parser = new LstParser(simulator.getCurrentLstFile());
		List<String> codeLines = parser.parse();
		simulator.setCurrentCode(codeLines);
	}

	@Override
	public void onLeavingState(Simulator simulator) {
		LOGGER.info("Leaving state 'SimStateIdle'");
		simulator.getDebugConsole().onNext("Leaving state 'SimStateIdle'");
	}
}
