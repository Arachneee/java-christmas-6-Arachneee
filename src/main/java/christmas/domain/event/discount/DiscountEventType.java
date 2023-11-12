package christmas.domain.event.discount;

import christmas.domain.event.Event;
import christmas.domain.order.Order;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public enum DiscountEventType implements Event {

    CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인", Order::isAfterChristmas,
            order -> order.countDayAfterFirstDay() * Amount.CHRISTMAS_ONCE.value + Amount.CHRISTMAS_OFFSET.value),
    WEEKDAY_DISCOUNT("평일 할인", Order::isWeekend,
            order -> order.countWeekEventMenu() * Amount.WEEKDAY_ONCE.value),
    WEEKEND_DISCOUNT("주말 할인", Order::isWeekday,
            order -> order.countWeekEventMenu() * Amount.WEEKEND_ONCE.value),
    SPECIAL_DISCOUNT("특별 할인", order -> order.isNotSunDay() && order.isNotChristmasDay(),
            order -> Amount.SPECIAL_ONCE.value);

    private final String title;
    private final Predicate<Order> unavailable;
    private final Function<Order, Integer> calculate;

    DiscountEventType(final String title, final Predicate<Order> unavailable,
            final Function<Order, Integer> calculate) {
        this.title = title;
        this.unavailable = unavailable;
        this.calculate = calculate;
    }

    public static List<Discount> discountAll(final Order order) {
        return Arrays.stream(values())
                .map(eventType -> eventType.createDiscount(order))
                .toList();
    }

    private Discount createDiscount(final Order order) {
        return Discount.of(this, this.calculateBenefits(order));
    }

    @Override
    public int calculateBenefits(final Order order) {
        if (order.isTotalPriceUnder(Amount.BASE_MIN.value) || unavailable.test(order)) {
            return Amount.ZERO.value;
        }

        return calculate.apply(order);
    }

    @Override
    public String getTitle() {
        return title;
    }

    private enum Amount {
        CHRISTMAS_ONCE(100),
        CHRISTMAS_OFFSET(1_000),
        WEEKDAY_ONCE(2_023),
        WEEKEND_ONCE(2_023),
        SPECIAL_ONCE(1_000),
        BASE_MIN(10_000),
        ZERO(0);

        private final int value;

        Amount(final int value) {
            this.value = value;
        }
    }
}
