# 🧐 미션 - 자동차 경주 게임

[우아한테크코스](https://github.com/woowacourse) precourse 문제
중 [미션 - 자동차 경주 게임](https://github.com/woowacourse/java-racingcar-precourse) 풀이 기록하기.

개발은 TDD(테스트 주도 개발) 방식으로 진행될 것이며, 입출력 및 프로그래밍 요구사항을 부합하도록 풀어 볼 예정.

## 0. 설계

### entity

|  클래스  | 기능                                                     |
|:-----:|:-------------------------------------------------------|
|  Car  | - 이름, 위치 저장 및 반환<br/> - 작동(move or idle)<br/> - 유효성 체크 |
| Board | - Car 표시<br/> - 라운드별 우승자 저장 및 반환<br/> - 유효성 체크         |

### service

|     클래스     | 기능                       |
|:-----------:|:-------------------------|
| GameManager | - 게임 관리                  |
|    Game     | - 사용자 입력 절차<br/> - 게임 진행 |

### ui

|  클래스   | 기능          |
|:------:|:------------|
| Input  | - 사용자 입력 처리 |
| Output | - 출력 처리     |

### util

|    클래스     | 기능          |
|:----------:|:------------|
| Validation | - 공통 유효성 체크 |

## 1. Car 이름, 위치 저장 및 반환

```java
// CarTest.java

package racingcar.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CarTest {
    @Test
    void 이름_위치_저장_및_반환() {
        String name = "test";
        Car car = new Car(name);
        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getPosition()).isEqualTo(0);
    }
}
```

테스트 케이스 생성.

```java
// Car.java

package racingcar.entity;

public class Car {
    private final String name;
    private int position = 0;

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
```

이름, 위치 저장 및 반환 구현.

## 2. Car 작동(move or idle)

```java
// CarConstants.java

package racingcar.entity;

public final class CarConstants {
    private CarConstants() {
    }

    public static final int MIN_OPERATION_SEED = 0;
    public static final int MAX_OPERATION_SEED = 9;
    public static final int MIN_MOVEMENT_SEED = 4;
}
```

작동 관련 상수 정의.

```java
// CarTest.java

package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.CarConstants.*;

import org.junit.jupiter.api.Test;

public class CarTest {
    @Test
    void 작동_move() {
        Car car = new Car("test");
        car.operate(MIN_MOVEMENT_SEED);
        assertThat(car.getPosition()).isEqualTo(1);
    }

    @Test
    void 작동_idle() {
        Car car = new Car("test");
        car.operate(MIN_MOVEMENT_SEED - 1);
        assertThat(car.getPosition()).isEqualTo(0);
    }
}
```

테스트 케이스 생성.

```java
// Car.java

package racingcar.entity;

import static racingcar.entity.CarConstants.*;

public class Car {
    public void operate(int seed) {
        if (seed >= MIN_MOVEMENT_SEED) {
            this.move();
        }
    }

    private void move() {
        this.position++;
    }
}
```

작동(move or idle) 구현.

## 3. Car 유효성 체크

```java
// CarConstants.java

package racingcar.entity;

public final class CarConstants {
    private CarConstants() {
    }

    public static final int MAX_NAME_LENGTH = 5;

    public static final String EMPTY_NAME_INPUT_MESSAGE = "자동차 이름은 필수 입력입니다.";
    public static final String NAME_LENGTH_OVER_FORMAT = "자동차 이름은 최대 %d 자 이하만 지정 가능합니다.";
    public static final String SEED_RANGE_OVER_FORMAT = "자동차 작동 시드는 %d ~ %d 범위 이여야만 합니다.";
}
```

유효성 관련 상수 정의.

### 자동차 이름이 미입력 된 경우

```java
// CarTest.java

package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.CarConstants.*;

import org.junit.jupiter.api.Test;

public class CarTest {
    @Test
    void 자동차_이름_미입력() {
        assertThatThrownBy(() -> new Car(null)).isEqualTo(IllegalArgumentException.class)
            .hasMessage(EMPTY_NAME_INPUT_MESSAGE);
    }
}
```

테스트 케이스 생성.

```java
// Car.java

package racingcar.entity;

import static racingcar.entity.CarConstants.*;

public class Car {
    public Car(String name) throws IllegalArgumentException {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException(EMPTY_NAME_INPUT_MESSAGE);
        }
    }
}
```

자동차 이름 저장시 null 체크.

### 자동차 이름 초과일 경우

```java
// CarTest.java

package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.CarConstants.*;

import org.junit.jupiter.api.Test;

public class CarTest {
    @Test
    void 자동차_이름_초과() {
        String message = String.format(NAME_LENGTH_OVER_FORMAT, MAX_NAME_LENGTH);
        assertThatThrownBy(() -> new Car("invalid name")).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }
}
```

테스트 케이스 생성.

```java
// Car.java

package racingcar.entity;

import static racingcar.entity.CarConstants.*;

public class Car {
    private void validateName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException(EMPTY_NAME_INPUT_MESSAGE);
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(NAME_LENGTH_OVER_FORMAT, MAX_NAME_LENGTH));
        }
    }
}
```

자동차 이름 저장시 이름 초과 유무 체크.

### 자동차 작동 시드 초과일 경우

```java
// CarTest.java

package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.CarConstants.*;

import org.junit.jupiter.api.Test;

public class CarTest {
    @Test
    void 자동차_작동_시드_초과1() {
        Car car = new Car("test");
        String message = String.format(SEED_RANGE_OVER_FORMAT, MIN_OPERATION_SEED, MAX_OPERATION_SEED);
        assertThatThrownBy(() -> car.operate(MIN_OPERATION_SEED - 1)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }

    @Test
    void 자동차_작동_시드_초과2() {
        Car car = new Car("test");
        String message = String.format(SEED_RANGE_OVER_FORMAT, MIN_OPERATION_SEED, MAX_OPERATION_SEED);
        assertThatThrownBy(() -> car.operate(MAX_OPERATION_SEED + 1)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }
}
```

테스트 케이스 생성.

```java
// Car.java

package racingcar.entity;

import static racingcar.entity.CarConstants.*;

public class Car {
    public void operate(int seed) throws IllegalArgumentException {
        this.validateSeed(seed);
        if (seed >= MIN_MOVEMENT_SEED) {
            this.move();
        }
    }

    private void validateSeed(int seed) throws IllegalArgumentException {
        if (seed < MIN_OPERATION_SEED || seed > MAX_OPERATION_SEED) {
            throw new IllegalArgumentException(
                String.format(SEED_RANGE_OVER_FORMAT, MIN_OPERATION_SEED, MAX_OPERATION_SEED));
        }
    }
}
```

자동차 작동시 시드 범위 체크.

## 4. Board Car 표시

```java
// BoardConstants.java

package racingcar.entity;

public final class BoardConstants {
    private BoardConstants() {
    }

    public static final String DIST_SIGN = "-";
    public static final String CAR_DISPLAY_FORMAT = "%s : %s";
}
```

Car 표시 관련 상수 정의.

```java
// BoardTest.java

package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.BoardConstants.*;

import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void Car_표시() {
        String name = "test";
        String result = String.format(CAR_DISPLAY_FORMAT, name, DIST_SIGN);

        Board board = new Board();
        Car car = new Car(name);
        car.operate(CarConstants.MIN_MOVEMENT_SEED);
        assertThat(board.displayCar(car)).isEqualTo(result);
    }
}
```

테스트 케이스 생성.

```java
// Board.java

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
```

Car 표시 구현.

## 5. 라운드별 우승자 저장 및 반환

```java
// BoardConstants.java

package racingcar.entity;

public final class BoardConstants {
    private BoardConstants() {
    }

    public static final String WINNER_SEPARATOR = ", ";
}
```

우승자 반환 관련 상수 정의.

```java
// BoardTest.java

package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.BoardConstants.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void 라운드별_우승자_저장_및_반환() {
        String name1 = "test1", name2 = "test2";
        String result = String.format("%s%s%s", name1, WINNER_SEPARATOR, name2);
        Board board = new Board();
        List<Car> carList = Arrays.asList(new Car(name1), new Car(name2));
        board.update(carList);
        assertThat(board.displayWinner(1)).isEqualTo(result);
    }
}
```

테스트 케이스 생성.

```java
// Board.java

package racingcar.entity;

import static racingcar.entity.BoardConstants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {
    private final Map<Integer, List<String>> record = new HashMap<>();

    private int round = 0;

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
```

라운드별 우승자 저장 및 반환 구현.

## 6. Board 유효성 체크

```java
// BoardConstants.java

package racingcar.entity;

public final class BoardConstants {
    private BoardConstants() {
    }

    public static final String EMPTY_CAR_MESSAGE = "자동차가 존재하지 않습니다.";
    public static final String EMPTY_CAR_LIST_MESSAGE = "자동차 목록이 존재하지 않습니다.";
    public static final String NOT_PROGRESS_ROUND_MESSAGE = "아직 미진행된 라운드입니다.";
}
```

유효성 관련 상수 정의.

### 자동차 표시시 미존재일 경우

```java
// BoardTest.java

package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.BoardConstants.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void 자동차_표시시_미존재() {
        Board board = new Board();
        assertThatThrownBy(() -> board.displayCar(null)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(EMPTY_CAR_MESSAGE);
    }
}
```

테스트 케이스 생성.

```java
// Board.java

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
}
```

자동차 표시시 유효성 체크.

### 라운드 우승자 갱신시 자동차 목록이 미존재할 경우

```java
// BoardTest.java

package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.BoardConstants.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void 라운드_우승자_갱신시_자동차_목록_미존재() {
        Board board = new Board();
        assertThatThrownBy(() -> board.update(null)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(EMPTY_CAR_LIST_MESSAGE);
    }
}
```

테스트 케이스 생성.

```java
// Board.java

package racingcar.entity;

import static racingcar.entity.BoardConstants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {
    private final Map<Integer, List<String>> record = new HashMap<>();

    private int round = 0;

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
}
```

라운드 우승자 갱신시 자동차 목록 유효성 체크.

### 미진행된 라운드 우승자를 반환할 경우

```java
// BoardTest.java

package racingcar.entity;

import static org.assertj.core.api.Assertions.*;
import static racingcar.entity.BoardConstants.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void 라운드_미진행된_우승자_출력() {
        Board board = new Board();
        assertThatThrownBy(() -> board.displayWinner(1)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage(NOT_PROGRESS_ROUND_MESSAGE);
    }
}
```

테스트 케이스 생성.

```java
// Board.java

package racingcar.entity;

import static racingcar.entity.BoardConstants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {
    private final Map<Integer, List<String>> record = new HashMap<>();

    private int round = 0;

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
```

우승자 반환 전 라운드 유효성 체크.

## 7. 콘솔 입출력

```java
// Input.java

package racingcar.ui;

public interface Input {
    String readline();
}
```

입력 인터페이스 생성.

```java
// ConsoleInput.java

package racingcar.ui;

import camp.nextstep.edu.missionutils.Console;

public class ConsoleInput implements Input {
    @Override
    public String readline() {
        return Console.readLine();
    }
}
```

콘솔 입력 구현.

```java
// Output.java

package racingcar.ui;

public interface Output {
    void print(String message);

    void println(String message);

    void printNextLine();

    void printByFormat(String format, String message);

    void printError(String message);
}
```

출력 인터페이스 생성.

```java
// OutputConstants.java

package racingcar.ui;

public final class OutputConstants {
    private OutputConstants() {
    }

    public static final String ERROR_FORMAT = "[ERROR] %s";
}
```

에러 출력 관련 상수 정의.

```java
// ConsoleOutput.java

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
```

콘솔 출력 구현.

## 8. 애플리케이션 Skeleton

```java
// Game.java

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
```

애플리케이션 런처 동작 및 종료 구성.

```java
// GameManager.java

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
```

애플리케이션 런처 매니저 생성.

```java
// Application.java

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
```

애플리케이션 실행 및 종료.

## 9. 사용자 입력 처리

```java
// GameConstants.java

package racingcar.service;

public final class GameConstants {
    private GameConstants() {
    }

    public static final String CAR_NAME_SEPARATOR = ",";
}
```

사용자 입력 관련 상수 정의.

```java
// Game.java

package racingcar.service;

import static racingcar.service.GameConstants.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

import racingcar.entity.Car;
import racingcar.ui.Input;
import racingcar.ui.Output;

public class Game {
    public void run() {
        List<Car> carList = this.createCarList(this.questInputCarName());
        int finalRound = this.questInputFinalRound();
    }

    private String[] questInputCarName() {
        this.output.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String answer = this.input.readline();
        return answer.split(CAR_NAME_SEPARATOR);
    }

    private List<Car> createCarList(String[] carNames) throws IllegalArgumentException {
        return Arrays.stream(carNames).map(Car::new).collect(Collectors.toList());
    }

    private int questInputFinalRound() {
        this.output.println("시도할 회수는 몇회인가요?");
        String answer = this.input.readline();
        return Integer.parseInt(answer);
    }
}
```

애플리케이션 실행시 자동차 이름과 시도할 횟수 입력 받도록 구현.
