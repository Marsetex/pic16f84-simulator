package de.marsetex.picsimulator.instruction.literal;

import de.marsetex.picsimulator.instruction.IPicInstruction;
import de.marsetex.picsimulator.microcontroller.PIC16F84;

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
