package christmas.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constant.Menu;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderTest {

    @DisplayName("총 주문 가격을 계산할 수 있다.")
    @ParameterizedTest
    @MethodSource("menuProvider")
    void calculateTotalPrice(Map<Menu, Integer> menuCount, int target) {
        // given
        Order order = Order.of(OrderDay.from(1), menuCount);

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
        Order order = Order.of(OrderDay.from(day), menuCount);

        // when
        int count = order.countWeekEventMenu();

        // then
        assertThat(count).isEqualTo(target);
    }

    static Stream<Arguments> weekCategoryProvider() {
        return Stream.of(
                arguments(1, Map.of(Menu.BARBECUE_RIBS, 1), 1),
                arguments(1, Map.of(Menu.BARBECUE_RIBS, 2, Menu.SEAFOOD_PASTA, 3), 5),
                arguments(1, Map.of(Menu.BARBECUE_RIBS, 3, Menu.SEAFOOD_PASTA, 3, Menu.ICE_CREAM, 6), 6),
                arguments(3, Map.of(Menu.ICE_CREAM, 1), 1),
                arguments(3, Map.of(Menu.ICE_CREAM, 2, Menu.CHOCOLATE_CAKE, 3), 5),
                arguments(3, Map.of(Menu.ICE_CREAM, 3, Menu.CHOCOLATE_CAKE, 3, Menu.SEAFOOD_PASTA, 6), 6)
        );
    }

}