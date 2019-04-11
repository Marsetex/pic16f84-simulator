package de.marsetex.picsimulator.instruction.literal;

import de.marsetex.picsimulator.instruction.IPicInstruction;
import de.marsetex.picsimulator.microcontroller.PIC16F84;

public class Xorlw implements IPicInstruction {

    private int k;

    public Xorlw(short opcode) {
        k = opcode & 0x00FF;
    }

    @Override
    public void execute(PIC16F84 pic) {
        pic.setWRegister((byte) (k ^ pic.getWRegister()));
    }
}
