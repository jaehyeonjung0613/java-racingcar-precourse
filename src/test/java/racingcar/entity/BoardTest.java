package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.BoardConstants.*;

import java.util.Arrays;
import java.util.List;

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

    @Test
    void 라운드별_우승자_저장_및_반환() {
        String name1 = "test1", name2 = "test2";
        String result = String.format("%s%s%s", name1, WINNER_SEPARATOR, name2);
        Board board = new Board();
        List<Car> carList = Arrays.asList(new Car(name1), new Car(name2));
        board.update(carList);
        assertThat(board.displayWinner(1)).isEqualTo(result);
    }
}
