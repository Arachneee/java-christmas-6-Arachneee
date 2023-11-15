package christmas.service.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import christmas.domain.event.discount.DiscountEventType;
import christmas.domain.event.discount.DiscountRepository;
import christmas.domain.order.Order;
import christmas.domain.order.Day;
import christmas.domain.order.constant.Menu;
import christmas.response.EventResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestFactory;
@Nested
@DisplayName("할인 서비스")
class DiscountServiceTest {

    DiscountService discountService;

    @BeforeEach
    void setUp() {
        DiscountRepository discountRepository = new DiscountRepository();
        discountService = new DiscountService(discountRepository);
    }

    @DisplayName("할인 이벤트 적용 시나리오")
    @TestFactory
    Collection<DynamicTest> getActiveEventResult() {
        // given
        Order order = Order.of(Day.from(3),
                Map.of(Menu.T_BONE_STEAK, 1, Menu.BARBECUE_RIBS, 1,
                        Menu.CHOCOLATE_CAKE, 2, Menu.ZERO_COLA, 1));

        discountService.applyEventAll(DiscountEventType.class, order);

        // when // then
        return List.of(
                DynamicTest.dynamicTest("적용된 할인 제목과 금액 리스트를 구할 수 있다.", () -> {
                    // given // when
                    List<EventResponse> activeEventResult = discountService.getActiveEventResult();

                    // then
                    assertThat(activeEventResult).hasSize(3)
                            .extracting("title", "amount")
                            .containsExactlyInAnyOrder(
                                    tuple("크리스마스 디데이 할인", 1_200),
                                    tuple("평일 할인", 4_046),
                                    tuple("특별 할인", 1_000)
                            );
                }),
                DynamicTest.dynamicTest("전체 적용된 혜택 금액을 구할 수 있다.", () -> {
                    // given // when
                    int totalAmount = discountService.calculateTotalBenefits();

                    // then
                    assertThat(totalAmount).isEqualTo(1_200 + 4_046 + 1_000);
                })
        );

    }

}