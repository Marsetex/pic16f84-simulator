package de.marsetex.pic16f84sim.instruction.literal;

import de.marsetex.pic16f84sim.instruction.StatusFlagChangerInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.register.helper.StatusRegisterHelper;

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
        byte w = pic.getWRegister().getWRegisterValue();
        int result = w + literal;

        isValueEqualsZero((byte) result);
        hasOverflowOccured(result);
        checkDigitCarry(w, literal);

        pic.getWRegister().setWRegisterValue((byte) result);
    }

    private void checkDigitCarry(int w, int literal) {
        if((w & 0x0F) + (literal & 0x0F)  > 0x0F) {
            StatusRegisterHelper.setDCFlag();
        } else {
            StatusRegisterHelper.resetDCFlag();
        }
    }

    private void hasOverflowOccured(int result) {
        if(result > 0x0FF) {
            StatusRegisterHelper.setCFlag();
        } else {
            StatusRegisterHelper.setCFlag();
        }
    }
}
