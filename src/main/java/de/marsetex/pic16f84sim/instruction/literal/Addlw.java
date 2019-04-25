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
    public int execute(PIC16F84 pic) {
        byte w = pic.getWRegister().getWRegisterValue();
        int result = w + literal;

        isValueEqualsZero((byte) result);
        hasOverflowOccured(result);
        checkDigitCarry(w, literal);

        pic.getWRegister().setWRegisterValue((byte) result);

        return 1;
    }
}
