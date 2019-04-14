package de.marsetex.pic16f84sim.microcontroller;

import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;
import de.marsetex.pic16f84sim.microcontroller.memory.ProgramMemory;
import de.marsetex.pic16f84sim.microcontroller.memory.Stack;
import de.marsetex.pic16f84sim.microcontroller.register.ProgramCounter;
import de.marsetex.pic16f84sim.microcontroller.register.WRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PIC16F84 {

    private final ProgramMemory programMemory;
    private final DataMemory dataMemory;
    private final Stack stack;

    private final WRegister wRegister;
    private final ProgramCounter programCounter;

    public PIC16F84() {
        programMemory = new ProgramMemory();
        dataMemory = new DataMemory();
        stack = new Stack();

        wRegister = new WRegister();
        programCounter = new ProgramCounter();
    }

    public ProgramMemory getProgramMemory() {
        return programMemory;
    }

    public DataMemory getDataMemory() {
        return dataMemory;
    }

    public Stack getStack() {
        return stack;
    }

    public WRegister getWRegister() {
        return wRegister;
    }

    public ProgramCounter getProgramCounter() {
        return programCounter;
    }
}
