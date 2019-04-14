package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

/**
 * Move W to f
 * Datasheet: Page 64
 */
public class Movwf implements IPicInstruction {

    private final byte fileRegister;

    public Movwf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
    }

    @Override
    public void execute(PIC16F84 pic) {
        byte w = pic.getWRegister().getWRegisterValue();
        pic.getDataMemory().store(fileRegister, w);
    }
}
