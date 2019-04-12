package de.marsetex.pic16f84sim.instruction;

import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

public interface IPicInstruction {

    public void execute(PIC16F84 pic);
}
