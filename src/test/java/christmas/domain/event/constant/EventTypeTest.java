package christmas.domain.event.constant;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.day.Day;
import christmas.domain.discount.Discount;
import christmas.domain.discount.Discounts;
import christmas.domain.order.Order;
import christmas.domain.order.constant.Menu;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EventTypeTest {

    @DisplayName("할인 정책을 중복 적용할 수 있다.")
    @Test
    void discountAll() {
        // given
        Order order = Order.of(Day.from(25),
                Map.of(Menu.T_BONE_STEAK, 1, Menu.ICE_CREAM, 2, Menu.BUTTON_MUSHROOM_SOUP, 1, Menu.CHRISTMAS_PASTA, 2));

        // when
        Discounts discounts = EventType.discountAll(order);

        // then
        assertThat(discounts.getDiscounts())
                .containsExactlyInAnyOrder(Discount.of(EventType.CHRISTMAS_D_DAY_DISCOUNT, 3400),
                        Discount.of(EventType.WEEKDAY_DISCOUNT, 2023 * 2),
                        Discount.of(EventType.WEEKEND_DISCOUNT, 0),
                        Discount.of(EventType.SPECIAL_DISCOUNT, 1000),
                        Discount.of(EventType.GIFT, 25000));
    }

    @DisplayName("증정 이벤트인지 판별 할 수 있다. True")
    @Test
    void isGiftTrue() {
        // given // when
        boolean gift = EventType.GIFT.isGift();

        // then
        assertThat(gift).isTrue();
    }

    @DisplayName("증정 이벤트인지 판별 할 수 있다. True")
    @ParameterizedTest
    @ValueSource(strings = {"CHRISTMAS_D_DAY_DISCOUNT", "WEEKDAY_DISCOUNT", "WEEKEND_DISCOUNT", "SPECIAL_DISCOUNT"})
    void isGiftFalse(EventType eventType) {
        // given // when
        boolean gift = eventType.isGift();

        // then
        assertThat(gift).isFalse();
    }


}