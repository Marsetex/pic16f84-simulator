package de.marsetex.pic16f84sim.instruction.literal;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

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
