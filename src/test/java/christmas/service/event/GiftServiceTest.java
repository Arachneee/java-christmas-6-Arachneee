package christmas.service.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import christmas.domain.event.gift.GiftRepository;
import christmas.domain.order.Order;
import christmas.domain.order.day.Day;
import christmas.domain.order.menu.Menu;
import christmas.response.EventResponse;
import christmas.response.GiftMenuResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class GiftServiceTest {

    GiftService giftService;

    @BeforeEach
    void setUp() {
        GiftRepository giftRepository = new GiftRepository();
        giftService = new GiftService(giftRepository);
    }

    @DisplayName("할인 적용 시나리오")
    @TestFactory
    Collection<DynamicTest> getActiveEventResult() {
        // given
        Order order = Order.of(Day.from(3),
                Map.of(Menu.T_BONE_STEAK, 1, Menu.BARBECUE_RIBS, 1,
                        Menu.CHOCOLATE_CAKE, 2, Menu.ZERO_COLA, 1));

        giftService.applyEventAll(order);

        // when // then
        return List.of(
                DynamicTest.dynamicTest("적용된 할인 제목과 금액 리스트를 구할 수 있다.", () -> {
                    // given // when
                    List<EventResponse> activeEventResult = giftService.getActiveEventResult();

                    // then
                    assertThat(activeEventResult).hasSize(1)
                            .extracting("title", "amount")
                            .containsExactlyInAnyOrder(
                                    tuple("증정 이벤트", 25_000)
                            );
                }),
                DynamicTest.dynamicTest("전체 적용된 해택 금액을 구할 수 있다.", () -> {
                    // given // when
                    int totalAmount = giftService.calculateTotalBenefits();

                    // then
                    assertThat(totalAmount).isEqualTo(25_000);
                }),
                DynamicTest.dynamicTest("전체 증정되는 메뉴이름과 수량을 구할 수 있다.", () -> {
                    // given // when
                    List<GiftMenuResponse> giftMenuResponse = giftService.getGiftMenuResponse();

                    // then
                    assertThat(giftMenuResponse).hasSize(1)
                            .extracting("title", "count")
                            .containsExactlyInAnyOrder(
                                    tuple("샴페인", 1)
                            );
                })
        );

    }

}