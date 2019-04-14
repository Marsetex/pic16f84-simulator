package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

/**
 * No Operation
 * Datasheet: Page 65
 */
public class Nop implements IPicInstruction {

    @Override
    public void execute(PIC16F84 pic) {
        // Do nothing
    }
}
