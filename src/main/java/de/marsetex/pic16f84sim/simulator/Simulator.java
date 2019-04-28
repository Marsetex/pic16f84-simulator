package de.marsetex.pic16f84sim.simulator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

import de.marsetex.pic16f84sim.decoder.InstructionDecoder;
import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;
import de.marsetex.pic16f84sim.state.ISimState;
import de.marsetex.pic16f84sim.state.SimStateIdle;
import de.marsetex.pic16f84sim.state.SimStateNoFile;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.state.SimStateStepMode;
import de.marsetex.pic16f84sim.ui.models.CodeModel;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Simulator implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(Simulator.class);

	private static Simulator simulator;

	private final PIC16F84 picController;
	private final InstructionDecoder decoder;

	private final PublishSubject<List<String>> codeLines;
	private final PublishSubject<Integer> currentExecutedCode;
	private final PublishSubject<List<Integer>> breakpointSubject;
	private final PublishSubject<Double> runtimeCounterSubject;
	private final PublishSubject<String> debugConsole;
	private final PublishSubject<String> fileLoad;

	private File currentLstFile;
	private List<String> currentCode;
	private List<Integer> breakpoints;
	private ISimState currentState;
	private boolean simulationRunning;
	private long quartzFrequency = 4000000;
	private double runtimeCounter;

	private Simulator() {
		simulator = null;
		picController = new PIC16F84();
		decoder = new InstructionDecoder();
		breakpoints = new ArrayList<>();

		codeLines = PublishSubject.create();
		currentExecutedCode = PublishSubject.create();
		breakpointSubject = PublishSubject.create();
		runtimeCounterSubject = PublishSubject.create();
		debugConsole = PublishSubject.create();
		fileLoad = PublishSubject.create();

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
			int pcValue = picController.getProgramCounter().getProgramCounterValue();

			for(Integer breakpointPosition : breakpoints) {
				if(pcValue == breakpointPosition.intValue()) {
					simulationRunning = false;
					changeState(new SimStateStepMode());
					break;
				}
			}

			if(simulationRunning != false) {
				executeSingleInstruction();

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void executeSingleInstruction() {
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
		breakpoints.clear();

		notifyBreakpointChange();
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

	public void setCurrentLstFile(File lstFile) {
		currentLstFile = lstFile;
	}

	public void setQuartzFrequency(long longValue) {
		quartzFrequency = longValue;
	}

	public void removeBreakpoint(CodeModel selectedCode) {
		int newBreakpointPosition = Integer.parseInt(selectedCode.getCodeLine().substring(0, 4), 16);

		for(int i = 0; i < breakpoints.size(); i++) {
			if(newBreakpointPosition == breakpoints.get(i).intValue()) {
				breakpoints.remove(i);
				notifyBreakpointChange();
				break;
			}
		}
	}

	public void addBreakpoint(CodeModel selectedCode) {
		String code = selectedCode.getCodeLine().substring(0, 4);

		if(!code.isBlank()) {
			breakpoints.add(Integer.parseInt(code,16));
			notifyBreakpointChange();
		}
	}

	public PIC16F84 getPicController() {
		return picController;
	}

	public File getCurrentLstFile() {
		return currentLstFile;
	}

	public PublishSubject<List<String>> getCodeLines() {
		return codeLines;
	}

	public PublishSubject<Integer> getCurrentExecutedCode() {
		return currentExecutedCode;
	}

	public PublishSubject<List<Integer>> getBreakpoints() { return breakpointSubject; }

	public PublishSubject<Double> getRuntimeCounterSubject() {
		return runtimeCounterSubject;
	}

	public PublishSubject<String> getDebugConsole() {
		return debugConsole;
	}

	public PublishSubject<String> getFileLoad() {
		return fileLoad;
	}

	private void notifyCodeChanged() {
		codeLines.onNext(currentCode);
	}

	private void notifyCurrentExecutedCode() {
		currentExecutedCode.onNext(picController.getProgramCounter().getProgramCounterValue());
	}

	private void notifyBreakpointChange() {
		breakpointSubject.onNext(breakpoints);
	}

	private void notifyRuntimeCounter(double runtimeCounter) {
		runtimeCounterSubject.onNext(runtimeCounter);
	}

	private void notifyDebugConsole(String s) {
		debugConsole.onNext(s);
	}
}
