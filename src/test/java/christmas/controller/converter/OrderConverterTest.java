package christmas.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.domain.order.Day;
import christmas.domain.order.constant.Menu;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderConverterTest {

    @DisplayName("문자를 Day로 변환할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "10", "14", "19", "25", "29", "30", "31"})
    void convertToDay(String input) {
        // when
        Day day = OrderConverter.convertToDay(input);

        // then
        assertThat(day).isEqualTo(Day.from(Integer.parseInt(input)));
    }

    @DisplayName("문자가 정수가 아니면 예외처리된다.")
    @ParameterizedTest
    @ValueSource(strings = {"1.0", "가", ",", "-", "abd", "1 1", " ", "/"})
    void convertToDayNotInteger(String input) {
        assertThatThrownBy(() -> OrderConverter.convertToDay(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @DisplayName("문자가 12월에 없으면 예외 처리된다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "32", "-1", "33"})
    void convertToDayNotDecember(String input) {
        assertThatThrownBy(() -> OrderConverter.convertToDay(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @DisplayName("문자를 MenuEnumMap으로 변환할 수 있다.")
    @ParameterizedTest
    @MethodSource("menuCountProvider")
    void convertToMenu(String menuCount, Map<Menu, Integer> target) {
        // when
        EnumMap<Menu, Integer> convert = OrderConverter.convertToMenu(menuCount);

        // then
        assertThat(convert).containsExactlyInAnyOrderEntriesOf(target);
    }

    static Stream<Arguments> menuCountProvider() {
        return Stream.of(
                arguments("티본스테이크-1,초코케이크-2",
                        Map.of(Menu.T_BONE_STEAK, 1, Menu.CHOCOLATE_CAKE, 2)),
                arguments("티본스테이크-1",
                        Map.of(Menu.T_BONE_STEAK, 1)),
                arguments("티본스테이크-1,초코케이크-19",
                        Map.of(Menu.T_BONE_STEAK, 1, Menu.CHOCOLATE_CAKE, 19)),
                arguments("티본스테이크-1,초코케이크-2,아이스크림-1",
                        Map.of(Menu.T_BONE_STEAK, 1, Menu.CHOCOLATE_CAKE, 2, Menu.ICE_CREAM, 1))
        );
    }

    @DisplayName("중복된 메뉴가 있을 경우 예외가 발생한다.")
    @Test
    void throwingDuplicateOrderException() {
        // given
        String menuCount = "티본스테이크-1,초코케이크-2,티본스테이크-3";

        assertThatThrownBy(() -> OrderConverter.convertToMenu(menuCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("입력 메뉴의 수가 숫자가 아닐 경우 예외가 발생한다.")
    @Test
    void convertCountToInt() {
        // given
        String menuCount = "티본스테이크-A";

        assertThatThrownBy(() -> OrderConverter.convertToMenu(menuCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("입력 메뉴의 수가 0으로 시작하면 예외가 발생한다.")
    @Test
    void convertCountToIntZero() {
        // given
        String menuCount = "티본스테이크-01";

        assertThatThrownBy(() -> OrderConverter.convertToMenu(menuCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("입력 메뉴가 형식에 맞지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"티본스테이크-1,초코케이크-2,", ",티본스테이크-1,초코케이크-2", "티본스테이크-1,,초코케이크-2",
            "티본스테이크-1-3,초코케이크-2", "티본스테이크--1,초코케이크-2"})
    void validateFormat(String menuCount) {
        assertThatThrownBy(() -> OrderConverter.convertToMenu(menuCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

}