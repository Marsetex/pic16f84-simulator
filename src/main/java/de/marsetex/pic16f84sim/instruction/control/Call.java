package de.marsetex.pic16f84sim.instruction.control;

import de.marsetex.pic16f84sim.instruction.IPicInstruction;
import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.register.ProgramCounter;

/**
 * Call subroutine
 * Datasheet: Page 59
 */
public class Call implements IPicInstruction {

    private final short address;

    public Call(short opcode) {
        address = (short) (opcode & 0x07FF);
    }

    @Override
    public void execute(PIC16F84 pic) {
        ProgramCounter pc = pic.getProgramCounter();
        
        pic.getStack().push((short) (pc.getProgramCounter()+1));
        pc.setProgramCounter(address);
    }
}
