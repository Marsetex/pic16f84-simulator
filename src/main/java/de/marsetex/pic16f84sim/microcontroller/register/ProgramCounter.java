package de.marsetex.pic16f84sim.microcontroller.register;

import io.reactivex.subjects.PublishSubject;

public class ProgramCounter {

    private final PublishSubject<Integer> pcSubject;
    private int programCounter;

    public ProgramCounter(PublishSubject<Integer> subject) {
        pcSubject = subject;

        programCounter = 0;
    }

    public void resetProgramCounter() {
        setProgramCounterValue(0);
    }

    public void incrementProgramCounter() {
        programCounter++;
        notifyUpdate();
    }

    public int getProgramCounterValue() {
        return programCounter;
    }

    public void setProgramCounterValue(int newCounterCalue) {
        programCounter = newCounterCalue;
        notifyUpdate();
    }

    private void notifyUpdate() {
        pcSubject.onNext(programCounter);
    }
}
