package de.marsetex.picsimulator;

import java.util.List;

import de.marsetex.picsimulator.microcontroller.PIC16F84;
import de.marsetex.picsimulator.state.ISimState;
import de.marsetex.picsimulator.state.SimStateNoFile;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Simulator {

	private static final Logger LOGGER = LogManager.getLogger(Simulator.class);

	private static Simulator simulator;

	private final PIC16F84 picController;

	private PublishSubject<List<String>> codeLines;
	private ISimState currentState;

	private Simulator() {
		simulator = null;
		picController = new PIC16F84();
		codeLines = PublishSubject.create();

		currentState = new SimStateNoFile();
		currentState.onEnteringState(this);
	}

	public static Simulator getInstance() {
		if (simulator == null) {
			simulator = new Simulator();
		}
		return simulator;
	}

	public void changeState(ISimState newState) {
		if (currentState.isTransitionAllowed(newState)) {
			currentState.onLeavingState(getInstance());
			currentState = newState;
			currentState.onEnteringState(getInstance());
		}
	}

	public void loadCodeIntoProgramMemory(List<String> codeLines) {
		for (String codeLine : codeLines) {
			String opcode = codeLine.substring(0, 9);
			if(!opcode.isBlank()) {
				String[] splitOpcode = opcode.split(" ");
				picController.loadOpcodeIntoProgramMemory(splitOpcode[0], splitOpcode[1]);
			}
		}
	}

	public PublishSubject<List<String>> getCodeLines() {
		return codeLines;
	}
}
