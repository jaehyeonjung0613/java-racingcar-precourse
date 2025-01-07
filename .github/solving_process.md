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

