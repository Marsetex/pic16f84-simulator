package de.marsetex.pic16f84sim.microcontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PIC16F84 {

    private static final Logger LOGGER = LogManager.getLogger(PIC16F84.class);

    private short[] programMemory = new short[8192];

    private byte[] ramBank0 = new byte[128];
    private byte[] ramBank1 = new byte[128];

    private byte wRegister;
    private int programCounter = 0;

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

    public short getNextInstruction() {
        return programMemory[programCounter];
    }

    public void loadOpcodeIntoProgramMemory(String addressOfOpcode, String opcode) {
        programMemory[Short.parseShort(addressOfOpcode, 16)] = Short.parseShort(opcode, 16);
        LOGGER.info("Stored " + opcode + " in address: " + addressOfOpcode);
    }

    public byte getWRegister() {
        return wRegister;
    }

    public void setWRegister(byte wRegister) {
        this.wRegister = wRegister;
    }

    public void resetProgramCounter() {
        programCounter = 0;
    }

    public void incrementProgramCounter() {
        programCounter++;
    }

    public void setProgramCounterValue(short k) {
        programCounter = k;
    }
}
