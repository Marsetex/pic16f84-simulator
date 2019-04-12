package de.marsetex.picsimulator.instruction.literal;

import de.marsetex.picsimulator.instruction.IPicInstruction;
import de.marsetex.picsimulator.microcontroller.PIC16F84;

/**
 * AND literal k with W. Sets flags: Z
 * Datasheet: Page 57
 */
public class Andlw implements IPicInstruction {

    private byte k;

    public Andlw(short opcode) {
        k = (byte) opcode;
    }

    @Override
    public void execute(PIC16F84 pic) {
        byte result = (byte) (pic.getWRegister() & k);
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
