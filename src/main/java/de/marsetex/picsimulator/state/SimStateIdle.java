package de.marsetex.picsimulator.state;

import java.io.File;
import java.util.List;

import de.marsetex.picsimulator.Simulator;
import de.marsetex.picsimulator.parser.LSTParser;
import io.reactivex.Observable;

public class SimStateIdle implements ISimState {

	private final File lstFile;

	public SimStateIdle(File file) {
		lstFile = file;
		System.out.println(file.getPath());
	}

	@Override
	public boolean isTransitionAllowed(ISimState state) {
		if(state instanceof SimStateIdle) {
			return true;
		}
		return false;
	}

	@Override
	public void onEnteringState(Simulator simulator) {
		LSTParser parser = new LSTParser(lstFile);
		simulator.getCodeLines().onNext(parser.parse());
	}

	@Override
	public void onLeavingState(Simulator simulator) {
		// TODO Auto-generated method stub

	}
}
