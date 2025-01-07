package racingcar.entity;

public final class CarConstants {
    private CarConstants() {
    }

    public static final int MAX_NAME_LENGTH = 5;
    public static final int MIN_OPERATION_SEED = 0;
    public static final int MAX_OPERATION_SEED = 9;
    public static final int MIN_MOVEMENT_SEED = 4;

    public static final String EMPTY_NAME_INPUT_MESSAGE = "자동차 이름은 필수 입력입니다.";
    public static final String NAME_LENGTH_OVER_FORMAT = "자동차 이름은 최대 %d 자 이하만 지정 가능합니다.";
    public static final String SEED_RANGE_OVER_FORMAT = "자동차 작동 시드는 %d ~ %d 범위 이여야만 합니다.";
}
