package racingcar.service;

import static racingcar.service.GameConstants.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import camp.nextstep.edu.missionutils.Randoms;
import racingcar.entity.Board;
import racingcar.entity.Car;
import racingcar.entity.CarConstants;
import racingcar.ui.Input;
import racingcar.ui.Output;
import racingcar.util.Validation;

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
        Board board = new Board();
        List<Car> carList = this.createCarList(this.questInputCarName());
        int finalRound = this.questInputFinalRound();
        this.output.printNextLine();
        for (int round = 1; round <= finalRound; round++) {
            this.startRacingOfRound(carList, board);
        }
        this.printFinalRoundWinner(finalRound, board);
    }

    private String[] questInputCarName() {
        this.output.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String answer = this.input.readline();
        return answer.split(CAR_NAME_SEPARATOR);
    }

    private List<Car> createCarList(String[] carNames) throws IllegalArgumentException {
        return Arrays.stream(carNames).map(Car::new).collect(Collectors.toList());
    }

    private int questInputFinalRound() throws IllegalArgumentException {
        this.output.println("시도할 회수는 몇회인가요?");
        String answer = this.input.readline();
        this.validateFinalRound(answer);
        return Integer.parseInt(answer);
    }

    private void validateFinalRound(String _finalRound) throws IllegalArgumentException {
        if (!Validation.isNumeric(_finalRound)) {
            throw new IllegalArgumentException(NOT_NUMERIC_FINAL_ROUND_MESSAGE);
        }
    }

    private void startRacingOfRound(List<Car> carList, Board board) {
        for (Car car : carList) {
            car.operate(this.createCarSeed());
            this.output.println(board.displayCar(car));
        }
        this.output.printNextLine();
        board.update(carList);
    }

    private int createCarSeed() {
        return Randoms.pickNumberInRange(CarConstants.MIN_OPERATION_SEED, CarConstants.MAX_OPERATION_SEED);
    }

    private void printFinalRoundWinner(int finalRound, Board board) {
        this.output.println(String.format("최종 우승자 : %s", board.displayWinner(finalRound)));
    }

    public void finish() {
        instance = null;
    }
}
