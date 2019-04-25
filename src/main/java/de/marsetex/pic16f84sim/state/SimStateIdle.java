package de.marsetex.pic16f84sim.state;

import java.io.File;
import java.util.List;

import de.marsetex.pic16f84sim.parser.LstParser;
import de.marsetex.pic16f84sim.simulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimStateIdle implements ISimState {

	private static final Logger LOGGER = LogManager.getLogger(SimStateIdle.class);

	private final File lstFile;

	public SimStateIdle(File file) {
		lstFile = file;
	}

	@Override
	public boolean isTransitionAllowed(ISimState state) {
		if(state instanceof SimStateIdle) {
			return true;
		}
		if(state instanceof SimStateContMode) {
			return true;
		}
		return false;
	}

	@Override
	public void onEnteringState(Simulator simulator) {
		LstParser parser = new LstParser(lstFile);
		List<String> codeLines = parser.parse();
		simulator.setCurrentCode(codeLines);

		LOGGER.info("Entering state 'SimStateIdle'");
		simulator.getDebugConsole().onNext("Entering state 'SimStateIdle'");
	}

	@Override
	public void onLeavingState(Simulator simulator) {
		LOGGER.info("Leaving state 'SimStateIdle'");
		simulator.getDebugConsole().onNext("Leaving state 'SimStateIdle'");
	}
}
