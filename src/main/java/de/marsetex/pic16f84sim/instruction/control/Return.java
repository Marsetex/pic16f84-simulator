package de.marsetex.pic16f84sim.instruction.control;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.memory.Stack;
import de.marsetex.pic16f84sim.microcontroller.register.ProgramCounter;

/**
 * Return from Subroutine
 * Datasheet: Page 66
 */
public class Return implements IPicInstruction {

    @Override
    public void execute(PIC16F84 pic) {
        ProgramCounter pc = pic.getProgramCounter();
        Stack stack = pic.getStack();

        pc.setProgramCounter(stack.pop());
    }
}
