package de.marsetex.pic16f84sim.instruction.control;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

public class Clrwdt implements IPicInstruction {

    @Override
    public int execute(PIC16F84 pic) {
        return 1;
    }
}
