package racingcar.entity;

import static racingcar.entity.CarConstants.*;

public class Car {
    private final String name;
    private int position = 0;

    public Car(String name) throws IllegalArgumentException {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException(EMPTY_NAME_INPUT_MESSAGE);
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(NAME_LENGTH_OVER_FORMAT, MAX_NAME_LENGTH));
        }
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void operate(int seed) throws IllegalArgumentException {
        this.validateSeed(seed);
        if (seed >= MIN_MOVEMENT_SEED) {
            this.move();
        }
    }

    private void validateSeed(int seed) throws IllegalArgumentException {
        if (seed < MIN_OPERATION_SEED || seed > MAX_OPERATION_SEED) {
            throw new IllegalArgumentException(
                String.format(SEED_RANGE_OVER_FORMAT, MIN_OPERATION_SEED, MAX_OPERATION_SEED));
        }
    }

    private void move() {
        this.position++;
    }
}
