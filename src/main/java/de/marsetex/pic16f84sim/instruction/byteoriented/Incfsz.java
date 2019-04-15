package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

/**
 * Increment f, Skip if 0
 * Datasheet: Page 63
 */
public class Incfsz implements IPicInstruction {

    private final byte fileRegister;
    private final short destination;

    public Incfsz(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public void execute(PIC16F84 pic) {

    }
}
