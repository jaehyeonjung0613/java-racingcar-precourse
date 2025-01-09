package racingcar;

import racingcar.service.Game;
import racingcar.service.GameManager;

public class Application {
    public static void main(String[] args) {
        Game game = new GameManager().build();
        try {
            game.run();
        } finally {
            game.finish();
        }
    }
}
