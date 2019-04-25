package de.marsetex.pic16f84sim.simulator;

import java.util.List;

import de.marsetex.pic16f84sim.decoder.InstructionDecoder;
import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;
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
	private final PublishSubject<Integer> currentExecutedCode;
	private final PublishSubject<Double> runtimeCounterSubject;
	private final PublishSubject<String> debugConsole;

	private List<String> currentCode;
	private ISimState currentState;
	private boolean simulationRunning;
	private long quartzFrequency = 4000000;
	private double runtimeCounter;

	private Simulator() {
		simulator = null;
		picController = new PIC16F84();
		decoder = new InstructionDecoder();

		codeLines = PublishSubject.create();
		currentExecutedCode = PublishSubject.create();
		runtimeCounterSubject = PublishSubject.create();
		debugConsole = PublishSubject.create();

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
			notifyCurrentExecutedCode();
			picController.getProgramCounter().incrementProgramCounter();

			// Decode
			IPicInstruction instruction = decoder.decode(opcode);

			// Execute
			LOGGER.info("Executing: " + instruction.getClass().getSimpleName());
			notifyDebugConsole("Executing: " + instruction.getClass().getSimpleName());
			int cycles = instruction.execute(picController);
			updateRuntimeCounter(cycles);

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

	public void resetSimulation() {
		DataMemory dataMemory = picController.getDataMemory();

		picController.getProgramCounter().resetProgramCounter();
		dataMemory.store((byte) 0x02, (byte) 0x0); // Reset PCL
		dataMemory.store((byte) 0x0A, (byte) 0x0); // Reset PCLATH

		dataMemory.store((byte) 0x03, (byte) 0x18); // Reset STATUS
		dataMemory.store((byte) 0x0B, (byte) 0x00); // Reset INTCON
		dataMemory.store((byte) 0x85, (byte) 0x1F); // Reset TRISA
		dataMemory.store((byte) 0x86, (byte) 0xFF); // Reset TRISB
		dataMemory.store((byte) 0x81, (byte) 0xFF); // Reset OPTION

		runtimeCounter = 0;
		notifyCurrentExecutedCode();
		notifyRuntimeCounter(runtimeCounter);
	}

	public boolean changeState(ISimState newState) {
		boolean transitionAllowed = currentState.isTransitionAllowed(newState);
		if (transitionAllowed) {
			currentState.onLeavingState(getInstance());
			currentState = newState;
			currentState.onEnteringState(getInstance());
		}
		return transitionAllowed;
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

	private void updateRuntimeCounter(int cycles) {
		double timePerCycle = 4000000.0 / quartzFrequency;
		runtimeCounter = runtimeCounter + (timePerCycle * cycles);
		notifyRuntimeCounter(runtimeCounter);
	}

	public void setCurrentCode(List<String> currentCode) {
		this.currentCode = currentCode;
		notifyCodeChanged();
		loadCodeIntoProgramMemory();
	}

	public void setQuartzFrequency(long longValue) {
		quartzFrequency = longValue;
	}

	public PIC16F84 getPicController() {
		return picController;
	}

	public PublishSubject<List<String>> getCodeLines() {
		return codeLines;
	}

	public PublishSubject<Integer> getCurrentExecutedCode() {
		return currentExecutedCode;
	}

	public PublishSubject<Double> getRuntimeCounterSubject() {
		return runtimeCounterSubject;
	}

	public PublishSubject<String> getDebugConsole() {
		return debugConsole;
	}

	private void notifyCodeChanged() {
		codeLines.onNext(currentCode);
	}

	private void notifyCurrentExecutedCode() {
		currentExecutedCode.onNext(picController.getProgramCounter().getProgramCounterValue());
	}

	private void notifyRuntimeCounter(double runtimeCounter) {
		runtimeCounterSubject.onNext(runtimeCounter);
	}

	private void notifyDebugConsole(String s) {
		debugConsole.onNext(s);
	}
}
