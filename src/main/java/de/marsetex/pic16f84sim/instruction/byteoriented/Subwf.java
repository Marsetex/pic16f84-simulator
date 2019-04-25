package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.instruction.StatusFlagChangerInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;
import de.marsetex.pic16f84sim.microcontroller.register.WRegister;

/**
 * Subtract W from f. Sets flags: C, DC, Z
 * Datasheet: Page 69
 */
public class Subwf extends StatusFlagChangerInstruction {

    private final byte fileRegister;
    private final short destination;

    public Subwf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public int execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();
        WRegister wRegister = pic.getWRegister();

        int wTwosComplement = (~pic.getWRegister().getWRegisterValue() + 1) & 0xFF;
        byte fValue = dataMemory.load(fileRegister);
        int result = fValue + wTwosComplement;

        isValueEqualsZero((byte) result);
        hasOverflowOccured(result);
        checkDigitCarry(wTwosComplement, fValue);

        if(destination == 0) {
            wRegister.setWRegisterValue((byte) result);
        } else {
            dataMemory.store(fileRegister, (byte) result);
        }

        return 1;
    }
}
