package de.marsetex.picsimulator.instruction;

import de.marsetex.picsimulator.microcontroller.PIC16F84;

public interface IPicInstruction {

    public void execute(PIC16F84 pic);
}
