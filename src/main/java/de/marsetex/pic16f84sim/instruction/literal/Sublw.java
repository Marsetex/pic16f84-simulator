package de.marsetex.pic16f84sim.instruction.literal;

import de.marsetex.pic16f84sim.instruction.StatusFlagChangerInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.register.helper.StatusRegisterHelper;

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
        int wTwosComplement = ~pic.getWRegister().getWRegisterValue()+1;
        int result = literal +wTwosComplement;

        isValueEqualsZero((byte) result);
        hasOverflowOccuredSubstraction(result);
        checkDigitCarrySubstraction(wTwosComplement, literal);

        pic.getWRegister().setWRegisterValue((byte) result);
    }
}
