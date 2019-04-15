package de.marsetex.pic16f84sim.instruction.byteoriented;

import de.marsetex.pic16f84sim.instruction.StatusFlagChangerInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;

/**
 * Complement f. Sets flag: Z
 * Datasheet: Page 61
 */
public class Comf extends StatusFlagChangerInstruction {

    private final byte fileRegister;
    private final short destination;

    public Comf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public void execute(PIC16F84 pic) {

    }
}
