package christmas.domain.event;

import christmas.domain.discount.Discount;
import christmas.domain.discount.Discounts;
import christmas.domain.order.Order;
import christmas.domain.order.constant.Menu;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public enum EventType {

    CHRISTMAS_D_DAY_DISCOUNT(Title.CHRISTMAS_D_DAY.text, Order::isAfterChristmas,
            order -> order.countDayAfterFirstDay() * Amount.CHRISTMAS_ONCE.value + Amount.CHRISTMAS_OFFSET.value),
    WEEKDAY_DISCOUNT(Title.WEEKDAY.text, Order::isWeekend,
            order -> order.countWeekEventMenu() * Amount.WEEKDAY_ONCE.value),
    WEEKEND_DISCOUNT(Title.WEEKEND.text, Order::isWeekday,
            order -> order.countWeekEventMenu() * Amount.WEEKEND_ONCE.value),
    SPECIAL_DISCOUNT(Title.SPECIAL.text, order -> order.isNotSunDay() && order.isNotChristmasDay(),
            order -> Amount.SPECIAL_ONCE.value),
    GIFT("증정 이벤트", order -> order.isTotalPriceUnder(Amount.GIFT_MIN.value),
            order -> GiftMenu.GIFT.menu.getPrice());

    private final String title;
    private final Predicate<Order> unavailable;
    private final Function<Order, Integer> calculate;

    EventType(final String title, final Predicate<Order> unavailable, final Function<Order, Integer> calculate) {
        this.title = title;
        this.unavailable = unavailable;
        this.calculate = calculate;
    }

    public static Discounts discountAll(final Order order) {
        return Discounts.of(applyAllEvent(order), order.calculateTotalPrice());
    }

    private static List<Discount> applyAllEvent(final Order order) {
        return Arrays.stream(values())
                .map(eventType -> Discount.of(eventType, eventType.getApply(order)))
                .toList();
    }

    public int getApply(final Order order) {
        if (order.isTotalPriceUnder(Amount.BASE_MIN.value) || unavailable.test(order)) {
            return 0;
        }

        return calculate.apply(order);
    }

    public boolean isGift() {
        return this.equals(GIFT);
    }

    public String getTitle() {
        return title;
    }

    private enum Title {
        CHRISTMAS_D_DAY("크리스마스 디데이 할인"),
        WEEKDAY("평일 할인"),
        WEEKEND("주말 할인"),
        SPECIAL("특별 할인"),
        GIFT("증정 이벤트");

        private final String text;

        Title(final String text) {
            this.text = text;
        }
    }

    private enum Amount {
        CHRISTMAS_ONCE(100),
        CHRISTMAS_OFFSET(1_000),
        WEEKDAY_ONCE(2_023),
        WEEKEND_ONCE(2_023),
        SPECIAL_ONCE(1_000),
        BASE_MIN(10_000),
        GIFT_MIN(120_000);

        private final int value;
        Amount(final int value) {
            this.value = value;
        }
    }

    public enum GiftMenu {
        GIFT(Menu.CHAMPAGNE);

        private final Menu menu;
        GiftMenu(final Menu menu) {
            this.menu = menu;
        }

    }
}
