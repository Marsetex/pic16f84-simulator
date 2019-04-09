package de.marsetex.picsimulator.instruction.decoder;

import de.marsetex.picsimulator.instruction.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InstructionDecoder {

    private static final Logger LOGGER = LogManager.getLogger(InstructionDecoder.class);

    public IInstruction decode(short opcode) {
        switch (opcode >> 12) {
            case 0b000:
                return decodeOpcodeWithPrefix00(opcode);
            case 0b001:
                return decodeOpcodeWithPrefix01(opcode);
            case 0b010:
                return decodeOpcodeWithPrefix10(opcode);
            case 0b011:
                return decodeOpcodeWithPrefix11(opcode);
            default:
                LOGGER.error("Unknown opcode prefix");
                return null;
        }
    }

    private IInstruction decodeOpcodeWithPrefix00(short opcode) {
        switch(opcode >> 8) {
            case 0b0000000:
                return null; // NOP, RETURN, RETFIE, SLEEP, CLRWDT or MOVWF
            case 0b0000001:
                return null; // CLRW or CLRF
            case 0b0000010:
                return null; // SUBWF
            case 0b0000011:
                return null; // DECF
            case 0b0000100:
                return null; // IORWF
            case 0b0000101:
                return null; // ANDWF
            case 0b0000110:
                return null; // XORWF
            case 0b0000111:
                return null; // ADDWF
            case 0b0001000:
                return null; // MOVF
            case 0b0001001:
                return null; // COMF
            case 0b0001010:
                return null; // INCF
            case 0b0001011:
                return null; // DECFSZ
            case 0b0001100:
                return null; // RRF
            case 0b0001101:
                return null; // RLF
            case 0b0001110:
                return null; // SWAPF
            case 0b0001111:
                return null; // INCFSZ
            default:
                LOGGER.error("Unknown opcode with prefix 0b000");
                return null;
        }
    }

    private IInstruction decodeOpcodeWithPrefix01(short opcode) {
        switch(opcode >> 10) {
            case 0b0100:
                return new BCF();
            case 0b0101:
                return new BSF();
            case 0b0110:
                return new BTFSC();
            case 0b0111:
                return new BTFSS();
            default:
                LOGGER.error("Unknown opcode with prefix 0b001");
                return null;
        }
    }

    private IInstruction decodeOpcodeWithPrefix10(short opcode) {
        switch(opcode >> 11) {
            case 0b0100:
                return new Call();
            case 0b0101:
                return new Goto();
            default:
                LOGGER.error("Unknown opcode with prefix 0b010");
                return null;
        }
    }

    private IInstruction decodeOpcodeWithPrefix11(short opcode) {
        switch(opcode >> 8) {
            case 0b0110000:
            case 0b0110001:
            case 0b0110010:
            case 0b0110011:
                return new Movlw();
            case 0b0110100:
            case 0b0110101:
            case 0b0110110:
            case 0b0110111:
                return new Retlw();
            case 0b0111000:
                return new Iorlw();
            case 0b0111001:
                return new Andlw();
            case 0b0111010:
                return new Xorlw();
            case 0b0111100:
            case 0b0111101:
                return new Sublw();
            case 0b0111110:
            case 0b0111111:
                return new Addlw();
            default:
                LOGGER.error("Unknown opcode with prefix 0b011");
                return null;
        }
    }
}
