package christmas.domain.event.gift;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.event.Event;
import christmas.domain.order.Day;
import christmas.domain.order.Order;
import christmas.domain.order.constant.Menu;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("선물 이벤트 타입은")
class GiftEventTypeTest {

    @Nested
    @DisplayName("공통적으로")
    class Common {

        @DisplayName("할인 정책을 중복 적용할 수 있다.")
        @Test
        void discountAll() {
            // given
            Order order = Order.of(Day.from(25),
                    Map.of(Menu.T_BONE_STEAK, 2, Menu.ICE_CREAM, 2, Menu.BUTTON_MUSHROOM_SOUP, 1, Menu.CHRISTMAS_PASTA,
                            2));

            // when
            EnumMap<GiftEventType, Integer> giftEventTypeResult = Event.applyAll(GiftEventType.class, order);

            // then
            assertThat(giftEventTypeResult)
                    .containsExactlyInAnyOrderEntriesOf(Map.of(GiftEventType.PRESENTATION, 25000));
        }

        @DisplayName("혜택 금액을 계산 할 수 있다.")
        @Test
        void calculateBenefits() {
            // given
            Order order = Order.of(Day.from(1), Map.of(Menu.T_BONE_STEAK, 3));

            // when
            int benefits = GiftEventType.PRESENTATION.calculateBenefits(order);

            // then
            assertThat(benefits).isEqualTo(25_000);
        }

        @DisplayName("메뉴이름을 알 수 있다.")
        @Test
        void getMenuTitle() {
            // given // when
            String menuTitle = GiftEventType.PRESENTATION.getMenuTitle();

            // then
            assertThat(menuTitle).isEqualTo("샴페인");
        }

        @DisplayName("메뉴의 수량을 알 수 있다.")
        @Test
        void getCount() {
            // given // when
            int count = GiftEventType.PRESENTATION.getCount();

            // then
            assertThat(count).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("증정 이벤트는")
    class PresentationEvent {

        @DisplayName("할인 전 주문금액이 120,000이상일 때 적용된다.")
        @Test
        void calculateBenefitsTrue() {
            // given
            Order order = Order.of(Day.from(1), Map.of(Menu.T_BONE_STEAK, 3));

            // when
            int benefits = GiftEventType.PRESENTATION.calculateBenefits(order);

            // then
            assertThat(benefits).isEqualTo(25_000);
        }

        @DisplayName("할인 전 주문금액이 120,000이하면 적용되지 않는다.")
        @Test
        void calculateBenefitsFalse() {
            // given
            Order order = Order.of(Day.from(1), Map.of(Menu.T_BONE_STEAK, 2));

            // when
            int benefits = GiftEventType.PRESENTATION.calculateBenefits(order);

            // then
            assertThat(benefits).isEqualTo(0);
        }
    }


}