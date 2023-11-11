package christmas.view;

import static java.lang.System.lineSeparator;

import christmas.response.DiscountDetailResponse;
import christmas.response.DiscountResponse;
import christmas.response.GiftResponse;
import christmas.response.MenuCountResponse;
import christmas.response.OrderResponse;
import christmas.response.OrderSummaryResponse;
import java.util.List;

public class OutputView {

    private static final int ZERO = 0;

    public void printHello() {
        System.out.println(Header.HELLO.value);
    }

    public void printError(final String message) {
        System.out.println(message);
    }

    public void printOrderSummary(final OrderSummaryResponse orderSummaryResponse) {
        printOrder(orderSummaryResponse.orderResponse());
        printDiscountDetail(orderSummaryResponse.discountDetailResponse());
    }

    private void printOrder(final OrderResponse orderResponse) {
        printPreViewHeader(orderResponse.day());
        printOrderMenuCount(orderResponse.menuCount());
    }

    private void printDiscountDetail(final DiscountDetailResponse discountDetailResponse) {
        printPriceBeforeDiscount(discountDetailResponse.priceBeforeDiscount());
        printGift(discountDetailResponse.giftResponse());
        printActiveDiscount(discountDetailResponse.activeDiscounts());
        printTotalDiscountAmount(discountDetailResponse.totalDiscountAmount());
        printPriceAfterDiscount(discountDetailResponse.priceAfterDiscount());
        printBadge(discountDetailResponse.badge());
    }

    private void printPreViewHeader(final int day) {
        System.out.printf(Response.PREVIEW_EVENT.value + Response.DOUBLE_ENTER.value, day);
    }

    private void printOrderMenuCount(final List<MenuCountResponse> menuCountResponses) {
        System.out.println(Header.MENU.value);

        menuCountResponses
                .forEach(menuCountResponse -> System.out.printf(Response.MENU_COUNT.value + Response.ENTER.value,
                        menuCountResponse.title(),
                        menuCountResponse.count()));

        System.out.println();
    }

    private void printPriceBeforeDiscount(final int totalPrice) {
        System.out.println(Header.BEFORE_TOTAL_PRICE.value);
        System.out.printf(Response.POSITIVE_MONEY.value + Response.DOUBLE_ENTER.value, totalPrice);
    }

    private void printGift(final GiftResponse giftResponse) {
        System.out.println(Header.GIFT.value);

        if (giftResponse.count() == ZERO) {
            System.out.println(Response.NONE.value + Response.ENTER.value);
            return;
        }

        System.out.printf(Response.MENU_COUNT.value + Response.DOUBLE_ENTER.value,
                giftResponse.title(), giftResponse.count());
    }

    private void printActiveDiscount(final List<DiscountResponse> discounts) {
        System.out.println(Header.DISCOUNT.value);

        if (discounts.isEmpty()) {
            System.out.println(Response.NONE.value + Response.ENTER.value);
            return;
        }

        discounts.forEach(discount -> System.out.printf(Response.DISCOUNT_RESULT.value + Response.ENTER.value,
                discount.title(), discount.amount()));

        System.out.println();
    }

    private void printTotalDiscountAmount(final int totalDiscountAmount) {
        System.out.println(Header.TOTAL_DISCOUNT.value);

        if (totalDiscountAmount == ZERO) {
            System.out.printf(Response.POSITIVE_MONEY.value + Response.DOUBLE_ENTER.value, totalDiscountAmount);
            return;
        }

        System.out.printf(Response.NEGATIVE_MONEY.value + Response.DOUBLE_ENTER.value, totalDiscountAmount);
    }

    private void printPriceAfterDiscount(final int afterDiscountPayment) {
        System.out.println(Header.AFTER_PAYMENT.value);
        System.out.printf(Response.POSITIVE_MONEY.value + Response.DOUBLE_ENTER.value, afterDiscountPayment);
    }

    private void printBadge(final String badge) {
        System.out.println(Header.BADGE.value);

        if (badge.isBlank()) {
            System.out.println(Response.NONE.value);
            return;
        }

        System.out.println(badge);
    }

    private enum Response {

        PREVIEW_EVENT("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),
        MENU_COUNT("%s %d개"),
        DISCOUNT_RESULT("%s: -%,d원"),
        POSITIVE_MONEY("%,d원"),
        NEGATIVE_MONEY("-%,d원"),
        NONE("없음"),
        ENTER(lineSeparator()),
        DOUBLE_ENTER(lineSeparator() + lineSeparator());

        private final String value;

        Response(final String value) {
            this.value = value;
        }
    }

    private enum Header {
        HELLO("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다."),
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
    }
}
