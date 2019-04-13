package de.marsetex.pic16f84sim.microcontroller.register;

/**
 * Datasheet: Page 14
 */
public class StatusRegister {

    /**
     * Bit 0: C
     * Bit 1: DC
     * Bit 2: Z
     */
    private byte statusRegister;

    public void setCFlag() {
        statusRegister = (byte) (statusRegister | 0b00000001);
    }

    public void resetCFlag() {
        statusRegister = (byte) (statusRegister & 0b11111110);
    }

    public void setDCFlag() {
        statusRegister = (byte) (statusRegister | 0b00000010);
    }

    public void resetDCFlag() {
        statusRegister = (byte) (statusRegister & 0b11111101);
    }

    public void setZFlag() {
        statusRegister = (byte) (statusRegister | 0b00000100);
    }

    public void resetZFlag() {
        statusRegister = (byte) (statusRegister & 0b11111011);
    }
}
