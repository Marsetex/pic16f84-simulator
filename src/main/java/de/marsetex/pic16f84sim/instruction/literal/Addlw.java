package de.marsetex.pic16f84sim.instruction.literal;

import de.marsetex.pic16f84sim.instruction.StatusFlagChangerInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

/**
 * Add literal and value of W register. Sets flags: C, DC and Z
 * Datasheet: Page 57
 */
public class Addlw extends StatusFlagChangerInstruction {

    private byte literal;

    public Addlw(short opcode) {
        literal = (byte) opcode;
    }

    @Override
    public void execute(PIC16F84 pic) {
        byte w = pic.getWRegister().getWRegister();
        int result = w + literal;

        isValueEqualsZero(pic, (byte) result);
        hasOverflowOccured(pic, result);
        checkDigitCarry(pic, w, literal);

        pic.getWRegister().setWRegister((byte) result);
    }

    private void checkDigitCarry(PIC16F84 pic, int w, int literal) {
        if((w & 0x0F) + (literal & 0x0F)  > 0x0F) {
            pic.getStatusRegister().setDCFlag();
        } else {
            pic.getStatusRegister().resetDCFlag();
        }
    }

    private void hasOverflowOccured(PIC16F84 pic, int result) {
        if(result > 0x0FF) {
            pic.getStatusRegister().setCFlag();
        } else {
            pic.getStatusRegister().resetCFlag();
        }
    }
}
