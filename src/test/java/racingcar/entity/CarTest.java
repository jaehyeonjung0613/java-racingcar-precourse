package racingcar.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CarTest {
    @Test
    void 이름_위치_저장_및_반환() {
        String name = "test";
        Car car = new Car(name);
        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getPosition()).isEqualTo(0);
    }
}
