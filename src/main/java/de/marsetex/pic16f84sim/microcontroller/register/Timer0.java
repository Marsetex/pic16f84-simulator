package de.marsetex.pic16f84sim.microcontroller.register;

import de.marsetex.pic16f84sim.microcontroller.PIC16F84;
import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;

/**
 * Datasheet: Page 27
 */
public class Timer0 {

    private static final byte TMR0_ADDRESS = 0x01;
    private static final byte INTCON_ADDRESS = 0x0B;

    private final PIC16F84 picController;

    private boolean nextIncOverflow;

    public Timer0(PIC16F84 pic) {
        picController = pic;

        nextIncOverflow = false;
    }

    public void increaseTimerCounter() {
        DataMemory dataMemory = picController.getDataMemory();

        if(nextIncOverflow) {
            byte intconValue = dataMemory.load(INTCON_ADDRESS);
            intconValue = (byte) (intconValue | 0b00000100);

            dataMemory.store(INTCON_ADDRESS, intconValue);
        }

        byte timerCounter = dataMemory.load(TMR0_ADDRESS);
        nextIncOverflow = timerCounter == 0xFF;
        timerCounter++;

        dataMemory.store(TMR0_ADDRESS, timerCounter);
    }
}
