package de.marsetex.pic16f84sim.simulator;

import java.util.List;

import de.marsetex.pic16f84sim.decoder.InstructionDecoder;
import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.state.ISimState;
import de.marsetex.pic16f84sim.state.SimStateNoFile;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
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
			// Fetch
			short opcode = picController.getProgramMemory().getNextInstruction();
			picController.getProgramCounter().incrementProgramCounter();

			// Decode
			IPicInstruction instruction = decoder.decode(opcode);

			// Execute
			instruction.execute(picController);
			LOGGER.info("Executed: " + instruction.getClass().getSimpleName());

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopSimulation() {
		simulationRunning = false;
	}

	public void reset() {
		picController.getProgramCounter().resetProgramCounter();
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
				picController.getProgramMemory().loadOpcodeIntoProgramMemory(splitOpcode[0], splitOpcode[1]);
			}
		}
	}
}
