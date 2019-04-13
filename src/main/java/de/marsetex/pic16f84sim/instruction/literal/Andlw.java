package de.marsetex.pic16f84sim.instruction.literal;

import de.marsetex.pic16f84sim.instruction.StatusFlagChangerInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

/**
 * AND literal with W. Sets flags: Z
 * Datasheet: Page 57
 */
public class Andlw extends StatusFlagChangerInstruction {

    private byte literal;

    public Andlw(short opcode) {
        literal = (byte) opcode;
    }

    @Override
    public void execute(PIC16F84 pic) {
        byte result = (byte) (pic.getWRegister().getWRegister() & literal);

        isValueEqualsZero(pic, result);

        pic.getWRegister().setWRegister(result);
    }
}
