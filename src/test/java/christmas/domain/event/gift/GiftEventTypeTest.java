package christmas.domain.event.gift;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.order.Order;
import christmas.domain.order.Day;
import christmas.domain.order.constant.Menu;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GiftEventTypeTest {

    @DisplayName("증정 이벤트의 해택 금액을 계산 할 수 있다.")
    @Test
    void calculateBenefits() {
        // given
        Order order = Order.of(Day.from(1), Map.of(Menu.T_BONE_STEAK, 3));

        // when
        int benefits = GiftEventType.PRESENTATION.calculateBenefits(order);

        // then
        assertThat(benefits).isEqualTo(25_000);
    }

    @DisplayName("증정 이벤트의 메뉴이름을 알 수 있다.")
    @Test
    void getMenuTitle() {
        // given // when
        String menuTitle = GiftEventType.PRESENTATION.getMenuTitle();

        // then
        assertThat(menuTitle).isEqualTo("샴페인");
    }

    @DisplayName("증정 이벤트의 메뉴의 수량을 알 수 있다.")
    @Test
    void getCount() {
        // given // when
        int count = GiftEventType.PRESENTATION.getCount();

        // then
        assertThat(count).isEqualTo(1);
    }
}