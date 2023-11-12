package christmas.controller.convertor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.order.day.Day;
import christmas.domain.order.menu.Menu;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PlannerConvertorTest {

    @DisplayName("문자를 Day로 변환할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "10", "14", "19", "25", "29", "30", "31"})
    void convertToDay(String input) {
        // when
        Day day = PlannerConvertor.convertToDay(input);

        // then
        assertThat(day).isEqualTo(Day.from(1));
    }

    @DisplayName("문자가 정수가 아니면 예외처리된다.")
    @ParameterizedTest
    @ValueSource(strings = {"1.0", "가", ",", "-", "abd", "1 1", " ", "/"})
    void convertToDayNotInteger(String input) {
        assertThatThrownBy(() -> PlannerConvertor.convertToDay(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @DisplayName("문자가 12월에 없으면 예외 처리된다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "32", "-1", "33"})
    void convertToDayNotDecember(String input) {
        assertThatThrownBy(() -> PlannerConvertor.convertToDay(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @DisplayName("문자를 MenuEnumMap으로 변환할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"티본스테이크-1,초코케이크-2", "티본스테이크-1", "티본스테이크-1,초코케이크-19",
            "티본스테이크-1,초코케이크-2,아이스크림-1", "티본스테이크-1,초코케이크-2,아이스크림-1,레드와인-1"})
    void convertToMenu(String menuCount) {
        // when
        EnumMap<Menu, Integer> convert = PlannerConvertor.convertToMenu(menuCount);

        // then
        assertThat(convert).containsExactlyInAnyOrderEntriesOf(
                Map.of(Menu.T_BONE_STEAK, 1, Menu.CHOCOLATE_CAKE, 2, Menu.ZERO_COLA, 3));
    }

    @DisplayName("중복된 메뉴가 있을 경우 예외가 발생한다.")
    @Test
    void throwingDuplicateOrderException() {
        // given
        String menuCount = "티본스테이크-1,초코케이크-2,티본스테이크-3";

        assertThatThrownBy(() -> PlannerConvertor.convertToMenu(menuCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("입력 메뉴의 수가 숫자가 아닐 경우 예외가 발생한다.")
    @Test
    void convertCountToInt() {
        // given
        String menuCount = "티본스테이크-A,초코케이크-2,티본스테이크-3";

        assertThatThrownBy(() -> PlannerConvertor.convertToMenu(menuCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("입력 메뉴가 형식에 맞지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"티본스테이크-1,초코케이크-2,", ",티본스테이크-1,초코케이크-2", "티본스테이크-1,,초코케이크-2",
            "티본스테이크-1-3,초코케이크-2", "티본스테이크--1,초코케이크-2"})
    void validateFormat(String menuCount) {
        assertThatThrownBy(() -> PlannerConvertor.convertToMenu(menuCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

}