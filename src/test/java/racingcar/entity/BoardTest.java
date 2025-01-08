package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.BoardConstants.*;

import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void Car_표시() {
        String name = "test";
        String result = String.format(CAR_DISPLAY_FORMAT, name, DIST_SIGN);

        Board board = new Board();
        Car car = new Car(name);
        car.operate(CarConstants.MIN_MOVEMENT_SEED);
        assertThat(board.displayCar(car)).isEqualTo(result);
    }
}
