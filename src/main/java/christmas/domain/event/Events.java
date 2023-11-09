package christmas.domain.event;

import christmas.domain.order.Order;
import christmas.domain.result.DiscountResult;
import christmas.domain.result.DiscountResults;
import java.util.Arrays;

public enum Events {

    CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인", new ChristmasEvent(), true),
    WEEKDAY_DISCOUNT("평일 할인", new WeekEvent(), true),
    WEEKEND_DISCOUNT("주말 할인", new WeekEvent(), true),
    SPECIAL_DISCOUNT("특별 할인",new SpecialEvent(), true),
    PRESENTATION("증정 이벤트",new PresentationEvent(), false);

    private final String title;
    private final Event event;
    private final boolean isDiscount;

    Events(final String title, final Event event, final boolean isDiscount) {
        this.title = title;
        this.event = event;
        this.isDiscount = isDiscount;
    }

    public static DiscountResults discount(final Order order) {
        return DiscountResults.from(Arrays.stream(values())
                .map(entry -> DiscountResult.of(entry, entry.getApply(order)))
                .toList());
    }

    private int getApply(final Order order) {
        return this.event.apply(order);
    }

    public boolean isDiscount() {
        return isDiscount;
    }
}
