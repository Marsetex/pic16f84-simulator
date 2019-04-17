package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;
import de.marsetex.pic16f84sim.microcontroller.register.WRegister;

/**
 * Swap nibbles in f
 * Datasheet: Page 69
 */
public class Swapf implements IPicInstruction {

    private final byte fileRegister;
    private final short destination;

    public Swapf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public void execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();

        byte fValue = dataMemory.load(fileRegister);

        byte upperBits = (byte) (fValue << 4);
        byte lowerBits = (byte) (fValue >> 4);

        fValue = (byte) (lowerBits | upperBits);

        if(destination == 0) {
            pic.getWRegister().setWRegisterValue(fValue);
        } else {
            dataMemory.store(fileRegister, fValue);
        }
    }
}
