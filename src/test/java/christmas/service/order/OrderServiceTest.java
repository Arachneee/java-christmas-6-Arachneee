package christmas.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;

import christmas.domain.order.Order;
import christmas.domain.order.day.Day;
import christmas.domain.order.menu.Menu;
import christmas.response.EventDetailResponse;
import christmas.response.EventDetailResponse.EventDetailResponseBuilder;
import christmas.response.EventResponse;
import christmas.response.GiftMenuResponse;
import christmas.response.OrderResponse;
import christmas.response.OrderSummaryResponse;
import christmas.service.event.EventDetailService;
import java.util.List;
import java.util.Map;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderServiceTest {

    EventDetailService eventDetailService;
    OrderService orderService;

    @BeforeEach
    void setUp() {
        eventDetailService = Mockito.mock(EventDetailService.class);
        orderService = new OrderService(eventDetailService);
    }

    @DisplayName("주문 Summary를 생성 할 수 있다.")
    @Test
    void createOrderSummary() {
        // given
        Order order = Order.of(Day.from(3),
                Map.of(Menu.T_BONE_STEAK, 1,
                        Menu.BARBECUE_RIBS, 1,
                        Menu.CHOCOLATE_CAKE, 2,
                        Menu.ZERO_COLA, 1));

        given(eventDetailService.getEventDetail(order.calculateTotalPrice()))
                .willReturn(EventDetailResponseBuilder.builder()
                        .priceBeforeEvent(142_000)
                        .priceAfterEvent(135_754)
                        .totalBenefitsAmount(31_246)
                        .giftMenuResponses(List.of(GiftMenuResponse.of("샴페인", 1)))
                        .activeEvents(List.of(EventResponse.of("크리스마스 디데이 할인", 1_200),
                                EventResponse.of("평일 할인", 4_046),
                                EventResponse.of("특별 할인", 1_000),
                                EventResponse.of("증정 이벤트", 2_5000)))
                        .badge("산타")
                        .build());

        // when
        OrderSummaryResponse orderSummary = orderService.createOrderSummary(order);

        // then
        OrderResponse orderResponse = orderSummary.orderResponse();

        assertThat(orderResponse.day()).isEqualTo(3);
        assertThat(orderResponse.menuCount()).hasSize(4)
                .extracting("title", "count")
                .containsExactlyInAnyOrder(
                        tuple("티본스테이크", 1),
                        tuple("바비큐립", 1),
                        tuple("초코케이크", 2),
                        tuple("제로콜라", 1)
                );

        EventDetailResponse eventDetail = orderSummary.eventDetailResponse();

        assertThat(eventDetail.priceBeforeEvent()).isEqualTo(142_000);
        assertThat(eventDetail.giftMenuResponses()).hasSize(1)
                .extracting("title", "count")
                .containsExactly(Tuple.tuple("샴페인", 1));
        assertThat(eventDetail.activeEvents()).hasSize(4)
                .extracting("title", "amount")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("크리스마스 디데이 할인", 1_200),
                        Tuple.tuple("평일 할인", 4_046),
                        Tuple.tuple("특별 할인", 1_000),
                        Tuple.tuple("증정 이벤트", 25_000)
                );
        assertThat(eventDetail.totalBenefitsAmount()).isEqualTo(31_246);
        assertThat(eventDetail.priceAfterEvent()).isEqualTo(135_754);
        assertThat(eventDetail.badge()).isEqualTo("산타");
    }
}