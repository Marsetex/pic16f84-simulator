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

    protected void checkDigitCarry2(int w, int literal) {
        if((w & 0x0F) + (literal & 0x0F)  > 0x0F) {
            StatusRegisterHelper.setDCFlag();
        } else {
            StatusRegisterHelper.resetDCFlag();
        }
    }

    protected void hasOverflowOccured2(int result) {
        if(result > 0x0FF) {
            StatusRegisterHelper.setCFlag();
        } else {
            StatusRegisterHelper.setCFlag();
        }
    }
}
