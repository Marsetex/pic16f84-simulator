package de.marsetex.pic16f84sim.microcontroller.memory;

public class Stack {

    private final short[] stack;

    private int stackPointer;

    public Stack() {
        stack = new short[8];
    }

    public void push(short address) {
        stack[stackPointer] = address;
        stackPointer++;

        if(stackPointer == 8) {
            stackPointer = 0;
        }
    }

    public short pop() {
        if(stackPointer == 0) {
            stackPointer = 7;
        } else {
            stackPointer--;
        }

        return stack[stackPointer];
    }
}
