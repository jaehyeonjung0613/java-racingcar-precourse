package racingcar.ui;

import camp.nextstep.edu.missionutils.Console;

public class ConsoleInput implements Input {
    @Override
    public String readline() {
        return Console.readLine();
    }
}
