package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.CarConstants.*;

import org.junit.jupiter.api.Test;

public class CarTest {
    @Test
    void 이름_위치_저장_및_반환() {
        String name = "test";
        Car car = new Car(name);
        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getPosition()).isEqualTo(0);
    }

    @Test
    void 작동_move() {
        Car car = new Car("test");
        car.operate(MIN_MOVEMENT_SEED);
        assertThat(car.getPosition()).isEqualTo(1);
    }

    @Test
    void 작동_idle() {
        Car car = new Car("test");
        car.operate(MIN_MOVEMENT_SEED - 1);
        assertThat(car.getPosition()).isEqualTo(0);
    }
}
