package racingcar.ui;

import static racingcar.ui.OutputConstants.*;

import java.io.PrintStream;

public class ConsoleOutput implements Output {
    private final PrintStream console = System.out;

    @Override
    public void print(String message) {
        console.print(message);
    }

    @Override
    public void println(String message) {
        console.println(message);
    }

    @Override
    public void printNextLine() {
        console.println();
    }

    @Override
    public void printByFormat(String format, String message) {
        this.println(String.format(format, message));
    }

    @Override
    public void printError(String message) {
        this.printByFormat(ERROR_FORMAT, message);
    }
}
