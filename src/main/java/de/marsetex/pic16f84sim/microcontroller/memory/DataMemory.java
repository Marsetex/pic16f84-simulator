package de.marsetex.pic16f84sim.microcontroller.memory;

import de.marsetex.pic16f84sim.microcontroller.register.helper.StatusRegisterHelper;
import de.marsetex.pic16f84sim.simulator.Simulator;

/**
 * Datasheet: Page 13
 */
public class DataMemory {

    private byte[] ramBank0;
    private byte[] ramBank1;

    public DataMemory() {
        ramBank0 = new byte[128];
        ramBank1 = new byte[128];
    }

    public void store(byte fileRegisterAddress, byte valueToStore) {
        byte normalizedAddress = (byte) (fileRegisterAddress % 128);

        if(normalizedAddress > 0x4F) {
            // TODO: Error msg

        } else if(normalizedAddress > 0x0B) {
            storeInGeneralPurposeRegister(normalizedAddress, valueToStore);

        } else {
            storeInSpecialFunctionRegister(normalizedAddress, valueToStore);
        }
    }

    private void storeInGeneralPurposeRegister(byte fileRegisterAddress, byte valueToStore) {
        ramBank0[fileRegisterAddress] = valueToStore;
    }

    private void storeInSpecialFunctionRegister(byte fileRegisterAddress, byte valueToStore) {
        switch (fileRegisterAddress) {
            case 0x02: // PCL
            case 0x03: // STATUS
            case 0x04: // FSR
            case 0x0A: // PCLATH
            case 0x0B: // INTCON
                ramBank0[fileRegisterAddress] = valueToStore;
                ramBank1[fileRegisterAddress] = valueToStore;
                break;
            case 0x01: // TMR0 (Bank0) or OPTION (Bank1)
            case 0x05: // PORTA (Bank0) or TRISA (Bank1)
            case 0x06: // PORTB (Bank0) or TRISB (Bank1)
            case 0x08: // EEDATA (Bank0) or EECON1 (Bank1)
                if(getRP0Flag() == 0) {
                    ramBank0[fileRegisterAddress] = valueToStore; // TMR0
                } else {
                    ramBank1[fileRegisterAddress] = valueToStore; // OPTION
                }
                break;
            case 0x09:
                if(getRP0Flag() == 0) {
                    ramBank0[fileRegisterAddress] = valueToStore; // EEADR
                }
                break;
            default:
                // Do nothing for file addresses: 00h (indirect addr.), 07h (undefined)
                break;
        }
    }

    public byte load(byte fileRegisterAddress) {
        byte normalizedAddress = (byte) (fileRegisterAddress % 128);

        if(normalizedAddress > 0x4F) {
            // TODO: Error msg
            return 0;

        } else if(normalizedAddress > 0x0B) {
            return loadFromGeneralPurposeRegister(normalizedAddress);

        } else {
            return loadFromSpecialFunctionRegister(normalizedAddress);
        }
    }

    private byte loadFromGeneralPurposeRegister(byte fileRegisterAddress) {
        return ramBank0[fileRegisterAddress];
    }

    private byte loadFromSpecialFunctionRegister(byte fileRegisterAddress) {
        if(getRP0Flag() == 0) {
            return ramBank0[fileRegisterAddress];
        } else {
            return ramBank1[fileRegisterAddress];
        }
    }

    private byte getRP0Flag() {
        return (byte) (ramBank0[0x03] & 0b0010000);
    }
}
