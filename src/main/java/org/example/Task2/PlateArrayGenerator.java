package org.example.Task2;

import java.util.Random;

public class PlateArrayGenerator implements ArrayGenerator<String> {
    private final Random rnd = new Random();
    private static final String LETTERS = "АВЕКМНОРСТУХ";

    private char randomLetter() {
        return LETTERS.charAt(rnd.nextInt(LETTERS.length()));
    }

    public String[] generate(int length) {
        String[] array = new String[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.format(
                    "%s%03d%s%s%02d",
                    randomLetter(),
                    rnd.nextInt(1000),
                    randomLetter(),
                    randomLetter(),
                    1 + rnd.nextInt(199) // регион
            );
        }
        return array;
    }
}
