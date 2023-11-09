package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import christmas.constant.Menu;
import christmas.domain.Order;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;

class ChristmasDiscountTest {

    @DisplayName("날짜에 따른 할인 금액을 계산할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"1,1000", "2,1100", "3,1200", "24,3300","25,3400"}, delimiter = ',')
    void calculateAmount(int day, int discountTarget) {
        // given
        ChristmasDiscount christmasDiscount = new ChristmasDiscount();
        OrderDay orderDay = OrderDay.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.TIBONSTAKE, 1));


        // when
        int discountAmount = christmasDiscount.calculateAmount(order);

        // then
        assertThat(discountAmount).isEqualTo(discountTarget);
    }


}