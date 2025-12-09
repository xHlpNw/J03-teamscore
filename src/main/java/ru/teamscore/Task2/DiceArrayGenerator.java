package ru.teamscore.Task2;

import java.util.Random;

public class DiceArrayGenerator implements ArrayGenerator<Integer> {
    private final Random rnd = new Random();

    @Override
    public Integer[] generate(int length) {
        Integer[] array = new Integer[length];

        for (int i = 0; i < array.length; i++) {
            array[i] = 1 + rnd.nextInt(6);
        }

        return array;
    }
}
