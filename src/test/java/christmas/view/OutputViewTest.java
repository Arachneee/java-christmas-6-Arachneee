package christmas.view;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.exception.ErrorMessage;
import christmas.response.EventDetailResponse;
import christmas.response.EventDetailResponse.EventDetailResponseBuilder;
import christmas.response.EventResponse;
import christmas.response.GiftMenuResponse;
import christmas.response.MenuCountResponse;
import christmas.response.OrderResponse;
import christmas.response.OrderSummaryResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
@DisplayName("출력 뷰는")
class OutputViewTest {

    TestWriter writer = new TestWriter();
    OutputView outputView;


    @BeforeEach
    void setUp() {
        writer.clear();
        outputView = new OutputView(writer);
    }

    @DisplayName("초기 인사를 출력할 수 있다.")
    @Test
    void printHello() {
        // given // when
        outputView.printHello();

        // then
        assertThat(writer.getString())
                .isEqualTo("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다." + lineSeparator());
    }

    @DisplayName("에러메시지를 출력할 수 있다.")
    @Test
    void printError() {
        // given
        String message = ErrorMessage.INVALID_ORDER.getMessage();

        // when
        outputView.printError(message);

        // then
        assertThat(writer.getString())
                .isEqualTo("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요." + lineSeparator());
    }

    @Nested
    @DisplayName("주문 해택 요약 정보를 출력할 수 있다.")
    class PrintOrderSummary {

        @DisplayName("해택이 존재한다.")
        @Test
        void printOrderSummary() {
            // given
            OrderResponse orderResponse = OrderResponse.of(3, List.of(
                    MenuCountResponse.of("티본스테이크", 1),
                    MenuCountResponse.of("바비큐립", 1),
                    MenuCountResponse.of("초코케이크", 2),
                    MenuCountResponse.of("제로콜라", 1)));

            EventDetailResponse eventDetailResponse = EventDetailResponseBuilder.builder()
                    .badge("산타")
                    .totalBenefitsAmount(31_246)
                    .priceBeforeEvent(142_000)
                    .priceAfterEvent(135_754)
                    .giftMenuResponses(List.of(GiftMenuResponse.of("샴페인", 1)))
                    .activeEvents(List.of(
                            EventResponse.of("크리스마스 디데이 할인", 1_200),
                            EventResponse.of("평일 할인", 4_046),
                            EventResponse.of("특별 할인", 1_000),
                            EventResponse.of("증정 이벤트", 25_000)))
                    .build();

            OrderSummaryResponse orderSummaryResponse = OrderSummaryResponse.of(orderResponse, eventDetailResponse);

            // when
            outputView.printOrderSummary(orderSummaryResponse);

            // then
            assertThat(writer.getString())
                    .isEqualTo("""
                            12월 3일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!
                                                    
                            <주문 메뉴>
                            티본스테이크 1개
                            바비큐립 1개
                            초코케이크 2개
                            제로콜라 1개
                                                    
                            <할인 전 총주문 금액>
                            142,000원
                                                    
                            <증정 메뉴>
                            샴페인 1개
                                                    
                            <혜택 내역>
                            크리스마스 디데이 할인: -1,200원
                            평일 할인: -4,046원
                            특별 할인: -1,000원
                            증정 이벤트: -25,000원
                                                    
                            <총혜택 금액>
                            -31,246원
                                                    
                            <할인 후 예상 결제 금액>
                            135,754원
                                                    
                            <12월 이벤트 배지>
                            산타
                            """);
        }

        @DisplayName("적용된 혜택이 없을 경우 없음을 출력한다.")
        @Test
        void printOrderSummaryNone() {
            // given
            OrderResponse orderResponse = OrderResponse.of(26, List.of(
                    MenuCountResponse.of("타파스", 1),
                    MenuCountResponse.of("제로콜라", 1)));

            EventDetailResponse eventDetailResponse = EventDetailResponseBuilder.builder()
                    .badge("")
                    .totalBenefitsAmount(0)
                    .priceBeforeEvent(8_500)
                    .priceAfterEvent(8_500)
                    .giftMenuResponses(List.of())
                    .activeEvents(List.of())
                    .build();

            OrderSummaryResponse orderSummaryResponse = OrderSummaryResponse.of(orderResponse, eventDetailResponse);

            // when
            outputView.printOrderSummary(orderSummaryResponse);

            // then
            assertThat(writer.getString())
                    .isEqualTo("""
                            12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!
                                                    
                            <주문 메뉴>
                            타파스 1개
                            제로콜라 1개
                                                    
                            <할인 전 총주문 금액>
                            8,500원
                                                    
                            <증정 메뉴>
                            없음
                                                    
                            <혜택 내역>
                            없음
                                                    
                            <총혜택 금액>
                            0원
                                                    
                            <할인 후 예상 결제 금액>
                            8,500원
                                                    
                            <12월 이벤트 배지>
                            없음
                            """);
        }
    }


}