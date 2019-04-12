package de.marsetex.pic16f84sim.instruction.literal;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

/**
 * Inclusive OR literal k with W. Sets flags: Z
 * Datasheet: Page 63
 */
public class Iorlw implements IPicInstruction {

    private byte k;

    public Iorlw(short opcode) {
        k = (byte) opcode;
    }

    @Override
    public void execute(PIC16F84 pic) {
        byte result = (byte) (pic.getWRegister() | k);
        isValueEqualsZero(result);
        pic.setWRegister(result);
    }

    private void isValueEqualsZero(byte result) {
        if(result == 0x0) {
            // set flag Z to 1
        } else {
            // set flag Z to 0
        }
    }
}
