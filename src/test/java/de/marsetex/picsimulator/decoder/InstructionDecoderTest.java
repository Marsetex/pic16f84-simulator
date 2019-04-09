package de.marsetex.picsimulator.decoder;

import de.marsetex.picsimulator.instruction.*;
import de.marsetex.picsimulator.instruction.literal.*;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstructionDecoderTest {

    private final InstructionDecoder decoder = new InstructionDecoder();

    @Test
    public void testDecodeMovlw() {
        String movlwOpcode = "3011";
        IPicInstruction instruction = decoder.decode(Short.parseShort(movlwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Movlw.class));
    }

    @Test
    public void testDecodeRetlw() {
        // String movlwOpcode = "3011";
        // IInstruction instruction = decoder.decode(Short.parseShort(opCodeAsString, 16));
        // assertThat(instruction, IsInstanceOf.instanceOf(Movlw.class));
    }

    @Test
    public void testDecodeIorlw() {
        String iorlwOpcode = "380D";
        IPicInstruction instruction = decoder.decode(Short.parseShort(iorlwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Iorlw.class));
    }

    @Test
    public void testDecodeAndlw() {
        String andlwOpcode = "3930";
        IPicInstruction instruction = decoder.decode(Short.parseShort(andlwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Andlw.class));
    }

    @Test
    public void testDecodeXorlw() {
        String xorlwOpcode = "3A20";
        IPicInstruction instruction = decoder.decode(Short.parseShort(xorlwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Xorlw.class));
    }

    @Test
    public void testDecodeSublw() {
        String sublwOpcode = "3C3D";
        IPicInstruction instruction = decoder.decode(Short.parseShort(sublwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Sublw.class));
    }

    @Test
    public void testDecodeAddlw() {
        String addlwOpcode = "3E25";
        IPicInstruction instruction = decoder.decode(Short.parseShort(addlwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Addlw.class));
    }
}