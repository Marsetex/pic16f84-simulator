package de.marsetex.picsimulator.instruction.literal;

import de.marsetex.picsimulator.instruction.IPicInstruction;
import de.marsetex.picsimulator.microcontroller.PIC16F84;

public class Movlw implements IPicInstruction {

    private int opcodePrefix;
    private int variableK;

    public Movlw(short opcode) {
        opcodePrefix = opcode >> 8;
        variableK = opcode & 0x00FF;
    }

    @Override
    public void execute(PIC16F84 pic) {
        pic.setWRegister((byte) variableK);
    }
}
