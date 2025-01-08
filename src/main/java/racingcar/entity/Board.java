package racingcar.entity;

import static racingcar.entity.BoardConstants.*;

public class Board {
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
}
