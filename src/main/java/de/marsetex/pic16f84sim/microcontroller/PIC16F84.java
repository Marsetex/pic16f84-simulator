package de.marsetex.pic16f84sim.microcontroller;

import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;
import de.marsetex.pic16f84sim.microcontroller.memory.ProgramMemory;
import de.marsetex.pic16f84sim.microcontroller.memory.Stack;
import de.marsetex.pic16f84sim.microcontroller.register.ProgramCounter;
import de.marsetex.pic16f84sim.microcontroller.register.Timer0;
import de.marsetex.pic16f84sim.microcontroller.register.WRegister;
import io.reactivex.subjects.PublishSubject;

public class PIC16F84 {

    private final PublishSubject<byte[]> gprSubject;
    private final PublishSubject<byte[]> sfrSubject;
    private final PublishSubject<short[]> stackSubject;
    private final PublishSubject<Integer> stackPointerSubject;
    private final PublishSubject<Byte> wRegisterSubject;
    private final PublishSubject<Integer> pcSubject;

    private final ProgramMemory programMemory;
    private final DataMemory dataMemory;
    private final Stack stack;

    private final WRegister wRegister;
    private final ProgramCounter programCounter;
    private final Timer0 timer0;

    public PIC16F84() {
        gprSubject = PublishSubject.create();
        sfrSubject = PublishSubject.create();
        stackSubject = PublishSubject.create();
        stackPointerSubject = PublishSubject.create();
        wRegisterSubject = PublishSubject.create();
        pcSubject = PublishSubject.create();

        programMemory = new ProgramMemory();
        dataMemory = new DataMemory(gprSubject, sfrSubject);
        stack = new Stack(stackSubject, stackPointerSubject);

        wRegister = new WRegister(wRegisterSubject);
        programCounter = new ProgramCounter(pcSubject);
        timer0 = new Timer0(this);
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

    public Timer0 getTimer0() {
        return timer0;
    }

    public PublishSubject<byte[]> getGprSubject() {
        return gprSubject;
    }

    public PublishSubject<byte[]> getSfrSubject() {
        return sfrSubject;
    }

    public PublishSubject<short[]> getStackSubject() {
        return stackSubject;
    }

    public PublishSubject<Integer> getStackPointerSubject() {
        return stackPointerSubject;
    }

    public PublishSubject<Byte> getWRegisterSubject() {
        return wRegisterSubject;
    }

    public PublishSubject<Integer> getPcSubject() {
        return pcSubject;
    }
}
