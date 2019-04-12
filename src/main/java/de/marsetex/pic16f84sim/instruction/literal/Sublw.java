package de.marsetex.pic16f84sim.instruction.literal;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

/**
 * Subtract W from literal k. Sets flags: C, DC and Z
 * Datasheet: Page 68
 */
public class Sublw implements IPicInstruction {

    private byte k;

    public Sublw(short opcode) {
        k = (byte) opcode;
    }

    @Override
    public void execute(PIC16F84 pic) {
        int tempW = ~pic.getWRegister()+1;
        int result = k+tempW;

        isValueEqualsZero((byte) result);
        hasOverflowOccured(result);
        checkDigitCarry(tempW,k);
        pic.setWRegister((byte) result);
    }

    private void checkDigitCarry(int tempW, int k) {
        if((k & 0x0F) + (tempW & 0x0F)  > 0x0F) {
            // set flag DC to 1
        } else {
            // set flag DC to 0
        }
    }

    private void hasOverflowOccured(int result) {
        if(result >= 0) {
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
