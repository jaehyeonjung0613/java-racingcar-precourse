package racingcar.entity;

import static racingcar.entity.BoardConstants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {
    private final Map<Integer, List<String>> record = new HashMap<>();

    private int round = 0;

    public String displayCar(Car car) {
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

    public void update(List<Car> carList) {
        int maxDist = carList.stream().mapToInt(Car::getPosition).max().getAsInt();
        List<String> winnerList = carList.stream()
            .filter((car) -> maxDist == car.getPosition())
            .map(Car::getName)
            .collect(Collectors.toList());
        this.round++;
        this.record.put(this.round, winnerList);
    }

    public String displayWinner(int round) {
        return String.join(WINNER_SEPARATOR, this.record.get(round));
    }
}
