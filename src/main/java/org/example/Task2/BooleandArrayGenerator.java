package org.example.Task2;

import java.util.Random;

public class BooleandArrayGenerator implements ArrayGenerator<Boolean> {
    private final Random rnd = new Random();

    @Override
    public Boolean[] generate(int length) {
        Boolean[] array = new Boolean[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = rnd.nextBoolean();
        }
        return array;
    }
}
