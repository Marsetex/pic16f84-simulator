package de.marsetex.pic16f84sim.microcontroller;

import de.marsetex.pic16f84sim.microcontroller.register.ProgramCounter;
import de.marsetex.pic16f84sim.microcontroller.register.StatusRegister;
import de.marsetex.pic16f84sim.microcontroller.register.WRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PIC16F84 {

    private static final Logger LOGGER = LogManager.getLogger(PIC16F84.class);

    private final short[] programMemory;

    private final WRegister wRegister;
    private final StatusRegister statusRegister;

    private final ProgramCounter counter;

    private byte[] ramBank0 = new byte[128];
    private byte[] ramBank1 = new byte[128];

    private byte portA;
    private byte portB;
    private byte trisA;
    private byte trisB;
    private byte option;
    private byte pclath;
    private byte intcon;
    private byte fsr;
    private byte status;
    private byte pcl;
    private byte tmr0;
    private byte ind;

    public PIC16F84() {
        programMemory = new short[8192];
        wRegister = new WRegister();
        statusRegister = new StatusRegister();
        counter = new ProgramCounter();
    }

    public void loadOpcodeIntoProgramMemory(String addressOfOpcode, String opcode) {
        programMemory[Short.parseShort(addressOfOpcode, 16)] = Short.parseShort(opcode, 16);
        LOGGER.info("Stored " + opcode + " in address: " + addressOfOpcode);
    }

    public short getNextInstruction() {
        return programMemory[counter.getProgramCounter()];
    }

    public WRegister getWRegister() {
        return wRegister;
    }

    public ProgramCounter getProgramCounter() {
        return counter;
    }

    public StatusRegister getStatusRegister() {
        return statusRegister;
    }
}
