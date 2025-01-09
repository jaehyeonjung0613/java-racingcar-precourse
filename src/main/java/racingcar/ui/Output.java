package racingcar.ui;

public interface Output {
    void print(String message);

    void println(String message);

    void printNextLine();

    void printByFormat(String format, String message);

    void printError(String message);
}
