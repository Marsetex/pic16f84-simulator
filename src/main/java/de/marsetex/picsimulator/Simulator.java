package de.marsetex.picsimulator;

import java.util.List;

import de.marsetex.picsimulator.state.ISimState;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class Simulator {

	private static Simulator simulator;

	private PublishSubject<List<String>> codeLines = PublishSubject.create();
	private ISimState currentState;

	private Simulator() {
		simulator = null;
	}

	public static Simulator getInstance() {
		if (simulator == null) {
			simulator = new Simulator();
		}
		return simulator;
	}

	public void changeState(ISimState newState) {
		if (currentState == null) {
			currentState = newState;
			currentState.onEnteringState(getInstance());

		} else {
			if (currentState.isTransitionAllowed(newState)) {
				currentState.onLeavingState(getInstance());
				currentState = newState;
				currentState.onEnteringState(getInstance());
			}
		}
	}

	public PublishSubject<List<String>> getCodeLines() {
		return codeLines;
	}
}
