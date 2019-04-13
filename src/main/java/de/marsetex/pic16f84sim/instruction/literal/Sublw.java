package de.marsetex.pic16f84sim.instruction.literal;

import de.marsetex.pic16f84sim.instruction.StatusFlagChangerInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

/**
 * Subtract W from literal. Sets flags: C, DC and Z
 * Datasheet: Page 68
 */
public class Sublw extends StatusFlagChangerInstruction {

    private byte literal;

    public Sublw(short opcode) {
        literal = (byte) opcode;
    }

    @Override
    public void execute(PIC16F84 pic) {
        int wTwosComplement = ~pic.getWRegister().getWRegister()+1;
        int result = literal +wTwosComplement;

        isValueEqualsZero(pic, (byte) result);
        hasOverflowOccured(pic, result);
        checkDigitCarry(pic, wTwosComplement, literal);

        pic.getWRegister().setWRegister((byte) result);
    }

    private void checkDigitCarry(PIC16F84 pic, int w, int k) {
        if((k & 0x0F) + (w & 0x0F)  > 0x0F) {
            pic.getStatusRegister().setDCFlag();
        } else {
            pic.getStatusRegister().resetDCFlag();
        }
    }

    private void hasOverflowOccured(PIC16F84 pic, int result) {
        if(result >= 0x0) {
            pic.getStatusRegister().setCFlag();
        } else {
            pic.getStatusRegister().resetDCFlag();
        }
    }
}
