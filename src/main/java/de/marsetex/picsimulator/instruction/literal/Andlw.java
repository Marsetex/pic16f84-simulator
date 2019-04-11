package de.marsetex.picsimulator.instruction.literal;

import de.marsetex.picsimulator.instruction.IPicInstruction;
import de.marsetex.picsimulator.microcontroller.PIC16F84;

public class Andlw implements IPicInstruction {

    private int k;

    public Andlw(short opcode) {
        k = opcode & 0x00FF;
    }

    @Override
    public void execute(PIC16F84 pic) {
        pic.setWRegister((byte) ((byte) k & pic.getWRegister()));
    }
}
