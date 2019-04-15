package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

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

    }
}
