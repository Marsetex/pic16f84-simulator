package de.marsetex.picsimulator;

import java.util.List;

import de.marsetex.picsimulator.decoder.InstructionDecoder;
import de.marsetex.picsimulator.instruction.IPicInstruction;
import de.marsetex.picsimulator.microcontroller.PIC16F84;
import de.marsetex.picsimulator.state.ISimState;
import de.marsetex.picsimulator.state.SimStateNoFile;
import io.reactivex.subjects.PublishSubject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Simulator implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(Simulator.class);

	private static Simulator simulator;

	private final PIC16F84 picController;
	private final InstructionDecoder decoder;
	private final PublishSubject<List<String>> codeLines;

	private List<String> currentCode;
	private ISimState currentState;
	private boolean simulationRunning;

	private Simulator() {
		simulator = null;
		picController = new PIC16F84();
		decoder = new InstructionDecoder();
		codeLines = PublishSubject.create();

		currentState = new SimStateNoFile();
		currentState.onEnteringState(this);

		simulationRunning = false;
	}

	public static Simulator getInstance() {
		if (simulator == null) {
			simulator = new Simulator();
		}
		return simulator;
	}

	@Override
	public void run() {
		simulationRunning = true;

		while(simulationRunning) {
			for (String codeLine : currentCode) {
				String opcodeAsString = codeLine.substring(0, 9);
				if(!opcodeAsString.isBlank()) {
					short opcode = picController.getNextInstruction();
					IPicInstruction instruction = decoder.decode(opcode);
					instruction.execute(picController);
					picController.incrementProgramCounter();
				}
			}
		}
	}

	public void stopSimulation() {
		simulationRunning = false;
	}

	public void reset() {
		picController.resetProgramCounter();
	}

	public void changeState(ISimState newState) {
		if (currentState.isTransitionAllowed(newState)) {
			currentState.onLeavingState(getInstance());
			currentState = newState;
			currentState.onEnteringState(getInstance());
		}
	}

	public PublishSubject<List<String>> getCodeLines() {
		return codeLines;
	}

	public PIC16F84 getPicController() {
		return picController;
	}

	public List<String> getCurrentCode() {
		return currentCode;
	}

	public void setCurrentCode(List<String> currentCode) {
		this.currentCode = currentCode;
		notifyCodeChanged();
		loadCodeIntoProgramMemory();
	}

	private void notifyCodeChanged() {
		codeLines.onNext(currentCode);
	}

	private void loadCodeIntoProgramMemory() {
		for (String codeLine : currentCode) {
			String opcode = codeLine.substring(0, 9);
			if(!opcode.isBlank()) {
				String[] splitOpcode = opcode.split(" ");
				getPicController().loadOpcodeIntoProgramMemory(splitOpcode[0], splitOpcode[1]);
			}
		}
	}
}
