package de.marsetex.picsimulator.instruction.control;

import de.marsetex.picsimulator.instruction.IPicInstruction;
import de.marsetex.picsimulator.microcontroller.PIC16F84;

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
    public void execute(PIC16F84 pic) {
        pic.setProgramCounterValue(address);
    }
}
