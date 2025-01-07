package racingcar.entity;

import static racingcar.entity.CarConstants.*;

public class Car {
    private final String name;
    private int position = 0;

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void operate(int seed) {
        if (seed >= MIN_MOVEMENT_SEED) {
            this.move();
        }
    }

    private void move() {
        this.position++;
    }
}
