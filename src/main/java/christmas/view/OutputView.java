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
import static christmas.view.constant.Response.POSITIVE_MONEY;
import static christmas.view.constant.Response.PREVIEW_EVENT;
import static java.lang.System.lineSeparator;

import christmas.controller.dto.DiscountResultDto;
import christmas.controller.dto.DiscountResultsDto;
import christmas.controller.dto.GiftDto;
import christmas.controller.dto.OrderDto;
import java.util.List;

public class OutputView {

    private static final String ENTER = lineSeparator();
    private static final String DOUBLE_ENTER = ENTER + ENTER;

    public void printHello() {
        System.out.println(HELLO.getMessage());
    }


    public void printError(final String message) {
        System.out.println(message);
    }

    public void printOrder(final OrderDto orderDto) {
        printPreViewHeader(orderDto.day());
        printOrderMenuCount(orderDto);
        printBeforeDiscountTotalPrice(orderDto.totalPrice());
    }

    private static void printPreViewHeader(final int day) {
        System.out.printf(getPreViewFormat(), day);
    }

    private static String getPreViewFormat() {
        return PREVIEW_EVENT.getMessage() + DOUBLE_ENTER;
    }

    private static void printOrderMenuCount(final OrderDto orderDto) {
        System.out.println(MENU.getMessage());

        orderDto.menuCount()
                .forEach(menuCountDto -> System.out.printf(MENU_COUNT.getMessage() + ENTER,
                        menuCountDto.name(),
                        menuCountDto.count()));

        System.out.println();
    }

    private void printBeforeDiscountTotalPrice(final int totalPrice) {
        System.out.println(BEFORE_TOTAL_PRICE.getMessage());
        System.out.printf(POSITIVE_MONEY.getMessage() + DOUBLE_ENTER, totalPrice);
    }

    public void printAllResults(final DiscountResultsDto discountResultsDto) {
        printGiftMenu(discountResultsDto.giftDto());
        printDiscountResults(discountResultsDto.discounts());
        printTotalDiscountAmount(discountResultsDto.totalDiscountAmount());
        printAfterDiscountPayment(discountResultsDto.afterDiscountPayment());
        printBadge(discountResultsDto.badge());
    }

    private static void printGiftMenu(final GiftDto giftDto) {
        System.out.println(GIFT.getMessage());
        System.out.printf(MENU_COUNT.getMessage() + DOUBLE_ENTER,
                giftDto.title(),
                giftDto.count());
    }

    private void printDiscountResults(final List<DiscountResultDto> discounts) {
        System.out.println(DISCOUNT.getMessage());

        discounts.forEach(discount -> System.out.printf(DISCOUNT_RESULT.getMessage() + ENTER,
                discount.title(), discount.payment()));

        System.out.println();
    }

    private void printTotalDiscountAmount(final int totalDiscountAmount) {
        System.out.println(TOTAL_DISCOUNT.getMessage());
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
