package christmas.service.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import christmas.response.EventDetailResponse;
import christmas.response.EventResponse;
import christmas.response.GiftMenuResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
@DisplayName("이벤트 상세 서비스는")
class EventDetailServiceTest {

    private DiscountService discountService;
    private GiftService giftService;
    private EventDetailService eventDetailService;

    @BeforeEach
    void setUp() {
        discountService = mock(DiscountService.class);
        giftService = mock(GiftService.class);
        eventDetailService = new EventDetailService(discountService, giftService);
    }

    @DisplayName("할인 적용 세부 사항을 얻을 수 있다.")
    @Test
    void getEventDetail() {
        // given
        given(discountService.calculateTotalBenefits()).willReturn(4_023);
        given(giftService.calculateTotalBenefits()).willReturn(25_000);

        given(giftService.getGiftMenuResponse()).willReturn(List.of(GiftMenuResponse.of("샴페인", 1)));

        given(discountService.getActiveEventResult()).willReturn(
                List.of(EventResponse.of("크리스마스 디데이 할인", 1_000),
                        EventResponse.of("평일 할인", 2_023),
                        EventResponse.of("특별 할인", 1_000))
        );

        given(giftService.getActiveEventResult()).willReturn(
                List.of(EventResponse.of("증정 이벤트", 25_000)));

        int priceBeforeDiscount = 150_000;

        // when
        EventDetailResponse eventDetail = eventDetailService.getEventDetail(priceBeforeDiscount);

        // then
        assertThat(eventDetail.priceBeforeEvent()).isEqualTo(150_000);

        assertThat(eventDetail.giftMenuResponses()).hasSize(1)
                .extracting("title", "count")
                .containsExactly(tuple("샴페인", 1));

        assertThat(eventDetail.activeEvents()).hasSize(4)
                .extracting("title", "amount")
                .containsExactlyInAnyOrder(
                        tuple("크리스마스 디데이 할인", 1_000),
                        tuple("평일 할인", 2_023),
                        tuple("특별 할인", 1_000),
                        tuple("증정 이벤트", 25_000)
                );

        assertThat(eventDetail.totalBenefitsAmount()).isEqualTo(29_023);
        assertThat(eventDetail.priceAfterEvent()).isEqualTo(150_000 - 4_023);
        assertThat(eventDetail.badge()).isEqualTo("산타");
    }

}