package de.marsetex.pic16f84sim.instruction.control;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

/**
 * Go to address
 * Datasheet: Page 62
 */
public class Goto implements IPicInstruction {

    private short address;

    public Goto(short opcode) {
        address = (short) (opcode & 0x07FF);
    }

    @Override
    public int execute(PIC16F84 pic) {
        pic.getProgramCounter().setProgramCounterValue(address);
        return 2;
    }
}
