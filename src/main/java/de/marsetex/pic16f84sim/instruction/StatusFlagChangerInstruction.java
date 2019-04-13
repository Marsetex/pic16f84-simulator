package de.marsetex.pic16f84sim.instruction;

import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

public abstract class StatusFlagChangerInstruction implements IPicInstruction {

    protected void isValueEqualsZero(PIC16F84 pic, byte result) {
        if(result == 0x0) {
            pic.getStatusRegister().setZFlag();
        } else {
            pic.getStatusRegister().resetZFlag();
        }
    }
}
