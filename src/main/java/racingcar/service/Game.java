package racingcar.service;

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
    }

    public void finish() {
        instance = null;
    }
}
