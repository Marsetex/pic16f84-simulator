package de.marsetex.picsimulator.decoder;

import de.marsetex.picsimulator.instruction.IPicInstruction;
import de.marsetex.picsimulator.instruction.bitoriented.*;
import de.marsetex.picsimulator.instruction.byteoriented.*;
import de.marsetex.picsimulator.instruction.control.*;
import de.marsetex.picsimulator.instruction.literal.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InstructionDecoder {

    private static final Logger LOGGER = LogManager.getLogger(InstructionDecoder.class);

    public IPicInstruction decode(short opcode) {
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

    private IPicInstruction decodeOpcodeWithPrefix00(short opcode) {
        switch(opcode >> 8) {
            case 0b0000000:
                return decodeSpecialCasesWithPrefix00(opcode);
            case 0b0000001:
                return ((opcode >> 7) == 0b011) ? new Clrf() : new Clrw();
            case 0b0000010:
                return new Subwf();
            case 0b0000011:
                return new Decf();
            case 0b0000100:
                return new Iorwf();
            case 0b0000101:
                return new Andwf();
            case 0b0000110:
                return new Xorwf();
            case 0b0000111:
                return new Addwf();
            case 0b0001000:
                return new Movf();
            case 0b0001001:
                return new Comf();
            case 0b0001010:
                return new Incf();
            case 0b0001011:
                return new Decfsz();
            case 0b0001100:
                return new Rrf();
            case 0b0001101:
                return new Rlf();
            case 0b0001110:
                return new Swapf();
            case 0b0001111:
                return new Incfsz();
            default:
                LOGGER.error("Unknown opcode with prefix 0b000");
                return null;
        }
    }

    /**
     * Decode instructions, which can not be identified by bit shifting.
     * @param opcode
     * @return
     */
    private IPicInstruction decodeSpecialCasesWithPrefix00(short opcode) {
        if((opcode >> 7) == 0b01) {
            return new Movwf();
        }

        switch(opcode) {
            case 0b0000000000000000:
            case 0b0000000000100000:
            case 0b0000000001000000:
            case 0b0000000001100000:
                return new Nop();
            case 0b0000000000001000:
                return new Return();
            case 0b0000000000001001:
                return new Retfie();
            case 0b0000000001100011:
                return new Sleep();
            case 0b0000000001100100:
                return new Clrwdt();
            default:
                LOGGER.error("Unknown opcode with prefix 0b000 (special cases)");
                return null;
        }
    }

    private IPicInstruction decodeOpcodeWithPrefix01(short opcode) {
        switch(opcode >> 10) {
            case 0b0100:
                return new Bcf();
            case 0b0101:
                return new Bsf();
            case 0b0110:
                return new Btfsc();
            case 0b0111:
                return new Btfss();
            default:
                LOGGER.error("Unknown opcode with prefix 0b001");
                return null;
        }
    }

    private IPicInstruction decodeOpcodeWithPrefix10(short opcode) {
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

    private IPicInstruction decodeOpcodeWithPrefix11(short opcode) {
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
