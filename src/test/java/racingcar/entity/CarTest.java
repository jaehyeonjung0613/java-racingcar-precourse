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

    @Test
    void 자동차_이름_미입력() {
        assertThatThrownBy(() -> new Car(null)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(EMPTY_NAME_INPUT_MESSAGE);
    }

    @Test
    void 자동차_이름_초과() {
        String message = String.format(NAME_LENGTH_OVER_FORMAT, MAX_NAME_LENGTH);
        assertThatThrownBy(() -> new Car("invalid name")).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }

    @Test
    void 자동차_작동_시드_초과1() {
        Car car = new Car("test");
        String message = String.format(SEED_RANGE_OVER_FORMAT, MIN_OPERATION_SEED, MAX_OPERATION_SEED);
        assertThatThrownBy(() -> car.operate(MIN_OPERATION_SEED - 1)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }

    @Test
    void 자동차_작동_시드_초과2() {
        Car car = new Car("test");
        String message = String.format(SEED_RANGE_OVER_FORMAT, MIN_OPERATION_SEED, MAX_OPERATION_SEED);
        assertThatThrownBy(() -> car.operate(MAX_OPERATION_SEED + 1)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }
}
