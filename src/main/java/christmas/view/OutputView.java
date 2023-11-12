package christmas.view;

import static java.lang.System.lineSeparator;

import christmas.response.EventDetailResponse;
import christmas.response.EventResponse;
import christmas.response.GiftMenuResponse;
import christmas.response.MenuCountResponse;
import christmas.response.OrderResponse;
import christmas.response.OrderSummaryResponse;
import java.util.List;

public class OutputView {

    private static final int ZERO = 0;

    public void printHello() {
        System.out.println(Response.HELLO.value);
    }

    public void printError(final String message) {
        System.out.println(message);
    }

    public void printOrderSummary(final OrderSummaryResponse orderSummaryResponse) {
        printOrder(orderSummaryResponse.orderResponse());
        printDiscountDetail(orderSummaryResponse.eventDetailResponse());
    }

    private void printOrder(final OrderResponse orderResponse) {
        printPreViewHeader(orderResponse.day());
        printOrderMenuCount(orderResponse.menuCount());
    }

    private void printDiscountDetail(final EventDetailResponse eventDetailResponse) {
        printPriceBeforeEvent(eventDetailResponse.priceBeforeEvent());
        printGift(eventDetailResponse.giftMenuResponses());
        printActiveEvent(eventDetailResponse.activeEvents());
        printTotalBenefitsAmount(eventDetailResponse.totalBenefitsAmount());
        printPriceAfterEvent(eventDetailResponse.priceAfterEvent());
        printBadge(eventDetailResponse.badge());
    }

    private void printPreViewHeader(final int day) {
        System.out.printf(Response.PREVIEW_EVENT.value + Response.ENTER.value, day);
    }

    private void printOrderMenuCount(final List<MenuCountResponse> menuCountResponses) {
        System.out.println(Header.MENU.getValueWithEnter());

        menuCountResponses
                .forEach(menuCountResponse -> System.out.printf(Response.MENU_COUNT.value + Response.ENTER.value,
                        menuCountResponse.title(),
                        menuCountResponse.count()));
    }

    private void printPriceBeforeEvent(final int totalPrice) {
        System.out.println(Header.BEFORE_TOTAL_PRICE.getValueWithEnter());
        System.out.printf(Response.POSITIVE_MONEY.value + Response.ENTER.value, totalPrice);
    }

    private void printGift(final List<GiftMenuResponse> giftMenus) {
        System.out.println(Header.GIFT.getValueWithEnter());

        if (giftMenus.isEmpty()) {
            printNone();
            return;
        }

        giftMenus.forEach(giftMenu -> System.out.printf(Response.MENU_COUNT.value + Response.ENTER.value,
                giftMenu.title(), giftMenu.count()));
    }

    private void printActiveEvent(final List<EventResponse> discounts) {
        System.out.println(Header.DISCOUNT.getValueWithEnter());

        if (discounts.isEmpty()) {
            printNone();
            return;
        }

        discounts.forEach(discount -> System.out.printf(Response.DISCOUNT_RESULT.value + Response.ENTER.value,
                discount.title(), discount.amount()));
    }

    private void printTotalBenefitsAmount(final int totalDiscountAmount) {
        System.out.println(Header.TOTAL_DISCOUNT.getValueWithEnter());

        if (totalDiscountAmount == ZERO) {
            System.out.printf(Response.POSITIVE_MONEY.value + Response.ENTER.value, totalDiscountAmount);
            return;
        }

        System.out.printf(Response.NEGATIVE_MONEY.value + Response.ENTER.value, totalDiscountAmount);
    }

    private void printPriceAfterEvent(final int afterDiscountPayment) {
        System.out.println(Header.AFTER_PAYMENT.getValueWithEnter());
        System.out.printf(Response.POSITIVE_MONEY.value + Response.ENTER.value, afterDiscountPayment);
    }

    private void printBadge(final String badge) {
        System.out.println(Header.BADGE.getValueWithEnter());

        if (badge.isBlank()) {
            printNone();
            return;
        }

        System.out.println(badge);
    }

    private void printNone() {
        System.out.println(Response.NONE.value);
    }

    private enum Response {
        HELLO("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다."),
        PREVIEW_EVENT("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),
        MENU_COUNT("%s %d개"),
        DISCOUNT_RESULT("%s: -%,d원"),
        POSITIVE_MONEY("%,d원"),
        NEGATIVE_MONEY("-%,d원"),
        NONE("없음"),
        ENTER(lineSeparator());

        private final String value;

        Response(final String value) {
            this.value = value;
        }
    }

    private enum Header {
        MENU("<주문 메뉴>"),
        BEFORE_TOTAL_PRICE("<할인 전 총주문 금액>"),
        GIFT("<증정 메뉴>"),
        DISCOUNT("<혜택 내역>"),
        TOTAL_DISCOUNT("<총혜택 금액>"),
        AFTER_PAYMENT("<할인 후 예상 결제 금액>"),
        BADGE("<12월 이벤트 배지>");

        private final String value;

        Header(final String value) {
            this.value = value;
        }

        public String getValueWithEnter() {
            return Response.ENTER.value + value;
        }
    }
}
