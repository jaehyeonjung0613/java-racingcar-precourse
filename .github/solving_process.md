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
