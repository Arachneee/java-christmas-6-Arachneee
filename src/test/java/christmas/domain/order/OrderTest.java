package christmas.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.domain.day.Day;
import christmas.domain.order.constant.Menu;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTest {

    @DisplayName("주문이 정상적으로 생성된다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 19})
    void of(int count) {
        // given
        Day day = Day.from(1);
        Map<Menu, Integer> menu = Map.of(Menu.T_BONE_STEAK, count, Menu.ZERO_COLA, 1);

        // when
        Order order = Order.of(day, menu);

        // then
        assertThat(order)
                .extracting("day", "menuCount")
                .containsExactly(day, menu);
    }

    @DisplayName("단일 주문 메뉴의 수가 1미만이면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-10, -5, -1, 0})
    void validateCountRange(int count) {
        // given
        Day day = Day.from(1);
        Map<Menu, Integer> menu = Map.of(Menu.T_BONE_STEAK, count);


        // when // then
        assertThatThrownBy(() -> Order.of(day, menu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("전체 주문 수량이 20개초과이면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {21, 22, 100, 9999})
    void validateTotalCountMax(int count) {
        // given
        Day day = Day.from(1);
        Map<Menu, Integer> menu = Map.of(Menu.T_BONE_STEAK, count);

        // when // then
        assertThatThrownBy(() -> Order.of(day, menu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("음료만 주문하면 예외를 발생한다.")
    @Test
    void validateOnlyBeverage() {
        // given
        Day day = Day.from(1);
        Map<Menu, Integer> menu = Map.of(Menu.ZERO_COLA, 10);

        // when // then
        assertThatThrownBy(() -> Order.of(day, menu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("총 주문 가격을 계산할 수 있다.")
    @ParameterizedTest
    @MethodSource("menuProvider")
    void calculateTotalPrice(Map<Menu, Integer> menuCount, int target) {
        // given
        Order order = Order.of(Day.from(1), menuCount);

        // when
        int totalPrice = order.calculateTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(target);
    }

    static Stream<Arguments> menuProvider() {
        return Stream.of(
                arguments(Map.of(Menu.ICE_CREAM, 1), 5000),
                arguments(Map.of(Menu.TAPAS, 2), Menu.TAPAS.getPrice() * 2),
                arguments(Map.of(Menu.CAESAR_SALAD, 10), Menu.CAESAR_SALAD.getPrice() * 10),
                arguments(Map.of(Menu.BUTTON_MUSHROOM_SOUP, 1, Menu.ZERO_COLA, 1),
                        Menu.BUTTON_MUSHROOM_SOUP.getPrice() + Menu.ZERO_COLA.getPrice()),
                arguments(Map.of(Menu.BUTTON_MUSHROOM_SOUP, 3, Menu.ZERO_COLA, 4),
                        Menu.BUTTON_MUSHROOM_SOUP.getPrice() * 3 + Menu.ZERO_COLA.getPrice() * 4)
        );
    }

    @DisplayName("이벤트 카테고리의 메뉴 개수를 구할 수 있다.")
    @ParameterizedTest
    @MethodSource("weekCategoryProvider")
    void countWeekEventMenu(int day, Map<Menu, Integer> menuCount, int target) {
        // given
        Order order = Order.of(Day.from(day), menuCount);

        // when
        int count = order.countWeekEventMenu();

        // then
        assertThat(count).isEqualTo(target);
    }

    static Stream<Arguments> weekCategoryProvider() {
        return Stream.of(
                arguments(1, Map.of(Menu.BARBECUE_RIBS, 1), 1),
                arguments(1, Map.of(Menu.BARBECUE_RIBS, 2, Menu.SEAFOOD_PASTA, 3), 5),
                arguments(1, Map.of(Menu.BARBECUE_RIBS, 3, Menu.SEAFOOD_PASTA, 3,
                        Menu.ICE_CREAM, 6), 6),
                arguments(3, Map.of(Menu.ICE_CREAM, 1), 1),
                arguments(3, Map.of(Menu.ICE_CREAM, 2, Menu.CHOCOLATE_CAKE, 3), 5),
                arguments(3, Map.of(Menu.ICE_CREAM, 3, Menu.CHOCOLATE_CAKE, 3,
                        Menu.SEAFOOD_PASTA, 6), 6)
        );
    }

    @DisplayName("전체 주문 가격이 특정 가격보다 적은지 판별할 수 있다. True")
    @Test
    void isTotalPriceUnderTrue() {
        // given
        Day day = Day.from(1);
        Map<Menu, Integer> menu = Map.of(Menu.T_BONE_STEAK, 1, Menu.ZERO_COLA, 1);
        Order order = Order.of(day, menu);

        // when
        boolean totalPriceUnder = order.isTotalPriceUnder(59_999);

        // then
        assertThat(totalPriceUnder).isTrue();
    }

    @DisplayName("전체 주문 가격이 특정 가격보다 적은지 판별할 수 있다. False")
    @Test
    void isTotalPriceUnderFalse() {
        // given
        Day day = Day.from(1);
        Map<Menu, Integer> menu = Map.of(Menu.T_BONE_STEAK, 1, Menu.ZERO_COLA, 1);
        Order order = Order.of(day, menu);

        // when
        boolean totalPriceUnder = order.isTotalPriceUnder(60_000);

        // then
        assertThat(totalPriceUnder).isTrue();
    }

    @DisplayName("getter로 조회한 메뉴 카운트 맵을 변경할 수 없다.")
    @Test
    void getMenuCount() {
        // given
        Day day = Day.from(1);
        Map<Menu, Integer> menu = Map.of(Menu.T_BONE_STEAK, 1);
        Order order = Order.of(day, menu);

        // when
        Map<Menu, Integer> menuCount = order.getMenuCount();

        assertThatThrownBy(() -> menuCount.put(Menu.ICE_CREAM, 2))
                .isInstanceOf(UnsupportedOperationException.class);

    }

}