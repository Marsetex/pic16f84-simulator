package de.marsetex.pic16f84sim.microcontroller.register;

public class ProgramCounter {

    private int programCounter;

    public ProgramCounter() {
        programCounter = 0;
    }

    public void resetProgramCounter() {
        setProgramCounterValue(0);
    }

    public void incrementProgramCounter() {
        programCounter++;
    }

    public int getProgramCounterValue() {
        return programCounter;
    }

    public void setProgramCounterValue(int newCounterCalue) {
        programCounter = newCounterCalue;
    }
}
