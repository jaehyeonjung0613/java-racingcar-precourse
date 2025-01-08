package racingcar.entity;

import static racingcar.entity.BoardConstants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {
    private final Map<Integer, List<String>> record = new HashMap<>();

    private int round = 0;

    public String displayCar(Car car) throws IllegalArgumentException {
        this.validateCar(car);
        String distSign = this.getDistSign(car.getPosition());
        return String.format(CAR_DISPLAY_FORMAT, car.getName(), distSign);
    }

    private String getDistSign(int position) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < position; i++) {
            builder.append(DIST_SIGN);
        }
        return builder.toString();
    }

    private void validateCar(Car car) throws IllegalArgumentException {
        if (car == null) {
            throw new IllegalArgumentException(EMPTY_CAR_MESSAGE);
        }
    }

    public void update(List<Car> carList) throws IllegalArgumentException {
        this.validateCarList(carList);
        int maxDist = carList.stream().mapToInt(Car::getPosition).max().getAsInt();
        List<String> winnerList = carList.stream()
            .filter((car) -> maxDist == car.getPosition())
            .map(Car::getName)
            .collect(Collectors.toList());
        this.round++;
        this.record.put(this.round, winnerList);
    }

    private void validateCarList(List<Car> carList) throws IllegalArgumentException {
        if (carList == null) {
            throw new IllegalArgumentException(EMPTY_CAR_LIST_MESSAGE);
        }
    }

    public String displayWinner(int round) throws IllegalArgumentException {
        this.validateRound(round);
        return String.join(WINNER_SEPARATOR, this.record.get(round));
    }

    private void validateRound(int round) throws IllegalArgumentException {
        if (this.record.get(round) == null) {
            throw new IllegalArgumentException(NOT_PROGRESS_ROUND_MESSAGE);
        }
    }
}
