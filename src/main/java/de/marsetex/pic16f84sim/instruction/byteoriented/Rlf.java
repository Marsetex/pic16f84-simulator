package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;
import de.marsetex.pic16f84sim.microcontroller.register.helper.StatusRegisterHelper;

/**
 * Rotate Left f through Carry. Sets flag: C
 * Datasheet: Page 67
 */
public class Rlf implements IPicInstruction {

    private final byte fileRegister;
    private final short destination;

    public Rlf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public void execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();

        byte fValue = dataMemory.load(fileRegister);
        byte cFlag = StatusRegisterHelper.getCFlag();

        if((fValue & 0x80) == 0) {
            StatusRegisterHelper.resetCFlag();
        } else {
            StatusRegisterHelper.setCFlag();
        }

        byte result = (byte) (fValue << 1);
        result = (byte) (result | cFlag);

        if(destination == 0) {
            pic.getWRegister().setWRegisterValue((byte) result);
        } else {
            dataMemory.store(fileRegister, (byte) result);
        }
    }
}
