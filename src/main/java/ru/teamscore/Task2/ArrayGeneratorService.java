package ru.teamscore.Task2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ArrayGeneratorService {
    private final ArrayGenerator<?> generator;
    private final ObjectMapper mapper = new ObjectMapper();

    public ArrayGeneratorService(String type) {
        switch (type.toLowerCase()) {
            case "dice":
                generator = new DiceArrayGenerator();
                break;
            case "boolean":
                generator = new BooleandArrayGenerator();
                break;
            case "plate":
                generator = new PlateArrayGenerator();
                break;
            default:
                throw new IllegalArgumentException(String.format(
                        "Некорректный тип генератора: %s",
                        type
                ));
        }
    }

    public String generateJson(int length) {
        Object array = generator.generate(length);

        try {
            return mapper.writeValueAsString(array);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
