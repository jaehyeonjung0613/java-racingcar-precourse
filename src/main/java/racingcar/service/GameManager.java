package racingcar.service;

import racingcar.ui.ConsoleInput;
import racingcar.ui.ConsoleOutput;
import racingcar.ui.Input;
import racingcar.ui.Output;

public class GameManager {
    private Input input;
    private Output output;

    public GameManager() {
        input = new ConsoleInput();
        output = new ConsoleOutput();
    }

    public GameManager input(Input input) {
        this.input = input;
        return this;
    }

    public GameManager output(Output output) {
        this.output = output;
        return this;
    }

    public Game build() {
        return Game.getInstance(input, output);
    }
}
