package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.StatusFlagChangerInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;
import de.marsetex.pic16f84sim.microcontroller.register.WRegister;

/**
 * Add W and f. Sets flags: C, DC, Z
 * Datasheet: Page 57
 */
public class Addwf extends StatusFlagChangerInstruction {

    private final byte fileRegister;
    private final short destination;

    public Addwf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public void execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();
        WRegister wRegister = pic.getWRegister();

        byte wValue = wRegister.getWRegisterValue();
        byte fValue = dataMemory.load(fileRegister);

        int result = wValue + fValue;
        isValueEqualsZero((byte) result);
        hasOverflowOccured2(result);
        checkDigitCarry2(wValue, fValue);

        if(destination == 0) {
            wRegister.setWRegisterValue((byte) result);
        } else {
            dataMemory.store(fileRegister, (byte) result);
        }
    }
}
