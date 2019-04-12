package de.marsetex.picsimulator.instruction.literal;

import de.marsetex.picsimulator.instruction.IPicInstruction;
import de.marsetex.picsimulator.microcontroller.PIC16F84;

/**
 * Move literal k to W register
 * Datasheet: Page 64
 */
public class Movlw implements IPicInstruction {

    private byte k;

    public Movlw(short opcode) {
        k = (byte) opcode;
    }

    @Override
    public void execute(PIC16F84 pic) {
        pic.setWRegister(k);
    }
}
