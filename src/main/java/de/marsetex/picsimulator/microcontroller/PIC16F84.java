package de.marsetex.picsimulator.microcontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PIC16F84 {

    private static final Logger LOGGER = LogManager.getLogger(PIC16F84.class);

    private short[] programMemory = new short[8192];

    public void loadOpcodeIntoProgramMemory(String addressOfOpcode, String opcode) {
        programMemory[Short.parseShort(addressOfOpcode, 16)] = Short.parseShort(opcode, 16);
        LOGGER.info("Stored " + opcode + " in address: " + addressOfOpcode);
    }
}
