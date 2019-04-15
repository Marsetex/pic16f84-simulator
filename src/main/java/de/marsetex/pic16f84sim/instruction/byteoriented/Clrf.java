package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.StatusFlagChangerInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;

/**
 * Clear f
 * Datasheet: Page 60
 */
public class Clrf extends StatusFlagChangerInstruction {

    private final byte fileRegister;

    public Clrf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
    }

    @Override
    public void execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();

        isValueEqualsZero((byte) 0);

        dataMemory.store(fileRegister, (byte) 0);
    }
}
