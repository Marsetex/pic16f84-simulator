package de.marsetex.pic16f84sim.microcontroller.register;

public class ProgramCounter {

    private int programCounter;

    public ProgramCounter() {
        programCounter = 0;
    }

    public void resetProgramCounter() {
        setProgramCounter(0);
    }

    public void incrementProgramCounter() {
        programCounter++;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int newCounterCalue) {
        programCounter = newCounterCalue;
    }
}
