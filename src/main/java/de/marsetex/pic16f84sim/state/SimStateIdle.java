package de.marsetex.pic16f84sim.state;

import java.io.File;
import java.util.List;

import de.marsetex.pic16f84sim.parser.LstParser;
import de.marsetex.pic16f84sim.simulator.Simulator;

public class SimStateIdle implements ISimState {

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
	}

	@Override
	public void onLeavingState(Simulator simulator) {
		// TODO Auto-generated method stub

	}
}
