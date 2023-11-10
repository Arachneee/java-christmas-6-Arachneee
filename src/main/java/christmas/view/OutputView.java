package christmas.view;

import static christmas.view.constant.Header.AFTER_PAYMENT;
import static christmas.view.constant.Header.BADGE;
import static christmas.view.constant.Header.BEFORE_TOTAL_PRICE;
import static christmas.view.constant.Header.DISCOUNT;
import static christmas.view.constant.Header.GIFT;
import static christmas.view.constant.Header.HELLO;
import static christmas.view.constant.Header.MENU;
import static christmas.view.constant.Header.TOTAL_DISCOUNT;
import static christmas.view.constant.Response.DISCOUNT_RESULT;
import static christmas.view.constant.Response.MENU_COUNT;
import static christmas.view.constant.Response.NEGATIVE_MONEY;
import static christmas.view.constant.Response.NONE;
import static christmas.view.constant.Response.POSITIVE_MONEY;
import static christmas.view.constant.Response.PREVIEW_EVENT;
import static java.lang.System.lineSeparator;

import christmas.controller.dto.DiscountDto;
import christmas.controller.dto.DiscountsDto;
import christmas.controller.dto.GiftDto;
import christmas.controller.dto.OrderDto;
import java.util.List;

public class OutputView {

    private static final String ENTER = lineSeparator();
    private static final String DOUBLE_ENTER = ENTER + ENTER;
    private static final int ZERO = 0;

    public void printHello() {
        System.out.println(HELLO.getMessage());
    }


    public void printError(final String message) {
        System.out.println(message);
    }

    public void printAllOrder(final OrderDto orderDto) {
        printPreViewHeader(orderDto.day());
        printOrderMenuCount(orderDto);
    }

    private void printPreViewHeader(final int day) {
        System.out.printf(getPreViewFormat(), day);
    }

    private String getPreViewFormat() {
        return PREVIEW_EVENT.getMessage() + DOUBLE_ENTER;
    }

    private void printOrderMenuCount(final OrderDto orderDto) {
        System.out.println(MENU.getMessage());

        orderDto.menuCount()
                .forEach(menuCountDto -> System.out.printf(MENU_COUNT.getMessage() + ENTER,
                        menuCountDto.title(),
                        menuCountDto.count()));

        System.out.println();
    }

    public void printAllResults(final DiscountsDto discountsDto) {
        printBeforeDiscountTotalPrice(discountsDto.amountBeforeDiscount());
        printGiftMenu(discountsDto.giftDto());
        printDiscountResults(discountsDto.activeDiscount());
        printTotalDiscountAmount(discountsDto.totalDiscountAmount());
        printAfterDiscountPayment(discountsDto.amountAfterDiscount());
        printBadge(discountsDto.badge());
    }

    private void printBeforeDiscountTotalPrice(final int totalPrice) {
        System.out.println(BEFORE_TOTAL_PRICE.getMessage());
        System.out.printf(POSITIVE_MONEY.getMessage() + DOUBLE_ENTER, totalPrice);
    }

    private void printGiftMenu(final GiftDto giftDto) {
        System.out.println(GIFT.getMessage());

        printGiftResult(giftDto);
    }

    private void printGiftResult(final GiftDto giftDto) {
        if (giftDto.count() == ZERO) {
            System.out.println(NONE.getMessage() + ENTER);
            return;
        }

        System.out.printf(MENU_COUNT.getMessage() + DOUBLE_ENTER,
                giftDto.title(),
                giftDto.count());
    }

    private void printDiscountResults(final List<DiscountDto> discounts) {
        System.out.println(DISCOUNT.getMessage());

        printDiscounts(discounts);
    }

    private void printDiscounts(final List<DiscountDto> discounts) {
        if (discounts.isEmpty()) {
            System.out.println(NONE.getMessage() + ENTER);
            return;
        }

        discounts.forEach(discount -> System.out.printf(DISCOUNT_RESULT.getMessage() + ENTER,
                discount.title(), discount.payment()));
        System.out.println();
    }

    private void printTotalDiscountAmount(final int totalDiscountAmount) {
        System.out.println(TOTAL_DISCOUNT.getMessage());

        printTotalDiscount(totalDiscountAmount);
    }

    private void printTotalDiscount(final int totalDiscountAmount) {
        if (totalDiscountAmount == ZERO) {
            System.out.printf(POSITIVE_MONEY.getMessage() + DOUBLE_ENTER, totalDiscountAmount);
            return;
        }

        System.out.printf(NEGATIVE_MONEY.getMessage() + DOUBLE_ENTER, totalDiscountAmount);
    }

    private void printAfterDiscountPayment(final int afterDiscountPayment) {
        System.out.println(AFTER_PAYMENT.getMessage());
        System.out.printf(POSITIVE_MONEY.getMessage() + DOUBLE_ENTER, afterDiscountPayment);
    }

    private void printBadge(final String badge) {
        System.out.println(BADGE.getMessage());
        System.out.println(badge);
    }
}
