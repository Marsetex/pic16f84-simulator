package de.marsetex.picsimulator.instruction.literal;

import de.marsetex.picsimulator.instruction.IPicInstruction;
import de.marsetex.picsimulator.microcontroller.PIC16F84;

/**
 * Add literal k and value of W register. Sets flags: C, DC and Z
 * Datasheet: Page 57
 */
public class Addlw implements IPicInstruction {

    private byte k;

    public Addlw(short opcode) {
        k = (byte) opcode;
    }

    @Override
    public void execute(PIC16F84 pic) {
        int result = pic.getWRegister() + k;
        isValueEqualsZero((byte) result);
        hasOverflowOccured(result);
        checkDigitCarry(result, k);
        pic.setWRegister((byte) result);
    }

    private void checkDigitCarry(int tempW, int k) {
        if((tempW & 0x0F) + (k & 0x0F)  > 0x0F) {
            // set flag DC to 1
        } else {
            // set flag DC to 0
        }
    }

    private void hasOverflowOccured(int result) {
        if(result > 0x0FF) {
            // set flag C to 1
        } else {
            // set flag C to 0
        }
    }

    private void isValueEqualsZero(byte result) {
        if(result == 0x0) {
            // set flag Z to 1
        } else {
            // set flag Z to 0
        }
    }
}
