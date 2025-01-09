package racingcar.service;

import static racingcar.service.GameConstants.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

import racingcar.entity.Car;
import racingcar.ui.Input;
import racingcar.ui.Output;

public class Game {
    private static Game instance = null;

    private final Input input;
    private final Output output;

    private Game(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    protected static Game getInstance(Input input, Output output) {
        if (instance == null) {
            instance = new Game(input, output);
        }
        return instance;
    }

    public void run() {
        List<Car> carList = this.createCarList(this.questInputCarName());
        int finalRound = this.questInputFinalRound();
    }

    private String[] questInputCarName() {
        this.output.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String answer = this.input.readline();
        return answer.split(CAR_NAME_SEPARATOR);
    }

    private List<Car> createCarList(String[] carNames) throws IllegalArgumentException {
        return Arrays.stream(carNames).map(Car::new).collect(Collectors.toList());
    }

    private int questInputFinalRound() {
        this.output.println("시도할 회수는 몇회인가요?");
        String answer = this.input.readline();
        return Integer.parseInt(answer);
    }

    public void finish() {
        instance = null;
    }
}
