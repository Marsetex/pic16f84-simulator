package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.StatusFlagChangerInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.register.WRegister;

/**
 * Clear W
 * Datasheet: Page 60
 */
public class Clrw extends StatusFlagChangerInstruction {

    @Override
    public void execute(PIC16F84 pic) {
        isValueEqualsZero((byte) 0);

        pic.getWRegister().setWRegisterValue((byte) 0);
    }
}
