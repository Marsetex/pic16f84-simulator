package de.marsetex.pic16f84sim.instruction;

import de.marsetex.pic16f84sim.microcontroller.register.helper.StatusRegisterHelper;

public abstract class StatusFlagChangerInstruction implements IPicInstruction {

    protected void isValueEqualsZero(byte result) {
        if(result == 0x0) {
            StatusRegisterHelper.setZFlag();
        } else {
            StatusRegisterHelper.resetZFlag();
        }
    }
}
