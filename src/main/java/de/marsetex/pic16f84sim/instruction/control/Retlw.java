package de.marsetex.pic16f84sim.instruction.control;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.memory.Stack;
import de.marsetex.pic16f84sim.microcontroller.register.ProgramCounter;

/**
 * Return with literal in W
 * Datasheet: Page 66
 */
public class Retlw implements IPicInstruction {

    private final byte literal;

    public Retlw(short opcode) {
        literal = (byte) opcode;
    }

    @Override
    public void execute(PIC16F84 pic) {
        ProgramCounter pc = pic.getProgramCounter();
        Stack stack = pic.getStack();

        pic.getWRegister().setWRegisterValue(literal);
        pc.setProgramCounterValue(stack.pop());
    }
}
